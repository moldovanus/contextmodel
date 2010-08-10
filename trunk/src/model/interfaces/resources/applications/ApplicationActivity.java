package model.interfaces.resources.applications;

import model.interfaces.resources.BusinessContextResource;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:09:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationActivity extends BusinessContextResource {

    //TODO: de bagat task weight 
    //TODO: de bagat mai multe cores

    Double getCPURequiredValue();

    void setCPURequiredValue(Double value);

    Double getCPUWeight();

    Double getHDDWeight();

    Double getMEMWeight();


    void setCPUWeight(Double weight);

    void setHDDWeight(Double weight);

    void setMEMWeight(Double weight);


    Double getHDDRequiredValue();

    void setHDDRequiredValue(Double value);

    Double getMEMRequiredValue();

    void setMEMRequiredValue(Double value);

    Double getCPUAllocatedValue();

    void setCPUAllocatedValue(Double value);

    Double getHDDAllocatedValue();

    void setHDDAllocatedValue(Double value);

    Double getMEMAllocatedValue();

    void setMEMAllocatedValue(Double value);

    Double getPerformanceEstimation();

    void setPerformanceEstimation(Double value);

    Double getPerformanceDegradation();

    void setPerformanceDegradation(Double value);
}
