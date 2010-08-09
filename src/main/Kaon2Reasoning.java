package main;

import evaluation.InstanceGenerator;
import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.util.ModelAccess;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.logic.Literal;
import org.semanticweb.kaon2.api.logic.Rule;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.logic.Variable;
import org.semanticweb.kaon2.api.owl.elements.*;
import org.semanticweb.kaon2.api.reasoner.Query;
import org.semanticweb.kaon2.api.reasoner.Reasoner;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 11:22:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Kaon2Reasoning {
    public static String DATABASE_ONTOLOGY_DESCRIPTOR_URI = "file:src/model/impl/kaonImpl/dao/database_ontology_mapping.xml";
    public static String FILE_ONTOLOGY_DESCRIPTOR_URI = "file:src/model/impl/kaonImpl/dao/file_ontology.xml";

    public void initializeOntology() {

        Logger logger = Logger.getLogger(Kaon2Reasoning.class);
        PropertyConfigurator.configure("D:\\contextmodel\\src\\model\\impl\\databaseImpl\\dao\\log4j.properties");
        HibernateUtil.recreateDatabase();
        ModelAccess modelAccess = InstanceGenerator.generatePolicyInstances(10, ModelAccess.DATABASE_ACCESS);
//        modelAccess = InstanceGenerator.generateComplexResourceInstances(2, ModelAccess.DATABASE_ACCESS);

        System.out.println("created instances");
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
        Ontology ontology_1 = null;
        Ontology ontology_2 = null;
        String ontologyURI_1 = null;
        String ontologyURI_2 = "http://coned.dsrl.com/database/contextmodel#";
        try {
            System.out.println("registering ontology");
            resolver.registerReplacement(ontologyURI_2, DATABASE_ONTOLOGY_DESCRIPTOR_URI);
            ontologyURI_1 = resolver.registerOntology(FILE_ONTOLOGY_DESCRIPTOR_URI);
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ontologyManager.setOntologyResolver(resolver);
        try {
            System.out.println("opening ontology");
            ontology_1 = ontologyManager.openOntology(ontologyURI_1,
                    new HashMap<String, Object>());
            ontology_2 = ontologyManager.openOntology(ontologyURI_2,
                    new HashMap<String, Object>());
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // URL : "http://coned.dsrl.com/contextmodel#"
        createRulesForPolicies(ontology_1, "http://coned.dsrl.com/contextmodel#", modelAccess);
//
//        OntologyManager ontologyManager = null;
//        try {
//            ontologyManager = KAON2Manager.newOntologyManager();
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
//        String simpleOntologyURL = "http://www.owl-ontologies.com/Ontology1280294013.owl#";
//        String ontologyURL = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#";
//        resolver.registerReplacement(ontologyURL, "file:./ontology/a.owl");
//        ontologyManager.setOntologyResolver(resolver);
//        Ontology ontology = null;
//        try {
//            ontology = ontologyManager.openOntology(ontologyURL, new HashMap<String, Object>());
//        } catch (Exception e) {
//            System.err.println(e.getCause());  //To change body of catch statement use File | Settings | File Templates.
//        }
//        createRulesForPolicies(ontology_1, ontologyURI_1);
        // We now create a sample ontology describing relationships among objects in a domain.
//        OWLClass contextResource = KAON2Manager.factory().owlClass(ontologyURL + "ContextResource");
//        OWLClass serviceCenterITFacilityResource = KAON2Manager.factory().owlClass(ontologyURL + "ServiceCenterITFacilityResource");
//        OWLClass ITFacilityPassiveResource = KAON2Manager.factory().owlClass(ontologyURL + "ITFacilityPassiveResource");
//        OWLClass contextAction = KAON2Manager.factory().owlClass(ontologyURL + "ContextAction");
//
//        DataProperty hasAssociatedActions = KAON2Manager.factory().dataProperty(ontologyURL + "hasAssociatedActions");
//
//        ObjectProperty associatedActions = KAON2Manager.factory().objectProperty(ontologyURL + "associatedActions");
//
//        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();

        // We now add describe the domain of the ontology.
//        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(serviceCenterITFacilityResource, contextResource), OntologyChangeEvent.ChangeType.ADD));
//        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(ITFacilityPassiveResource, contextResource), OntologyChangeEvent.ChangeType.ADD));
//        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(ITFacilityPassiveResource, serviceCenterITFacilityResource), OntologyChangeEvent.ChangeType.ADD));
//
//        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyDomain(associatedActions, contextAction), OntologyChangeEvent.ChangeType.ADD));

        // We now create a rule that axiomatizes the following relationship:
        //
        // If
        //    a person X works on a project Y, and
        //    the project Y is about a topic Z,
        // then
        //    the person X knows about topic Z.
        //
        // In Prolog, this rule would be written like this:
        //     personKnowsAboutTopic(X,Z) :- worksOn(X,Y), projectHasTopic(Y,Z).
        //       ITFacilityPassiveResource(X) :- ServiceCenterITFacilityResource(X), recordedValue(X,V),
        //                                       associatedActions(X,A).
        // Although the practice often disputes this rule, we shall pretend that we live in a perfect
        // world where only competent people are woking on interesting projects. (sigh!)
        //
        // The above rule is directly converted into an object strucutre. We first create the variables X, Y and Z:
//        Variable X = KAON2Manager.factory().variable("X");
//        Variable V = KAON2Manager.factory().variable("V");
//        Variable A = KAON2Manager.factory().variable("A");
//        Literal serviceCenterITFacilityResource_X = KAON2Manager.factory().literal(true, serviceCenterITFacilityResource, new Term[]{X});
//        Literal associatedActions_X_A = KAON2Manager.factory().literal(true, hasAssociatedActions, new Term[]{X, A});
//        Literal ITFacilityPassiveResource_X = KAON2Manager.factory().literal(true, ITFacilityPassiveResource, new Term[]{X});
//        Literal check = KAON2Manager.factory().literal(false, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = true"),
//                A);
//        long start, end, total;
//        start = System.currentTimeMillis();
//        long used1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        // We now create the rule.
//        Rule rule = KAON2Manager.factory().rule(
//                ITFacilityPassiveResource_X,                          // this is the rule head, i.e. the consequent of the rule
//                new Literal[]{serviceCenterITFacilityResource_X, associatedActions_X_A, check}   // this is the rule body, i.e. the condition of the rule
//        );
//        try {
//            // Rule is a kind of axiom, so it can be added to the ontology in the same way as
//            // any axiom is added, i.e. by an OntologyChangeEvent.
//            changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
//            System.out.println(rule.toString());
//
//            ontology.applyChanges(changes);
//            end = System.currentTimeMillis();
//            total = end - start;
//            long used2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//            System.out.println("Total" + total);
//            System.out.println("Total memory "+ (used2-used1)/(1024.0*1024.0));
//            Reasoner reasoner = null;
//            reasoner = ontology.createReasoner();
//
//            Query whoIsPassive = reasoner.createQuery(new Literal[]{
//                    KAON2Manager.factory().literal(true, ITFacilityPassiveResource, new Term[]{X}),
//            }, new Variable[]{X});
//           Query whoIsPassive = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
//                   " ?x rdf:type <"+ontologyURL+"ITFacilityPassiveResource> ;} ");
//            System.out.println();
//            System.out.println("-----------The list of passive resources:-------------");
//            whoIsPassive.open();
//
//            // We now iterate over the query results.
//            while (!whoIsPassive.afterLast()) {
//                // A query result is a set of tuples. The values in each tuple correspond to the distinguished variables.
//                // In the above example, the distinguished variables are [X,Y]; this means that the first object in
//                // the tuple is the value for the X variable, and the second one is the value for the Y variable.
//                Term[] tupleBuffer = whoIsPassive.tupleBuffer();
//                System.out.println("Resource '" + tupleBuffer[0].toString() + "is passive.");
//                whoIsPassive.next();
//            }
//            end = System.currentTimeMillis();
//            total = end - start;
//            System.out.println("Total" + total);
//            whoIsPassive.close();
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    public void createRulesForPolicies(Ontology ontology, String ontologyURL, ModelAccess modelAccess) {

        OWLClass energyPolicy = KAON2Manager.factory().owlClass(ontologyURL + "ServiceCenterITComputingPolicy");
        OWLClass simpleResource = KAON2Manager.factory().owlClass(ontologyURL + "ServiceCenterITComputingResource");
        OWLClass complexResource = KAON2Manager.factory().owlClass(ontologyURL + "ComplexResource");
        OWLClass itComputingContextPolicy = KAON2Manager.factory().owlClass(ontologyURL + "ITComputingContextPolicy");

        ObjectProperty associatedComplexResource = KAON2Manager.factory().objectProperty(ontologyURL + "policySubject");
        ObjectProperty associatedSimpleResources = KAON2Manager.factory().objectProperty(ontologyURL + "simpleResources");
        DataProperty policyName = KAON2Manager.factory().dataProperty(ontologyURL + "policyName");
        DataProperty resourceID = KAON2Manager.factory().dataProperty(ontologyURL + "resourceID");
        DataProperty currentWorkload = KAON2Manager.factory().dataProperty(ontologyURL + "currentWorkload");
        DataProperty maxWorkload = KAON2Manager.factory().dataProperty(ontologyURL + "maximumWorkload");
        DataProperty optimalWorkload = KAON2Manager.factory().dataProperty(ontologyURL + "optimalWorkload");

        DataProperty isRespected = KAON2Manager.factory().dataProperty(ontologyURL + "isRespected");
        DataRange range = null;
        DataCardinality cardinality = null;
        try {
            range = KAON2Manager.factory().dataRange("<xsd:boolean>", Namespaces.INSTANCE);
            cardinality = KAON2Manager.factory().dataCardinality(1, 1, isRespected, range);

        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        //KAON2Manager.factory().dataPropertyAttribute(maxWorkload,DataPropertyAttribute.DATA_PROPERTY_FUNCTIONAL );
        // DataPropertyAttribute.DATA_PROPERTY_FUNCTIONAL

        Variable X = KAON2Manager.factory().variable("X");
        Variable Y = KAON2Manager.factory().variable("Y");
        Variable Z = KAON2Manager.factory().variable("Z");
        Variable assocResource = KAON2Manager.factory().variable("assocResource");
        Variable complexRes = KAON2Manager.factory().variable("complexResource");
        Variable complexResourceID = KAON2Manager.factory().variable("resourceID");
        Literal card = KAON2Manager.factory().literal(true, cardinality, new Term[]{X});

        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();
        // changes.add(new OntologyChangeEvent(card,OntologyChangeEvent.ChangeType.ADD));
        /*****************Add energy policies****************************/
        try {
            Reasoner reasoner = ontology.createReasoner();
            Collection<ITComputingContextPolicy> energyPolicies = modelAccess.getAllITComputingContextPolicyInstances();
            Iterator<ITComputingContextPolicy> energyPoliciesIterator = energyPolicies.iterator();
            while (energyPoliciesIterator.hasNext()) {
                // Term[] tupleBuffer = allEnergyPolicies.tupleBuffer();
                ITComputingContextPolicy currentPolicy = energyPoliciesIterator.next();
                ContextResource target = currentPolicy.getPolicySubject().get(0);
                String nameOfPolicy = currentPolicy.getName();
                //  policy = KAON2Manager.factory().individual(ontologyURL+"nameOfPolicy");
                List<ContextResource> contextRes = currentPolicy.getPolicySubject();
                String complexID = contextRes.get(0).getResourceID();
                Individual targetIndividual = KAON2Manager.factory().individual(ontologyURL + "id" + currentPolicy.getId());
                // Literal enforceRespected = KAON2Manager.factory().literal(true, KAON2Manager.factory().ifTrue(1), Z);
                Literal energyPolicy_X = KAON2Manager.factory().literal(true, energyPolicy, new Term[]{X});
                Literal isRespectedPolicy_X =
                        KAON2Manager.factory().literal(true, isRespected, new Term[]{targetIndividual, KAON2Manager.factory().constant(true)});
//                Literal energyPolicyName =
//                        KAON2Manager.factory().literal(true, policyName, new Term[]{targetIndividual, KAON2Manager.factory().constant(nameOfPolicy)});
////                Literal checkPolicyName =
////                        KAON2Manager.factory().literal(true, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = " + nameOfPolicy), Y);
                Literal associatedComplexResource_X =
                        KAON2Manager.factory().literal(true, associatedComplexResource, new Term[]{targetIndividual, complexRes});
                Literal complexResID =
                        KAON2Manager.factory().literal(true, resourceID, new Term[]{complexRes, complexResourceID});
                Literal checkResID =
                        KAON2Manager.factory().literal(true, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = " + complexID), complexResourceID);

                Literal[] arrayOfLiterals = new Literal[100];
                int i = 0;
                int simpleResourceNumber = 0;

//                arrayOfLiterals[i++] = energyPolicy_X;
//                arrayOfLiterals[i++] = energyPolicyName;
//                arrayOfLiterals[i++] = checkPolicyName;
                arrayOfLiterals[i++] = associatedComplexResource_X;
//                arrayOfLiterals[i++] = complexResID;
//                arrayOfLiterals[i++] = checkResID;
                Axiom cardinal = KAON2Manager.factory().axiom("[dataAtMost 1 http://coned.dsrl.com/contextmodel#isRespected]", Namespaces.INSTANCE);
                ontology.addAxiom(cardinal);


                Collection<ServiceCenterITComputingResource> simpleResources = ((ComplexResource) contextRes.get(0)).getResources();
                Iterator<ServiceCenterITComputingResource> simpleResourceIterator = simpleResources.iterator();
                while (simpleResourceIterator.hasNext()) {
                    ServiceCenterITComputingResource currentSimpleResource = simpleResourceIterator.next();
                    Variable currentWorkloadi = KAON2Manager.factory().variable("currentWorkload" + simpleResourceNumber);
                    Variable maxWorkloadi = KAON2Manager.factory().variable("maximumWorkload" + simpleResourceNumber);
                    Variable optimalWorkloadi = KAON2Manager.factory().variable("optimalWorkload" + simpleResourceNumber);
                    Variable simpleResIDi = KAON2Manager.factory().variable("resourceID" + simpleResourceNumber);
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, simpleResource, new Term[]{assocResource});
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, resourceID, new Term[]{assocResource, simpleResIDi});
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = " + currentSimpleResource.getResourceID()), simpleResIDi);
//                    /**********************check constraints*****************/
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, currentWorkload, new Term[]{assocResource, currentWorkloadi});
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, optimalWorkload, new Term[]{assocResource, optimalWorkloadi});
//                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, maxWorkload, new Term[]{assocResource, maxWorkloadi});
////                    arrayOfLiterals[i++] = KAON2Manager.factory().literal(true, KAON2Manager.factory().ifTrue(4),
//                            new Term[]{
//                                    KAON2Manager.factory().constant("($2/2.0) <=$1  &&  $1<($2+($3-$2)/2.0)"),
//                                    currentWorkloadi, optimalWorkloadi, maxWorkloadi,
//                            });
                    simpleResourceNumber++;
                }
                Literal[] literals = new Literal[i];
                for (int j = 0; j < i; j++) {
                    literals[j] = arrayOfLiterals[j];
                }
                Rule rule = KAON2Manager.factory().rule(
                        isRespectedPolicy_X,                          // this is the rule head, i.e. the consequent of the rule
                        literals  // this is the rule body, i.e. the condition of the rule
                );
                changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
            }

////            Query getAllPolicies = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
////                    " ?x rdf:type <" + ontologyURL + "ITComputingContextPolicy> ;}");
//         
            ontology.applyChanges(changes);
            Request request = ontology.createAxiomRequest(Rule.class);
            Set<Rule> ruleSet = request.getAll();
            for (Rule r : ruleSet) {
                System.out.println(r.toString());
            }

            Query getAllPolicies = reasoner.createQuery(new Literal[]{
//                    KAON2Manager.factory().literal(true, isRespected, new Term[]{X, Y}),
                    KAON2Manager.factory().literal(true, KAON2Manager.factory().dataProperty(ontologyURL + "policyName"), new Term[]{X, Z})
            }, new Variable[]{X, Z});
            getAllPolicies.open();
            while (!getAllPolicies.afterLast()) {
                Term[] tupleBuffer = getAllPolicies.tupleBuffer();
                for (Term aTupleBuffer : tupleBuffer) {
                    System.out.print("  " + aTupleBuffer);
                }
                System.out.println();
                getAllPolicies.next();
            }

            getAllPolicies = reasoner.createQuery(new Literal[]{
                    KAON2Manager.factory().literal(true, isRespected, new Term[]{X, Y}),
//                    KAON2Manager.factory().literal(true, KAON2Manager.factory().dataProperty(ontologyURL+"policyName"), new Term[]{X, Z})
            }, new Variable[]{X, Y});
            getAllPolicies.open();
            while (!getAllPolicies.afterLast()) {
                Term[] tupleBuffer = getAllPolicies.tupleBuffer();
                for (Term aTupleBuffer : tupleBuffer) {
                    System.out.print("  " + aTupleBuffer);
                }
                System.out.println();
                getAllPolicies.next();
            }
            energyPolicies = modelAccess.getAllITComputingContextPolicyInstances();
            energyPoliciesIterator = energyPolicies.iterator();
            while (energyPoliciesIterator.hasNext()) {
                System.out.println(energyPoliciesIterator.next().isRespected());
            }
            reasoner.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void simpleOnt() {
        //http://www.owl-ontologies.com/Ontology1280211994.owl
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();

        resolver.registerReplacement("http://www.owl-ontologies.com/Ontology1280211994.owl#", "file:./ontology/newTry.owl");
        ontologyManager.setOntologyResolver(resolver);
        Ontology ontology = null;
        try {
            ontology = ontologyManager.openOntology("http://www.owl-ontologies.com/Ontology1280211994.owl#", new HashMap<String, Object>());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // We now create a sample ontology describing relationships among objects in a domain.
        OWLClass myClass = KAON2Manager.factory().owlClass("http://www.owl-ontologies.com/Ontology1280211994.owl#myClass");
        OWLClass activeClass = KAON2Manager.factory().owlClass("http://www.owl-ontologies.com/Ontology1280211994.owl#activeClass");
        OWLClass passiveClass = KAON2Manager.factory().owlClass("http://www.owl-ontologies.com/Ontology1280211994.owl#passiveClass");
        OWLClass contextAction = KAON2Manager.factory().owlClass("http://www.owl-ontologies.com/Ontology1280211994.owl#Action");
        ObjectProperty associatedActions = KAON2Manager.factory().objectProperty("http://www.owl-ontologies.com/Ontology1280211994.owl#associatedActions");
        DataProperty hasAssociatedActions = KAON2Manager.factory().dataProperty("http://www.owl-ontologies.com/Ontology1280211994.owl#hasAssociatedActions");
        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();

        // We now add describe the domain of the ontology.
        //  changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(serviceCenterITFacilityResource, contextResource), OntologyChangeEvent.ChangeType.ADD));
        //   changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(ITFacilityPassiveResource, contextResource), OntologyChangeEvent.ChangeType.ADD));
        //  changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(ITFacilityPassiveResource, serviceCenterITFacilityResource), OntologyChangeEvent.ChangeType.ADD));

        // changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyDomain(associatedActions, contextAction), OntologyChangeEvent.ChangeType.ADD));

        // We now create a rule that axiomatizes the following relationship:
        //
        // If
        //    a person X works on a project Y, and
        //    the project Y is about a topic Z,
        // then
        //    the person X knows about topic Z.
        //
        // In Prolog, this rule would be written like this:
        //     personKnowsAboutTopic(X,Z) :- worksOn(X,Y), projectHasTopic(Y,Z).
        //       ITFacilityPassiveResource(X) :- ServiceCenterITFacilityResource(X), recordedValue(X,V),
        //                                       associatedActions(X,A).
        // Although the practice often disputes this rule, we shall pretend that we live in a perfect
        // world where only competent people are working on interesting projects. (sigh!)
        //
        // The above rule is directly converted into an object strucutre. We first create the variables X, Y and Z:
        Variable X = KAON2Manager.factory().variable("X");
        Variable A = KAON2Manager.factory().variable("A");

        // We now create the literals (notice that all of them are positive):
        Literal serviceCenterITFacilityResource_X = KAON2Manager.factory().literal(true, myClass, new Term[]{X});
        Literal associatedActions_X_A = KAON2Manager.factory().literal(true, hasAssociatedActions, new Term[]{X, A});
        Literal ITFacilityPassiveResource_X = KAON2Manager.factory().literal(true, passiveClass, new Term[]{X});
        Literal check = KAON2Manager.factory().literal(false, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = true"),
                A);
        // We now create the rule.
        long start, end, total;
        start = System.currentTimeMillis();
        Rule rule = KAON2Manager.factory().rule(
                ITFacilityPassiveResource_X,                          // this is the rule head, i.e. the consequent of the rule
                new Literal[]{serviceCenterITFacilityResource_X, associatedActions_X_A, check}   // this is the rule body, i.e. the condition of the rule
        );
        try {
            // Rule is a kind of axiom, so it can be added to the ontology in the same way as
            // any axiom is added, i.e. by an OntologyChangeEvent.
            changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
            System.out.println(rule.toString());

            ontology.applyChanges(changes);
            end = System.currentTimeMillis();
            total = end - start;
            System.out.println("Total" + total);
            Reasoner reasoner = null;
            reasoner = ontology.createReasoner();
            Query whoIsPassive = reasoner.createQuery(new Literal[]{
                    KAON2Manager.factory().literal(true, passiveClass, new Term[]{X}),
            }, new Variable[]{X});

            System.out.println();
            System.out.println("-----------The list of passive resources:-------------");
            whoIsPassive.open();

            // We now iterate over the query results.
            while (!whoIsPassive.afterLast()) {
                // A query result is a set of tuples. The values in each tuple correspond to the distinguished variables.
                // In the above example, the distinguished variables are [X,Y]; this means that the first object in
                // the tuple is the value for the X variable, and the second one is the value for the Y variable.
                Term[] tupleBuffer = whoIsPassive.tupleBuffer();
                System.out.println("Resource '" + tupleBuffer[0].toString() + "is passive.");
                whoIsPassive.next();
            }

            whoIsPassive.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String args[]) {
        Kaon2Reasoning kaon2Reasoning = new Kaon2Reasoning();
        kaon2Reasoning.initializeOntology();


    }
}
