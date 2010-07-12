package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterITFacilityResource;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:42:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DeployActivity extends ConsolidationAction {
    ApplicationActivity getActivity();

    void setActivity(ApplicationActivity activity);

    ServiceCenterITComputingResource getResourceTo();

    void setResourceTo(ServiceCenterITComputingResource resource);
}
