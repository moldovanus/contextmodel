package model.impl.ontologyImpl.resources.applications;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.impl.ontologyImpl.resources.DefaultBusinessContextResource;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ApplicationActivity
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultApplicationActivity extends DefaultBusinessContextResource
        implements ApplicationActivity {

    private List<Integer> receivedCoresIndex;

    {
        receivedCoresIndex = new ArrayList<Integer>();
    }

    public DefaultApplicationActivity(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultApplicationActivity() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    public Collection getActivityPolicies() {
        return getPropertyValuesAs(getActivityPoliciesProperty(), BusinessPolicy.class);
    }


    public RDFProperty getActivityPoliciesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasActivityPolicies() {
        return getPropertyValueCount(getActivityPoliciesProperty()) > 0;
    }


    public Iterator listActivityPolicies() {
        return listPropertyValuesAs(getActivityPoliciesProperty(), BusinessPolicy.class);
    }


    public void addActivityPolicies(BusinessPolicy newActivityPolicies) {
        addPropertyValue(getActivityPoliciesProperty(), newActivityPolicies);
    }


    public void removeActivityPolicies(BusinessPolicy oldActivityPolicies) {
        removePropertyValue(getActivityPoliciesProperty(), oldActivityPolicies);
    }


    public void setActivityPolicies(Collection newActivityPolicies) {
        setPropertyValues(getActivityPoliciesProperty(), newActivityPolicies);
    }

    public boolean isRunning() {
        return getCpuAllocatedValue() > 0;
    }

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

       public Collection getPartOf() {
           return getPropertyValuesAs(getPartOfProperty(), ContextElement.class);
       }


       public RDFProperty getPartOfProperty() {
           final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf";
           final String name = getOWLModel().getResourceNameForURI(uri);
           return getOWLModel().getRDFProperty(name);
       }


       public boolean hasPartOf() {
           return getPropertyValueCount(getPartOfProperty()) > 0;
       }


       public Iterator listPartOf() {
           return listPropertyValuesAs(getPartOfProperty(), ContextElement.class);
       }


       public void addPartOf(ContextElement newPartOf) {
           addPropertyValue(getPartOfProperty(), newPartOf);
       }


       public void removePartOf(ContextElement oldPartOf) {
           removePropertyValue(getPartOfProperty(), oldPartOf);
       }


       public void setPartOf(Collection newPartOf) {
           setPropertyValues(getPartOfProperty(), newPartOf);
       }

    public void addReceivedCoreIndex(int i) {
        receivedCoresIndex.add(i);
    }

    public void removeReceivedCoreIndex(int i) {
        receivedCoresIndex.remove(i);
    }

    public void removeAllReceivedCoreIndex() {
        receivedCoresIndex.clear();;
    }

    public List<Integer> getReceivedCoreIndexes() {
       return receivedCoresIndex;
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedServer

    public ServiceCenterServer getAssociatedServer() {
        return (ServiceCenterServer) getPropertyValueAs(getAssociatedServerProperty(), ServiceCenterServer.class);
    }


    public RDFProperty getAssociatedServerProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedServer";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasAssociatedServer() {
        return getPropertyValueCount(getAssociatedServerProperty()) > 0;
    }


    public void setAssociatedServer(ServiceCenterServer newAssociatedServer) {
        setPropertyValue(getAssociatedServerProperty(), newAssociatedServer);
    }

    public void removeAssociatedServer(ServiceCenterServer newAssociatedServer) {
        removePropertyValue(getAssociatedServerProperty(),newAssociatedServer);
    }
}