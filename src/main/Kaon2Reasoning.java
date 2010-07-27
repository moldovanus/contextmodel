package main;

import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.logic.Literal;
import org.semanticweb.kaon2.api.logic.Rule;
import org.semanticweb.kaon2.api.logic.Term;
import org.semanticweb.kaon2.api.logic.Variable;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.semanticweb.kaon2.api.reasoner.Query;
import org.semanticweb.kaon2.api.reasoner.Reasoner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 11:22:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Kaon2Reasoning {
    public void initializeOntology() {
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
        String ontologyURL = "http://www.owl-ontologies.com/Ontology1280294013.owl#";
        resolver.registerReplacement(ontologyURL, "file:./ontology/myNewOntology.rdf-xml.owl");
        ontologyManager.setOntologyResolver(resolver);
        Ontology ontology = null;
        try {
            ontology = ontologyManager.openOntology(ontologyURL, new HashMap<String, Object>());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // We now create a sample ontology describing relationships among objects in a domain.
        OWLClass contextResource = KAON2Manager.factory().owlClass(ontologyURL + "ContextResource");
        OWLClass serviceCenterITFacilityResource = KAON2Manager.factory().owlClass(ontologyURL + "ServiceCenterITFacilityResource");
        OWLClass ITFacilityPassiveResource = KAON2Manager.factory().owlClass(ontologyURL + "ITFacilityPassiveResource");
        OWLClass contextAction = KAON2Manager.factory().owlClass(ontologyURL + "ContextAction");

        DataProperty hasAssociatedActions = KAON2Manager.factory().dataProperty(ontologyURL + "hasAssociatedActions");

        ObjectProperty associatedActions = KAON2Manager.factory().objectProperty(ontologyURL + "associatedActions");

        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();

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
        Variable X = KAON2Manager.factory().variable("X");
        Variable V = KAON2Manager.factory().variable("V");
        Variable A = KAON2Manager.factory().variable("A");
        Literal serviceCenterITFacilityResource_X = KAON2Manager.factory().literal(true, serviceCenterITFacilityResource, new Term[]{X});
        Literal associatedActions_X_A = KAON2Manager.factory().literal(true, hasAssociatedActions, new Term[]{X, A});
        Literal ITFacilityPassiveResource_X = KAON2Manager.factory().literal(true, ITFacilityPassiveResource, new Term[]{X});
        Literal check = KAON2Manager.factory().literal(false, KAON2Manager.factory().ifTrue(2), KAON2Manager.factory().constant("$1 = true"),
                A);
        long start, end, total;
        start = System.currentTimeMillis();
        // We now create the rule.
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
                    KAON2Manager.factory().literal(true, ITFacilityPassiveResource, new Term[]{X}),
            }, new Variable[]{X});
//           Query whoIsPassive = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
//                   " ?x rdf:type <"+ontologyURL+"ITFacilityPassiveResource> ;} ");
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
            end = System.currentTimeMillis();
            total = end - start;
            System.out.println("Total" + total);
            whoIsPassive.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
        Rule rule = KAON2Manager.factory().rule(
                ITFacilityPassiveResource_X,                          // this is the rule head, i.e. the consequent of the rule
                new Literal[]{serviceCenterITFacilityResource_X, associatedActions_X_A, check}   // this is the rule body, i.e. the condition of the rule
        );
        try {
            // Rule is a kind of axiom, so it can be added to the ontology in the same way as
            // any axiom is added, i.e. by an OntologyChangeEvent.
            changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
            System.out.println(rule.toString());
            long start, end, total;
            start = System.currentTimeMillis();
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
