package gui.datacenterConfiguration.impl;

import globalLoop.utils.GlobalVars;
import gui.datacenterConfiguration.AbstractConfigurator;
import gui.datacenterConfiguration.IServerTableModel;
import gui.datacenterConfiguration.TableCellValueValidator;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import main.PelletJena;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.util.ModelAccess;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ServerDto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: Jun 18, 2010
 * Time: 1:16:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConfigurator extends AbstractConfigurator {

    private ServerInfoTableModel tableModel;


    private class ServerInfoTableModel extends AbstractTableModel implements IServerTableModel {

        private String[] columnNames;
        private ArrayList<String[]> rowsData;
        private Map<String, TableCellValueValidator> cellValueValidators;

        private ServerInfoTableModel() {
            columnNames = new String[]{
                    IServerTableModel.SERVER_NAME, IServerTableModel.SERVER_IP_ADDR,
                    IServerTableModel.SERVER_MAC_ADDR, IServerTableModel.SERVER_STORAGE_PATH,
                    IServerTableModel.SERVER_CORE_COUNT, IServerTableModel.SERVER_TOTAL_CPU,
                    IServerTableModel.SERVER_MIN_CPU, IServerTableModel.SERVER_MAX_CPU,
                    IServerTableModel.SERVER_TOTAL_MEMORY,
                    IServerTableModel.SERVER_MIN_MEMORY, IServerTableModel.SERVER_MAX_MEMORY,
                    IServerTableModel.SERVER_TOTAL_STORAGE,
                    IServerTableModel.SERVER_MIN_STORAGE, IServerTableModel.SERVER_MAX_STORAGE
            };
            rowsData = new ArrayList<String[]>(1);
            cellValueValidators = new HashMap<String, TableCellValueValidator>();

            TableCellValueValidator stringValuesValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[\\w]+");
                }
            };

            TableCellValueValidator integerValuesValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[0-9]+");
                }
            };

            TableCellValueValidator ipAddressValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
                }
            };
            TableCellValueValidator macAddressValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[0-9a-fA-F]+\\-[0-9a-fA-F]+\\-[0-9a-fA-F]+\\-[0-9a-fA-F]+\\-[0-9a-fA-F]+\\-[0-9a-fA-F]+");
                }
            };

            TableCellValueValidator storagePathValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0;
                }
            };

            cellValueValidators.put(IServerTableModel.SERVER_NAME, stringValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_IP_ADDR, ipAddressValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MAC_ADDR, macAddressValidator);
            cellValueValidators.put(IServerTableModel.SERVER_STORAGE_PATH, storagePathValidator);
            cellValueValidators.put(IServerTableModel.SERVER_CORE_COUNT, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_TOTAL_CPU, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MIN_CPU, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MAX_CPU, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_TOTAL_MEMORY, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MIN_MEMORY, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MAX_MEMORY, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_TOTAL_STORAGE, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MIN_STORAGE, integerValuesValidator);
            cellValueValidators.put(IServerTableModel.SERVER_MAX_STORAGE, integerValuesValidator);

        }

        public int getRowCount() {
            return rowsData.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return rowsData.get(rowIndex)[columnIndex];
        }

        public String getColumnName(int column) {
            return columnNames[column];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (rowIndex != -1) {
                TableCellValueValidator validator = cellValueValidators.get(columnNames[columnIndex]);
                if (validator.validateValue((String) aValue)) {
                    rowsData.get(rowIndex)[columnIndex] = (String) aValue;
                }
            }
        }

        public void insertEmptyRow() {
            int fieldCount = columnNames.length;
            String[] newRow = new String[fieldCount];
            for (int i = 0; i < fieldCount; i++) {
                newRow[i] = "";
            }
            rowsData.add(newRow);
            configurationTable.repaint();

        }

        public void duplicateSelectedRow() {
            int selectedRow = configurationTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            String[] data = new String[columnNames.length];
            System.arraycopy(rowsData.get(selectedRow), 0, data, 0, columnNames.length);
            rowsData.add(data);
            configurationTable.repaint();
        }

        public void removeRow() {
            int selectedRow = configurationTable.getSelectedRow();
            if (selectedRow != -1 && selectedRow < rowsData.size()) {
                configurationTable.setEditingRow(-1);
                rowsData.remove(selectedRow);
                configurationTable.repaint();
            }
        }

        public void createServerEntities(Agent agent) {
            int rowsCount = rowsData.size();
            for (int j = 0; j < rowsCount; j++) {
                String[] data = rowsData.get(j);
                int dataLength = data.length;
                for (int i = 0; i < dataLength; i++) {
                    if (data[i].length() == 0) {
                        JOptionPane.showMessageDialog(null, "Column " + i + " from row " + j + " is empty. Operation not performed.", "Incomplete specification", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            List<ExtendedServerDto> serverDtos = new ArrayList<ExtendedServerDto>();

            for (String[] data : rowsData) {
                String serverName = data[0].trim();
                ExtendedServerDto dto = new ExtendedServerDto();
                dto.setServerName(serverName);
                dto.setIpAddress(data[1].trim());
                dto.setMacAddress(data[2].trim());
                dto.setMaximumCPU(Integer.parseInt(data[5].trim()));
                dto.setOptimumCPU(Integer.parseInt(data[6].trim()));
                dto.setCoreCount(Integer.parseInt(data[4].trim()));
                dto.setMaximumMemory(Integer.parseInt(data[8].trim()));
                dto.setOptimumMemory(Integer.parseInt(data[9].trim()));
                dto.setMaximumStorage(Integer.parseInt(data[11].trim()));
                dto.setOptimumStorage(Integer.parseInt(data[12].trim()));
                serverDtos.add(dto);
            }

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            try {
                msg.setContentObject(new Object[]{"Servers added", serverDtos});
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
            agent.send(msg);

        }

        public List<String[]> getTableData() {
            return rowsData;
        }

        public void setTableData(List<String[]> data) {
            rowsData.clear();
            rowsData.addAll(data);
        }

        public void startEditSelectedRow() {
            configurationTable.setEditingRow(configurationTable.getSelectedRow());
        }
    }

    public ServerConfigurator( ) {
        configurationTable = new JTable();
        tableModel = new ServerInfoTableModel();
        configurationTable.setModel(tableModel);

        columnsToolTips = new HashMap<String, String>();
        columnsToolTips.put(IServerTableModel.SERVER_NAME, "<html>The name with which the server will be reffered . " +
                "<br>Accepted values :   <b><code>alphabetic characters a-z A-Z</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_IP_ADDR, "<html>The IP address of the server." +
                "<br>Accepted values : <b><code>[0-9].[0-9].[0-9].[0-9]</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MAC_ADDR, "<html>The MAC adress of the server network card, used in the wake up process" +
                ".<br>Accepted values :  <b><code>[0-9a-z]-[0-9a-z]-[0-9a-z]-[0-9a-z]-[0-9a-z]-[0-9a-z]</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_STORAGE_PATH, "<html>The path where the virtual machines will be stored ." +
                "<br>Accepted values :   <b><code>path format accepted by any file explorer. example: C:\\VirtualMachines</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MIN_CPU, "<html>The minimum acceptable CPU load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MAX_CPU, "<html>The maximum acceptable CPU load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MAX_MEMORY, "<html>The minimum acceptable Memory load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MIN_MEMORY, "<html>The maximum acceptable Memory load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b><</br>/html>");
        columnsToolTips.put(IServerTableModel.SERVER_MAX_STORAGE, "<html>The minimum acceptable Storage load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(IServerTableModel.SERVER_MIN_STORAGE, "<html>The maximum acceptable Storage load according to green load indicators  ." +
                "<br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");

        TableColumnModel columnModel = configurationTable.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            TableColumn column = columnModel.getColumn(i);
            renderer.setToolTipText(columnsToolTips.get((String) column.getHeaderValue()));
            column.setCellRenderer(renderer);
        }
        tablePane = new JScrollPane(configurationTable);
        configurationTable.setFillsViewportHeight(true);

    }


    public void insertEmptyRow() {
        tableModel.insertEmptyRow();
    }

    public void duplicateSelectedRow() {
        tableModel.duplicateSelectedRow();
    }

    public void removeRow() {
        tableModel.removeRow();
    }

    public List<String[]> getTableData() {
        return tableModel.getTableData();
    }

    public void setTableData(List<String[]> data) {
        tableModel.setTableData(data);
    }

    public void createEntities(Agent agent) {
        tableModel.createServerEntities(agent);
    }

//    public static void main(String[] main) {
//        new ServerConfigurator();
//    }
}