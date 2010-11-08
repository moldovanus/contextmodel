package utils.contextSituationsAccess;

import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.dtos.VirtualTaskInfo;

/**
 * Created by IntelliJ IDEA.
 * User: oneadmin
 * Date: Nov 8, 2010
 * Time: 12:49:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskToVirtualInfoMapper {
    private TaskToVirtualInfoMapper() {
    }

    public static VirtualTaskInfo map(ApplicationActivity activity) {

//         private VirtualNetworkInfo virtualNetworkInfo;
//    private List<VirtualDiskInfo> virtualDiskInfos;
//
//    private int id;
//
//    private int requestedCPU;
//    private int requestedMemory;
        VirtualTaskInfo taskInfo = new VirtualTaskInfo(activity.getLocalName());
//        VirtualNetworkInfo networkInfo = new VirtualNetworkInfo();

        taskInfo.setId(activity.getId());
        taskInfo.setRequestedCPU((int) activity.getCpuRequiredMaxValue());
        taskInfo.setRequestedMemory((int) activity.getMemRequiredMaxValue());

        taskInfo.setRequestedCPU((int) activity.getCpuRequiredMaxValue());
//        dto.setHddWeight(activity.getHDDWeight());
//        dto.setReceivedCores((int) activity.getNumberOfCoresAllocatedValue());
//        dto.setReceivedCPU((int) activity.getCpuAllocatedValue());
//        dto.setReceivedMemory((int) activity.getMemAllocatedValue());
//        dto.setReceivedStorage((int) activity.getHddAllocatedValue());
//        dto.setRequestedCores((int) activity.getNumberOfCoresRequiredValue());
//        dto.setRequestedCPUMin((int) activity.getCpuRequiredMinValue());
//        dto.setRequestedCPUMax((int) activity.getCpuRequiredMaxValue());
//        dto.setRequestedMemoryMin((int) activity.getMemRequiredMinValue());
//        dto.setRequestedMemoryMax((int) activity.getMemRequiredMaxValue());
//        dto.setRequestedStorageMin((int) activity.getHddRequiredMinValue());
//        dto.setRequestedStorageMax((int) activity.getHddRequiredMaxValue());
//        dto.setTaskName(activity.getLocalName());
//
        //TODO :DefaultDeployActivity deploy not yet implemente fully
        System.err.println("TaskToVirtualInfoMapper.map not implemented fully yet. Todo");
        return taskInfo;
    }
}
