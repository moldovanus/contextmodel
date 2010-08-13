package model.interfaces.resources;

import model.interfaces.resources.applications.ApplicationActivity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:52:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceCenterITComputingResource extends ContextResource {
    //TODO: ApplicationActivity nu trebe scos din asta?NU NU. k aici intra de ex pe un SCITCR daca i CPU numa partea de CPU.
    //cumva sa poti rula partea de CPU pe un server, de mem pe alt server, de astea. Ink imposibile da cine stie
    List<Integer> getEnergyStates();

    void setEnergyStates(List<Integer> states);

    List<ApplicationActivity> getRunningActivities();

    void setRunningActivities(List<ApplicationActivity> states);

    void addRunningActivity(ApplicationActivity applicationActivity);

    void removeRunningActivity(ApplicationActivity applicationActivity);

    Double getMaximumWorkLoad();

    void setMaximumWorkLoad(Double value);

    Double getOptimalWorkLoad();

    void setOptimalWorkLoad(Double value);

    Double getCurrentWorkLoad();

    void setCurrentWorkLoad(Double value);

    Integer getCurrentEnergyState();

    void setCurrentEnergyState(Integer value);
}
