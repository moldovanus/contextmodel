package gui.resourceMonitor.serverMonitorPlotter.impl;

import gui.resourceMonitor.resourceMonitorPlotter.ResourceMonitorPlotter;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;
import gui.resourceMonitor.serverMonitorPlotter.ServerMonitor;
import model.impl.util.ModelAccess;
import model.interfaces.resources.Core;
import model.interfaces.resources.MEM;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.StubProxy;
import utils.worldInterface.dtos.ServerDto;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 15, 2010
 * Time: 8:44:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMonitorXYPlotter extends ServerMonitor {

    public ServerMonitorXYPlotter(ServiceCenterServer server, ModelAccess modelAccess) {
        super(server, modelAccess);
        setup();
    }

    public ServerMonitorXYPlotter(ServiceCenterServer server, ModelAccess modelAccess, int refreshRate) {
        super(server, modelAccess, refreshRate);
        setup();
    }

    protected void setup() {

        monitorPanel = new JPanel();
        monitorPanel.setLayout(new GridLayout(1, 2));

        Collection cores = server.getCpuResources().iterator().next().getAssociatedCores();
        int coresCount = cores.size();
        if (coresCount % 2 != 0) {
            coresCount++;
        }

        coresMonitors = new ArrayList<ResourceMonitorPlotter>();

//        monitorPanel.setLayout(new GridLayout(coresCount / 2 + 1, 2));

//        for (Object o : cores) {
//            Core core = (Core) o;
        Core core = (Core) cores.iterator().next();
        ResourceMonitorPlotter plotter = new ResourceMonitorXYChartPlotter("CPU", "Time", "Usage", 0, core.getMaximumWorkLoad().intValue());
        plotter.setSnapshotIncrement(refreshRate / 1000);
        JPanel graphPanel = plotter.getGraphPanel();
        graphPanel.setSize(250, 150);
        monitorPanel.add(graphPanel);
        coresMonitors.add(plotter);
//        }

        MEM memory = server.getMemResources().iterator().next();
        memoryMonitor = new ResourceMonitorXYChartPlotter("Memory", "Time", "Usage", 0, memory.getMaximumWorkLoad().intValue());
        memoryMonitor.setSnapshotIncrement(refreshRate / 1000);
        monitorPanel.add(memoryMonitor.getGraphPanel());

//        HDD storage = server.getHddResources().iterator().next();
//        storageMonitor = new ResourceMonitorXYChartPlotter("Storage","Time","Usage", 0, storage.getMaximumWorkLoad().intValue());
//        storageMonitor.setSnapshotIncrement(refreshRate / 1000);
//        monitorPanel.add(storageMonitor.getGraphPanel());

    }

    protected void refreshData() {
        ServerManagementProxyInterface proxyInterface = getProxy();
        if (!server.getIsActive()) {
            return;
        }

        if ((proxyInterface instanceof StubProxy)) {
            ServiceCenterServer serverData = modelAccess.getServiceCenterServer(server.getName());
            List<Core> cores = serverData.getCpuResources().iterator().next().getAssociatedCores();

            Core core = cores.get(0);
            coresMonitors.get(0).setCurrentValue(
                    core.getMaximumWorkLoad().intValue()
                            - (core.getMaximumWorkLoad().intValue() - core.getCurrentWorkLoad().intValue()));

            MEM memory = serverData.getMemResources().iterator().next();
            memoryMonitor.setCurrentValue(
                    memory.getMaximumWorkLoad().intValue()
                            - (memory.getMaximumWorkLoad().intValue() - memory.getCurrentWorkLoad().intValue()));
        } else {
            ServerDto serverDto = proxyInterface.getServerInfo();
            List<Integer> freeCPU = serverDto.getFreeCPU();
            int totalCPU = serverDto.getTotalCPU();
            int coresCount = coresMonitors.size();
            for (int i = 0; i < coresCount; i++) {
                coresMonitors.get(i).setCurrentValue(totalCPU - freeCPU.get(i));
            }
            memoryMonitor.setCurrentValue(serverDto.getTotalMemory() - serverDto.getFreeMemory());

        }

//        HDD storage = server.getHddResources().iterator().next();
//        List<StorageDto> storageList = serverDto.getStorage();
//        StorageDto targetStorage = null;
//        String storagePath = storage.getPhysicalPath();
//        for (StorageDto storageDto : storageList) {
//            if (storageDto.getName().charAt(0) == storagePath.charAt(0)) {
//                targetStorage = storageDto;
//                break;
//            }
//        }

//        storageMonitor.setCurrentValue(targetStorage.getSize() - targetStorage.getFreeSpace());

    }


}
