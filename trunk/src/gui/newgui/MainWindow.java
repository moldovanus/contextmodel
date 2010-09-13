/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on Sep 7, 2010, 12:24:38 PM
 */

package gui.newgui;

import globalLoop.agents.GUIAgent;
import gui.energyConsumption.EnergyConsumption;
import gui.energyConsumption.EnergyConsumptionFactory;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author Administrator
 */
public class MainWindow extends javax.swing.JFrame implements Observer {

    private GUIAgent agent;
    private ModelAccess modelAccess;
    private MainWindow selfReference;

    private int decisionTime = 0;
    private int decisionTimeRefreshRateInMillis = 1000;

    private int energyEstimateWithoutAlg = 0;
    private int energyEstimateWithAlg = 0;

//    private List<ServerDto> computingResourcesList;
    private Map<TaskDto, String> applicationActivitiesList;

    {
        applicationActivitiesList = new HashMap<TaskDto, String>();
//        computingResourcesList = new ArrayList<ServerDto>();
    }

    public void logMessage(String message) {
        boolean caretIsAtEnd = logTextArea.getCaretPosition() == logTextArea.getDocument().getLength();
        logTextArea.append(message + "\n");
        if (caretIsAtEnd) {
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        }
    }

    public void setRunButtonActionListener(ActionListener listener) {
        showTopologyButton.addActionListener(listener);
    }
//
//    public List<ServerDto> getComputingResourcesList() {
//        return computingResourcesList;
//    }
//
//    public void setComputingResourcesList(List<ServerDto> computingResourcesList) {
//        this.computingResourcesList = computingResourcesList;
//        refreshComputingResourcesTree();
//    }
//
//    public void addComputingResource(ServerDto dto) {
//        computingResourcesList.add(dto);
//        refreshComputingResourcesTree();
//    }
//
//    public void removeComputingResource(ServerDto dto) {
//        computingResourcesList.remove(dto);
//        refreshComputingResourcesTree();
//    }

    public void setApplicationActivities(Collection<TaskDto> activities) {
        applicationActivitiesList.clear();
        for (TaskDto taskDto : activities) {
            applicationActivitiesList.put(taskDto, taskDto.getTaskName());
        }
        refreshApplicationActivities();
    }


    public void setApplicationActivityStatus(TaskDto taskDto, String status) {
        applicationActivitiesList.put(taskDto, status);
        refreshApplicationActivities();
    }

    private void refreshApplicationActivities() {
        Thread thread = new Thread() {
            public void run() {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Activities");
                for (TaskDto taskDto : applicationActivitiesList.keySet()) {
                    DefaultMutableTreeNode task = new DefaultMutableTreeNode(applicationActivitiesList.get(taskDto));
                    DefaultMutableTreeNode taskRequestedCores = new DefaultMutableTreeNode("requested cores: " + taskDto.getRequestedCores());
                    DefaultMutableTreeNode taskRequestedCpu =
                            new DefaultMutableTreeNode("requested CPU min: " + taskDto.getRequestedCPUMin()
                                    + ", CPU max: " + taskDto.getRequestedCPUMax());
                    DefaultMutableTreeNode taskRequestedMem =
                            new DefaultMutableTreeNode("requested MEM min: " + taskDto.getRequestedMemoryMin()
                                    + ", MEM max: " + taskDto.getRequestedMemoryMax());
                    DefaultMutableTreeNode taskRequestedHdd =
                            new DefaultMutableTreeNode("requested HDD min: " + taskDto.getRequestedStorageMin()
                                    + ", HDD max: " + taskDto.getRequestedStorageMax());

                    task.add(taskRequestedCores);
                    task.add(taskRequestedCpu);
                    task.add(taskRequestedMem);
                    task.add(taskRequestedHdd);
                    root.add(task);
                }
                DefaultTreeModel model = (DefaultTreeModel) workloadScheduleJTree.getModel();
                model.setRoot(root);
            }
        };
        thread.start();
    }


