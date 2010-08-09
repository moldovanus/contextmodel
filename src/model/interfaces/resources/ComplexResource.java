package model.interfaces.resources;

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

    Iterator listResourceWorkloadProperty();

    void addResourceWorkloadProperty(String newResourceWorkloadProperty);

    void removeResourceWorkloadProperty(String oldResourceWorkloadProperty);

    void setResourceWorkloadProperty(Collection newResourceWorkloadProperty);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#simpleResources

    Collection getSimpleResources();

    boolean hasSimpleResources();

    Iterator listSimpleResources();

    void addSimpleResources(SimpleResource newSimpleResources);

    void removeSimpleResources(SimpleResource oldSimpleResources);

    void setSimpleResources(Collection newSimpleResources);
}
