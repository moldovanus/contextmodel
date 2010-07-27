package main;

import evaluation.InstanceGenerator;
import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.util.ModelAccess;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.reasoner.Query;
import org.semanticweb.kaon2.api.reasoner.Reasoner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 11:22:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Kaon2Reasoning {
    public static String ONTOLOGY_DESCRIPTOR_URI = "file:src/model/impl/kaonImpl/dao/ontology_descriptor.xml";

    public void initializeOntology() {
        HibernateUtil.recreateDatabase();
        java.util.Date before = new Date();
        System.out.println(new Date());
        ModelAccess modelAccess = InstanceGenerator.generateComplexResourceInstances(75, ModelAccess.DATABASE_ACCESS);
        System.out.println("created instances");
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();

//        resolver.registerReplacement("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#",
//                "/model/impl/kaonImpl/dao/ontology_descriptor.xml");
//        ontologyManager.setOntologyResolver(resolver);

        Ontology ontology = null;

        String ontologyURI = null;
        try {
            System.out.println("registering ontology");
            ontologyURI = resolver.registerOntology(ONTOLOGY_DESCRIPTOR_URI);
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        ontologyManager.setOntologyResolver(resolver);

        try {
            System.out.println("opening ontology");
            ontology = ontologyManager.openOntology(ontologyURI,
                    new HashMap<String, Object>());
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        java.util.Date after = new Date();

        java.util.Date result = new Date(after.getTime() - before.getTime());
        System.out.println("Creation time: " + result.getMinutes() + ":" + result.getSeconds());
//        DatabaseModelFactory databaseModelFactory = new DatabaseModelFactory();
//        ApplicationActivity activity = databaseModelFactory.createHDDIntensiveActivity("AA_1");
//        activity.setCPUAllocatedValue(34.0);
//        databaseModelFactory.persistEntity(activity);


//        DataProperty goodResource =
//                KAON2Manager.factory().dataProperty("http://coned.dsrl.com/contextmodel#goodResourcePredicate");
//
//        DataProperty hasName =
//                KAON2Manager.factory().dataProperty("http://coned.dsrl.com/contextmodel#applicationActivityName");
//
//
//        // We now create a rule that axiomatizes the following relationship:
//        //
//        // If
//        //    a person X works on a project Y, and
//        //    the project Y is about a topic Z,
//        // then
//        //    the person X knows about topic Z.
//        //
//        // In Prolog, this rule would be written like this:
//        //     personKnowsAboutTopic(X,Z) :- worksOn(X,Y), projectHasTopic(Y,Z).
//        //
//        // Although the practice often disputes this rule, we shall pretend that we live in a perfect
//        // world where only competent people are woking on interesting projects. (sigh!)
//        //
//        // The above rule is directly converted into an object strucutre. We first create the variables X, Y and Z:
//        Variable X = KAON2Manager.factory().variable("X");
//        Variable Y = KAON2Manager.factory().variable("Y");
//        Variable Z = KAON2Manager.factory().variable("Z");
//
//        // We now create the literals (notice that all of them are positive):
//        Literal head = KAON2Manager.factory().literal(true, goodResource, new Term[]{X, Z});
//        Literal condition = KAON2Manager.factory().literal(true, hasName, new Term[]{X, Y});
//
//
//        // We now create the rule.
//        Rule rule = KAON2Manager.factory().rule(
//                head,                          // this is the rule head, i.e. the consequent of the rule
//                new Literal[]{condition}   // this is the rule body, i.e. the condition of the rule
//        );
//        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();
//        changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
////        try {
////            ontology.applyChanges(changes);
////        } catch (KAON2Exception e) {
////            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////        }

//         java.util.Date before = new Date();

//        ModelAccess modelAccess = InstanceGenerator.getModelAccessInstance(ModelAccess.PREVAYLER_ACCESS);
        //ModelAccess modelAccess = InstanceGenerator.generateComplexResourceInstances(5, ModelAccess.DATABASE_ACCESS);
        before = new Date();

        //long total = Runtime.getRuntime().totalMemory();
        long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory:" + used);

        Reasoner reasoner = null;
//
        try {
            reasoner = ontology.createReasoner();

            Query query = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
                    " ?x rdf:type <http://coned.dsrl.com/contextmodel#ContextEntity> ; " +
                    " <http://coned.dsrl.com/contextmodel#resourceName> ?Y; }");
            query.open();

            while (!query.afterLast()) {
                Term[] tupleBuffer = query.tupleBuffer();

                for (int i = 0; i < tupleBuffer.length; i++) {


                    // System.out.print(tupleBuffer[i]);

                }
                //System.out.println(" ]");
                query.next();
            }
            after = new Date();

            result = new Date(after.getTime() - before.getTime());
            System.out.println("Querry time: " + result.getMinutes() + ":" + result.getSeconds());

            query.dispose();
            reasoner.dispose();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        OntologyManager ontologyManager = null;
//        try {
//            ontologyManager = KAON2Manager.newOntologyManager();
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
//
////        resolver.registerReplacement("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#",
////                "/model/impl/kaonImpl/dao/ontology_descriptor.xml");
////        ontologyManager.setOntologyResolver(resolver);
//
//        Ontology ontology = null;
//        try {
//            String ontologyURI = resolver.registerOntology(ONTOLOGY_DESCRIPTOR_URI);
//
//            ontologyManager.setOntologyResolver(resolver);
//
//            ontology = ontologyManager.openOntology(ontologyURI,
//                    new HashMap<String, Object>());
//            Reasoner reasoner = ontology.createReasoner();
//            Query query = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
//                    " ?x rdf:type <http://coned.dsrl.com/contextmodel#ApplicationActivity> ; " +
//                    " <http://coned.dsrl.com/contextmodel#applicationActivityName> ?j; " +
//                    " <http://coned.dsrl.com/contextmodel#applicationActivityCPUAllocated> ?k; " +
//                    " <http://coned.dsrl.com/contextmodel#applicationActivityPerformanceDegradation> ?f }");
//            query.open();
//
//
//            while (!query.afterLast()) {
//                Term[] tupleBuffer = query.tupleBuffer();
//                System.out.print("[ ");
//                for (int i = 0; i < tupleBuffer.length; i++) {
//                    if (i != 0)
//                        System.out.print(", ");
//                    System.out.print(tupleBuffer[i]);
//
//                }
//                System.out.println(" ]");
//                query.next();
//            }
//            query.dispose();
//            reasoner.dispose();
//
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        System.out.println("-----------------------------------");

        try {
            ontology.saveOntology(OntologyFileFormat.OWL_XML, new File("C:\\Users\\Administrator\\Desktop\\contextmodel\\src\\model\\impl\\kaonImpl\\dao\\ontology_descriptor_1.xml"), "ISO-8859-1");
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String args[]) {
        Kaon2Reasoning kaon2Reasoning = new Kaon2Reasoning();
        kaon2Reasoning.initializeOntology();
    }
}
