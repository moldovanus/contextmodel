package model.impl.databaseImpl;

import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.databaseImpl.ontology.actions.*;
import model.impl.databaseImpl.ontology.policies.*;
import model.impl.databaseImpl.ontology.resources.*;
import model.impl.databaseImpl.ontology.resources.applications.ApplicationImpl;
import model.impl.databaseImpl.ontology.resources.applications.CPUIntensiveActivityImpl;
import model.impl.databaseImpl.ontology.resources.applications.HDDIntensiveActivityImpl;
import model.impl.databaseImpl.ontology.resources.applications.MEMIntensiveActivityImpl;
import model.impl.util.ModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.List;
//TODO : de pus si la getAllInstances tot metoda generica si apelata

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 13, 2010
 * Time: 12:44:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseModelFactory implements ModelFactory {

    private <T> T getEntity(Class<? extends T> entityType, String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(entityType);
        criteria.add(Restrictions.eq("name", name));
        T object = (T) criteria.uniqueResult();
        // session.flush();
        transaction.commit();
        return object;
    }

    private Collection getAllEntityInstances(Class entityType) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(entityType);
        Collection object = criteria.list();
        // session.flush();
        transaction.commit();
        return object;
    }


    public void removeEntity(ContextElement element) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(element);
//        Query query = session.createSQLQuery("Delete from equals where uid = " + ((ContextElementImpl) element).getId());
//        query.executeUpdate();
        // session.flush();
        transaction.commit();
    }

    public void persistEntity(ContextElement entity) {

        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        // session.flush();
        session.saveOrUpdate(entity);
//        Query query = session.createSQLQuery("Insert into equals values(" + ((ContextElementImpl) entity).getId()
//                + "," + ((ContextElementImpl) entity).getId() + ")");
//        query.executeUpdate();
        transaction.commit();
    }

    public DPMAction createDPMAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        DPMAction dpmAction = new DPMActionImpl();
        dpmAction.setName(name);
        session.save(dpmAction);
        // session.flush();
        transaction.commit();
        return dpmAction;
    }

    public DPMAction getDPMAction(String name) {
        return getEntity(DPMAction.class, name);
    }

    public Collection<DPMAction> getAllDPMActionInstances() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(DPMAction.class);
        List dpmActions = criteria.list();
        // session.flush();
        transaction.commit();
        return dpmActions;
    }

    public ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ITComputingResourceAdaptationAction object = new ITComputingResourcesAdaptationActionImpl();
        object.setName(name);
        session.save(object);
        // session.flush();
        transaction.commit();
        return object;
    }

    public ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(String name) {
        return getEntity(ITComputingResourceAdaptationAction.class, name);
    }

    public Collection<ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances() {
        return getAllEntityInstances(ITComputingResourceAdaptationAction.class);
    }

    public ContextAction createContextAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ContextAction object = new ContextActionImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ContextAction getContextAction(String name) {
        return getEntity(ContextAction.class, name);
    }

    public Collection<ContextAction> getAllContextActionInstances() {
        return getAllEntityInstances(ContextAction.class);
    }

    public HDDIntensiveActivity createHDDIntensiveActivity(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        HDDIntensiveActivity object = new HDDIntensiveActivityImpl();
        object.setName(name);
        session.save(object);
//       //// session.flush();
        transaction.commit();
        return object;
    }

    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
        return getEntity(HDDIntensiveActivity.class, name);
    }

    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
        return getAllEntityInstances(HDDIntensiveActivity.class);
    }

    public ApplicationActivity createApplicationActivity(String name) {
//        Session session = HibernateUtil.getSession();
//        Transaction transaction = session.beginTransaction();
//        ApplicationActivity object = new ApplicationActivityImpl();
//        object.setName(name);
//        session.save(object);
//       //// session.flush();
//        transaction.commit();
//        return object;
        return null;
    }

    public ApplicationActivity getApplicationActivity(String name) {
        return getEntity(ApplicationActivity.class, name);
    }

    public Collection<ApplicationActivity> getAllApplicationActivityInstances() {
        return getAllEntityInstances(ApplicationActivity.class);
    }

    public GPI_KPI_Policy createGPI_KPI_Policy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        GPI_KPI_Policy object = new GPI_KPI_PolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public GPI_KPI_Policy getGPI_KPI_Policy(String name) {
        return getEntity(GPI_KPI_Policy.class, name);
    }

    public Collection<GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances() {
        return getAllEntityInstances(GPI_KPI_Policy.class);
    }

    public ContextPolicy createContextPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ContextPolicy object = new ContextPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ContextPolicy getContextPolicy(String name) {
        return getEntity(ContextPolicy.class, name);
    }

    public Collection<ContextPolicy> getAllContextPolicyInstances() {
        return getAllEntityInstances(ContextPolicy.class);
    }

    public ServiceCenterITComputingResource createServiceCenterITComputingResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ServiceCenterITComputingResource object = new ServiceCenterITComputingResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
        return getEntity(ServiceCenterITComputingResource.class, name);
    }

    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
        return getAllEntityInstances(ServiceCenterITComputingResource.class);
    }

    public ContextResource getContextResource(String name) {
        return getEntity(ContextResource.class, name);
    }

    public Collection<ContextResource> getAllContextResourceInstances() {
        return getAllEntityInstances(ContextResource.class);
    }

    public ServiceCenterServer createServiceCenterServer(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ServiceCenterServer object = new ServiceCenterServerImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ServiceCenterServer getServiceCenterServer(String name) {
        return getEntity(ServiceCenterServer.class, name);
    }

    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
        return getAllEntityInstances(ServiceCenterServer.class);
    }

    public ComplexResource createComplexResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ComplexResource object = new ComplexResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ComplexResource getComplexResource(String name) {
        return getEntity(ComplexResource.class, name);
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        return getAllEntityInstances(ComplexResource.class);
    }


    public ContextElement getContextElement(String name) {
        return getEntity(ContextElement.class, name);
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        return getAllEntityInstances(ContextElement.class);
    }

    public ServiceCenterITFacilityResource createServiceCenterITFacilityResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ServiceCenterITFacilityResource object = new ServiceCenterITFacilityResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ServiceCenterITFacilityResource getServiceCenterITFacilityResource(String name) {
        return getEntity(ServiceCenterITFacilityResource.class, name);
    }

    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
        return getAllEntityInstances(ServiceCenterITFacilityResource.class);
    }

    public BusinessContextResource createBusinessContextResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        BusinessContextResource object = new BusinessContextResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public BusinessContextResource getBusinessContextResource(String name) {
        return getEntity(BusinessContextResource.class, name);
    }

    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
        return getAllEntityInstances(BusinessContextResource.class);
    }

    public Application createApplication(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Application object = new ApplicationImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public Application getApplication(String name) {
        return getEntity(Application.class, name);
    }

    public Collection<Application> getAllApplicationInstances() {
        return getAllEntityInstances(Application.class);
    }

    public CPUIntensiveActivity createCPUIntensiveActivity(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        CPUIntensiveActivity object = new CPUIntensiveActivityImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public CPUIntensiveActivity getCPUIntensiveActivity(String name) {
        return getEntity(CPUIntensiveActivity.class, name);
    }

    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
        return getAllEntityInstances(CPUIntensiveActivity.class);
    }

    public ApplicationAdaptationAction createApplicationAdaptationAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ApplicationAdaptationAction object = new ApplicationAdaptationActionImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ApplicationAdaptationAction getApplicationAdaptationAction(String name) {
        return getEntity(ApplicationAdaptationAction.class, name);
    }

    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
        return getAllEntityInstances(ApplicationAdaptationAction.class);
    }

    public ApplicationRedesign createApplicationRedesign(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ApplicationRedesign object = new ApplicationRedesignImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ApplicationRedesign getApplicationRedesign(String name) {
        return getEntity(ApplicationRedesign.class, name);
    }

    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
        return getAllEntityInstances(ApplicationRedesign.class);
    }

    public ITFacilityActiveResource createITFacilityActiveResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ITFacilityActiveResource object = new ITFacilityActiveResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ITFacilityActiveResource getITFacilityActiveResource(String name) {
        return getEntity(ITFacilityActiveResource.class, name);
    }

    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
        return getAllEntityInstances(ITFacilityActiveResource.class);
    }

    public HDD createHDD(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        HDD object = new HDDImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public HDD getHDD(String name) {
        return getEntity(HDD.class, name);
    }

    public Collection<HDD> getAllHDDInstances() {
        return getAllEntityInstances(HDD.class);
    }

    public SimpleResource createSimpleResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        SimpleResource object = new SimpleResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public SimpleResource getSimpleResource(String name) {
        return getEntity(SimpleResource.class, name);
    }

    public Collection<SimpleResource> getAllSimpleResourceInstances() {
        return getAllEntityInstances(SimpleResource.class);
    }

    public ITComputingContextPolicy createITComputingContextPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ITComputingContextPolicy object = new ITComputingContextPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ITComputingContextPolicy getITComputingContextPolicy(String name) {
        return getEntity(ITComputingContextPolicy.class, name);
    }

    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
        return getAllEntityInstances(ITComputingContextPolicy.class);
    }

    public ITFacilityPassiveResource createITFacilityPassiveResource(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ITFacilityPassiveResource object = new ITFacilityPassiveResourceImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ITFacilityPassiveResource getITFacilityPassiveResource(String name) {
        return getEntity(ITFacilityPassiveResource.class, name);
    }

    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
        return getAllEntityInstances(ITFacilityPassiveResource.class);
    }

    public MigrateActivity createMigrateActivity(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MigrateActivity object = new MigrateActivityImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public MigrateActivity getMigrateActivity(String name) {
        return getEntity(MigrateActivity.class, name);
    }

    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
        return getAllEntityInstances(MigrateActivity.class);
    }

    public ConsolidationAction createConsolidationAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ConsolidationAction object = new ConsolidationActionImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ConsolidationAction getConsolidationAction(String name) {
        return getEntity(ConsolidationAction.class, name);
    }

    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
        return getAllEntityInstances(ConsolidationAction.class);
    }

    public Sensor createSensor(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Sensor object = new SensorImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public Sensor getSensor(String name) {
        return getEntity(Sensor.class, name);
    }

    public Collection<Sensor> getAllSensorInstances() {
        return getAllEntityInstances(Sensor.class);
    }

    public Actuator createActuator(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Actuator object = new ActuatorImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public Actuator getActuator(String name) {
        return getEntity(Actuator.class, name);
    }

    public Collection<Actuator> getAllActuatorInstances() {
        return getAllEntityInstances(Actuator.class);
    }

    public BusinessPolicy createBusinessPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        BusinessPolicy object = new BusinessPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public BusinessPolicy getBusinessPolicy(String name) {
        return getEntity(BusinessPolicy.class, name);
    }

    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
        return getAllEntityInstances(BusinessPolicy.class);
    }

    public MEM createMEM(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MEM object = new MEMImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public MEM getMEM(String name) {
        return getEntity(MEM.class, name);
    }

    public Collection<MEM> getAllMEMInstances() {
        return getAllEntityInstances(MEM.class);
    }

    public ExternalStorage createExternalStorage(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ExternalStorage object = new ExternalStorageImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ExternalStorage getExternalStorage(String name) {
        return getEntity(ExternalStorage.class, name);
    }

    public Collection<ExternalStorage> getAllExternalStorageInstances() {
        return getAllEntityInstances(ExternalStorage.class);
    }

    public CPU createCPU(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        CPU object = new CPUImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public CPU getCPU(String name) {
        return getEntity(CPU.class, name);
    }

    public Collection<CPU> getAllCPUInstances() {
        return getAllEntityInstances(CPU.class);
    }

    public SLAPolicy createSLAPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        SLAPolicy object = new SLAPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public SLAPolicy getSLAPolicy(String name) {
        return getEntity(SLAPolicy.class, name);
    }

    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
        return getAllEntityInstances(SLAPolicy.class);
    }

    public MEMIntensiveActivity createMEMIntensiveActivity(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        MEMIntensiveActivity object = new MEMIntensiveActivityImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public MEMIntensiveActivity getMEMIntensiveActivity(String name) {
        return getEntity(MEMIntensiveActivity.class, name);
    }

    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
        return getAllEntityInstances(MEMIntensiveActivity.class);
    }

    public Facility createFacility(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Facility object = new FacilityImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public Facility getFacility(String name) {
        return getEntity(Facility.class, name);
    }

    public Collection<Facility> getAllFacilityInstances() {
        return getAllEntityInstances(Facility.class);
    }

    public QoSPolicy createQoSPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        QoSPolicy object = new QoSPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public QoSPolicy getQoSPolicy(String name) {
        return getEntity(QoSPolicy.class, name);
    }

    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
        return getAllEntityInstances(QoSPolicy.class);
    }

    public DeployActivity createDeployActivity(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        DeployActivity object = new DeployActivityImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public DeployActivity getDeployActivity(String name) {
        return getEntity(DeployActivity.class, name);
    }

    public Collection<DeployActivity> getAllDeployActivityInstances() {
        return getAllEntityInstances(DeployActivity.class);
    }

    public EnvironmentPolicy createEnvironmentPolicy(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        EnvironmentPolicy object = new EnvironmentPolicyImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public EnvironmentPolicy getEnvironmentPolicy(String name) {
        return getEntity(EnvironmentPolicy.class, name);
    }

    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
        return getAllEntityInstances(EnvironmentPolicy.class);
    }

    public ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ITFacilityResourceAdaptationAction object = new ITFacilityResourcesAdaptationActionImpl();
        object.setName(name);
        session.save(object);
        //// session.flush();
        transaction.commit();
        return object;
    }

    public ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(String name) {
        return getEntity(ITFacilityResourceAdaptationAction.class, name);
    }

    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
        return getAllEntityInstances(ITFacilityResourceAdaptationAction.class);
    }
}