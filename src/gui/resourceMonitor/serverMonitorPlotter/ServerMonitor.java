package gui.resourceMonitor.serverMonitorPlotter;

import gui.resourceMonitor.AbstractMonitor;
import gui.resourceMonitor.resourceMonitorPlotter.ResourceMonitorPlotter;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 16, 2010
 * Time: 1:03:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ServerMonitor extends AbstractMonitor {


    protected List<ResourceMonitorPlotter> coresMonitors;

    protected ResourceMonitorPlotter memoryMonitor;
    protected ResourceMonitorPlotter storageMonitor;

    protected ServiceCenterServer server;
    protected ModelAccess modelAccess;

    protected ServerMonitor(ServiceCenterServer server, ModelAccess access) {
        this.server = server;
        modelAccess = access;
    }

    protected ServerMonitor(ServiceCenterServer server, ModelAccess access, int refreshRate) {
        this.server = server;
        this.refreshRate = refreshRate;
    }

    protected ServerManagementProxyInterface getProxy() {
        return ProxyFactory.createServerManagementProxy();
    }

    public void executeStandaloneWindow() {
        JFrame frame = new JFrame(server.getLocalName() + " Monitor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(monitorPanel, "Center");
        frame.setSize(400, 600);
        frame.setVisible(true);
    }

    protected abstract void refreshData();
}
