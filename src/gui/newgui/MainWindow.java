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
import utils.worldInterface.dtos.ServerDto;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * @author Administrator
 */
public class MainWindow extends javax.swing.JFrame {

    private GUIAgent agent;
    private List<ServerDto> computingResourcesList;
    private Map<TaskDto, String> applicationActivitiesList;

    {
        applicationActivitiesList = new HashMap<TaskDto, String>();
        computingResourcesList = new ArrayList<ServerDto>();
    }

    public void logMessage(String message) {
        logTextArea.append(message + "\n");
    }

    public void setRunButtonActionListener(ActionListener listener) {
        runOnButton.addActionListener(listener);
    }

    public List<ServerDto> getComputingResourcesList() {
        return computingResourcesList;
    }

    public void setComputingResourcesList(List<ServerDto> computingResourcesList) {
        this.computingResourcesList = computingResourcesList;
        refreshComputingResourcesTree();
    }

    public void addComputingResource(ServerDto dto) {
        computingResourcesList.add(dto);
        refreshComputingResourcesTree();
    }

    public void removeComputingResource(ServerDto dto) {
        computingResourcesList.remove(dto);
        refreshComputingResourcesTree();
    }

    public void setApplicationActivities(Collection<TaskDto> activities) {
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

                for (ServerDto dto : computingResourcesList) {
                    DefaultMutableTreeNode serverNode = new DefaultMutableTreeNode(dto.getServerName());
                    {
                        DefaultMutableTreeNode serverCPU = new DefaultMutableTreeNode("CPU");
                        DefaultMutableTreeNode coreCount = new DefaultMutableTreeNode("cores: " + dto.getCoreCount());
                        DefaultMutableTreeNode totalServerCPU = new DefaultMutableTreeNode("total: " + dto.getTotalCPU());

                        serverCPU.add(coreCount);
                        serverCPU.add(totalServerCPU);

                        serverNode.add(serverCPU);
                    }

                    {
                        DefaultMutableTreeNode serverMEM = new DefaultMutableTreeNode("MEM");
                        DefaultMutableTreeNode totalServerCPU = new DefaultMutableTreeNode("total: " + dto.getTotalMemory());

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
     * @param agent
     */
    public MainWindow(GUIAgent agent) {
        this.agent = agent;
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
        showExpertConfigMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExpertConfigurationGUI gui = new  ExpertConfigurationGUI();
                ExpertConfigurationGUIController controller = new ExpertConfigurationGUIController(agent,gui);
                gui.setVisible(true);
            }
        });
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("GAMES Initial Window");

    }

    public void addFileMenuAction(AbstractAction abstractAction){
         fileMenuItem.add(abstractAction);
    }

    private boolean isSimulationChosen() {
        return chooseRunRadioGroup.getSelection().getMnemonic() == 0;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        computingResourcesTree = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        workLoadScheduleScrollPane = new javax.swing.JScrollPane();
        workloadScheduleJTree = new javax.swing.JTree();
        runOnButton = new javax.swing.JButton();
        simulateChoiceRadio = new javax.swing.JRadioButton();
        realChoiceRadio = new javax.swing.JRadioButton();
        logControlPane = new javax.swing.JPanel();
        logLabel = new javax.swing.JLabel();
        showLogButton = new javax.swing.JToggleButton();
        logBasePanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        configurationMenuItem = new javax.swing.JMenu();
        loadConfigMenuItem = new javax.swing.JMenuItem();
        showExpertConfigMenuItem = new javax.swing.JMenuItem();

        chooseRunRadioGroup.add(simulateChoiceRadio);
        chooseRunRadioGroup.add(realChoiceRadio);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame"); // NOI18N


        gamesNameLabel.setText("            GAMES PROJECT TITLE");

        gamesLogoPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout gamesLogoPanelLayout = new javax.swing.GroupLayout(gamesLogoPanel);
        gamesLogoPanel.setLayout(gamesLogoPanelLayout);
        gamesLogoPanelLayout.setHorizontalGroup(
                gamesLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 328, Short.MAX_VALUE)
        );
        gamesLogoPanelLayout.setVerticalGroup(
                gamesLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 243, Short.MAX_VALUE)
        );

        computingResourcesLabel.setText("Computing Resources");

        jScrollPane1.setViewportView(computingResourcesTree);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout computingResourcesPanelLayout = new javax.swing.GroupLayout(computingResourcesPanel);
        computingResourcesPanel.setLayout(computingResourcesPanelLayout);
        computingResourcesPanelLayout.setHorizontalGroup(
                computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                        .addGroup(computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(computingResourcesPanelLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(computingResourcesLabel)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        computingResourcesPanelLayout.setVerticalGroup(
                computingResourcesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, computingResourcesPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(computingResourcesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
        );

        jLabel1.setText("Workload Schedule");

        workLoadScheduleScrollPane.setViewportView(workloadScheduleJTree);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(workLoadScheduleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel1)
                        .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(workLoadScheduleScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        runOnButton.setText("Run on");

        simulateChoiceRadio.setText("simulated service center");
        simulateChoiceRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateChoiceRadioActionPerformed(evt);
            }
        });

        realChoiceRadio.setText("real service center");

        logLabel.setText("Log");

        showLogButton.setText("Hide Log");

        javax.swing.GroupLayout logBasePanelLayout = new javax.swing.GroupLayout(logBasePanel);
        logBasePanel.setLayout(logBasePanelLayout);
        logBasePanelLayout.setHorizontalGroup(
                logBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 851, Short.MAX_VALUE)
        );
        logBasePanelLayout.setVerticalGroup(
                logBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 123, Short.MAX_VALUE)
        );

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        javax.swing.GroupLayout logControlPaneLayout = new javax.swing.GroupLayout(logControlPane);
        logControlPane.setLayout(logControlPaneLayout);
        logControlPaneLayout.setHorizontalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                        .addComponent(logLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(showLogButton)
                                        .addContainerGap())
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
                                .addGap(20, 20, 20))))
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        logControlPaneLayout.setVerticalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(logLabel)
                                        .addComponent(showLogButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE)))
        );

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
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(logControlPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(computingResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(gamesNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(gamesLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(runOnButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(simulateChoiceRadio)
                                        .addComponent(realChoiceRadio))))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(gamesNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(31, 31, 31)
                                                        .addComponent(gamesLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(9, 9, 9)
                                                                .addComponent(runOnButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                        .addComponent(simulateChoiceRadio)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(realChoiceRadio))))
                                                .addGroup(layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addComponent(computingResourcesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(27, 27, 27))
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGap(11, 11, 11)
                        .addComponent(logControlPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulateChoiceRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateChoiceRadioActionPerformed

    }//GEN-LAST:event_simulateChoiceRadioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup chooseRunRadioGroup;
    private javax.swing.JLabel computingResourcesLabel;
    private javax.swing.JPanel computingResourcesPanel;
    private javax.swing.JTree computingResourcesTree;
    private javax.swing.JMenu configurationMenuItem;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JPanel gamesLogoPanel;
    private javax.swing.JLabel gamesNameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem loadConfigMenuItem;
    private javax.swing.JPanel logBasePanel;
    private javax.swing.JPanel logControlPane;
    private javax.swing.JLabel logLabel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JRadioButton realChoiceRadio;
    private javax.swing.JButton runOnButton;
    private javax.swing.JMenuItem showExpertConfigMenuItem;
    private javax.swing.JToggleButton showLogButton;
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
//                mainWindow.setApplicationActivities(strings);
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
}
