package gui.newgui;

import globalLoop.agents.GUIAgent;
import globalLoop.utils.GlobalVars;
import gui.energyConsumption.EnergyConsumption;
import gui.energyConsumption.EnergyConsumptionFactory;
import gui.resourceMonitor.AbstractMonitor;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import model.impl.util.ModelAccess;
import sun.management.ManagementFactory;
import utils.fileIO.ConfigurationFileIO;
import utils.misc.Pair;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 9:40:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExpertConfigurationGUIController implements Observer {
    private GUIAgent agent;
    private ServerConfigurationController serverConfigurationController;
    private TaskConfigurationController taskConfigurationController;
    private ModelAccess modelAccess;

    private ExpertConfigurationGUI expertGui;
    private WorkloadSchedulerController workloadSchedulerController;
    private ServersMonitorController serversMonitorController;

    private final String SAVE_TASKS_CONFIGURATION_TOOLTIP = "Saves the tasks configuration information from the tasks table in a user specified file";
    private final String SAVE_SERVES_CONFIGURATION_TOOLTIP = "Saves the servers configuration information from the servers table in a user specified file";
    private final String LOAD_TASKS_CONFIGURATION_TOOLTIP = "<html>Loads a previousely saved tasks configuration.<br><b>WARNING:</b>Replaces the existing configuration</br></html>";
    private final String LOAD_SERVERS_CONFIGURATION_TOOLTIP = "<html>Loads a previousely saved servers configuration.<br><b>WARNING:</b>Replaces the existing configuration</br></html>";

    private AbstractAction saveTasksConfiguration;
    private AbstractAction saveServersConfiguration;
    private AbstractAction loadTasksConfiguration;
    private AbstractAction loadServersConfiguration;

    private FileFilter generalConfigFilter;
    private FileFilter tasksConfigFilter;
    private FileFilter serversConfigFilter;


    private JFileChooser fileChooser;

    private int decisionTime = 0;
    private int decisionTimeRefreshRateInMillis = 1000;
    private List<Timer> timers;
    private ActionListener scheduleTimerActionListener;
    private int scheduleCount = 0;
    private Timer scheduleTimer;


    private int energyEstimateWithoutAlg = 0;
    private int energyEstimateWithAlg = 0;

    {
        timers = new ArrayList<Timer>(2);
    }

    public void resetScheduleCount() {
        scheduleCount = 0;
        expertGui.setTimerProgress(scheduleCount);
    }

    public ExpertConfigurationGUIController(GUIAgent a, ModelAccess ma, ExpertConfigurationGUI gui,
                                            ServerConfigurationController sController,
                                            TaskConfigurationController tController,
                                            WorkloadSchedulerController wController,
                                            Timer timer) {
        this.agent = a;
        scheduleTimer = timer;
        this.serverConfigurationController = sController;
        this.taskConfigurationController = tController;

        a.addObserver(this);

        this.modelAccess = ma;
        this.expertGui = gui;
        workloadSchedulerController = wController;
        serversMonitorController = new ServersMonitorController(modelAccess);

        expertGui.addServerConfigurationPanel(sController.getConfigurationPanel());
        expertGui.addWorkloadConfigurationPanel(tController.getConfigurationPanel());

        expertGui.addAvailableTasksPanel(workloadSchedulerController.getAvailableTasksTree());
        createDecisionTimeChart();
        createMemoryUsageChart();
        createEnergyConsumptionCharts();
        fileChooser = new JFileChooser();

        generalConfigFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".general_config");
            }

            @Override
            public String getDescription() {
                return "Complete Configuration Files (.general_config)";
            }
        };

        tasksConfigFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".tasks_config");
            }

            @Override
            public String getDescription() {
                return "Workload Configuration File (.tasks_config)";
            }
        };

        serversConfigFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".servers_config");
            }

            @Override
            public String getDescription() {
                return "Workload Configuration File (.servers_config)";
            }
        };

        File file = new File("./testConfigurations");
        if (file.exists()) {
            fileChooser.setCurrentDirectory(file);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }
        refreshServersMonitorsPanel();

        scheduleTimerActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                expertGui.setTimerProgress(scheduleCount);

                List<Pair<String, Integer>> scheduledTasks = workloadSchedulerController.getScheduledTasksFor(scheduleCount);
