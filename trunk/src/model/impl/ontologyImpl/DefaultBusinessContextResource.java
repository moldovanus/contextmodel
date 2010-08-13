package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.resources.BusinessContextResource;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#BusinessContextResource
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultBusinessContextResource extends DefaultContextResource
        implements BusinessContextResource {

    public DefaultBusinessContextResource(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultBusinessContextResource() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight

    public float getCPUWeight() {
        return getPropertyValueLiteral(getCPUWeightProperty()).getFloat();
    }


    public RDFProperty getCPUWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPUWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCPUWeight() {
        return getPropertyValueCount(getCPUWeightProperty()) > 0;
    }


    public void setCpuWeight(float newCPUWeight) {
        setPropertyValue(getCPUWeightProperty(), new java.lang.Float(newCPUWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight

    public float getHDDWeight() {
        return getPropertyValueLiteral(getHDDWeightProperty()).getFloat();
    }


    public RDFProperty getHDDWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDDWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHDDWeight() {
        return getPropertyValueCount(getHDDWeightProperty()) > 0;
    }


    public void setHddWeight(float newHDDWeight) {
        setPropertyValue(getHDDWeightProperty(), new java.lang.Float(newHDDWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight

    public float getMEMWeight() {
        return getPropertyValueLiteral(getMEMWeightProperty()).getFloat();
    }


    public RDFProperty getMEMWeightProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEMWeight";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMEMWeight() {
        return getPropertyValueCount(getMEMWeightProperty()) > 0;
    }


    public void setMemWeight(float newMEMWeight) {
        setPropertyValue(getMEMWeightProperty(), new java.lang.Float(newMEMWeight));
    }

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue

    public float getCpuAllocatedValue() {
        return getPropertyValueLiteral(getCpuAllocatedValueProperty()).getFloat();
    }


    public RDFProperty getCpuAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuAllocatedValue() {
        return getPropertyValueCount(getCpuAllocatedValueProperty()) > 0;
    }


    public void setCpuAllocatedValue(float newCpuAllocatedValue) {
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

    public float getHddAllocatedValue() {
        return getPropertyValueLiteral(getHddAllocatedValueProperty()).getFloat();
    }


    public RDFProperty getHddAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHddAllocatedValue() {
        return getPropertyValueCount(getHddAllocatedValueProperty()) > 0;
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

    public float getMemAllocatedValue() {
        return getPropertyValueLiteral(getMemAllocatedValueProperty()).getFloat();
    }


    public RDFProperty getMemAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMemAllocatedValue() {
        return getPropertyValueCount(getMemAllocatedValueProperty()) > 0;
    }


    public void setMemAllocatedValue(float newMemAllocatedValue) {
        setPropertyValue(getMemAllocatedValueProperty(), new java.lang.Float(newMemAllocatedValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMaxValue

    public float getMemRequiredMaxValue() {
        return getPropertyValueLiteral(getMemRequiredMaxValueProperty()).getFloat();
    }


    public RDFProperty getMemRequiredMaxValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMaxValue";
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


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresAllocatedValue

    public float getNumberOfCoresAllocatedValue() {
        return getPropertyValueLiteral(getNumberOfCoresAllocatedValueProperty()).getFloat();
    }


    public RDFProperty getNumberOfCoresAllocatedValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresAllocatedValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasNumberOfCoresAllocatedValue() {
        return getPropertyValueCount(getNumberOfCoresAllocatedValueProperty()) > 0;
    }


    public void setNumberOfCoresAllocatedValue(float newNumberOfCoresAllocatedValue) {
        setPropertyValue(getNumberOfCoresAllocatedValueProperty(), new java.lang.Float(newNumberOfCoresAllocatedValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresRequiredValue

    public float getNumberOfCoresRequiredValue() {
        return getPropertyValueLiteral(getNumberOfCoresRequiredValueProperty()).getFloat();
    }


    public RDFProperty getNumberOfCoresRequiredValueProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#numberOfCoresRequiredValue";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasNumberOfCoresRequiredValue() {
        return getPropertyValueCount(getNumberOfCoresRequiredValueProperty()) > 0;
    }


    public void setNumberOfCoresRequiredValue(float newNumberOfCoresRequiredValue) {
        setPropertyValue(getNumberOfCoresRequiredValueProperty(), new java.lang.Float(newNumberOfCoresRequiredValue));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation

    public float getPerformanceDegradation() {
        return getPropertyValueLiteral(getPerformanceDegradationProperty()).getFloat();
    }


    public RDFProperty getPerformanceDegradationProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceDegradation";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformanceDegradation() {
        return getPropertyValueCount(getPerformanceDegradationProperty()) > 0;
    }


    public void setPerformanceDegradation(float newPerformanceDegradation) {
        setPropertyValue(getPerformanceDegradationProperty(), new java.lang.Float(newPerformanceDegradation));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation

    public float getPerformanceEstimation() {
        return getPropertyValueLiteral(getPerformanceEstimationProperty()).getFloat();
    }


    public RDFProperty getPerformanceEstimationProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#performanceEstimation";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformanceEstimation() {
        return getPropertyValueCount(getPerformanceEstimationProperty()) > 0;
    }


    public void setPerformanceEstimation(float newPerformanceEstimation) {
        setPropertyValue(getPerformanceEstimationProperty(), new java.lang.Float(newPerformanceEstimation));
    }
}
