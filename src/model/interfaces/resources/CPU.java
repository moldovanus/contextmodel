package model.interfaces.resources;

import model.interfaces.ContextElement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:07:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CPU extends SimpleResource {
    List<Core> getAssociatedCores();

    void setAssociatedCores(List<Core> newAssociatedCores);

    float getCacheRate();

    void setCacheRate(float newCacheRate);

    float getClockRate();

    void setClockRate(float newClockRate);

    void removeAssociatedCores(Core oldAssociatedCores);

    void setAssociatedCores(Collection newAssociatedCores);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate

    boolean hasCacheRate();

    Iterator listCacheRate();

    void addCacheRate(float newCacheRate);

    void removeCacheRate(float oldCacheRate);

    void setCacheRate(Collection newCacheRate);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuClockRate

    Collection getCpuClockRate();

    boolean hasCpuClockRate();

    Iterator listCpuClockRate();

    void addCpuClockRate(float newCpuClockRate);

    void removeCpuClockRate(float oldCpuClockRate);

    void setCpuClockRate(Collection newCpuClockRate);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();

    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);
}
