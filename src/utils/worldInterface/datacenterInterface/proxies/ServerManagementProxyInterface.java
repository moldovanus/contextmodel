package utils.worldInterface.datacenterInterface.proxies;

import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.dtos.PhysicalHost;
import utils.worldInterface.dtos.ServerInfo;
import utils.worldInterface.dtos.VirtualTaskInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 8, 2010
 * Time: 10:50:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServerManagementProxyInterface {


    ServerInfo getServerInfo(PhysicalHost physicalHost) throws ServiceCenterAccessException;

    VirtualTaskInfo deployVirtualMachine(VirtualTaskInfo infoVirtual, PhysicalHost physicalHost) throws ServiceCenterAccessException;

    void startVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException;

    void stopVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException;

    void migrateVirtualMachine(VirtualTaskInfo info, PhysicalHost destination) throws ServiceCenterAccessException;

    PhysicalHost addHost(PhysicalHost host) throws ServiceCenterAccessException;

    void removeHost(PhysicalHost host) throws ServiceCenterAccessException;

    void enableHost(PhysicalHost host) throws ServiceCenterAccessException;

    void disableHost(PhysicalHost host) throws ServiceCenterAccessException;

    void deleteVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException;

//    void modifyVirtualMachine(String vmName, int memory, int procPercentage, int cores) throws ServiceCenterAccessException;
//

    void wakeUpServer(PhysicalHost host) throws ServiceCenterAccessException;

    void sendServerToSleep(PhysicalHost host) throws ServiceCenterAccessException;

    String getEnergyConsumptionInfo() throws ServiceCenterAccessException;

}
