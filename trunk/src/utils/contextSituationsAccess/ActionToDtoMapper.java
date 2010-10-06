package utils.contextSituationsAccess;

import model.interfaces.actions.ContextAction;
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
        for (ContextResource resource : action.getResources()) {
            resources.add(resource.getLocalName());
        }
        dto.setContextResources(resources);

        return dto;
    }
}
