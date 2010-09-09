/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExpertConfigurationGUI.java
 *
 * Created on Sep 7, 2010, 4:30:35 PM
 */

package gui.newgui;

import utils.fileIO.ConfigurationFileIO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @author Administrator
 */
public class ExpertConfigurationGUI extends javax.swing.JFrame {

    private JTree availableTasksTree;

    /**
     * Creates new
     *
     *
     *
     * form ExpertConfigurationGUI
     */
    public ExpertConfigurationGUI() {
        initComponents();
        initComponents_2();
    }

    private void initComponents_2() {
        showLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (logTextArea.isVisible()) {
                    logTextArea.setVisible(false);
                } else {
                    logTextArea.setVisible(true);
                }
            }
        });

        showLogButton.setText("Hide Log");
        this.setTitle("GAMES Expert Window");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(realChoiceRadio);
        radioGroup.add(simulateChoiceRadio);


    }

    public void addFileMenuAction(AbstractAction action) {
        fileMenu.add(action);
    }

    public void logMessage(String message) {
        logTextArea.append(message + "\n");
    }

    public void addDecisionTimePanel(JPanel panel) {
        decisionTimeBasePanel.add(panel, "Center");
        decisionTimeBasePanel.repaint();
    }

    public void addMemoryUsagePanel(JPanel graphPanel) {
        memoryUsageBasePanel.add(graphPanel, "Center");
        memoryUsageBasePanel.repaint();
    }

    public void addServerConfigurationPanel(JPanel panel) {
        serverConfigurationTabPanel.add(panel, "Center");
        serverConfigurationTabPanel.repaint();
    }

    public void addWorkloadConfigurationPanel(JPanel panel) {
        workloadConfigurationPanel.add(panel, "Center");
        workloadConfigurationPanel.repaint();
    }

    public void addAvailableTasksPanel(JTree tree) {
        if (availableTasksTree != null) {
            existingWorkloadBasePanel.remove(availableTasksTree);
            existingWorkloadBasePanel.add(tree, "Center");
            existingWorkloadBasePanel.repaint();
        } else {
            existingWorkloadBasePanel.add(tree, "Center");
            existingWorkloadBasePanel.repaint();
            availableTasksTree = tree;
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        upperPanel = new javax.swing.JPanel();
        realChoiceRadio = new javax.swing.JRadioButton();
        runOnButton = new javax.swing.JButton();
        simulateChoiceRadio = new javax.swing.JRadioButton();
        logControlPane3 = new javax.swing.JPanel();
        logLabel3 = new javax.swing.JLabel();
        showLogButton = new javax.swing.JToggleButton();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        tabbedPane = new javax.swing.JTabbedPane();
        generalInfoTabPanel = new javax.swing.JPanel();
        decisionTimeBasePanel = new javax.swing.JPanel();
        decisionTimePanel = new javax.swing.JPanel();
        memoryUsageBasePanel = new javax.swing.JPanel();
        memoryUsagePanel = new javax.swing.JPanel();
        serverConfigurationTabPanel = new javax.swing.JPanel();
        workloadConfigurationPanel = new javax.swing.JPanel();
        workloadSchedulerPanel = new javax.swing.JPanel();
        centerWorkloadSchedulerTimer = new javax.swing.JPanel();
        existingWorkloadBasePanel = new javax.swing.JPanel();
        existingWorkloadScrollPanel = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        scheduleButton = new javax.swing.JButton();
        scheduleDelaySpinner = new javax.swing.JSpinner();
        timeDelayLabel = new javax.swing.JLabel();
        existingWorkloadLable = new javax.swing.JLabel();
        scheduleWorkloadBasePanel = new javax.swing.JPanel();
        scheduleTablePanel = new javax.swing.JPanel();
        scheduleControlPanel = new javax.swing.JPanel();
        deleteScheduleRowButton = new javax.swing.JButton();
        duplicateScheduleRowButton = new javax.swing.JButton();
        generateRandomScheduleRowButton = new javax.swing.JButton();
        workloadScheduleLabel = new javax.swing.JLabel();
        schedulerControlPanel = new javax.swing.JPanel();
        timerControlPanel = new javax.swing.JPanel();
        scheduleControlLeftPanel = new javax.swing.JPanel();
        timerLabel = new javax.swing.JLabel();
        timerProgressLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        scheduleControlRightPanel = new javax.swing.JPanel();
        radioButtonPanel = new javax.swing.JPanel();
        popupNotificationRadioButton = new javax.swing.JRadioButton();
        resourcesMonitorPanel = new javax.swing.JPanel();
        eneryEfficiencyPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        upperPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        realChoiceRadio.setText("real service center");

        runOnButton.setText("Run on");

        simulateChoiceRadio.setText("simulated service center");
        simulateChoiceRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateChoiceRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout upperPanelLayout = new javax.swing.GroupLayout(upperPanel);
        upperPanel.setLayout(upperPanelLayout);
        upperPanelLayout.setHorizontalGroup(
                upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(upperPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(runOnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(simulateChoiceRadio)
                                .addComponent(realChoiceRadio))
                        .addContainerGap(704, Short.MAX_VALUE))
        );
        upperPanelLayout.setVerticalGroup(
                upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(upperPanelLayout.createSequentialGroup()
                        .addGroup(upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(upperPanelLayout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(runOnButton))
                                .addGroup(upperPanelLayout.createSequentialGroup()
                                .addComponent(simulateChoiceRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(realChoiceRadio)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        logLabel3.setText("Log");

        showLogButton.setText("Show Log");

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        javax.swing.GroupLayout logControlPane3Layout = new javax.swing.GroupLayout(logControlPane3);
        logControlPane3.setLayout(logControlPane3Layout);
        logControlPane3Layout.setHorizontalGroup(
                logControlPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPane3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logControlPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPane3Layout.createSequentialGroup()
                                .addComponent(logLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(showLogButton)
                                .addContainerGap())
                        .addGroup(logControlPane3Layout.createSequentialGroup()
                        .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1066, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        logControlPane3Layout.setVerticalGroup(
                logControlPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPane3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logControlPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(logLabel3)
                                .addComponent(showLogButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        decisionTimeBasePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        decisionTimeBasePanel.setLayout(new java.awt.BorderLayout());

        decisionTimePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout decisionTimePanelLayout = new javax.swing.GroupLayout(decisionTimePanel);
        decisionTimePanel.setLayout(decisionTimePanelLayout);
        decisionTimePanelLayout.setHorizontalGroup(
                decisionTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 669, Short.MAX_VALUE)
        );
        decisionTimePanelLayout.setVerticalGroup(
                decisionTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 333, Short.MAX_VALUE)
        );

        decisionTimeBasePanel.add(decisionTimePanel, java.awt.BorderLayout.CENTER);

        memoryUsageBasePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        memoryUsageBasePanel.setLayout(new java.awt.BorderLayout());

        memoryUsagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout memoryUsagePanelLayout = new javax.swing.GroupLayout(memoryUsagePanel);
        memoryUsagePanel.setLayout(memoryUsagePanelLayout);
        memoryUsagePanelLayout.setHorizontalGroup(
                memoryUsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 408, Short.MAX_VALUE)
        );
        memoryUsagePanelLayout.setVerticalGroup(
                memoryUsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 333, Short.MAX_VALUE)
        );

        memoryUsageBasePanel.add(memoryUsagePanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout generalInfoTabPanelLayout = new javax.swing.GroupLayout(generalInfoTabPanel);
        generalInfoTabPanel.setLayout(generalInfoTabPanelLayout);
        generalInfoTabPanelLayout.setHorizontalGroup(
                generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(generalInfoTabPanelLayout.createSequentialGroup()
                        .addComponent(decisionTimeBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memoryUsageBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                        .addContainerGap())
        );
        generalInfoTabPanelLayout.setVerticalGroup(
                generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalInfoTabPanelLayout.createSequentialGroup()
                        .addGroup(generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(memoryUsageBasePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                .addComponent(decisionTimeBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                        .addContainerGap())
        );

        tabbedPane.addTab("General Info", generalInfoTabPanel);

        serverConfigurationTabPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Server Configuration", serverConfigurationTabPanel);

        workloadConfigurationPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Workload Configuration", workloadConfigurationPanel);

        workloadSchedulerPanel.setLayout(new java.awt.BorderLayout(0, 5));

        existingWorkloadBasePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        existingWorkloadBasePanel.setLayout(new java.awt.BorderLayout());
        existingWorkloadBasePanel.add(existingWorkloadScrollPanel, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        scheduleButton.setText("Schedule >>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        jPanel1.add(scheduleButton, gridBagConstraints);

        scheduleDelaySpinner.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 0.5;
        jPanel1.add(scheduleDelaySpinner, gridBagConstraints);

        timeDelayLabel.setText("Time Delay");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.5;
        jPanel1.add(timeDelayLabel, gridBagConstraints);

        existingWorkloadBasePanel.add(jPanel1, java.awt.BorderLayout.SOUTH);

        existingWorkloadLable.setText("                             Available Activities");
        existingWorkloadBasePanel.add(existingWorkloadLable, java.awt.BorderLayout.PAGE_START);

        scheduleWorkloadBasePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scheduleWorkloadBasePanel.setLayout(new java.awt.BorderLayout());

        scheduleTablePanel.setLayout(new java.awt.BorderLayout());
        scheduleWorkloadBasePanel.add(scheduleTablePanel, java.awt.BorderLayout.LINE_START);

        scheduleControlPanel.setLayout(new java.awt.GridLayout(1, 3));

        deleteScheduleRowButton.setText("Delete Row");
        scheduleControlPanel.add(deleteScheduleRowButton);

        duplicateScheduleRowButton.setText("Duplicate Row");
        scheduleControlPanel.add(duplicateScheduleRowButton);

        generateRandomScheduleRowButton.setText("Generate Random");
        scheduleControlPanel.add(generateRandomScheduleRowButton);

        scheduleWorkloadBasePanel.add(scheduleControlPanel, java.awt.BorderLayout.SOUTH);

        workloadScheduleLabel.setText("                                                    Workload Schedule ");
        scheduleWorkloadBasePanel.add(workloadScheduleLabel, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout centerWorkloadSchedulerTimerLayout = new javax.swing.GroupLayout(centerWorkloadSchedulerTimer);
        centerWorkloadSchedulerTimer.setLayout(centerWorkloadSchedulerTimerLayout);
        centerWorkloadSchedulerTimerLayout.setHorizontalGroup(
                centerWorkloadSchedulerTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(centerWorkloadSchedulerTimerLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(existingWorkloadBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(scheduleWorkloadBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                        .addGap(311, 311, 311))
        );
        centerWorkloadSchedulerTimerLayout.setVerticalGroup(
                centerWorkloadSchedulerTimerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(centerWorkloadSchedulerTimerLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(existingWorkloadBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerWorkloadSchedulerTimerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scheduleWorkloadBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
        );

        workloadSchedulerPanel.add(centerWorkloadSchedulerTimer, java.awt.BorderLayout.CENTER);

        schedulerControlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        schedulerControlPanel.setLayout(new java.awt.BorderLayout());

        timerControlPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        timerControlPanel.setLayout(new java.awt.GridLayout(1, 2));

        scheduleControlLeftPanel.setLayout(new java.awt.GridBagLayout());

        timerLabel.setText("Schedule Timer Control:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        scheduleControlLeftPanel.add(timerLabel, gridBagConstraints);

        timerProgressLabel.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.5;
        scheduleControlLeftPanel.add(timerProgressLabel, gridBagConstraints);

        startButton.setText("Start");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        scheduleControlLeftPanel.add(startButton, gridBagConstraints);

        pauseButton.setText("Pause");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        scheduleControlLeftPanel.add(pauseButton, gridBagConstraints);

        stopButton.setText("Stop");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        scheduleControlLeftPanel.add(stopButton, gridBagConstraints);

        timerControlPanel.add(scheduleControlLeftPanel);

        scheduleControlRightPanel.setLayout(new java.awt.GridLayout(1, 3));
        timerControlPanel.add(scheduleControlRightPanel);

        schedulerControlPanel.add(timerControlPanel, java.awt.BorderLayout.CENTER);

        radioButtonPanel.setLayout(new java.awt.BorderLayout());

        popupNotificationRadioButton.setText("Generate Pop-Up Notifications");
        popupNotificationRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupNotificationRadioButtonActionPerformed(evt);
            }
        });
        radioButtonPanel.add(popupNotificationRadioButton, java.awt.BorderLayout.CENTER);

        schedulerControlPanel.add(radioButtonPanel, java.awt.BorderLayout.SOUTH);

        workloadSchedulerPanel.add(schedulerControlPanel, java.awt.BorderLayout.SOUTH);

        tabbedPane.addTab("Workload Scheduler", workloadSchedulerPanel);

        resourcesMonitorPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Resources Monitor", resourcesMonitorPanel);

        javax.swing.GroupLayout eneryEfficiencyPanelLayout = new javax.swing.GroupLayout(eneryEfficiencyPanel);
        eneryEfficiencyPanel.setLayout(eneryEfficiencyPanelLayout);
        eneryEfficiencyPanelLayout.setHorizontalGroup(
                eneryEfficiencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1101, Short.MAX_VALUE)
        );
        eneryEfficiencyPanelLayout.setVerticalGroup(
                eneryEfficiencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 348, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Energy Eficiency Monitor", eneryEfficiencyPanel);

        fileMenu.setText("File");
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(upperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(logControlPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addComponent(upperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logControlPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulateChoiceRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateChoiceRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simulateChoiceRadioActionPerformed

    private void popupNotificationRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupNotificationRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_popupNotificationRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpertConfigurationGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centerWorkloadSchedulerTimer;
    private javax.swing.JPanel decisionTimeBasePanel;
    private javax.swing.JPanel decisionTimePanel;
    private javax.swing.JButton deleteScheduleRowButton;
    private javax.swing.JButton duplicateScheduleRowButton;
    private javax.swing.JPanel eneryEfficiencyPanel;
    private javax.swing.JPanel existingWorkloadBasePanel;
    private javax.swing.JLabel existingWorkloadLable;
    private javax.swing.JScrollPane existingWorkloadScrollPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel generalInfoTabPanel;
    private javax.swing.JButton generateRandomScheduleRowButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel logControlPane3;
    private javax.swing.JLabel logLabel3;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JPanel memoryUsageBasePanel;
    private javax.swing.JPanel memoryUsagePanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton pauseButton;
    private javax.swing.JRadioButton popupNotificationRadioButton;
    private javax.swing.JPanel radioButtonPanel;
    private javax.swing.JRadioButton realChoiceRadio;
    private javax.swing.JPanel resourcesMonitorPanel;
    private javax.swing.JButton runOnButton;
    private javax.swing.JButton scheduleButton;
    private javax.swing.JPanel scheduleControlLeftPanel;
    private javax.swing.JPanel scheduleControlPanel;
    private javax.swing.JPanel scheduleControlRightPanel;
    private javax.swing.JSpinner scheduleDelaySpinner;
    private javax.swing.JPanel scheduleTablePanel;
    private javax.swing.JPanel scheduleWorkloadBasePanel;
    private javax.swing.JPanel schedulerControlPanel;
    private javax.swing.JPanel serverConfigurationTabPanel;
    private javax.swing.JToggleButton showLogButton;
    private javax.swing.JRadioButton simulateChoiceRadio;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JLabel timeDelayLabel;
    private javax.swing.JPanel timerControlPanel;
    private javax.swing.JLabel timerLabel;
    private javax.swing.JLabel timerProgressLabel;
    private javax.swing.JPanel upperPanel;
    private javax.swing.JPanel workloadConfigurationPanel;
    private javax.swing.JLabel workloadScheduleLabel;
    private javax.swing.JPanel workloadSchedulerPanel;
    // End of variables declaration//GEN-END:variables


}
