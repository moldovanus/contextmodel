package model.impl.databaseImpl.ontology.actions;

import model.interfaces.actions.DPMAction;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterITFacilityResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:21:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class DPMActionImpl extends ITComputingResourcesAdaptationActionImpl implements DPMAction {
    private ServiceCenterITFacilityResource resourceOn;
    int stateBeforeAction;
    int stateAfterAction;
    int noStatesJump;
    boolean toLowOrHighState;

    public DPMActionImpl() {
    }

    public DPMActionImpl(ServiceCenterITFacilityResource resourceOn) {
        this.resourceOn = resourceOn;
    }

    public ServiceCenterITComputingResource getResourceOn() {
        return (ServiceCenterITComputingResource) resourceOn;
    }

    public void setResourceOn(ServiceCenterITComputingResource resourceOn) {
        this.resourceOn = (ServiceCenterITFacilityResource) resourceOn;
    }

    public int getStateBeforeAction() {
        return stateBeforeAction;
    }

    public void setStateBeforeAction(int stateBeforeAction) {
        this.stateBeforeAction = stateBeforeAction;
    }

    public int getStateAfterAction() {
        return stateAfterAction;
    }

    public void setStateAfterAction(int stateAfterAction) {
        this.stateAfterAction = stateAfterAction;
    }

    public int getNoStatesJump() {
        return noStatesJump;
    }

    public void setNoStatesJump(int noStatesJump) {
        this.noStatesJump = noStatesJump;
    }

    public boolean getToLowOrHighState() {
        return toLowOrHighState;
    }

    public void setToLowOrHighState(boolean toLowOrHighState) {
        this.toLowOrHighState = toLowOrHighState;
    }
}
