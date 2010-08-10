package model.interfaces.resources;

import model.interfaces.policies.BusinessPolicy;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:52:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BusinessContextResource extends ContextResource {

    //TODO: de scos si lasat in ApplicationN Activity

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    Collection getActivityPolicies();

    boolean hasActivityPolicies();

    Iterator listActivityPolicies();

    void addActivityPolicies(BusinessPolicy newActivityPolicies);

    void removeActivityPolicies(BusinessPolicy oldActivityPolicies);

    void setActivityPolicies(Collection newActivityPolicies);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    float getCpuAllocatedValue();

    boolean hasCpuAllocatedValue();

    void setCpuAllocatedValue(float newCpuAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredValue

    float getCpuRequiredValue();

    boolean hasCpuRequiredValue();

    void setCpuRequiredValue(float newCpuRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue

    float getHddAllocatedValue();

    boolean hasHddAllocatedValue();

    void setHddAllocatedValue(float newHddAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredValue

    float getHddRequiredValue();

    boolean hasHddRequiredValue();

    void setHddRequiredValue(float newHddRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue

    float getMemAllocatedValue();

    boolean hasMemAllocatedValue();

    void setMemAllocatedValue(float newMemAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredValue

    float getMemRequiredValue();

    boolean hasMemRequiredValue();

    void setMemRequiredValue(float newMemRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    Double getPerformanceDegradation();

    boolean hasPerformanceDegradation();

    void setPerformanceDegradation(Double newPerformanceDegradation);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    Double getPerformanceEstimation();

    boolean hasPerformanceEstimation();

    void setPerformanceEstimation(Double newPerformanceEstimation);
}
