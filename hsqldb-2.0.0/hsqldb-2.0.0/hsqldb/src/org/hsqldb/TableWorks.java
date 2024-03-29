/* Copyright (c) 2001-2010, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb;

import org.hsqldb.HsqlNameManager.HsqlName;
import org.hsqldb.error.Error;
import org.hsqldb.error.ErrorCode;
import org.hsqldb.index.Index;
import org.hsqldb.lib.ArrayUtil;
import org.hsqldb.lib.HsqlArrayList;
import org.hsqldb.lib.OrderedHashSet;
import org.hsqldb.navigator.RowIterator;
import org.hsqldb.persist.PersistentStore;
import org.hsqldb.rights.Grantee;
import org.hsqldb.types.Types;

/**
 * The methods in this class perform alterations to the structure of an
 * existing table which may result in a new Table object
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.7.0
 */
public class TableWorks {

    OrderedHashSet emptySet = new OrderedHashSet();
    private Database database;
    private Table table;
    private Session session;

    public TableWorks(Session session, Table table) {

        this.database = table.database;
        this.table = table;
        this.session = session;
    }

    public Table getTable() {
        return table;
    }

    void checkCreateForeignKey(Constraint c) {

        boolean check =
                c.core.updateAction == SchemaObject.ReferentialAction
                        .SET_DEFAULT || c.core.updateAction == SchemaObject
                        .ReferentialAction.SET_NULL || c.core
                        .updateAction == SchemaObject.ReferentialAction.CASCADE || c
                        .core.deleteAction == SchemaObject.ReferentialAction
                        .SET_DEFAULT || c.core.deleteAction == SchemaObject
                        .ReferentialAction.SET_NULL;

        if (check) {
            for (int i = 0; i < c.core.refCols.length; i++) {
                ColumnSchema col = table.getColumn(c.core.refCols[i]);

                if (col.isGenerated()) {
                    throw Error.error(ErrorCode.X_42524, col.getNameString());
                }
            }
        }

        if (c.core.mainName == table.getName()) {
            if (ArrayUtil.haveCommonElement(c.core.refCols, c.core.mainCols)) {
                throw Error.error(ErrorCode.X_42527);
            }
        }

        // column defaults
        check =
                c.core.updateAction == SchemaObject.ReferentialAction.SET_DEFAULT
                        || c.core.deleteAction
                        == SchemaObject.ReferentialAction.SET_DEFAULT;

        if (check) {
            for (int i = 0; i < c.core.refCols.length; i++) {
                ColumnSchema col = table.getColumn(c.core.refCols[i]);
                Expression defExpr = col.getDefaultExpression();

                if (defExpr == null) {
                    String columnName = col.getName().statementName;

                    throw Error.error(ErrorCode.X_42521, columnName);
                }
            }
        }

        check = c.core.updateAction == SchemaObject.ReferentialAction.SET_NULL
                || c.core.deleteAction
                == SchemaObject.ReferentialAction.SET_NULL;

        if (check && !session.isProcessingScript) {
            for (int i = 0; i < c.core.refCols.length; i++) {
                ColumnSchema col = table.getColumn(c.core.refCols[i]);

                if (!col.isNullable()) {
                    String columnName = col.getName().statementName;

                    throw Error.error(ErrorCode.X_42520, columnName);
                }
            }
        }

        database.schemaManager.checkSchemaObjectNotExists(c.getName());

        // duplicate name check for a new table
        if (table.getConstraint(c.getName().name) != null) {
            throw Error.error(ErrorCode.X_42504, c.getName().statementName);
        }

        // existing FK check
        if (table.getFKConstraintForColumns(
                c.core.mainTable, c.core.mainCols, c.core.refCols) != null) {
            throw Error.error(ErrorCode.X_42528, c.getName().statementName);
        }

        if (c.core.mainTable.isTemp() != table.isTemp()) {
            throw Error.error(ErrorCode.X_42524, c.getName().statementName);
        }

        if (c.core.mainTable.getUniqueConstraintForColumns(
                c.core.mainCols, c.core.refCols) == null) {
            throw Error.error(ErrorCode.X_42529,
                    c.getMain().getName().statementName);
        }

        // check after UNIQUE check
        c.core.mainTable.checkColumnsMatch(c.core.mainCols, table,
                c.core.refCols);

        boolean[] checkList =
                c.core.mainTable.getColumnCheckList(c.core.mainCols);

//        Grantee   grantee   = table.getOwner();
        Grantee grantee = session.getGrantee();

        grantee.checkReferences(c.core.mainTable, checkList);
    }

