package model.interfaces.resources;

import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:17:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextResource extends ContextElement {
    String getResourceID();

    void setResourceID(String resourceID);
}
