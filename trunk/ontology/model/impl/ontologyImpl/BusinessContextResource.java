package model.impl.ontologyImpl;

import edu.stanford.smi.protegex.owl.model.RDFProperty;

import java.util.Collection;
import java.util.Iterator;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#BusinessContextResource
 *
 * @version generated on Fri Jul 09 18:31:36 GMT 2010
 */
public interface BusinessContextResource extends ContextResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    Collection getActivityPolicies();

    RDFProperty getActivityPoliciesProperty();

    boolean hasActivityPolicies();

    Iterator listActivityPolicies();

    void addActivityPolicies(BusinessPolicy newActivityPolicies);

    void removeActivityPolicies(BusinessPolicy oldActivityPolicies);

    void setActivityPolicies(Collection newActivityPolicies);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    float getCpuAllocatedValue();

    RDFProperty getCpuAllocatedValueProperty();

    boolean hasCpuAllocatedValue();

    void setCpuAllocatedValue(float newCpuAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredValue

    float getCpuRequiredValue();

    RDFProperty getCpuRequiredValueProperty();

    boolean hasCpuRequiredValue();

    void setCpuRequiredValue(float newCpuRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue

    float getHddAllocatedValue();

    RDFProperty getHddAllocatedValueProperty();

    boolean hasHddAllocatedValue();

    void setHddAllocatedValue(float newHddAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredValue

    float getHddRequiredValue();

    RDFProperty getHddRequiredValueProperty();

    boolean hasHddRequiredValue();

    void setHddRequiredValue(float newHddRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue

    float getMemAllocatedValue();

    RDFProperty getMemAllocatedValueProperty();

    boolean hasMemAllocatedValue();

    void setMemAllocatedValue(float newMemAllocatedValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredValue

    float getMemRequiredValue();

    RDFProperty getMemRequiredValueProperty();

    boolean hasMemRequiredValue();

    void setMemRequiredValue(float newMemRequiredValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    float getPerformanceDegradation();

    RDFProperty getPerformanceDegradationProperty();

    boolean hasPerformanceDegradation();

    void setPerformanceDegradation(float newPerformanceDegradation);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    float getPerformanceEstimation();

    RDFProperty getPerformanceEstimationProperty();

    boolean hasPerformanceEstimation();

    void setPerformanceEstimation(float newPerformanceEstimation);
}
