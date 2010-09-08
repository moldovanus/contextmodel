package gui.newgui;

import model.impl.util.ModelAccess;
import model.interfaces.resources.applications.ApplicationActivity;
import org.apache.log4j.jmx.Agent;
import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;
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

    public WorkloadSchedulerController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
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
        workloadTreeDisplay = new WorkloadTreeDisplay(availableTasks);

    }

    public JTree getAvailableTasksTree() {
        return workloadTreeDisplay.getTree();
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
}
