package model.interfaces.resources.applications;

import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.BusinessContextResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:09:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationActivity extends BusinessContextResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    Collection getActivityPolicies();

    RDFProperty getActivityPoliciesProperty();

    boolean hasActivityPolicies();

    Iterator listActivityPolicies();

    void addActivityPolicies(BusinessPolicy newActivityPolicies);

    void removeActivityPolicies(BusinessPolicy oldActivityPolicies);

    void setActivityPolicies(Collection newActivityPolicies);

    boolean isRunning();

}
