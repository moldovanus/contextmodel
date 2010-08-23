package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.actions.DPMAction;
import model.interfaces.resources.ServiceCenterITComputingResource;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#DPMAction
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultDPMAction extends DefaultITComputingResourceAdaptationAction
        implements DPMAction {

    public DefaultDPMAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultDPMAction() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#noStatesJump

    public int getNoStatesJump() {
        return getPropertyValueLiteral(getNoStatesJumpProperty()).getInt();
    }


    public RDFProperty getNoStatesJumpProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#noStatesJump";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasNoStatesJump() {
        return getPropertyValueCount(getNoStatesJumpProperty()) > 0;
    }


    public void setNoStatesJump(int newNoStatesJump) {
        setPropertyValue(getNoStatesJumpProperty(), new java.lang.Integer(newNoStatesJump));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceOn

    public ServiceCenterITComputingResource getResourceOn() {
        return (ServiceCenterITComputingResource) getPropertyValueAs(getResourceOnProperty(), ServiceCenterITComputingResource.class);
    }


    public RDFProperty getResourceOnProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceOn";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceOn() {
        return getPropertyValueCount(getResourceOnProperty()) > 0;
    }


    public void setResourceOn(ServiceCenterITComputingResource newResourceOn) {
        setPropertyValue(getResourceOnProperty(), newResourceOn);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#stateAfterAction

    public int getStateAfterAction() {
        return getPropertyValueLiteral(getStateAfterActionProperty()).getInt();
    }


    public RDFProperty getStateAfterActionProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#stateAfterAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasStateAfterAction() {
        return getPropertyValueCount(getStateAfterActionProperty()) > 0;
    }


    public void setStateAfterAction(int newStateAfterAction) {
        setPropertyValue(getStateAfterActionProperty(), new java.lang.Integer(newStateAfterAction));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#stateBeforeAction

    public int getStateBeforeAction() {
        return getPropertyValueLiteral(getStateBeforeActionProperty()).getInt();
    }


    public RDFProperty getStateBeforeActionProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#stateBeforeAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasStateBeforeAction() {
        return getPropertyValueCount(getStateBeforeActionProperty()) > 0;
    }


    public void setStateBeforeAction(int newStateBeforeAction) {
        setPropertyValue(getStateBeforeActionProperty(), new java.lang.Integer(newStateBeforeAction));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#toLowOrHighState

    public boolean getToLowOrHighState() {
        return getPropertyValueLiteral(getToLowOrHighStateProperty()).getBoolean();
    }


    public RDFProperty getToLowOrHighStateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#toLowOrHighState";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasToLowOrHighState() {
        return getPropertyValueCount(getToLowOrHighStateProperty()) > 0;
    }


    public void setToLowOrHighState(boolean newToLowOrHighState) {
        setPropertyValue(getToLowOrHighStateProperty(), new java.lang.Boolean(newToLowOrHighState));
    }


    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
