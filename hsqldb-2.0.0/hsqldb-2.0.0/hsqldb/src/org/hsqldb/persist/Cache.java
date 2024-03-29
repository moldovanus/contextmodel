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


package org.hsqldb.persist;

import org.hsqldb.error.Error;
import org.hsqldb.lib.ArraySort;
import org.hsqldb.lib.Iterator;
import org.hsqldb.lib.StopWatch;
import org.hsqldb.store.BaseHashMap;

import java.util.Comparator;

/**
 * New implementation of row caching for CACHED tables.<p>
 * <p/>
 * Manages memory for the cache map and its contents based on least recently
 * used clearup.<p>
 * Also provides services for selecting rows to be saved and passing them
 * to DataFileCache.<p>
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.8.0
 */
public class Cache extends BaseHashMap {

    final DataFileCache dataFileCache;
    private int capacity;         // number of Rows
    private long bytesCapacity;    // number of bytes
    private final CachedObjectComparator rowComparator;

//
    private CachedObject[] rowTable;
    long cacheBytesLength;

    // for testing
    StopWatch saveAllTimer = new StopWatch(false);
    StopWatch sortTimer = new StopWatch(false);
    int saveRowCount = 0;

    Cache(DataFileCache dfc) {

        super(dfc.capacity(), BaseHashMap.intKeyOrValue,
                BaseHashMap.objectKeyOrValue, true);

        maxCapacity = dfc.capacity();
        dataFileCache = dfc;
        capacity = dfc.capacity();
        bytesCapacity = dfc.bytesCapacity();
        rowComparator = new CachedObjectComparator();
        rowTable = new CachedObject[capacity];
        cacheBytesLength = 0;
    }

    /**
     * Structural initialisations take place here. This allows the Cache to
     * be resized while the database is in operation.
     */
    void init(int capacity, long bytesCapacity) {
    }

    long getTotalCachedBlockSize() {
        return cacheBytesLength;
    }

    /**
     * Returns a row if in memory cache.
     */
    public synchronized CachedObject get(int pos) {

        if (accessCount > ACCESS_MAX) {
            updateAccessCounts();
            resetAccessCount();
            updateObjectAccessCounts();
        }

        int lookup = getLookup(pos);

        if (lookup == -1) {
            return null;
        }

        accessTable[lookup] = ++accessCount;

        CachedObject object = (CachedObject) objectValueTable[lookup];

        return object;
    }

    /**
     * Adds a row to the cache.
     */
    synchronized void put(int key, CachedObject row) {

        int storageSize = row.getStorageSize();

        if (size() >= capacity
                || storageSize + cacheBytesLength > bytesCapacity) {
            cleanUp();
        }

        if (accessCount > ACCESS_MAX) {
            updateAccessCounts();
            resetAccessCount();
            updateObjectAccessCounts();
        }

        super.addOrRemove(key, row, null, false);
        row.setInMemory(true);

        cacheBytesLength += storageSize;
    }

    /**
     * Removes an object from memory cache. Does not release the file storage.
     */
    synchronized CachedObject release(int i) {

        CachedObject r = (CachedObject) super.addOrRemove(i, null, null, true);

        if (r == null) {
            return null;
        }

        cacheBytesLength -= r.getStorageSize();

        r.setInMemory(false);

        return r;
    }

    /**
     * Replace a row in the cache.
     */
    synchronized void replace(int key, CachedObject row) {

        int lookup = super.getLookup(key);

        super.objectValueTable[lookup] = row;
    }

    private void updateAccessCounts() {

        CachedObject r;
        int count;

        for (int i = 0; i < objectValueTable.length; i++) {
            r = (CachedObject) objectValueTable[i];

            if (r != null) {
                count = r.getAccessCount();

                if (count > accessTable[i]) {
                    accessTable[i] = count;
                }
            }
        }
    }

    private void updateObjectAccessCounts() {

        CachedObject r;
        int count;

        for (int i = 0; i < objectValueTable.length; i++) {
            r = (CachedObject) objectValueTable[i];

            if (r != null) {
                count = accessTable[i];

                r.updateAccessCount(count);
            }
        }
    }

