package gui.newgui;

import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 3:08:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkloadTreeDisplay {
    private JTree tree;
    private List<TaskDto> tasks;

    public WorkloadTreeDisplay(List<TaskDto> tasks) {
        this.tasks = tasks;
        tree = new JTree();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Activities");
        for (TaskDto taskDto : tasks) {
            DefaultMutableTreeNode task = new DefaultMutableTreeNode(taskDto.getTaskName());
            DefaultMutableTreeNode taskRequestedCores =
                    new DefaultMutableTreeNode("requested cores: " + taskDto.getRequestedCores());
            DefaultMutableTreeNode taskRequestedCpu =
                    new DefaultMutableTreeNode("requested CPU: [ min: " + taskDto.getRequestedCPUMin()
                            + ",max: " + taskDto.getRequestedCPUMax() + "]");
            DefaultMutableTreeNode taskRequestedMem =
                    new DefaultMutableTreeNode("requested MEM: [ min: " + taskDto.getRequestedMemoryMin()
                            + ",max: " + taskDto.getRequestedMemoryMax() + "]");
            DefaultMutableTreeNode taskRequestedHdd =
                    new DefaultMutableTreeNode("requested HDD: [ min: " + taskDto.getRequestedStorageMin()
                            + ",max: " + taskDto.getRequestedStorageMax() + "]");

            task.add(taskRequestedCores);
            task.add(taskRequestedCpu);
            task.add(taskRequestedMem);
            task.add(taskRequestedHdd);
            root.add(task);
        }
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.setRoot(root);
    }

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Activities");
        for (TaskDto taskDto : tasks) {
            DefaultMutableTreeNode task = new DefaultMutableTreeNode(taskDto.getTaskName());
            DefaultMutableTreeNode taskRequestedCores =
                    new DefaultMutableTreeNode("requested cores: " + taskDto.getRequestedCores());
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
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.setRoot(root);
    }


}
