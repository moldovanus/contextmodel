package reasoning.impl;

import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import reasoning.Evaluator;
import utils.exceptions.IndividualNotFoundException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 25, 2010
 * Time: 11:46:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class PelletEvaluator implements Evaluator {
    private OWLModel owlModel;

    public PelletEvaluator(OWLModel owlModel) {
        this.owlModel = owlModel;
    }

    public boolean evaluate_2(ContextPolicy policy) {
        boolean respected = true;
        if (policy instanceof BusinessPolicy) {
            BusinessPolicy businessPolicy = (BusinessPolicy) policy;
            if (businessPolicy.getPolicySubject().size() == 0) {
                return true;
            }
            ApplicationActivity activity = (ApplicationActivity) businessPolicy.getPolicySubject().get(0);
            respected = activity.getNumberOfCoresRequiredValue() == activity.getNumberOfCoresAllocatedValue()
                    && (activity.getCpuRequiredMaxValue() >= activity.getCpuAllocatedValue()
                    && activity.getCpuRequiredMinValue() <= activity.getCpuAllocatedValue())
                    && (activity.getMemRequiredMaxValue() >= activity.getMemAllocatedValue()
                    && activity.getMemRequiredMinValue() <= activity.getMemAllocatedValue());
        } else if (policy instanceof ITComputingContextPolicy) {
            ITComputingContextPolicy contextPolicy = (ITComputingContextPolicy) policy;
            ServiceCenterServer server = (ServiceCenterServer) contextPolicy.getPolicySubject().get(0);
            CPU cpu = server.getCpuResources().iterator().next();
            MEM mem = server.getMemResources().iterator().next();
            List<Core> cores = cpu.getAssociatedCores();
            for (Core core : cores) {
                if ((core.getCurrentWorkLoad() > core.getMaximumWorkLoad() - (core.getMaximumWorkLoad() - core.getOptimalWorkLoad()) / 2.0)
                        || (core.getCurrentWorkLoad() < core.getOptimalWorkLoad() / 2.0)) {
                    return false;
                }

            }
            if ((mem.getCurrentWorkLoad() > mem.getMaximumWorkLoad() - (mem.getMaximumWorkLoad() - mem.getOptimalWorkLoad()) / 2.0)
                    || (mem.getCurrentWorkLoad() < mem.getOptimalWorkLoad() / 2.0)) {
                return false;
            }

        }
        return respected;
    }

    public boolean evaluatePolicy(ContextPolicy policy, String propertyName) throws IndividualNotFoundException {
        return evaluate_2(policy);
//     RuntimeEvaluator evaluator = new RuntimeEvaluator();
//        evaluator.markBefore();
//        OntModel ontModel = ModelFactory.createOntologyModel(org.mindswap.pellet.jena.PelletReasonerFactory.THE_SPEC);
//        ontModel.add(owlModel.getJenaModel());
//        Individual individual = ontModel.getIndividual(policy.getName());
//        if (individual == null) {
//            throw new IndividualNotFoundException(policy.getName());
//        }
//        Property property = ontModel.getProperty(propertyName);
////        ValidityReport validityReport = ontModel.validate();
////        Iterator<ValidityReport.Report> reportIterator = validityReport.getReports();
////        while (reportIterator.hasNext()) {
////            ValidityReport.Report report = reportIterator.next();
////            System.out.println(report.toString());
////        }
//
////        try {
////            ontModel.write(new BufferedWriter(new FileWriter(new File("./ontology/context_KAON_1.rdf-xml.owl"))));
////        } catch (IOException e) {
////            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////        }
////
////        System.exit(1);
//
//        RDFNode propertyValue = individual.getPropertyValue(property);
//        boolean value = (propertyValue != null) && propertyValue.asLiteral().getBoolean();
////        if (propertyValue == null) {
////            System.out.println("Property null");
////            DefaultApplicationActivity activity = (DefaultApplicationActivity) policy.getPolicySubject().get(0);
////            System.out.println(activity.getMemRequiredMinValue() + " " + activity.getMemAllocatedValue() + " " + activity.getMemRequiredMaxValue());
////        }
//        evaluator.markAfter();
//        evaluator.printResult();
//        return value;
    }
}
