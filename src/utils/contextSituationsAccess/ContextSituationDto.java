package utils.contextSituationsAccess;

import com.softnetConsult.utils.collections.Pair;
import utils.clustering.ClusteringAlgorithm;
import utils.clustering.ClusteringAlgorithmFactory;
import utils.worldInterface.dtos.ActionDto;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ExtendedTaskDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final int COMPARISON_ACCURACY_FOR_TASKS = 2;
    private final int COMPARISON_ACCURACY_FOR_SERVERS = 1;
    ClusteringAlgorithm clusteringAlgorithmForTasks;
    ClusteringAlgorithm clusteringAlgorithmForServers;

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

    /**
     * @param contextSituationDto pair <ActionFromCurrentContextName,ActionFromSavedContextName> =
     *                            match between action that can be executed in current context and action found in saved context
     * @return
     */
    public Map<String, String> matchTasks(ContextSituationDto contextSituationDto) {
        HashMap<String, String> associatedTask = new HashMap<String, String>();

        for (ExtendedTaskDto dto : tasks) {
            if (!associatedTask.containsKey(dto)) {
                for (ExtendedTaskDto dto1 : contextSituationDto.getTasks()) {
                    if (clusteringAlgorithmForTasks.getBelongingCluster(dto) ==
                            clusteringAlgorithmForTasks.getBelongingCluster(dto1)
                            && !associatedTask.containsValue(dto1)) {
                        associatedTask.put(dto1.getTaskName(), dto.getTaskName());
                    }
                }
            }
        }
        return associatedTask;
    }

    /**
     * @param contextSituationDto pair <ServerFromCurrentContext,ServerFromSavedContext> =  match between
     *                            curent contetx servers and saved state servers
     * @return
     */
    public Map<String, String> matchServers(ContextSituationDto contextSituationDto) {
        HashMap<String, String> associatedServer = new HashMap<String, String>();

        for (ExtendedServerDto dto : servers) {
            if (!associatedServer.containsKey(dto)) {
                for (ExtendedServerDto dto1 : contextSituationDto.getServers()) {
                    if (clusteringAlgorithmForServers.getBelongingCluster(dto) == clusteringAlgorithmForServers.getBelongingCluster(dto1) &&
                            !associatedServer.containsValue(dto1)) {
                        associatedServer.put(dto1.getServerName(), dto.getServerName());
                    }
                }
            }
        }
        return associatedServer;
    }

    public boolean equals(ContextSituationDto contextSituationDto) {
        if (tasks.size() != contextSituationDto.getTasks().size()) return false;
        if (servers.size() != contextSituationDto.getServers().size()) return false;
        ClusteringAlgorithmFactory factory = new ClusteringAlgorithmFactory();
        clusteringAlgorithmForTasks = factory.getKMeansAlgorithm(tasks.size() / COMPARISON_ACCURACY_FOR_TASKS);
        clusteringAlgorithmForServers = factory.getKMeansAlgorithm(servers.size() / COMPARISON_ACCURACY_FOR_SERVERS);
        clusteringAlgorithmForTasks.initializeClusters(tasks);
        clusteringAlgorithmForServers.initializeClusters(servers);
        clusteringAlgorithmForTasks.initializeClusters(tasks);
        clusteringAlgorithmForServers.initializeClusters(servers);
        clusteringAlgorithmForTasks.addObjectsToKnowledgeBase(contextSituationDto.getTasks());
        clusteringAlgorithmForServers.addObjectsToKnowledgeBase(contextSituationDto.getServers());
        clusteringAlgorithmForTasks.refreshClusters();
        clusteringAlgorithmForServers.refreshClusters();
        int tasksC1[] = new int[clusteringAlgorithmForTasks.getNrOfClusters()];
        int tasksC2[] = new int[clusteringAlgorithmForTasks.getNrOfClusters()];
        int nr = 0;
        for (ExtendedTaskDto dto : tasks) {
            nr = clusteringAlgorithmForTasks.getBelongingCluster(dto);
            if (nr > -1)
                tasksC1[nr]++;
        }
        for (ExtendedTaskDto dto : contextSituationDto.getTasks()) {
            nr = clusteringAlgorithmForTasks.getBelongingCluster(dto);
            if (nr > -1)
                tasksC2[nr]++;
        }
        boolean different = false;
        for (int i = 0; i < clusteringAlgorithmForTasks.getNrOfClusters(); i++) {
            if (tasksC1[i] != tasksC2[i]) {
                different = true;
            }
        }
        if (!different) {
            int serversC1[] = new int[clusteringAlgorithmForServers.getNrOfClusters()];
            int serversC2[] = new int[clusteringAlgorithmForServers.getNrOfClusters()];
            nr = 0;
            for (ExtendedServerDto dto : servers) {
                nr = clusteringAlgorithmForServers.getBelongingCluster(dto);
                if (nr > -1) {
                    serversC1[nr]++;
                }
            }
            for (ExtendedServerDto dto : contextSituationDto.getServers()) {
                nr = clusteringAlgorithmForServers.getBelongingCluster(dto);
                if (nr > -1) {
                    serversC2[nr]++;
                }
            }
            for (int i = 0; i < clusteringAlgorithmForTasks.getNrOfClusters(); i++) {
                if (serversC1[i] != serversC1[i]){
                    different = true;
                }
            }
        }
        return different;
    }


}
