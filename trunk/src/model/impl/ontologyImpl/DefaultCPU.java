package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#CPU
 *
 * @version generated on Tue Jul 06 17:49:11 GMT 2010
 */
public class DefaultCPU extends DefaultSimpleResource
        implements CPU {

    public DefaultCPU(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultCPU() {
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedCores

    public List<Core> getAssociatedCores() {
        return new ArrayList<Core>(getPropertyValuesAs(getAssociatedCoresProperty(), Core.class));
    }


    public RDFProperty getAssociatedCoresProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#associatedCores";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasAssociatedCores() {
        return getPropertyValueCount(getAssociatedCoresProperty()) > 0;
    }


    public Iterator listAssociatedCores() {
        return listPropertyValuesAs(getAssociatedCoresProperty(), Core.class);
    }


    public void addAssociatedCores(Core newAssociatedCores) {
        addPropertyValue(getAssociatedCoresProperty(), newAssociatedCores);
    }


    public void removeAssociatedCores(Core oldAssociatedCores) {
        removePropertyValue(getAssociatedCoresProperty(), oldAssociatedCores);
    }


    public void setAssociatedCores(List<Core> newAssociatedCores) {
        setPropertyValues(getAssociatedCoresProperty(), newAssociatedCores);
    }


    public float getCacheRate() {
        return getPropertyValueLiteral(getCacheRateProperty()).getFloat();
    }


    public RDFProperty getCacheRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCacheRate() {
        return getPropertyValueCount(getCacheRateProperty()) > 0;
    }


    public void setCacheRate(float newCacheRate) {
        setPropertyValue(getCacheRateProperty(), new java.lang.Float(newCacheRate));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#clockRate

    public float getClockRate() {
        return getPropertyValueLiteral(getClockRateProperty()).getFloat();
    }


    public RDFProperty getClockRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#clockRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasClockRate() {
        return getPropertyValueCount(getClockRateProperty()) > 0;
    }


    public void setClockRate(float newClockRate) {
        setPropertyValue(getClockRateProperty(), new java.lang.Float(newClockRate));
    }


}
