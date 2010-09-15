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
import globalLoop.utils.GlobalVars;
import gui.energyConsumption.EnergyConsumption;
import gui.energyConsumption.EnergyConsumptionFactory;
import gui.resourceMonitor.resourceMonitorPlotter.impl.MultipleResourceMonitorXYChartPlotter;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorBarChartPlotter;
import gui.resourceMonitor.resourceMonitorPlotter.impl.ResourceMonitorXYChartPlotter;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.misc.Pair;
import utils.fileIO.ConfigurationFileIO;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

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

    private ServerConfigurationController serverConfigurationController;
    private TaskConfigurationController taskConfigurationController;
    private WorkloadSchedulerController workloadSchedulerController;
    private JFileChooser fileChooser;

    private Timer scheduleTimer;

    private int scheduleCount = 0;
    private List<Pair<String, Integer>> schedule;

    private ExpertConfigurationGUIController controller;

//    private List<ServerDto> computingResourcesList;
    private Map<TaskDto, String> applicationActivitiesList;

    FileFilter generalConfigFilter = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.getName().endsWith("general_config");
        }

        @Override
        public String getDescription() {
            return "Complete Configuration Files (.general_config)";
        }
    };


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
                workloadScheduleJTree = new JTree(root);
//                DefaultTreeModel model = (DefaultTreeModel) workloadScheduleJTree.getModel();
//                model.setRoot(root);
                workLoadScheduleScrollPane.setViewportView(workloadScheduleJTree);
                workLoadScheduleScrollPane.repaint();
            }
        };
        thread.start();
    }


    private void refreshComputingResourcesTree() {

        Thread refreshThread = new Thread() {
            @Override
            public void run() {

                chartsPanel.remove(computingResourcesTree);
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
        selfReference = this;
        initComponents();
        initComponents_2();

    }

    private void initComponents_2() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./logo_games.jpg");
        ImageIcon icon = new ImageIcon(image.getScaledInstance(imageLogoPanel.getWidth(), imageLogoPanel.getHeight(), Image.SCALE_SMOOTH));
        JLabel label = new JLabel();
        label.setIcon(icon);
        imageLogoPanel.setLayout(new BorderLayout());
        imageLogoPanel.add(label, "Center");
        Image image_1 = toolkit.getImage("./bus_architecture.png");
        ImageIcon icon_1 = new ImageIcon(image_1.getScaledInstance(imageLogoPanel1.getWidth(), imageLogoPanel1.getHeight(), Image.SCALE_SMOOTH));
        JLabel label_1 = new JLabel();
        label_1.setIcon(icon_1);
        imageLogoPanel1.setLayout(new BorderLayout());
        imageLogoPanel1.add(label_1, "Center");

        createDecisionTimeChart();
        createEnergyConsumptionCharts();
        ButtonGroup group = new ButtonGroup();
        group.add(simulateChoiceRadio);
        group.add(realChoiceRadio);

        workloadSchedulerController = new WorkloadSchedulerController(modelAccess);
        serverConfigurationController = new ServerConfigurationController(agent);
        taskConfigurationController = new TaskConfigurationController(agent);


        final ActionListener scheduleTimerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (schedule == null) {
                    return;
                }
                List<String> scheduledTasks = new ArrayList<String>();
                for (Pair<String, Integer> entry : schedule) {
                    if (entry.getSecond().equals(scheduleCount)) {
                        scheduledTasks.add(entry.getFirst());
                    }
                }
                List<TaskDto> availableTasks = new ArrayList<TaskDto>();
//                Collection<ApplicationActivity> activities = modelAccess.getAllApplicationActivityInstances();
                String taskNames = "";
                for (String name : scheduledTasks) {
                    taskNames += name + ", ";
                    ApplicationActivity activity = modelAccess.getApplicationActivity(name);
                    //TODO; remove if other solution for templates implemented
                    if (activity.getLocalName().toLowerCase().contains("template")) {

                        TaskDto taskDto = new TaskDto();
                        taskDto.setTaskName(activity.getLocalName());
                        taskDto.setRequestedCores((int) activity.getNumberOfCoresRequiredValue());
                        taskDto.setRequestedCPUMax((int) activity.getCpuRequiredMaxValue());
                        taskDto.setRequestedCPUMin((int) activity.getCpuRequiredMinValue());
                        taskDto.setRequestedMemoryMax((int) activity.getMemRequiredMaxValue());
                        taskDto.setRequestedMemoryMin((int) activity.getMemRequiredMinValue());
                        taskDto.setRequestedStorageMax((int) activity.getHddRequiredMaxValue());
                        taskDto.setRequestedStorageMin((int) activity.getHddRequiredMinValue());

                        availableTasks.add(taskDto);
                    }
                }

//                if (scheduledTasks.size() > 0) {
//                    JOptionPane.showMessageDialog(null, "Tasks: " + taskNames + " added");
//                }

                jade.lang.acl.ACLMessage msg = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
                try {
                    msg.setContentObject(new Object[]{"Create clones", scheduledTasks});
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
                agent.send(msg);


                scheduleCount++;
            }
        };

        scheduleTimer = new Timer(1000, scheduleTimerListener);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(generalConfigFilter);

        File file = new File("./testConfigurations");
        if (file.exists()) {
            fileChooser.setCurrentDirectory(file);
        } else {
            fileChooser.setCurrentDirectory(new File("."));
        }

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scheduleTimer.setDelay(1000);
                scheduleTimer.start();
            }
        });
        pauseButton.setText("Pause");

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (scheduleTimer != null) {
                    if (scheduleTimer.isRunning()) {
                        scheduleTimer.stop();
                        if (controller != null) {
                            controller.resetScheduleCount();
                        }
                        pauseButton.setText("Resume");
                    } else {
                        scheduleTimer.start();
                        pauseButton.setText("Pause");
                    }
                }
            }
        });
        restartButton.setText("Reset");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                try {
                    msg.setContentObject(new Object[]{"Delete all"});
                } catch (IOException ex) {
                    ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
                agent.send(msg);
                serverConfigurationController.generateEntities(agent);
                taskConfigurationController.generateEntities(agent);
                List<TaskDto> availableTasks = new ArrayList<TaskDto>();
                setApplicationActivities(availableTasks);
                refreshComputingResourcesTree();
                scheduleCount = 0;
            }
        });

        loadConfigMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] data;


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

                        schedule = (List<Pair<String, Integer>>) data[2];
                        workloadSchedulerController.setSchedule(schedule);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Invalid file", "Load error", JOptionPane.WARNING_MESSAGE);
                        e1.printStackTrace();
                    }
                }
            }

        });

        simulateChoiceRadio.setSelected(true);
        simulateChoiceRadio.addActionListener(new

                ActionListener() {
                    public void actionPerformed
                            (ActionEvent
                                    e) {
                        ProxyFactory.setReturnStub(isSimulationChosen());
                    }
                }

        );

        realChoiceRadio.addActionListener(new

                ActionListener() {
                    public void actionPerformed
                            (ActionEvent
                                    e) {
                        ProxyFactory.setReturnStub(isSimulationChosen());
                    }
                }

        );

        ActionListener logButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (logTextArea.isVisible()) {
                    showLogButton.setText("Show log");
                    logTextArea.setVisible(false);
                    logBasePanel.remove(logScrollPane);
                    logBasePanel.repaint();
                    selfReference.setSize(selfReference.getWidth(), selfReference.getHeight() - logBasePanel.getHeight());
                } else {
                    showLogButton.setText("Hide log");
                    logTextArea.setVisible(true);
                    logBasePanel.add(logScrollPane);
                    logScrollPane.setViewportView(logTextArea);
                    logBasePanel.repaint();
                    selfReference.setSize(selfReference.getWidth(), selfReference.getHeight() + logBasePanel.getHeight());
                }
            }
        };
        showLogButton.setText("Hide Log");
        showLogButton.addActionListener(logButtonListener);
        logButtonListener.actionPerformed(new

                ActionEvent(this, 0, "")

        );


        showExpertConfigMenuItem.addActionListener(new

                ActionListener() {
                    public void actionPerformed
                            (ActionEvent
                                    e) {
                        ExpertConfigurationGUI gui = new ExpertConfigurationGUI();
                        workloadSchedulerController.refreshAvailableTasks();

                        controller = new ExpertConfigurationGUIController(agent, modelAccess, gui,
                                serverConfigurationController, taskConfigurationController,
                                workloadSchedulerController, scheduleTimer);
                        gui.setVisible(true);
                    }
                }

        );

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
        final MultipleResourceMonitorXYChartPlotter plotter1 =
                new MultipleResourceMonitorXYChartPlotter("Energy Consumption",
                        new String[]{"With GAMES infrastructure", "Without GAMES infrastructure"},
                        "Time(s)", "Energy Consumed (W)", 0, 200);
        final ResourceMonitorBarChartPlotter barChartPlotter = new ResourceMonitorBarChartPlotter("Energy Reduction", "Improvement", 0, 100);

        plotter1.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);
        barChartPlotter.setSnapshotIncrement(decisionTimeRefreshRateInMillis / 1000);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                plotter1.setCurrentValue(new Object[]{energyEstimateWithAlg, energyEstimateWithoutAlg});
                if (energyEstimateWithoutAlg > 0) {
                    barChartPlotter.setCurrentValue(100 - (energyEstimateWithAlg * 100) / energyEstimateWithoutAlg);
                } else {
                    barChartPlotter.setCurrentValue(0);
                }
            }
        };
        Timer refreshTimerEnergyConsumption = new Timer(decisionTimeRefreshRateInMillis, actionListener);
        refreshTimerEnergyConsumption.start();
        energyEfficiencyXYPanel.add(plotter1.getGraphPanel(), "Center");
