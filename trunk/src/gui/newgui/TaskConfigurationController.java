package gui.newgui;

import gui.datacenterConfiguration.AbstractConfigurator;
import gui.datacenterConfiguration.impl.TaskConfigurator;
import jade.core.Agent;
import model.impl.util.ModelAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 12:49:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskConfigurationController {
    private final String ADD_ROW_TOOLTIP = "Inserts an empty row in the table";
    private final String DUPLICATE_ROW_TOOLTIP = "Creates an identical row with the selected row";
    private final String REMOVE_ROW_TOOLTIP = "Deletes the selected row from the table";
    private final String CREATE_VIRTUAL_MACHINES_TOOLTIP = "<html>Creates virtual machines based on an existing virtual machine with the specified resource requirements." +
            "<br><b>WARNING:</b>It will erase all previous virtual machines from the context model and generate new ones.</br></html>";

    private JPanel taskConfigurationPanel;
    JPanel taskConfigurationButtonsPanel;

    private AbstractConfigurator taskConfigurator;

    private Agent agent;

    private JButton addTaskRowButton;
    private JButton duplicateTaskRowButton;
    private JButton removeTaskRowButton;
    private JButton generateVirtualMachinesButton;

    public TaskConfigurationController(Agent agent) {
        this.agent = agent;
        taskConfigurator = new TaskConfigurator();
        initComponents();
    }

    private void initComponents() {
        taskConfigurationPanel = new JPanel();
        addTaskRowButton = new JButton("Add row");
        duplicateTaskRowButton = new JButton("Duplicate row");
        removeTaskRowButton = new JButton("Remove selected");
        generateVirtualMachinesButton = new JButton("Create Virtual Machines");

        addTaskRowButton.setToolTipText(ADD_ROW_TOOLTIP);
        duplicateTaskRowButton.setToolTipText(DUPLICATE_ROW_TOOLTIP);
        removeTaskRowButton.setToolTipText(REMOVE_ROW_TOOLTIP);
        generateVirtualMachinesButton.setToolTipText(CREATE_VIRTUAL_MACHINES_TOOLTIP);

        addTaskRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                taskConfigurator.insertEmptyRow();
            }
        });

        duplicateTaskRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                taskConfigurator.duplicateSelectedRow();
            }
        });
        removeTaskRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                taskConfigurator.removeRow();
            }
        });

        generateVirtualMachinesButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                taskConfigurator.createEntities(agent);
            }
        });

        taskConfigurationPanel.setLayout(new BorderLayout());
        taskConfigurationButtonsPanel = new JPanel();
        taskConfigurationButtonsPanel.setLayout(new GridBagLayout());
        taskConfigurationPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        taskConfigurationPanel.add(new JLabel("Workload configuration: virtual machines resources requirements"), BorderLayout.NORTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        taskConfigurationButtonsPanel.add(addTaskRowButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        taskConfigurationButtonsPanel.add(duplicateTaskRowButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        taskConfigurationButtonsPanel.add(removeTaskRowButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        taskConfigurationButtonsPanel.add(generateVirtualMachinesButton, constraints);

        taskConfigurationPanel.add(taskConfigurator.getTablePane(), BorderLayout.CENTER);
        taskConfigurationPanel.add(taskConfigurationButtonsPanel, BorderLayout.SOUTH);


    }

    public JPanel getConfigurationPanel() {
        return taskConfigurationPanel;
    }
 
    public java.util.List<String[]> getTableData() {
        return taskConfigurator.getTableData();
    }

    public void setTableData(java.util.List<String[]> data) {
        taskConfigurator.setTableData(data);
    }
}
