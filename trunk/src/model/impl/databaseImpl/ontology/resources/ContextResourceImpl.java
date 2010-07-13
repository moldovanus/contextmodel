package model.impl.databaseImpl.ontology.resources;

import model.impl.databaseImpl.ontology.ContextElementImpl;
import model.interfaces.resources.ContextResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:08:02 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ContextResourceImpl extends ContextElementImpl implements ContextResource {
    private String resourceID;

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }
}
