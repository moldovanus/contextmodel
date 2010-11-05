package model.impl.ontologyImpl.resources;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;
import model.interfaces.resources.applications.ApplicationActivity;

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


    public void addAssociatedCore(Core newAssociatedCores) {
        addPropertyValue(getAssociatedCoresProperty(), newAssociatedCores);
        this.setCurrentWorkLoad(this.getCurrentWorkLoad() + newAssociatedCores.getCurrentWorkLoad());

    }


    public void removeAssociatedCore(Core oldAssociatedCores) {
        removePropertyValue(getAssociatedCoresProperty(), oldAssociatedCores);
        this.setCurrentWorkLoad(this.getCurrentWorkLoad() - oldAssociatedCores.getCurrentWorkLoad());

    }


    public void setAssociatedCores(List<Core> newAssociatedCores) {
        setPropertyValues(getAssociatedCoresProperty(), newAssociatedCores);
        this.setOptimalWorkLoad(newAssociatedCores.get(0).getOptimalWorkLoad());
        this.setMaximumWorkLoad(newAssociatedCores.get(0).getMaximumWorkLoad());
        double currentWorkload = 0;
        for (Core core : newAssociatedCores) {
            currentWorkload += core.getCurrentWorkLoad();
        }
        this.setCurrentWorkLoad(currentWorkload * newAssociatedCores.size());

    }


    public Double getCacheRate() {
        return getPropertyValueLiteral(getCacheRateProperty()).getDouble();
    }


    public RDFProperty getCacheRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cacheRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCacheRate() {
        return getPropertyValueCount(getCacheRateProperty()) > 0;
    }


    public void setCacheRate(Double newCacheRate) {
        setPropertyValue(getCacheRateProperty(), new java.lang.Float(newCacheRate));
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuClockRate

    public Double getCpuClockRate() {
        return getPropertyValueLiteral(getCpuClockRateProperty()).getDouble();
    }


    public RDFProperty getCpuClockRateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuClockRate";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasCpuClockRate() {
        return getPropertyValueCount(getCpuClockRateProperty()) > 0;
    }


    public void setCpuClockRate(Double newClockRate) {
        setPropertyValue(getCpuClockRateProperty(), new java.lang.Float(newClockRate));
    }

    @Override
    public boolean hasResourcesFor(ApplicationActivity activity) {

        if (this.getCurrentWorkLoad() + activity.getCpuRequiredMaxValue() * activity.getNumberOfCoresRequiredValue() > this.getMaximumWorkLoad() + this.getOptimalWorkLoad())
            return false;
        if (this.getCurrentWorkLoad() + activity.getCpuRequiredMaxValue() * activity.getNumberOfCoresRequiredValue() < this.getOptimalWorkLoad() / 2.0)
            return false;
        return true;
//        List<Core> cores = this.getAssociatedCores();
//        float requestedCoresNo = activity.getNumberOfCoresRequiredValue();
//        if (cores.size() < requestedCoresNo) {
//            return false;
//        }
//        if (requestedCoresNo * activity.getCpuRequiredMaxValue() <= this.getAssociatedCores().size()*((this.getAssociatedCores().get(0).getMaximumWorkLoad()+ this.getAssociatedCores().get(0).getOptimalWorkLoad()) / 2) - this.getCurrentWorkLoad())
//            return true;
//        else return false;
//            boolean hasResources = false;
//
//        for (Core core : cores) {
//            if (core.hasResourcesFor(activity)) {
//                requestedCoresNo--;
//            }
//            if (requestedCoresNo == 0) {
//                hasResources = true;
//                break;
//            }
//        }
//
//        return hasResources;
    }

    @Override
    public void addRunningActivity(ApplicationActivity activity) {

//        List<Core> cores = this.getAssociatedCores();
//        float requestedCoresNo = activity.getNumberOfCoresRequiredValue();
//        int coreCount = cores.size();
//        for (int i = 0; i < coreCount; i++) {
//            Core core = cores.get(i);
//            if (core.hasResourcesFor(activity)) {
//                core.addRunningActivity(activity);
//                activity.addReceivedCoreIndex(i);
//                requestedCoresNo--;
//            }
//            if (requestedCoresNo == 0) {
//                break;
//            }
//        }
        //TODO: check if ok
        this.setCurrentWorkLoad(this.getCurrentWorkLoad() + activity.getCpuRequiredMaxValue());
        super.addRunningActivity(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void removeRunningActivity(ApplicationActivity activity) {
        List<Core> cores = this.getAssociatedCores();
        int coreCount = cores.size();
        for (int i = 0; i < coreCount; i++) {
            Core core = cores.get(i);
            if (core.getRunningActivities().contains(activity)) {
                core.removeRunningActivity(activity);

            }
        }
        this.setCurrentWorkLoad(this.getCurrentWorkLoad() - activity.getCpuAllocatedValue());
        super.removeRunningActivity(activity);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