//                List<TaskDto> availableTasks = new ArrayList<TaskDto>();
////                Collection<ApplicationActivity> activities = modelAccess.getAllApplicationActivityInstances();
                String taskNames = "";
                for (Pair<String, Integer> entry : scheduledTasks) {
                    taskNames += entry.getFirst() + ", ";
//                    ApplicationActivity activity = modelAccess.getApplicationActivity(name);
//                    //TODO; remove if other solution for templates implemented
//                    if (activity.getLocalName().toLowerCase().contains("template")) {
//
//                        TaskDto taskDto = new TaskDto();
//                        taskDto.setTaskName(activity.getLocalName());
//                        taskDto.setRequestedCores((int) activity.getNumberOfCoresRequiredValue());
//                        taskDto.setRequestedCPUMax((int) activity.getCpuRequiredMaxValue());
//                        taskDto.setRequestedCPUMin((int) activity.getCpuRequiredMinValue());
//                        taskDto.setRequestedMemoryMax((int) activity.getMemRequiredMaxValue());
//                        taskDto.setRequestedMemoryMin((int) activity.getMemRequiredMinValue());
//                        taskDto.setRequestedStorageMax((int) activity.getHddRequiredMaxValue());
//                        taskDto.setRequestedStorageMin((int) activity.getHddRequiredMinValue());
//
//                        availableTasks.add(taskDto);
//                    }
                }
                if (expertGui.generatePopupMessages()) {
//                            WorkloadTreeDisplay workloadTreeDisplay = new WorkloadTreeDisplay(availableTasks);
//                            final JDialog dialog = new JDialog(expertGui);
//
//                            dialog.getContentPane().setLayout(new BorderLayout());
//                            dialog.add(workloadTreeDisplay.getTree(), "Center");
////                            JButton button = new JButton("OK");
////                            button.addActionListener(new ActionListener() {
////                                public void actionPerformed(ActionEvent e) {
////                                    dialog.dispose();
////                                }
////                            });
////                            dialog.add(button, "South");
//                            dialog.setSize(200,300);
//                            dialo
//
//
// g.setVisible(true);
//                            dialog.setModal(true);

                    if (scheduledTasks.size() > 0) {
                        JOptionPane.showMessageDialog(null, "Tasks: " + taskNames + " added");
                    }
                }