    private void refreshComputingResourcesTree() {

        Thread refreshThread = new Thread() {
            @Override
            public void run() {

                computingResourcesPanel.remove(computingResourcesTree);
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Resources");

                for (ServiceCenterServer server : modelAccess.getAllServiceCenterServerInstances()) {
                    DefaultMutableTreeNode serverNode = new DefaultMutableTreeNode(server.getLocalName());
                    {
                        DefaultMutableTreeNode serverCPU = new DefaultMutableTreeNode("CPU");
                        DefaultMutableTreeNode coreCount =
                                new DefaultMutableTreeNode("cores: "
                                        + server.getCpuResources().iterator().next().getAssociatedCores().size());
                        DefaultMutableTreeNode totalServerCPU =
                                new DefaultMutableTreeNode("frequency: "
                                        + server.getCpuResources().iterator().next().getAssociatedCores().iterator().next().getMaximumWorkLoad());

                        serverCPU.add(coreCount);
                        serverCPU.add(totalServerCPU);

                        serverNode.add(serverCPU);
                    }

                    {
                        DefaultMutableTreeNode serverMEM = new DefaultMutableTreeNode("MEM");
                        DefaultMutableTreeNode totalServerCPU =
                                new DefaultMutableTreeNode("size: "
                                        + server.getMemResources().iterator().next().getMaximumWorkLoad());

                        serverMEM.add(totalServerCPU);

                        serverNode.add(serverMEM);
                    }
                    root.add(serverNode);
                }
                DefaultTreeModel model = (DefaultTreeModel) computingResourcesTree.getModel();
                model.setRoot(root);
            }
        };
        refreshThread.start();
    }

    /**
     * Creates new form MainWindow
     *
     * @param agent
     * @param modelAccess
     */
    public MainWindow(GUIAgent agent, ModelAccess modelAccess) {
        this.agent = agent;
        agent.addObserver(this);
        this.modelAccess = modelAccess;
        initComponents();
        initComponents_2();
        selfReference = this;
    }

