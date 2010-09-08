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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Administrator
 */
public class ExpertConfigurationGUI extends javax.swing.JFrame {

    /**
     * Creates new form ExpertConfigurationGUI
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

    public void addServerConfigurationPanel(JPanel panel){
        serverConfigurationTabPanel.add(panel,"Center");
        serverConfigurationTabPanel.repaint();
    }

    public void addWorkloadConfigurationPanel(JPanel panel){
        workloadConfigurationPanel.add(panel,"Center");
        workloadConfigurationPanel.repaint();
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
                        .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
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
            .addGap(0, 584, Short.MAX_VALUE)
        );
        decisionTimePanelLayout.setVerticalGroup(
            decisionTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        decisionTimeBasePanel.add(decisionTimePanel, java.awt.BorderLayout.CENTER);

        memoryUsageBasePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        memoryUsageBasePanel.setLayout(new java.awt.BorderLayout());

        memoryUsagePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout memoryUsagePanelLayout = new javax.swing.GroupLayout(memoryUsagePanel);
        memoryUsagePanel.setLayout(memoryUsagePanelLayout);
        memoryUsagePanelLayout.setHorizontalGroup(
            memoryUsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );
        memoryUsagePanelLayout.setVerticalGroup(
            memoryUsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        memoryUsageBasePanel.add(memoryUsagePanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout generalInfoTabPanelLayout = new javax.swing.GroupLayout(generalInfoTabPanel);
        generalInfoTabPanel.setLayout(generalInfoTabPanelLayout);
        generalInfoTabPanelLayout.setHorizontalGroup(
            generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalInfoTabPanelLayout.createSequentialGroup()
                .addComponent(decisionTimeBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memoryUsageBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addContainerGap())
        );
        generalInfoTabPanelLayout.setVerticalGroup(
            generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalInfoTabPanelLayout.createSequentialGroup()
                .addGroup(generalInfoTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(memoryUsageBasePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(decisionTimeBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabbedPane.addTab("General Info", generalInfoTabPanel);

        serverConfigurationTabPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Server Configuration", serverConfigurationTabPanel);

        workloadConfigurationPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Workload Configuration", workloadConfigurationPanel);

        javax.swing.GroupLayout workloadSchedulerPanelLayout = new javax.swing.GroupLayout(workloadSchedulerPanel);
        workloadSchedulerPanel.setLayout(workloadSchedulerPanelLayout);
        workloadSchedulerPanelLayout.setHorizontalGroup(
            workloadSchedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 927, Short.MAX_VALUE)
        );
        workloadSchedulerPanelLayout.setVerticalGroup(
            workloadSchedulerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Workload Scheduler", workloadSchedulerPanel);

        resourcesMonitorPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("Resources Monitor", resourcesMonitorPanel);

        javax.swing.GroupLayout eneryEfficiencyPanelLayout = new javax.swing.GroupLayout(eneryEfficiencyPanel);
        eneryEfficiencyPanel.setLayout(eneryEfficiencyPanelLayout);
        eneryEfficiencyPanelLayout.setHorizontalGroup(
            eneryEfficiencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 927, Short.MAX_VALUE)
        );
        eneryEfficiencyPanelLayout.setVerticalGroup(
            eneryEfficiencyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Energy Eficiency Monitor", eneryEfficiencyPanel);

        fileMenu.setText("File");
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(upperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logControlPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(upperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logControlPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulateChoiceRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateChoiceRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simulateChoiceRadioActionPerformed

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
    private javax.swing.JPanel decisionTimeBasePanel;
    private javax.swing.JPanel decisionTimePanel;
    private javax.swing.JPanel eneryEfficiencyPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel generalInfoTabPanel;
    private javax.swing.JPanel logControlPane3;
    private javax.swing.JLabel logLabel3;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JPanel memoryUsageBasePanel;
    private javax.swing.JPanel memoryUsagePanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton realChoiceRadio;
    private javax.swing.JPanel resourcesMonitorPanel;
    private javax.swing.JButton runOnButton;
    private javax.swing.JPanel serverConfigurationTabPanel;
    private javax.swing.JToggleButton showLogButton;
    private javax.swing.JRadioButton simulateChoiceRadio;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPanel upperPanel;
    private javax.swing.JPanel workloadConfigurationPanel;
    private javax.swing.JPanel workloadSchedulerPanel;
    // End of variables declaration//GEN-END:variables


}