    /**
     * Reduces the number of rows held in this Cache object. <p>
     * <p/>
     * Cleanup is done by checking the accessCount of the Rows and removing
     * the rows with the lowest access count.
     * <p/>
     * Index operations require that up to 5 recently accessed rows remain
     * in the cache.
     */
    private synchronized void cleanUp() {

        updateAccessCounts();

        int removeCount = size() / 2;
        int accessTarget = getAccessCountCeiling(removeCount, removeCount / 8);
        BaseHashMap.BaseHashIterator it = new BaseHashIterator();
        int savecount = 0;

        for (; it.hasNext();) {
            CachedObject row = (CachedObject) it.next();
            int currentAccessCount = it.getAccessCount();

            if (currentAccessCount <= accessTarget) {
                synchronized (row) {
                    if (row.isKeepInMemory()) {
                        it.setAccessCount(accessTarget + 1);
                    } else {
                        row.setInMemory(false);

                        if (row.hasChanged()) {
                            rowTable[savecount++] = row;
                        }

                        it.remove();

                        cacheBytesLength -= row.getStorageSize();

                        removeCount--;
                    }
                }
            }

            if (savecount == rowTable.length) {
                saveRows(savecount);

                savecount = 0;
            }
        }

        super.setAccessCountFloor(accessTarget);
        saveRows(savecount);
    }

    synchronized void forceCleanUp() {

        BaseHashMap.BaseHashIterator it = new BaseHashIterator();

        for (; it.hasNext();) {
            CachedObject row = (CachedObject) it.next();

            synchronized (row) {
                if (!row.isKeepInMemory()) {
                    row.setInMemory(false);
                    it.remove();

                    cacheBytesLength -= row.getStorageSize();
                }
            }
        }
    }

    private synchronized void saveRows(int count) {

        if (count == 0) {
            return;
        }

        rowComparator.setType(CachedObjectComparator.COMPARE_POSITION);
        sortTimer.start();
        ArraySort.sort(rowTable, 0, count, rowComparator);
        sortTimer.stop();
        saveAllTimer.start();
        dataFileCache.saveRows(rowTable, 0, count);

        saveRowCount += count;

        /*
                // not necessary if the full storage size of each object is written out
                try {
                    dataFile.file.seek(fileFreePosition);
                } catch (IOException e){}
        */
        saveAllTimer.stop();
    }

    /**
     * Writes out all modified cached Rows.
     */
    synchronized void saveAll() {

        Iterator it = new BaseHashIterator();
        int savecount = 0;

        for (; it.hasNext();) {
            if (savecount == rowTable.length) {
                saveRows(savecount);

                savecount = 0;
            }

            CachedObject r = (CachedObject) it.next();

            if (r.hasChanged()) {
                rowTable[savecount++] = r;
            }
        }

        saveRows(savecount);

        Error.printSystemOut(
                saveAllTimer.elapsedTimeToMessage(
                        "Cache.saveRows() total row save time"));
        Error.printSystemOut("Cache.saveRow() total row save count = "
                + saveRowCount);
        Error.printSystemOut(
                sortTimer.elapsedTimeToMessage("Cache.sort() total time"));
    }

    /**
     * clears out the memory cache
     */
    synchronized public void clear() {

        super.clear();

        cacheBytesLength = 0;
    }

    static final class CachedObjectComparator implements Comparator {

        static final int COMPARE_LAST_ACCESS = 0;
        static final int COMPARE_POSITION = 1;
        static final int COMPARE_SIZE = 2;
        private int compareType;

        CachedObjectComparator() {
        }

        void setType(int type) {
            compareType = type;
        }

        public int compare(Object a, Object b) {

            switch (compareType) {

                case COMPARE_POSITION:
                    return ((CachedObject) a).getPos()
                            - ((CachedObject) b).getPos();

                case COMPARE_SIZE:
                    return ((CachedObject) a).getStorageSize()
                            - ((CachedObject) b).getStorageSize();

                default:
                    return 0;
            }
        }
    }
}
