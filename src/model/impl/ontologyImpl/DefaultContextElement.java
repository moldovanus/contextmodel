package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.interfaces.ContextElement;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ContextElement
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultContextElement extends AbstractCodeGeneratorIndividual
        implements ContextElement {


    public DefaultContextElement(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
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
        return (o instanceof ContextElement) && (((ContextElement) o).getName().equals(this.getName()));
    }

}
