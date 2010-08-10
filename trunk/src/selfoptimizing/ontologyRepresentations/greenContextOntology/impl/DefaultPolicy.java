package selfoptimizing.ontologyRepresentations.greenContextOntology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.RDFSLiteral;
import selfoptimizing.ontologyRepresentations.greenContextOntology.Policy;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Datacenter.owl#Policy
 *
 * @version generated on Sun Mar 07 13:11:11 EET 2010
 */
public abstract class DefaultPolicy extends DefaultContextElement
        implements Policy {

    public DefaultPolicy(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultPolicy() {
    }


    // Property http://www.owl-ontologies.com/Datacenter.owl#priority

    public int getPriority() {
        return getPropertyValueLiteral(getPriorityProperty()).getInt();
    }


    public RDFProperty getPriorityProperty() {
        final String uri = "http://www.owl-ontologies.com/Datacenter.owl#priority";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPriority() {
        return getPropertyValueCount(getPriorityProperty()) > 0;
    }


    public void setPriority(int newPriority) {
        setPropertyValue(getPriorityProperty(), new java.lang.Integer(newPriority));
    }


    // Property http://www.owl-ontologies.com/Datacenter.owl#referenced

    public Object getReferenced() {
        return (Object) getPropertyValue(getReferencedProperty());
    }


    public RDFProperty getReferencedProperty() {
        final String uri = "http://www.owl-ontologies.com/Datacenter.owl#policyTarget";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasReferenced() {
        return getPropertyValueCount(getReferencedProperty()) > 0;
    }


    public void setReferenced(Object newReferenced) {
        setPropertyValue(getReferencedProperty(), newReferenced);
    }


    // Property http://www.owl-ontologies.com/Datacenter.owl#respected
    //TODO : check new method
//    public boolean getRespected(OntModel ontModel) {
//
//        Individual ind = ontModel.getIndividual(getName());
//        Property isOK = ontModel.getProperty(getRespectedProperty().getName());
//        RDFNode ok = null;
//        try {
//            ok = ind.getPropertyValue(isOK);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.err.println("Do not initialize the respected property of the policy. Because when SWRL rule triggers it will add another value not override the last one so the exactly one restriction is broken.");
//            System.err.println(e.getMessage());
//            System.err.println(e.getCause());
//            e.printStackTrace();
//        }
//        if (ok == null) {
//            return false;
//        } else {
//            return ok.toString().contains("true");
//        }
//
//    }

    // Property http://www.owl-ontologies.com/Datacenter.owl#respected

    public boolean getRespected() {
        System.err.println("Warning: call to getRespected() in DefaultPolicy. Use getRespected(OntModel model) if valid result expected.\n First does not return correct value( SWRL rules trigger only on OntModel  ");
        RDFSLiteral respected = getPropertyValueLiteral(getRespectedProperty());
        if (respected == null) {
            return false;
        } else return respected.getBoolean();
    }


    public RDFProperty getRespectedProperty() {
        final String uri = "http://www.owl-ontologies.com/Datacenter.owl#respected";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasRespected() {
        return getPropertyValueCount(getRespectedProperty()) > 0;
    }


    public void setRespected(boolean newRespected) {
        setPropertyValue(getRespectedProperty(), new java.lang.Boolean(newRespected));
    }
}
