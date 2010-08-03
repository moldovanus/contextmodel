package main;

import evaluation.InstanceGenerator;
import model.impl.databaseImpl.dao.HibernateUtil;
import model.impl.util.ModelAccess;
import model.interfaces.resources.applications.HDDIntensiveActivity;
import net.sf.ehcache.management.Cache;
import net.sf.ehcache.management.CacheManager;
import net.sf.ehcache.management.CacheStatistics;
import org.apache.log4j.Logger;
import org.semanticweb.kaon2.api.*;
import org.semanticweb.kaon2.api.formatting.OntologyFileFormat;
import org.semanticweb.kaon2.api.logic.*;
import org.semanticweb.kaon2.api.owl.elements.DataProperty;
import org.semanticweb.kaon2.api.owl.elements.Individual;
import org.semanticweb.kaon2.api.owl.elements.OWLClass;
import org.semanticweb.kaon2.api.owl.elements.ObjectProperty;
import org.semanticweb.kaon2.api.reasoner.Query;
import org.semanticweb.kaon2.api.reasoner.Reasoner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    public static String DATABASE_ONTOLOGY_DESCRIPTOR_URI = "file:src/model/impl/kaonImpl/dao/database_ontology_mapping.xml";
    public static String FILE_ONTOLOGY_DESCRIPTOR_URI = "file:src/model/impl/kaonImpl/dao/file_ontology.xml";

    public void initializeOntology() {

        Logger logger = Logger.getLogger(Kaon2Reasoning.class);

//        PropertyConfigurator.configure("C:\\Users\\Administrator\\Desktop\\contextmodel\\src\\model\\impl\\databaseImpl\\dao\\log4j.properties");


        HibernateUtil.recreateDatabase();
        java.util.Date before = new Date();
        System.out.println(new Date());
        ModelAccess modelAccess = InstanceGenerator.generateComplexResourceInstances(25, ModelAccess.DATABASE_ACCESS);
        System.out.println("created instances");
        OntologyManager ontologyManager = null;
        try {
            ontologyManager = KAON2Manager.newOntologyManager();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();


//        resolver.registerReplacement("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#",
//                "/model/impl/kaonImpl/dao/database_ontology_mapping.xml");
//        ontologyManager.setOntologyResolver(resolver);

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

        java.util.Date after = new Date();

        java.util.Date result = new Date(after.getTime() - before.getTime());


        before = new Date();
        HDDIntensiveActivity hddIntensiveActivity = modelAccess.getDatabaseModelFactory().createHDDIntensiveActivity("Test00");
        after = new Date();
        result = new Date(after.getTime() - before.getTime());
        System.out.println("Creation HDD time: " + result.getMinutes() + ":" + result.getSeconds());
        hddIntensiveActivity.setCPUAllocatedValue(32.0);
        hddIntensiveActivity.setMEMAllocatedValue(22.0);
        hddIntensiveActivity.setHDDAllocatedValue(12.0);
        hddIntensiveActivity.setCPUAllocatedValue(32.0);
        hddIntensiveActivity.setName("fff");
//
        before = new Date();
        modelAccess.getDatabaseModelFactory().persistEntity(hddIntensiveActivity);
        result = new Date(after.getTime() - before.getTime());
        System.out.println("Persist HDD time: " + result.getMinutes() + ":" + result.getSeconds());
//
        DataProperty applicationActivityCPUAllocated =
                KAON2Manager.factory().dataProperty("http://coned.dsrl.com/contextmodel#applicationActivityCPUAllocated");

        DataProperty hasName =
                KAON2Manager.factory().dataProperty("http://coned.dsrl.com/contextmodel#resourceName");

//
//         We now create a rule that axiomatizes the following relationship:
//
//         If
//            a person X works on a project Y, and
//            the project Y is about a topic Z,
//         then
//            the person X knows about topic Z.
//
//         In Prolog, this rule would be written like this:
//             personKnowsAboutTopic(X,Z) :- worksOn(X,Y), projectHasTopic(Y,Z).
//
//         Although the practice often disputes this rule, we shall pretend that we live in a perfect
//         world where only competent people are woking on interesting projects. (sigh!)
//
//         The above rule is directly converted into an object strucutre. We first create the variables X, Y and Z:
        Variable X = KAON2Manager.factory().variable("X");
        Variable Y = KAON2Manager.factory().variable("Y");
        Variable Z = KAON2Manager.factory().variable("Z");
        Constant c = KAON2Manager.factory().constant("Test");
        Constant c1 = KAON2Manager.factory().constant("0");

        // We now create the literals (notice that all of them are positive):
        Literal head = KAON2Manager.factory().literal(true, hasName, new Term[]{X, c});
        Literal condition = KAON2Manager.factory().literal(true, applicationActivityCPUAllocated, new Term[]{X, Z});


        // We now create the rule.
        Rule rule = KAON2Manager.factory().rule(
                head,                          // this is the rule head, i.e. the consequent of the rule
                new Literal[]{condition}   // this is the rule body, i.e. the condition of the rule
        );
        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();
        changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));
        try {
            ontology_1.applyChanges(changes);
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//         System.exit(1);
//         java.util.Date before = new Date();

//        ModelAccess modelAccess = InstanceGenerator.getModelAccessInstance(ModelAccess.PREVAYLER_ACCESS);
        //ModelAccess modelAccess = InstanceGenerator.generateComplexResourceInstances(5, ModelAccess.DATABASE_ACCESS);
        long a = System.currentTimeMillis();
        before = new Date();


        //long total = Runtime.getRuntime().totalMemory();
        long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory:" + used);

        Reasoner reasoner = null;
//
//        try {
//            reasoner = ontology_1.createReasoner();
//            Variable A = KAON2Manager.factory().variable("A");
//            Variable B = KAON2Manager.factory().variable("B");
//            Variable C = KAON2Manager.factory().variable("C");
//
//            OWLClass entity = KAON2Manager.factory().owlClass("http://coned.dsrl.com/contextmodel#ContextEntity");
//            Query query = reasoner.createQuery(new Literal[]{
////                    KAON2Manager.factory().literal(true, entity, new Term[]{A}),
//                    KAON2Manager.factory().literal(true, hasName, new Term[]{A, B}),
////                    KAON2Manager.factory().literal(true, applicationActivityCPUAllocated, new Term[]{X, Z}),
//            }, new Variable[]{A, B});
////            Query query = reasoner.createQuery(Namespaces.INSTANCE, "SELECT *   WHERE {" +
////                    " ?X rdf:type <http://coned.dsrl.com/contextmodel#ContextEntity> ; " +
////                    "  <http://coned.dsrl.com/contextmodel#resourceName> ?Y ;" +
////                    "   }");
//            try {
//                query.open();
//            } catch (Exception e) {
//                System.err.println(e.getCause());
//                e.printStackTrace();
//            }
//
//            while (!query.afterLast()) {
//                Term[] tupleBuffer = query.tupleBuffer();
//
//                for (Term aTupleBuffer : tupleBuffer) {
//                    System.out.print(aTupleBuffer + "//");
//                }
//                System.out.println(" ]");
//                query.next();
//            }
//            after = new Date();
//
//            result = new Date(after.getTime() - before.getTime());
//            System.out.println("Query time: " + result.getMinutes() + ":" + result.getSeconds());
//            System.out.println("Time in millis" + (System.currentTimeMillis() - a));
//            query.dispose();
//            reasoner.dispose();
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        try {
            ontology_2.persist();
        } catch (KAON2Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Collection<ApplicationActivity> activities = modelAccess.getAllApplicationActivityInstances();
        //for (ApplicationActivity activity : activities) {
        //System.out.println(activity.getName());
        // }
        CacheManager manager = new CacheManager(net.sf.ehcache.CacheManager.create());
        before = new Date();
        System.out.println(modelAccess.getAllContextElementInstances().size());

        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Hibernate Query 1 time: " + result.getMinutes() + ":" + result.getSeconds());

        before = new Date();
        System.out.println(modelAccess.getAllContextElementInstances().size());

        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Hibernate Query 2 time: " + result.getMinutes() + ":" + result.getSeconds());

        before = new Date();
        System.out.println(modelAccess.getAllContextElementInstances().size());

        after = new Date();

        result = new Date(after.getTime() - before.getTime());
        System.out.println("Hibernate Query 3 time: " + result.getMinutes() + ":" + result.getSeconds());

        List<Cache> caches = manager.getCaches();
        for (Cache cache : caches) {
            CacheStatistics cacheStatistics = cache.getStatistics();
            System.out.println("-----" + cache.getName() + "----");
            System.out.println("In memory:" + cacheStatistics.getMemoryStoreObjectCount()
                    + " OnDisk:" + cacheStatistics.getDiskStoreObjectCount()
                    + " Hits: " + cacheStatistics.getCacheHits()
                    + " Miss: " + cacheStatistics.getCacheMisses()
            );
        }


//        before = new Date();
//        Collection<ComplexResource> resources = modelAccess.getAllComplexResourceInstances();
//        System.out.println(resources.size());
//        for (ComplexResource resource : resources) {
//            List<ServiceCenterITComputingResource> list = resource.getResources();
//            for (ServiceCenterITComputingResource serviceCenterITComputingResource : list) {
//                List<ApplicationActivity> activities = serviceCenterITComputingResource.getRunningActivities();
//                for (ApplicationActivity activity : activities) {
////                    Double d = activity.getCPUAllocatedValue();
////                    d += 20;
////                    System.out.println(d);
//                }
//            }
//        }
//
//        after = new Date();
//        result = new Date(after.getTime() - before.getTime());
//        System.out.println("Hibernate Query CR time: " + result.getMinutes() + ":" + result.getSeconds());
//        OntologyManager ontologyManager = null;
//        try {
//            ontologyManager = KAON2Manager.newOntologyManager();
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
//
////        resolver.registerReplacement("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#",
////                "/model/impl/kaonImpl/dao/database_ontology_mapping.xml");
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
        //System.exit(1);
//        try {
//            ontology_2.saveOntology(OntologyFileFormat.OWL_XML, new File("C:\\ontology_descriptor_1.xml"), "ISO-8859-1");
//        } catch (KAON2Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

    }


    public static void main(String args[]) throws Exception {
        Kaon2Reasoning kaon2Reasoning = new Kaon2Reasoning();
        kaon2Reasoning.initializeOntology();
    }


    public void saveTestontology() throws Exception {
        // To create an ontology, we again start by creating a connection.
        // We again need to register a resolver that will provide a physical URI
        // for the ontology. In this example, the physical URI is relative to the current directory.
        OntologyManager ontologyManager = KAON2Manager.newOntologyManager();
        DefaultOntologyResolver resolver = new DefaultOntologyResolver();
        resolver.registerReplacement("http://kaon2.semanticweb.org/example04", "file:example04.xml");
        ontologyManager.setOntologyResolver(resolver);

        // We create an ontology by specifying its logical URI. The resolver provides the physical URI.
        // Up until now this example is the same as Example 2.
        Ontology ontology = ontologyManager.createOntology("http://kaon2.semanticweb.org/example04", new HashMap<String, Object>());

        // We now create a sample ontology describing relationships among objects in a domain.
        OWLClass person = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#person");
        OWLClass student = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#student");
        OWLClass professor = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#professor");

        OWLClass project = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#project");
        OWLClass euProject = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#euProject");
        OWLClass dfgProject = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#dfgProject");

        OWLClass topic = KAON2Manager.factory().owlClass("http://kaon2.semanticweb.org/example04#topic");

        ObjectProperty worksOn = KAON2Manager.factory().objectProperty("http://kaon2.semanticweb.org/example04#worksOn");
        ObjectProperty projectHasTopic = KAON2Manager.factory().objectProperty("http://kaon2.semanticweb.org/example04#projectHasTopic");
        ObjectProperty personKnowsAboutTopic = KAON2Manager.factory().objectProperty("http://kaon2.semanticweb.org/example04#personKnowsAboutTopic");

        Individual semanticWeb = KAON2Manager.factory().individual("http://kaon2.semanticweb.org/example04#semanticWeb");
        Individual descriptionLogics = KAON2Manager.factory().individual("http://kaon2.semanticweb.org/example04#descriptionLogics");
        Individual owl = KAON2Manager.factory().individual("http://kaon2.semanticweb.org/example04#owl");

        // We perform updates as in Example 2, by adding a sequence of axioms to the ontology.
        List<OntologyChangeEvent> changes = new ArrayList<OntologyChangeEvent>();

        // We now add describe the domain of the ontology.
        // All students are persons.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(student, person), OntologyChangeEvent.ChangeType.ADD));
        // All professors are persons.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(professor, person), OntologyChangeEvent.ChangeType.ADD));
        // EU projects are projects
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(euProject, project), OntologyChangeEvent.ChangeType.ADD));
        // DFG projects are projects
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().subClassOf(dfgProject, project), OntologyChangeEvent.ChangeType.ADD));
        // Persons work on projects.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyDomain(worksOn, person), OntologyChangeEvent.ChangeType.ADD));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyRange(worksOn, project), OntologyChangeEvent.ChangeType.ADD));
        // Projects have topics.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyDomain(projectHasTopic, project), OntologyChangeEvent.ChangeType.ADD));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyRange(projectHasTopic, topic), OntologyChangeEvent.ChangeType.ADD));
        // Persons know about topics.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyDomain(personKnowsAboutTopic, person), OntologyChangeEvent.ChangeType.ADD));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyRange(personKnowsAboutTopic, topic), OntologyChangeEvent.ChangeType.ADD));
        // Semantic Web, description logics and OWL are topics.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(topic, semanticWeb), OntologyChangeEvent.ChangeType.ADD));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(topic, descriptionLogics), OntologyChangeEvent.ChangeType.ADD));
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(topic, owl), OntologyChangeEvent.ChangeType.ADD));

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
        //
        // Although the practice often disputes this rule, we shall pretend that we live in a perfect
        // world where only competent people are woking on interesting projects. (sigh!)
        //
        // The above rule is directly converted into an object strucutre. We first create the variables X, Y and Z:
        Variable X = KAON2Manager.factory().variable("X");
        Variable Y = KAON2Manager.factory().variable("Y");
        Variable Z = KAON2Manager.factory().variable("Z");

        // We now create the literals (notice that all of them are positive):
        Literal personKnowsAboutTopic_X_Z = KAON2Manager.factory().literal(true, personKnowsAboutTopic, new Term[]{X, Z});
        Literal worksOn_X_Y = KAON2Manager.factory().literal(true, worksOn, new Term[]{X, Y});
        Literal projectHasTopic_Y_Z = KAON2Manager.factory().literal(true, projectHasTopic, new Term[]{Y, Z});

        // We now create the rule.
        Rule rule = KAON2Manager.factory().rule(
                personKnowsAboutTopic_X_Z,                          // this is the rule head, i.e. the consequent of the rule
                new Literal[]{worksOn_X_Y, projectHasTopic_Y_Z}   // this is the rule body, i.e. the condition of the rule
        );

        // Rule is a kind of axiom, so it can be added to the ontology in the same way as
        // any axiom is added, i.e. by an OntologyChangeEvent.
        changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));

        // We shall create another rule which says:
        //
        // If
        //    a project X is about Semantic Web
        // then
        //    the project X is about description logic.
        //
        // In Prolog, this rule would be written like this:
        //     projectHasTopic(X,example04:descriptionLogics) :- projectHasTopic(X,example04:semanticWeb)
        //
        // The above rule is the dream of every logician ("you shall be assimilated, resistence is futile",
        // if you know what I mean :-)
        //
        // We do not to create new variable X; we simply reuse the already created object.
        // We now create the literals inline:
        rule = KAON2Manager.factory().rule(
                KAON2Manager.factory().literal(true, projectHasTopic, new Term[]{X, descriptionLogics}),
                new Literal[]{KAON2Manager.factory().literal(true, projectHasTopic, new Term[]{X, semanticWeb})}
        );

        // We add the rule to the chande list.
        changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));

        // Creating rules in the above way can be tedious. Therefore, the LISP-like syntax
        // can be used to encode rules as well. To use it, we first initialize an instance
        // of the Namespaces class in the same way as in Example 2.
        Namespaces namespaces = new Namespaces();
        namespaces.registerPrefix("example04", "http://kaon2.semanticweb.org/example04#");

        // We shall create a yet another rule that is similar to the above rule which says the following:
        //
        // If
        //     a person X knows about OWL
        // then
        //    the person X knowls about description logics as well.
        //
        // In Prolog, this rule would be written like this:
        //     personKnowsAboutTopic(X,example04:descriptionLogics) :- personKnowsAboutTopic(X,example04:owl)
        rule = (Rule) KAON2Manager.factory().axiom(
                "[rule [" +                                                                      // States that the rule follows. The opening brackes introduces a set of head literals.
                        "    [[oprop example04:personKnowsAboutTopic] X [example04:descriptionLogics]]" +  // This is the specification of one head literal. The individuals must be enclosed in [].
                        "  ] [" +                                                                        // The bracket ] terminated the set of head literals. The bracket [ opens the set of body literals.
                        "    [[oprop example04:personKnowsAboutTopic] X [example04:owl]]" +                // This is the one body literal.
                        "  ]" +                                                                          // This closes the set of body literals.
                        "]"                                                                             // This closes the rule.
                , namespaces);

        // We add the rule to the chande list.
        changes.add(new OntologyChangeEvent(rule, OntologyChangeEvent.ChangeType.ADD));

        // We now add some facts. We shall use these facts to query answering later.
        Individual boris = KAON2Manager.factory().individual("http://kaon2.semanticweb.org/example04#boris");
        // Boris is a student.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(student, boris), OntologyChangeEvent.ChangeType.ADD));
        // DIP is an EU project.
        Individual dip = KAON2Manager.factory().individual("http://kaon2.semanticweb.org/example04#dip");
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().classMember(euProject, dip), OntologyChangeEvent.ChangeType.ADD));
        // DIP is about semantic web.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(projectHasTopic, dip, semanticWeb), OntologyChangeEvent.ChangeType.ADD));
        // Boris works on DIP.
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(worksOn, boris, dip), OntologyChangeEvent.ChangeType.ADD));

        // We now apply the changes to the ontology. Only after this action is done,
        // the axioms created above are added to the ontology.
        ontology.applyChanges(changes);

        // We now save the ontology by calling the serializer. Observe that the
        // location where the ontology is stored does not need to be the same
        // as the physical URI. This is deliberate, as this allows you to implement
        // 'Save As' operation. The second parameter defines the character encoding used
        // in the XML file. we save the ontology into 'c:\temp\example04.xml'.

        ontology.saveOntology(OntologyFileFormat.OWL_XML, new File("c:\\example04.xml"), "ISO-8859-1");


        // We are now ready to ask some questions. Our goal is to obtain the expertise of all persons. In order words,
        // we want to ask the following conjunctive query:
        //
        //   Person(X),personKnowsAboutTopic(X,Y)
        //
        // The first thing we need to do is create an instance of the reasoner. Reasoners consume resources, so you should keep
        // them around only for as long as you need them. However, if you plan to ask several queries, it is *MUCH* more efficient
        // to use one and the same reasoner for all queries. One reasoner can be used to run several concurrent queries.
        Reasoner reasoner = ontology.createReasoner();

        // We now create the query object. A query consists of the following things:
        //
        // - a list of literals definint the query (they are created as usual)
        // - a list of distinguished variables, i.e. the variables that will be returned
        Query whatDoPeopleKnowAbout = reasoner.createQuery(new Literal[]{
                KAON2Manager.factory().literal(true, person, new Term[]{X}),
                KAON2Manager.factory().literal(true, personKnowsAboutTopic, new Term[]{X, Y}),
        }, new Variable[]{X, Y});

        // Creating the query has the effect of compiling it. A single query can be executed then several times.
        // A query is executed by invoking the Query.open() method.
        System.out.println();
        System.out.println("The list of people and things that they know about:");
        System.out.println("---------------------------------------------------");

        whatDoPeopleKnowAbout.open();
        // We now iterate over the query results.
        while (!whatDoPeopleKnowAbout.afterLast()) {
            // A query result is a set of tuples. The values in each tuple correspond to the distinguished variables.
            // In the above example, the distinguished variables are [X,Y]; this means that the first object in
            // the tuple is the value for the X variable, and the second one is the value for the Y variable.
            Term[] tupleBuffer = whatDoPeopleKnowAbout.tupleBuffer();
            System.out.println("Person '" + tupleBuffer[0].toString() + "' knows about '" + tupleBuffer[1].toString() + "'.");
            whatDoPeopleKnowAbout.next();
        }

        System.out.println("---------------------------------------------------");

        // If a query has been successfully opened, it should also be closed; otherwise, a resource leak occurs.
        whatDoPeopleKnowAbout.close();

        // After a query is closed, it may be reopened. The results of the query will reflect the changes to the ontology.
        // To see this, we shall add an additional fact that DIP is about OWL.
        changes.clear();
        changes.add(new OntologyChangeEvent(KAON2Manager.factory().objectPropertyMember(projectHasTopic, dip, owl), OntologyChangeEvent.ChangeType.ADD));
        ontology.applyChanges(changes);

        System.out.println();
        System.out.println("New facts have been successfully added to the ontology.");
        System.out.println();

        // We now execute the query again. The results reflect the change in the ontology.
        System.out.println("The same query executed again:");
        System.out.println("---------------------------------------------------");

        whatDoPeopleKnowAbout.open();
        while (!whatDoPeopleKnowAbout.afterLast()) {
            Term[] tupleBuffer = whatDoPeopleKnowAbout.tupleBuffer();
            System.out.println("Person '" + tupleBuffer[0].toString() + "' knows about '" + tupleBuffer[1].toString() + "'.");
            whatDoPeopleKnowAbout.next();
        }
        System.out.println("---------------------------------------------------");
        whatDoPeopleKnowAbout.close();

        // When a query is not needed any more, it should be disposed of. Forgetting to do so will result in
        // a fairly serious memory leak.
        whatDoPeopleKnowAbout.dispose();

        // Reasoners have to be disposed off after they are not used any more. Forgetting to do so will result in
        // a fairly serious memory leak!
        reasoner.dispose();

        // Don't forget to close the connection!
        ontologyManager.close();
    }
}
