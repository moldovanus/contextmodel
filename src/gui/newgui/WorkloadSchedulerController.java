package gui.newgui;

import model.impl.util.ModelAccess;
import model.interfaces.resources.applications.ApplicationActivity;
import org.apache.log4j.jmx.Agent;
import selfoptimizing.utils.Pair;
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
    private List<Pair<String, Integer>> schedule;
    private TableModel scheduleTableModel;

    public WorkloadSchedulerController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
        List<TaskDto> availableTasks = new ArrayList<TaskDto>();
        schedule = new ArrayList<Pair<String, Integer>>();
        workloadTreeDisplay = new WorkloadTreeDisplay(availableTasks);
        refreshAvailableTasks();
        initTableModel();
    }

    private void initTableModel() {
        scheduleTableModel = new AbstractTableModel() {

            public int getRowCount() {
                return schedule.size();
            }

            public int getColumnCount() {
                return 2;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                Pair<String, Integer> pair = schedule.get(rowIndex);
                Object value = null;
                switch (columnIndex) {
                    case 0:
                        value = pair.getFirst();
                        break;
                    case 1:
                        value = pair.getSecond();
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
                        value = "Schedule delay";
                        break;
                }
                return value;
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

    public void scheduleSelectedTasks(int scheduleDelay) {
        TreePath[] paths = workloadTreeDisplay.getTree().getSelectionPaths();
        if (paths == null) {
            return;
        }
        for (TreePath path : paths) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (node.isLeaf() && ! node.isRoot()) {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                schedule.add(new Pair<String, Integer>(parent.toString(), scheduleDelay));
            } else {
                schedule.add(new Pair<String, Integer>(node.toString(), scheduleDelay));
            }
        }
    }

    public JTree getAvailableTasksTree() {
        return workloadTreeDisplay.getTree();
    }

    public List<String> getScheduledTasksFor(int scheduleTime) {
        List<String> list = new ArrayList<String>();
        for (Pair<String, Integer> pair : schedule) {
            if (pair.getSecond().equals(scheduleTime)) {
                list.add(pair.getFirst());
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
                workloadTreeDisplay.setTasks(availableTasks);
            }

        }.start();
    }

    public TableModel getScheduleTableModel() {
        return scheduleTableModel;
    }


}
