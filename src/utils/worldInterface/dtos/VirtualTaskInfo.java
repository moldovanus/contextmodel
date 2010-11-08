package utils.worldInterface.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 11:11:10 AM
 * To change this template use File | Settings | File Templates.
 */

public class VirtualTaskInfo {
    private VirtualNetworkInfo virtualNetworkInfo;
    private List<VirtualDiskInfo> virtualDiskInfos;

    private String taskName;
    private int id;

    private int requestedCPU;
    private int requestedMemory;

    public VirtualTaskInfo(String taskName) {
        virtualDiskInfos = new ArrayList<VirtualDiskInfo>();
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public VirtualNetworkInfo getNetworkInfo() {
        return virtualNetworkInfo;
    }

    public int getRequestedCPU() {
        return requestedCPU;
    }

    public int getRequestedMemory() {
        return requestedMemory;
    }


    public List<VirtualDiskInfo> getDiskInfos() {
        return virtualDiskInfos;
    }


    public List<VirtualDiskInfo> getDiskDtos() {
        return virtualDiskInfos;
    }

    public void addDiskDto(VirtualDiskInfo virtualDiskInfo) {
        this.virtualDiskInfos.add(virtualDiskInfo);
    }


    public void setRequestedCPU(int requestedCPU) {
        this.requestedCPU = requestedCPU;
    }

    public void setRequestedMemory(int requestedMemory) {
        this.requestedMemory = requestedMemory;
    }

    public void setNetworkInfo(VirtualNetworkInfo virtualNetworkInfo) {
        this.virtualNetworkInfo = virtualNetworkInfo;
    }
}
