package main;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import model.impl.ontologyImpl.OntologyModelFactory;
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

    public void initializeOntology() {
        File ontologyDataCenterFile = new File("ontology/context.owl");
        protegeFactory = null;
        owlModel = null;
        try {
            owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyDataCenterFile.toURI().toString());
            protegeFactory = new OntologyModelFactory();
        } catch (OntologyLoadException e) {
            e.printStackTrace();
        }
        /***************************Pellet Reasoner*****************************/
        ontModel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        ontModel.add(owlModel.getJenaModel());
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        Model emptyModel = ModelFactory.createDefaultModel();
        InfModel model = ModelFactory.createInfModel(reasoner, emptyModel);
        model.add(owlModel.getJenaModel());
    }

    public void addRules() {
        Collection<ITComputingContextPolicy> energyPolicies = protegeFactory.getAllITComputingContextPolicyInstances();
        SWRLFactory factory = new SWRLFactory(owlModel);
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
    }

    public void checkRespected() {
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
        end = System.currentTimeMillis();
        total = end - start;
        System.out.println(total);
    }

    public boolean getRespected(OntModel ontModel, ContextElement el) {

        Individual ind = ontModel.getIndividual(el.getName());
        final String name = owlModel.getResourceNameForURI("http://www.owl-ontologies.com/Datacenter.owl#isRespected");
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

    public static void main(String args[]) {
        PelletJena pelletJena = new PelletJena();
        pelletJena.initializeOntology();
        pelletJena.addRules();
        pelletJena.checkRespected();
    }
}
