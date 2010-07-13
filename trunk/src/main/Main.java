package main;

import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.databaseImpl.ontology.EventImpl;
import model.impl.databaseImpl.ontology.policies.ContextPolicyImpl;
import model.impl.databaseImpl.ontology.resources.SimpleResourceImpl;
import model.interfaces.resources.ContextResource;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
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
        HibernateUtil.recreateDatabase();

//         private String policyName;
//    private String evaluationCondition;
//
//    private boolean respected;
//    private List<ContextResource> policySubject;
//    private List<ContextResource> policyTarget;
//
//    private Event triggerEvent;
        ContextPolicyImpl contextPolicy = new ContextPolicyImpl();
        contextPolicy.setPolicyName("Test name");
        contextPolicy.setEvaluationCondition("EVALUATION");
        contextPolicy.setRespected(true);
        List<ContextResource> contextResourceList_1 = new ArrayList<ContextResource>();
        List<ContextResource> contextResourceList_2 = new ArrayList<ContextResource>();
        SimpleResourceImpl contextResource_1 = new SimpleResourceImpl();
        contextResource_1.setResourceWorkLoadProperty("AAA");

        //contextResource_1.setId("IDDDD");
        SimpleResourceImpl contextResource_2 = new SimpleResourceImpl();
        contextResource_2.setResourceWorkLoadProperty("BBB");
        //contextResource_2.setId("IDDDD_3");

        contextResourceList_1.add(contextResource_1);
        contextPolicy.setPolicySubject(contextResourceList_1);

        contextResourceList_2.add(contextResource_2);
        contextPolicy.setPolicyTarget(contextResourceList_2);

        contextPolicy.setTriggerEvent(new EventImpl());
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
//        Criteria criteria = session.createCriteria(ContextPolicyImpl.class);
//        List<ContextPolicyImpl> list = criteria.list();
//        for (ContextPolicyImpl cr : list) {
//            System.out.println(((ContextResource) cr.getPolicyTarget().get(0)).getResourceID());
//        }
        session.save(contextPolicy);
        session.flush();
        transaction.commit();


    }
}
