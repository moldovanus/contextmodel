package utils.contextSituationsAccess;

import model.interfaces.actions.ContextAction;
import model.interfaces.actions.DeployActivity;
import model.interfaces.actions.MigrateActivity;
import model.interfaces.actions.SetServerStateActivity;
import model.interfaces.resources.ContextResource;
import utils.worldInterface.dtos.ActionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:32:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionToDtoMapper {

    private ActionToDtoMapper() {
    }

    public static ActionDto map(ContextAction action) {
        ActionDto dto = new ActionDto();
        dto.setActionClassName(action.getClass().getName());
        dto.setActionName(action.getLocalName());
        dto.setCost(action.getCost());
        List<String> resources = new ArrayList<String>();
        if (action instanceof DeployActivity) {
            resources.add(((DeployActivity) action).getActivity().getLocalName());
        } else if (action instanceof MigrateActivity) {
            resources.add(((MigrateActivity) action).getActivity().getLocalName());
        } else if (action instanceof SetServerStateActivity) {
            dto.setTargetServerState(((SetServerStateActivity) action).getTargetServerState());
        }

        for (ContextResource resource : action.getResources()) {
            resources.add(resource.getLocalName());
        }

        dto.setContextResources(resources);

        return dto;
    }
}
