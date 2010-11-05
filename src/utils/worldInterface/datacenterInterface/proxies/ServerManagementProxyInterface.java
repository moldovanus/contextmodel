package utils.worldInterface.datacenterInterface.proxies;

import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.dtos.DeployedTaskInfo;
import utils.worldInterface.dtos.ServerDto;
import utils.worldInterface.dtos.ServerInfo;
import utils.worldInterface.dtos.TaskDeployInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 8, 2010
 * Time: 10:50:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServerManagementProxyInterface {


    ServerInfo getServerInfo() throws ServiceCenterAccessException;

    DeployedTaskInfo deployVirtualMachine(TaskDeployInfo deployInfo) throws ServiceCenterAccessException;

    void startVirtualMachine(String vmName) throws ServiceCenterAccessException;

    void stopVirtualMachine(String vmName) throws ServiceCenterAccessException;

    void deleteVirtualMachine(String vmName) throws ServiceCenterAccessException;

    void modifyVirtualMachine(String vmName, int memory, int procPercentage, int cores) throws ServiceCenterAccessException;

    /**
     * @param mac       - mac address of the server to be woken up
     * @param ipAddress - ip of router
     * @param port      - port forwarded to the server to be waken up
     */
    void wakeUpServer(String mac, String ipAddress, int port) throws ServiceCenterAccessException;

    void sendServerToSleep() throws ServiceCenterAccessException;
    String getEnergyConsumptionInfo() throws ServiceCenterAccessException;

}
