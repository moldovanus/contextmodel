package model.impl.databaseImpl.ontology.actions;

import model.interfaces.actions.DeployActivity;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterITFacilityResource;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:19:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeployActivityImpl extends ConsolidationActionImpl implements DeployActivity {
    private ApplicationActivity activity;
    private ServiceCenterITFacilityResource resourceTo;

    public DeployActivityImpl() {

    }

    public DeployActivityImpl(ApplicationActivity activity, ServiceCenterITFacilityResource resourceTo) {
        this.activity = activity;
        this.resourceTo = resourceTo;

    }

    public ApplicationActivity getActivity() {
        return activity;
    }

    public void setActivity(ApplicationActivity activity) {
        this.activity = activity;
    }

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource) resourceTo;
    }

    public void setResourceTo(ServiceCenterITComputingResource resourceTo) {
        this.resourceTo = (ServiceCenterITFacilityResource) resourceTo;
    }
}
