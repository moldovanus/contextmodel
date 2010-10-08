package utils.worldInterface.dtos;


import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 8, 2010
 * Time: 10:51:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerDto implements Serializable {
    public int coreCount;
    public int totalCPU;
    public List<Integer> freeCPU;
    public List<StorageDto> storage;
    public int totalMemory;
    public int freeMemory;
    private String serverName;

    @Override
    public boolean equals(Object sv) {
        if (sv instanceof ServiceCenterServer) {
            ServiceCenterServer server = (ServiceCenterServer) sv;
            CPU cpu = server.getCpuResources().iterator().next();
            Collection<Core> cores = cpu.getAssociatedCores();
            ArrayList<Integer> freeCpus = new ArrayList(cores.size());
//            Storage s = server.getAssociatedStorage();
//            int st = 0;
//            for (int i = 0; i < storage.size(); i++) {
//                st += storage.get(i).getFreeSpace();
//            }
//            if (st != (s.getTotal() - s.getUsed())) {
//                return false;
//            }

            int i = 0;
            for (Core core : cores) {
                if (freeCPU.get(i) != core.getMaximumWorkLoad() - core.getCurrentWorkLoad()) {
                    i++;
                    return false;
                }
                i++;
                if (core.getMaximumWorkLoad() != totalCPU)
                    return false;
            }

            MEM memory = server.getMemResources().iterator().next();

            if (freeMemory != (memory.getMaximumWorkLoad() - memory.getCurrentWorkLoad()))
                return false;

            if (totalMemory != memory.getMaximumWorkLoad()) return false;

            if (coreCount != cores.size()) return false;
        }
        if (sv instanceof ServerDto) {
            ServerDto sDto = (ServerDto) sv;
            if (sDto.getCoreCount() != coreCount) return false;
            List<Integer> freeCpus = sDto.getFreeCPU();
            for (Integer freeCpu : freeCpus) {
                if (!freeCPU.contains(freeCpu)) return false;
            }

            List<StorageDto> storages = sDto.getStorage();
            if (!(storage==null && storages==null ))   {
                if (storages == null) return false;
            for (StorageDto st : storages) {
                if (!storage.contains(st)) return false;
            }
            }
            if (totalMemory != sDto.getTotalMemory()) return false;
            if (freeMemory != sDto.getFreeMemory()) return false;
            if (totalCPU != sDto.getTotalCPU()) return false;
        }

        return true;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public int getTotalCPU() {
        return totalCPU;
    }

    public void setTotalCPU(int totalCPU) {
        this.totalCPU = totalCPU;
    }

    public List<Integer> getFreeCPU() {
        return freeCPU;
    }

    public void setFreeCPU(List<Integer> freeCPU) {
        this.freeCPU = freeCPU;
    }

    public List<StorageDto> getStorage() {
        return storage;
    }

    public void setStorage(List<StorageDto> storage) {
        this.storage = storage;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(int freeMemory) {
        this.freeMemory = freeMemory;
    }


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
