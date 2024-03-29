package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.impl.util.ModelAccess;
import model.interfaces.actions.ApplicationRedesign;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ApplicationRedesign
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultApplicationRedesignAction extends DefaultApplicationAdaptationAction
        implements ApplicationRedesign {
       private int cost = 0;
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public DefaultApplicationRedesignAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultApplicationRedesignAction() {
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
