package gui.datacenterConfiguration.impl;

import globalLoop.utils.GlobalVars;
import gui.datacenterConfiguration.AbstractConfigurator;
import gui.datacenterConfiguration.ITaskTableModel;
import gui.datacenterConfiguration.TableCellValueValidator;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import main.PelletJena;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.util.ModelAccess;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.applications.Application;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.dtos.ExtendedTaskDto;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: Jun 18, 2010
 * Time: 4:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskConfigurator extends AbstractConfigurator {

    private TaskInfoTableModel tableModel;


    private class TaskInfoTableModel extends AbstractTableModel implements ITaskTableModel {

        private String[] columnNames;
        private ArrayList<String[]> rowsData;
        private Map<String, TableCellValueValidator> cellValueValidators;

        private TaskInfoTableModel() {
            columnNames = new String[]{
                    ITaskTableModel.TASK_NAME, ITaskTableModel.TASK_REQUESTED_CORES_NO,
                    ITaskTableModel.TASK_CPU_WEIGHT, ITaskTableModel.TASK_MEMORY_WEIGHT,
                    ITaskTableModel.TASK_STORAGE_WEIGHT,
                    ITaskTableModel.TASK_MIN_CPU, ITaskTableModel.TASK_MAX_CPU,
                    ITaskTableModel.TASK_MIN_MEMORY, ITaskTableModel.TASK_MAX_MEMORY,
                    ITaskTableModel.TASK_MIN_STORAGE, ITaskTableModel.TASK_MAX_STORAGE
            };
            rowsData = new ArrayList<String[]>(1);

            cellValueValidators = new HashMap<String, TableCellValueValidator>();

            TableCellValueValidator stringValuesValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[\\w]+");
                }
            };

            TableCellValueValidator realValuesValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("0.[1-9]+");
                }
            };

            TableCellValueValidator integerValuesValidator = new TableCellValueValidator() {
                public boolean validateValue(String value) {
                    return value.length() != 0 && value.matches("[0-9]+");
                }
            };

            cellValueValidators.put(ITaskTableModel.TASK_NAME, stringValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_CPU_WEIGHT, realValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_STORAGE_WEIGHT, realValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MEMORY_WEIGHT, realValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_REQUESTED_CORES_NO, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MIN_CPU, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MAX_CPU, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MIN_MEMORY, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MAX_MEMORY, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MIN_STORAGE, integerValuesValidator);
            cellValueValidators.put(ITaskTableModel.TASK_MAX_STORAGE, integerValuesValidator);

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

        public void createTaskEntities(Agent agent) {

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


            List<ExtendedTaskDto> taskDtos = new ArrayList<ExtendedTaskDto>();

            for (String[] data : rowsData) {

                ExtendedTaskDto task = new ExtendedTaskDto();
                task.setTaskName(data[0].trim());
                task.setRequestedCores(Integer.parseInt(data[1].trim()));
                task.setCpuWeight(Float.parseFloat(data[2].trim()));
                task.setMemWeight(Float.parseFloat(data[3].trim()));
                task.setHddWeight(Float.parseFloat(data[4].trim()));
                task.setRequestedCPUMin(Integer.parseInt(data[5].trim()));
                task.setRequestedCPUMax(Integer.parseInt(data[6].trim()));
                task.setRequestedMemoryMin(Integer.parseInt(data[7].trim()));
                task.setRequestedMemoryMax(Integer.parseInt(data[8].trim()));
                task.setRequestedStorageMin(Integer.parseInt(data[9].trim()));
                task.setRequestedStorageMax(Integer.parseInt(data[10].trim()));

                taskDtos.add(task);

            }

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            try {
                msg.setContentObject(new Object[]{"Tasks added", taskDtos});
            } catch (IOException e) {
                e.printStackTrace();
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

    public TaskConfigurator() {

        configurationTable = new JTable();
        tableModel = new TaskInfoTableModel();
        configurationTable.setModel(tableModel);

        columnsToolTips = new HashMap<String, String>();
        columnsToolTips.put(ITaskTableModel.TASK_NAME, "<html>The name with which the task will be reffered.   <br>Accepted values : <b><code>alphabetic characters a-z A-Z</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_REQUESTED_CORES_NO, "<html>The number of cores the task requires to run.   <br>Accepted values : <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_CPU_WEIGHT, "<html>The importance of the CPU for the task.   <br>Accepted values :  <b><code>0.1-0.9</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MEMORY_WEIGHT, "<html>The importance of the Memory for the task.   <br>Accepted values :  <b><code>0.1-0.9</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_STORAGE_WEIGHT, "<html>The importance of the Storage for the task.   <br>Accepted values :   <b><code>0.1-0.9</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MIN_CPU, "<html>The minimum CPU the task needs to run.   <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MAX_CPU, "<html>The maximum CPU it has been payed for.   <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MIN_MEMORY, "<html>The minimum Memory the task needs to run.   <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MAX_MEMORY, "<html>The maximum Memory it has been payed for.   <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MAX_STORAGE, "<html>The minimum Storage the task needs to run.   <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");
        columnsToolTips.put(ITaskTableModel.TASK_MIN_STORAGE, "<html>The maximum Storage it has been payed for.  <br>Accepted values :   <b><code>nonnegative integer</code></b></br></html>");

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
        tableModel.createTaskEntities(agent);
    }

//    public static void main(String[] main) {
//        new TaskConfigurator();
//    }


}
