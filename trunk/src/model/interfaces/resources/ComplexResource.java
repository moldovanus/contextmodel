package model.interfaces.resources;

import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:55:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ComplexResource extends ServiceCenterITComputingResource {

    List<String> getResourceWorkloadProperty();

    void setResourceWorkloadProperty(List<String> list);

    List<ServiceCenterITComputingResource> getResources();

    void setResources(List<ServiceCenterITComputingResource> list);
    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight

    float getCPUWeight();


    boolean hasCPUWeight();

    void setCPUWeight(float newCPUWeight);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight

    float getHDDWeight();


    boolean hasHDDWeight();

    void setHDDWeight(float newHDDWeight);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight

    float getMEMWeight();


    boolean hasMEMWeight();

    void setMEMWeight(float newMEMWeight);
    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuResources

    Collection<CPU> getCpuResources();


    boolean hasCpuResources();

    Iterator listCpuResources();

    void addCpuResources(CPU newCpuResources);

    void removeCpuResources(CPU oldCpuResources);

    void setCpuResources(Collection newCpuResources);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddResources

    Collection<HDD> getHddResources();


    boolean hasHddResources();

    Iterator listHddResources();

    void addHddResources(HDD newHddResources);

    void removeHddResources(HDD oldHddResources);

    void setHddResources(Collection newHddResources);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#isActive

    boolean getIsActive();


    boolean hasIsActive();

    void setIsActive(boolean newIsActive);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memResources

    Collection<MEM> getMemResources();


    boolean hasMemResources();

    Iterator listMemResources();

    void addMemResources(MEM newMemResources);

    void removeMemResources(MEM oldMemResources);

    void setMemResources(Collection newMemResources);

    Iterator listResourceWorkloadProperty();

    void addResourceWorkloadProperty(String newResourceWorkloadProperty);

    void removeResourceWorkloadProperty(String oldResourceWorkloadProperty);

    void setResourceWorkloadProperty(Collection newResourceWorkloadProperty);

    boolean hasResourcesFor(ApplicationActivity task);

}
