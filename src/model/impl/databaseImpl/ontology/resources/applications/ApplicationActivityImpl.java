package model.impl.databaseImpl.resources.applications;

import model.impl.databaseImpl.resources.BusinessContextResourceImpl;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:09:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationActivityImpl extends BusinessContextResourceImpl implements ApplicationActivity {
    private Double CPURequiredValue;
    private Double HDDRequiredValue;
    private Double MEMRequiredValue;
    private Double CPUAllocatedValue;
    private Double HDDAllocatedValue;
    private Double MEMAllocatedValue;
    private Double performanceEstimation;
    private Double performanceDegradation;

    public Double getCPURequiredValue() {
        return CPURequiredValue;
    }

    public void setCPURequiredValue(Double CPURequiredValue) {
        this.CPURequiredValue = CPURequiredValue;
    }

    public Double getHDDRequiredValue() {
        return HDDRequiredValue;
    }

    public void setHDDRequiredValue(Double HDDRequiredValue) {
        this.HDDRequiredValue = HDDRequiredValue;
    }

    public Double getMEMRequiredValue() {
        return MEMRequiredValue;
    }

    public void setMEMRequiredValue(Double MEMRequiredValue) {
        this.MEMRequiredValue = MEMRequiredValue;
    }

    public Double getCPUAllocatedValue() {
        return CPUAllocatedValue;
    }

    public void setCPUAllocatedValue(Double CPUAllocatedValue) {
        this.CPUAllocatedValue = CPUAllocatedValue;
    }

    public Double getHDDAllocatedValue() {
        return HDDAllocatedValue;
    }

    public void setHDDAllocatedValue(Double HDDAllocatedValue) {
        this.HDDAllocatedValue = HDDAllocatedValue;
    }

    public Double getMEMAllocatedValue() {
        return MEMAllocatedValue;
    }

    public void setMEMAllocatedValue(Double MEMAllocatedValue) {
        this.MEMAllocatedValue = MEMAllocatedValue;
    }

    public Double getPerformanceEstimation() {
        return performanceEstimation;
    }

    public void setPerformanceEstimation(Double performanceEstimation) {
        this.performanceEstimation = performanceEstimation;
    }

    public Double getPerformanceDegradation() {
        return performanceDegradation;
    }

    public void setPerformanceDegradation(Double performanceDegradation) {
        this.performanceDegradation = performanceDegradation;
    }
}
