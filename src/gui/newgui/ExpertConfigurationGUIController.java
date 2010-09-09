package gui.newgui;

import globalLoop.agents.GUIAgent;
import gui.energyConsumption.EnergyConsumption;
import gui.energyConsumption.EnergyConsumptionFactory;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;
import model.impl.util.ModelAccess;
import utils.fileIO.ConfigurationFileIO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 9:40:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExpertConfigurationGUIController {
    private GUIAgent agent;
    private ModelAccess modelAccess;

    private ExpertConfigurationGUI expertGui;
    private ServerConfigurationController serverConfigurationController;
    private TaskConfigurationController taskConfigurationController;
    private WorkloadSchedulerController workloadSchedulerController;


    private final String SAVE_TASKS_CONFIGURATION_TOOLTIP = "Saves the tasks configuration information from the tasks table in a user specified file";
    private final String SAVE_SERVES_CONFIGURATION_TOOLTIP = "Saves the servers configuration information from the servers table in a user specified file";
    private final String LOAD_TASKS_CONFIGURATION_TOOLTIP = "<html>Loads a previousely saved tasks configuration.<br><b>WARNING:</b>Replaces the existing configuration</br></html>";
    private final String LOAD_SERVERS_CONFIGURATION_TOOLTIP = "<html>Loads a previousely saved servers configuration.<br><b>WARNING:</b>Replaces the existing configuration</br></html>";

    private AbstractAction saveTasksConfiguration;
    private AbstractAction saveServersConfiguration;
    private AbstractAction loadTasksConfiguration;
    private AbstractAction loadServersConfiguration;

    private JFileChooser fileChooser;


    private int decisionTime = 0;
    private int decisionTimeRefreshRateInMillis = 1000;
    private List<Timer> timers;

    {
        timers = new ArrayList<Timer>(2);
    }

    public ExpertConfigurationGUIController(GUIAgent agent, ModelAccess modelAccess, ExpertConfigurationGUI gui) {
        this.agent = agent;
        this.modelAccess = modelAccess;
        this.expertGui = gui;
        serverConfigurationController = new ServerConfigurationController(agent);
        taskConfigurationController = new TaskConfigurationController(agent);
        workloadSchedulerController = new WorkloadSchedulerController(modelAccess);
        expertGui.addServerConfigurationPanel(serverConfigurationController.getConfigurationPanel());
        expertGui.addWorkloadConfigurationPanel(taskConfigurationController.getConfigurationPanel());

        expertGui.addAvailableTasksPanel(workloadSchedulerController.getAvailableTasksTree());
        createDecisionTimeChart();
        createMemoryUsageChart();

        fileChooser = new JFileChooser();
        File file = new File("./testConfigurations");
        if (file.exists()) {
            fileChooser.setCurrentDirectory(file);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        saveTasksConfiguration = new AbstractAction("Save tasks configuration") {

            public void actionPerformed(ActionEvent e) {
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

        expertGui.addFileMenuAction(new AbstractAction("Close") {
            public void actionPerformed(ActionEvent e) {
                expertGui.dispose();
                for (Timer timer : timers) {
                    timer.stop();
                }
            }
        });
    }

    private void createServersConfigurationPanel() {

    }

    private void createMemoryUsageChart() {
        final ResourceMonitorXYChartPlotter plotter = new ResourceMonitorXYChartPlotter("Memory usage", "Time(s)", "Memory used (MB)", 0, 100);
        plotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
        final Runtime runtime = Runtime.getRuntime();
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        plotter.setCurrentValue(new Long(runtime.totalMemory() - runtime.freeMemory()).intValue() / 1048576);
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
        final ResourceMonitorXYChartPlotter plotter = new ResourceMonitorXYChartPlotter("DecisionTime", "Time(s)", "Decision Making Time(s)", 0, 100);
        plotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        plotter.setCurrentValue(decisionTime);
                    }
                }.start();
                decisionTime = 0;
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
}