    private void initComponents_2() {
        createDecisionTimeChart();
        createEnergyConsumptionCharts();
        simulateChoiceRadio.setSelected(true);
        simulateChoiceRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProxyFactory.setReturnStub(isSimulationChosen());
            }
        });

        realChoiceRadio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProxyFactory.setReturnStub(isSimulationChosen());
            }
        });

        showLogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (logTextArea.isVisible()) {
                    logTextArea.setVisible(false);
                    logBasePanel.remove(logScrollPane);
                    logBasePanel.repaint();
                    selfReference.setSize(selfReference.getWidth(), selfReference.getHeight() - logBasePanel.getHeight());
                } else {
                    logTextArea.setVisible(true);
                    logBasePanel.add(logScrollPane);
                    logScrollPane.setViewportView(logTextArea);
                    logBasePanel.repaint();
                    selfReference.setSize(selfReference.getWidth(), selfReference.getHeight() + logBasePanel.getHeight());
                }
            }
        });

        showLogButton.setText("Hide Log");
        showExpertConfigMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExpertConfigurationGUI gui = new ExpertConfigurationGUI();
                ExpertConfigurationGUIController controller = new ExpertConfigurationGUIController(agent, modelAccess, gui);
                gui.setVisible(true);
            }
        });
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("GAMES Initial Window");
        refreshApplicationActivities();
        refreshComputingResourcesTree();
    }

    private void createDecisionTimeChart() {
        final ResourceMonitorXYChartPlotter plotter = new ResourceMonitorXYChartPlotter("DecisionTime", "Time (s)", "Decision Time (ms)", 0, 100);
        plotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);

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
//        this.energyConsumptionPanel.setLayout(new BorderLayout());
        decisionTimePanel.add(plotter.getGraphPanel(), "Center");
    }

    private void refreshEnergyEstimate() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        EnergyConsumptionFactory energyConsumptionFactory = new EnergyConsumptionFactory();
        EnergyConsumption energyConsumption = energyConsumptionFactory.getEstimator(modelAccess);
        energyEstimateWithoutAlg = energyConsumption.getValueWithoutAlgorithm();
        energyEstimateWithAlg = energyConsumption.getValueWithRunningAlgorithm();
        System.out.println("After refresh  " + energyEstimateWithoutAlg + " ___ " + energyEstimateWithAlg);
    }

    private void createEnergyConsumptionCharts() {
        refreshEnergyEstimate();
        final ResourceMonitorXYChartPlotter plotter1 = new ResourceMonitorXYChartPlotter("Energy Consumption without GAMES infrastructure", "Time(s)", "Energy Consumed (W)", 0, 5000);
        final ResourceMonitorXYChartPlotter plotter2 = new ResourceMonitorXYChartPlotter("Energy Consumption", "Time(s)", "Energy Consumed (W)", 0, 5000);
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
        energyConsumptionPanel.add(plotter2.getGraphPanel(), "Center");
    }

    public void addFileMenuAction(AbstractAction abstractAction) {
        fileMenuItem.add(abstractAction);
    }

    public void addRunOnButtonActionListener(ActionListener listener) {
        showTopologyButton.addActionListener(listener);
    }

    private boolean isSimulationChosen() {
        return simulateChoiceRadio.isSelected();
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooseRunRadioGroup = new javax.swing.ButtonGroup();
        gamesNameLabel = new javax.swing.JLabel();
        gamesLogoPanel = new javax.swing.JPanel();
        computingResourcesPanel = new javax.swing.JPanel();
        computingResourcesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        computingResourcesTree = new javax.swing.JTree();
        energyConsumptionPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        workLoadScheduleScrollPane = new javax.swing.JScrollPane();
        workloadScheduleJTree = new javax.swing.JTree();
        decisionTimePanel = new javax.swing.JPanel();
        showTopologyButton = new javax.swing.JButton();
        simulateChoiceRadio = new javax.swing.JRadioButton();
        realChoiceRadio = new javax.swing.JRadioButton();
        logControlPane = new javax.swing.JPanel();
        logLabel = new javax.swing.JLabel();
        showLogButton = new javax.swing.JToggleButton();
        logBasePanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        runOnLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        configurationMenuItem = new javax.swing.JMenu();
        loadConfigMenuItem = new javax.swing.JMenuItem();
        showExpertConfigMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame"); // NOI18N

        gamesNameLabel.setText("            GAMES PROJECT TITLE");

        gamesLogoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout gamesLogoPanelLayout = new javax.swing.GroupLayout(gamesLogoPanel);
        gamesLogoPanel.setLayout(gamesLogoPanelLayout);
        gamesLogoPanelLayout.setHorizontalGroup(
                gamesLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 340, Short.MAX_VALUE)
        );
        gamesLogoPanelLayout.setVerticalGroup(
                gamesLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 247, Short.MAX_VALUE)
        );

        computingResourcesLabel.setText("Computing Resources");

        jScrollPane2.setViewportView(computingResourcesTree);

        energyConsumptionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        energyConsumptionPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout computingResourcesPanelLayout = new javax.swing.GroupLayout(computingResourcesPanel);
        computingResourcesPanel.setLayout(computingResourcesPanelLayout);
        computingResourcesPanelLayout.setHorizontalGroup(
                computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                        .addGroup(computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(computingResourcesLabel))
                                .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                                .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(energyConsumptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        computingResourcesPanelLayout.setVerticalGroup(
                computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(computingResourcesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(energyConsumptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addContainerGap())
        );

        jLabel1.setText("Workload Schedule");

        workLoadScheduleScrollPane.setViewportView(workloadScheduleJTree);

        decisionTimePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        decisionTimePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(decisionTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(workLoadScheduleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(workLoadScheduleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(decisionTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        showTopologyButton.setText("Show Topology");

        simulateChoiceRadio.setText("simulated service center");
        simulateChoiceRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateChoiceRadioActionPerformed(evt);
            }
        });

        realChoiceRadio.setText("real service center");

        logLabel.setText("Log");

        showLogButton.setText("Show Log");
        showLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLogButtonActionPerformed(evt);
            }
        });

        logBasePanel.setLayout(new java.awt.BorderLayout());

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        logBasePanel.add(logScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout logControlPaneLayout = new javax.swing.GroupLayout(logControlPane);
        logControlPane.setLayout(logControlPaneLayout);
        logControlPaneLayout.setHorizontalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 846, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addComponent(logLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(showLogButton)))
                        .addGap(111, 111, 111))
        );
        logControlPaneLayout.setVerticalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(logLabel)
                                .addComponent(showLogButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        runOnLabel.setText("Run on");

        menuBar.setName("menuBar"); // NOI18N

        fileMenuItem.setText("File");
        fileMenuItem.setName("fileMenuItem"); // NOI18N
        menuBar.add(fileMenuItem);

        configurationMenuItem.setText("Configuration");
        configurationMenuItem.setName("editMenuItem"); // NOI18N

        loadConfigMenuItem.setText("Load Configuration");
        configurationMenuItem.add(loadConfigMenuItem);

        showExpertConfigMenuItem.setText("Show Expert Configuration Window");
        configurationMenuItem.add(showExpertConfigMenuItem);

        menuBar.add(configurationMenuItem);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(logControlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(computingResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(gamesNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(runOnLabel)
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(simulateChoiceRadio)
                                                        .addComponent(realChoiceRadio))
                                                .addGap(27, 27, 27)
                                                .addComponent(showTopologyButton))
                                        .addComponent(gamesLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(computingResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(gamesNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(gamesLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(showTopologyButton))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(runOnLabel))
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(simulateChoiceRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(realChoiceRadio)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logControlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulateChoiceRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateChoiceRadioActionPerformed

    }//GEN-LAST:event_simulateChoiceRadioActionPerformed

    private void showLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLogButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showLogButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup chooseRunRadioGroup;
    private javax.swing.JLabel computingResourcesLabel;
    private javax.swing.JPanel computingResourcesPanel;
    private javax.swing.JTree computingResourcesTree;
    private javax.swing.JMenu configurationMenuItem;
    private javax.swing.JPanel decisionTimePanel;
    private javax.swing.JPanel energyConsumptionPanel;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JPanel gamesLogoPanel;
    private javax.swing.JLabel gamesNameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem loadConfigMenuItem;
    private javax.swing.JPanel logBasePanel;
    private javax.swing.JPanel logControlPane;
    private javax.swing.JLabel logLabel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton realChoiceRadio;
    private javax.swing.JLabel runOnLabel;
    private javax.swing.JMenuItem showExpertConfigMenuItem;
    private javax.swing.JToggleButton showLogButton;
    private javax.swing.JButton showTopologyButton;
    private javax.swing.JRadioButton simulateChoiceRadio;
    private javax.swing.JScrollPane workLoadScheduleScrollPane;
    private javax.swing.JTree workloadScheduleJTree;
    // End of variables declaration//GEN-END:variables


//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                List<ServerDto> dtos = new ArrayList<ServerDto>();
//                ServerDto dto_1 = new ServerDto();
//                dto_1.setServerName("Server_1");
//                dto_1.setCoreCount(2);
//                dto_1.setTotalCPU(2000);
//                dto_1.setTotalMemory(1024);
//
//                ServerDto dto_2 = new ServerDto();
//
//                dto_2.setServerName("Server_2");
//                dto_2.setCoreCount(2);
//                dto_2.setTotalCPU(2000);
//                dto_2.setTotalMemory(1024);
//
//                MainWindow mainWindow = new MainWindow(agent);
//                mainWindow.setVisible(true);
//                mainWindow.addComputingResource(dto_1);
//                mainWindow.addComputingResource(dto_2);
//
//
//                Collection<TaskDto> strings = new ArrayList<TaskDto>();
//
//                TaskDto taskDto = new TaskDto();
//                taskDto.setTaskName("Activity_1");
//                taskDto.setRequestedCPUMax(500);
//                taskDto.setRequestedCPUMin(100);
//                taskDto.setRequestedMemoryMax(500);
//                taskDto.setRequestedMemoryMin(100);
//                taskDto.setRequestedStorageMax(500);
//                taskDto.setRequestedStorageMin(100);
//
//                taskDto.setRequestedCores(2);
//
//
// //                mainWindow.setApplicationActivities(strings);
//
//                mainWindow.setApplicationActivityStatus(taskDto, "->.. Processing ->.. Activity_1");
//            }
//        });
//    }

//    public static void main(String[] args){
//         System.out.println(Runtime.getRuntime().totalMemory()  - Runtime.getRuntime().freeMemory() );
//        try {
//            Thread.sleep(500000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

    public void update(Observable o, Object arg) {
        Object[] data = (Object[]) arg;
        Object dataType = data[0];
        if (dataType.equals("Log")) {
            this.logMessage(data[1].toString());
        } else if (dataType.equals("Tasks added")) {
            List<TaskDto> availableTasks = (List<TaskDto>) data[1];
            setApplicationActivities(availableTasks);
            refreshApplicationActivities();
        } else if (dataType.equals("Servers added")) {
            refreshComputingResourcesTree();
        } else if (data[0].equals("Running time")) {
            refreshEnergyEstimate();
            decisionTime = ((Long) data[1]).intValue();
        } else if (dataType.equals("TaskStatusChanged")) {
            List<String> names = (List<String>) data[1];
            for (TaskDto taskDto : applicationActivitiesList.keySet()) {
                boolean taskMarked = false;
                for (String name : names) {
                    if (taskDto.getTaskName().equals(name)) {
                        setApplicationActivityStatus(taskDto, "->>..Received ->>.." + taskDto.getTaskName());
                        names.remove(name);
                        taskMarked = true;
                        break;
                    }
                }
                if (!taskMarked) {
                    setApplicationActivityStatus(taskDto, taskDto.getTaskName());
                }
            }
        }
    }
}
