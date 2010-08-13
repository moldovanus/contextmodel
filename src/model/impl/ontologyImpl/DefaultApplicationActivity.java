package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ApplicationActivity
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultApplicationActivity extends DefaultBusinessContextResource
        implements ApplicationActivity {

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
    
}
