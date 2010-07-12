package model.interfaces.actions;

import model.interfaces.ContextElement;
import model.interfaces.resources.ContextResource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:31:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextAction extends ContextElement {
    List<ContextResource> getResources();

    void setResources(List<ContextResource> resources);
}
