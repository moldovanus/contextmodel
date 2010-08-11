package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.actions.FacilityDefaultAction;
import model.interfaces.actions.ITFacilityResourceAdaptationAction;
import model.interfaces.resources.ContextResource;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ITFacilityResourceAdaptationAction
 *
 * @version generated on Tue Jul 06 17:49:11 GMT 2010
 */
public class DefaultITFacilityResourceAdaptationAction extends DefaultContextAction
        implements ITFacilityResourceAdaptationAction {

    public DefaultITFacilityResourceAdaptationAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultITFacilityResourceAdaptationAction() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedResources

    public Collection getAssociatedResources() {
        return getPropertyValuesAs(getAssociatedResourcesProperty(), ContextResource.class);
    }


    public RDFProperty getAssociatedResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasAssociatedResources() {
        return getPropertyValueCount(getAssociatedResourcesProperty()) > 0;
    }


    public Iterator listAssociatedResources() {
        return listPropertyValuesAs(getAssociatedResourcesProperty(), ContextResource.class);
    }


    public void addAssociatedResources(ContextResource newAssociatedResources) {
        addPropertyValue(getAssociatedResourcesProperty(), newAssociatedResources);
    }


    public void removeAssociatedResources(ContextResource oldAssociatedResources) {
        removePropertyValue(getAssociatedResourcesProperty(), oldAssociatedResources);
    }


    public void setAssociatedResources(Collection newAssociatedResources) {
        setPropertyValues(getAssociatedResourcesProperty(), newAssociatedResources);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#facilityAction

    public FacilityDefaultAction getFacilityAction() {
        return (FacilityDefaultAction) getPropertyValueAs(getFacilityActionProperty(), FacilityDefaultAction.class);
    }


    public RDFProperty getFacilityActionProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#facilityAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasFacilityAction() {
        return getPropertyValueCount(getFacilityActionProperty()) > 0;
    }


    public void setFacilityAction(FacilityDefaultAction newFacilityAction) {
        setPropertyValue(getFacilityActionProperty(), newFacilityAction);
    }


}
