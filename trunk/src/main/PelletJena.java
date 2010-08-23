package main;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSNamedClass;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.util.ModelAccess;
import model.interfaces.ContextElement;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.policies.ITComputingContextPolicy;
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
 * Date: Jul 18, 2010
 * Time: 10:48:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class PelletJena {
    private JenaOWLModel owlModel;
    private OntModel ontModel;
    private OntologyModelFactory protegeFactory;
    private Reasoner reasoner;

    public void initializeOntology() {
        File ontologyDataCenterFile = new File("./ontology/myNewOntology.rdf-xml.owl");
        protegeFactory = null;
        owlModel = null;
        try {
            owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyDataCenterFile.toURI().toString());
            protegeFactory = new OntologyModelFactory();
        } catch (OntologyLoadException e) {
            e.printStackTrace();
        }
        ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        ontModel.add(owlModel.getJenaModel());
        Collection<Instance> instances = owlModel.getDirectInstances(owlModel.getCls("http://www.owl-ontologies.com/Ontology1280294013.owl#ITFacilityPassiveResource"));
        String uri = "http://www.owl-ontologies.com/Ontology1280294013.owl#ITFacilityPassiveResource";
        String name = owlModel.getResourceNameForURI(uri);
        RDFSNamedClass cls = owlModel.getRDFSNamedClass(name);
        RDFResource owlIndividual;
        for (Iterator it = cls.getInstances(true).iterator(); it.hasNext();) {
            //  for (Iterator it =instances.iterator();it.hasNext();){
            System.out.println(it.next().toString() + "AAA");

        }

        reasoner = PelletReasonerFactory.theInstance().create();
        Model emptyModel = ModelFactory.createDefaultModel();
        InfModel model = ModelFactory.createInfModel(reasoner, emptyModel);
        model.add(owlModel.getJenaModel());
    }


    public static void generateEnergyRules(OWLModel model, ModelAccess modelAccess) {
        Collection<ITComputingContextPolicy> energyPolicies = modelAccess.getAllITComputingContextPolicyInstances();

        SWRLFactory factory = new SWRLFactory(model);
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
                    swrlRule += "SimpleResource(?a" + a + ") ^ resourceID(?a" + a + ",\"" + simpleResource.getResourceID() + "\") ^ currentWorkload(?a" + a + ",?cWorkload" + a + ") ^ maximumWorkload(?a" + a + ",?maxWorkload" + a + ") ^ swrlb:lessThanOrEqual(?cWorkload" + a + ",?maximumWorkload" + a + ")^ swrlb:multiply(?optimalWorkload0,0.33,?downDif" + a + ") ^  \n" +
                            "sqwrl:difference(?maxWorkload" + a + ", ?optimalWorkload" + a + ",?sumOf" + a + ")  ^  " +
                            "sqwrl:difference(?optimalWorkload" + a + ",?downDif" + a + ",?minThreshold" + a + ")  ^  " +
                            " swrlb:divide(?sumOf" + a + ", 2,?upDif" + a + ") ^ \n" +
                            "sqwrl:sum(?optimalWorkload" + a + ",?upDif" + a + ",?maxThreshold" + a + " )   ^ swrlb:lessThanOrEqual(?minThreshold" + a + ",?ckload" + a + ")^ swrlb:lessThanOrEqual(?cWorkload" + a + ",?maxThreshold" + a + ") ^ ";
                    a++;
                }
                System.out.println(swrlRule + "ComplexResource(?x) ^ ResourceID(?x," + compResource.getResourceID() + ") ^  currentWorkload(?x, ?cWorkload) ^  maximumWorkload(?x, ?maxWorkload) ^  swrlb:lessThanOrEqual(?cWorkload, ?maximumWorkload) -> isRespected(" + currentPolicy.getName() + ", true)");
                SWRLImp imp = factory.createImp(swrlRule + "ComplexResource(?x) ^  currentWorkload(?x, ?cWorkload) ^  maximumWorkload(?x, ?maxWorkload) ^  swrlb:lessThanOrEqual(?cWorkload, ?maxWorkload) -> isRespected(" + currentPolicy.getName() + ", true)");

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    public static void generateBussinessRules(OWLModel model, ModelAccess modelAccess) {
        Collection<ITComputingContextPolicy> energyPolicies = modelAccess.getAllITComputingContextPolicyInstances();

        SWRLFactory factory = new SWRLFactory(model);
        String swrlRule = "";
        Collection<BusinessPolicy> businessPolicies = modelAccess.getAllBusinessPolicyInstances();
        for (Iterator it = businessPolicies.iterator(); it.hasNext();) {
            BusinessPolicy businessPolicy = (BusinessPolicy) it.next();
            ApplicationActivity applicationActivity = (ApplicationActivity) businessPolicy.getPolicySubject().get(0);
            try {

                swrlRule = "ContextModel:ApplicationActivity(?x) ^ ContextModel:resourceID(?x,\"" + applicationActivity.getResourceID() + "\") ^ ContextModel:memAllocatedValue(?x,?memAllocated) ^ ContextModel:memRequiredValue(?x, ?memRequiredValue) ^ swrlb:lessThanOrEqual(?memAllocatedValue,?memRequiredValue) \n " +
                        " ^ ContextModel:hddAllocatedValue(?x,?hddAllocated) ^ ContextModel:hddRequiredValue(?x, ?hddRequiredValue) ^ swrlb:lessThanOrEqual(?hddAllocatedValue,?hddRequiredValue)  \n" +
                        " ^ ContextModel:cpuAllocatedValue(?x,?cpuAllocated) ^ ContextModel:cpuRequiredValue(?x, ?cpuRequiredValue) ^ swrlb:lessThanOrEqual(?cpuAllocatedValue,?cpuRequiredValue) ";
                System.out.println(swrlRule);
                SWRLImp imp = factory.createImp(swrlRule);

            } catch (SWRLParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void checkRespected() {
        long used1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start, end, total;
        start = System.currentTimeMillis();
        Collection<ITComputingContextPolicy> energyPolicies = protegeFactory.getAllITComputingContextPolicyInstances();
        for (Iterator it = energyPolicies.iterator(); it.hasNext();) {
            System.out.println(getRespected(ontModel, (ITComputingContextPolicy) it.next()));
        }
        Collection<BusinessPolicy> businessPolicies = protegeFactory.getAllBusinessPolicyInstances();
        for (Iterator it = businessPolicies.iterator(); it.hasNext();) {
            System.out.println(getRespected(ontModel, (BusinessPolicy) it.next()));
        }
        long used2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        end = System.currentTimeMillis();
        System.out.println("Used memory: " + (used2 - used1) / (1024.0 * 1024.0));
        total = end - start;
        System.out.println(total);
    }

    public void checkNewRule() {
        long start, end, total;
        start = System.currentTimeMillis();
        long used1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        SWRLFactory factory = new SWRLFactory(owlModel);
        String swrlRule = "";
        try {
            swrlRule = "ServiceCenterITFacilityResource(?x) ^ \n" +
                    "hasAssociatedActions(?x, false)   -> \n" +
                    "ITFacilityPassiveResource(?x)";

            SWRLImp imp = factory.createImp(swrlRule);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        long used2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        end = System.currentTimeMillis();
        total = end - start;
        System.out.println("Reasoning time " + total);
        System.out.println("Memory:" + (used2 - used1) / (1024.0 * 1024.0));
        Collection<Instance> instances = owlModel.getDirectInstances(owlModel.getCls("http://www.owl-ontologies.com/Ontology1280294013.owl#ITFacilityPassiveResource"));

        String uri = "http://www.owl-ontologies.com/Ontology1280294013.owl#ITFacilityPassiveResource";
        String name = owlModel.getResourceNameForURI(uri);
        RDFSNamedClass cls = owlModel.getRDFSNamedClass(name);
        RDFResource owlIndividual;
        for (Iterator it = cls.getInstances(true).iterator(); it.hasNext();) {
            //  for (Iterator it =instances.iterator();it.hasNext();){
            System.out.println(it.next().toString() + "AAA");

        }

        end = System.currentTimeMillis();
        total = end - start;
        System.out.println("Elapsed time: " + total);

    }

    public boolean getRespected(OntModel ontModel, ContextElement el) {

        Individual ind = ontModel.getIndividual(el.getName());

        final String name = owlModel.getResourceNameForURI("http://www.owl-ontologies.com/ContextModel.owl#isRespected");
        Property isOK = ontModel.getProperty(name);
        RDFNode ok = null;
        try {
            ok = ind.getPropertyValue(isOK);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Do not initialize the respected property of the policy. Because when SWRL rule triggers it will add another value not override the last one so the exactly one restriction is broken.");
            System.err.println(e.getMessage());
            System.err.println(e.getCause());
            e.printStackTrace();
        }
        if (ok == null) {
            return false;
        } else {
            return ok.toString().contains("true");
        }
    }

    /**
     * *****************************Bossam - face queriuri si evalueaza swrl-uri da nu le adauga *******
     */
//    public void checkBossamReasoning() {
//
//        IReasonerFactory reasonerFactory = ReasonerFactory.getInstance();
//        IReasoner r = reasonerFactory.createOwlDlReasoner();
//        FileReader reader = null;
//        try {
//            reader = new FileReader("./ontology/myNewOntology.rdf-xml.owl");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        r.load(IReasoner.SWRLRDF, reader, "http://www.owl-ontologies.com/Ontology1280294013.owl#");
//        try {
//            String result = r.run();
//            System.out.println("Result " + result);
//        } catch (InconsistencyException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        Answer answer = r.ask1("query q is ITFacilityPassiveResource(?x); ");
//        List l = answer.getBindings();
//        for (Iterator it = l.iterator(); it.hasNext();) {
//            System.out.println(it.next());
//        }
//    }
    public void checkJEnaRules() {
        long used1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long start, end, total;
        start = System.currentTimeMillis();
        String rules = "[rule: (?C, rdf:type, ServiceCenterITFacilityResource),(?C, hasAssociatedActions,false)-> (?C,rdf:type,ITFacilityPassiveResource)]";
        GenericRuleReasoner ruleReasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        ruleReasoner.setDerivationLogging(true);
        OntModelSpec spec = new OntModelSpec(PelletReasonerFactory.THE_SPEC);
        spec.setReasoner(ruleReasoner);
        ontModel = ModelFactory.createOntologyModel(spec);
        ontModel.add(owlModel.getJenaModel());
        long used2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        end = System.currentTimeMillis();
        total = end - start;
        System.out.println("Used memory:" + (used2 - used1) / (1024.0 * 1024.0));

        System.out.println("Elapsed time: " + total);

    }

    public static void main(String args[]) {
        PelletJena pelletJena = new PelletJena();
        pelletJena.initializeOntology();
        //pelletJena.addRules();
        //pelletJena.checkRespected();
        //  pelletJena.checkNewRule();
        // pelletJena.checkBossamReasoning();
        //pelletJena.checkJEnaRules();
    }
}