//        JPanel panel = barChartPlotter.getGraphPanel();
//        panel.setSize(energyConsumptionPanel.getWidth() / 3, energyConsumptionPanel.getHeight());
        energyEfficiencyBarPanel.add(barChartPlotter.getGraphPanel(), "Center");


    }

    public void addFileMenuAction(AbstractAction abstractAction) {
        fileMenuItem.add(abstractAction);
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
        java.awt.GridBagConstraints gridBagConstraints;

        chooseRunRadioGroup = new javax.swing.ButtonGroup();
        logControlPane = new javax.swing.JPanel();
        logLabel = new javax.swing.JLabel();
        showLogButton = new javax.swing.JToggleButton();
        logBasePanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        simulateChoiceRadio = new javax.swing.JRadioButton();
        runOnLabel = new javax.swing.JLabel();
        realChoiceRadio = new javax.swing.JRadioButton();
        restartButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();
        chartsPanel = new javax.swing.JPanel();
        energyConsumptionPanel = new javax.swing.JPanel();
        energyEfficiencyXYPanel = new javax.swing.JPanel();
        energyEfficiencyBarPanel = new javax.swing.JPanel();
        energyEfficiencyLabel = new javax.swing.JLabel();
        decisionTimePanel = new javax.swing.JPanel();
        treesPanel = new javax.swing.JPanel();
        computingResourcesPanel = new javax.swing.JPanel();
        computingResourcesScroolPanel = new javax.swing.JScrollPane();
        computingResourcesTree = new javax.swing.JTree();
        computingResourcesLabel = new javax.swing.JLabel();
        workloadSchedulePanel = new javax.swing.JPanel();
        workLoadScheduleScrollPane = new javax.swing.JScrollPane();
        workloadScheduleJTree = new javax.swing.JTree();
        workloadScheduleLabel = new javax.swing.JLabel();
        upperPanel = new javax.swing.JPanel();
        imageLogoPanel = new javax.swing.JPanel();
        imageLogoPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        configurationMenuItem = new javax.swing.JMenu();
        loadConfigMenuItem = new javax.swing.JMenuItem();
        showExpertConfigMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frame"); // NOI18N

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

        simulateChoiceRadio.setText("simulated service center");
        simulateChoiceRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateChoiceRadioActionPerformed(evt);
            }
        });

        runOnLabel.setText("Run on");

        realChoiceRadio.setText("real service center");

        restartButton.setText("Restart");

        pauseButton.setText("Stop");

        startButton.setText("Start");

        javax.swing.GroupLayout logControlPaneLayout = new javax.swing.GroupLayout(logControlPane);
        logControlPane.setLayout(logControlPaneLayout);
        logControlPaneLayout.setHorizontalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 975, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addComponent(logLabel)
                                .addGap(10, 10, 10)
                                .addComponent(showLogButton)
                                .addGap(18, 18, 18)
                                .addComponent(runOnLabel)
                                .addGap(6, 6, 6)
                                .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(simulateChoiceRadio)
                                        .addComponent(realChoiceRadio))
                                .addGap(29, 29, 29)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        logControlPaneLayout.setVerticalGroup(
                logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logControlPaneLayout.createSequentialGroup()
                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(logControlPaneLayout.createSequentialGroup()
                                                .addComponent(simulateChoiceRadio)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(realChoiceRadio))
                                        .addGroup(logControlPaneLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(runOnLabel)
                                        .addComponent(showLogButton)
                                        .addComponent(logLabel)))))
                                .addGroup(logControlPaneLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(logControlPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(startButton)
                                .addComponent(pauseButton)
                                .addComponent(restartButton))))
                        .addGap(18, 18, 18)
                        .addComponent(logBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(37, Short.MAX_VALUE))
        );

        centerPanel.setLayout(new java.awt.GridBagLayout());

        chartsPanel.setLayout(new java.awt.GridBagLayout());

        energyConsumptionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        energyEfficiencyXYPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        energyEfficiencyXYPanel.setLayout(new java.awt.BorderLayout());

        energyEfficiencyBarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        energyEfficiencyBarPanel.setLayout(new java.awt.BorderLayout());

        energyEfficiencyLabel.setText("     Energy Efficiency Gain");
        energyEfficiencyBarPanel.add(energyEfficiencyLabel, java.awt.BorderLayout.PAGE_START);

        decisionTimePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        decisionTimePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout energyConsumptionPanelLayout = new javax.swing.GroupLayout(energyConsumptionPanel);
        energyConsumptionPanel.setLayout(energyConsumptionPanelLayout);
        energyConsumptionPanelLayout.setHorizontalGroup(
                energyConsumptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, energyConsumptionPanelLayout.createSequentialGroup()
                        .addComponent(energyEfficiencyXYPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(energyEfficiencyBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(decisionTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        energyConsumptionPanelLayout.setVerticalGroup(
                energyConsumptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(energyConsumptionPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(energyConsumptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(energyEfficiencyBarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                        .addComponent(decisionTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                        .addComponent(energyEfficiencyXYPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        chartsPanel.add(energyConsumptionPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        centerPanel.add(chartsPanel, gridBagConstraints);

        treesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        treesPanel.setLayout(new java.awt.GridLayout(1, 2));

        computingResourcesPanel.setLayout(new java.awt.BorderLayout());

        computingResourcesScroolPanel.setViewportView(computingResourcesTree);

        computingResourcesPanel.add(computingResourcesScroolPanel, java.awt.BorderLayout.CENTER);

        computingResourcesLabel.setText("Computing Resources");
        computingResourcesPanel.add(computingResourcesLabel, java.awt.BorderLayout.PAGE_START);

        treesPanel.add(computingResourcesPanel);

        workloadSchedulePanel.setLayout(new java.awt.BorderLayout());

        workLoadScheduleScrollPane.setViewportView(workloadScheduleJTree);

        workloadSchedulePanel.add(workLoadScheduleScrollPane, java.awt.BorderLayout.CENTER);

        workloadScheduleLabel.setText("Workload Schedule");
        workloadSchedulePanel.add(workloadScheduleLabel, java.awt.BorderLayout.PAGE_START);

        treesPanel.add(workloadSchedulePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        centerPanel.add(treesPanel, gridBagConstraints);

        upperPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        upperPanel.setMinimumSize(new java.awt.Dimension(0, 50));

        javax.swing.GroupLayout imageLogoPanelLayout = new javax.swing.GroupLayout(imageLogoPanel);
        imageLogoPanel.setLayout(imageLogoPanelLayout);
        imageLogoPanelLayout.setHorizontalGroup(
                imageLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 377, Short.MAX_VALUE)
        );
        imageLogoPanelLayout.setVerticalGroup(
                imageLogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 87, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout imageLogoPanel1Layout = new javax.swing.GroupLayout(imageLogoPanel1);
        imageLogoPanel1.setLayout(imageLogoPanel1Layout);
        imageLogoPanel1Layout.setHorizontalGroup(
                imageLogoPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 393, Short.MAX_VALUE)
        );
        imageLogoPanel1Layout.setVerticalGroup(
                imageLogoPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 87, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout upperPanelLayout = new javax.swing.GroupLayout(upperPanel);
        upperPanel.setLayout(upperPanelLayout);
        upperPanelLayout.setHorizontalGroup(
                upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upperPanelLayout.createSequentialGroup()
                        .addContainerGap(92, Short.MAX_VALUE)
                        .addComponent(imageLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(imageLogoPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123))
        );
        upperPanelLayout.setVerticalGroup(
                upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(upperPanelLayout.createSequentialGroup()
                        .addGroup(upperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(imageLogoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(imageLogoPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(logControlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(centerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                        .addComponent(upperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addComponent(upperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(centerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logControlPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulateChoiceRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateChoiceRadioActionPerformed

    }//GEN-LAST:event_simulateChoiceRadioActionPerformed

    private void showLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLogButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showLogButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel centerPanel;
    private javax.swing.JPanel chartsPanel;
    private javax.swing.ButtonGroup chooseRunRadioGroup;
    private javax.swing.JLabel computingResourcesLabel;
    private javax.swing.JPanel computingResourcesPanel;
    private javax.swing.JScrollPane computingResourcesScroolPanel;
    private javax.swing.JTree computingResourcesTree;
    private javax.swing.JMenu configurationMenuItem;
    private javax.swing.JPanel decisionTimePanel;
    private javax.swing.JPanel energyConsumptionPanel;
    private javax.swing.JPanel energyEfficiencyBarPanel;
    private javax.swing.JLabel energyEfficiencyLabel;
    private javax.swing.JPanel energyEfficiencyXYPanel;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JPanel imageLogoPanel;
    private javax.swing.JPanel imageLogoPanel1;
    private javax.swing.JMenuItem loadConfigMenuItem;
    private javax.swing.JPanel logBasePanel;
    private javax.swing.JPanel logControlPane;
    private javax.swing.JLabel logLabel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton pauseButton;
    private javax.swing.JRadioButton realChoiceRadio;
    private javax.swing.JButton restartButton;
    private javax.swing.JLabel runOnLabel;
    private javax.swing.JMenuItem showExpertConfigMenuItem;
    private javax.swing.JToggleButton showLogButton;
    private javax.swing.JRadioButton simulateChoiceRadio;
    private javax.swing.JButton startButton;
    private javax.swing.JPanel treesPanel;
    private javax.swing.JPanel upperPanel;
    private javax.swing.JScrollPane workLoadScheduleScrollPane;
    private javax.swing.JTree workloadScheduleJTree;
    private javax.swing.JLabel workloadScheduleLabel;
    private javax.swing.JPanel workloadSchedulePanel;
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
            if (data[1].toString().startsWith("Executing")) {
                refreshEnergyEstimate();
            }
        } else if (dataType.equals("Tasks added")) {
            List<TaskDto> availableTasks = (List<TaskDto>) data[1];
            setApplicationActivities(availableTasks);
        } else if (dataType.equals("Servers added")) {
            refreshComputingResourcesTree();
        } else if (data[0].equals("Running time")) {
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