    /**
     * Creates a foreign key on an existing table. Foreign keys are enforced by
     * indexes on both the referencing (child) and referenced (main) tables.
     * <p/>
     * <p> Since version 1.7.2, a unique constraint on the referenced columns
     * must exist. The non-unique index on the referencing table is now always
     * created whether or not a PK or unique constraint index on the columns
     * exist. Foriegn keys on temp tables can reference other temp tables with
     * the same rules above. Foreign keys on permanent tables cannot reference
     * temp tables. Duplicate foreign keys are now disallowed.
     *
     * @param c the constraint object
     */
    void addForeignKey(Constraint c) {

        checkModifyTable();
        checkCreateForeignKey(c);

        Constraint uniqueConstraint =
                c.core.mainTable.getUniqueConstraintForColumns(c.core.mainCols,
                        c.core.refCols);
        Index mainIndex = uniqueConstraint.getMainIndex();

        uniqueConstraint.checkReferencedRows(session, table);

        boolean isForward = false;

        if (c.core.mainTable.getSchemaName() == table.getSchemaName()) {
            int offset = database.schemaManager.getTableIndex(table);

            if (offset != -1
                    && offset
                    < database.schemaManager.getTableIndex(
                    c.core.mainTable)) {
                isForward = true;
            }
        } else {
            isForward = true;
        }

        HsqlName indexName = database.nameManager.newAutoName("IDX",
                table.getSchemaName(), table.getName(), SchemaObject.INDEX);
        Index refIndex = table.createIndexStructure(indexName, c.core.refCols,
                null, null, false, true, isForward);
        HsqlName mainName = database.nameManager.newAutoName("REF",
                c.getName().name, table.getSchemaName(), table.getName(),
                SchemaObject.INDEX);

        c.core.uniqueName = uniqueConstraint.getName();
        c.core.mainName = mainName;
        c.core.mainIndex = mainIndex;
        c.core.refTable = table;
        c.core.refName = c.getName();
        c.core.refIndex = refIndex;
        c.isForward = isForward;

        Table tn = table.moveDefinition(session, table.tableType, null, c,
                refIndex, -1, 0, emptySet, emptySet);

        moveData(table, tn, -1, 0);
        database.schemaManager.addSchemaObject(c);
        setNewTableInSchema(tn);

        Table mainTable = database.schemaManager.getTable(session,
                c.core.mainTable.getName().name,
                c.core.mainTable.getSchemaName().name);

        mainTable.addConstraint(new Constraint(mainName, c));
        updateConstraints(tn, emptySet);
        database.schemaManager.recompileDependentObjects(tn);

        table = tn;
    }

    /**
     * Checks if the attributes of the Column argument, c, are compatible with
     * the operation of adding such a Column to the Table argument, table.
     *
     * @param col the Column to add to the Table, t
     */
    void checkAddColumn(ColumnSchema col) {

        checkModifyTable();

        if (table.isText() && !table.isEmpty(session)) {
            throw Error.error(ErrorCode.X_S0521);
        }

        if (table.findColumn(col.getName().name) != -1) {
            throw Error.error(ErrorCode.X_42504);
        }

        if (col.isPrimaryKey() && table.hasPrimaryKey()) {
            throw Error.error(ErrorCode.X_42530);
        }

        if (col.isIdentity() && table.hasIdentityColumn()) {
            throw Error.error(ErrorCode.X_42525);
        }

        if (!table.isEmpty(session) && !col.hasDefault()
                && (!col.isNullable() || col.isPrimaryKey())
                && !col.isIdentity()) {
            throw Error.error(ErrorCode.X_42531);
        }
    }

