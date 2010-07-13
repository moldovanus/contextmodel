package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.resources.ContextResource;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ContextResource
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultContextResource extends DefaultContextElement
        implements ContextResource {

    public DefaultContextResource(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultContextResource() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceID

    public String getResourceID() {
        return (String) getPropertyValue(getResourceIDProperty());
    }


    public RDFProperty getResourceIDProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceID";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceID() {
        return getPropertyValueCount(getResourceIDProperty()) > 0;
    }


    public void setResourceID(String newResourceID) {
        setPropertyValue(getResourceIDProperty(), newResourceID);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceWorkloadProperty

    public Collection getResourceWorkloadProperty() {
        return getPropertyValues(getResourceWorkloadPropertyProperty());
    }


    public RDFProperty getResourceWorkloadPropertyProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceWorkloadProperty";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasResourceWorkloadProperty() {
        return getPropertyValueCount(getResourceWorkloadPropertyProperty()) > 0;
    }


    public Iterator listResourceWorkloadProperty() {
        return listPropertyValues(getResourceWorkloadPropertyProperty());
    }


    public void addResourceWorkloadProperty(String newResourceWorkloadProperty) {
        addPropertyValue(getResourceWorkloadPropertyProperty(), newResourceWorkloadProperty);
    }


    public void removeResourceWorkloadProperty(String oldResourceWorkloadProperty) {
        removePropertyValue(getResourceWorkloadPropertyProperty(), oldResourceWorkloadProperty);
    }


    public void setResourceWorkloadProperty(Collection newResourceWorkloadProperty) {
        setPropertyValues(getResourceWorkloadPropertyProperty(), newResourceWorkloadProperty);
    }
}