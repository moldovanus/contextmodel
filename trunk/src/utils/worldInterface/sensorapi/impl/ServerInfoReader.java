package utils.worldInterface.sensorapi.impl;

import model.impl.util.ModelAccess;
import model.interfaces.resources.*;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.ServerDto;
import utils.worldInterface.dtos.StorageDto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

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
                        refreshServerInfo();
                    }
                };
                thread.start();
            }
        });
    }


    private void refreshServerInfo() {
        ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
        //server.setProxy(proxy);
        ServerDto serverInfo = proxy.getServerInfo();

        CPU cpu = server.getCpuResources().iterator().next();
        int coreCount = serverInfo.getCoreCount();
        List<Core> cores = cpu.getAssociatedCores();
        /*for (int i = 0; i < coreCount; i++) {
            cores.add(modelAccess.createCore(server.getLocalName() + "_Core_" + i));
        }*/
        //cpu.setAssociatedCore(cores);

        int totalCPU = serverInfo.getTotalCPU();
        Object[] freeCPUValues = serverInfo.getFreeCPU().toArray();
        int index = 0;
        int freeCPU = totalCPU - (Integer) freeCPUValues[0];
        for (Object item : cores) {
            Core core = (Core) item;
            // core.setMaxAcceptableValue(totalCPU);
            //core.setMinAcceptableValue(1);
            core.setMaximumWorkLoad((double) totalCPU);
            core.setCurrentWorkLoad((double) freeCPU);
        }

        MEM serverMemory = server.getMemResources().iterator().next();
        int totalMemory = serverInfo.getTotalMemory();
        //serverMemory.setMaxAcceptableValue(totalMemory);
        serverMemory.setMaximumWorkLoad((double) totalMemory);
        serverMemory.setCurrentWorkLoad((double) totalMemory - serverInfo.getFreeMemory());

        HDD storage = server.getHddResources().iterator().next();
        List<StorageDto> storageList = serverInfo.getStorage();
        StorageDto targetStorage = null;
        String storagePath = storage.getPhysicalPath();
        for (StorageDto storageDto : storageList) {
            if (storageDto.getName().charAt(0) == storagePath.charAt(0)) {
                targetStorage = storageDto;
                break;
            }
        }

        int storageSize = targetStorage.getSize();
        // storage.setMaxAcceptableValue(storageSize);
        storage.setMaximumWorkLoad((double) storageSize);
        storage.setCurrentWorkLoad((double) storageSize - targetStorage.getFreeSpace());
    }


}
