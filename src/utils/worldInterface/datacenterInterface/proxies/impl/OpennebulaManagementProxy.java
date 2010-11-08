package utils.worldInterface.datacenterInterface.proxies.impl;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vnet.VirtualNetwork;
import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.datacenterInterface.xmlParsers.NebulaHostInfoXMLParser;
import utils.worldInterface.dtos.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 10:30:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpennebulaManagementProxy extends ServerManagementProxy {

    private final String NEBULA_RCP_ADDRESS = "http://192.168.1.10:2633/RPC2";
    private final String NEBULA_CREDENTIALS = "oneadmin:oneadmin";

    public OpennebulaManagementProxy() {
    }

    public static void main(String[] args) {
        try {
//            ServerDto dto = new OpennebulaManagementProxy().getServerInfo();
//            System.out.println(dto.getTotalCpu());
            VirtualNetworkInfo network = new VirtualNetworkInfo();
            network.setIp("192.168.1.102");
            network.setVncPassword("123456");
            network.setVncPort(5902);
            VirtualDiskInfo diskInfo = new VirtualDiskInfo();
            diskInfo.setFormat("ext3");
            diskInfo.setSize(1024);
            diskInfo.setSource("./home/oneadmin/Desktop/disk.0");
            diskInfo.setType("disk");
            VirtualTaskInfo taskInfo = new VirtualTaskInfo("Test_OCA");
            taskInfo.setNetworkInfo(network);
            taskInfo.addDiskDto(diskInfo);
            taskInfo.setRequestedCPU(200);
            taskInfo.setRequestedMemory(128);
            PhysicalHost host = new PhysicalHost();
            host.setHostname("192.168.1.13");
            host.setIm(PhysicalHost.IM_KVM);
            host.setTm(PhysicalHost.TM_SSH);
            host.setVmm(PhysicalHost.VMM_KVM);

            OpennebulaManagementProxy opennebulaManagementProxy = new OpennebulaManagementProxy();
            PhysicalHost physicalHost = opennebulaManagementProxy.addHost(host);
            taskInfo = opennebulaManagementProxy.deployVirtualMachine(taskInfo, physicalHost);

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            opennebulaManagementProxy.disableHost(physicalHost);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            opennebulaManagementProxy.enableHost(physicalHost);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


            opennebulaManagementProxy.deleteVirtualMachine(taskInfo);

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            opennebulaManagementProxy.removeHost(physicalHost);

        } catch (ServiceCenterAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public ServerInfo getServerInfo(PhysicalHost physicalHost) throws ServiceCenterAccessException {

        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        Host host = new Host(physicalHost.getId(), client);
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
     * @param infoVirtual
     * @return the ID of the newly deployed VM
     * @throws ServiceCenterAccessException
     */
    public VirtualTaskInfo deployVirtualMachine(VirtualTaskInfo infoVirtual, PhysicalHost physicalHost) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        VirtualNetworkInfo virtualNetworkInfo = infoVirtual.getNetworkInfo();
        String vnetTemplate = "NAME = \"Net_" + infoVirtual.getTaskName() + "\"\n" +
                "TYPE = FIXED\n" +
                "BRIDGE = virbr0\n" +
                "LEASES =[IP=" + virtualNetworkInfo.getIp() + "]";
        OneResponse createNetworkResponse = VirtualNetwork.allocate(client, vnetTemplate);
        if (createNetworkResponse.isError()) {
            throw new ServiceCenterAccessException(createNetworkResponse.getErrorMessage());
        } else {
            infoVirtual.getNetworkInfo().setId(Integer.parseInt(createNetworkResponse.getMessage()));
        }

        String vmTemplate = "NAME   = vm-example \n" +
                "CPU    = " + infoVirtual.getRequestedCPU() + "\n" +
                "MEMORY = " + infoVirtual.getRequestedMemory() + "\n" +
                "OS     = [\n" +
                "   boot = hd,\n" +
                "   ROOT = hda\n" +
                "    ]\n";
        List<VirtualDiskInfo> virtualDiskInfos = infoVirtual.getDiskDtos();
        String disksTemplate = "";
        for (VirtualDiskInfo virtualDiskInfo : virtualDiskInfos) {
            disksTemplate +=
                    "DISK   = [\n" +
                            "   type = " + virtualDiskInfo.getType() + ",\n" +
                            "   clone=no,\n" +
                            "   size = " + virtualDiskInfo.getSize() + ",\n" +
                            "   format = " + virtualDiskInfo.getFormat() + ",\n" +
                            "   source= \"" + virtualDiskInfo.getSource() + "\",\n" +
                            "   target   = hda,\n" +
                            "   readonly = \"no\" \n" +
                            "   ]\n";
        }

        vmTemplate += disksTemplate;
        vmTemplate += "\n" +
                "NIC = [ network = \"Net_" + infoVirtual.getTaskName() + "\" ]\n" +
                "GRAPHICS = [ \n" +
                "  TYPE    = \"vnc\", \n" +
                "  LISTEN  = \"0.0.0.0\",\n" +
                "  PORT    = \"" + virtualNetworkInfo.getVncPort() + "\",\n" +
                "  PASSWD  = \"" + virtualNetworkInfo.getVncPassword() + "\"\n" +
                "]";

        OneResponse createVirtualMachineResponse = VirtualMachine.allocate(client, vmTemplate);
        if (createVirtualMachineResponse.isError()) {
            VirtualNetwork.delete(client, infoVirtual.getNetworkInfo().getId());
            throw new ServiceCenterAccessException(createVirtualMachineResponse.getErrorMessage());
        } else {
            infoVirtual.setId(Integer.parseInt(createVirtualMachineResponse.getMessage()));
        }

        VirtualMachine virtualMachine = new VirtualMachine(Integer.parseInt(createVirtualMachineResponse.getMessage()), client);
        OneResponse deployVirtualMachineResponse = virtualMachine.deploy(physicalHost.getId());
        if (deployVirtualMachineResponse.isError()) {
            VirtualMachine vm = new VirtualMachine(infoVirtual.getId(), client);
            OneResponse finalizeResponse = vm.finalizeVM();
            if (finalizeResponse.isError()) {
                throw new ServiceCenterAccessException(finalizeResponse.getErrorMessage());
            } else {
                throw new ServiceCenterAccessException(deployVirtualMachineResponse.getErrorMessage());
            }
        }

        return infoVirtual;

    }

    public void startVirtualMachine(VirtualTaskInfo taskInfo) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopVirtualMachine(VirtualTaskInfo infoVirtual) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        VirtualMachine machine = new VirtualMachine(infoVirtual.getId(), client);
        OneResponse response = machine.stop();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public void deleteVirtualMachine(VirtualTaskInfo infoVirtual) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        VirtualMachine machine = new VirtualMachine(infoVirtual.getId(), client);
        OneResponse response = machine.finalizeVM();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public void migrateVirtualMachine(VirtualTaskInfo taskInfo, PhysicalHost destination) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        VirtualMachine machine = new VirtualMachine(taskInfo.getId(), client);
        OneResponse response = machine.migrate(destination.getId());
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public PhysicalHost addHost(PhysicalHost host) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        OneResponse response = Host.allocate(client, host.getHostname(), host.getIm(), host.getVmm(), host.getTm());
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        } else {
            host.setId(Integer.parseInt(response.getMessage()));
        }
        return host;
    }

    public void removeHost(PhysicalHost physicalHost) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        Host host = new Host(physicalHost.getId(), client);
        OneResponse response = host.delete();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public void enableHost(PhysicalHost physicalHost) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        Host host = new Host(physicalHost.getId(), client);
        OneResponse response = host.enable();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public void disableHost(PhysicalHost physicalHost) throws ServiceCenterAccessException {
        Client client = null;
        try {
            client = new Client(NEBULA_CREDENTIALS, NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }
        Host host = new Host(physicalHost.getId(), client);
        OneResponse response = host.disable();
        if (response.isError()) {
            throw new ServiceCenterAccessException(response.getErrorMessage());
        }
    }

    public void wakeUpServer(PhysicalHost host) throws ServiceCenterAccessException {
        throw new UnsupportedOperationException("Not implemented yet");
//        System.err.println("Wake up disabled from Hyper-V Management Proxy");
//        try {
//            URL url = new URL("http://" + GlobalVars.GLOBAL_LOOP_CONTROLLER_IP + "/Service1.asmx/WakeUpServer?"
//                    + "mac=" + host.getMac() + "&ipAddress=" + GlobalVars.BROADCAST_IP_ADDRESS + "&port=" + GlobalVars.WAKE_UP_PORT);
//            URLConnection connection = url.openConnection();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
//            waitUntilTargetIsAlive(getHostName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void waitUntilTargetIsAlive(String ip) {
        String pingCmd = "ping " + ip;
        boolean ok = false;
        while (!ok) {
            try {
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(pingCmd);

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);

                    if (!inputLine.contains("unreachable") && !inputLine.equals("") &&
                            inputLine.contains("Reply")) {
                        ok = true;
                        break;
                    }

                }
                in.close();

            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void sendServerToSleep(PhysicalHost host) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEnergyConsumptionInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
