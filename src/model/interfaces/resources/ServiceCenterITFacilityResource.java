package model.interfaces.resources;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:17:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceCenterITFacilityResource extends ContextResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hasAssociatedActions

    Collection getHasAssociatedActions();

    boolean hasHasAssociatedActions();

    Iterator listHasAssociatedActions();

    void addHasAssociatedActions(boolean newHasAssociatedActions);

    void removeHasAssociatedActions(boolean oldHasAssociatedActions);

    void setHasAssociatedActions(Collection newHasAssociatedActions);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceProperty

    String getResourceProperty();

    boolean hasResourceProperty();

    void setResourceProperty(String newResourceProperty);
}
