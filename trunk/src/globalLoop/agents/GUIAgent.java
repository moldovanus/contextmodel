package globalLoop.agents;


import globalLoop.agents.behaviors.ReceiveChangesGUIBehaviour;
import gui.ActionsOutputFrame;
import gui.newgui.MainWindow;
import gui.resourceMonitor.IMonitor;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.logger.LoggerGUI;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observer;

public class GUIAgent extends Agent {


    private final String EXIT_TOOLTIP = "Shuts down the system gracefully.";

    private final String LOG_TOOLTIP = "<html>Opens a window where are logged:" +
            "<br>" +
            "<ul>" +
            "<li>The value of each sensor</li>" +
            "<li>The broken policies</li>" +
            "<li>The context-repairing actions policies</li>" +
            "</ul>" +
            "</br>" +
            "</html>";

    private final String ENVIRONMENT_CONTEXT_MONITOR_TOOLTIP = "<html>Opens a window which contains:" +
            "<br> " +
            "<ul>" +
            "<li>The value of each of the environment sensors</li>" +
            "<li>A list of all the broken policies at a given time</li>" +
            "<li>The actions the system takes in order to bring itself in an accepted state</li>" +
            "</br>" +
            "<br>Different from the Log because it shows instand data, it does not list previous values.</br>" +
            "</html>";

    private final String DATACENTER_CONFIGURATION_TOOLTIP = "<html>Opens a window used for configuring the system by:" +
            "<br> " +
            "<ul>" +
            "<li>Defining servers, their associated resources and optimum load indicators</li>" +
            "<li>Defining tasks and their resources requirements used as datacenter workload</li>" +
            "</br>" +
            "</html>";

    private final String DATACENTER_PENDING_TASKS_QUEUE_TOOLTIP = "<html>Opens a window which displays information about tasks that are waiting to be deployed:" +
            "<br> " +
            "<ul>" +
            "<li>A list of waiting tasks names</li>" +
            "<li>The requested resources ranges for each waiting task</li>" +
            "</br>" +
            "</html>";

    private final String DATACENTER_SERVER_MONITORS_TOOLTIP = "<html>Opens a window which displays a server monitor for each server in the datacenter." +
            "<br>Each server monitor displays:</br>" +
            "<br> " +
            "<ul>" +
            "<li>A task monitor similar to Pending tasks Queue monitor for tasks that are running on the server</li>" +
            "<li>Server total resources usage</li>" +
            "<li>The server resources used by each of the running tasks, by the Operating System and the unused amount</li>" +
            "</br>" +
            "</html>";


    private final String X3D_REPRESENTATION_TOOLTIP = "<html>Opens a window which displays a 3D context representation for a simulated datacenter scenario.</html>";


    private ActionsOutputFrame enviromentMonitor;
    private JMenuBar menuBar;
    private LoggerGUI enviromentLogger;
    private LoggerGUI datacenterLogger;

    private AgentController x3DController;
    private AgentContainer container;
    private ModelAccess modelAccess;
    private java.util.List<IMonitor> serverMonitors;
    private IMonitor tasksQueueMonitor;

    private MainWindow mainWindow;
    private JFrame frame;

    private java.util.List<Observer> observers;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Object[] objects) {
        for (Observer observer : observers) {
            observer.update(null, objects);
        }
    }

    @Override
    protected void setup() {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InstantiationException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        System.out.println("[GUIAgent] Hello!");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            modelAccess = (ModelAccess) args[0];
        } else {
            System.out.println("[GUIAgent] No Model Access provided.");
            this.doDelete();
            return;
        }
        observers = new ArrayList<Observer>();
//        serverMonitors = new ArrayList<IMonitor>();
//        tasksQueueMonitor = new TasksQueueMonitor(modelAccess);
//        container = getContainerController();
//
//        enviromentMonitor = new ActionsOutputFrame("Enviroment");
//        enviromentLogger = new LoggerGUI("EnviromentManagementLog");
//        datacenterLogger = new LoggerGUI("DatacenterManagementLog");
//
//        enviromentLogger.setLogPath("./Logs/");
//        datacenterLogger.setLogPath("./Logs/");
//
//        frame = new JFrame("System Control Unit");
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        menuBar = new JMenuBar();
//        frame.setSize(500, 400);
//
//        frame.setLayout(new BorderLayout());
//
//
//        ImageIcon icon = new ImageIcon("./src/images/main_image.png");
//
//        JLabel label = new JLabel("", icon, JLabel.CENTER);
//        frame.add(label, BorderLayout.CENTER);
//
//        frame.setJMenuBar(menuBar);
//
//        JMenu fileMenu = new JMenu("File");
//        JMenu windowMenu = new JMenu("Window");
//
//        JMenu enviromentalControlMenu = new JMenu("Enviromental Information");
//        JMenu datacenterControlMenu = new JMenu("Datacenter Information");
//
        AbstractAction exitAction = new AbstractAction("Exit") {

            public void actionPerformed(ActionEvent e) {
                Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
                for (ServiceCenterServer server : servers) {
                    ServerManagementProxyInterface serverManagementProxyInterface =
                            ProxyFactory.createServerManagementProxy(server.getIpAddress());
                    Collection<ApplicationActivity> runningTasks = server.getRunningActivities();
                    for (ApplicationActivity task : runningTasks) {
                        serverManagementProxyInterface.stopVirtualMachine(task.getLocalName());
                        serverManagementProxyInterface.deleteVirtualMachine(task.getLocalName());
                    }
                }
                shutdownPlatform();
            }
        };
