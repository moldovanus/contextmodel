package utils.contextSituationsAccess;

import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.dtos.ExtendedTaskDto;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:19:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskToDtoMapper {

    private TaskToDtoMapper() {
    }

    public static ExtendedTaskDto map(ApplicationActivity activity) {

        ExtendedTaskDto dto = new ExtendedTaskDto();
        dto.setCpuWeight(activity.getCPUWeight());
        dto.setMemWeight(activity.getMEMWeight());
        dto.setHddWeight(activity.getHDDWeight());
        dto.setReceivedCores((int) activity.getNumberOfCoresAllocatedValue());
        dto.setReceivedCPU((int) activity.getCpuAllocatedValue());
        dto.setReceivedMemory((int) activity.getMemAllocatedValue());
        dto.setReceivedStorage((int) activity.getHddAllocatedValue());
        dto.setRequestedCores((int) activity.getNumberOfCoresRequiredValue());
        dto.setRequestedCPUMin((int) activity.getCpuRequiredMinValue());
        dto.setRequestedCPUMax((int) activity.getCpuRequiredMaxValue());
        dto.setRequestedMemoryMin((int) activity.getMemRequiredMinValue());
        dto.setRequestedMemoryMax((int) activity.getMemRequiredMaxValue());
        dto.setRequestedStorageMin((int) activity.getHddRequiredMinValue());
        dto.setRequestedStorageMax((int) activity.getHddRequiredMaxValue());
        dto.setTaskName(activity.getLocalName());
        return dto;
    }
}
