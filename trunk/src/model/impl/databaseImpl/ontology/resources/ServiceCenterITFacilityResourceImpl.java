package model.impl.databaseImpl.ontology.resources;

import model.interfaces.resources.ServiceCenterITFacilityResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:09:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceCenterITFacilityResourceImpl extends ContextResourceImpl implements ServiceCenterITFacilityResource {
    private String resourceProperty;

    public String getResourceProperty() {
        return resourceProperty;
    }

    public void setResourceProperty(String resourceProperty) {
        this.resourceProperty = resourceProperty;
    }
}
