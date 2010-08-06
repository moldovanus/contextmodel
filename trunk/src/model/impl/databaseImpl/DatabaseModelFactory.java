package model.impl.databaseImpl;

import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.databaseImpl.ontology.ContextElementImpl;
import model.impl.databaseImpl.ontology.actions.*;
import model.impl.databaseImpl.ontology.policies.*;
import model.impl.databaseImpl.ontology.resources.*;
import model.impl.databaseImpl.ontology.resources.applications.*;
import model.impl.util.ModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;
import org.hibernate.CacheMode;
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
        criteria.setCacheable(true);
        criteria.setCacheMode(CacheMode.NORMAL);
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
        criteria.setCacheMode(CacheMode.NORMAL);
        criteria.setCacheable(true);
        Collection object = criteria.list();
        // session.flush()
        // ;

//        System.out.println("\n !!!!!!! \n");
//        Statistics statistics = session.getSessionFactory().getStatistics();
//        System.out.println("Collection fetch count: " + statistics.getCollectionFetchCount());
//        System.out.println("Collection recreate count: " + statistics.getCollectionRecreateCount());
//        System.out.println("Collection load count: " + statistics.getCollectionLoadCount());
//        System.out.println("Query cache put: " + statistics.getQueryCachePutCount());
//        System.out.println("Query cache hit: " + statistics.getQueryCacheHitCount());
//        System.out.println("Query cache miss: " + statistics.getQueryCacheMissCount());
//          System.out.println("Query execution time: " + statistics.getQueryExecutionMaxTime());
//        System.out.println("Second level cache put: " + statistics.getSecondLevelCachePutCount());
//        System.out.println("Second level cache hit: " + statistics.getSecondLevelCacheHitCount());
//        System.out.println("Second level cache miss: " + statistics.getSecondLevelCacheMissCount());

