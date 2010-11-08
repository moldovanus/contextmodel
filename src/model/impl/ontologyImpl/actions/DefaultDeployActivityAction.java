package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import globalLoop.utils.GlobalVars;
import model.impl.util.ModelAccess;
import model.interfaces.actions.DeployActivity;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.Core;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#DeployActivity
 *
 * @version generated on Tue Jul 06 17:49:11 GMT 2010
 */
public class DefaultDeployActivityAction extends DefaultConsolidationAction
        implements DeployActivity {
    private int cost = 200;

    public DefaultDeployActivityAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultDeployActivityAction() {
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


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#resourceTo

    public ServiceCenterITComputingResource getResourceTo() {
        return (ServiceCenterITComputingResource)
                getResources().toArray()[0];
    }


    public void setResourceTo(ServiceCenterITComputingResource newResourceTo) {
        if (hasResources()) {
            ContextResource resourceTo = getResourceTo();
            removePropertyValue(getResourcesProperty(), resourceTo);
        }

        addPropertyValue(getResourcesProperty(), newResourceTo);
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        ServiceCenterServer server = (ServiceCenterServer) getResourceTo();
        server.addRunningActivity(getActivity());
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        ServiceCenterServer server = (ServiceCenterServer) getResourceTo();
        server.removeRunningActivity(getActivity());
    }

    @Override
    public String toString() {
        return this.getLocalName() + " " + this.getActivity().getLocalName() + " to " + this.getResourceTo().getLocalName();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultDeployActivityAction)) {
            return false;
        }
        DefaultDeployActivityAction activityAction = (DefaultDeployActivityAction) o;
        return activityAction.getName().equals(this.getName()) || activityAction.getResourceTo().equals(this.getResourceTo()) && activityAction.getActivity().equals(this.getActivity());
    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        ServiceCenterServer server = (ServiceCenterServer) getResourceTo();
        ApplicationActivity task = getActivity();
        ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy();
        if (proxy != null) {
            int procTime = ((int) task.getCpuRequiredMaxValue() * 100) /
                    ((Core) server.getCpuResources().iterator().next().getAssociatedCores().iterator().next()).getMaximumWorkLoad().intValue();
            String path = server.getHddResources().iterator().next().getPhysicalPath();
            String base = "";
            if ((task.getCPUWeight() >= task.getMEMWeight()) && (task.getCPUWeight() >= task.getHDDWeight())) {
                base = GlobalVars.BASE_VM_NAME_CPU;
            } else {
                if ((task.getMEMWeight() >= task.getHDDWeight()) && (task.getMEMWeight() >= task.getCPUWeight())) {
                    base = GlobalVars.BASE_VM_NAME_MEM;
                } else {
                    base = GlobalVars.BASE_VM_NAME_HDD;
                }
            }

            System.out.println("Deploying ...");
            //TODO :DefaultDeployActivity deploy not yet implemented fully
            System.err.println("DefaultDeployActivity deploy not yet implementef fully. Todo");
//            proxy.deployVirtualMachineWithCustomResources(GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH,
//                    GlobalVars.VIRTUAL_MACHINES_NETWORK_PATH , server.getLocalName(),base,
//                    task.getLocalName(), task.getLocalName(), (int) task.getMemRequiredMaxValue(),
//                    procTime, (int) task.getNumberOfCoresAllocatedValue());
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
