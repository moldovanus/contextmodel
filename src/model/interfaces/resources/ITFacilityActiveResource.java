package model.interfaces.resources;

import model.interfaces.actions.ITFacilityResourcesAdaptationAction;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:48:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityActiveResource extends ServiceCenterITFacilityResource {

    List<ITFacilityResourcesAdaptationAction> getActions();

    void setActions(List<ITFacilityResourcesAdaptationAction> actions);

}
