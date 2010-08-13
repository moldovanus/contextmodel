package model.impl.prevailerImpl;
//
//import model.impl.databaseImpl.ontology.actions.ApplicationAdaptationActionImpl;
//import model.impl.databaseImpl.ontology.actions.ApplicationRedesignImpl;
//import model.impl.databaseImpl.ontology.actions.DPMActionImpl;
//import model.impl.databaseImpl.ontology.actions.ITComputingResourcesAdaptationActionImpl;
//import model.impl.databaseImpl.ontology.policies.ContextPolicyImpl;
//import model.impl.databaseImpl.ontology.policies.GPI_KPI_PolicyImpl;
//import model.impl.databaseImpl.ontology.resources.*;
//import model.impl.databaseImpl.ontology.resources.applications.ApplicationImpl;
//import model.impl.databaseImpl.ontology.resources.applications.CPUIntensiveActivityImpl;
//import model.impl.databaseImpl.ontology.resources.applications.HDDIntensiveActivityImpl;

import model.impl.util.ModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 21, 2010
 * Time: 10:21:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrevaylerDatabaseContainer implements ModelFactory, Serializable {
//    private Map<String, ContextElement> elements;
//    private static final PrevaylerDatabaseContainer containerInstance = new PrevaylerDatabaseContainer();
//
//    private <T> T getEntity(Class<? extends T> entityType, String name) {
//        return (T) elements.get(name);
//    }
//
//    private PrevaylerDatabaseContainer() {
//        elements = new LinkedHashMap<String, ContextElement>();
//    }
//
//    public static PrevaylerDatabaseContainer getContainerInstance() {
//        return containerInstance;
//    }
//
//    public void removeEntity(ContextElement element) {
//        elements.remove(element.getName());
//    }
//
//    public void persistEntity(ContextElement entity) {
//        String key = entity.getName();
//        if (elements.containsKey(key)) {
//            throw new UnsupportedOperationException("Entity name \"" + key + "\" not unique");
//        }
//        elements.put(key, entity);
//    }
//
//    public DPMAction createDPMAction(String name) {
//        ContextElement element = new DPMActionImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (DPMAction) element;
//    }
//
//    public DPMAction getDPMAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<DPMAction> getAllDPMActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(String name) {
//        ContextElement element = new ITComputingResourcesAdaptationActionImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ITComputingResourceAdaptationAction) element;
//    }
//
//    public ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ContextAction createContextAction(String name) {
////        ContextElement element = new ContextActionImpl();
////        element.setName(name);
////        persistEntity(element);
////        return (ContextAction) element;
//        return null;
//    }
//
//    public ContextAction getContextAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ContextAction> getAllContextActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public HDDIntensiveActivity createHDDIntensiveActivity(String name) {
//        ContextElement element = new HDDIntensiveActivityImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (HDDIntensiveActivity) element;
//    }
//
//    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
//        return getEntity(HDDIntensiveActivity.class, name);
//    }
//
//    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
//        Collection<HDDIntensiveActivity> collection = new ArrayList<HDDIntensiveActivity>();
//        for (ContextElement element : elements.values()) {
//            if (element instanceof HDDIntensiveActivity) {
//                collection.add((HDDIntensiveActivity) element);
//            }
//        }
//        return collection;
//    }
//
//    public ApplicationActivity createApplicationActivity(String name) {
//        return null;
//    }
//
//    public ApplicationActivity getApplicationActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ApplicationActivity> getAllApplicationActivityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public GPI_KPI_Policy createGPI_KPI_Policy(String name) {
//        ContextElement element = new GPI_KPI_PolicyImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (GPI_KPI_Policy) element;
//    }
//
//    public GPI_KPI_Policy getGPI_KPI_Policy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ContextPolicy createContextPolicy(String name) {
//        ContextElement element = new ContextPolicyImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ContextPolicy) element;
//    }
//
//    public ContextPolicy getContextPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ContextPolicy> getAllContextPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ServiceCenterITComputingResource createServiceCenterITComputingResource(String name) {
//        ContextElement element = new ServiceCenterITComputingResourceImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ServiceCenterITComputingResource) element;
//    }
//
//    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
//        return getEntity(ServiceCenterITComputingResource.class, name);
//    }
//
//    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
//        Collection<ServiceCenterITComputingResource> collection = new ArrayList<ServiceCenterITComputingResource>();
//        for (ContextElement element : elements.values()) {
//            if (element instanceof ServiceCenterITComputingResource) {
//                collection.add((ServiceCenterITComputingResource) element);
//            }
//        }
//        return collection;
//    }
//
//    public ContextResource getContextResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ContextResource> getAllContextResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ServiceCenterServer createServiceCenterServer(String name) {
//        ContextElement element = new ServiceCenterServerImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ServiceCenterServer) element;
//    }
//
//    public ServiceCenterServer getServiceCenterServer(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ComplexResource createComplexResource(String name) {
//        ContextElement element = new ComplexResourceImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ComplexResource) element;
//    }
//
//    public ComplexResource getComplexResource(String name) {
//        return getEntity(ComplexResource.class, name);
//    }
//
//    public Collection<ComplexResource> getAllComplexResourceInstances() {
//        Collection<ComplexResource> collection = new ArrayList<ComplexResource>();
//        for (ContextElement element : elements.values()) {
//            if (element instanceof ComplexResource) {
//                collection.add((ComplexResource) element);
//            }
//        }
//        return collection;
//    }
//
//    public ContextElement getContextElement(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ContextElement> getAllContextElementInstances() {
//        Collection<ContextElement> collection = new ArrayList<ContextElement>();
//        for (ContextElement element : elements.values()) {
//            if (element instanceof ContextElement) {
//                collection.add((ContextElement) element);
//            }
//        }
//        return collection;
//    }
//
//    public ServiceCenterITFacilityResource createServiceCenterITFacilityResource(String name) {
//        ContextElement element = new ServiceCenterITFacilityResourceImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ServiceCenterITFacilityResource) element;
//    }
//
//    public ServiceCenterITFacilityResource getServiceCenterITFacilityResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public BusinessContextResource createBusinessContextResource(String name) {
//        ContextElement element = new BusinessContextResourceImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (BusinessContextResource) element;
//    }
//
//    public BusinessContextResource getBusinessContextResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Application createApplication(String name) {
//        ContextElement element = new ApplicationImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (Application) element;
//    }
//
//    public Application getApplication(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<Application> getAllApplicationInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public CPUIntensiveActivity createCPUIntensiveActivity(String name) {
//        ContextElement element = new CPUIntensiveActivityImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (CPUIntensiveActivity) element;
//    }
//
//    public CPUIntensiveActivity getCPUIntensiveActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ApplicationAdaptationAction createApplicationAdaptationAction(String name) {
//        ContextElement element = new ApplicationAdaptationActionImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ApplicationAdaptationAction) element;
//    }
//
//    public ApplicationAdaptationAction getApplicationAdaptationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ApplicationRedesign createApplicationRedesign(String name) {
//        ContextElement element = new ApplicationRedesignImpl();
//        element.setName(name);
//        persistEntity(element);
//        return (ApplicationRedesign) element;
//    }
//
//    public ApplicationRedesign getApplicationRedesign(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityActiveResource createITFacilityActiveResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityActiveResource getITFacilityActiveResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public HDD createHDD(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public HDD getHDD(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<HDD> getAllHDDInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public SimpleResource createSimpleResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public SimpleResource getSimpleResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<SimpleResource> getAllSimpleResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITComputingContextPolicy createITComputingContextPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITComputingContextPolicy getITComputingContextPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityPassiveResource createITFacilityPassiveResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityPassiveResource getITFacilityPassiveResource(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MigrateActivity createMigrateActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MigrateActivity getMigrateActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ConsolidationAction createConsolidationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ConsolidationAction getConsolidationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Sensor createSensor(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Sensor getSensor(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<Sensor> getAllSensorInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Actuator createActuator(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Actuator getActuator(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<Actuator> getAllActuatorInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public BusinessPolicy createBusinessPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public BusinessPolicy getBusinessPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MEM createMEM(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MEM getMEM(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<MEM> getAllMEMInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ExternalStorage createExternalStorage(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ExternalStorage getExternalStorage(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ExternalStorage> getAllExternalStorageInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public CPU createCPU(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public CPU getCPU(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<CPU> getAllCPUInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public SLAPolicy createSLAPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public SLAPolicy getSLAPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MEMIntensiveActivity createMEMIntensiveActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public MEMIntensiveActivity getMEMIntensiveActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Facility createFacility(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Facility getFacility(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<Facility> getAllFacilityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public QoSPolicy createQoSPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public QoSPolicy getQoSPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public DeployActivity createDeployActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public DeployActivity getDeployActivity(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<DeployActivity> getAllDeployActivityInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public EnvironmentPolicy createEnvironmentPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public EnvironmentPolicy getEnvironmentPolicy(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(String name) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }

    public void removeEntity(ContextElement element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void persistEntity(ContextElement entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SetServerStateActivity createSetServerStateActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SetServerStateActivity getSetServerStateActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<SetServerStateActivity> getAllSetServerStateActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public FacilityDefaultAction createFacilityDefaultAction(String name) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public DPMAction createDPMAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DPMAction getDPMAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<DPMAction> getAllDPMActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextAction createContextAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextAction getContextAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ContextAction> getAllContextActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HDDIntensiveActivity createHDDIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationActivity createApplicationActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationActivity getApplicationActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ApplicationActivity> getAllApplicationActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GPI_KPI_Policy createGPI_KPI_Policy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GPI_KPI_Policy getGPI_KPI_Policy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextPolicy createContextPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextPolicy getContextPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ContextPolicy> getAllContextPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterITComputingResource createServiceCenterITComputingResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextResource getContextResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ContextResource> getAllContextResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterServer createServiceCenterServer(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterServer getServiceCenterServer(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ComplexResource createComplexResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ComplexResource getComplexResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ContextElement getContextElement(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterITFacilityResource createServiceCenterITFacilityResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceCenterITFacilityResource getServiceCenterITFacilityResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BusinessContextResource createBusinessContextResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BusinessContextResource getBusinessContextResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Application createApplication(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Application getApplication(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Application> getAllApplicationInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CPUIntensiveActivity createCPUIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CPUIntensiveActivity getCPUIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationAdaptationAction createApplicationAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationAdaptationAction getApplicationAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationRedesign createApplicationRedesign(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ApplicationRedesign getApplicationRedesign(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityActiveResource createITFacilityActiveResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityActiveResource getITFacilityActiveResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HDD createHDD(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HDD getHDD(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<HDD> getAllHDDInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SimpleResource createSimpleResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SimpleResource getSimpleResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<SimpleResource> getAllSimpleResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITComputingContextPolicy createITComputingContextPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITComputingContextPolicy getITComputingContextPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityPassiveResource createITFacilityPassiveResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityPassiveResource getITFacilityPassiveResource(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MigrateActivity createMigrateActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MigrateActivity getMigrateActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ConsolidationAction createConsolidationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ConsolidationAction getConsolidationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Sensor createSensor(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Sensor getSensor(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Sensor> getAllSensorInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Actuator createActuator(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Actuator getActuator(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Actuator> getAllActuatorInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BusinessPolicy createBusinessPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BusinessPolicy getBusinessPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MEM createMEM(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MEM getMEM(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<MEM> getAllMEMInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ExternalStorage createExternalStorage(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ExternalStorage getExternalStorage(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ExternalStorage> getAllExternalStorageInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CPU createCPU(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CPU getCPU(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<CPU> getAllCPUInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SLAPolicy createSLAPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SLAPolicy getSLAPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MEMIntensiveActivity createMEMIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MEMIntensiveActivity getMEMIntensiveActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Facility createFacility(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Facility getFacility(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Facility> getAllFacilityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public QoSPolicy createQoSPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public QoSPolicy getQoSPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DeployActivity createDeployActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DeployActivity getDeployActivity(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<DeployActivity> getAllDeployActivityInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EnvironmentPolicy createEnvironmentPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EnvironmentPolicy getEnvironmentPolicy(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
