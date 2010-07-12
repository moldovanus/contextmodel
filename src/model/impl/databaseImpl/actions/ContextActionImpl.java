package model.impl.databaseImpl.actions;

import model.impl.databaseImpl.ContextElementImpl;
import model.interfaces.actions.ContextAction;
import model.interfaces.resources.ContextResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:13:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContextActionImpl extends ContextElementImpl implements ContextAction {
    private List<ContextResource> resources;

    public ContextActionImpl() {
        this.resources = new ArrayList<ContextResource>();
    }

    public List<ContextResource> getResources() {
        return resources;
    }

    public void setResources(List<ContextResource> resources) {
        this.resources = resources;
    }
}