    void addColumn(ColumnSchema column, int colIndex,
                   HsqlArrayList constraints) {

        Index index = null;
        Table originalTable = table;
        Constraint mainConstraint = null;
        boolean addFK = false;
        boolean addUnique = false;
        boolean addCheck = false;

        checkAddColumn(column);

        Constraint c = (Constraint) constraints.get(0);

        if (c.getConstraintType()
                == SchemaObject.ConstraintTypes.PRIMARY_KEY) {
            if (column.getDataType().isLobType()) {
                throw Error.error(ErrorCode.X_42534);
            }

            c.core.mainCols = new int[]{colIndex};

            database.schemaManager.checkSchemaObjectNotExists(c.getName());

            if (table.hasPrimaryKey()) {
                throw Error.error(ErrorCode.X_42530);
            }

            addUnique = true;
        } else {
            c = null;
        }

        table = table.moveDefinition(session, table.tableType, column, c,
                null, colIndex, 1, emptySet, emptySet);

        for (int i = 1; i < constraints.size(); i++) {
            c = (Constraint) constraints.get(i);

            switch (c.constType) {

                case SchemaObject.ConstraintTypes.UNIQUE: {
                    if (addUnique) {
                        throw Error.error(ErrorCode.X_42522);
                    }

                    if (column.getDataType().isLobType()) {
                        throw Error.error(ErrorCode.X_42534);
                    }

                    addUnique = true;
                    c.core.mainCols = new int[]{colIndex};

                    database.schemaManager.checkSchemaObjectNotExists(
                            c.getName());

                    HsqlName indexName =
                            database.nameManager.newAutoName("IDX",
                                    c.getName().name,
                                    table.getSchemaName(),
                                    table.getName(),
                                    SchemaObject.INDEX);

                    // create an autonamed index
                    index = table.createAndAddIndexStructure(session,
                            indexName, c.getMainColumns(), null, null, true,
                            true, false);
                    c.core.mainTable = table;
                    c.core.mainIndex = index;

                    table.addConstraint(c);

                    break;
                }
                case SchemaObject.ConstraintTypes.FOREIGN_KEY: {
                    if (addFK) {
                        throw Error.error(ErrorCode.X_42528);
                    }

                    addFK = true;
                    c.core.refCols = new int[]{colIndex};

                    boolean isSelf = originalTable == c.core.mainTable;

                    if (isSelf) {
                        c.core.mainTable = table;
                    }

                    c.setColumnsIndexes(table);
                    checkCreateForeignKey(c);

                    Constraint uniqueConstraint =
                            c.core.mainTable.getUniqueConstraintForColumns(
                                    c.core.mainCols, c.core.refCols);
                    boolean isForward = c.core.mainTable.getSchemaName()
                            != table.getSchemaName();
                    int offset =
                            database.schemaManager.getTableIndex(originalTable);

                    if (!isSelf
                            && offset
                            < database.schemaManager.getTableIndex(
                            c.core.mainTable)) {
                        isForward = true;
                    }

                    HsqlName indexName =
                            database.nameManager.newAutoName("IDX",
                                    c.getName().name,
                                    table.getSchemaName(),
                                    table.getName(),
                                    SchemaObject.INDEX);

                    index = table.createAndAddIndexStructure(session,
                            indexName, c.getRefColumns(), null, null, false,
                            true, isForward);
                    c.core.uniqueName = uniqueConstraint.getName();
                    c.core.mainName = database.nameManager.newAutoName("REF",
                            c.core.refName.name, table.getSchemaName(),
                            table.getName(), SchemaObject.INDEX);
                    c.core.mainIndex = uniqueConstraint.getMainIndex();
                    c.core.refIndex = index;
                    c.isForward = isForward;

                    table.addConstraint(c);

                    mainConstraint = new Constraint(c.core.mainName, c);

                    break;
                }
                case SchemaObject.ConstraintTypes.CHECK:
                    if (addCheck) {
                        throw Error.error(ErrorCode.X_42528);
                    }

                    addCheck = true;

                    c.prepareCheckConstraint(session, table, false);
                    table.addConstraint(c);

                    if (c.isNotNull()) {
                        column.setNullable(false);
                        table.setColumnTypeVars(colIndex);
                    }
                    break;
            }
        }

        column.compile(session, table);
        moveData(originalTable, table, colIndex, 1);

        if (mainConstraint != null) {
            mainConstraint.getMain().addConstraint(mainConstraint);
        }

        registerConstraintNames(constraints);
        setNewTableInSchema(table);
        updateConstraints(table, emptySet);
        database.schemaManager.addSchemaObject(column);
        database.schemaManager.recompileDependentObjects(table);
        table.compile(session, null);
    }

    void updateConstraints(OrderedHashSet tableSet,
                           OrderedHashSet dropConstraints) {

        for (int i = 0; i < tableSet.size(); i++) {
            Table t = (Table) tableSet.get(i);

            updateConstraints(t, dropConstraints);
        }
    }

    void updateConstraints(Table t, OrderedHashSet dropConstraints) {

        for (int i = t.constraintList.length - 1; i >= 0; i--) {
            Constraint c = t.constraintList[i];

            if (dropConstraints.contains(c.getName())) {
                t.removeConstraint(i);

                continue;
            }

            if (c.getConstraintType()
                    == SchemaObject.ConstraintTypes.FOREIGN_KEY) {
                Table mainT = database.schemaManager.getUserTable(session,
                        c.core.mainTable.getName());
                Constraint mainC = mainT.getConstraint(c.getMainName().name);

                mainC.core = c.core;
            } else if (c.getConstraintType()
                    == SchemaObject.ConstraintTypes.MAIN) {
                Table refT = database.schemaManager.getUserTable(session,
                        c.core.refTable.getName());
                Constraint refC = refT.getConstraint(c.getRefName().name);

                refC.core = c.core;
            }
        }
    }

