package gui.newgui;

import gui.resourceMonitor.AbstractMonitor;
import gui.resourceMonitor.serverMonitorPlotter.impl.ServerMonitorPiePlotter;
import gui.resourceMonitor.serverMonitorPlotter.impl.ServerMonitorXYPlotter;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import utils.misc.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 10, 2010
 * Time: 10:31:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServersMonitorController {
    private Map<String, Pair<AbstractMonitor, AbstractMonitor>> serverMonitors;
    private ModelAccess modelAccess;


    public ServersMonitorController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
        serverMonitors = new HashMap<String, Pair<AbstractMonitor, AbstractMonitor>>();
    }

    private void refreshServerInfo() {
        Set<String> keys = serverMonitors.keySet();
        for (String key : keys) {
            Pair<AbstractMonitor, AbstractMonitor> pair = serverMonitors.remove(key);
            pair.getFirst().stopTimer();
            pair.getSecond().stopTimer();
        }

        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        for (ServiceCenterServer server : servers) {
            serverMonitors.put(
                    server.getLocalName(),
                    new Pair<AbstractMonitor, AbstractMonitor>(
                            new ServerMonitorXYPlotter(server, modelAccess),
                            new ServerMonitorPiePlotter(server, modelAccess)));
        }
    }


    public Map<String, Pair<AbstractMonitor, AbstractMonitor>> getServerMonitors() {
        refreshServerInfo();
        return serverMonitors;
    }
}