//                jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
//                try {
//                    msg.setContentObject(new Object[]{"Create clones", scheduledTasks});
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
//                agent.send(msg);

                scheduleCount++;
            }
        };

        scheduleTimer.addActionListener(scheduleTimerActionListener);

        expertGui.addStartTimerButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleTimer.setDelay(1000);
                scheduleTimer.start();
            }
        });

        expertGui.addPauseTimerButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleTimer.stop();
            }
        });

        expertGui.addStopTimerButtonListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleCount = 0;
                expertGui.setTimerProgress(scheduleCount);
                scheduleTimer.stop();
            }
        });

        expertGui.addScheduleTableModel(workloadSchedulerController.getScheduleTableModel());

        expertGui.addDuplicateRowActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workloadSchedulerController.duplicateRow(expertGui.getWorkloadScheduleTable().getSelectedRow());
            }
        });

        expertGui.addRemoveSelectedActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workloadSchedulerController.deleteSelected(expertGui.getWorkloadScheduleTable().getSelectedRow());
            }
        });

        expertGui.addScheduleButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workloadSchedulerController.scheduleSelectedTasks(expertGui.getCreationTime(), expertGui.getDestroyTime());
            }
        });

        AbstractAction saveConfiguration = new AbstractAction("Save complete configuration") {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(generalConfigFilter);
                Object[] data = new Object[3];
                data[0] = serverConfigurationController.getTableData();
                data[1] = taskConfigurationController.getTableData();
                data[2] = workloadSchedulerController.getSchedule();

                int userOption = fileChooser.showSaveDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (file.getPath().endsWith(".general_config")) {
                            ConfigurationFileIO.saveGeneralConfig(data, file);
                        } else {
                            ConfigurationFileIO.saveGeneralConfig(data, new File(file.getPath() + ".general_config"));
                        }
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "File save error", "Save error", JOptionPane.WARNING_MESSAGE);
                        e1.printStackTrace();
                    }
                }

            }
        };

        AbstractAction loadConfiguration = new AbstractAction("Load complete configuration") {
            public void actionPerformed(ActionEvent e) {
                Object[] data;
                fileChooser.setFileFilter(generalConfigFilter);
                int userOption = fileChooser.showOpenDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (!file.getName().endsWith("general_config")) {
                            throw new Exception();
                        }
                        data = (Object[]) ConfigurationFileIO.loadGeneralConfig(file);

                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        try {
                            msg.setContentObject(new Object[]{"Delete all"});
                        } catch (IOException ex) {
                            ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
                        agent.send(msg);


                        serverConfigurationController.setTableData((List<String[]>) data[0]);
                        taskConfigurationController.setTableData((List<String[]>) data[1]);

                        serverConfigurationController.getConfigurationPanel().repaint();
                        taskConfigurationController.getConfigurationPanel().repaint();

                        serverConfigurationController.generateEntities(agent);
                        taskConfigurationController.generateEntities(agent);

                        workloadSchedulerController.refreshAvailableTasks();
                        workloadSchedulerController.setSchedule((List<Pair<String, Pair<Integer, Integer>>>) data[2]);
                        expertGui.repaintSchedule();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Invalid file", "Load error", JOptionPane.WARNING_MESSAGE);
                        e1.printStackTrace();
                    }
                }

            }
        };

        saveTasksConfiguration = new AbstractAction("Save tasks configuration") {

            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(tasksConfigFilter);
                int userOption = fileChooser.showSaveDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (file.getPath().endsWith(".tasks_config")) {
                            ConfigurationFileIO.saveConfiguration(taskConfigurationController.getTableData(), file);
                        } else {
                            ConfigurationFileIO.saveConfiguration(taskConfigurationController.getTableData(), new File(file.getPath() + ".tasks_config"));
                        }
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "File save error", "Save error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };

        saveServersConfiguration = new AbstractAction("Save servers configuration") {

            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(serversConfigFilter);
                int userOption = fileChooser.showSaveDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (file.getPath().endsWith(".servers_config")) {
                            ConfigurationFileIO.saveConfiguration(serverConfigurationController.getTableData(), file);
                        } else {
                            ConfigurationFileIO.saveConfiguration(serverConfigurationController.getTableData(), new File(file.getPath() + ".servers_config"));
                        }

                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "File save error", "Save error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };

        loadTasksConfiguration = new AbstractAction("Load tasks configuration") {

            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(tasksConfigFilter);
                int userOption = fileChooser.showOpenDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (file.getName().endsWith("servers_config")) {
                            throw new Exception();
                        }
                        java.util.List<String[]> data = ConfigurationFileIO.loadConfiguration(file);
                        taskConfigurationController.setTableData(data);
                        taskConfigurationController.getConfigurationPanel().repaint();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Invalid file", "Load error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };

        loadServersConfiguration = new AbstractAction("Load servers configuration") {

            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileFilter(serversConfigFilter);
                int userOption = fileChooser.showOpenDialog(null);
                if (userOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        if (file.getName().endsWith("tasks_config")) {
                            throw new Exception();
                        }
                        java.util.List<String[]> data = ConfigurationFileIO.loadConfiguration(file);
                        serverConfigurationController.setTableData(data);
                        serverConfigurationController.getConfigurationPanel().repaint();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Invalid file", "Load error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };

        saveTasksConfiguration.putValue(AbstractAction.SHORT_DESCRIPTION, SAVE_TASKS_CONFIGURATION_TOOLTIP);
        saveServersConfiguration.putValue(AbstractAction.SHORT_DESCRIPTION, SAVE_SERVES_CONFIGURATION_TOOLTIP);
        loadTasksConfiguration.putValue(AbstractAction.SHORT_DESCRIPTION, LOAD_TASKS_CONFIGURATION_TOOLTIP);
        loadServersConfiguration.putValue(AbstractAction.SHORT_DESCRIPTION, LOAD_SERVERS_CONFIGURATION_TOOLTIP);

        expertGui.addFileMenuAction(saveTasksConfiguration);
        expertGui.addFileMenuAction(saveServersConfiguration);
        expertGui.addFileMenuAction(loadTasksConfiguration);
        expertGui.addFileMenuAction(loadServersConfiguration);
        expertGui.addFileMenuAction(saveConfiguration);
        expertGui.addFileMenuAction(loadConfiguration);

        expertGui.addFileMenuAction(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                expertGui.dispose();
                for (Timer timer : timers) {
                    timer.stop();
                }
            }
        });
    }


    private void refreshEnergyEstimate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        EnergyConsumptionFactory energyConsumptionFactory = new EnergyConsumptionFactory();
        EnergyConsumption energyConsumption = energyConsumptionFactory.getEstimator(modelAccess);
        energyEstimateWithoutAlg = energyConsumption.getValueWithoutAlgorithm().intValue();
        energyEstimateWithAlg = energyConsumption.getValueWithRunningAlgorithm().intValue();
        System.out.println("After refresh  " + energyEstimateWithoutAlg + " ___ " + energyEstimateWithAlg);
    }

    private void refreshServersMonitorsPanel() {
        JTabbedPane tabbedPane = expertGui.getServerMonitorTabbedPane();
        tabbedPane.removeAll();

        Map<String, Pair<AbstractMonitor, AbstractMonitor>> map = serversMonitorController.getServerMonitors();
        for (String serverName : map.keySet()) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            Pair<AbstractMonitor, AbstractMonitor> pair = map.get(serverName);
            panel.add(pair.getFirst().getMonitorPanel(), 0, 0);
            panel.add(pair.getSecond().getMonitorPanel(), 0, 1);
            tabbedPane.addTab(serverName, panel);
        }
    }

    private void createEnergyConsumptionCharts() {
        refreshEnergyEstimate();
        final ResourceMonitorXYChartPlotter plotter1 =
                new ResourceMonitorXYChartPlotter("Energy Consumption without GAMES infrastructure",
                        "Time(s)", "Power Consumed (W)", 0, 300);
        final ResourceMonitorXYChartPlotter plotter2 =
                new ResourceMonitorXYChartPlotter("Energy Consumption with GAMES infrastructure",
                        "Time(s)", "Power Consumed (W)", 0, 300);
        plotter1.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
        plotter2.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plotter1.setCurrentValue(energyEstimateWithoutAlg);
                plotter2.setCurrentValue(energyEstimateWithAlg);
            }
        };
        Timer refreshTimerEnergyConsumption = new Timer(decisionTimeRefreshRateInMillis, actionListener);
        refreshTimerEnergyConsumption.start();
        timers.add(refreshTimerEnergyConsumption);
        expertGui.addEnergyConsumptionWithoutOptimizationAlgorithm(plotter1.getGraphPanel());
        expertGui.addEnergyConsumptionWithOptimizationAlgorithm(plotter2.getGraphPanel());
    }

    private void createMemoryUsageChart() {
        final ResourceMonitorXYChartPlotter plotter = new ResourceMonitorXYChartPlotter("Memory usage", "Time(s)", "Memory used (MB)", 0, 100);
        plotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
                        MemoryUsage heapUsage = mbean.getHeapMemoryUsage();
                        MemoryUsage nonHeapUsage = mbean.getNonHeapMemoryUsage();
                        plotter.setCurrentValue(new Long(heapUsage.getUsed() + nonHeapUsage.getUsed()).intValue() / 1048576);
                    }
                }.start();
            }
        };
        Timer refreshDecisionTimeTimer = new Timer(decisionTimeRefreshRateInMillis, actionListener);
        refreshDecisionTimeTimer.start();
        timers.add(refreshDecisionTimeTimer);
        expertGui.addMemoryUsagePanel(plotter.getGraphPanel());
    }

    private void createDecisionTimeChart() {
        final ResourceMonitorXYChartPlotter plotter = new ResourceMonitorXYChartPlotter("DecisionTime", "Time (s)", "Decision Making Time (milliseconds)", 0, 100);
        plotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
//        plotter.setMaxTimeRange(3000);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        plotter.setCurrentValue(decisionTime);
                        decisionTime = 0;
                    }
                }.start();
            }
        };
        Timer refreshDecisionTimeTimer = new Timer(decisionTimeRefreshRateInMillis, actionListener);
        refreshDecisionTimeTimer.start();
        timers.add(refreshDecisionTimeTimer);
        expertGui.addDecisionTimePanel(plotter.getGraphPanel());
    }

    public GUIAgent getAgent() {
        return agent;
    }

    public void setAgent(GUIAgent agent) {
        this.agent = agent;
    }

    public int getDecisionTime() {
        return decisionTime;
    }

    public void setDecisionTime(int decisionTime) {
        this.decisionTime = decisionTime;
    }

    public void update(Observable o, Object arg) {
        Object[] data = (Object[]) arg;
        if (data[0].equals("Log")) {
            this.expertGui.logMessage(data[1].toString());
        } else if (data[0].equals("Tasks added")) {
            workloadSchedulerController.refreshAvailableTasks();
        } else if (data[0].equals("Servers added")) {
            new Thread() {
                @Override
                public void run() {
                    refreshServersMonitorsPanel();
                    refreshEnergyEstimate();
                }

            }.start();
        } else if (data[0].equals("Running time")) {
            refreshEnergyEstimate();
            decisionTime = ((Long) data[1]).intValue();
        }
    }


}