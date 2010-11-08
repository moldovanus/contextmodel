package utils.worldInterface.datacenterInterface.proxies.impl;

import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.dtos.PhysicalHost;
import utils.worldInterface.dtos.ServerInfo;
import utils.worldInterface.dtos.VirtualTaskInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: May 29, 2010
 * Time: 10:19:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class StubProxy extends ServerManagementProxy {


    public ServerInfo getServerInfo(PhysicalHost physicalHost) throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public VirtualTaskInfo deployVirtualMachine(VirtualTaskInfo infoVirtual, PhysicalHost physicalHost) throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void migrateVirtualMachine(VirtualTaskInfo info, PhysicalHost destination) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public PhysicalHost addHost(PhysicalHost host) throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeHost(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void enableHost(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void disableHost(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteVirtualMachine(VirtualTaskInfo info) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void wakeUpServer(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendServerToSleep(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEnergyConsumptionInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
