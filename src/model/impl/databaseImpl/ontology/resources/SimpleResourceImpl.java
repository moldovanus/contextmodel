package model.impl.databaseImpl.ontology.resources;

import model.interfaces.resources.SimpleResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:25:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleResourceImpl extends ServiceCenterITComputingResourceImpl implements SimpleResource {
    private String resourceWorkLoadProperty;

    public String getResourceWorkLoadProperty() {
        return resourceWorkLoadProperty;
    }

    public void setResourceWorkLoadProperty(String resourceWorkLoadProperty) {
        this.resourceWorkLoadProperty = resourceWorkLoadProperty;
    }
}
