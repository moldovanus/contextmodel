package model.impl.databaseImpl.resources;

import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:16:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceCenterITComputingResourceImpl extends ContextResourceImpl implements ServiceCenterITComputingResource {
    private List<Integer> energyStates;
    private List<ApplicationActivity> runningActivities;

    private Double maximumWorkLoad;
    private Double optimalWorkLoad;
    private Double currentWorkLoad;
    private Integer currentEnergyState;

    public ServiceCenterITComputingResourceImpl() {
        energyStates = new ArrayList<Integer>();
        runningActivities = new ArrayList<ApplicationActivity>();
    }

    public List<Integer> getEnergyStates() {
        return energyStates;
    }

    public void setEnergyStates(List<Integer> energyStates) {
        this.energyStates = energyStates;
    }

    public List<ApplicationActivity> getRunningActivities() {
        return runningActivities;
    }

    public void setRunningActivities(List<ApplicationActivity> runningActivities) {
        this.runningActivities = runningActivities;
    }

    public Double getMaximumWorkLoad() {
        return maximumWorkLoad;
    }

    public void setMaximumWorkLoad(Double maximumWorkLoad) {
        this.maximumWorkLoad = maximumWorkLoad;
    }

    public Double getOptimalWorkLoad() {
        return optimalWorkLoad;
    }

    public void setOptimalWorkLoad(Double optimalWorkLoad) {
        this.optimalWorkLoad = optimalWorkLoad;
    }

    public Double getCurrentWorkLoad() {
        return currentWorkLoad;
    }

    public void setCurrentWorkLoad(Double currentWorkLoad) {
        this.currentWorkLoad = currentWorkLoad;
    }

    public Integer getCurrentEnergyState() {
        return currentEnergyState;
    }

    public void setCurrentEnergyState(Integer currentEnergyState) {
        this.currentEnergyState = currentEnergyState;
    }
}
