package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.impl.util.ModelAccess;
import model.interfaces.actions.SetServerStateActivity;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;


/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#SetServerStateActivity
 *
 * @version generated on Thu Aug 12 15:26:22 EEST 2010
 */
public class DefaultSetServerStateAction extends DefaultConsolidationAction
        implements SetServerStateActivity {


    private Integer oldServerState = 0;

    public DefaultSetServerStateAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultSetServerStateAction() {
    }


    public int getTargetServerState() {
        if (hasTargetServerState()) {
            return getPropertyValueLiteral(getTargetServerStateProperty()).getInt();
        } else {
            setTargetServerState(0);
            return 0;
        }
    }


    public RDFProperty getTargetServerStateProperty() {
        final String uri = "http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#targetServerState";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasTargetServerState() {
        return getPropertyValueCount(getTargetServerStateProperty()) > 0;
    }


    public void setTargetServerState(int newTargetServerState) {
        setPropertyValue(getTargetServerStateProperty(), new java.lang.Integer(newTargetServerState));
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        for (ContextResource resource : getResources()) {
            ServiceCenterServer server = (ServiceCenterServer) resource;
            oldServerState = server.getCurrentEnergyState();
            int newState = getTargetServerState();
            server.setCurrentEnergyState(newState);
            if (newState == 0) {
                server.setIsActive(false);
            } else {
                server.setIsActive(true);
            }
        }
    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        for (ContextResource resource : getResources()) {
            ServiceCenterServer server = (ServiceCenterServer) resource;
//            if (getTargetServerState() == 0) {
//                ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
//                proxy.sendServerToSleep();
//            } else {
//                ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(GlobalVars.GLOBAL_LOOP_CONTROLLER_IP);
//                proxy.wakeUpServer(server.getMacAddress(), server.getIpAddress(), 9);
//            }
            ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy();
            if (getTargetServerState() == 0) {

            }
        }
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        for (ContextResource resource : getResources()) {
            ServiceCenterServer server = (ServiceCenterServer) resource;
            server.setCurrentEnergyState(oldServerState);
            if (oldServerState == 0) {
                server.setIsActive(false);
            } else {
                server.setIsActive(true);
            }
        }
    }

    public void setCost(int cost) {

    }

    public int getCost() {
        if (oldServerState == 0) return 1200;
        else return 100;
    }

    @Override
    public String toString() {
        String serversList = "";
        for (ContextResource resource : getResources()) {
            ServiceCenterServer server = (ServiceCenterServer) resource;
            serversList += server.getLocalName() + ", ";
        }
        return this.getLocalName() + " " + serversList + " to " + this.getTargetServerState();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultSetServerStateAction)) {
            return false;
        }
        DefaultSetServerStateAction action = (DefaultSetServerStateAction) o;
        return (action.getName().equals(this.getName())) || (action.getResources().equals(this.getResources()));// && ((action.getTargetServerState() == this.getTargetServerState()) || (action.getTargetServerState() == this.oldServerState)));
    }
}
