package gui.newgui;

import globalLoop.agents.GUIAgent;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private ExpertConfigurationGUI expertGui;
    private ServerConfigurationController serverConfigurationController;
    private TaskConfigurationController taskConfigurationController;

    private int decisionTime = 0;
    private int decisionTimeRefreshRateInMillis = 1000;
    private List<Timer> timers;

    {
        timers = new ArrayList<Timer>(2);
    }

    public ExpertConfigurationGUIController(GUIAgent agent, ExpertConfigurationGUI gui) {
        this.agent = agent;
        this.expertGui = gui;
        serverConfigurationController = new ServerConfigurationController(agent);
        taskConfigurationController = new TaskConfigurationController(agent);

        expertGui.addServerConfigurationPanel(serverConfigurationController.getConfigurationPanel());
        expertGui.addWorkloadConfigurationPanel(taskConfigurationController.getConfigurationPanel());

        createDecisionTimeChart();
        createMemoryUsageChart();

        gui.addFileMenuAction(new AbstractAction("Close") {
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
