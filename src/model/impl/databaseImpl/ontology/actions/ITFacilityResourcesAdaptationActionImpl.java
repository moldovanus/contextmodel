package model.impl.databaseImpl.ontology.actions;

import model.interfaces.actions.ContextAction;
import model.interfaces.actions.ITFacilityResourceAdaptationAction;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:24:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ITFacilityResourcesAdaptationActionImpl extends ContextActionImpl implements ITFacilityResourceAdaptationAction {
    private ContextAction action;

    public ITFacilityResourcesAdaptationActionImpl() {
    }

    public ITFacilityResourcesAdaptationActionImpl(ContextAction action) {
        this.action = action;
    }

    public ContextAction getAction() {
        return action;
    }

    public void setAction(ContextAction action) {
        this.action = action;
    }
}
