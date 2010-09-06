package model.interfaces.resources.applications;

import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.BusinessContextResource;
import model.interfaces.resources.ServiceCenterServer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    boolean hasActivityPolicies();

    Iterator listActivityPolicies();

    void addActivityPolicies(BusinessPolicy newActivityPolicies);

    void removeActivityPolicies(BusinessPolicy oldActivityPolicies);

    void setActivityPolicies(Collection newActivityPolicies);

    boolean isRunning();

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();

    RDFProperty getPartOfProperty();

    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);

    void addReceivedCoreIndex(int i);
    void removeReceivedCoreIndex(int i);
    void removeAllReceivedCoreIndex();
    List<Integer> getReceivedCoreIndexes();


     // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedServer

    ServiceCenterServer getAssociatedServer();

    boolean hasAssociatedServer();

    void setAssociatedServer(ServiceCenterServer newAssociatedServer);
    void removeAssociatedServer(ServiceCenterServer newAssociatedServer);

}
