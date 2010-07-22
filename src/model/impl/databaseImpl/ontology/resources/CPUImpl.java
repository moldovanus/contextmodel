package model.impl.databaseImpl.ontology.resources;

import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 12:37:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CPUImpl extends SimpleResourceImpl implements CPU {
    private List<Core> associatedCores;
    private float cacheRate;
    private float clockRate;

    public List<Core> getAssociatedCores() {
        return associatedCores;
    }

    public void setAssociatedCores(List<Core> associatedCores) {
        this.associatedCores = associatedCores;
    }

    public float getCacheRate() {
        return cacheRate;
    }

    public void setCacheRate(float cacheRate) {
        this.cacheRate = cacheRate;
    }

    public float getClockRate() {
        return clockRate;
    }

    public void setClockRate(float clockRate) {
        this.clockRate = clockRate;
    }
}
