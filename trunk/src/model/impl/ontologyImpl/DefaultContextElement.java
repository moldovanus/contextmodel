package model.impl.ontologyImpl;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import edu.stanford.smi.protegex.owl.swrl.exceptions.SWRLFactoryException;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import model.interfaces.ContextElement;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ContextElement
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultContextElement extends DefaultOWLIndividual
        implements ContextElement {
    private OWLModel owlModel;


    public DefaultContextElement(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
        this.owlModel = owlModel;
    }

    public Integer getId() {
        return null;
    }

    public void setId(Integer integer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public DefaultContextElement() {
    }

    public boolean equals(Object o) {
        return (o instanceof ContextElement) && (((ContextElement) o).getFrameID().equals(this.getFrameID()));
    }

    @Override
    public void setPropertyValue(RDFProperty property, Object value) {
        OntModel ontModel = getOWLModel().getOntModel();
        this.setPropertyValue(property, value, ontModel);
    }

    @Override
    public Object getPropertyValue(RDFProperty property) {
        OntModel ontModel = getOWLModel().getOntModel();
        return this.getPropertyValue(property, ontModel);   //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Sets the property value both on the OWL model and on the ONT model
     * to trigger SWRL rule evaluation
     *
     * @param rdfProperty the slot of the entity
     * @param value       the value of the property to be inserted in slot rdfProperty
     * @param ontModel    the ont model on which this property will also be set
     */
    public void setPropertyValue(RDFProperty rdfProperty, Object value, OntModel ontModel) {

        super.setPropertyValue(rdfProperty, value);
        Individual targetIndividual = ontModel.getIndividual(this.getName());
        if (targetIndividual == null) {
            System.out.println("It is null");
            System.exit(1);
        }
        Property targetProperty = ontModel.getProperty(rdfProperty.getName());
        if (targetIndividual.hasProperty(targetProperty)) {
            targetIndividual.removeAll(targetProperty);
        }
        targetIndividual.setPropertyValue(targetProperty, ontModel.createLiteralStatement(
                targetIndividual, targetProperty, value).getLiteral().as(RDFNode.class));
    }

    public Object getPropertyValue(RDFProperty rdfProperty, OntModel ontModel) {
        
        Individual targetIndividual = ontModel.getIndividual(this.getName());
//        ExtendedIterator<Individual> iterator = ontModel.listIndividuals();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next().getURI());
//        }

        Property targetProperty = ontModel.getProperty(rdfProperty.getName());
        if (targetIndividual.hasProperty(targetProperty)) {
            targetIndividual.removeAll(targetProperty);
        }

        System.out.print("ONT val: " + targetIndividual.getPropertyValue(targetProperty));
        System.out.println("OWL val: " + super.getPropertyValue(rdfProperty));
        return targetIndividual.getPropertyValue(targetProperty).asLiteral().getInt();

    }

    public final void deleteInstance(OntModel ontModel, SWRLFactory swrlFactory)
            throws SWRLFactoryException {
        super.delete();

        //to be commented to avoid SWRL rule evaluation
        //remove instance from underlying Ont model
//        Individual i = ontModel.getIndividual(getName());
//        i.remove();
//
//        //remove swrl rule associated to task
//        SWRLImp rule = swrlFactory
//                .getImp("http://www.owl-ontologies.com/Datacenter.owl#QoS_Policy_"
//                + this.getName().split("_")[1] + "_swrl_rule");
//        rule.delete();
    }
}
