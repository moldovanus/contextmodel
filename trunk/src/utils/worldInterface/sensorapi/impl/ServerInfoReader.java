package utils.worldInterface.sensorapi.impl;

import model.impl.util.ModelAccess;
import model.interfaces.resources.CPU;
import model.interfaces.resources.HDD;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import utils.exceptions.ApplicationException;
import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.PhysicalHost;
import utils.worldInterface.dtos.ServerInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: May 25, 2010
 * Time: 10:00:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerInfoReader {

    private ServiceCenterServer server;
    private ModelAccess modelAccess;
    private int refreshInterval = 1000;

    private Timer timer;

    public ServerInfoReader(ServiceCenterServer server, ModelAccess modelAccess, int refreshInterval) {
        this.server = server;
        this.modelAccess = modelAccess;
        this.refreshInterval = refreshInterval;
        timer = new Timer(refreshInterval, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            refreshServerInfo();
                        } catch (ApplicationException ex) {
                            System.out.println(ex.getMessage());
                            ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                };
                thread.start();
            }
        });
    }


    private void refreshServerInfo() throws ApplicationException {
        ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy();
        //server.setProxy(proxy);
        ServerInfo serverInfo;
        try {
            PhysicalHost host = new PhysicalHost();
            host.setId(server.getId());

            serverInfo = proxy.getServerInfo(host);
        } catch (ServiceCenterAccessException e) {
            throw new ApplicationException(e.getMessage(), e.getCause());
        }

        CPU cpu = server.getCpuResources().iterator().next();
        cpu.setCurrentWorkLoad((double) serverInfo.getUsedCpu());
        cpu.setMaximumWorkLoad((double) serverInfo.getTotalCpu());

        MEM serverMemory = server.getMemResources().iterator().next();
        serverMemory.setMaximumWorkLoad((double) serverInfo.getTotalMem());
        serverMemory.setCurrentWorkLoad((double) serverInfo.getUsedMem());

        HDD storage = server.getHddResources().iterator().next();
//        List<StorageDto> storageList = serverInfo.getStorage();
//        StorageDto targetStorage = null;
//        String storagePath = storage.getPhysicalPath();
//        for (StorageDto storageDto : storageList) {
//            if (storageDto.getName().charAt(0) == storagePath.charAt(0)) {
//                targetStorage = storageDto;
//                break;
//            }
//        }
//
//        int storageSize = targetStorage.getSize();
//        storage.setMaxAcceptableValue(storageSize);
        storage.setMaximumWorkLoad((double) serverInfo.getTotalDisk());
        storage.setCurrentWorkLoad((double) serverInfo.getUsedDisk());
    }


}
