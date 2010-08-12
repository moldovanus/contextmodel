package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:40:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MigrateActivity extends ConsolidationAction {

    //TODO: de facut jmechrie de genu getResourceFrom{ getAllResources().get(0)}
    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activity

    ApplicationActivity getActivity();

    boolean hasActivity();

    void setActivity(ApplicationActivity newActivity);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom

    ServiceCenterITComputingResource getResourceFrom();

    boolean hasResourceFrom();

    void setResourceFrom(ServiceCenterITComputingResource newResourceFrom);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    ServiceCenterITComputingResource getResourceTo();

    boolean hasResourceTo();

    void setResourceTo(ServiceCenterITComputingResource newResourceTo);
}
