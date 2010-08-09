package model.interfaces.resources;

import model.interfaces.actions.ITFacilityResourceAdaptationAction;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:48:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityActiveResource extends ServiceCenterITFacilityResource {
    List<ITFacilityResourceAdaptationAction> getActions();

    void setActions(List<ITFacilityResourceAdaptationAction> actions);

    boolean hasAssociatedActions();

    void addAssociatedActions(ITFacilityResourceAdaptationAction newAssociatedActions);

    void removeAssociatedActions(ITFacilityResourceAdaptationAction oldAssociatedActions);

    void setAssociatedActions(Collection newAssociatedActions);
}
