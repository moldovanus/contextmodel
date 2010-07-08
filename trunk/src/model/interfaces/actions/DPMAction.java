package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITFacilityResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:44:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DPMAction extends ITComputingResourcesAdaptationAction {

    ServiceCenterITFacilityResource getResourceOn();

    void setResourceOn(ServiceCenterITFacilityResource resource);

    int getStateBeforeAction();

    void setStateBeforeAction();

    int getStateAfterAction();

    void setStateAfterAction();

    int getNoStatesJump();

    void setNoStatesJump();

    boolean getToLowOrHighState();

    void setToLowOrHighState(boolean value);

}
