package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.actions.MigrateActivity;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MigrateActivity
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultMigrateActivity extends DefaultConsolidationAction
        implements MigrateActivity {

    public DefaultMigrateActivity(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultMigrateActivity() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activity

    public ApplicationActivity getActivity() {
        return (ApplicationActivity) getPropertyValueAs(getActivityProperty(), ApplicationActivity.class);
    }


    public RDFProperty getActivityProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activity";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasActivity() {
        return getPropertyValueCount(getActivityProperty()) > 0;
    }


    public void setActivity(ApplicationActivity newActivity) {
        setPropertyValue(getActivityProperty(), newActivity);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom

    //FACUT JMECHELIE :D

    public ServiceCenterITComputingResource getResourceFrom() {
        return (ServiceCenterITComputingResource)
                ((Collection<ContextResource>) getPropertyValueAs(getResourcesProperty(), ContextResource.class)).toArray()[0];
    }


    public RDFProperty getResourceFromProperty() {
        throw new UnsupportedOperationException("This method should not exist. Please delete it.");
//        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom";
//        final String name = getOWLModel().getResourceNameForURI(uri);
//        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceFrom() {
        return getPropertyValueCount(getResourcesProperty()) > 1;
    }


    public void setResourceFrom(ServiceCenterITComputingResource newResourceFrom) {
        ContextResource resourceTo = getResourceTo();
        removePropertyValue(getResourcesProperty(), getResourceFrom());
        removePropertyValue(getResourcesProperty(), resourceTo);
        addPropertyValue(getResourcesProperty(), newResourceFrom);
        setPropertyValue(getResourcesProperty(), resourceTo);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource)
                ((Collection<ContextResource>) getPropertyValueAs(getResourcesProperty(), ContextResource.class)).toArray()[1];
    }


    public RDFProperty getResourceToProperty() {
        throw new UnsupportedOperationException("This method should not exist. Please delete it.");
//        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo";
//        final String name = getOWLModel().getResourceNameForURI(uri);
//        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceTo() {
        return getPropertyValueCount(getResourcesProperty()) > 2;
    }


    public void setResourceTo(ServiceCenterITComputingResource newResourceTo) {
        setPropertyValue(getResourceToProperty(), newResourceTo);
        ContextResource resourceFrom = getResourceFrom();
        removePropertyValue(getResourcesProperty(), resourceFrom);
        removePropertyValue(getResourcesProperty(), getResourceTo());
        addPropertyValue(getResourcesProperty(), resourceFrom);
        setPropertyValue(getResourcesProperty(), newResourceTo);
    }
}
