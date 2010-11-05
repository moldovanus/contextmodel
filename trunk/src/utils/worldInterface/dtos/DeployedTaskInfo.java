package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 5, 2010
 * Time: 10:14:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeployedTaskInfo {
    private int taskID;
    private int networkID;

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getNetworkID() {
        return networkID;
    }

    public void setNetworkID(int networkID) {
        this.networkID = networkID;
    }
}