//        String[] regionNames = statistics.getSecondLevelCacheRegionNames();
//        for(String name : regionNames){
//            SecondLevelCacheStatistics secondLevelCacheStatistics = statistics.getSecondLevelCacheStatistics(name);
//             System.out.println("Second level cache \""+name+"\" elements in mem: " + secondLevelCacheStatistics.getElementCountInMemory());
//             System.out.println("Second level cache \""+name+"\" elements on disk: " + secondLevelCacheStatistics.getElementCountOnDisk());
//             System.out.println("Second level cache \""+name+"\" hit count: " + secondLevelCacheStatistics.getHitCount());
//             System.out.println("Second level cache \""+name+"\" miss count: " + secondLevelCacheStatistics.getMissCount());
//             System.out.println("Second level cache \""+name+"\" put count: " + secondLevelCacheStatistics.getPutCount());
//        }
//
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
        return getAllEntityInstances(ITComputingResourcesAdaptationActionImpl.class);
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
        return getAllEntityInstances(ContextActionImpl.class);
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
        return getAllEntityInstances(HDDIntensiveActivityImpl.class);
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
        return getAllEntityInstances(ApplicationActivityImpl.class);
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
        return getAllEntityInstances(GPI_KPI_PolicyImpl.class);
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
        return getAllEntityInstances(ContextPolicyImpl.class);
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
        return getAllEntityInstances(ServiceCenterITComputingResourceImpl.class);
    }

    public ContextResource getContextResource(String name) {
        return getEntity(ContextResourceImpl.class, name);
    }

    public Collection<ContextResource> getAllContextResourceInstances() {
        return getAllEntityInstances(ContextResourceImpl.class);
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
        return getEntity(ServiceCenterServerImpl.class, name);
    }

    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
        return getAllEntityInstances(ServiceCenterServerImpl.class);
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
        return getEntity(ComplexResourceImpl.class, name);
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        return getAllEntityInstances(ComplexResourceImpl.class);
    }


    public ContextElement getContextElement(String name) {
        return getEntity(ContextElementImpl.class, name);
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        return getAllEntityInstances(ContextElementImpl.class);
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
        return getEntity(ServiceCenterITFacilityResourceImpl.class, name);
    }

    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
        return getAllEntityInstances(ServiceCenterITFacilityResourceImpl.class);
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
        return getEntity(BusinessContextResourceImpl.class, name);
    }

    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
        return getAllEntityInstances(BusinessContextResourceImpl.class);
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
        return getEntity(ApplicationImpl.class, name);
    }

    public Collection<Application> getAllApplicationInstances() {
        return getAllEntityInstances(ApplicationImpl.class);
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
        return getEntity(CPUIntensiveActivityImpl.class, name);
    }

    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
        return getAllEntityInstances(CPUIntensiveActivityImpl.class);
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
        return getEntity(ApplicationAdaptationActionImpl.class, name);
    }

    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
        return getAllEntityInstances(ApplicationAdaptationActionImpl.class);
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
        return getEntity(ApplicationRedesignImpl.class, name);
    }

    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
        return getAllEntityInstances(ApplicationRedesignImpl.class);
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
        return getEntity(ITFacilityActiveResourceImpl.class, name);
    }

    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
        return getAllEntityInstances(ITFacilityActiveResourceImpl.class);
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
        return getEntity(HDDImpl.class, name);
    }

    public Collection<HDD> getAllHDDInstances() {
        return getAllEntityInstances(HDDImpl.class);
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
        return getEntity(SimpleResourceImpl.class, name);
    }

    public Collection<SimpleResource> getAllSimpleResourceInstances() {
        return getAllEntityInstances(SimpleResourceImpl.class);
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
        return getEntity(ITComputingContextPolicyImpl.class, name);
    }

    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
        return getAllEntityInstances(ITComputingContextPolicyImpl.class);
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
        return getEntity(ITFacilityPassiveResourceImpl.class, name);
    }

    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
        return getAllEntityInstances(ITFacilityPassiveResourceImpl.class);
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
        return getEntity(MigrateActivityImpl.class, name);
    }

    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
        return getAllEntityInstances(MigrateActivityImpl.class);
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
        return getEntity(ConsolidationActionImpl.class, name);
    }

    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
        return getAllEntityInstances(ConsolidationActionImpl.class);
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
        return getEntity(SensorImpl.class, name);
    }

    public Collection<Sensor> getAllSensorInstances() {
        return getAllEntityInstances(SensorImpl.class);
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
        return getEntity(ActuatorImpl.class, name);
    }

    public Collection<Actuator> getAllActuatorInstances() {
        return getAllEntityInstances(ActuatorImpl.class);
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
        return getEntity(BusinessPolicyImpl.class, name);
    }

    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
        return getAllEntityInstances(BusinessPolicyImpl.class);
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
        return getEntity(MEMImpl.class, name);
    }

    public Collection<MEM> getAllMEMInstances() {
        return getAllEntityInstances(MEMImpl.class);
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
        return getEntity(ExternalStorageImpl.class, name);
    }

    public Collection<ExternalStorage> getAllExternalStorageInstances() {
        return getAllEntityInstances(ExternalStorageImpl.class);
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
        return getEntity(CPUImpl.class, name);
    }

    public Collection<CPU> getAllCPUInstances() {
        return getAllEntityInstances(CPUImpl.class);
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
        return getEntity(SLAPolicyImpl.class, name);
    }

    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
        return getAllEntityInstances(SLAPolicyImpl.class);
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
        return getEntity(MEMIntensiveActivityImpl.class, name);
    }

    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
        return getAllEntityInstances(MEMIntensiveActivityImpl.class);
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
        return getEntity(FacilityImpl.class, name);
    }

    public Collection<Facility> getAllFacilityInstances() {
        return getAllEntityInstances(FacilityImpl.class);
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
        return getEntity(QoSPolicyImpl.class, name);
    }

    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
        return getAllEntityInstances(QoSPolicyImpl.class);
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
        return getEntity(DeployActivityImpl.class, name);
    }

    public Collection<DeployActivity> getAllDeployActivityInstances() {
        return getAllEntityInstances(DeployActivityImpl.class);
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
        return getEntity(EnvironmentPolicyImpl.class, name);
    }

    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
        return getAllEntityInstances(EnvironmentPolicyImpl.class);
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
        return getEntity(ITFacilityResourcesAdaptationActionImpl.class, name);
    }

    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
        return getAllEntityInstances(ITFacilityResourcesAdaptationActionImpl.class);
    }
}