    OrderedHashSet makeNewTables(OrderedHashSet tableSet,
                                 OrderedHashSet dropConstraintSet,
                                 OrderedHashSet dropIndexSet) {

        OrderedHashSet newSet = new OrderedHashSet();

        for (int i = 0; i < tableSet.size(); i++) {
            Table t = (Table) tableSet.get(i);
            TableWorks tw = new TableWorks(session, t);

            tw.makeNewTable(dropConstraintSet, dropIndexSet);
            newSet.add(tw.getTable());
        }

        return newSet;
    }

    /**
     * Drops constriants and their indexes in table. Uses set of names.
     */
    void makeNewTable(OrderedHashSet dropConstraintSet,
                      OrderedHashSet dropIndexSet) {

        Table tn = table.moveDefinition(session, table.tableType, null, null,
                null, -1, 0, dropConstraintSet,
                dropIndexSet);

        if (tn.indexList.length == table.indexList.length) {
            database.persistentStoreCollection.releaseStore(tn);

            return;
        }

        moveData(table, tn, -1, 0);

        table = tn;
    }

    /**
     * Because of the way indexes and column data are held in memory and on
     * disk, it is necessary to recreate the table when an index is added to a
     * non-empty cached table.
     * <p/>
     * <p> With empty tables, Index objects are simply added
     * <p/>
     * <p> With MEOMRY and TEXT tables, a new index is built up and nodes for
     * earch row are interlinked (fredt@users)
     *
     * @param col    int[]
     * @param name   HsqlName
     * @param unique boolean
     * @return new index
     */
    Index addIndex(int[] col, HsqlName name, boolean unique) {

        Index newindex;

        checkModifyTable();

        if (table.isEmpty(session) || table.isIndexingMutable()) {
            newindex = table.createIndex(session, name, col, null, null,
                    unique, false, false);
        } else {
            newindex = table.createIndexStructure(name, col, null, null,
                    unique, false, false);

            Table tn = table.moveDefinition(session, table.tableType, null,
                    null, newindex, -1, 0, emptySet,
                    emptySet);

            // for all sessions move the data
            moveData(table, tn, -1, 0);

            table = tn;

            setNewTableInSchema(table);
            updateConstraints(table, emptySet);
        }

        database.schemaManager.addSchemaObject(newindex);
        database.schemaManager.recompileDependentObjects(table);

        return newindex;
    }

    void addPrimaryKey(Constraint constraint, HsqlName name) {

        checkModifyTable();

        if (table.hasPrimaryKey()) {
            throw Error.error(ErrorCode.X_42532);
        }

        database.schemaManager.checkSchemaObjectNotExists(name);

        Table tn = table.moveDefinition(session, table.tableType, null,
                constraint, null, -1, 0, emptySet,
                emptySet);

        moveData(table, tn, -1, 0);

        table = tn;

        database.schemaManager.addSchemaObject(constraint);
        setNewTableInSchema(table);
        updateConstraints(table, emptySet);
        database.schemaManager.recompileDependentObjects(table);
    }

    /**
     * A unique constraint relies on a unique indexe on the table. It can cover
     * a single column or multiple columns.
     * <p/>
     * <p> All unique constraint names are generated by Database.java as unique
     * within the database. Duplicate constraints (more than one unique
     * constriant on the same set of columns) are not allowed. (fredt@users)
     *
     * @param cols int[]
     * @param name HsqlName
     */
    void addUniqueConstraint(int[] cols, HsqlName name) {

        checkModifyTable();
        database.schemaManager.checkSchemaObjectNotExists(name);

        if (table.getUniqueConstraintForColumns(cols) != null) {
            throw Error.error(ErrorCode.X_42522);
        }

        // create an autonamed index
        HsqlName indexname = database.nameManager.newAutoName("IDX",
                name.name, table.getSchemaName(), table.getName(),
                SchemaObject.INDEX);
        Index index = table.createIndexStructure(indexname, cols, null, null,
                true, true, false);
        Constraint constraint =
                new Constraint(name, table, index,
                        SchemaObject.ConstraintTypes.UNIQUE);
        Table tn = table.moveDefinition(session, table.tableType, null,
                constraint, index, -1, 0, emptySet,
                emptySet);

        moveData(table, tn, -1, 0);

        table = tn;

        database.schemaManager.addSchemaObject(constraint);
        setNewTableInSchema(table);
        updateConstraints(table, emptySet);
        database.schemaManager.recompileDependentObjects(table);
    }

