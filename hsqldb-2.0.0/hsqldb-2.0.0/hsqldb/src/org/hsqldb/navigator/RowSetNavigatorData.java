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


package org.hsqldb.navigator;

import org.hsqldb.*;
import org.hsqldb.error.Error;
import org.hsqldb.error.ErrorCode;
import org.hsqldb.index.Index;
import org.hsqldb.lib.ArraySort;
import org.hsqldb.lib.ArrayUtil;
import org.hsqldb.lib.LongKeyHashMap;
import org.hsqldb.result.ResultMetaData;
import org.hsqldb.rowio.RowInputInterface;
import org.hsqldb.rowio.RowOutputInterface;

import java.io.IOException;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Implementation of RowSetNavigator for result sets.
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.9.0
 */
public class RowSetNavigatorData extends RowSetNavigator
        implements Comparator {

    public static final Object[][] emptyTable = new Object[0][];

    //
    int currentOffset;
    int baseBlockSize;

    //
    Object[][] table = emptyTable;

    //
    final Session session;
    QueryExpression queryExpression;
    int visibleColumnCount;
    boolean isSimpleAggregate;
    Object[] simpleAggregateData;

    //
    //
    private Index mainIndex;

    //
    TreeMap rowMap;
    LongKeyHashMap idMap;

    RowSetNavigatorData(Session session) {
        this.session = session;
    }

    public RowSetNavigatorData(Session session, QuerySpecification select) {

        this.session = session;
        this.queryExpression = select;
        this.rangePosition = select.resultRangePosition;
        visibleColumnCount = select.getColumnCount();
        isSimpleAggregate = select.isAggregated && !select.isGrouped;

        if (select.isGrouped) {
            mainIndex = select.groupIndex;
            rowMap = new TreeMap(this);
        }

        if (select.idIndex != null) {
            idMap = new LongKeyHashMap();
        }
    }

    public RowSetNavigatorData(Session session,
                               QueryExpression queryExpression) {
        this.session = session;
        this.queryExpression = queryExpression;
        visibleColumnCount = queryExpression.getColumnCount();
    }

    public void sortFull() {

        mainIndex = queryExpression.fullIndex;

        ArraySort.sort(table, 0, size, this);
        reset();
    }

    public void sortOrder() {

        if (queryExpression.orderIndex != null) {
            mainIndex = queryExpression.orderIndex;

            ArraySort.sort(table, 0, size, this);
        }

        reset();
    }

    public void sortUnion(SortAndSlice sortAndSlice) {

        if (sortAndSlice.index != null) {
            mainIndex = sortAndSlice.index;

            ArraySort.sort(table, 0, size, this);
            reset();
        }
    }

    public void add(Object[] data) {

        ensureCapacity();

        table[size] = data;

        size++;

        if (rowMap != null) {
            rowMap.put(data, data);
        }

        if (idMap != null) {
            Long id = (Long) data[visibleColumnCount];

            idMap.put(id.longValue(), data);
        }
    }

    public boolean addRow(Row row) {
        throw Error.runtimeError(ErrorCode.U_S0500, "RowSetNavigatorClient");
    }

    public void update(Object[] oldData, Object[] newData) {
        // noop
    }

    void addAdjusted(Object[] data, int[] columnMap) {

        data = projectData(data, columnMap);

        add(data);
    }

    void insertAdjusted(Object[] data, int[] columnMap) {
        projectData(data, columnMap);
        insert(data);
    }

    Object[] projectData(Object[] data, int[] columnMap) {

        if (columnMap == null) {
            data = (Object[]) ArrayUtil.resizeArrayIfDifferent(data,
                    visibleColumnCount);
        } else {
            Object[] newData = new Object[visibleColumnCount];

            ArrayUtil.projectRow(data, columnMap, newData);

            data = newData;
        }

        return data;
    }

    /**
     * for union only
     */
    void insert(Object[] data) {

        ensureCapacity();
        System.arraycopy(table, currentPos, table, currentPos + 1,
                size - currentPos);

        table[currentPos] = data;

        size++;
    }

    public void clear() {
        this.table = emptyTable;
        this.size = table.length;
        reset();
    }

    public boolean absolute(int position) {
        return super.absolute(position);
    }

    public Object[] getCurrent() {

        if (currentPos < 0 || currentPos >= size) {
            return null;
        }

        if (currentPos == currentOffset + table.length) {
            getBlock(currentOffset + table.length);
        }

        return table[currentPos - currentOffset];
    }

    public Row getCurrentRow() {
        throw Error.runtimeError(ErrorCode.U_S0500, "RowSetNavigatorClient");
    }

    public Object[] getNextRowData() {
        return next() ? getCurrent()
                : null;
    }

    public boolean next() {
        return super.next();
    }

    public void remove() {

        System.arraycopy(table, currentPos + 1, table, currentPos,
                size - currentPos - 1);

        table[size - 1] = null;

        currentPos--;
        size--;
    }

    public void reset() {
        super.reset();
    }

    public void close() {
        super.close();
    }

    public boolean isMemory() {
        return true;
    }

    public void read(RowInputInterface in,
                     ResultMetaData meta) throws IOException {
    }

    public void write(RowOutputInterface out,
                      ResultMetaData meta) throws IOException {

        reset();
        out.writeLong(id);
        out.writeInt(size);
        out.writeInt(0);    // offset
        out.writeInt(size);

        while (hasNext()) {
            Object[] data = getNext();

            out.writeData(meta.getExtendedColumnCount(), meta.columnTypes,
                    data, null, null);
        }

        reset();
    }

    public Object[] getData(long rowId) {
        return (Object[]) idMap.get(rowId);
    }

    public void copy(RowSetNavigatorData other, int[] rightColumnIndexes) {

        while (other.hasNext()) {
            Object[] currentData = other.getNext();

            addAdjusted(currentData, rightColumnIndexes);
        }

        other.close();
    }

    public void union(RowSetNavigatorData other) {

        Object[] currentData;

        removeDuplicates();
        other.removeDuplicates();

        while (other.hasNext()) {
            currentData = other.getNext();

            int position = ArraySort.searchFirst(table, 0, size, currentData,
                    this);

            if (position < 0) {
                position = -position - 1;
                currentPos = position;

                insert(currentData);
            }
        }

        other.close();
        reset();
    }

    public void unionAll(RowSetNavigatorData other) {

        other.reset();

        while (other.hasNext()) {
            Object[] currentData = other.getNext();

            add(currentData);
        }

        other.close();
        reset();
    }

    public void intersect(RowSetNavigatorData other) {

        removeDuplicates();
        other.sortFull();

        while (hasNext()) {
            Object[] currentData = getNext();
            boolean hasRow = other.containsRow(currentData);

            if (!hasRow) {
                remove();
            }
        }

        other.close();
        reset();
    }

    public void intersectAll(RowSetNavigatorData other) {

        Object[] compareData = null;
        RowIterator it;
        Object[] otherData = null;

        sortFull();
        other.sortFull();

        it = queryExpression.fullIndex.emptyIterator();

        while (hasNext()) {
            Object[] currentData = getNext();
            boolean newGroup =
                    compareData == null
                            || queryExpression.fullIndex.compareRowNonUnique(
                            session, currentData, compareData,
                            visibleColumnCount) != 0;

            if (newGroup) {
                compareData = currentData;
                it = other.findFirstRow(currentData);
            }

            otherData = it.getNext();

            if (otherData != null
                    && queryExpression.fullIndex.compareRowNonUnique(
                    session, currentData, otherData,
                    visibleColumnCount) == 0) {
                continue;
            }

            remove();
        }

        other.close();
        reset();
    }

    public void except(RowSetNavigatorData other) {

        removeDuplicates();
        other.sortFull();

        while (hasNext()) {
            Object[] currentData = getNext();
            boolean hasRow = other.containsRow(currentData);

            if (hasRow) {
                remove();
            }
        }

        other.close();
        reset();
    }

    public void exceptAll(RowSetNavigatorData other) {

        Object[] compareData = null;
        RowIterator it;
        Object[] otherData = null;

        sortFull();
        other.sortFull();

        it = queryExpression.fullIndex.emptyIterator();

        while (hasNext()) {
            Object[] currentData = getNext();
            boolean newGroup =
                    compareData == null
                            || queryExpression.fullIndex.compareRowNonUnique(
                            session, currentData, compareData,
                            queryExpression.fullIndex.getColumnCount()) != 0;

            if (newGroup) {
                compareData = currentData;
                it = other.findFirstRow(currentData);
            }

            otherData = it.getNext();

            if (otherData != null
                    && queryExpression.fullIndex.compareRowNonUnique(
                    session, currentData, otherData,
                    queryExpression.fullIndex.getColumnCount()) == 0) {
                remove();
            }
        }

        other.close();
        reset();
    }

    public boolean hasUniqueNotNullRows() {

        sortFull();
        reset();

        Object[] lastRowData = null;

        while (hasNext()) {
            Object[] currentData = getNext();

            if (hasNull(currentData)) {
                continue;
            }

            if (lastRowData != null
                    && queryExpression.fullIndex.compareRow(
                    session, lastRowData, currentData) == 0) {
                return false;
            } else {
                lastRowData = currentData;
            }
        }

        return true;
    }

    public void removeDuplicates() {

        sortFull();
        reset();

        Object[] lastRowData = null;

        while (hasNext()) {
            Object[] currentData = getNext();

            if (lastRowData != null
                    && queryExpression.fullIndex.compareRow(
                    session, lastRowData, currentData) == 0) {
                remove();
            } else {
                lastRowData = currentData;
            }
        }

        reset();
    }

    public void trim(int limitstart, int limitcount) {

        if (size == 0) {
            return;
        }

        if (limitstart >= size) {
            clear();

            return;
        }

        if (limitstart != 0) {
            reset();

            for (int i = 0; i < limitstart; i++) {
                next();
                remove();
            }
        }

        if (limitcount == 0 || limitcount >= size) {
            return;
        }

        reset();

        for (int i = 0; i < limitcount; i++) {
            next();
        }

        while (hasNext()) {
            next();
            remove();
        }

        reset();
    }

    boolean hasNull(Object[] data) {

        for (int i = 0; i < visibleColumnCount; i++) {
            if (data[i] == null) {
                return true;
            }
        }

        return false;
    }

    /**
     * Special case for isSimpleAggregate cannot use index lookup.
     */
    public Object[] getGroupData(Object[] data) {

        if (isSimpleAggregate) {
            if (simpleAggregateData == null) {
                simpleAggregateData = data;

                return null;
            }

            return simpleAggregateData;
        }

        return (Object[]) rowMap.get(data);
    }

    boolean containsRow(Object[] data) {

        int position = ArraySort.searchFirst(table, 0, size, data, this);

        return position >= 0;
    }

    RowIterator findFirstRow(Object[] data) {

        int position = ArraySort.searchFirst(table, 0, size, data, this);

        if (position < 0) {
            position = size;
        } else {
            position--;
        }

        return new DataIterator(position);
    }

    /**
     * baseBlockSize remains unchanged.
     */
    void getBlock(int offset) {

        // no op for no blocks
    }

    private void ensureCapacity() {

        if (size == table.length) {
            int newSize = size == 0 ? 4
                    : size * 2;
            Object[][] newTable = new Object[newSize][];

            System.arraycopy(table, 0, newTable, 0, size);

            table = newTable;
        }
    }

    void implement() {
        throw Error.error(ErrorCode.U_S0500, "RSND");
    }

    class DataIterator implements RowIterator {

        int pos;

        DataIterator(int position) {
            pos = position;
        }

        public Row getNextRow() {
            return null;
        }

        public Object[] getNext() {

            if (hasNext()) {
                pos++;

                return table[pos];
            }

            return null;
        }

        public boolean hasNext() {
            return pos < size - 1;
        }

        public void remove() {
        }

        public boolean setRowColumns(boolean[] columns) {
            return false;
        }

        public void release() {
        }

        public long getRowId() {
            return 0L;
        }
    }

    public int compare(Object a, Object b) {
        return mainIndex.compareRow(session, (Object[]) a, (Object[]) b);
    }
}
