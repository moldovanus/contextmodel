package utils.worldInterface.dtos;

import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 12:52:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedTaskDto extends TaskDto{
    private float cpuWeight;
    private float memWeight;
    private float hddWeight;

    public float getCpuWeight() {
        return cpuWeight;
    }

    public void setCpuWeight(float cpuWeight) {
        this.cpuWeight = cpuWeight;
    }

    public float getMemWeight() {
        return memWeight;
    }

    public void setMemWeight(float memWeight) {
        this.memWeight = memWeight;
    }

    public float getHddWeight() {
        return hddWeight;
    }

    public void setHddWeight(float hddWeight) {
        this.hddWeight = hddWeight;
    }

       public double distanceTo(ExtendedTaskDto task) {
        double minDistance = 1000000f;
        double memory1[] = {this.getRequestedMemoryMin(), this.getRequestedMemoryMax(), this.getRequestedMemoryMax(), this.getRequestedMemoryMin(), this.getRequestedMemoryMin(), this.getRequestedMemoryMax(), this.getRequestedMemoryMax(), this.getRequestedMemoryMin()};
        double cpu1[] = {this.getRequestedCPUMin(), this.getRequestedCPUMin(), this.getRequestedCPUMax(), this.getRequestedCPUMax(), this.getRequestedCPUMin(), this.getRequestedCPUMin(), this.getRequestedCPUMax(), this.getRequestedCPUMax()};
        double hdd1[] = {this.getRequestedStorageMax(), this.getRequestedStorageMax(), this.getRequestedStorageMax(), this.getRequestedStorageMax(), this.getRequestedStorageMin(), this.getRequestedStorageMin(), this.getRequestedStorageMin(), this.getRequestedStorageMin()};
        double memory2[] = {task.getRequestedMemoryMin(), task.getRequestedMemoryMax(), task.getRequestedMemoryMax(), task.getRequestedMemoryMin(), task.getRequestedMemoryMin(), task.getRequestedMemoryMax(), task.getRequestedMemoryMax(), task.getRequestedMemoryMin()};
        double cpu2[] = {task.getRequestedCPUMin(), task.getRequestedCPUMin(), task.getRequestedCPUMax(), task.getRequestedCPUMax(), task.getRequestedCPUMin(), task.getRequestedCPUMin(), task.getRequestedCPUMax(), task.getRequestedCPUMax()};
         double hdd2[] = {task.getRequestedStorageMax(), task.getRequestedStorageMax(), task.getRequestedStorageMax(), task.getRequestedStorageMax(), task.getRequestedStorageMin(), task.getRequestedStorageMin(), task.getRequestedStorageMin(), task.getRequestedStorageMin()};
      for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double dist = Math.sqrt((Math.pow(memory1[i] - memory2[j], 2) + Math.pow((cpu1[i] - cpu2[j]), 2) + Math.pow((hdd1[i] - hdd2[j]), 2)));
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }
}
