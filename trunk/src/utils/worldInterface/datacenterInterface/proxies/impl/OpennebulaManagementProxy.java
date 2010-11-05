package utils.worldInterface.datacenterInterface.proxies.impl;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vnet.VirtualNetwork;
import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.xmlParsers.NebulaHostInfoXMLParser;
import utils.worldInterface.dtos.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 10:30:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpennebulaManagementProxy extends ServerManagementProxy {

    private int hostId = 3;
    private final String NEBULA_RCP_ADDRESS = "http://192.168.1.10:2633/RPC2";
    private final String NEBULA_CREDENTIALS = "oneadmin:oneadmin";

    public OpennebulaManagementProxy(String hostName, int hostId) {
        super(hostName);
        this.hostId = hostId;
    }

//    public static void main(String[] args) {
//        try {
////            ServerDto dto = new OpennebulaManagementProxy().getServerInfo();
////            System.out.println(dto.getTotalCpu());
//            NetworkInfo network = new NetworkInfo();
//            network.setIp("192.168.1.102");
//            network.setVncPassword("123456");
//            network.setVncPort(5902);
//            DiskInfo diskInfo = new DiskInfo();
//            diskInfo.setFormat("ext3");
//            diskInfo.setSize(1024);
//            diskInfo.setSource("./home/utcn/Desktop/hard_last_try");
//            diskInfo.setType("disk");
//            TaskDeployInfo taskInfo = new TaskDeployInfo("Test_OCA");
//            taskInfo.setNetworkDto(network);
//            taskInfo.addDiskDto(diskInfo);
//            taskInfo.setRequestedCPU(200);
//            taskInfo.setRequestedMemory(128);
//
//            new OpennebulaManagementProxy().deployVirtualMachine(taskInfo);
//
//        } catch (ServiceCenterAccessException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

    public ServerInfo getServerInfo() throws ServiceCenterAccessException {

        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        Host host = new Host(hostId, client);
        OneResponse response = host.info();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
        ServerInfo dto = null;
        try {
            dto = NebulaHostInfoXMLParser.parseServerInfo(response.getMessage());
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }

        return dto;
    }

    /**
     * @param deployInfo
     * @return the ID of the newly deployed VM
     * @throws ServiceCenterAccessException
     */
    public DeployedTaskInfo deployVirtualMachine(TaskDeployInfo deployInfo) throws ServiceCenterAccessException {
        Client client = null;
        DeployedTaskInfo deployedTaskInfo = new DeployedTaskInfo();
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        NetworkInfo networkInfo = deployInfo.getNetworkDto();
        String vnetTemplate = "NAME = \"Net_" + deployInfo.getTaskName() + "\"\n" +
                "TYPE = FIXED\n" +
                "BRIDGE = virbr0\n" +
                "LEASES =[IP=" + networkInfo.getIp() + "]";
        OneResponse createNetworkResponse = VirtualNetwork.allocate(client, vnetTemplate);
        if (createNetworkResponse.isError()) {
            throw new ServiceCenterAccessException(createNetworkResponse.getErrorMessage());
        } else {
            deployedTaskInfo.setNetworkID(Integer.parseInt(createNetworkResponse.getMessage()));
        }

        String vmTemplate = "NAME   = vm-example \n" +
                "CPU    = " + deployInfo.getRequestedCPU() + "\n" +
                "MEMORY = " + deployInfo.getRequestedMemory() + "\n" +
                "OS     = [\n" +
                "   boot = hd,\n" +
                "   ROOT = hda\n" +
                "    ]\n";
        List<DiskInfo> diskInfos = deployInfo.getDiskDtos();
        String disksTemplate = "";
        for (DiskInfo diskInfo : diskInfos) {
            disksTemplate +=
                    "DISK   = [\n" +
                            "   type = " + diskInfo.getType() + ",\n" +
                            "   clone=no,\n" +
                            "   size = " + diskInfo.getSize() + ",\n" +
                            "   format = " + diskInfo.getFormat() + ",\n" +
                            "   source= \"" + diskInfo.getSource() + "\",\n" +
                            "   target   = hda,\n" +
                            "   readonly = \"no\" \n" +
                            "   ]\n";
        }

        vmTemplate += disksTemplate;
        vmTemplate += "\n" +
                "NIC = [ network = \"Net_" + deployInfo.getTaskName() + "\" ]\n" +
                "GRAPHICS = [ \n" +
                "  TYPE    = \"vnc\", \n" +
                "  LISTEN  = \"0.0.0.0\",\n" +
                "  PORT    = \"" + networkInfo.getVncPort() + "\",\n" +
                "  PASSWD  = \"" + networkInfo.getVncPassword() + "\"\n" +
                "]";

        OneResponse createVirtualMachineResponse = VirtualMachine.allocate(client, vmTemplate);
        if (createVirtualMachineResponse.isError()) {
            VirtualNetwork.delete(client, deployedTaskInfo.getNetworkID());
            throw new ServiceCenterAccessException(createVirtualMachineResponse.getErrorMessage());
        } else {
            deployedTaskInfo.setTaskID(Integer.parseInt(createVirtualMachineResponse.getMessage()));
        }

        VirtualMachine virtualMachine = new VirtualMachine(Integer.parseInt(createVirtualMachineResponse.getMessage()), client);
        OneResponse deployVirtualMachineResponse = virtualMachine.deploy(hostId);
        if (deployVirtualMachineResponse.isError()) {
            VirtualMachine vm = new VirtualMachine(deployedTaskInfo.getTaskID(), client);
            OneResponse finalizeResponse = vm.finalizeVM();
            if (finalizeResponse.isError()) {
                throw new ServiceCenterAccessException(finalizeResponse.getErrorMessage());
            } else {
                throw new ServiceCenterAccessException(deployVirtualMachineResponse.getErrorMessage());
            }
        }

        return deployedTaskInfo;

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
