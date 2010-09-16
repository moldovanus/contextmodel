package gui.energyConsumption.energyConsumptionImpl;

import gui.energyConsumption.EnergyConsumption;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.datacenterInterface.proxies.impl.StubProxy;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 9, 2010
 * Time: 11:02:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnergyEstimator implements EnergyConsumption {
    private ModelAccess modelAccess;
    public static final int BASE_POWER_CONSUMPTION = 115;

    public EnergyEstimator(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }

    public Number getValueWithRunningAlgorithm() {
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        double totalEnergyConsumed = 0;
        for (ServiceCenterServer server : servers) {
            if (server.getIsActive()) {
                ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
                if (proxy instanceof StubProxy) {
                    totalEnergyConsumed += BASE_POWER_CONSUMPTION;
                } else {
                    String energyConsumption = proxy.getEnergyConsumptionInfo();
                    double d = Float.parseFloat(energyConsumption);
                    totalEnergyConsumed += d;
                }
            }
        }
        return totalEnergyConsumed;
    }

    public Number getValueWithoutAlgorithm() {
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        int totalEnergyConsumed = 0;
        for (ServiceCenterServer server : servers) {
            if (server.getIsActive()) {
                ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
                if (proxy instanceof StubProxy) {
                    totalEnergyConsumed += BASE_POWER_CONSUMPTION;
                } else {
                    String energyConsumption = proxy.getEnergyConsumptionInfo();
                    double d = Float.parseFloat(energyConsumption);
                    totalEnergyConsumed += d;
                }
            } else {
                totalEnergyConsumed += BASE_POWER_CONSUMPTION;
            }
        }
        return totalEnergyConsumed;
    }
}
