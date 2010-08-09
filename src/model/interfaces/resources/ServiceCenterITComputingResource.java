package model.interfaces.resources;

import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:52:11 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceCenterITComputingResource extends ContextResource {
    List<Integer> getEnergyStates();

    void setEnergyStates(List<Integer> states);

    List<ApplicationActivity> getRunningActivities();

    void setRunningActivities(List<ApplicationActivity> states);

    Double getMaximumWorkLoad();

    void setMaximumWorkLoad(Double value);

    Double getOptimalWorkLoad();

    void setOptimalWorkLoad(Double value);

    Double getCurrentWorkLoad();

    void setCurrentWorkLoad(Double value);

    Integer getCurrentEnergyState();

    void setCurrentEnergyState(Integer value);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#energyStates

    boolean hasEnergyStates();

    Iterator listEnergyStates();

    void addEnergyStates(int newEnergyStates);

    void removeEnergyStates(int oldEnergyStates);

    void setEnergyStates(Collection newEnergyStates);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#maximumWorkload

    float getMaximumWorkload();

    boolean hasMaximumWorkload();

    void setMaximumWorkload(float newMaximumWorkload);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#optimalWorkload

    float getOptimalWorkload();

    boolean hasOptimalWorkload();

    void setOptimalWorkload(float newOptimalWorkload);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#runningActivities

    boolean hasRunningActivities();

    Iterator listRunningActivities();

    void addRunningActivities(ApplicationActivity newRunningActivities);

    void removeRunningActivities(ApplicationActivity oldRunningActivities);

    void setRunningActivities(Collection newRunningActivities);
}
