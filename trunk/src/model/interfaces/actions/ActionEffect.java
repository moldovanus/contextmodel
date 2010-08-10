package model.interfaces.actions;

import model.interfaces.resources.ITFacilityPassiveResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 11:52:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ActionEffect {

    /**
     * effect = ax + b, where x = value of resource
     *
     * @param resource - resource on which the effect is applied
     * @param a
     * @param b
     */
    void enforce(ITFacilityPassiveResource resource, double a, double b);
}
