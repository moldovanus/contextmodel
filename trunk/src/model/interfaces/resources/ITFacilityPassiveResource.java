package model.interfaces.resources;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:18:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityPassiveResource extends ServiceCenterITFacilityResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedActiveResources

    Collection getAssociatedActiveResources();


    boolean hasAssociatedActiveResources();

    Iterator listAssociatedActiveResources();

    void addAssociatedActiveResources(ITFacilityActiveResource newAssociatedActiveResources);

    void removeAssociatedActiveResources(ITFacilityActiveResource oldAssociatedActiveResources);

    void setAssociatedActiveResources(Collection newAssociatedActiveResources);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#minimumValue

    Double getMinimumValue();

    boolean hasMinimumValue();

    void setMinimumValue(Double newMinimumValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#recordedValue

    Double getRecordedValue();

    boolean hasRecordedValue();

    void setRecordedValue(Double newRecordedValue);
}