    void addCheckConstraint(Constraint c) {

        database.schemaManager.checkSchemaObjectNotExists(c.getName());
        c.prepareCheckConstraint(session, table, true);
        table.addConstraint(c);

        if (c.isNotNull()) {
            ColumnSchema column = table.getColumn(c.notNullColumnIndex);

            column.setNullable(false);
            table.setColumnTypeVars(c.notNullColumnIndex);
        }

        database.schemaManager.addSchemaObject(c);
    }

    /**
     * Because of the way indexes and column data are held in memory and on
     * disk, it is necessary to recreate the table when an index is added to or
     * removed from a non-empty table.
     * <p/>
     * <p> Originally, this method would break existing foreign keys as the
     * table order in the DB was changed. The new table is now linked in place
     * of the old table (fredt@users)
     *
     * @param indexName String
     */
    void dropIndex(String indexName) {

        Index index;

        checkModifyTable();

        index = table.getIndex(indexName);

        if (table.isIndexingMutable()) {
            table.dropIndex(index.getPosition());
        } else {
            OrderedHashSet indexSet = new OrderedHashSet();

            indexSet.add(table.getIndex(indexName).getName());

            Table tn = table.moveDefinition(session, table.tableType, null,
                    null, null, -1, 0, emptySet,
                    indexSet);

            moveData(table, tn, -1, 0);
            updateConstraints(tn, emptySet);
            setNewTableInSchema(tn);

            table = tn;
        }

        if (!index.isConstraint()) {
            database.schemaManager.removeSchemaObject(index.getName());
        }

        database.schemaManager.recompileDependentObjects(table);
    }

    void dropColumn(int colIndex, boolean cascade) {

        OrderedHashSet constraintNameSet = new OrderedHashSet();
        OrderedHashSet dependentConstraints =
                table.getDependentConstraints(colIndex);
        OrderedHashSet cascadingConstraints =
                table.getContainingConstraints(colIndex);
        OrderedHashSet indexNameSet = table.getContainingIndexNames(colIndex);
        ColumnSchema column = table.getColumn(colIndex);
        HsqlName columnName = column.getName();
        OrderedHashSet referencingObjects =
                database.schemaManager.getReferencingObjectNames(table.getName(),
                        columnName);

        checkModifyTable();

        if (!cascade) {
            if (!cascadingConstraints.isEmpty()) {
                Constraint c = (Constraint) cascadingConstraints.get(0);
                HsqlName name = c.getName();

                throw Error.error(ErrorCode.X_42536,
                        name.getSchemaQualifiedStatementName());
            }

            if (!referencingObjects.isEmpty()) {
                mainLoop:
                for (int i = 0; i < referencingObjects.size(); i++) {
                    HsqlName name = (HsqlName) referencingObjects.get(i);

                    if (name == columnName) {
                        continue;
                    }

                    for (int j = 0; j < dependentConstraints.size(); j++) {
                        Constraint c =
                                (Constraint) dependentConstraints.get(j);
                        ;

                        if (c.getName() == name) {
                            continue mainLoop;
                        }
                    }

                    throw Error.error(ErrorCode.X_42536,
                            name.getSchemaQualifiedStatementName());
                }
            }
        }

        dependentConstraints.addAll(cascadingConstraints);
        cascadingConstraints.clear();

        OrderedHashSet tableSet = new OrderedHashSet();

        for (int i = 0; i < dependentConstraints.size(); i++) {
            Constraint c = (Constraint) dependentConstraints.get(i);

            if (c.constType == SchemaObject.ConstraintTypes.FOREIGN_KEY) {
                tableSet.add(c.getMain());
                constraintNameSet.add(c.getMainName());
                constraintNameSet.add(c.getRefName());
                indexNameSet.add(c.getRefIndex().getName());
            }

            if (c.constType == SchemaObject.ConstraintTypes.MAIN) {
                tableSet.add(c.getRef());
                constraintNameSet.add(c.getMainName());
                constraintNameSet.add(c.getRefName());
                indexNameSet.add(c.getRefIndex().getName());
            }

            constraintNameSet.add(c.getName());
        }

        tableSet = makeNewTables(tableSet, constraintNameSet, indexNameSet);

        Table tn = table.moveDefinition(session, table.tableType, null, null,
                null, colIndex, -1, constraintNameSet,
                indexNameSet);

        moveData(table, tn, colIndex, -1);
        database.schemaManager.removeSchemaObjects(referencingObjects);
        database.schemaManager.removeSchemaObjects(constraintNameSet);
        database.schemaManager.removeSchemaObject(columnName);
        setNewTableInSchema(tn);
        setNewTablesInSchema(tableSet);
        updateConstraints(tn, emptySet);
        updateConstraints(tableSet, constraintNameSet);
        database.schemaManager.recompileDependentObjects(tableSet);
        database.schemaManager.recompileDependentObjects(tn);
        tn.compile(session, null);

        if (column.getDataType().isLobType()) {
            RowIterator it = table.rowIterator(session);

            while (it.hasNext()) {
                Row row = it.getNextRow();
                Object[] data = row.getData();

                if (data[colIndex] != null) {
                    session.sessionData.adjustLobUsageCount(data[colIndex],
                            -1);
                }
            }
        }

        table = tn;
    }

