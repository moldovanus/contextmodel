package model.impl.util;

import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 13, 2010
 * Time: 12:33:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ModelFactory {

    void removeEntity(ContextElement element);

    void persistEntity(ContextElement entity);

    model.interfaces.actions.DPMAction createDPMAction(java.lang.String name);

    model.interfaces.actions.DPMAction getDPMAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.DPMAction> getAllDPMActionInstances();

    model.interfaces.actions.ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(java.lang.String name);

    model.interfaces.actions.ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances();

    model.interfaces.actions.ContextAction createContextAction(java.lang.String name);

    model.interfaces.actions.ContextAction getContextAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ContextAction> getAllContextActionInstances();

    model.interfaces.resources.applications.HDDIntensiveActivity createHDDIntensiveActivity(java.lang.String name);

    model.interfaces.resources.applications.HDDIntensiveActivity getHDDIntensiveActivity(java.lang.String name);

    java.util.Collection<model.interfaces.resources.applications.HDDIntensiveActivity> getAllHDDIntensiveActivityInstances();


    model.interfaces.resources.applications.ApplicationActivity createApplicationActivity(java.lang.String name);

    model.interfaces.resources.applications.ApplicationActivity getApplicationActivity(java.lang.String name);

    java.util.Collection<model.interfaces.resources.applications.ApplicationActivity> getAllApplicationActivityInstances();

    model.interfaces.policies.GPI_KPI_Policy createGPI_KPI_Policy(java.lang.String name);

    model.interfaces.policies.GPI_KPI_Policy getGPI_KPI_Policy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances();

    model.interfaces.policies.ContextPolicy createContextPolicy(java.lang.String name);

    model.interfaces.policies.ContextPolicy getContextPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.ContextPolicy> getAllContextPolicyInstances();


    model.interfaces.resources.ServiceCenterITComputingResource createServiceCenterITComputingResource(java.lang.String name);

    model.interfaces.resources.ServiceCenterITComputingResource getServiceCenterITComputingResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances();

    model.interfaces.resources.ContextResource getContextResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ContextResource> getAllContextResourceInstances();


    model.interfaces.resources.ServiceCenterServer createServiceCenterServer(java.lang.String name);

    model.interfaces.resources.ServiceCenterServer getServiceCenterServer(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ServiceCenterServer> getAllServiceCenterServerInstances();


    model.interfaces.resources.ComplexResource createComplexResource(java.lang.String name);

    model.interfaces.resources.ComplexResource getComplexResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ComplexResource> getAllComplexResourceInstances();

    model.interfaces.ContextElement getContextElement(java.lang.String name);

    java.util.Collection<model.interfaces.ContextElement> getAllContextElementInstances();


    model.interfaces.resources.ServiceCenterITFacilityResource createServiceCenterITFacilityResource(java.lang.String name);

    model.interfaces.resources.ServiceCenterITFacilityResource getServiceCenterITFacilityResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances();


    model.interfaces.resources.BusinessContextResource createBusinessContextResource(java.lang.String name);

    model.interfaces.resources.BusinessContextResource getBusinessContextResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.BusinessContextResource> getAllBusinessContextResourceInstances();


    model.interfaces.resources.applications.Application createApplication(java.lang.String name);

    model.interfaces.resources.applications.Application getApplication(java.lang.String name);

    java.util.Collection<model.interfaces.resources.applications.Application> getAllApplicationInstances();


    model.interfaces.resources.applications.CPUIntensiveActivity createCPUIntensiveActivity(java.lang.String name);

    model.interfaces.resources.applications.CPUIntensiveActivity getCPUIntensiveActivity(java.lang.String name);

    java.util.Collection<model.interfaces.resources.applications.CPUIntensiveActivity> getAllCPUIntensiveActivityInstances();


    model.interfaces.actions.ApplicationAdaptationAction createApplicationAdaptationAction(java.lang.String name);

    model.interfaces.actions.ApplicationAdaptationAction getApplicationAdaptationAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances();


    model.interfaces.actions.ApplicationRedesign createApplicationRedesign(java.lang.String name);

    model.interfaces.actions.ApplicationRedesign getApplicationRedesign(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ApplicationRedesign> getAllApplicationRedesignInstances();


    model.interfaces.resources.ITFacilityActiveResource createITFacilityActiveResource(java.lang.String name);

    model.interfaces.resources.ITFacilityActiveResource getITFacilityActiveResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ITFacilityActiveResource> getAllITFacilityActiveResourceInstances();


    model.interfaces.resources.HDD createHDD(java.lang.String name);

    model.interfaces.resources.HDD getHDD(java.lang.String name);

    java.util.Collection<model.interfaces.resources.HDD> getAllHDDInstances();


    model.interfaces.resources.SimpleResource createSimpleResource(java.lang.String name);

    model.interfaces.resources.SimpleResource getSimpleResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.SimpleResource> getAllSimpleResourceInstances();


    model.interfaces.policies.ITComputingContextPolicy createITComputingContextPolicy(java.lang.String name);

    model.interfaces.policies.ITComputingContextPolicy getITComputingContextPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.ITComputingContextPolicy> getAllITComputingContextPolicyInstances();


    model.interfaces.resources.ITFacilityPassiveResource createITFacilityPassiveResource(java.lang.String name);

    model.interfaces.resources.ITFacilityPassiveResource getITFacilityPassiveResource(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances();


    model.interfaces.actions.MigrateActivity createMigrateActivity(java.lang.String name);

    model.interfaces.actions.MigrateActivity getMigrateActivity(java.lang.String name);

    java.util.Collection<model.interfaces.actions.MigrateActivity> getAllMigrateActivityInstances();


    model.interfaces.actions.ConsolidationAction createConsolidationAction(java.lang.String name);

    model.interfaces.actions.ConsolidationAction getConsolidationAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ConsolidationAction> getAllConsolidationActionInstances();


    model.interfaces.resources.Sensor createSensor(java.lang.String name);

    model.interfaces.resources.Sensor getSensor(java.lang.String name);

    java.util.Collection<model.interfaces.resources.Sensor> getAllSensorInstances();


    model.interfaces.resources.Actuator createActuator(java.lang.String name);

    model.interfaces.resources.Actuator getActuator(java.lang.String name);

    java.util.Collection<model.interfaces.resources.Actuator> getAllActuatorInstances();


    model.interfaces.policies.BusinessPolicy createBusinessPolicy(java.lang.String name);

    model.interfaces.policies.BusinessPolicy getBusinessPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.BusinessPolicy> getAllBusinessPolicyInstances();


    model.interfaces.resources.MEM createMEM(java.lang.String name);

    model.interfaces.resources.MEM getMEM(java.lang.String name);

    java.util.Collection<model.interfaces.resources.MEM> getAllMEMInstances();


    model.interfaces.resources.ExternalStorage createExternalStorage(java.lang.String name);

    model.interfaces.resources.ExternalStorage getExternalStorage(java.lang.String name);

    java.util.Collection<model.interfaces.resources.ExternalStorage> getAllExternalStorageInstances();


    model.interfaces.resources.CPU createCPU(java.lang.String name);

    model.interfaces.resources.CPU getCPU(java.lang.String name);

    java.util.Collection<model.interfaces.resources.CPU> getAllCPUInstances();

    model.interfaces.policies.SLAPolicy createSLAPolicy(java.lang.String name);

    model.interfaces.policies.SLAPolicy getSLAPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.SLAPolicy> getAllSLAPolicyInstances();


    model.interfaces.resources.applications.MEMIntensiveActivity createMEMIntensiveActivity(java.lang.String name);

    model.interfaces.resources.applications.MEMIntensiveActivity getMEMIntensiveActivity(java.lang.String name);

    java.util.Collection<model.interfaces.resources.applications.MEMIntensiveActivity> getAllMEMIntensiveActivityInstances();


    model.interfaces.resources.Facility createFacility(java.lang.String name);

    model.interfaces.resources.Facility getFacility(java.lang.String name);

    java.util.Collection<model.interfaces.resources.Facility> getAllFacilityInstances();

    model.interfaces.policies.QoSPolicy createQoSPolicy(java.lang.String name);

    model.interfaces.policies.QoSPolicy getQoSPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.QoSPolicy> getAllQoSPolicyInstances();


    model.interfaces.actions.DeployActivity createDeployActivity(java.lang.String name);

    model.interfaces.actions.DeployActivity getDeployActivity(java.lang.String name);

    java.util.Collection<model.interfaces.actions.DeployActivity> getAllDeployActivityInstances();


    model.interfaces.policies.EnvironmentPolicy createEnvironmentPolicy(java.lang.String name);

    model.interfaces.policies.EnvironmentPolicy getEnvironmentPolicy(java.lang.String name);

    java.util.Collection<model.interfaces.policies.EnvironmentPolicy> getAllEnvironmentPolicyInstances();


    model.interfaces.actions.ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(java.lang.String name);

    model.interfaces.actions.ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(java.lang.String name);

    java.util.Collection<model.interfaces.actions.ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances();

}
