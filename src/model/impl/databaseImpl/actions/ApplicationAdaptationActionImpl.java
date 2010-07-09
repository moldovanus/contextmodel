package model.impl.databaseImpl.actions;

import model.interfaces.actions.ApplicationAdaptationAction;
import model.interfaces.resources.ServiceCenterITFacilityResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:15:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationAdaptationActionImpl extends ContextActionImpl implements ApplicationAdaptationAction {
    private List<ApplicationActivity> activities;
    private ServiceCenterITFacilityResource resourceFrom;
    private ServiceCenterITFacilityResource resourceTo;

    public ApplicationAdaptationActionImpl() {
        activities = new ArrayList<ApplicationActivity>();
    }

    public ApplicationAdaptationActionImpl(ServiceCenterITFacilityResource resourceFrom, ServiceCenterITFacilityResource resourceTo) {
        activities = new ArrayList<ApplicationActivity>();
        this.resourceFrom = resourceFrom;
        this.resourceTo = resourceTo;
    }

    public List<ApplicationActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ApplicationActivity> activities) {
        this.activities = activities;
    }

    public ServiceCenterITFacilityResource getResourceFrom() {
        return resourceFrom;
    }

    public void setResourceFrom(ServiceCenterITFacilityResource resourceFrom) {
        this.resourceFrom = resourceFrom;
    }

    public ServiceCenterITFacilityResource getResourceTo() {
        return resourceTo;
    }

    public void setResourceTo(ServiceCenterITFacilityResource resourceTo) {
        this.resourceTo = resourceTo;
    }
}
