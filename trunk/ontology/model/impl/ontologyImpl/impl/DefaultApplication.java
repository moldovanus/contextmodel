package model.impl.ontologyImpl.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.impl.ontologyImpl.Application;
import model.impl.ontologyImpl.DefaultBusinessContextResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#Application
 *
 * @version generated on Fri Jul 09 18:31:36 GMT 2010
 */
public class DefaultApplication extends DefaultBusinessContextResource
        implements Application {

    public DefaultApplication(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultApplication() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#BP_ActivityList

    public Collection getBP_ActivityList() {
        return getPropertyValuesAs(getBP_ActivityListProperty(), ApplicationActivity.class);
    }


    public RDFProperty getBP_ActivityListProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#BP_ActivityList";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasBP_ActivityList() {
        return getPropertyValueCount(getBP_ActivityListProperty()) > 0;
    }


    public Iterator listBP_ActivityList() {
        return listPropertyValuesAs(getBP_ActivityListProperty(), ApplicationActivity.class);
    }


    public void addBP_ActivityList(ApplicationActivity newBP_ActivityList) {
        addPropertyValue(getBP_ActivityListProperty(), newBP_ActivityList);
    }


    public void removeBP_ActivityList(ApplicationActivity oldBP_ActivityList) {
        removePropertyValue(getBP_ActivityListProperty(), oldBP_ActivityList);
    }


    public void setBP_ActivityList(Collection newBP_ActivityList) {
        setPropertyValues(getBP_ActivityListProperty(), newBP_ActivityList);
    }
}
