package model.impl.databaseImpl.ontology.resources;

import model.interfaces.resources.ITFacilityPassiveResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ITFacilityPassiveResourceImpl extends ServiceCenterITFacilityResourceImpl implements ITFacilityPassiveResource {
    private Double recordedValue;

    public Double getRecordedValue() {
        return recordedValue;
    }

    public void setRecordedValue(Double recordedValue) {
        this.recordedValue = recordedValue;
    }
}
