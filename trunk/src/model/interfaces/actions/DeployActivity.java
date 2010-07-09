package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITFacilityResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:42:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeployActivity extends ConsolidationAction {
    List<ApplicationActivity> getActivities();

    void setActivities(List<ApplicationActivity> activity);

    ServiceCenterITFacilityResource getResourceTo();

    void setResourceTo(ServiceCenterITFacilityResource resource);
}
