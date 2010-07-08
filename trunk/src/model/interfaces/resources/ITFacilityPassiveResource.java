package model.interfaces.resources;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:18:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITFacilityPassiveResource extends ServiceCenterITFacilityResource {
    Double getRecordedValue();

    void setRecordedValue(Double value);
}
