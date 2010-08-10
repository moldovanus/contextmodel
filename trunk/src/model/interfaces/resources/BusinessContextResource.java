package model.interfaces.resources;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:52:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BusinessContextResource extends ContextResource {


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    Collection getActivityPolicies();

    void setActivityPolicies(Collection newActivityPolicies);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    Double getCpuAllocatedValue();

    void setCpuAllocatedValue(Double newCpuAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredValue

    Double getCpuRequiredValue();

    void setCpuRequiredValue(Double newCpuRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue

    Double getHddAllocatedValue();

    void setHddAllocatedValue(Double newHddAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredValue

    Double getHddRequiredValue();

    void setHddRequiredValue(Double newHddRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue

    Double getMemAllocatedValue();

    boolean hasMemAllocatedValue();

    void setMemAllocatedValue(Double newMemAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredValue

    Double getMemRequiredValue();

    void setMemRequiredValue(Double newMemRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    Double getPerformanceDegradation();


    void setPerformanceDegradation(Double newPerformanceDegradation);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    Double getPerformanceEstimation();


    void setPerformanceEstimation(Double newPerformanceEstimation);
}
