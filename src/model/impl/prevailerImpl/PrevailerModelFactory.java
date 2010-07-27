package model.impl.prevailerImpl;

import model.impl.prevailerImpl.transactions.GetAllResourceInstancesTransaction;
import model.impl.prevailerImpl.transactions.GetResourceByNameTransaction;
import model.impl.prevailerImpl.transactions.PersistEntityTransaction;
import model.impl.prevailerImpl.transactions.RemoveResourceTransaction;
import model.impl.util.ModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.foundation.serialization.SkaringaSerializer;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 21, 2010
 * Time: 10:57:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrevailerModelFactory implements ModelFactory {

    private Prevayler prevailer;

    public PrevailerModelFactory() {
        PrevaylerFactory factory = new PrevaylerFactory();
        factory.configurePrevalenceDirectory("./contextModel_1");
        //factory.configureTransientMode(true);
        factory.configurePrevalentSystem(PrevaylerDatabaseContainer.getContainerInstance());
        factory.configureSnapshotSerializer(new SkaringaSerializer());
//      factory.configureJournalSerializer(new SkaringaSerializer());

        try {
            java.util.Date before = new Date();

            prevailer = factory.create();
            java.util.Date after = new Date();

            java.util.Date result = new Date(after.getTime() - before.getTime());
            System.out.println("Prevayler load time: " + result.getMinutes() + ":" + result.getSeconds());
//            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void takeSnapshot() throws IOException {
        prevailer.takeSnapshot();
    }

    public void removeEntity(ContextElement element) {
        prevailer.execute(new RemoveResourceTransaction(element));
    }

    public void persistEntity(ContextElement entity) {
//        try {
//            prevailer.takeSnapshot();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
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
        try {
            return (HDDIntensiveActivity)
                    prevailer.execute(new PersistEntityTransaction(name, HDDIntensiveActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
        try {
            return (HDDIntensiveActivity)
                    prevailer.execute(new GetResourceByNameTransaction(HDDIntensiveActivity.class.getName(), name));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
        try {
            return (Collection<HDDIntensiveActivity>)
                    prevailer.execute(new GetAllResourceInstancesTransaction(HDDIntensiveActivity.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
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
        try {
            return (ServiceCenterITComputingResource)
                    prevailer.execute(new PersistEntityTransaction(name, ServiceCenterITComputingResource.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
        try {
            return (ServiceCenterITComputingResource)
                    prevailer.execute(new GetResourceByNameTransaction(ServiceCenterITComputingResource.class.getName(), name));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
        try {
            return (Collection<ServiceCenterITComputingResource>)
                    prevailer.execute(new GetAllResourceInstancesTransaction(ServiceCenterITComputingResource.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
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
        try {
            return (ComplexResource)
                    prevailer.execute(new PersistEntityTransaction(name, ComplexResource.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public ComplexResource getComplexResource(String name) {
        try {
            return (ComplexResource)
                    prevailer.execute(new GetResourceByNameTransaction(ComplexResource.class.getName(), name));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        try {
            return (Collection<ComplexResource>)
                    prevailer.execute(new GetAllResourceInstancesTransaction(ComplexResource.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public ContextElement getContextElement(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        try {
            return (Collection<ContextElement>)
                    prevailer.execute(new GetAllResourceInstancesTransaction(ContextElement.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
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