    void registerConstraintNames(HsqlArrayList constraints) {

        for (int i = 0; i < constraints.size(); i++) {
            Constraint c = (Constraint) constraints.get(i);

            switch (c.constType) {

                case SchemaObject.ConstraintTypes.PRIMARY_KEY:
                case SchemaObject.ConstraintTypes.UNIQUE:
                case SchemaObject.ConstraintTypes.CHECK:
                    database.schemaManager.addSchemaObject(c);
            }
        }
    }

    void dropConstraint(String name, boolean cascade) {

        Constraint constraint = table.getConstraint(name);

        if (constraint == null) {
            throw Error.error(ErrorCode.X_42501, name);
        }

        switch (constraint.getConstraintType()) {

            case SchemaObject.ConstraintTypes.MAIN:
                throw Error.error(ErrorCode.X_28502);
            case SchemaObject.ConstraintTypes.PRIMARY_KEY:
            case SchemaObject.ConstraintTypes.UNIQUE: {
                checkModifyTable();

                OrderedHashSet dependentConstraints =
                        table.getDependentConstraints(constraint);

                // throw if unique constraint is referenced by foreign key
                if (!cascade && !dependentConstraints.isEmpty()) {
                    Constraint c = (Constraint) dependentConstraints.get(0);

                    throw Error.error(
                            ErrorCode.X_42533,
                            c.getName().getSchemaQualifiedStatementName());
                }

                OrderedHashSet tableSet = new OrderedHashSet();
                OrderedHashSet constraintNameSet = new OrderedHashSet();
                OrderedHashSet indexNameSet = new OrderedHashSet();

                for (int i = 0; i < dependentConstraints.size(); i++) {
                    Constraint c = (Constraint) dependentConstraints.get(i);
                    Table t = c.getMain();

                    if (t != table) {
                        tableSet.add(t);
                    }

                    t = c.getRef();

                    if (t != table) {
                        tableSet.add(t);
                    }

                    constraintNameSet.add(c.getMainName());
                    constraintNameSet.add(c.getRefName());
                    indexNameSet.add(c.getRefIndex().getName());
                }

                constraintNameSet.add(constraint.getName());

                if (constraint.getConstraintType()
                        == SchemaObject.ConstraintTypes.UNIQUE) {
                    indexNameSet.add(constraint.getMainIndex().getName());
                }

                Table tn = table.moveDefinition(session, table.tableType,
                        null, null, null, -1, 0,
                        constraintNameSet,
                        indexNameSet);

                moveData(table, tn, -1, 0);

                tableSet = makeNewTables(tableSet, constraintNameSet,
                        indexNameSet);

                if (constraint.getConstraintType()
                        == SchemaObject.ConstraintTypes.PRIMARY_KEY) {
                    int[] cols = constraint.getMainColumns();

                    for (int i = 0; i < cols.length; i++) {

                        // todo - check if table arrays relect the not-null correctly
                        tn.getColumn(cols[i]).setPrimaryKey(false);
                        tn.setColumnTypeVars(cols[i]);
                    }
                }

                //
                database.schemaManager.removeSchemaObjects(constraintNameSet);
                setNewTableInSchema(tn);
                setNewTablesInSchema(tableSet);
                updateConstraints(tn, emptySet);
                updateConstraints(tableSet, constraintNameSet);
                database.schemaManager.recompileDependentObjects(tableSet);
                database.schemaManager.recompileDependentObjects(tn);

                table = tn;

                // handle cascadingConstraints and cascadingTables
                break;
            }
            case SchemaObject.ConstraintTypes.FOREIGN_KEY: {
                checkModifyTable();

                OrderedHashSet constraints = new OrderedHashSet();
                Table mainTable = constraint.getMain();
                HsqlName mainName = constraint.getMainName();

                constraints.add(mainName);
                constraints.add(constraint.getRefName());

                OrderedHashSet indexes = new OrderedHashSet();

                indexes.add(constraint.getRefIndex().getName());

                Table tn = table.moveDefinition(session, table.tableType,
                        null, null, null, -1, 0,
                        constraints, indexes);

                moveData(table, tn, -1, 0);

                //
                database.schemaManager.removeSchemaObject(
                        constraint.getName());
                setNewTableInSchema(tn);

                // if constraint references same table, nothing changes
                mainTable.removeConstraint(mainName.name);
                updateConstraints(tn, emptySet);
                database.schemaManager.recompileDependentObjects(table);

                table = tn;

                break;
            }
            case SchemaObject.ConstraintTypes.CHECK:
                database.schemaManager.removeSchemaObject(
                        constraint.getName());

                if (constraint.isNotNull()) {
                    ColumnSchema column =
                            table.getColumn(constraint.notNullColumnIndex);

                    column.setNullable(false);
                    table.setColumnTypeVars(constraint.notNullColumnIndex);
                }
                break;
        }
    }

