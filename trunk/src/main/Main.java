package main;

import evaluation.InstanceGenerator;
import model.impl.databaseImpl.DatabaseModelFactory;
import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.util.ModelAccess;
import model.interfaces.ContextElement;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.SimpleResource;
import model.interfaces.resources.applications.ApplicationActivity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 11:04:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {


//         private String policyName;
//    private String evaluationCondition;
//
//    private boolean respected;
//    private List<ContextResource> policySubject;
//    private List<ContextResource> policyTarget;
//
//    private Event triggerEvent;
//        ContextPolicyImpl contextPolicy = new ContextPolicyImpl();
//        contextPolicy.setPolicyName("Test name");
//        contextPolicy.setEvaluationCondition("EVALUATION");
//        contextPolicy.setRespected(true);
//        List<ContextResource> contextResourceList_1 = new ArrayList<ContextResource>();
//        List<ContextResource> contextResourceList_2 = new ArrayList<ContextResource>();
//        SimpleResourceImpl contextResource_1 = new SimpleResourceImpl();
//        contextResource_1.setResourceWorkLoadProperty("AAA");
//
//        //contextResource_1.setId("IDDDD");
        // SimpleResourceImpl contextResource_2 = new SimpleResourceImpl();
        // contextResource_2.setName("dddd");
        // contextResource_2.setResourceWorkLoadProperty("aaa");

//        contextResource_2.setResourceWorkLoadProperty("BBB");
//        //contextResource_2.setId("IDDDD_3");
//        System.out.println(contextResource_2.getId());
//        contextResourceList_1.add(contextResource_1);
//        contextPolicy.setPolicySubject(contextResourceList_1);
//
//        contextResourceList_2.add(contextResource_2);
//        contextPolicy.setPolicyTarget(contextResourceList_2);
//
//        contextPolicy.setTriggerEvent(new EventImpl());
//        Session session = HibernateUtil.getSession();
//        Transaction transaction = session.beginTransaction();
        // Criteria criteria = session.createCriteria(ContextPolicyImpl.class);
//        List<ContextPolicyImpl> list = criteria.list();
//        ContextPolicyImpl l = null;
//        for (ContextPolicyImpl cr : list) {
//            System.out.println(((ContextResource) cr.getPolicyTarget().get(0)).getResourceID());
//            l =  cr;
//        }
        //session.save(contextResource_2);
//        session.flush();
//        transaction.commit();

        //System.out.println(l.getId());

//        DatabaseModelFactory factory = new DatabaseModelFactory();

//        SimpleResource s = factory.getSimpleResource("dddd");
//        System.out.println(s.getName());
//        Collection<ContextPolicy> resources = factory.getAllContextPolicyInstances();
//        for(ContextPolicy resource: resources){
//            System.out.println(resource.getPolicyName() + " __ " + resource.getEvaluationCondition());
//        }
        //HibernateUtil.recreateDatabase();
        java.util.Date before = new Date();

//        ModelAccess modelAccess = InstanceGenerator.getModelAccessInstance(ModelAccess.PREVAYLER_ACCESS);
        ModelAccess modelAccess = InstanceGenerator.generateComplexResourceInstances(5, ModelAccess.DATABASE_ACCESS);
        java.util.Date after = new Date();

        java.util.Date result = new Date(after.getTime() - before.getTime());
        System.out.println("Creation time: " + result.getMinutes() + ":" + result.getSeconds());

        //long total = Runtime.getRuntime().totalMemory();
        long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory:" + used);

        before = new Date();
        //System.out.println(modelAccess.getAllContextElementInstances().size());

        for(ContextElement element : modelAccess.getAllContextElementInstances()){
//            System.out.println(element.getName());
        }
        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Query time: " + result.getMinutes() + ":" + result.getSeconds());

        before = new Date();
        Collection<ComplexResource> resources = modelAccess.getAllComplexResourceInstances();
        for (ComplexResource resource : resources) {
            List<ServiceCenterITComputingResource> list = resource.getResources();
            for (ServiceCenterITComputingResource serviceCenterITComputingResource : list) {
                List<ApplicationActivity> activities = serviceCenterITComputingResource.getRunningActivities();
                for (ApplicationActivity activity : activities) {
//                    Double d = activity.getCPUAllocatedValue();
//                    d += 20;
//                    System.out.println(d);
                }
            }
        }

        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Query CR time: " + result.getMinutes() + ":" + result.getSeconds());

        before = new Date();
        modelAccess.getComplexResource("ComplexResource_34");

        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Query by name : " + result.getMinutes() + ":" + result.getSeconds());
       // System.exit(1);
//        try {
//            before = new Date();
//            modelAccess.getPrevailerModelFactory().takeSnapshot();
//            after = new Date();
//            result = new Date(after.getTime() - before.getTime());
//            System.out.println("Snapshot time: " + result.getMinutes() + ":" + result.getSeconds());
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


    }
}
