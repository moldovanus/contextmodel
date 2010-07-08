package model.interfaces.actions;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:32:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityResourcesAdaptationAction extends ContextAction {
    //List<ServiceCenterITFacilityResource> getResources();

    ContextAction getAction();

    void setAction(ContextAction action);
}
