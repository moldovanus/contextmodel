package gui.resourceMonitor.taskMonitor;

import gui.resourceMonitor.IMonitor;
import gui.resourceMonitor.resourceMonitorPlotter.impl.TaskResourceMonitor;
import model.interfaces.resources.applications.ApplicationActivity;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: May 24, 2010
 * Time: 11:48:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class TaskMonitor implements IMonitor {

    private JPanel taskPanel;
    private JFrame standaloneWindow;

    private TaskResourceMonitor taskCPUMonitor;
    private TaskResourceMonitor taskMemoryMonitor;
    private TaskResourceMonitor taskStorageMonitor;

    private ApplicationActivity task;

    private int refreshRate = 1000;
    private Timer refreshInfoTimer;

    public TaskMonitor(ApplicationActivity task) {
        this.task = task;
        setup();
        refreshInfoTimer = new Timer(refreshRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Thread thread = new Thread() {

                    public void run() {
                        refreshData();
                    }
                };
                thread.start();
            }
        });
        refreshInfoTimer.start();

    }

    private void refreshData() {

        if (task != null) {
            taskCPUMonitor.setCurrentValue(task.getCpuAllocatedValue());
            taskMemoryMonitor.setCurrentValue(task.getMemAllocatedValue());
            taskStorageMonitor.setCurrentValue(task.getHddAllocatedValue());
        }
    }

    private void setup() {
        taskPanel = new JPanel();
        taskPanel.setBackground(new Color(255, 255, 255));

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(3, 1));
        taskPanel.setLayout(new BorderLayout());


        taskCPUMonitor = new TaskResourceMonitor("CPU", (int)task.getCpuRequiredMinValue(),(int) task.getCpuRequiredMaxValue());
        taskMemoryMonitor = new TaskResourceMonitor("Memory", (int)task.getMemRequiredMinValue(),(int) task.getMemRequiredMaxValue());
        taskStorageMonitor = new TaskResourceMonitor("Storage", (int)task.getHddRequiredMinValue(),(int) task.getHddRequiredMaxValue());

        taskCPUMonitor.setCurrentValue(task.getCpuAllocatedValue());
        taskMemoryMonitor.setCurrentValue(task.getMemAllocatedValue());
        taskStorageMonitor.setCurrentValue(task.getHddAllocatedValue());

        JPanel cpuPanel = new JPanel();
                         cpuPanel.setLayout(new BorderLayout());
        cpuPanel.add(new JLabel("Cores " +  task.getNumberOfCoresRequiredValue()), BorderLayout.NORTH);
        cpuPanel.add(taskCPUMonitor.getGraphPanel(),BorderLayout.CENTER);

        centerPanel.add(cpuPanel);
        centerPanel.add(taskMemoryMonitor.getGraphPanel());
        centerPanel.add(taskStorageMonitor.getGraphPanel());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        taskPanel.add(new JLabel(task.getLocalName() + "(" + task.getLocalName() + ")"), BorderLayout.NORTH);
        taskPanel.add(centerPanel, BorderLayout.CENTER);

    }

    public void executeStandaloneWindow() {
        standaloneWindow = new JFrame(task.getLocalName() + "(" + task.getLocalName() + ")" + " Monitor");
        standaloneWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        standaloneWindow.setLayout(new BorderLayout());
        standaloneWindow.add(taskPanel, "Center");
        standaloneWindow.setSize(300, 500);
        standaloneWindow.setVisible(true);
    }

    public JPanel getMonitorPanel() {
        return taskPanel;
    }

    public void destroyStandaloneWindow() {
        if (standaloneWindow != null) {
            standaloneWindow.dispose();
        }
    }
}
