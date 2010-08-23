package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.impl.util.ModelAccess;
import model.interfaces.actions.ActionEffect;
import model.interfaces.actions.FacilityDefaultAction;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ITFacilityPassiveResource;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#FacilityDefaultAction
 *
 * @version generated on Thu Aug 12 06:33:35 GMT 2010
 */
public class DefaultFacilityDefaultAction extends DefaultContextAction
        implements FacilityDefaultAction {

    private ActionEffect effect;

    public DefaultFacilityDefaultAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultFacilityDefaultAction() {
    }

    public DefaultFacilityDefaultAction(ActionEffect effect) {
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        if (effect == null) {
            throw new UnsupportedOperationException("First assign an effect");
        }
        for (ContextResource contextResource : getResources()) {
            effect.execute((ITFacilityPassiveResource) contextResource);
        }
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        if (effect == null) {
            throw new UnsupportedOperationException("First assign an effect");
        }
        for (ContextResource contextResource : getResources()) {
            effect.undo((ITFacilityPassiveResource) contextResource);
        }
    }


    public ActionEffect getEffect() {
        return effect;
    }

    public void setEffect(ActionEffect effect) {
        this.effect = effect;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultFacilityDefaultAction)) {
            return false;
        }
        DefaultFacilityDefaultAction action = (DefaultFacilityDefaultAction) o;
        return action.getName().equals(this.getName()) || action.getResources().equals(this.getResources()) && action.getEffect().equals(this.getEffect());
    }
}
