package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.impl.util.ModelAccess;
import model.interfaces.actions.ServerAdaptationAction;
import model.interfaces.resources.CPU;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 7, 2010
 * Time: 11:37:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultServerAdaptationAction extends DefaultITComputingResourceAdaptationAction implements ServerAdaptationAction {
    private int cost = 10;
    private ServiceCenterServer server ;
    private int newOptimalValueForCpu;
    private int newOptimalValueForMem;
    private Double oldOptimalValueForCpu;
    private Double oldOptimalValueForMem;
        public DefaultServerAdaptationAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }
    public DefaultServerAdaptationAction (ServiceCenterServer server, int newOptimalValueForCpu, int newOptimalValueForMem) {
        this.server = server;
        this.setNewOptimalValueForCpu(newOptimalValueForCpu);
        this.setNewOptimalValueForMem(newOptimalValueForMem);
    }

    @Override
    public void execute(ModelAccess modelAccess) {
      CPU cpu = server.getCpuResources().iterator().next();
      MEM mem =   server.getMemResources().iterator().next();
      oldOptimalValueForCpu = cpu.getAssociatedCores().get(0).getOptimalWorkLoad();
      oldOptimalValueForMem = mem.getOptimalWorkLoad();
      cpu.setOptimalWorkLoad((double) getNewOptimalValueForCpu());
      mem.setOptimalWorkLoad((double) getNewOptimalValueForMem());
    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getNewOptimalValueForCpu() {
        return newOptimalValueForCpu;
    }

    public void setNewOptimalValueForCpu(int newOptimalValueForCpu) {
        this.newOptimalValueForCpu = newOptimalValueForCpu;
    }

    public int getNewOptimalValueForMem() {
        return newOptimalValueForMem;
    }

    public void setNewOptimalValueForMem(int newOptimalValueForMem) {
        this.newOptimalValueForMem = newOptimalValueForMem;
    }
    public void setServer(ServiceCenterServer server){
        this.server = server;
    }
    public ServiceCenterServer getServer(){
        return server;
    }
}
