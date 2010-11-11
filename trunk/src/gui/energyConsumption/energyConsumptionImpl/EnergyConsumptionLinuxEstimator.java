package gui.energyConsumption.energyConsumptionImpl;

import gui.energyConsumption.EnergyConsumption;
import model.impl.util.ModelAccess;
import model.interfaces.resources.CPU;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import utils.exceptions.ApplicationException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: oneadmin
 * Date: Nov 10, 2010
 * Time: 11:33:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnergyConsumptionLinuxEstimator implements EnergyConsumption {
    private ModelAccess modelAccess;
    public static final int BASE_POWER_CONSUMPTION = 90;
    private double previousPowerConsumption = 0;

    public EnergyConsumptionLinuxEstimator(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }

    public Number getValueWithRunningAlgorithm() throws ApplicationException {
        double toReturn = 0.0;
        if (modelAccess.isSimulation()) {
            toReturn = previousPowerConsumption;
        } else {
            Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();

            for (ServiceCenterServer server : servers) {
                if (server.getIsActive()) toReturn += BASE_POWER_CONSUMPTION;
                for (CPU cpu : server.getCpuResources()) {
                    if (cpu.getCurrentWorkLoad() > (cpu.getMaximumWorkLoad() + cpu.getOptimalWorkLoad()) / 2.0)
                        toReturn += (cpu.getCurrentWorkLoad() - (cpu.getMaximumWorkLoad() + cpu.getOptimalWorkLoad()) / 2.0) * 100 / cpu.getMaximumWorkLoad();

                }
                for (MEM mem : server.getMemResources()) {
                    if (mem.getCurrentWorkLoad() > (mem.getMaximumWorkLoad() + mem.getOptimalWorkLoad()) / 2.0)
                        toReturn += (mem.getCurrentWorkLoad() - (mem.getMaximumWorkLoad() + mem.getOptimalWorkLoad()) / 2.0) * 100 / mem.getMaximumWorkLoad();
                }
            }
            previousPowerConsumption = toReturn;
        }
        return toReturn;
    }

    public Number getValueWithoutAlgorithm() throws ApplicationException {
        double toReturn = 0.0;
        if (modelAccess.isSimulation()) {
            toReturn = previousPowerConsumption;
        } else {
            double totalCpu = 0.0;
            double totalMem = 0.0;
            double acceptableCpu = 0.0;
            double acceptableMem = 0.0;
            Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
            for (ServiceCenterServer server : servers) {
                toReturn += BASE_POWER_CONSUMPTION;
                if (server.getIsActive()) {


                    for (CPU cpu : server.getCpuResources()) {
                        totalCpu += cpu.getCurrentWorkLoad();
                        acceptableCpu += (cpu.getMaximumWorkLoad() + cpu.getOptimalWorkLoad()) / 2.0;

                    }
                    for (MEM mem : server.getMemResources()) {
                        totalMem += mem.getCurrentWorkLoad();
                        acceptableMem += (mem.getMaximumWorkLoad() + mem.getOptimalWorkLoad()) / 2.0;
                    }
                }
            }
            if (totalCpu != 0 && totalCpu > acceptableCpu)
                toReturn += ((totalCpu - acceptableCpu) * 100.0 / totalCpu);
            if (totalMem != 0 && totalMem > acceptableMem)
                toReturn += ((totalMem - acceptableMem) * 100.0 / totalMem);
            previousPowerConsumption = toReturn;
        }
        return toReturn;
    }
}
