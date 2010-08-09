package model.interfaces.resources;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:18:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityPassiveResource extends ServiceCenterITFacilityResource {
    void setRecordedValue(Double value);

    float getMaximumValue();

    boolean hasMaximumValue();

    void setMaximumValue(float newMaximumValue);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#minimumValue

    float getMinimumValue();

    boolean hasMinimumValue();

    void setMinimumValue(float newMinimumValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#recordedValue

    float getRecordedValue();

    boolean hasRecordedValue();

    void setRecordedValue(float newRecordedValue);
}