//
//        AbstractAction showSelfHealingLogMenuItem = new AbstractAction("Log") {
//
//            public void actionPerformed(ActionEvent e) {
//                enviromentLogger.setVisible(true);
//            }
//        };
//
//
//        AbstractAction showDatacenterLogMenuItem = new AbstractAction("Log") {
//
//            public void actionPerformed(ActionEvent e) {
//                datacenterLogger.setVisible(true);
//            }
//        };
//
//        AbstractAction showEnviromentMonitorWindowMenuItem = new AbstractAction("Context Monitor") {
//
//            public void actionPerformed(ActionEvent e) {
//                showEnviromentMonitor();
//            }
//        };
//
//        AbstractAction showServerMonitorsMenuItem = new AbstractAction("Server Monitors") {
//
//            public void actionPerformed(ActionEvent e) {
//
//                for (IMonitor monitor : serverMonitors) {
//                    monitor.destroyStandaloneWindow();
//                }
//
//                serverMonitors.clear();
//
//                Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
//                for (ServiceCenterServer server : servers) {
//                    IMonitor serverMonitor = new FullServerMonitor(server, ProxyFactory.createServerManagementProxy(server.getIpAddress()));
//                    serverMonitor.executeStandaloneWindow();
//                    serverMonitors.add(serverMonitor);
//                }
//            }
//        };
//
//        AbstractAction showTasksQueueMenuItem = new AbstractAction("Pending Tasks Queue") {
//
//            public void actionPerformed(ActionEvent e) {
//
//                tasksQueueMonitor.destroyStandaloneWindow();
//                tasksQueueMonitor.executeStandaloneWindow();
//            }
//        };
//
//        AbstractAction showDatacenterConfigurationWindow = new AbstractAction("Datacenter Configuration Window") {
//
//            public void actionPerformed(ActionEvent e) {
//                new ConfigurationGUI(modelAccess).setVisible(true);
//            }
//        };
//
//        AbstractAction showDatacenterSimulationWindowMenuItem = new AbstractAction("X3D  Representation") {
//
//            public void actionPerformed(ActionEvent e) {
//
////                try {
////                    x3DController = container.createNewAgent(GlobalVars.X3DAGENT_NAME, X3DAgent.class.getName(), null);
////                    x3DController.start();
////                } catch (StaleProxyException e1) {
////                    e1.printStackTrace();
////                }
//
//            }
//        };
//
//        showSelfHealingLogMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, LOG_TOOLTIP);
//        showDatacenterLogMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, LOG_TOOLTIP);
//        showEnviromentMonitorWindowMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, ENVIRONMENT_CONTEXT_MONITOR_TOOLTIP);
//        showTasksQueueMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, DATACENTER_PENDING_TASKS_QUEUE_TOOLTIP);
//        showDatacenterConfigurationWindow.putValue(AbstractAction.SHORT_DESCRIPTION, DATACENTER_CONFIGURATION_TOOLTIP);
//        showDatacenterSimulationWindowMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, X3D_REPRESENTATION_TOOLTIP);
//        showServerMonitorsMenuItem.putValue(AbstractAction.SHORT_DESCRIPTION, DATACENTER_SERVER_MONITORS_TOOLTIP);
//        exitAction.putValue(AbstractAction.SHORT_DESCRIPTION, EXIT_TOOLTIP);
//
//        fileMenu.add(exitAction);
//
//        windowMenu.add(enviromentalControlMenu);
//        windowMenu.add(datacenterControlMenu);
//
//        enviromentalControlMenu.add(showEnviromentMonitorWindowMenuItem);
//        enviromentalControlMenu.add(showSelfHealingLogMenuItem);
//
//        datacenterControlMenu.add(showDatacenterConfigurationWindow);
//        datacenterControlMenu.add(showDatacenterLogMenuItem);
//        datacenterControlMenu.add(showTasksQueueMenuItem);
//        datacenterControlMenu.add(showServerMonitorsMenuItem);
//        datacenterControlMenu.add(showDatacenterSimulationWindowMenuItem);
//
//        menuBar.add(fileMenu);
//        menuBar.add(windowMenu);
//
//        frame.setVisible(true);

        mainWindow = new MainWindow(this, modelAccess);
        mainWindow.addFileMenuAction(exitAction);
        mainWindow.setVisible(true);

        this.addBehaviour(new ReceiveChangesGUIBehaviour(this));

    }

    public void shutdownPlatform() {
        System.out.println("[GUIAgent] Shutting down platform ... ");
        try {
            this.getContainerController().getPlatformController().kill();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showEnviromentMonitor() {

        enviromentMonitor.setVisible(true);
    }

    public void setEnviromentMonitorActionsList(ArrayList<String[]> list) {
        enviromentMonitor.setActionsList(list);
    }

    public void setEnviromentMonitorBrokenStatesList(ArrayList<String[]> list) {
        enviromentMonitor.setBrokenStatesList(list);
    }

    public void setEnviromentMonitorBrokenPoliciesList(ArrayList<String> list) {
        enviromentMonitor.setBrokenPoliciesList(list);
    }

    public void logEnviromentManagementInformation(Color color, String header, ArrayList message) {
        enviromentLogger.log(color, header, message);
    }

    public void logDatacenterManagementInformation(Color color, String header, ArrayList message) {
        datacenterLogger.log(color, header, message);
    }


}