    /**
     * Allows changing the type or addition of an IDENTITY sequence.
     *
     * @param oldCol Column
     * @param newCol Column
     */
    void retypeColumn(ColumnSchema oldCol, ColumnSchema newCol) {

        boolean allowed = true;
        int oldType = oldCol.getDataType().typeCode;
        int newType = newCol.getDataType().typeCode;

        checkModifyTable();

        if (!table.isEmpty(session) && oldType != newType) {
            allowed =
                    newCol.getDataType().canConvertFrom(oldCol.getDataType());

            switch (oldType) {

                case Types.OTHER:
                case Types.JAVA_OBJECT:
                    allowed = false;
                    break;
            }
        }

        if (!allowed) {
            throw Error.error(ErrorCode.X_42561);
        }

        int colIndex = table.getColumnIndex(oldCol.getName().name);

        // if there is a multi-column PK, do not change the PK attributes
        if (newCol.isIdentity() && table.hasIdentityColumn()
                && table.identityColumn != colIndex) {
            throw Error.error(ErrorCode.X_42525);
        }

        if (table.getPrimaryKey().length > 1) {
            newCol.setPrimaryKey(oldCol.isPrimaryKey());

            if (ArrayUtil.find(table.getPrimaryKey(), colIndex) != -1) {
            }
        } else if (table.hasPrimaryKey()) {
            if (oldCol.isPrimaryKey()) {
                newCol.setPrimaryKey(true);
            } else if (newCol.isPrimaryKey()) {
                throw Error.error(ErrorCode.X_42532);
            }
        } else if (newCol.isPrimaryKey()) {
            throw Error.error(ErrorCode.X_42530);
        }

        // apply and return if only metadata change is required
        boolean meta = newType == oldType;

        meta &= oldCol.isNullable() == newCol.isNullable();
        meta &= oldCol.getDataType().scale == newCol.getDataType().scale;
        meta &= (oldCol.isIdentity() == newCol.isIdentity());
        meta &=
                (oldCol.getDataType().precision == newCol.getDataType().precision
                        || (oldCol.getDataType().precision
                        < newCol.getDataType().precision && (oldType
                        == Types.SQL_VARCHAR || oldType == Types.SQL_VARBINARY)));

        if (meta) {

            // size of some types may be increased with this command
            // default expressions can change
            oldCol.setType(newCol);
            oldCol.setDefaultExpression(newCol.getDefaultExpression());

            if (newCol.isIdentity()) {
                oldCol.setIdentity(newCol.getIdentitySequence());
            }

            table.setColumnTypeVars(colIndex);
            table.resetDefaultsFlag();

            return;
        }

        database.schemaManager.checkColumnIsReferenced(table.getName(),
                table.getColumn(colIndex).getName());
        table.checkColumnInCheckConstraint(colIndex);
        table.checkColumnInFKConstraint(colIndex);
        checkConvertColDataType(oldCol, newCol);
        retypeColumn(newCol, colIndex);
    }

    /**
     * @param oldCol Column
     * @param newCol Column
     */
    void checkConvertColDataType(ColumnSchema oldCol, ColumnSchema newCol) {

        int colIndex = table.getColumnIndex(oldCol.getName().name);
        RowIterator it = table.rowIterator(session);

        while (it.hasNext()) {
            Row row = it.getNextRow();
            Object o = row.getData()[colIndex];

            newCol.getDataType().convertToType(session, o,
                    oldCol.getDataType());
        }
    }

    /**
     * @param column   Column
     * @param colIndex int
     */
    private void retypeColumn(ColumnSchema column, int colIndex) {

        Table tn = table.moveDefinition(session, table.tableType, column,
                null, null, colIndex, 0, emptySet,
                emptySet);

        moveData(table, tn, colIndex, 0);
        updateConstraints(tn, emptySet);
        setNewTableInSchema(tn);
        database.schemaManager.recompileDependentObjects(table);

        table = tn;
    }

