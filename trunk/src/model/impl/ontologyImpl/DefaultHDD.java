package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.resources.HDD;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#HDD
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultHDD extends DefaultSimpleResource
        implements HDD {

    public DefaultHDD(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultHDD() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    public Collection getPartOf() {
        return getPropertyValuesAs(getPartOfProperty(), ContextElement.class);
    }


    public RDFProperty getPartOfProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPartOf() {
        return getPropertyValueCount(getPartOfProperty()) > 0;
    }


    public Iterator listPartOf() {
        return listPropertyValuesAs(getPartOfProperty(), ContextElement.class);
    }


    public void addPartOf(ContextElement newPartOf) {
        addPropertyValue(getPartOfProperty(), newPartOf);
    }


    public void removePartOf(ContextElement oldPartOf) {
        removePropertyValue(getPartOfProperty(), oldPartOf);
    }


    public void setPartOf(Collection newPartOf) {
        setPropertyValues(getPartOfProperty(), newPartOf);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#rotationSpeed

    public Double getRotationSpeed() {
        return getPropertyValueLiteral(getRotationSpeedProperty()).getDouble();
    }


    public RDFProperty getRotationSpeedProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#rotationSpeed";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasRotationSpeed() {
        return getPropertyValueCount(getRotationSpeedProperty()) > 0;
    }


    public void setRotationSpeed(Double newRotationSpeed) {
        setPropertyValue(getRotationSpeedProperty(), new java.lang.Float(newRotationSpeed));
    }

    @Override
    public boolean hasResourcesFor(ApplicationActivity activity) {
        return getMaximumWorkLoad() <= getCurrentWorkLoad() + activity.getHddRequiredMaxValue();
    }

    @Override
    public void addRunningActivity(ApplicationActivity activity) {
        setCurrentWorkLoad(getCurrentWorkLoad() + activity.getHddRequiredMaxValue());
        super.addRunningActivity(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void removeRunningActivity(ApplicationActivity activity) {
        setCurrentWorkLoad(getCurrentWorkLoad() - activity.getHddRequiredMaxValue());
        super.removeRunningActivity(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
