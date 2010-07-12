package model.impl.ontologyImpl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.interfaces.actions.ITComputingResourceAdaptationAction;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ITComputingResourceAdaptationAction
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultITComputingResourceAdaptationAction extends DefaultContextAction
        implements ITComputingResourceAdaptationAction {

    public DefaultITComputingResourceAdaptationAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultITComputingResourceAdaptationAction() {
    }


    /*
  // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom

  public List<ServiceCenterITComputingResource> getResourcesFrom() {
      return new ArrayList<ServiceCenterITComputingResource>(getPropertyValuesAs(getResourceFromProperty(), ServiceCenterITComputingResource.class));
  }


  public RDFProperty getResourceFromProperty() {
      final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom";
      final String name = getOWLModel().getResourceNameForURI(uri);
      return getOWLModel().getRDFProperty(name);
  }


  public boolean hasResourceFrom() {
      return getPropertyValueCount(getResourceFromProperty()) > 0;
  }


  public Iterator listResourceFrom() {
      return listPropertyValuesAs(getResourceFromProperty(), ServiceCenterITComputingResource.class);
  }


  public void addResourceFrom(ServiceCenterITComputingResource newResourceFrom) {
      addPropertyValue(getResourceFromProperty(), newResourceFrom);
  }


  public void removeResourceFrom(ServiceCenterITComputingResource oldResourceFrom) {
      removePropertyValue(getResourceFromProperty(), oldResourceFrom);
  }


  public void setResourcesFrom(List<ServiceCenterITComputingResource> newResourceFrom) {
      setPropertyValues(getResourceFromProperty(), newResourceFrom);
  }



  // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceOn

  public List<ServiceCenterITComputingResource> getResourceOn() {
      return new ArrayList<ServiceCenterITComputingResource>(getPropertyValuesAs(getResourceOnProperty(), ServiceCenterITComputingResource.class));
  }


  public RDFProperty getResourceOnProperty() {
      final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceOn";
      final String name = getOWLModel().getResourceNameForURI(uri);
      return getOWLModel().getRDFProperty(name);
  }


  public boolean hasResourceOn() {
      return getPropertyValueCount(getResourceOnProperty()) > 0;
  }


  public Iterator listResourceOn() {
      return listPropertyValuesAs(getResourceOnProperty(), ServiceCenterITComputingResource.class);
  }


  public void addResourceOn(ServiceCenterITComputingResource newResourceOn) {
      addPropertyValue(getResourceOnProperty(), newResourceOn);
  }


  public void removeResourceOn(ServiceCenterITComputingResource oldResourceOn) {
      removePropertyValue(getResourceOnProperty(), oldResourceOn);
  }


  public void setResourceOn(List<ServiceCenterITComputingResource> newResourceOn) {
      setPropertyValues(getResourceOnProperty(), newResourceOn);
  }



  // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

  public List<ServiceCenterITComputingResource> getResourcesTo() {
      return new ArrayList<ServiceCenterITComputingResource>(getPropertyValuesAs(getResourceToProperty(), ServiceCenterITComputingResource.class));
  }


  public RDFProperty getResourceToProperty() {
      final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo";
      final String name = getOWLModel().getResourceNameForURI(uri);
      return getOWLModel().getRDFProperty(name);
  }


  public boolean hasResourcesTo() {
      return getPropertyValueCount(getResourceToProperty()) > 0;
  }


  public Iterator listResourcesTo() {
      return listPropertyValuesAs(getResourceToProperty(), ServiceCenterITComputingResource.class);
  }


  public void addResourcesTo(ServiceCenterITComputingResource newResourceTo) {
      addPropertyValue(getResourceToProperty(), newResourceTo);
  }


  public void removeResourcesTo(ServiceCenterITComputingResource oldResourceTo) {
      removePropertyValue(getResourceToProperty(), oldResourceTo);
  }


  public void setResourcesTo(List<ServiceCenterITComputingResource> newResourceTo) {
      setPropertyValues(getResourceToProperty(), newResourceTo);
  }  */
}
