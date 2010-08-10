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
    //TODO: de bagat eventual weight

    List<Core> getAssociatedCores();

    void setAssociatedCores(List<Core> newAssociatedCores);

    void removeAssociatedCores(Core oldAssociatedCores);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate

    Double getCacheRate();

    boolean hasCacheRate();

    void setCacheRate(Double newCacheRate);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuClockRate

    Double getCpuClockRate();

    boolean hasCpuClockRate();

    void setCpuClockRate(Double newCpuClockRate);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();

    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);
}
