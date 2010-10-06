package utils.worldInterface.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:27:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionDto implements Serializable {
    private List<String> contextResources;
    private float cost;
    private String actionClassName;
    private String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public List<String> getContextResources() {
        return contextResources;
    }

    public void setContextResources(List<String> contextResources) {
        this.contextResources = contextResources;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getActionClassName() {
        return actionClassName;
    }

    public void setActionClassName(String actionClassName) {
        this.actionClassName = actionClassName;
    }
}