    /**
     * performs the work for changing the nullability of a column
     *
     * @param column   Column
     * @param nullable boolean
     */
    void setColNullability(ColumnSchema column, boolean nullable) {

        Constraint c = null;
        int colIndex = table.getColumnIndex(column.getName().name);

        if (column.isNullable() == nullable) {
            return;
        }

        if (nullable) {
            if (column.isPrimaryKey()) {
                throw Error.error(ErrorCode.X_42526);
            }

            table.checkColumnInFKConstraint(
                    colIndex, SchemaObject.ReferentialAction.SET_NULL);
            removeColumnNotNullConstraints(colIndex);
        } else {
            HsqlName constName = database.nameManager.newAutoName("CT",
                    table.getSchemaName(), table.getName(),
                    SchemaObject.CONSTRAINT);

            c = new Constraint(constName, null,
                    SchemaObject.ConstraintTypes.CHECK);
            c.check = new ExpressionLogical(column);

            c.prepareCheckConstraint(session, table, true);
            column.setNullable(false);
            table.addConstraint(c);
            table.setColumnTypeVars(colIndex);
            database.schemaManager.addSchemaObject(c);
        }
    }

    /**
     * performs the work for changing the default value of a column
     *
     * @param colIndex int
     * @param def      Expression
     */
    void setColDefaultExpression(int colIndex, Expression def) {

        if (def == null) {
            table.checkColumnInFKConstraint(
                    colIndex, SchemaObject.ReferentialAction.SET_DEFAULT);
        }

        table.setDefaultExpression(colIndex, def);
    }

    /**
     * Changes the type of a table
     *
     * @param session Session
     * @param newType int
     * @return boolean
     */
    public boolean setTableType(Session session, int newType) {

        int currentType = table.getTableType();

        if (currentType == newType) {
            return false;
        }

        switch (newType) {

            case TableBase.CACHED_TABLE:
                break;

            case TableBase.MEMORY_TABLE:
                break;

            default:
                return false;
        }

        Table tn;

        try {
            tn = table.moveDefinition(session, newType, null, null, null, -1,
                    0, emptySet, emptySet);

            moveData(table, tn, -1, 0);
            updateConstraints(tn, emptySet);
        } catch (HsqlException e) {
            return false;
        }

        setNewTableInSchema(tn);

        table = tn;

        database.schemaManager.recompileDependentObjects(table);

        return true;
    }

    void setNewTablesInSchema(OrderedHashSet tableSet) {

        for (int i = 0; i < tableSet.size(); i++) {
            Table t = (Table) tableSet.get(i);

            setNewTableInSchema(t);
        }
    }

    void setNewTableInSchema(Table newTable) {

        int i = database.schemaManager.getTableIndex(newTable);

        if (i != -1) {
            database.schemaManager.setTable(i, newTable);
        }
    }

    void removeColumnNotNullConstraints(int colIndex) {

        for (int i = table.constraintList.length - 1; i >= 0; i--) {
            Constraint c = table.constraintList[i];

            if (c.isNotNull()) {
                if (c.notNullColumnIndex == colIndex) {
                    database.schemaManager.removeSchemaObject(c.getName());
                }
            }
        }

        ColumnSchema column = table.getColumn(colIndex);

        column.setNullable(true);
        table.setColumnTypeVars(colIndex);
    }

    private void checkModifyTable() {

        if (session.getUser().isSystem()) {
            return;
        }

        if (session.isProcessingScript) {
            return;
        }

        if (database.isFilesReadOnly() || table.isReadOnly()) {
            throw Error.error(ErrorCode.DATA_IS_READONLY);
        }

        if (table.isText() && table.isConnected()) {
            throw Error.error(ErrorCode.X_S0521);
        }
    }

    void moveData(Table oldTable, Table newTable, int colIndex, int adjust) {

        if (oldTable.getTableType() == Table.TEMP_TABLE) {
            Session sessions[] = database.sessionManager.getAllSessions();

            for (int i = 0; i < sessions.length; i++) {
                sessions[i].sessionData.persistentStoreCollection.moveData(
                        oldTable, newTable, colIndex, adjust);
            }
        } else {
            PersistentStore oldStore =
                    database.persistentStoreCollection.getStore(oldTable);
            PersistentStore newStore =
                    database.persistentStoreCollection.getStore(newTable);

            newStore.moveData(session, oldStore, colIndex, adjust);
            database.persistentStoreCollection.releaseStore(oldTable);
        }
    }
}
