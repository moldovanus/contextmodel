package model.interfaces.actions;

import model.interfaces.ContextResource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:31:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextAction {
    List<ContextResource> getResources();

    void setResources(List<ContextResource> resources);
}
