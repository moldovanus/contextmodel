package utils.worldInterface.datacenterInterface.proxies.impl;

import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.dtos.*;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: May 29, 2010
 * Time: 10:19:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class StubProxy extends ServerManagementProxy {
    public StubProxy(String hostName) {
        super(hostName);
    }

    public ServerInfo getServerInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DeployedTaskInfo deployVirtualMachine(TaskDeployInfo deployInfo) throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void modifyVirtualMachine(String vmName, int memory, int procPercentage, int cores) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void wakeUpServer(String mac, String ipAddress, int port) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendServerToSleep() throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEnergyConsumptionInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
