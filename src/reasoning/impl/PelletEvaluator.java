package reasoning.impl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.reasoner.ValidityReport;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import globalLoop.utils.GlobalVars;
import model.impl.ontologyImpl.resources.applications.DefaultApplicationActivity;
import model.interfaces.policies.ContextPolicy;
import reasoning.Evaluator;
import utils.exceptions.IndividualNotFoundException;
import utils.misc.RuntimeEvaluator;

import java.io.*;
import java.util.Iterator;

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

    public boolean evaluatePolicy(ContextPolicy policy, String propertyName) throws IndividualNotFoundException {
        RuntimeEvaluator evaluator = new RuntimeEvaluator();
        evaluator.markBefore();
        OntModel ontModel = ModelFactory.createOntologyModel(org.mindswap.pellet.jena.PelletReasonerFactory.THE_SPEC);
        ontModel.add(owlModel.getJenaModel());
        Individual individual = ontModel.getIndividual(policy.getName());
        if (individual == null) {
            throw new IndividualNotFoundException(policy.getName());
        }
        Property property = ontModel.getProperty(propertyName);
//        ValidityReport validityReport = ontModel.validate();
//        Iterator<ValidityReport.Report> reportIterator = validityReport.getReports();
//        while (reportIterator.hasNext()) {
//            ValidityReport.Report report = reportIterator.next();
//            System.out.println(report.toString());
//        }

//        try {
//            ontModel.write(new BufferedWriter(new FileWriter(new File("./ontology/context_KAON_1.rdf-xml.owl"))));
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        System.exit(1);

        RDFNode propertyValue = individual.getPropertyValue(property);
        boolean value = (propertyValue != null) && propertyValue.asLiteral().getBoolean();
        if (propertyValue == null) {
            System.out.println("Property null");
//            DefaultApplicationActivity activity = (DefaultApplicationActivity) policy.getPolicySubject().get(0);
//            System.out.println(activity.getMemRequiredMinValue() + " " + activity.getMemAllocatedValue() + " " + activity.getMemRequiredMaxValue());
        }
        evaluator.markAfter();
        evaluator.printResult();
        return value;
    }
}
