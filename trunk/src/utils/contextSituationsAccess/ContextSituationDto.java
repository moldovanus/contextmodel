package utils.contextSituationsAccess;

import utils.worldInterface.dtos.ActionDto;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ExtendedTaskDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextSituationDto implements Serializable {
    private List<ExtendedServerDto> servers;
    private List<ExtendedTaskDto> tasks;
    private List<ActionDto> actions;

    public ContextSituationDto() {
        servers = new ArrayList<ExtendedServerDto>();
        tasks = new ArrayList<ExtendedTaskDto>();
        actions = new ArrayList<ActionDto>();
    }

    public void addServerDto(ExtendedServerDto dto) {
        servers.add(dto);
    }

    public void addTaskDto(ExtendedTaskDto dto) {
        tasks.add(dto);
    }

    public void addActionDto(ActionDto dto) {
        actions.add(dto);
    }

    public List<ExtendedServerDto> getServers() {
        return servers;
    }

    public void setServers(List<ExtendedServerDto> servers) {
        this.servers = servers;
    }

    public List<ExtendedTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<ExtendedTaskDto> tasks) {
        this.tasks = tasks;
    }

    public List<ActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ActionDto> actions) {
        this.actions = actions;
    }

}
