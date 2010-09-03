package model.impl.ontologyImpl.resources;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.List;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ServiceCenterServer
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultServiceCenterServer extends DefaultComplexResource
        implements ServiceCenterServer {

    public DefaultServiceCenterServer(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultServiceCenterServer() {
    }

    public boolean hostsActivity(ApplicationActivity activity) {
        List<ApplicationActivity> activities = getRunningActivities();
        for (ApplicationActivity a : activities) {
            if (a.getName().equals(activity.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addRunningActivity(ApplicationActivity activity) {
        Collection<CPU> cpus = this.getCpuResources();
        Collection<MEM> mems = this.getMemResources();
        Collection<HDD> hdds = this.getHddResources();

        for (CPU cpu : cpus) {
            if (cpu.hasResourcesFor(activity)) {
                cpu.addRunningActivity(activity);
                break;
            }
        }

        for (MEM mem : mems) {
            if (mem.hasResourcesFor(activity)) {
                mem.addRunningActivity(activity);
                break;
            }
        }

        for (HDD hdd : hdds) {
            if (hdd.hasResourcesFor(activity)) {
                hdd.addRunningActivity(activity);
            }
        }

        super.addRunningActivity(activity);    //To change body of overridden methods use File | Settings | File Templates.
        activity.setNumberOfCoresAllocatedValue(activity.getNumberOfCoresRequiredValue());
        activity.setCpuAllocatedValue(activity.getCpuRequiredMaxValue());
        activity.setMemAllocatedValue(activity.getMemRequiredMaxValue());
        activity.setHddAllocatedValue(activity.getHddRequiredMaxValue());
    }

    @Override
    public void removeRunningActivity(ApplicationActivity activity) {
        Collection<CPU> cpus = this.getCpuResources();
        Collection<MEM> mems = this.getMemResources();
        Collection<HDD> hdds = this.getHddResources();

        for (CPU cpu : cpus) {
            if (cpu.getRunningActivities().contains(activity)) {
                cpu.removeRunningActivity(activity);
                activity.removeAllReceivedCoreIndex();
                break;
            }
        }

        for (MEM mem : mems) {
            if (mem.getRunningActivities().contains(activity)) {
                mem.removeRunningActivity(activity);
                break;
            }
        }

        for (HDD hdd : hdds) {
            if (hdd.getRunningActivities().contains(activity)) {
                hdd.removeRunningActivity(activity);
            }
        }

        super.removeRunningActivity(activity);
        activity.setCpuAllocatedValue(0);
        activity.setMemAllocatedValue(0);
        activity.setHddAllocatedValue(0);
    }

    @Override
    public String toString() {
        String description = "";
        description += this.getLocalName() + "\n";
        description += "Active:" + this.getIsActive() + "\n";
        Collection<ServiceCenterITComputingResource> resources = this.getMemResources();
        for (ServiceCenterITComputingResource resource : resources) {
            description += " " + resource.getLocalName();
            description += " maxWorkload: " + resource.getMaximumWorkLoad();
            description += " currentWorkload: " + resource.getCurrentWorkLoad() + "\n";
        }

        resources = this.getHddResources();
        for (ServiceCenterITComputingResource resource : resources) {
            description += " " + resource.getLocalName();
            description += " maxWorkload: " + resource.getMaximumWorkLoad();
            description += " currentWorkload: " + resource.getCurrentWorkLoad() + "\n";
        }

        List<Core> cores = ((CPU) this.getCpuResources().iterator().next()).getAssociatedCores();
        for (Core resource : cores) {
            description += " " + resource.getLocalName();
            description += " maxWorkload: " + resource.getMaximumWorkLoad();
            description += " currentWorkload: " + resource.getCurrentWorkLoad() + "\n";
        }
        return description;
    }

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ipAddress

    public String getIpAddress() {
        return (String) getPropertyValue(getIpAddressProperty());
    }


    public RDFProperty getIpAddressProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ipAddress";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIpAddress() {
        return getPropertyValueCount(getIpAddressProperty()) > 0;
    }


    public void setIpAddress(String newIpAddress) {
        setPropertyValue(getIpAddressProperty(), newIpAddress);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#macAddress

    public String getMacAddress() {
        return (String) getPropertyValue(getMacAddressProperty());
    }


    public RDFProperty getMacAddressProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#macAddress";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasMacAddress() {
        return getPropertyValueCount(getMacAddressProperty()) > 0;
    }


    public void setMacAddress(String newMacAddress) {
        setPropertyValue(getMacAddressProperty(), newMacAddress);
    }
}
