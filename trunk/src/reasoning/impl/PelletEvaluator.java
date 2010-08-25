package reasoning.impl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.interfaces.policies.ContextPolicy;
import reasoning.Evaluator;
import utils.exceptions.IndividualNotFoundException;
import utils.misc.RuntimeEvaluator;

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

        boolean value = individual.getPropertyValue(property).asLiteral().getBoolean();
        evaluator.markAfter();
        evaluator.printResult();
        return value;
    }
}
