package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.resources.MEM;

import java.util.Collection;
import java.util.Iterator;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MEM
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultMEM extends DefaultSimpleResource
        implements MEM {

    public DefaultMEM(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultMEM() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate

    public Collection getCacheRate() {
        return getPropertyValues(getCacheRateProperty());
    }


    public RDFProperty getCacheRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCacheRate() {
        return getPropertyValueCount(getCacheRateProperty()) > 0;
    }


    public Iterator listCacheRate() {
        return listPropertyValues(getCacheRateProperty());
    }


    public void addCacheRate(float newCacheRate) {
        addPropertyValue(getCacheRateProperty(), newCacheRate);
    }


    public void removeCacheRate(float oldCacheRate) {
        removePropertyValue(getCacheRateProperty(), oldCacheRate);
    }


    public void setCacheRate(Collection newCacheRate) {
        setPropertyValues(getCacheRateProperty(), newCacheRate);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#clockRate

    public Collection getClockRate() {
        return getPropertyValues(getClockRateProperty());
    }


    public RDFProperty getClockRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#clockRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasClockRate() {
        return getPropertyValueCount(getClockRateProperty()) > 0;
    }


    public Iterator listClockRate() {
        return listPropertyValues(getClockRateProperty());
    }


    public void addClockRate(float newClockRate) {
        addPropertyValue(getClockRateProperty(), newClockRate);
    }


    public void removeClockRate(float oldClockRate) {
        removePropertyValue(getClockRateProperty(), oldClockRate);
    }


    public void setClockRate(Collection newClockRate) {
        setPropertyValues(getClockRateProperty(), newClockRate);
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
}
