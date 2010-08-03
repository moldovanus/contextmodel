package model.impl.databaseImpl.dao.ehcache;

import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 2, 2010
 * Time: 11:45:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyCacheWriter implements CacheWriter {

    public CacheWriter clone(Ehcache ehcache) throws CloneNotSupportedException {
        return (CacheWriter) ehcache.clone();
    }

    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void dispose() throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write(Element element) throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeAll(Collection<Element> elements) throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void delete(CacheEntry cacheEntry) throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteAll(Collection<CacheEntry> cacheEntries) throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
