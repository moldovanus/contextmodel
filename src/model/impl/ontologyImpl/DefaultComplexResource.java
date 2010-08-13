package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ComplexResource
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultComplexResource extends DefaultServiceCenterITComputingResource
        implements ComplexResource {

    public DefaultComplexResource(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }

    public DefaultComplexResource() {
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


    public void setCPUWeight(float newCPUWeight) {
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


    public void setHDDWeight(float newHDDWeight) {
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


    public void setMEMWeight(float newMEMWeight) {
        setPropertyValue(getMEMWeightProperty(), new java.lang.Float(newMEMWeight));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuResources

    public Collection getCpuResources() {
        return getPropertyValuesAs(getCpuResourcesProperty(), CPU.class);
    }


    public RDFProperty getCpuResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuResources() {
        return getPropertyValueCount(getCpuResourcesProperty()) > 0;
    }


    public Iterator listCpuResources() {
        return listPropertyValuesAs(getCpuResourcesProperty(), CPU.class);
    }


    public void addCpuResources(CPU newCpuResources) {
        addPropertyValue(getCpuResourcesProperty(), newCpuResources);
    }


    public void removeCpuResources(CPU oldCpuResources) {
        removePropertyValue(getCpuResourcesProperty(), oldCpuResources);
    }


    public void setCpuResources(Collection newCpuResources) {
        setPropertyValues(getCpuResourcesProperty(), newCpuResources);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddResources

    public Collection getHddResources() {
        return getPropertyValuesAs(getHddResourcesProperty(), HDD.class);
    }


    public RDFProperty getHddResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHddResources() {
        return getPropertyValueCount(getHddResourcesProperty()) > 0;
    }


    public Iterator listHddResources() {
        return listPropertyValuesAs(getHddResourcesProperty(), HDD.class);
    }


    public void addHddResources(HDD newHddResources) {
        addPropertyValue(getHddResourcesProperty(), newHddResources);
    }


    public void removeHddResources(HDD oldHddResources) {
        removePropertyValue(getHddResourcesProperty(), oldHddResources);
    }


    public void setHddResources(Collection newHddResources) {
        setPropertyValues(getHddResourcesProperty(), newHddResources);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#isActive

    public boolean getIsActive() {
        return getPropertyValueLiteral(getIsActiveProperty()).getBoolean();
    }


    public RDFProperty getIsActiveProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#isActive";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsActive() {
        return getPropertyValueCount(getIsActiveProperty()) > 0;
    }


    public void setIsActive(boolean newIsActive) {
        setPropertyValue(getIsActiveProperty(), new java.lang.Boolean(newIsActive));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memResources

    public Collection getMemResources() {
        return getPropertyValuesAs(getMemResourcesProperty(), MEM.class);
    }


    public RDFProperty getMemResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMemResources() {
        return getPropertyValueCount(getMemResourcesProperty()) > 0;
    }


    public Iterator listMemResources() {
        return listPropertyValuesAs(getMemResourcesProperty(), MEM.class);
    }


    public void addMemResources(MEM newMemResources) {
        addPropertyValue(getMemResourcesProperty(), newMemResources);
    }


    public void removeMemResources(MEM oldMemResources) {
        removePropertyValue(getMemResourcesProperty(), oldMemResources);
    }


    public void setMemResources(Collection newMemResources) {
        setPropertyValues(getMemResourcesProperty(), newMemResources);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceWorkloadProperty

    public List<String> getResourceWorkloadProperty() {
        return new ArrayList<String>(getPropertyValues(getResourceWorkloadPropertyProperty()));
    }

    public RDFProperty getResourceWorkloadPropertyProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceWorkloadProperty";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceWorkloadProperty() {
        return getPropertyValueCount(getResourceWorkloadPropertyProperty()) > 0;
    }


    public Iterator listResourceWorkloadProperty() {
        return listPropertyValues(getResourceWorkloadPropertyProperty());
    }


    public void addResourceWorkloadProperty(String newResourceWorkloadProperty) {
        addPropertyValue(getResourceWorkloadPropertyProperty(), newResourceWorkloadProperty);
    }


    public void removeResourceWorkloadProperty(String oldResourceWorkloadProperty) {
        removePropertyValue(getResourceWorkloadPropertyProperty(), oldResourceWorkloadProperty);
    }


    public void setResourceWorkloadProperty(List<String> newResourceWorkloadProperty) {
        setResourceWorkloadProperty((Collection) newResourceWorkloadProperty);
    }

    public void setResourceWorkloadProperty(Collection newResourceWorkloadProperty) {
        setPropertyValues(getResourceWorkloadPropertyProperty(), newResourceWorkloadProperty);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#simpleResources

    public List<ServiceCenterITComputingResource> getResources() {
        return new ArrayList<ServiceCenterITComputingResource>(getSimpleResources());
    }

    public Collection getSimpleResources() {
        return getPropertyValuesAs(getSimpleResourcesProperty(), SimpleResource.class);
    }

    public RDFProperty getSimpleResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#simpleResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasSimpleResources() {
        return getPropertyValueCount(getSimpleResourcesProperty()) > 0;
    }


    public Iterator listSimpleResources() {
        return listPropertyValuesAs(getSimpleResourcesProperty(), SimpleResource.class);
    }


    public void addSimpleResources(SimpleResource newSimpleResources) {
        addPropertyValue(getSimpleResourcesProperty(), newSimpleResources);
    }


    public void removeSimpleResources(SimpleResource oldSimpleResources) {
        removePropertyValue(getSimpleResourcesProperty(), oldSimpleResources);
    }


    public void setResources(List<ServiceCenterITComputingResource> newSimpleResources) {
        setSimpleResources(newSimpleResources);
    }

    public void setSimpleResources(Collection newSimpleResources) {
        setPropertyValues(getSimpleResourcesProperty(), newSimpleResources);
    }

    public boolean hasResourcesFor(ApplicationActivity task) {


        Collection<CPU> cpus = this.getCpuResources();
        for (CPU cpu : cpus) {
            Collection cores = cpu.getAssociatedCores();
            double requestedCores = task.getNumberOfCoresRequiredValue();
            if (cores.size() < requestedCores) {
                return false;
            }
            for (Object coreInst : cores) {

                Core core = (Core) coreInst;
                if (core.getCurrentWorkLoad() + task.getCpuRequiredMaxValue() > core.getMaximumWorkLoad() - (core.getMaximumWorkLoad() - core.getOptimalWorkLoad()) / 2.0) {
                    continue;
                } else {
                    requestedCores--;
                }
            }

            if (requestedCores > 0) {
                return false;
            }
        }
        Collection<MEM> memories = this.getMemResources();
        for (MEM mem : memories) {

            if (mem.getCurrentWorkLoad() + task.getMemRequiredMaxValue() > mem.getMaximumWorkLoad()) {
                return false;
            }
        }
        Collection<HDD> hdds = this.getMemResources();
        for (HDD hdd : hdds) {

            if (hdd.getCurrentWorkLoad() + task.getHddRequiredMaxValue() > hdd.getMaximumWorkLoad()) {
                return false;
            }
        }


        return true;
    }


}