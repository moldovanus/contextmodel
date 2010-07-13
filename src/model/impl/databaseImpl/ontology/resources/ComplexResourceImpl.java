package model.impl.databaseImpl.ontology.resources;

import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:21:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComplexResourceImpl extends ServiceCenterITComputingResourceImpl implements ComplexResource {
    List<String> resourceWorkloadProperty;
    List<ServiceCenterITComputingResource> resources;

    public ComplexResourceImpl() {
        resourceWorkloadProperty = new ArrayList<String>();
        resources = new ArrayList<ServiceCenterITComputingResource>();
    }

    public List<String> getResourceWorkloadProperty() {
        return resourceWorkloadProperty;
    }

    public void setResourceWorkloadProperty(List<String> resourceWorkloadProperty) {
        this.resourceWorkloadProperty = resourceWorkloadProperty;
    }

    public List<ServiceCenterITComputingResource> getResources() {
        return resources;
    }

    public void setResources(List<ServiceCenterITComputingResource> resources) {
        this.resources = resources;
    }
}
