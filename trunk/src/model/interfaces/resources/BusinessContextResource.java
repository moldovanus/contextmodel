package model.interfaces.resources;

import model.interfaces.policies.BusinessPolicy;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:52:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BusinessContextResource extends ContextResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight

    float getCPUWeight();

    boolean hasCPUWeight();

    void setCpuWeight(float newCPUWeight);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight

    float getHDDWeight();


    boolean hasHDDWeight();

    void setHddWeight(float newHDDWeight);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight

    float getMEMWeight();


    boolean hasMEMWeight();

    void setMemWeight(float newMEMWeight);


    /// Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    float getCpuAllocatedValue();

    boolean hasCpuAllocatedValue();

    void setCpuAllocatedValue(float newCpuAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMaxValue

    float getCpuRequiredMaxValue();

    boolean hasCpuRequiredMaxValue();

    void setCpuRequiredMaxValue(float newCpuRequiredMaxValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMinValue

    float getCpuRequiredMinValue();


    boolean hasCpuRequiredMinValue();

    void setCpuRequiredMinValue(float newCpuRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue

    float getHddAllocatedValue();


    boolean hasHddAllocatedValue();

    void setHddAllocatedValue(float newHddAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMaxValue

    float getHddRequiredMaxValue();


    boolean hasHddRequiredMaxValue();

    void setHddRequiredMaxValue(float newHddRequiredMaxValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMinValue

    float getHddRequiredMinValue();


    boolean hasHddRequiredMinValue();

    void setHddRequiredMinValue(float newHddRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue

    float getMemAllocatedValue();


    boolean hasMemAllocatedValue();

    void setMemAllocatedValue(float newMemAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMaxValue

    float getMemRequiredMaxValue();


    boolean hasMemRequiredMaxValue();

    void setMemRequiredMaxValue(float newMemRequiredMaxValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMinValue

    float getMemRequiredMinValue();


    boolean hasMemRequiredMinValue();

    void setMemRequiredMinValue(float newMemRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresAllocatedValue

    float getNumberOfCoresAllocatedValue();


    boolean hasNumberOfCoresAllocatedValue();

    void setNumberOfCoresAllocatedValue(float newNumberOfCoresAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresRequiredValue

    float getNumberOfCoresRequiredValue();


    boolean hasNumberOfCoresRequiredValue();

    void setNumberOfCoresRequiredValue(float newNumberOfCoresRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    float getPerformanceDegradation();


    boolean hasPerformanceDegradation();

    void setPerformanceDegradation(float newPerformanceDegradation);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    float getPerformanceEstimation();


    boolean hasPerformanceEstimation();

    void setPerformanceEstimation(float newPerformanceEstimation);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    Collection getActivityPolicies();

    boolean hasActivityPolicies();

    void addActivityPolicies(BusinessPolicy newActivityPolicies);

    void removeActivityPolicies(BusinessPolicy oldActivityPolicies);

    void setActivityPolicies(Collection newActivityPolicies);

}
