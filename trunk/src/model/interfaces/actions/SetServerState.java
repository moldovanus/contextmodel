package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterServer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 11:42:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SetServerState extends ConsolidationAction {
    int HIGH_POWER_STATE = 1;
    int LOW_POWER_STATE = 0;

    void setServerState(int state);

    void setTarget(ServiceCenterServer target);

    ServiceCenterServer getTarget();

}
