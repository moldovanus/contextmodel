package main;


import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.swrl.SWRLRuleEngine;
import edu.stanford.smi.protegex.owl.swrl.SWRLRuleEngineFactory;
import edu.stanford.smi.protegex.owl.swrl.bridge.BridgeFactory;
import edu.stanford.smi.protegex.owl.swrl.bridge.SWRLRuleEngineBridge;
import edu.stanford.smi.protegex.owl.swrl.bridge.exceptions.SWRLRuleEngineBridgeException;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.SWRLJessBridge;
import edu.stanford.smi.protegex.owl.swrl.exceptions.SWRLRuleEngineException;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.resources.Actuator;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.SimpleResource;
import model.interfaces.resources.applications.ApplicationActivity;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 11:04:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static JenaOWLModel owlModel;
    static OntModel ontModel;
    static OntologyModelFactory protegeFactory;

    public static void main(String args[]) {

        File ontologyDataCenterFile = new File(OntologyModelFactory.ontologyFile);
        protegeFactory = null;
        owlModel = null;
        try {
            owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyDataCenterFile.toURI().toString());
            protegeFactory = new OntologyModelFactory();
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
        /***************************Pellet Reasoner*****************************/
        ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        ontModel.add(owlModel.getJenaModel());
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        Model emptyModel = ModelFactory.createDefaultModel();
        InfModel model = ModelFactory.createInfModel(reasoner, emptyModel);
        model.add(owlModel.getJenaModel());


        /********************** Rete bridge ******************************/
        jess.Rete testRete = new jess.Rete();
        try {
            SWRLJessBridge bridge = new SWRLJessBridge(owlModel, testRete);
        } catch (SWRLRuleEngineBridgeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /*********************************** Print all individuals ******************************/
//        Collection classes = owlModel.getUserDefinedOWLNamedClasses();
//        for (Iterator it = classes.iterator(); it.hasNext();) {
//            OWLNamedClass cls = (OWLNamedClass) it.next();
//            Collection instances = cls.getInstances(false);
//            System.out.println("Class " + cls.getBrowserText() + "(" + instances.size() + ")");
//            for (Iterator jt = instances.iterator(); jt.hasNext();) {
//                OWLIndividual individual = (OWLIndividual) jt.next();
//                System.out.println(" - " + individual.getBrowserText());
//            }
//        }
        /***************** Protege SWRL  - add policies + infer ******************/
        Collection<ITComputingContextPolicy> energyPolicies = protegeFactory.getAllITComputingContextPolicyInstances();
        SWRLFactory factory = new SWRLFactory(owlModel);
        SWRLRuleEngineBridge bridge = null;
        try {
            bridge = BridgeFactory.createBridge(owlModel);
        } catch (SWRLRuleEngineBridgeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SWRLRuleEngine ruleEngine = null;
        try {
            ruleEngine = SWRLRuleEngineFactory.create(owlModel);
            ruleEngine.run();
            ruleEngine.reset();
        } catch (SWRLRuleEngineException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String swrlRule = "";
        for (Iterator it = energyPolicies.iterator(); it.hasNext();) {
            try {
                ITComputingContextPolicy currentPolicy = (ITComputingContextPolicy) it.next();
                ComplexResource compResource = (ComplexResource) currentPolicy.getPolicySubject().get(0);
                swrlRule = "";
                List<ServiceCenterITComputingResource> assocResource = compResource.getResources();
                int a = 0;
                for (Iterator simpleIter = assocResource.iterator(); simpleIter.hasNext();) {
                    SimpleResource simpleResource = (SimpleResource) simpleIter.next();
                    swrlRule += "ContextModel:SimpleResource(?a" + a + ") ^ ContextModel:resourceID(?a" + a + ",\"" + simpleResource.getResourceID() + "\") ^ ContextModel:currentWorkload(?a" + a + ",?currentWorkload" + a + ") ^ ContextModel:maximumWorkload(?a" + a + ",?maxWorkload" + a + ") ^ swrlb:lessThanOrEqual(?currentWorkload" + a + ",?maximumWorkload" + a + ")^ swrlb:multiply(?optimalWorkload0,0.33,?downDif" + a + ") ^  \n" +
                            "sqwrl:difference(?maxWorkload" + a + ", ?optimalWorkload" + a + ",?sumOf" + a + ")  ^  " +
                            "sqwrl:difference(?optimalWorkload" + a + ",?downDif" + a + ",?minThreshold" + a + ")  ^  " +
                            " swrlb:divide(?sumOf" + a + ", 2,?upDif" + a + ") ^ \n" +
                            "sqwrl:sum(?optimalWorkload" + a + ",?upDif" + a + ",?maxThreshold" + a + " )   ^ swrlb:lessThanOrEqual(?minThreshold" + a + ",?currentWorkload" + a + ")^ swrlb:lessThanOrEqual(?currentWorkload" + a + ",?maxThreshold" + a + ") ^ ";
                    a++;
                }
                System.out.println(swrlRule + "ContextModel:ComplexResource(?x) ^ ContextModel:ResourceID(?x," + compResource.getResourceID() + ") ^  ContextModel:currentWorkload(?x, ?currentWorkload) ^  ContextModel:maximumWorkload(?x, ?maxWorkload) ^  swrlb:lessThanOrEqual(?currentWorkload, ?maximumWorkload) -> isRespected(ContextModel:" + currentPolicy.getPolicyName() + ", true)");
                SWRLImp imp = factory.createImp(swrlRule + "ContextModel:ComplexResource(?x) ^  ContextModel:currentWorkload(?x, ?currentWorkload) ^  ContextModel:maximumWorkload(?x, ?maxWorkload) ^  swrlb:lessThanOrEqual(?currentWorkload, ?maximumWorkload) -> ContextModel:isRespected(ContextModel:" + currentPolicy.getPolicyName() + ", true)");

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        Collection<BusinessPolicy> businessPolicies = protegeFactory.getAllBusinessPolicyInstances();
        for (Iterator it = businessPolicies.iterator(); it.hasNext();) {
            BusinessPolicy businessPolicy = (BusinessPolicy) it.next();
            ApplicationActivity applicationActivity = (ApplicationActivity) businessPolicy.getPolicySubject().get(0);
            try {

                swrlRule += "ContextModel:ApplicationActivity(?x) ^ ContextModel:resourceID(?x,\"" + applicationActivity.getResourceID() + "\") ^ ContextModel:memAllocatedValue(?x,?memAllocated) ^ ContextModel:memRequiredValue(?x, ?memRequiredValue) ^ swrlb:lessThanOrEqual(?memAllocatedValue,?memRequiredValue) \n " +
                        " ^ ContextModel:hddAllocatedValue(?x,?hddAllocated) ^ ContextModel:hddRequiredValue(?x, ?hddRequiredValue) ^ swrlb:lessThanOrEqual(?hddAllocatedValue,?hddRequiredValue)  \n" +
                        " ^ ContextModel:cpuAllocatedValue(?x,?cpuAllocated) ^ ContextModel:cpuRequiredValue(?x, ?cpuRequiredValue) ^ swrlb:lessThanOrEqual(?cpuAllocatedValue,?cpuRequiredValue) ";
                System.out.println(swrlRule);
                SWRLImp imp = factory.createImp(swrlRule);

            } catch (SWRLParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }


        /************** Protege reasoner does not work **************/
//        try {
//            ruleEngine.infer();
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


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

    public static void printIterator(Iterator<?> i, String header) {
        System.out.println(header);
        for (int c = 0; c < header.length(); c++)
            System.out.print("=");
        System.out.println();

        if (i.hasNext()) {
            while (i.hasNext())
                System.out.println(i.next());
        } else
            System.out.println("<EMPTY>");

        System.out.println();
    }
}
