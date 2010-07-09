package model.impl.databaseImpl.actions;

import model.interfaces.actions.DeployActivity;
import model.interfaces.resources.ServiceCenterITFacilityResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:19:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeployActivityImpl extends ContextActionImpl implements DeployActivity {
    private List<ApplicationActivity> activities;
    private ServiceCenterITFacilityResource resourceTo;

    public DeployActivityImpl() {
        activities = new ArrayList<ApplicationActivity>();
    }

    public DeployActivityImpl(ServiceCenterITFacilityResource resourceTo) {
        this.resourceTo = resourceTo;
        activities = new ArrayList<ApplicationActivity>();
    }


    public List<ApplicationActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ApplicationActivity> activities) {
        this.activities = activities;
    }

    public ServiceCenterITFacilityResource getResourceTo() {
        return resourceTo;
    }

    public void setResourceTo(ServiceCenterITFacilityResource resourceTo) {
        this.resourceTo = resourceTo;
    }
}
