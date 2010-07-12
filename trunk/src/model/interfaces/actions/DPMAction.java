package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITComputingResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:44:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DPMAction extends ITComputingResourceAdaptationAction {

    ServiceCenterITComputingResource getResourceOn();

    void setResourceOn(ServiceCenterITComputingResource resource);

    int getStateBeforeAction();

    void setStateBeforeAction(int value);

    int getStateAfterAction();

    void setStateAfterAction(int value);

    int getNoStatesJump();

    void setNoStatesJump(int value);

    boolean getToLowOrHighState();

    void setToLowOrHighState(boolean value);

}
