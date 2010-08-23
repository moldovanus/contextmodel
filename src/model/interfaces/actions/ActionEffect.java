package model.interfaces.actions;

import model.interfaces.resources.ITFacilityPassiveResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 12, 2010
 * Time: 1:04:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ActionEffect {
    void execute(ITFacilityPassiveResource resource);

    void undo(ITFacilityPassiveResource resource);

    boolean equals(Object o);
}
