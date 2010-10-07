package utils.contextSituationsAccess;

import model.impl.util.ModelAccess;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 7, 2010
 * Time: 11:41:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextSituationToDtoMapper {

    private ContextSituationToDtoMapper() {
    }

    public static ContextSituationDto map(ModelAccess modelAccess) {
        ContextSituationDto situationDto = new ContextSituationDto();
        Collection<ServiceCenterServer> serversList = modelAccess.getAllServiceCenterServerInstances();
        Collection<ApplicationActivity> tasks = modelAccess.getAllApplicationActivityInstances();

        for (ServiceCenterServer server : serversList) {
            situationDto.addServerDto(ServerToDtoMapper.map(server));
        }

        for (ApplicationActivity activity : tasks) {
            situationDto.addTaskDto(TaskToDtoMapper.map(activity));
        }
        return situationDto;
    }
}
