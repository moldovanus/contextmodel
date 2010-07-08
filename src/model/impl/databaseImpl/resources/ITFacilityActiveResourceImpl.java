package model.impl.databaseImpl.resources;

import model.interfaces.actions.ITFacilityResourcesAdaptationAction;
import model.interfaces.resources.ITFacilityActiveResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:13:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ITFacilityActiveResourceImpl extends ServiceCenterITFacilityResourceImpl implements ITFacilityActiveResource {
    private List<ITFacilityResourcesAdaptationAction> actions;

    public ITFacilityActiveResourceImpl() {
        actions = new ArrayList<ITFacilityResourcesAdaptationAction>();
    }

    public List<ITFacilityResourcesAdaptationAction> getActions() {
        return actions;
    }

    public void setActions(List<ITFacilityResourcesAdaptationAction> actions) {
        this.actions = actions;
    }
}
