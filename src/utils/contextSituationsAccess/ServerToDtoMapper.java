package utils.contextSituationsAccess;

import model.interfaces.resources.CPU;
import model.interfaces.resources.Core;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.dtos.ExtendedServerDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerToDtoMapper {
    private ServerToDtoMapper() {

    }

    public static ExtendedServerDto map(ServiceCenterServer server) {
        ExtendedServerDto dto = new ExtendedServerDto();
        CPU cpu = server.getCpuResources().iterator().next();
        MEM mem = server.getMemResources().iterator().next();
        Collection<Core> cores = cpu.getAssociatedCores();
        List<Integer> freeCPU = new ArrayList<Integer>();
        for (Core core : cores) {
            freeCPU.add(core.getMaximumWorkLoad().intValue() - core.getCurrentWorkLoad().intValue());
        }

        //TODO: de gandit ce facem cu DTO-urile. Daca tinem core-uri, daca nu, cum facem
//        dto.setFreeCPU(freeCPU);

//        dto.setFreeMemory(mem.getMaximumWorkLoad().intValue() - mem.getCurrentWorkLoad().intValue());
        dto.setIpAddress(server.getIpAddress());
        dto.setMacAddress(server.getMacAddress());
        dto.setMaximumCPU(cpu.getMaximumWorkLoad().intValue());
        dto.setMaximumMemory(mem.getMaximumWorkLoad().intValue());
        dto.setOptimumCPU(cpu.getOptimalWorkLoad().intValue());
        dto.setOptimumMemory(mem.getOptimalWorkLoad().intValue());
        dto.setTotalCPU(cpu.getMaximumWorkLoad().intValue());
        dto.setTotalCPU(mem.getMaximumWorkLoad().intValue());
        dto.setUsedCPU(cpu.getCurrentWorkLoad().intValue());
        dto.setUsedMemory(mem.getCurrentWorkLoad().intValue());
        dto.setServerName(server.getLocalName());
        dto.setCoreNo(cores.size());
        return dto;
    }
}
