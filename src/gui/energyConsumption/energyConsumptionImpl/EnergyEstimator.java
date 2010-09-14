package gui.energyConsumption.energyConsumptionImpl;

import gui.energyConsumption.EnergyConsumption;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.impl.HyperVServerManagementProxy;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;

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
      public static final int  POWER_CONSUMPTION = 400;
    public EnergyEstimator(ModelAccess modelAccess){
           this.modelAccess = modelAccess;
    }

    public int getValueWithRunningAlgorithm() {
        Collection<ServiceCenterServer> servers= modelAccess.getAllServiceCenterServerInstances();
        double totalEnergyConsumed = 0;
        for (ServiceCenterServer server: servers){
            if (server.getIsActive()){
                 
                HyperVServerManagementProxy proxy = new HyperVServerManagementProxy(server.getIpAddress());
                String energyConsumption = proxy.getEnergyConsumptionInfo();
                double d =Integer.parseInt(energyConsumption );
                totalEnergyConsumed+=d;
            }
        }
        return (int)totalEnergyConsumed;  
    }

    public int getValueWithoutAlgorithm() {
       return modelAccess.getAllServiceCenterServerInstances().size()*POWER_CONSUMPTION;
    }
}
