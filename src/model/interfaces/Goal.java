package model.interfaces;

import model.interfaces.resources.ITFacilityPassiveResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:20:23 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Goal {
    boolean isAchieved(ITFacilityPassiveResource resource);     
}
