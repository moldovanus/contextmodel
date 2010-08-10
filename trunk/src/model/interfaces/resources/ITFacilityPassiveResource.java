package model.interfaces.resources;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:18:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityPassiveResource extends ServiceCenterITFacilityResource {


    Double getMaximumValue();

    boolean hasMaximumValue();

    void setMaximumValue(Double newMaximumValue);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#minimumValue

    Double getMinimumValue();

    boolean hasMinimumValue();

    void setMinimumValue(Double newMinimumValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#recordedValue

    Double getRecordedValue();

    boolean hasRecordedValue();

    void setRecordedValue(Double newRecordedValue);
}
