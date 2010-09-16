package gui.newgui;

import model.impl.util.ModelAccess;
import model.interfaces.resources.applications.ApplicationActivity;
import org.apache.log4j.jmx.Agent;
import utils.misc.Pair;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 4:27:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkloadSchedulerController {
    private Agent agent;
    private WorkloadTreeDisplay workloadTreeDisplay;
    private ModelAccess modelAccess;
    private List<Pair<String, Pair<Integer, Integer>>> schedule;
    private TableModel scheduleTableModel;

    public WorkloadSchedulerController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
        List<TaskDto> availableTasks = new ArrayList<TaskDto>();
        schedule = new ArrayList<Pair<String, Pair<Integer, Integer>>>();
        workloadTreeDisplay = new WorkloadTreeDisplay(availableTasks);
        refreshAvailableTasks();
        initTableModel();
    }

    public List<Pair<String, Pair<Integer, Integer>>> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Pair<String, Pair<Integer, Integer>>> schedule) {
        this.schedule.clear();
        this.schedule.addAll(schedule);
    }

    private void initTableModel() {
        scheduleTableModel = new AbstractTableModel() {

            public int getRowCount() {
                return schedule.size();
            }

            public int getColumnCount() {
                return 3;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Pair<String, Pair<Integer, Integer>> pair = schedule.get(rowIndex);
                Object value = null;
                switch (columnIndex) {
                    case 0:
                        value = pair.getFirst();
                        break;
                    case 1:
                        value = pair.getSecond().getFirst();
                        break;
                    case 2:
                        value = pair.getSecond().getSecond();
                        break;
                }
                return value;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public String getColumnName(int columnIndex) {
                String value = "";
                switch (columnIndex) {
                    case 0:
                        value = "Activity name";
                        break;
                    case 1:
                        value = "Creation time";
                        break;
                    case 2:
                        value = "Destroy time";
                        break;
                }
                return value;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex > 0;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 1:
                        schedule.get(rowIndex).getSecond().setFirst(new Integer(aValue.toString()));
                        break;
                    case 2:
                        schedule.get(rowIndex).getSecond().setSecond(new Integer(aValue.toString()));
                        break;
                }
            }
        };
    }

    public void duplicateRow(int rowIndex) {
        if (rowIndex < 0) {
            return;
        }
        schedule.add(rowIndex, schedule.get(rowIndex));
    }

    public void deleteSelected(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= schedule.size()) {
            return;
        }
        schedule.remove(rowIndex);
    }

    public void scheduleSelectedTasks(int scheduleDelay, int destroyTime) {
        TreePath[] paths = workloadTreeDisplay.getTree().getSelectionPaths();
        if (paths == null) {
            return;
        }
        for (TreePath path : paths) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node.isLeaf() && !node.isRoot()) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                schedule.add(new Pair<String, Pair<Integer, Integer>>(parent.toString(), new Pair<Integer, Integer>(scheduleDelay, destroyTime)));
            } else {
                schedule.add(new Pair<String, Pair<Integer, Integer>>(node.toString(), new Pair<Integer, Integer>(scheduleDelay, destroyTime)));
            }
        }
    }


    public JTree getAvailableTasksTree() {
        return workloadTreeDisplay.getTree();
    }

    public List<Pair<String, Integer>> getScheduledTasksFor(int scheduleTime) {
        List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
        for (Pair<String, Pair<Integer, Integer>> pair : schedule) {
            if (pair.getSecond().getFirst().equals(scheduleTime)) {
                list.add(new Pair<String, Integer>(pair.getFirst(), pair.getSecond().getSecond()));
            }
        }
        return list;
    }

    public void refreshAvailableTasks() {
        new Thread() {
            @Override
            public void run() {

                List<TaskDto> availableTasks = new ArrayList<TaskDto>();
                Collection<ApplicationActivity> activities = modelAccess.getAllApplicationActivityInstances();
                for (ApplicationActivity activity : activities) {
                    if (activity.getLocalName().matches("Template_[0-9]*")) {
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
                workloadTreeDisplay.setTasks(availableTasks);
            }

        }.start();
    }

    public TableModel getScheduleTableModel() {
        return scheduleTableModel;
    }


}
