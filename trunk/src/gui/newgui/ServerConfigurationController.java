package gui.newgui;

import gui.datacenterConfiguration.impl.ServerConfigurator;
import jade.core.Agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 12:24:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConfigurationController {

    private final String ADD_ROW_TOOLTIP = "Inserts an empty row in the table";
    private final String DUPLICATE_ROW_TOOLTIP = "Creates an identical row with the selected row";
    private final String REMOVE_ROW_TOOLTIP = "Deletes the selected row from the table";
    private final String CREATE_SERVER_INSTANCES = "<html>Creates server instances in the context model based on the provided specifications" +
            "<br><b>WARNING:</b>It will erase all servers from the context model and generate new ones.</br></html>";

    private JPanel serverConfigurationPanel;
    private JPanel serverConfigurationButtonsPanel;
    private JButton addServerRowButton;
    private JButton duplicateServerRowButton;
    private JButton removeServerRowButton;
    private JButton generateServersButton;


    private ServerConfigurator serverConfigurator;

    private Agent agent;

    public ServerConfigurationController(Agent agent) {
        this.agent = agent;
        serverConfigurator = new ServerConfigurator();
        initComponents();
    }

    private void initComponents() {

        serverConfigurationPanel = new JPanel();
        addServerRowButton = new JButton("Add row");
        duplicateServerRowButton = new JButton("Duplicate row");
        removeServerRowButton = new JButton("Remove selected");
        generateServersButton = new JButton("Create Server Instances");

        addServerRowButton.setToolTipText(ADD_ROW_TOOLTIP);
        duplicateServerRowButton.setToolTipText(DUPLICATE_ROW_TOOLTIP);
        removeServerRowButton.setToolTipText(REMOVE_ROW_TOOLTIP);
        generateServersButton.setToolTipText(CREATE_SERVER_INSTANCES);

        addServerRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                serverConfigurator.insertEmptyRow();
            }
        });
        duplicateServerRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                serverConfigurator.duplicateSelectedRow();
            }
        });

        removeServerRowButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                serverConfigurator.removeRow();
            }
        });
        generateServersButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                serverConfigurator.createEntities(agent);
            }
        });

        serverConfigurationPanel.setLayout(new BorderLayout());
        serverConfigurationButtonsPanel = new JPanel();
        serverConfigurationButtonsPanel.setLayout(new GridBagLayout());
        serverConfigurationButtonsPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        serverConfigurationPanel.add(new JLabel("Datacenter configuration: servers information and optimum load ranges"), BorderLayout.NORTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        serverConfigurationButtonsPanel.add(addServerRowButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        serverConfigurationButtonsPanel.add(duplicateServerRowButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        serverConfigurationButtonsPanel.add(removeServerRowButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        serverConfigurationButtonsPanel.add(generateServersButton, constraints);

        serverConfigurationPanel.add(serverConfigurator.getTablePane(), BorderLayout.CENTER);
        serverConfigurationPanel.add(serverConfigurationButtonsPanel, BorderLayout.SOUTH);

    }

    public JPanel getConfigurationPanel() {
        return serverConfigurationPanel;
    }

    public java.util.List<String[]> getTableData() {
        return serverConfigurator.getTableData();
    }

    public void setTableData(java.util.List<String[]> data) {
        serverConfigurator.setTableData(data);
    }

    public void generateEntities(Agent a) {
        serverConfigurator.createEntities(a);
    }
}
