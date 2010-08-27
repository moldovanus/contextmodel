package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.impl.util.ModelAccess;
import model.interfaces.actions.DeployActivity;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#DeployActivity
 *
 * @version generated on Tue Jul 06 17:49:11 GMT 2010
 */
public class DefaultDeployActivityAction extends DefaultConsolidationAction
        implements DeployActivity {

    public DefaultDeployActivityAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultDeployActivityAction() {
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


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource)
                getResources().toArray()[0];
    }


    public void setResourceTo(ServiceCenterITComputingResource newResourceTo) {
        if (hasResources()) {
            ContextResource resourceTo = getResourceTo();
            removePropertyValue(getResourcesProperty(), resourceTo);
        }

        addPropertyValue(getResourcesProperty(), newResourceTo);
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        ServiceCenterServer server = (ServiceCenterServer) getResourceTo();
        server.addRunningActivity(getActivity());
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        ServiceCenterServer server = (ServiceCenterServer) getResourceTo();
        server.removeRunningActivity(getActivity());
    }

    @Override
    public String toString() {
        return this.getLocalName() + " " + this.getActivity().getLocalName() + " to " + this.getResourceTo().getLocalName();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultDeployActivityAction)) {
            return false;
        }
        DefaultDeployActivityAction activityAction = (DefaultDeployActivityAction) o;
        return activityAction.getName().equals(this.getName()) || activityAction.getResourceTo().equals(this.getResourceTo()) && activityAction.getActivity().equals(this.getActivity());
    }
}