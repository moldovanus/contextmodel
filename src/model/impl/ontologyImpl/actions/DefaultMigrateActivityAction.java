package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import globalLoop.utils.GlobalVars;
import model.impl.util.ModelAccess;
import model.interfaces.actions.MigrateActivity;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.Core;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;

import java.util.Collection;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#MigrateActivity
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultMigrateActivityAction extends DefaultConsolidationAction
        implements MigrateActivity {
    private int cost = 1000;

    public DefaultMigrateActivityAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultMigrateActivityAction() {
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        ServiceCenterITComputingResource from = getResourceFrom();
        ServiceCenterITComputingResource to = getResourceTo();
        ApplicationActivity activity = getActivity();
        from.removeRunningActivity(activity);
        to.addRunningActivity(activity);
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        ServiceCenterITComputingResource from = getResourceFrom();
        ServiceCenterITComputingResource to = getResourceTo();
        ApplicationActivity activity = getActivity();
        to.removeRunningActivity(activity);
        from.addRunningActivity(activity);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activity

    public ApplicationActivity getActivity() {
        return (ApplicationActivity) getPropertyValueAs(getActivityProperty(), ApplicationActivity.class);
    }


    public RDFProperty getActivityProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#activity";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasActivity() {
        return getPropertyValueCount(getActivityProperty()) > 0;
    }


    public void setActivity(ApplicationActivity newActivity) {
        setPropertyValue(getActivityProperty(), newActivity);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceFrom

    public ServiceCenterITComputingResource getResourceFrom() {
        return (ServiceCenterITComputingResource)
                ((Collection<ContextResource>) getPropertyValuesAs(getResourcesProperty(), ContextResource.class)).toArray()[0];
    }

    public boolean hasResourceFrom() {
        return getPropertyValueCount(getResourcesProperty()) > 1;
    }


    public void setResourceFrom(ServiceCenterITComputingResource newResourceFrom) {
//        ContextResource resourceTo = getResourceTo();
//        removePropertyValue(getResourcesProperty(), getResourceFrom());
//        removePropertyValue(getResourcesProperty(), resourceTo);
        addPropertyValue(getResourcesProperty(), newResourceFrom);
//        setPropertyValue(getResourcesProperty(), resourceTo);
    }


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource)
                ((Collection<ContextResource>) getPropertyValuesAs(getResourcesProperty(), ContextResource.class)).toArray()[1];
    }


    public boolean hasResourceTo() {
        return getPropertyValueCount(getResourcesProperty()) > 2;
    }


    public void setResourceTo(ServiceCenterITComputingResource newResourceTo) {
//        ContextResource resourceFrom = getResourceFrom();
//        removePropertyValue(getResourcesProperty(), resourceFrom);
//        removePropertyValue(getResourcesProperty(), getResourceTo());
//        addPropertyValue(getResourcesProperty(), resourceFrom);
        addPropertyValue(getResourcesProperty(), newResourceTo);
    }

    @Override
    public String toString() {
        return this.getLocalName() + " " + this.getActivity().getLocalName() + " from "
                + this.getResourceFrom().getLocalName() + " to " + this.getResourceTo().getLocalName();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultMigrateActivityAction)) {
            return false;
        }
        DefaultMigrateActivityAction activityAction = (DefaultMigrateActivityAction) o;
        return activityAction.getName().equals(this.getName())
                || activityAction.getResourceFrom().equals(this.getResourceFrom())
                && activityAction.getResourceTo().equals(this.getResourceTo())
                && activityAction.getActivity().equals(this.getActivity());

    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        ServiceCenterServer oldServer = (ServiceCenterServer) getResourceFrom();
        ServiceCenterServer newServer = (ServiceCenterServer) getResourceTo();
        ApplicationActivity task = getActivity();
        ServerManagementProxyInterface oldServerProxy = ProxyFactory.createServerManagementProxy(oldServer.getIpAddress());
        ServerManagementProxyInterface newServerProxy = ProxyFactory.createServerManagementProxy(newServer.getIpAddress());
        if (oldServerProxy != null && newServerProxy != null) {
            String path = newServer.getHddResources().iterator().next().getPhysicalPath();
            int procTime = ((int) task.getCpuRequiredMaxValue() * 100) /
                          ((Core) newServer.getCpuResources().iterator().next().getAssociatedCores().iterator().next()).getMaximumWorkLoad().intValue();
                String base = "";
            if ((task.getCPUWeight() >= task.getMEMWeight() ) && (task.getCPUWeight()>= task.getHDDWeight())){
                base = GlobalVars.BASE_VM_NAME_CPU;                                                           }
            else{
                if ((task.getMEMWeight()>= task.getHDDWeight()) &&(task.getMEMWeight()>= task.getCPUWeight()))
                {
                    base = GlobalVars.BASE_VM_NAME_MEM;
                }
                else
                {
                    base = GlobalVars.BASE_VM_NAME_HDD;
                }
            }
            System.out.println("Moving ...");
            newServerProxy.deployVirtualMachineWithCustomResources(GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH,
                    GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH + newServer.getLocalName(), newServer.getLocalName(),base,
                    task.getLocalName(), task.getLocalName(), (int) task.getMemRequiredMaxValue(),
                    procTime, (int) task.getNumberOfCoresAllocatedValue());
            oldServerProxy.stopVirtualMachine(task.getLocalName());
            oldServerProxy.deleteVirtualMachine(task.getLocalName());
//            oldServerProxy.moveSourceActions(GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH + oldServer.getLocalName() + task.getLocalName(),
//                    task.getLocalName());
//            newServerProxy.moveDestinationActions(GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH + oldServer.getLocalName() + task.getLocalName(),
//                     GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH+newServer.getLocalName() ,
//                    task.getLocalName());

        } else {
            System.err.println("Proxy is null");
        }
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
