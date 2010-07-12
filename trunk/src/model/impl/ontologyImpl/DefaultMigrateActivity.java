package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.actions.MigrateActivity;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;


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

    public ApplicationActivity getApplicationActivity() {
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


    public void setApplicationActivity(ApplicationActivity newActivity) {
        setPropertyValue(getActivityProperty(), newActivity);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom

    public ServiceCenterITComputingResource getResourceFrom() {
        return (ServiceCenterITComputingResource) getPropertyValueAs(getResourceFromProperty(), ServiceCenterITComputingResource.class);
    }


    public RDFProperty getResourceFromProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceFrom() {
        return getPropertyValueCount(getResourceFromProperty()) > 0;
    }


    public void setResourceFrom(ServiceCenterITComputingResource newResourceFrom) {
        setPropertyValue(getResourceFromProperty(), newResourceFrom);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource) getPropertyValueAs(getResourceToProperty(), ServiceCenterITComputingResource.class);
    }


    public RDFProperty getResourceToProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceTo() {
        return getPropertyValueCount(getResourceToProperty()) > 0;
    }


    public void setResourceTo(ServiceCenterITComputingResource newResourceTo) {
        setPropertyValue(getResourceToProperty(), newResourceTo);
    }
}
