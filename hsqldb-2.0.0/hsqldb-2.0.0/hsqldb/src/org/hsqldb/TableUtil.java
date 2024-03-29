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
import org.hsqldb.lib.ArrayUtil;
import org.hsqldb.types.Type;

/*
 * Utility functions to set up special tables.
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.9.0
 */
public class TableUtil {

    static Table newTable(Database database, int type,
                          HsqlName tableHsqlName) {

        switch (type) {

            case TableBase.TEMP_TEXT_TABLE:
            case TableBase.TEXT_TABLE: {
                return new TextTable(database, tableHsqlName, type);
            }
            default: {
                return new Table(database, tableHsqlName, type);
            }
        }
    }

    static Table newLookupTable(Database database) {

        TableDerived table;
        HsqlName name = database.nameManager.getSubqueryTableName();

        table = new TableDerived(database, name, TableBase.SYSTEM_SUBQUERY,
                null, null);

        ColumnSchema column =
                new ColumnSchema(HsqlNameManager.getAutoColumnName(0),
                        Type.SQL_INTEGER, false, true, null);

        table.addColumn(column);
        table.createPrimaryKeyConstraint(table.getName(), new int[]{0},
                true);

        return table;
    }

    static void setTableIndexesForSubquery(Table table,
                                           boolean fullIndex,
                                           boolean uniqueRows) {

        int[] cols = null;

        if (fullIndex) {
            cols = new int[table.getColumnCount()];

            ArrayUtil.fillSequence(cols);
        }

        table.createPrimaryKey(null, uniqueRows ? cols
                : null, false);

        if (uniqueRows) {
            table.fullIndex = table.getPrimaryIndex();
        } else if (fullIndex) {
            table.fullIndex = table.createIndexForColumns(null, cols);
        }
    }

    public static void addAutoColumns(Table table, Type[] colTypes) {

        for (int i = 0; i < colTypes.length; i++) {
            ColumnSchema column =
                    new ColumnSchema(HsqlNameManager.getAutoColumnName(i),
                            colTypes[i], true, false, null);

            table.addColumnNoCheck(column);
        }
    }

    public static void setColumnsInSchemaTable(Table table,
                                               HsqlName[] columnNames, Type[] columnTypes) {

        for (int i = 0; i < columnNames.length; i++) {
            HsqlName columnName = columnNames[i];

            columnName = table.database.nameManager.newColumnSchemaHsqlName(
                    table.getName(), columnName);

            ColumnSchema column = new ColumnSchema(columnName, columnTypes[i],
                    true, false, null);

            table.addColumn(column);
        }

        table.setColumnStructures();
    }
}
