package model.interfaces.actions;

import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ITFacilityActiveResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:32:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityResourceAdaptationAction extends ContextAction {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedResources

    Collection getAssociatedResources();

    boolean hasAssociatedResources();

    Iterator listAssociatedResources();

    void addAssociatedResources(ContextResource newAssociatedResources);

    void removeAssociatedResources(ContextResource oldAssociatedResources);

    void setAssociatedResources(Collection newAssociatedResources);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#facilityAction

    ContextAction getFacilityAction();

    boolean hasFacilityAction();

    void setFacilityAction(ContextAction newFacilityAction);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#interractsWith

    Collection getInterractsWith();

    boolean hasInterractsWith();

    Iterator listInterractsWith();

    void addInterractsWith(ITFacilityActiveResource newInterractsWith);

    void removeInterractsWith(ITFacilityActiveResource oldInterractsWith);

    void setInterractsWith(Collection newInterractsWith);
}
