package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ApplicationActivity
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultApplicationActivity extends DefaultBusinessContextResource
        implements ApplicationActivity {

    public DefaultApplicationActivity(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultApplicationActivity() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight

    public Double getCPUWeight() {
        return getPropertyValueLiteral(getCPUWeightProperty()).getDouble();
    }


    public RDFProperty getCPUWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCPUWeight() {
        return getPropertyValueCount(getCPUWeightProperty()) > 0;
    }


    public void setCPUWeight(Double newCPUWeight) {
        setPropertyValue(getCPUWeightProperty(), new java.lang.Float(newCPUWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight

    public Double getHDDWeight() {
        return getPropertyValueLiteral(getHDDWeightProperty()).getDouble();
    }


    public RDFProperty getHDDWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHDDWeight() {
        return getPropertyValueCount(getHDDWeightProperty()) > 0;
    }


    public void setHDDWeight(Double newHDDWeight) {
        setPropertyValue(getHDDWeightProperty(), new java.lang.Float(newHDDWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight

    public Double getMEMWeight() {
        return getPropertyValueLiteral(getMEMWeightProperty()).getDouble();
    }


    public RDFProperty getMEMWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMEMWeight() {
        return getPropertyValueCount(getMEMWeightProperty()) > 0;
    }


    public void setMEMWeight(Double newMEMWeight) {
        setPropertyValue(getMEMWeightProperty(), new java.lang.Float(newMEMWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies

    public Collection getActivityPolicies() {
        return getPropertyValuesAs(getActivityPoliciesProperty(), BusinessPolicy.class);
    }


    public RDFProperty getActivityPoliciesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activityPolicies";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasActivityPolicies() {
        return getPropertyValueCount(getActivityPoliciesProperty()) > 0;
    }


    public Iterator listActivityPolicies() {
        return listPropertyValuesAs(getActivityPoliciesProperty(), BusinessPolicy.class);
    }


    public void addActivityPolicies(BusinessPolicy newActivityPolicies) {
        addPropertyValue(getActivityPoliciesProperty(), newActivityPolicies);
    }


    public void removeActivityPolicies(BusinessPolicy oldActivityPolicies) {
        removePropertyValue(getActivityPoliciesProperty(), oldActivityPolicies);
    }


    public void setActivityPolicies(Collection newActivityPolicies) {
        setPropertyValues(getActivityPoliciesProperty(), newActivityPolicies);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    public Double getCPUAllocatedValue() {
        return getPropertyValueLiteral(getCpuAllocatedValueProperty()).getDouble();
    }


    public RDFProperty getCpuAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuAllocatedValue() {
        return getPropertyValueCount(getCpuAllocatedValueProperty()) > 0;
    }


    public void setCPUAllocatedValue(Double newCpuAllocatedValue) {
        setPropertyValue(getCpuAllocatedValueProperty(), new java.lang.Float(newCpuAllocatedValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMaxValue

    public float getCpuRequiredMaxValue() {
        return getPropertyValueLiteral(getCpuRequiredMaxValueProperty()).getFloat();
    }


    public RDFProperty getCpuRequiredMaxValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMaxValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuRequiredMaxValue() {
        return getPropertyValueCount(getCpuRequiredMaxValueProperty()) > 0;
    }


    public void setCpuRequiredMaxValue(float newCpuRequiredMaxValue) {
        setPropertyValue(getCpuRequiredMaxValueProperty(), new java.lang.Float(newCpuRequiredMaxValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMinValue

    public float getCpuRequiredMinValue() {
        return getPropertyValueLiteral(getCpuRequiredMinValueProperty()).getFloat();
    }


    public RDFProperty getCpuRequiredMinValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMinValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuRequiredMinValue() {
        return getPropertyValueCount(getCpuRequiredMinValueProperty()) > 0;
    }


    public void setCpuRequiredMinValue(float newCpuRequiredMinValue) {
        setPropertyValue(getCpuRequiredMinValueProperty(), new java.lang.Float(newCpuRequiredMinValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue

    public Double getHDDAllocatedValue() {
        return getPropertyValueLiteral(getHddAllocatedValueProperty()).getDouble();
    }


    public RDFProperty getHddAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHddAllocatedValue() {
        return getPropertyValueCount(getHddAllocatedValueProperty()) > 0;
    }


    public void setHDDAllocatedValue(Double newHddAllocatedValue) {
        setPropertyValue(getHddAllocatedValueProperty(), new java.lang.Float(newHddAllocatedValue));
    }

    public void setHddAllocatedValue(float newHddAllocatedValue) {
        setPropertyValue(getHddAllocatedValueProperty(), new java.lang.Float(newHddAllocatedValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMaxValue

    public float getHddRequiredMaxValue() {
        return getPropertyValueLiteral(getHddRequiredMaxValueProperty()).getFloat();
    }


    public RDFProperty getHddRequiredMaxValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMaxValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHddRequiredMaxValue() {
        return getPropertyValueCount(getHddRequiredMaxValueProperty()) > 0;
    }


    public void setHddRequiredMaxValue(float newHddRequiredMaxValue) {
        setPropertyValue(getHddRequiredMaxValueProperty(), new java.lang.Float(newHddRequiredMaxValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMinValue

    public float getHddRequiredMinValue() {
        return getPropertyValueLiteral(getHddRequiredMinValueProperty()).getFloat();
    }


    public RDFProperty getHddRequiredMinValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMinValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHddRequiredMinValue() {
        return getPropertyValueCount(getHddRequiredMinValueProperty()) > 0;
    }


    public void setHddRequiredMinValue(float newHddRequiredMinValue) {
        setPropertyValue(getHddRequiredMinValueProperty(), new java.lang.Float(newHddRequiredMinValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue

    public Double getMEMAllocatedValue() {
        return getPropertyValueLiteral(getMemAllocatedValueProperty()).getDouble();
    }

    public RDFProperty getMemAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMemAllocatedValue() {
        return getPropertyValueCount(getMemAllocatedValueProperty()) > 0;
    }


    public void setMEMAllocatedValue(Double newMemAllocatedValue) {
        setPropertyValue(getMemAllocatedValueProperty(), new java.lang.Float(newMemAllocatedValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredValue

    public Double getMEMRequiredValue() {
        return getPropertyValueLiteral(getMemRequiredValueProperty()).getDouble();
    }


    public RDFProperty getMemRequiredValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMemRequiredMaxValue() {
        return getPropertyValueCount(getMemRequiredMaxValueProperty()) > 0;
    }


    public void setMemRequiredMaxValue(float newMemRequiredMaxValue) {
        setPropertyValue(getMemRequiredMaxValueProperty(), new java.lang.Float(newMemRequiredMaxValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMinValue

    public float getMemRequiredMinValue() {
        return getPropertyValueLiteral(getMemRequiredMinValueProperty()).getFloat();
    }


    public RDFProperty getMemRequiredMinValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMinValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMemRequiredMinValue() {
        return getPropertyValueCount(getMemRequiredMinValueProperty()) > 0;
    }


    public void setMemRequiredMinValue(float newMemRequiredMinValue) {
        setPropertyValue(getMemRequiredMinValueProperty(), new java.lang.Float(newMemRequiredMinValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    public Collection getPartOf() {
        return getPropertyValuesAs(getPartOfProperty(), ContextElement.class);
    }


    public RDFProperty getPartOfProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPartOf() {
        return getPropertyValueCount(getPartOfProperty()) > 0;
    }


    public Iterator listPartOf() {
        return listPropertyValuesAs(getPartOfProperty(), ContextElement.class);
    }


    public void addPartOf(ContextElement newPartOf) {
        addPropertyValue(getPartOfProperty(), newPartOf);
    }


    public void removePartOf(ContextElement oldPartOf) {
        removePropertyValue(getPartOfProperty(), oldPartOf);
    }


    public void setPartOf(Collection newPartOf) {
        setPropertyValues(getPartOfProperty(), newPartOf);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    public Double getPerformanceDegradation() {
        return getPropertyValueLiteral(getPerformanceDegradationProperty()).getDouble();
    }


    public RDFProperty getPerformanceDegradationProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformanceDegradation() {
        return getPropertyValueCount(getPerformanceDegradationProperty()) > 0;
    }


    public void setPerformanceDegradation(Double newPerformanceDegradation) {
        setPropertyValue(getPerformanceDegradationProperty(), new java.lang.Float(newPerformanceDegradation));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    public Double getPerformanceEstimation() {
        return getPropertyValueLiteral(getPerformanceEstimationProperty()).getDouble();
    }


    public RDFProperty getPerformanceEstimationProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformanceEstimation() {
        return getPropertyValueCount(getPerformanceEstimationProperty()) > 0;
    }


    public void setPerformanceEstimation(Double newPerformanceEstimation) {
        setPropertyValue(getPerformanceEstimationProperty(), new java.lang.Float(newPerformanceEstimation));
    }
}
