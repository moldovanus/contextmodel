package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:42:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeployActivity extends ConsolidationAction {
    //TODO: cum iau si fac getTo si Activity cand eu am colectia de resurse?

    ApplicationActivity getActivity();

    boolean hasActivity();

    void setActivity(ApplicationActivity newActivity);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    ServiceCenterITComputingResource getResourceTo();

    void setResourceTo(ServiceCenterITComputingResource newResourceTo);
}
 