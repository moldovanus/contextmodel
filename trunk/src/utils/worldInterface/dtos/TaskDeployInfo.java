package utils.worldInterface.dtos;

import utils.worldInterface.dtos.DiskInfo;
import utils.worldInterface.dtos.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 11:11:10 AM
 * To change this template use File | Settings | File Templates.
 */

public class TaskDeployInfo {
    private NetworkInfo networkInfo;
    private List<DiskInfo> diskInfos;

    private String taskName;

    private int requestedCPU;
    private int requestedMemory;

    public TaskDeployInfo(String taskName) {
        diskInfos = new ArrayList<DiskInfo>();
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public int getRequestedCPU() {
        return requestedCPU;
    }

    public int getRequestedMemory() {
        return requestedMemory;
    }


    public List<DiskInfo> getDiskInfos() {
        return diskInfos;
    }


    public List<DiskInfo> getDiskDtos() {
        return diskInfos;
    }

    public void addDiskDto(DiskInfo diskInfo) {
        this.diskInfos.add(diskInfo);
    }


    public NetworkInfo getNetworkDto() {
        return networkInfo;
    }

    public void setNetworkDto(NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }

    public void setRequestedCPU(int requestedCPU) {
        this.requestedCPU = requestedCPU;
    }

    public void setRequestedMemory(int requestedMemory) {
        this.requestedMemory = requestedMemory;
    }

    public void setNetworkInfo(NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }
}
