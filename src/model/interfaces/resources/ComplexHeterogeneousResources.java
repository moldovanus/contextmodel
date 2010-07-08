package model.interfaces.resources;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:55:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ComplexHeterogeneousResources extends ServiceCenterITComputingResource {

    List<String> getResourceWorkloadProperty();

    void setResourceWorkloadProperty(List<String> list);

    List<ServiceCenterITComputingResource> getResources();

    void setResources(List<ServiceCenterITComputingResource> list);

}
