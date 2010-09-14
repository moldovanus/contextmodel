package model.impl.ontologyImpl.actions;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import model.impl.util.ModelAccess;
import model.interfaces.actions.ApplicationAdaptationAction;
import model.interfaces.resources.Core;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#ApplicationAdaptationAction
 *
 * @version generated on Tue Jul 06 17:49:10 GMT 2010
 */
public class DefaultApplicationAdaptationAction extends DefaultContextAction
        implements ApplicationAdaptationAction {
    private int cost = 0;
    private int cpuMin;
    private int cpuMax;
    private int memMin;
    private int memMax;
    private double prevCpuMin;

    private double prevCpuMax;
    private double prevMemMin;
    private double prevMemMax;
    private ApplicationActivity task;
    public DefaultApplicationAdaptationAction(){
        
    }
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public DefaultApplicationAdaptationAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }

    public DefaultApplicationAdaptationAction(ApplicationActivity task) {
        this.task = task;
    }

    public DefaultApplicationAdaptationAction(ApplicationActivity task, int cpuMin, int cpuMax, int memMin, int memMax) {
        this.task = task;
        this.setCpuMin(cpuMin);
        this.setCpuMax(cpuMax);
        this.setMemMin(memMin);
        this.setMemMax(memMax);
    }

    @Override
    public void execute(ModelAccess modelAccess) {
        prevCpuMin = task.getCpuRequiredMinValue();
        prevCpuMax = task.getCpuRequiredMaxValue();
        prevMemMin = task.getMemRequiredMinValue();
        prevMemMax = task.getMemRequiredMaxValue();
        task.setCpuRequiredMinValue(cpuMin);
        task.setCpuRequiredMaxValue(cpuMax);
        task.setMemRequiredMinValue(memMin);
        task.setMemRequiredMaxValue(memMax);
    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        throw new UnsupportedOperationException("not implemented yet");
    
    }

    @Override
    public void undo(ModelAccess modelAccess) {
        task.setCpuRequiredMinValue((float) prevCpuMin);
        task.setCpuRequiredMaxValue((float) prevCpuMax);
        task.setMemRequiredMinValue((float) prevMemMin);
        task.setMemRequiredMaxValue((float) prevMemMax);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultApplicationAdaptationAction)) {
            return false;
        }

        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String toString() {
        return "Application Adaptation Action: name = "+this.getName()+" prevCpuMinValue ="+prevCpuMin+" prevCpuMax="+prevCpuMax+" prevMemMin="+prevMemMin+" prevMemMax="+prevMemMax+"\n"+
                " cpuMin="+cpuMin+ " cpuMax="+cpuMax+" memMin="+memMin + " memMax="+memMax+"\n"+
                "For Activity:"+ task.toString();
    }

    public int getCpuMin() {
        return cpuMin;
    }

    public void setCpuMin(int cpuMin) {
        this.cpuMin = cpuMin;
    }

    public int getCpuMax() {
        return cpuMax;
    }

    public void setCpuMax(int cpuMax) {
        this.cpuMax = cpuMax;
    }

    public int getMemMin() {
        return memMin;
    }

    public void setMemMin(int memMin) {
        this.memMin = memMin;
    }

    public int getMemMax() {
        return memMax;
    }

    public void setMemMax(int memMax) {
        this.memMax = memMax;
    }
    public ApplicationActivity getActivity(){
        return task;
    }
    public void setActivity(ApplicationActivity task){
        this.task = task;
    }

}
