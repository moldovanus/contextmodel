package model.impl.ontologyImpl.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.impl.ontologyImpl.ComplexResource;
import model.impl.ontologyImpl.DefaultServiceCenterITComputingResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ComplexResource
 *
 * @version generated on Fri Jul 09 18:31:36 GMT 2010
 */
public class DefaultComplexResource extends DefaultServiceCenterITComputingResource
        implements ComplexResource {

    public DefaultComplexResource(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultComplexResource() {
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


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#simpleResources

    public Collection getSimpleResources() {
        return getPropertyValuesAs(getSimpleResourcesProperty(), SimpleResource.class);
    }


    public RDFProperty getSimpleResourcesProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#simpleResources";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasSimpleResources() {
        return getPropertyValueCount(getSimpleResourcesProperty()) > 0;
    }


    public Iterator listSimpleResources() {
        return listPropertyValuesAs(getSimpleResourcesProperty(), SimpleResource.class);
    }


    public void addSimpleResources(SimpleResource newSimpleResources) {
        addPropertyValue(getSimpleResourcesProperty(), newSimpleResources);
    }


    public void removeSimpleResources(SimpleResource oldSimpleResources) {
        removePropertyValue(getSimpleResourcesProperty(), oldSimpleResources);
    }


    public void setSimpleResources(Collection newSimpleResources) {
        setPropertyValues(getSimpleResourcesProperty(), newSimpleResources);
    }
}
