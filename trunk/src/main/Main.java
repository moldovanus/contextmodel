package main;


import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.interfaces.resources.Actuator;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 11:04:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {

        File ontologyDataCenterFile = new File("ontology/context.owl");
        JenaOWLModel owlModel = null;
        try {
            owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyDataCenterFile.toURI().toString());
            OntologyModelFactory protegeFactory = new OntologyModelFactory(owlModel);
            Collection<Actuator> actuators = protegeFactory.getAllActuatorInstances();
            Iterator<Actuator> currentActuator = actuators.iterator();
            boolean over = false;
            Actuator ac = null;
            while (!over) {
                if (currentActuator.hasNext()) {
                    ac = currentActuator.next();
                    System.out.println("Actuator 1" + ac.getResourceID());
                } else over = true;

            }

        } catch (OntologyLoadException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        OntModel policyConversionModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        policyConversionModel.add(owlModel.getJenaModel());
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        Model emptyModel = ModelFactory.createDefaultModel();

        /*
Configuration config =
new Configuration();
config.configure("/utils/databaseAccess/hibernate.cfg.xml");
//  SessionFactory sessionFactory = config.buildSessionFactory();   //aaaaaaaa

SchemaExport export = new SchemaExport(config);
export.drop(false, true);
export.create(false, true);
List exceptions = export.getExceptions();
for (Object o : exceptions) {
System.out.println(o.toString());
}
        */


//        HibernateUtil.recreateDatabase();
//
////         private String policyName;
////    private String evaluationCondition;
////
////    private boolean respected;
////    private List<ContextResource> policySubject;
////    private List<ContextResource> policyTarget;
////
////    private Event triggerEvent;
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
//        SimpleResourceImpl contextResource_2 = new SimpleResourceImpl();
//        contextResource_2.setResourceWorkLoadProperty("BBB");
//        //contextResource_2.setId("IDDDD_3");
//
//        contextResourceList_1.add(contextResource_1);
//        contextPolicy.setPolicySubject(contextResourceList_1);
//
//        contextResourceList_2.add(contextResource_2);
//        contextPolicy.setPolicyTarget(contextResourceList_2);
//
//        contextPolicy.setTriggerEvent(new EventImpl());
//        Session session = HibernateUtil.getSession();
//        Transaction transaction = session.beginTransaction();
////        Criteria criteria = session.createCriteria(ContextPolicyImpl.class);
////        List<ContextPolicyImpl> list = criteria.list();
////        for (ContextPolicyImpl cr : list) {
////            System.out.println(((ContextResource) cr.getPolicyTarget().get(0)).getResourceID());
////        }
//        session.save(contextPolicy);
//        session.flush();
//        transaction.commit();
//


    }
}
