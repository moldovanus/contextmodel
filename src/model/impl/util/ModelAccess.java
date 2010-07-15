package model.impl.util;

import model.impl.databaseImpl.DatabaseModelFactory;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 15, 2010
 * Time: 9:58:53 AM
 * Delegates functionality to both OntologyModelFactory and DatabaseModelFactory
 */
public class ModelAccess implements ModelFactory {
    private OntologyModelFactory ontologyModelFactory;
    private DatabaseModelFactory databaseModelFactory;
    public boolean primaryAccessIsOntology = true;

    public ModelAccess(OntologyModelFactory ontologyModelFactory, DatabaseModelFactory databaseModelFactory) {
        this.ontologyModelFactory = ontologyModelFactory;
        this.databaseModelFactory = databaseModelFactory;
    }

    public boolean isPrimaryAccessIsOntology() {
        return primaryAccessIsOntology;
    }

    public void setPrimaryAccessIsOntology(boolean primaryAccessIsOntology) {
        this.primaryAccessIsOntology = primaryAccessIsOntology;
    }

    public void persistEntity(ContextElement entity) {
        ontologyModelFactory.persistEntity(entity);
        databaseModelFactory.persistEntity(entity);
    }

    public DPMAction createDPMAction(String name) {
        DPMAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createDPMAction(name);
            databaseModelFactory.createDPMAction(name);
        } else {
            ontologyModelFactory.createDPMAction(name);
            object = databaseModelFactory.createDPMAction(name);
        }
        return object;
    }

    public DPMAction getDPMAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getDPMAction(name);
        } else {
            return databaseModelFactory.getDPMAction(name);
        }
    }

    public Collection<DPMAction> getAllDPMActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllDPMActionInstances();
        } else {
            return databaseModelFactory.getAllDPMActionInstances();
        }
    }

    public ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(String name) {
        ITComputingResourceAdaptationAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createITComputingResourceAdaptationAction(name);
            databaseModelFactory.createITComputingResourceAdaptationAction(name);
        } else {
            ontologyModelFactory.createITComputingResourceAdaptationAction(name);
            object = databaseModelFactory.createITComputingResourceAdaptationAction(name);
        }
        return object;
    }

    public ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getITComputingResourceAdaptationAction(name);
        } else {
            return databaseModelFactory.getITComputingResourceAdaptationAction(name);
        }
    }

    public Collection<ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllITComputingResourceAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllITComputingResourceAdaptationActionInstances();
        }
    }

    public ContextAction createContextAction(String name) {
        ContextAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createContextAction(name);
            databaseModelFactory.createContextAction(name);
        } else {
            ontologyModelFactory.createContextAction(name);
            object = databaseModelFactory.createContextAction(name);
        }
        return object;
    }

    public ContextAction getContextAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getContextAction(name);
        } else {
            return databaseModelFactory.getContextAction(name);
        }
    }

    public Collection<ContextAction> getAllContextActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllContextActionInstances();
        } else {
            return databaseModelFactory.getAllContextActionInstances();
        }
    }

    public HDDIntensiveActivity createHDDIntensiveActivity(String name) {
        HDDIntensiveActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createHDDIntensiveActivity(name);
            databaseModelFactory.createContextAction(name);
        } else {
            ontologyModelFactory.createHDDIntensiveActivity(name);
            object = databaseModelFactory.createHDDIntensiveActivity(name);
        }
        return object;
    }

    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getHDDIntensiveActivity(name);
        } else {
            return databaseModelFactory.getHDDIntensiveActivity(name);
        }
    }

    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllHDDIntensiveActivityInstances();
        } else {
            return databaseModelFactory.getAllHDDIntensiveActivityInstances();
        }
    }

    public ApplicationActivity createApplicationActivity(String name) {
        ApplicationActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createApplicationActivity(name);
            databaseModelFactory.createContextAction(name);
        } else {
            ontologyModelFactory.createApplicationActivity(name);
            object = databaseModelFactory.createHDDIntensiveActivity(name);
        }
        return object;
    }

    public ApplicationActivity getApplicationActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getApplicationActivity(name);
        } else {
            return databaseModelFactory.getApplicationActivity(name);
        }
    }

    public Collection<ApplicationActivity> getAllApplicationActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllApplicationActivityInstances();
        } else {
            return databaseModelFactory.getAllApplicationActivityInstances();
        }
    }

    public GPI_KPI_Policy createGPI_KPI_Policy(String name) {
        GPI_KPI_Policy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createGPI_KPI_Policy(name);
            databaseModelFactory.createGPI_KPI_Policy(name);
        } else {
            ontologyModelFactory.createGPI_KPI_Policy(name);
            object = databaseModelFactory.createGPI_KPI_Policy(name);
        }
        return object;
    }

    public GPI_KPI_Policy getGPI_KPI_Policy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getGPI_KPI_Policy(name);
        } else {
            return databaseModelFactory.getGPI_KPI_Policy(name);
        }
    }

    public Collection<GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllGPI_KPI_PolicyInstances();
        } else {
            return databaseModelFactory.getAllGPI_KPI_PolicyInstances();
        }
    }

    public ContextPolicy createContextPolicy(String name) {
        ContextPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createContextPolicy(name);
            databaseModelFactory.createContextPolicy(name);
        } else {
            ontologyModelFactory.createContextPolicy(name);
            object = databaseModelFactory.createContextPolicy(name);
        }
        return object;
    }

    public ContextPolicy getContextPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getContextPolicy(name);
        } else {
            return databaseModelFactory.getContextPolicy(name);
        }
    }

    public Collection<ContextPolicy> getAllContextPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllContextPolicyInstances();
        } else {
            return databaseModelFactory.getAllContextPolicyInstances();
        }
    }

    public ServiceCenterITComputingResource createServiceCenterITComputingResource(String name) {
        ServiceCenterITComputingResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createServiceCenterITComputingResource(name);
            databaseModelFactory.createServiceCenterITComputingResource(name);
        } else {
            ontologyModelFactory.createServiceCenterITComputingResource(name);
            object = databaseModelFactory.createServiceCenterITComputingResource(name);
        }
        return object;
    }

    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getServiceCenterITComputingResource(name);
        } else {
            return databaseModelFactory.getServiceCenterITComputingResource(name);
        }
    }

    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllServiceCenterITComputingResourceInstances();
        } else {
            return databaseModelFactory.getAllServiceCenterITComputingResourceInstances();
        }
    }

    public ContextResource getContextResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getContextResource(name);
        } else {
            return databaseModelFactory.getContextResource(name);
        }
    }

    public Collection<ContextResource> getAllContextResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllContextResourceInstances();
        } else {
            return databaseModelFactory.getAllContextResourceInstances();
        }
    }

    public ServiceCenterServer createServiceCenterServer(String name) {
        ServiceCenterServer object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createServiceCenterServer(name);
            databaseModelFactory.createServiceCenterServer(name);
        } else {
            ontologyModelFactory.createServiceCenterServer(name);
            object = databaseModelFactory.createServiceCenterServer(name);
        }
        return object;
    }

    public ServiceCenterServer getServiceCenterServer(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getServiceCenterServer(name);
        } else {
            return databaseModelFactory.getServiceCenterServer(name);
        }
    }

    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllServiceCenterServerInstances();
        } else {
            return databaseModelFactory.getAllServiceCenterServerInstances();
        }
    }

    public ComplexResource createComplexResource(String name) {
        ComplexResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createComplexResource(name);
            databaseModelFactory.createComplexResource(name);
        } else {
            ontologyModelFactory.createComplexResource(name);
            object = databaseModelFactory.createComplexResource(name);
        }
        return object;
    }

    public ComplexResource getComplexResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getComplexResource(name);
        } else {
            return databaseModelFactory.getComplexResource(name);
        }
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllComplexResourceInstances();
        } else {
            return databaseModelFactory.getAllComplexResourceInstances();
        }
    }

    public ContextElement getContextElement(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getContextElement(name);
        } else {
            return databaseModelFactory.getContextElement(name);
        }
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllContextElementInstances();
        } else {
            return databaseModelFactory.getAllContextElementInstances();
        }
    }

    public ServiceCenterITFacilityResource createServiceCenterITFacilityResource(String name) {
        ServiceCenterITFacilityResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createServiceCenterITFacilityResource(name);
            databaseModelFactory.createServiceCenterITFacilityResource(name);
        } else {
            ontologyModelFactory.createServiceCenterITFacilityResource(name);
            object = databaseModelFactory.createServiceCenterITFacilityResource(name);
        }
        return object;
    }

    public ServiceCenterITFacilityResource getServiceCenterITFacilityResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getServiceCenterITFacilityResource(name);
        } else {
            return databaseModelFactory.getServiceCenterITFacilityResource(name);
        }
    }

    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllServiceCenterITFacilityResourceInstances();
        } else {
            return databaseModelFactory.getAllServiceCenterITFacilityResourceInstances();
        }
    }

    public BusinessContextResource createBusinessContextResource(String name) {
        BusinessContextResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createBusinessContextResource(name);
            databaseModelFactory.createBusinessContextResource(name);
        } else {
            ontologyModelFactory.createBusinessContextResource(name);
            object = databaseModelFactory.createBusinessContextResource(name);
        }
        return object;
    }

    public BusinessContextResource getBusinessContextResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getBusinessContextResource(name);
        } else {
            return databaseModelFactory.getBusinessContextResource(name);
        }
    }

    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllBusinessContextResourceInstances();
        } else {
            return databaseModelFactory.getAllBusinessContextResourceInstances();
        }
    }

    public Application createApplication(String name) {
        Application object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createApplication(name);
            databaseModelFactory.createApplication(name);
        } else {
            ontologyModelFactory.createApplication(name);
            object = databaseModelFactory.createApplication(name);
        }
        return object;
    }

    public Application getApplication(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getApplication(name);
        } else {
            return databaseModelFactory.getApplication(name);
        }
    }

    public Collection<Application> getAllApplicationInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllApplicationInstances();
        } else {
            return databaseModelFactory.getAllApplicationInstances();
        }
    }

    public CPUIntensiveActivity createCPUIntensiveActivity(String name) {
        CPUIntensiveActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createCPUIntensiveActivity(name);
            databaseModelFactory.createCPUIntensiveActivity(name);
        } else {
            ontologyModelFactory.createCPUIntensiveActivity(name);
            object = databaseModelFactory.createCPUIntensiveActivity(name);
        }
        return object;
    }

    public CPUIntensiveActivity getCPUIntensiveActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getCPUIntensiveActivity(name);
        } else {
            return databaseModelFactory.getCPUIntensiveActivity(name);
        }
    }

    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllCPUIntensiveActivityInstances();
        } else {
            return databaseModelFactory.getAllCPUIntensiveActivityInstances();
        }
    }

    public ApplicationAdaptationAction createApplicationAdaptationAction(String name) {
        ApplicationAdaptationAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createApplicationAdaptationAction(name);
            databaseModelFactory.createApplicationAdaptationAction(name);
        } else {
            ontologyModelFactory.createApplicationAdaptationAction(name);
            object = databaseModelFactory.createApplicationAdaptationAction(name);
        }
        return object;
    }

    public ApplicationAdaptationAction getApplicationAdaptationAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getApplicationAdaptationAction(name);
        } else {
            return databaseModelFactory.getApplicationAdaptationAction(name);
        }
    }

    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllApplicationAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllApplicationAdaptationActionInstances();
        }
    }

    public ApplicationRedesign createApplicationRedesign(String name) {
        ApplicationRedesign object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createApplicationRedesign(name);
            databaseModelFactory.createApplicationRedesign(name);
        } else {
            ontologyModelFactory.createApplicationRedesign(name);
            object = databaseModelFactory.createApplicationRedesign(name);
        }
        return object;
    }

    public ApplicationRedesign getApplicationRedesign(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getApplicationRedesign(name);
        } else {
            return databaseModelFactory.getApplicationRedesign(name);
        }
    }

    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllApplicationRedesignInstances();
        } else {
            return databaseModelFactory.getAllApplicationRedesignInstances();
        }
    }

    public ITFacilityActiveResource createITFacilityActiveResource(String name) {
        ITFacilityActiveResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createITFacilityActiveResource(name);
            databaseModelFactory.createITFacilityActiveResource(name);
        } else {
            ontologyModelFactory.createITFacilityActiveResource(name);
            object = databaseModelFactory.createITFacilityActiveResource(name);
        }
        return object;
    }

    public ITFacilityActiveResource getITFacilityActiveResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getITFacilityActiveResource(name);
        } else {
            return databaseModelFactory.getITFacilityActiveResource(name);
        }
    }

    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllITFacilityActiveResourceInstances();
        } else {
            return databaseModelFactory.getAllITFacilityActiveResourceInstances();
        }
    }

    public HDD createHDD(String name) {
        HDD object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createHDD(name);
            databaseModelFactory.createHDD(name);
        } else {
            ontologyModelFactory.createHDD(name);
            object = databaseModelFactory.createHDD(name);
        }
        return object;
    }

    public HDD getHDD(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getHDD(name);
        } else {
            return databaseModelFactory.getHDD(name);
        }
    }

    public Collection<HDD> getAllHDDInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllHDDInstances();
        } else {
            return databaseModelFactory.getAllHDDInstances();
        }
    }

    public SimpleResource createSimpleResource(String name) {
        SimpleResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createSimpleResource(name);
            databaseModelFactory.createSimpleResource(name);
        } else {
            ontologyModelFactory.createSimpleResource(name);
            object = databaseModelFactory.createSimpleResource(name);
        }
        return object;
    }

    public SimpleResource getSimpleResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getSimpleResource(name);
        } else {
            return databaseModelFactory.getSimpleResource(name);
        }
    }

    public Collection<SimpleResource> getAllSimpleResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllSimpleResourceInstances();
        } else {
            return databaseModelFactory.getAllSimpleResourceInstances();
        }
    }

    public ITComputingContextPolicy createITComputingContextPolicy(String name) {
        ITComputingContextPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createITComputingContextPolicy(name);
            databaseModelFactory.createITComputingContextPolicy(name);
        } else {
            ontologyModelFactory.createITComputingContextPolicy(name);
            object = databaseModelFactory.createITComputingContextPolicy(name);
        }
        return object;
    }

    public ITComputingContextPolicy getITComputingContextPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getITComputingContextPolicy(name);
        } else {
            return databaseModelFactory.getITComputingContextPolicy(name);
        }
    }

    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllITComputingContextPolicyInstances();
        } else {
            return databaseModelFactory.getAllITComputingContextPolicyInstances();
        }
    }

    public ITFacilityPassiveResource createITFacilityPassiveResource(String name) {
        ITFacilityPassiveResource object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createITFacilityPassiveResource(name);
            databaseModelFactory.createITFacilityPassiveResource(name);
        } else {
            ontologyModelFactory.createITFacilityPassiveResource(name);
            object = databaseModelFactory.createITFacilityPassiveResource(name);
        }
        return object;
    }

    public ITFacilityPassiveResource getITFacilityPassiveResource(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getITFacilityPassiveResource(name);
        } else {
            return databaseModelFactory.getITFacilityPassiveResource(name);
        }
    }

    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllITFacilityPassiveResourceInstances();
        } else {
            return databaseModelFactory.getAllITFacilityPassiveResourceInstances();
        }
    }

    public MigrateActivity createMigrateActivity(String name) {
        MigrateActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createMigrateActivity(name);
            databaseModelFactory.createMigrateActivity(name);
        } else {
            ontologyModelFactory.createMigrateActivity(name);
            object = databaseModelFactory.createMigrateActivity(name);
        }
        return object;
    }

    public MigrateActivity getMigrateActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getMigrateActivity(name);
        } else {
            return databaseModelFactory.getMigrateActivity(name);
        }
    }

    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllMigrateActivityInstances();
        } else {
            return databaseModelFactory.getAllMigrateActivityInstances();
        }
    }

    public ConsolidationAction createConsolidationAction(String name) {
        ConsolidationAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createConsolidationAction(name);
            databaseModelFactory.createConsolidationAction(name);
        } else {
            ontologyModelFactory.createConsolidationAction(name);
            object = databaseModelFactory.createConsolidationAction(name);
        }
        return object;
    }

    public ConsolidationAction getConsolidationAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getConsolidationAction(name);
        } else {
            return databaseModelFactory.getConsolidationAction(name);
        }
    }

    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllConsolidationActionInstances();
        } else {
            return databaseModelFactory.getAllConsolidationActionInstances();
        }
    }

    public Sensor createSensor(String name) {
        Sensor object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createSensor(name);
            databaseModelFactory.createSensor(name);
        } else {
            ontologyModelFactory.createSensor(name);
            object = databaseModelFactory.createSensor(name);
        }
        return object;
    }

    public Sensor getSensor(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getSensor(name);
        } else {
            return databaseModelFactory.getSensor(name);
        }
    }

    public Collection<Sensor> getAllSensorInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllSensorInstances();
        } else {
            return databaseModelFactory.getAllSensorInstances();
        }
    }

    public Actuator createActuator(String name) {
        Actuator object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createActuator(name);
            databaseModelFactory.createActuator(name);
        } else {
            ontologyModelFactory.createActuator(name);
            object = databaseModelFactory.createActuator(name);
        }
        return object;
    }

    public Actuator getActuator(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getActuator(name);
        } else {
            return databaseModelFactory.getActuator(name);
        }
    }

    public Collection<Actuator> getAllActuatorInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllActuatorInstances();
        } else {
            return databaseModelFactory.getAllActuatorInstances();
        }
    }

    public BusinessPolicy createBusinessPolicy(String name) {
        BusinessPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createBusinessPolicy(name);
            databaseModelFactory.createBusinessPolicy(name);
        } else {
            ontologyModelFactory.createBusinessPolicy(name);
            object = databaseModelFactory.createBusinessPolicy(name);
        }
        return object;
    }

    public BusinessPolicy getBusinessPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getBusinessPolicy(name);
        } else {
            return databaseModelFactory.getBusinessPolicy(name);
        }
    }

    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllBusinessPolicyInstances();
        } else {
            return databaseModelFactory.getAllBusinessPolicyInstances();
        }
    }

    public MEM createMEM(String name) {
        MEM object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createMEM(name);
            databaseModelFactory.createMEM(name);
        } else {
            ontologyModelFactory.createMEM(name);
            object = databaseModelFactory.createMEM(name);
        }
        return object;
    }

    public MEM getMEM(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getMEM(name);
        } else {
            return databaseModelFactory.getMEM(name);
        }
    }

    public Collection<MEM> getAllMEMInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllMEMInstances();
        } else {
            return databaseModelFactory.getAllMEMInstances();
        }
    }

    public ExternalStorage createExternalStorage(String name) {
        ExternalStorage object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createExternalStorage(name);
            databaseModelFactory.createExternalStorage(name);
        } else {
            ontologyModelFactory.createExternalStorage(name);
            object = databaseModelFactory.createExternalStorage(name);
        }
        return object;
    }

    public ExternalStorage getExternalStorage(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getExternalStorage(name);
        } else {
            return databaseModelFactory.getExternalStorage(name);
        }
    }

    public Collection<ExternalStorage> getAllExternalStorageInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllExternalStorageInstances();
        } else {
            return databaseModelFactory.getAllExternalStorageInstances();
        }
    }

    public CPU createCPU(String name) {
        CPU object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createCPU(name);
            databaseModelFactory.createCPU(name);
        } else {
            ontologyModelFactory.createCPU(name);
            object = databaseModelFactory.createCPU(name);
        }
        return object;
    }

    public CPU getCPU(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getCPU(name);
        } else {
            return databaseModelFactory.getCPU(name);
        }
    }

    public Collection<CPU> getAllCPUInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllCPUInstances();
        } else {
            return databaseModelFactory.getAllCPUInstances();
        }
    }

    public SLAPolicy createSLAPolicy(String name) {
        SLAPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createSLAPolicy(name);
            databaseModelFactory.createSLAPolicy(name);
        } else {
            ontologyModelFactory.createSLAPolicy(name);
            object = databaseModelFactory.createSLAPolicy(name);
        }
        return object;
    }

    public SLAPolicy getSLAPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getSLAPolicy(name);
        } else {
            return databaseModelFactory.getSLAPolicy(name);
        }
    }

    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllSLAPolicyInstances();
        } else {
            return databaseModelFactory.getAllSLAPolicyInstances();
        }
    }

    public MEMIntensiveActivity createMEMIntensiveActivity(String name) {
        MEMIntensiveActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createMEMIntensiveActivity(name);
            databaseModelFactory.createMEMIntensiveActivity(name);
        } else {
            ontologyModelFactory.createMEMIntensiveActivity(name);
            object = databaseModelFactory.createMEMIntensiveActivity(name);
        }
        return object;
    }

    public MEMIntensiveActivity getMEMIntensiveActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getMEMIntensiveActivity(name);
        } else {
            return databaseModelFactory.getMEMIntensiveActivity(name);
        }
    }

    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllMEMIntensiveActivityInstances();
        } else {
            return databaseModelFactory.getAllMEMIntensiveActivityInstances();
        }
    }

    public Facility createFacility(String name) {
        Facility object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createFacility(name);
            databaseModelFactory.createFacility(name);
        } else {
            ontologyModelFactory.createFacility(name);
            object = databaseModelFactory.createFacility(name);
        }
        return object;
    }

    public Facility getFacility(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getFacility(name);
        } else {
            return databaseModelFactory.getFacility(name);
        }
    }

    public Collection<Facility> getAllFacilityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllFacilityInstances();
        } else {
            return databaseModelFactory.getAllFacilityInstances();
        }
    }

    public QoSPolicy createQoSPolicy(String name) {
        QoSPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createQoSPolicy(name);
            databaseModelFactory.createQoSPolicy(name);
        } else {
            ontologyModelFactory.createQoSPolicy(name);
            object = databaseModelFactory.createQoSPolicy(name);
        }
        return object;
    }

    public QoSPolicy getQoSPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getQoSPolicy(name);
        } else {
            return databaseModelFactory.getQoSPolicy(name);
        }
    }

    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllQoSPolicyInstances();
        } else {
            return databaseModelFactory.getAllQoSPolicyInstances();
        }
    }

    public DeployActivity createDeployActivity(String name) {
        DeployActivity object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createDeployActivity(name);
            databaseModelFactory.createDeployActivity(name);
        } else {
            ontologyModelFactory.createDeployActivity(name);
            object = databaseModelFactory.createDeployActivity(name);
        }
        return object;
    }

    public DeployActivity getDeployActivity(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getDeployActivity(name);
        } else {
            return databaseModelFactory.getDeployActivity(name);
        }
    }

    public Collection<DeployActivity> getAllDeployActivityInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllDeployActivityInstances();
        } else {
            return databaseModelFactory.getAllDeployActivityInstances();
        }
    }

    public EnvironmentPolicy createEnvironmentPolicy(String name) {
        EnvironmentPolicy object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createEnvironmentPolicy(name);
            databaseModelFactory.createEnvironmentPolicy(name);
        } else {
            ontologyModelFactory.createEnvironmentPolicy(name);
            object = databaseModelFactory.createEnvironmentPolicy(name);
        }
        return object;
    }

    public EnvironmentPolicy getEnvironmentPolicy(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getEnvironmentPolicy(name);
        } else {
            return databaseModelFactory.getEnvironmentPolicy(name);
        }
    }

    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllEnvironmentPolicyInstances();
        } else {
            return databaseModelFactory.getAllEnvironmentPolicyInstances();
        }
    }

    public ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(String name) {
        ITFacilityResourceAdaptationAction object;
        if (primaryAccessIsOntology) {
            object = ontologyModelFactory.createITFacilityResourceAdaptationAction(name);
            databaseModelFactory.createITFacilityResourceAdaptationAction(name);
        } else {
            ontologyModelFactory.createITFacilityResourceAdaptationAction(name);
            object = databaseModelFactory.createITFacilityResourceAdaptationAction(name);
        }
        return object;
    }

    public ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(String name) {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getITFacilityResourceAdaptationAction(name);
        } else {
            return databaseModelFactory.getITFacilityResourceAdaptationAction(name);
        }
    }

    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
        if (primaryAccessIsOntology) {
            return ontologyModelFactory.getAllITFacilityResourceAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllITFacilityResourceAdaptationActionInstances();
        }
    }
}
