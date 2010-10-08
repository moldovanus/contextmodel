package globalLoop.agents.behaviors;

import globalLoop.agents.RLAgent;
import globalLoop.utils.GlobalVars;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import model.impl.ontologyImpl.actions.DefaultDeployActivityAction;
import model.impl.ontologyImpl.actions.DefaultMigrateActivityAction;
import model.impl.ontologyImpl.actions.DefaultSetServerStateAction;
import model.impl.util.ModelAccess;
import model.interfaces.ContextSnapshot;
import model.interfaces.actions.*;
import model.interfaces.policies.GPI_KPI_Policy;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;
import reasoning.Evaluator;
import reasoning.impl.PelletEvaluator;
import utils.clustering.Cluster;
import utils.clustering.ClusteringAlgorithm;
import utils.clustering.ClusteringAlgorithmFactory;
import utils.contextSituationsAccess.*;
import utils.exceptions.IndividualNotFoundException;
import utils.logger.LoggerGUI;
import utils.misc.DecisionTreeNode;
import utils.misc.Pair;
import utils.negotiator.Negotiator;
import utils.negotiator.impl.NegotiatorFactory;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.datacenterInterface.proxies.impl.StubProxy;
import utils.worldInterface.dtos.ActionDto;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ServerDto;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 1:45:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RLServiceCenterServersManagement extends TickerBehaviour {
    private RLAgent agent;
    ModelAccess modelAccess;
    private ContextSnapshot smallestEntropyContext;
    private LoggerGUI logger;
    private int stackDepth = 0;
    private static final int MAXIMUM_STACK_DEPTH = 40;

    private DecisionTreeNode rootDecisionTreeNode;


    public RLServiceCenterServersManagement(RLAgent a, ModelAccess modelAccess, long period) {
        super(a, period);
        agent = a;
        this.modelAccess = modelAccess;
        logger = new LoggerGUI("Service_Center_Log");
        logger.setLogPath("./logs/");

    }


    private double taskRespectanceDegree(ApplicationActivity applicationActivity) {
        double respectance =
                applicationActivity.getCPUWeight() *
                        (applicationActivity.getNumberOfCoresRequiredValue()
                                - applicationActivity.getNumberOfCoresAllocatedValue()
                                + applicationActivity.getCpuRequiredMaxValue()
                                - applicationActivity.getCpuAllocatedValue())
                        + applicationActivity.getMEMWeight() *
                        (applicationActivity.getMemRequiredMaxValue() - applicationActivity.getMemAllocatedValue())
                        + applicationActivity.getHDDWeight() *
                        (applicationActivity.getHddRequiredMaxValue() - applicationActivity.getHddAllocatedValue());
        return respectance;
    }

    private double energyRespectanceDegree(ServiceCenterServer server) {
        double respectance = 0.0;
        Collection<ServiceCenterITComputingResource> resources = server.getResources();

        CPU associatedCPU = null;
        Collection<Core> cores = new ArrayList<Core>();
        HDD storage = null;
        MEM memory = null;
        double diff = 0.0;
        for (CPU cpu : server.getCpuResources()) {
            double cpuCores = 0.0;
            cores = cpu.getAssociatedCores();
            for (Core core : cores) {
                diff = 0.0;
                Double usedCore = core.getCurrentWorkLoad();
                Double coreMaxAcceptableValue = core.getOptimalWorkLoad() + (core.getMaximumWorkLoad() - core.getOptimalWorkLoad()) / 2.0;
                Double coreMinAcceptableValue = core.getOptimalWorkLoad() / 2.0;
                if (usedCore > coreMaxAcceptableValue) {
                    diff = usedCore - coreMaxAcceptableValue;
                } else if (usedCore < coreMinAcceptableValue) {
                    diff = coreMinAcceptableValue - usedCore;
                }
                cpuCores += diff;
            }
            cpuCores /= cores.size();
            if (server.hasCPUWeight())
                respectance += server.getCPUWeight() * cpuCores;
            else
                respectance += cpuCores;

        }
        diff = 0.0;
        double usedMemory = 0.0;
        double total = 0.0;
        double optimal = 0.0;
        double memoryMaxAcceptableValue = 0.0;

        //TODO: de verificat daca e ok rangeu
        double memoryMinAcceptableValue = optimal / 2.0;
        Collection<MEM> memories = server.getMemResources();
        for (MEM mem : memories) {
            usedMemory = mem.getCurrentWorkLoad();
            total = mem.getMaximumWorkLoad();
            optimal = mem.getOptimalWorkLoad();
            memoryMaxAcceptableValue = optimal + (total - optimal) / 2.0;

            if (usedMemory > memoryMaxAcceptableValue) {
                diff = usedMemory - memoryMaxAcceptableValue;
            } else if (usedMemory < memoryMinAcceptableValue) {
                diff = memoryMinAcceptableValue - usedMemory;
            }
            if (server.hasMEMWeight())
                respectance += server.getMEMWeight() * diff;
            respectance += diff;
        }
        diff = 0.0;

        Double usedStorage = 0.0;
        Double storageMaxAcceptableValue = 0.0;
        double storageMinAcceptableValue = 0.0;

        for (HDD hdd : server.getHddResources()) {
            total = hdd.getMaximumWorkLoad();
            optimal = hdd.getOptimalWorkLoad();
            usedStorage = hdd.getCurrentWorkLoad();
            storageMaxAcceptableValue = optimal + (total - optimal) / 2.0;
            storageMinAcceptableValue = optimal / 2.0;
            if (usedStorage > storageMaxAcceptableValue) {
                diff = usedStorage - storageMaxAcceptableValue;
            } else if (usedStorage < storageMinAcceptableValue) {
                diff = storageMinAcceptableValue - usedStorage;
            }
            if (server.hasHDDWeight())
                respectance += server.getHDDWeight() * diff;
            else
                respectance += diff;
        }


        return respectance;
    }

    private Pair<Double, List<GPI_KPI_Policy>> computeEntropy() {
        List<GPI_KPI_Policy> brokenPolicies = new ArrayList<GPI_KPI_Policy>();
        GPI_KPI_Policy brokenPolicy = null;
        double entropy = 0.0;
        Collection<QoSPolicy> qosPolicies = modelAccess.getAllQoSPolicyInstances();
        Evaluator evaluator = new PelletEvaluator(modelAccess.getOntologyModelFactory().getOwlModel());
        for (QoSPolicy policy : qosPolicies) {

            Collection<ContextResource> task = policy.getPolicySubject();
            //if task has been deleted
            if (task == null) {
                continue;
            }
            try {
                if (!evaluator.evaluatePolicy(policy, policy.getIsRespectedPropertyName())) {
                    brokenPolicies.add(policy);
                    if (brokenPolicy == null) {
                        brokenPolicy = policy;
                    }
                    if (policy.hasPolicyWeight()) {
                        for (ContextResource app : task) {
                            entropy += policy.getPolicyWeight() * taskRespectanceDegree((ApplicationActivity) app);
                        }
                    }
                }
            } catch (IndividualNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        Collection<ITComputingContextPolicy> policies = modelAccess.getAllITComputingContextPolicyInstances();
        for (ITComputingContextPolicy policy : policies) {
            Collection<ContextResource> servers = policy.getPolicySubject();
            for (ContextResource r : servers) {
                ServiceCenterServer server = (ServiceCenterServer) r;
                if (server.getIsActive())
                    try {
                        if (!evaluator.evaluatePolicy(policy, policy.getIsRespectedPropertyName())) {
//                            System.out.println("Broken server : " + server.getLocalName());
                            brokenPolicies.add(policy);
                            if (brokenPolicy == null) {
                                brokenPolicy = policy;
                            }
                            if (policy.hasPolicyWeight()) {
                                entropy += policy.getPolicyWeight() * energyRespectanceDegree(server);
                            } else
                                entropy += energyRespectanceDegree((ServiceCenterServer) r);
                        }
                    } catch (IndividualNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
            }
        }

        return new Pair<Double, List<GPI_KPI_Policy>>(entropy, brokenPolicies);
    }


    private double computeRewardFunction(ContextSnapshot previous, ContextSnapshot current, ContextAction c) {
        double function = 0.0d;
        if (previous != null) {
            double temp = previous.getContextEntropy() - current.getContextEntropy() - current.getActions().size() * 100 - c.getCost(); //- c.getCost() - current.getActions().size() * 100;
            function += ContextSnapshot.gamma * temp;
        } else {
            function = -current.getContextEntropy();
        }

        return function;
    }

    protected ServiceCenterServer getMinDistanceServer(ApplicationActivity task) {
        ServiceCenterServer retServer = null;
        double difference;
        double minDif = 10000000.0d;
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();


        for (ServiceCenterServer server : servers) {
            difference = 0.0d;
            double optimal = 0.0;
            double total = 0.0;
            Collection<Core> cores = new ArrayList<Core>();
            for (CPU resource : server.getCpuResources()) {
                cores = resource.getAssociatedCores();
                for (Core core : cores) {
                    optimal = core.getOptimalWorkLoad();
                    total = core.getMaximumWorkLoad();
                    difference += Math.pow(total - (total - optimal) / 2.0 - core.getCurrentWorkLoad()
                            - optimal, 2);
                }
            }


            for (MEM mem : server.getMemResources()) {
                optimal = mem.getOptimalWorkLoad();
                total = mem.getMaximumWorkLoad();

                difference += Math.pow(total - (total - optimal) / 2.0 - mem.getCurrentWorkLoad()
                        - optimal / 2.0, 2);
            }
            for (HDD hdd : server.getHddResources()) {
                optimal = hdd.getOptimalWorkLoad();
                total = hdd.getMaximumWorkLoad();
                difference += Math.pow(total - (total - optimal) / 2.0 - hdd.getCurrentWorkLoad()
                        - optimal / 2.0, 2);
            }
            difference = Math.sqrt(difference);

            if (server.hasResourcesToBeNegotiatedFor(task))
                if (difference < minDif) {
                    minDif = difference;
                    retServer = server;
                }
        }
        return retServer;
    }

    private ContextSnapshot reinforcementLearning(PriorityQueue<ContextSnapshot> queue) {
        System.out.println("STACK depth: " + stackDepth++);

        ContextSnapshot newContext = queue.poll();
        if (newContext == null || (stackDepth >= MAXIMUM_STACK_DEPTH)) {
            Pair<Double, List<GPI_KPI_Policy>> entropyAndPolicy = computeEntropy();

            System.out.println("Could not repair the context totally. Returning best solution:");
            sendLogToGUI("Could not repair the context totally. Returning best solution:\n");
            logger.log(Color.ORANGE, "Could not repair the context totally. Returning best solution", new ArrayList<String>());

            Queue<ContextAction> commands = smallestEntropyContext.getActions();
            for (ContextAction command : commands) {
                System.out.println("Simulating " + command.toString());
                sendLogToGUI("Simulating " + command.toString());
            }
            sendLogToGUI("\n");
//            System.out.println("Broken " + entropyAndPolicy.getSecond().getLocalName() + "\n Referenced " + entropyAndPolicy.getSecond().getReferenced().toString());

            //agent.getSelfOptimizingLogger().log(Color.red, "No solution found", "Could not repair the context totally. Returning best solution.");
            stackDepth = 0;
            return smallestEntropyContext;
        }

        ContextSituationDto contextSituationDto = ContextSituationToDtoMapper.map(modelAccess);
        Collection<ContextSituationDto> contextSituationDtos;
        List<ContextAction> actionsList = new ArrayList<ContextAction>();

        try {
            contextSituationDtos = ContextSituationAccess.getSituations();
            for (ContextSituationDto situationDto : contextSituationDtos) {
                if (contextSituationDto.equals(situationDto)) {
                    System.err.println("Match found");
                    List<ActionDto> actionDtos = situationDto.getActions();
                    Map<String, String> serversMatch = contextSituationDto.matchServers(situationDto);
                    Map<String, String> tasksMatch = contextSituationDto.matchTasks(situationDto);
                    for (ActionDto actionDto : actionDtos) {
                        String className = actionDto.getActionClassName();
                        List<String> resources = actionDto.getContextResources();
                        ContextAction action = null;
                        if (className.equals(DefaultDeployActivityAction.class.getName())) {
                            action = modelAccess.createDeployActivity(actionDto.getActionName());
                            for (String string : resources) {
                                if (tasksMatch.containsKey(string)) {
                                    ((DefaultDeployActivityAction) action).setActivity(modelAccess.getApplicationActivity(tasksMatch.get(string)));
                                } else if (serversMatch.containsKey(string)) {
                                    action.addResource(modelAccess.getContextResource(serversMatch.get(string)));
                                } else {
                                    System.err.println("Something wrong happened cand am create ActionDto sau nu face clusteringu match");
                                }
                            }
                        } else if (className.equals(DefaultMigrateActivityAction.class.getName())) {
                            action = modelAccess.createMigrateActivity(actionDto.getActionName());
                            for (String string : resources) {
                                if (tasksMatch.containsKey(string)) {
                                    ((DefaultMigrateActivityAction) action).setActivity(modelAccess.getApplicationActivity(tasksMatch.get(string)));
                                } else if (serversMatch.containsKey(string)) {
                                    action.addResource(modelAccess.getContextResource(serversMatch.get(string)));
                                } else {
                                    System.err.println("Something wrong happened cand am create ActionDto  sau nu face clusteringu match");
                                }
                            }
                        } else if (className.equals(DefaultSetServerStateAction.class.getName())) {
                            action = modelAccess.createSetServerStateActivity(actionDto.getActionName());
                            for (String string : resources) {
                                if (serversMatch.containsKey(string)) {
                                    action.addResource(modelAccess.getContextResource(serversMatch.get(string)));
                                    ((DefaultSetServerStateAction) action).setTargetServerState(actionDto.getTargetServerState());
                                } else {
                                    System.err.println("Something wrong happened cand am create ActionDto  sau nu face clusteringu match");
                                }
                            }
                        }
                        if (action == null) {
                            System.err.println("Something gone wrong la cautat in save  -sau la luat actiunile de pe contextu curent nu cel salvat :)) dai vina pe mine >:P [-( ");
                        } else {
                            actionsList.add(action);
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (actionsList.size() > 0) {
            newContext.setActions(new PriorityQueue<ContextAction>(actionsList));
            return newContext;
        }

        System.out.println("Step no " + stackDepth + " :----------------------------");
        sendLogToGUI("Step no " + stackDepth + "\n :----------------------------");
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        newContext.executeActions(modelAccess);
        Queue<ContextAction> actions = newContext.getActions();
        ArrayList<String> message = new ArrayList<String>();

        for (ContextAction action : actions) {
            message.add("Simulating: " + action.toString());
            sendLogToGUI("Simulating:  " + action.toString());
            System.out.println("Simulating " + action.toString());
        }

        DecisionTreeNode currentStep;
        if (actions.size() == 0) {
            currentStep = rootDecisionTreeNode;
        } else {
            currentStep = newContext.getDecisionTreeNode();
        }

        logger.log(Color.BLACK, "Current try", message);
//        datacenterMemory.restoreProtegeFactory(protegeFactory);
        //TODO: de bagat dupa ce bagam memoria
//        Queue<ContextAction> commands = datacenterMemory.getActionsForTasks(protegeFactory);
//        if (commands != null) {
//            System.out.println("Remembered...!!");
//            newContext.addActions(commands);
//        }
        Pair<Double, List<GPI_KPI_Policy>> entropyAndPolicy = computeEntropy();

        if (smallestEntropyContext != null) {
            if (newContext.getContextEntropy() < smallestEntropyContext.getContextEntropy())
                smallestEntropyContext = newContext;
        } else {
            smallestEntropyContext = newContext;
        }

        System.out.println("\n Entropy: " + entropyAndPolicy.getFirst()
//                  + ",  Reward: " + newContext.getRewardFunction()
                + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond().size() == 0) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()) + "\n");
        sendLogToGUI("\n Entropy: " + entropyAndPolicy.getFirst()
//                + ",  Reward: " + newContext.getRewardFunction()
                + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond().size() == 0) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()) + "\n");
        System.out.println("------------------------------");


        ArrayList<String> simulationResultMessage = new ArrayList<String>();
        simulationResultMessage.add("\n Entropy: " + entropyAndPolicy.getFirst()
                + ",  Reward: " + newContext.getRewardFunction()
                + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond().size() == 0) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()) + "\n");
        logger.log(Color.black, "Simulation result context", simulationResultMessage);

        List<ContextResource> associatedTasks = new ArrayList<ContextResource>();
        List<ContextResource> associatedServers = new ArrayList<ContextResource>();

        if (entropyAndPolicy.getFirst() > 0) {
            if (entropyAndPolicy.getSecond() != null) {
                List<GPI_KPI_Policy> policies = entropyAndPolicy.getSecond();
                for (GPI_KPI_Policy policy : policies) {
                    if (policy.getPolicySubject().get(0) instanceof ApplicationActivity) {
                        associatedTasks.add(policy.getPolicySubject().get(0));
                    } else {
                        associatedServers.add(policy.getPolicySubject().get(0));
                    }
                }
            }

            if (associatedTasks != null) {
                // deploy actions
                for (ContextResource activity : associatedTasks) {
                    ApplicationActivity task = (ApplicationActivity) activity;
                    for (ServiceCenterServer serverInstance : servers) {

                        //  for (ContextResource res : associatedTasks) {
                        // TODO: punem mai multe later on cand consideram ca avem nevoie de mai multe taskuri asociate unei politici

                        if ((serverInstance.getCurrentEnergyState() != 0) && serverInstance.hasResourcesFor(task)
                                && !serverInstance.hostsActivity(task) && !task.isRunning()) {
                            DeployActivity newAction = modelAccess.createDeployActivity("Deploy_"
                                    + task.getName() + "_to_" + serverInstance.getName());
                            newAction.addResource(serverInstance);
                            newAction.setActivity(task);

                            if (!newContext.getActions().contains(newAction)) {

                                ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                                cs.getActions().add(newAction);
                                newAction.execute(modelAccess);
                                Double afterExecuteEntropy = computeEntropy().getFirst();
                                DecisionTreeNode node = new DecisionTreeNode("Step " + stackDepth
                                        , newAction.toString(), "" + afterExecuteEntropy);
                                cs.setDecisionTreeNode(node);
                                currentStep.addChild(node);
                                cs.setContextEntropy(afterExecuteEntropy);
                                cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                                newAction.undo(modelAccess);

                                queue.add(cs);
                            }

                        }
                    }
                }


//              move actions
                Collection<ServiceCenterServer> servers1 = modelAccess.getAllServiceCenterServerInstances();
                for (ServiceCenterServer sourceServer : servers) {
                    if (sourceServer.getIsActive()) {
                        Iterator it = sourceServer.getRunningActivities().iterator();
                        while (it.hasNext()) {
                            ApplicationActivity myTask = (ApplicationActivity) it.next();
                            for (ServiceCenterServer destinationServer : servers1) {
                                if (destinationServer.getIsActive() && destinationServer.hasResourcesFor(myTask)
                                        && !destinationServer.hostsActivity(myTask)) {
                                    MigrateActivity newAction =
                                            modelAccess.createMigrateActivity("Migrate_from_"
                                                    + destinationServer.getName()
                                                    + "_to_" + myTask.getName() + "_Activity");

                                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                                    newAction.setResourceFrom(sourceServer);
                                    newAction.setResourceTo(destinationServer);
                                    newAction.setActivity(myTask);

                                    //if action is not already in the actions list
                                    if (!cs.getActions().contains(newAction)) {

                                        cs.getActions().add(newAction);
                                        newAction.execute(modelAccess);

                                        Double afterExecuteEntropy = computeEntropy().getFirst();
                                        DecisionTreeNode node = new DecisionTreeNode("Step " + stackDepth
                                                , newAction.toString(), "" + afterExecuteEntropy);
                                        cs.setDecisionTreeNode(node);
                                        currentStep.addChild(node);
                                        cs.setContextEntropy(afterExecuteEntropy);
                                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                                        newAction.undo(modelAccess);

                                        queue.add(cs);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (associatedServers.size() != 0) {
                for (ContextResource contextResource : associatedServers) {
                    ServiceCenterServer server = (ServiceCenterServer) contextResource;
                    Iterator it = server.getRunningActivities().iterator();
                    // move tasks from server
                    while (it.hasNext()) {
                        ApplicationActivity myTask = (ApplicationActivity) it.next();
                        for (ServiceCenterServer destinationServer : servers) {
                            if (destinationServer.getIsActive() && !destinationServer.hostsActivity(myTask)
                                    && destinationServer.hasResourcesFor(myTask)) {
                                MigrateActivity newAction = modelAccess.createMigrateActivity("Migrate_from_"
                                        + server.getName()
                                        + "_to_" + myTask.getName() + "_Activity");
                                newAction.setResourceFrom(server);
                                newAction.setResourceTo(destinationServer);
                                newAction.setActivity(myTask);
                                if (!newContext.getActions().contains(newAction)) {

                                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                                    cs.getActions().add(newAction);
                                    newAction.execute(modelAccess);
                                    Double afterExecuteEntropy = computeEntropy().getFirst();
                                    DecisionTreeNode node = new DecisionTreeNode("Step " + stackDepth
                                            , newAction.toString(), "" + afterExecuteEntropy);
                                    cs.setDecisionTreeNode(node);
                                    currentStep.addChild(node);
                                    cs.setContextEntropy(afterExecuteEntropy);
                                    cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                                    newAction.undo(modelAccess);

                                    queue.add(cs);
                                }
                            }
                        }
                    }
                }
            }
//             wake up
            for (ServiceCenterServer serverInstance : servers) {
                if ((!serverInstance.getIsActive()) && associatedTasks.size() != 0 && serverInstance.hasResourcesFor((ApplicationActivity) associatedTasks.iterator().next())) { //&& (task!=null) && serverInstance.hasResourcesFor(task)) {
//                    System.out.println(serverInstance.getLocalName() + " " + serverInstance.getIsActive() + " is waking up");
                    SetServerStateActivity newAction =
                            modelAccess.createSetServerStateActivity("Set_state_for_" + serverInstance.getName()
                                    + "_to_" + 1);
                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                    newAction.setTargetServerState(1);
                    newAction.addResource(serverInstance);

                    //if action is not already in the actions list
                    if (!cs.getActions().contains(newAction)) {
                        cs.getActions().add(newAction);

                        newAction.execute(modelAccess);
                        Double afterExecuteEntropy = computeEntropy().getFirst();
                        DecisionTreeNode node = new DecisionTreeNode("Step " + stackDepth
                                , newAction.toString(), "" + afterExecuteEntropy);
                        cs.setDecisionTreeNode(node);
                        currentStep.addChild(node);
                        cs.setContextEntropy(afterExecuteEntropy);
                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                        newAction.undo(modelAccess);

                        queue.add(cs);
                    }
                }
            }

            // sleep
            for (ServiceCenterServer serverInstance : servers) {
                if (serverInstance.getIsActive() && (!serverInstance.hasRunningActivities())) {
                    SetServerStateActivity newAction =
                            modelAccess.createSetServerStateActivity("Set_state_for_" + serverInstance.getName()
                                    + "_to_" + 0);
                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                    newAction.setTargetServerState(0);
                    newAction.addResource(serverInstance);

                    if (!cs.getActions().contains(newAction)) {

                        cs.getActions().add(newAction);

                        newAction.execute(modelAccess);
                        Double afterExecuteEntropy = computeEntropy().getFirst();
                        DecisionTreeNode node = new DecisionTreeNode("Step " + stackDepth
                                , newAction.toString(), "" + afterExecuteEntropy);
                        cs.setDecisionTreeNode(node);
                        currentStep.addChild(node);
                        cs.setContextEntropy(afterExecuteEntropy);
                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                        newAction.undo(modelAccess);

                        queue.add(cs);
                    }
                }
            }

            /*

            //negotiate allocating more resources only if all the tasks have been deployed
            //if (allDeployed &&
            //always try a negotiation in order to solve problems :P
            if( server != null) {
                NegotiateResourcesCommand negotiateResourcesCommand = new NegotiateResourcesCommand(protegeFactory, utils.negotiator,server.getName());

                if (!newContext.getActions().contains(negotiateResourcesCommand)) {
                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                    cs.getActions().add(negotiateResourcesCommand);
                    //  cs.executeActions();
                    negotiateResourcesCommand.execute(datacenterPolicyConversionModel);
                    cs.setContextEntropy(computeEntropy().getFirst());
                    Pair<Double,Policy> e = computeEntropy();

                    System.out.println("After negotiation " + negotiateResourcesCommand + "\n For server : " + server +   + e.getFirst() + "  " + e.getSecond());
                    cs.setRewardFunction(computeRewardFunction(newContext, cs, negotiateResourcesCommand));
                    // cs.rewind();
                    negotiateResourcesCommand.rewind(datacenterPolicyConversionModel);
                    queue.add(cs);
                }
            }*/

            newContext.undoAllActions(modelAccess);

            newContext = reinforcementLearning(queue);
        } else {
            newContext.undoAllActions(modelAccess);
        }
        return newContext;
    }

    public void adjustPolicies(ServiceCenterServer server) {
        CPU cpu = server.getCpuResources().iterator().next();
        for (Core core : cpu.getAssociatedCores()) {
            if ((core.getOptimalWorkLoad() / 2.0) > core.getCurrentWorkLoad()) {
                core.setOptimalWorkLoad(core.getCurrentWorkLoad() * 2);
                cpu.setOptimalWorkLoad(core.getCurrentWorkLoad() * 2);
            }
            if (((core.getOptimalWorkLoad() + core.getMaximumWorkLoad()) / 2.0) < core.getCurrentWorkLoad()) {
                core.setOptimalWorkLoad(core.getCurrentWorkLoad() * 2 - core.getMaximumWorkLoad());
                cpu.setOptimalWorkLoad(core.getCurrentWorkLoad() * 2 - core.getMaximumWorkLoad());
            }
        }
        MEM mem = server.getMemResources().iterator().next();
        if ((mem.getOptimalWorkLoad() / 2.0) > mem.getCurrentWorkLoad()) {
            mem.setOptimalWorkLoad((mem.getCurrentWorkLoad() - 100) * 2);
        }
        if (((mem.getOptimalWorkLoad() + mem.getMaximumWorkLoad()) / 2.0) < mem.getCurrentWorkLoad()) {
            mem.setOptimalWorkLoad((mem.getCurrentWorkLoad() + 100) * 2 - mem.getMaximumWorkLoad());
        }
    }

    @Override
    protected void onTick() {
        modelAccess.setSimulation(true);
//        agent.killScheduledTasks();
        stackDepth = 0;
//        refresh server information
        System.out.println("New configuration");
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        for (ServiceCenterServer server : servers) {
            ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
            if (!server.getIsActive() || (proxy instanceof StubProxy)) {
                continue;
            }

            ServerDto dto = proxy.getServerInfo();
            int coreCount = dto.getCoreCount();
            int totalCPU = dto.getTotalCPU();

            //read info only about the number of cores wanted for test
            List<Integer> integers = dto.getFreeCPU();
            List<Core> cores = server.getCpuResources().iterator().next().getAssociatedCores();
            for (int i = 0; i < cores.size(); i++) {
                Core core = cores.get(i);
                core.setCurrentWorkLoad(totalCPU - integers.get(i).doubleValue());
                core.setMaximumWorkLoad((double) totalCPU);
            }

//            List<StorageDto> storageDtos = dto.getStorage();
//            for(StorageDto storageDto : storageDtos){
//                HDD  hdd = server.getHddResources().iterator().next();
//                if ( storageDto.getName().charAt(0) == hdd.getPhysicalPath().charAt(0)){
//                    hdd.setCurrentWorkLoad((double)storageDto.getSize() - storageDto.getFreeSpace());
//                    hdd.setMaximumWorkLoad((double)storageDto.getSize());
//                    break;
//                }
//            }

            MEM memory = server.getMemResources().iterator().next();
            memory.setCurrentWorkLoad((double) dto.getTotalMemory() - dto.getFreeMemory());
            memory.setMaximumWorkLoad((double) dto.getTotalMemory());
            System.out.println(server.toString());
            System.out.println(server.getCpuResources().iterator().next().toString());
            System.out.println(memory.toString());
        }

        PriorityQueue<ContextSnapshot> queue = new PriorityQueue<ContextSnapshot>(1, new Comparator<ContextSnapshot>() {

            public int compare(ContextSnapshot snapshot_1, ContextSnapshot snapshot_2) {
                if (snapshot_1.getContextEntropy() < snapshot_2.getContextEntropy()) {
                    return -1;
                } else if (snapshot_1.getContextEntropy() == snapshot_2.getContextEntropy()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        ContextSnapshot initialContext = new ContextSnapshot(new LinkedList());
        Pair<Double, List<GPI_KPI_Policy>> entropyAndPolicy = computeEntropy();


        System.out.println("\n Entropy: " + entropyAndPolicy.getFirst()
                + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond().size() == 0) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()));

        if (entropyAndPolicy.getFirst() > 0) {
            sendLogToGUI("\n Entropy: "
                    + entropyAndPolicy.getFirst()
                    + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond().size() == 0) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()));
        }

        initialContext.setContextEntropy(entropyAndPolicy.getFirst());
        initialContext.setRewardFunction(computeRewardFunction(null, initialContext, null));
        queue.add(initialContext);

        if (entropyAndPolicy.getFirst() > 0) {
            ContextSituationDto situationDto = new ContextSituationDto();
            Collection<ServiceCenterServer> serversList = modelAccess.getAllServiceCenterServerInstances();
            Collection<ApplicationActivity> tasks = modelAccess.getAllApplicationActivityInstances();

            for (ServiceCenterServer server : serversList) {
                situationDto.addServerDto(ServerToDtoMapper.map(server));
            }

            for (ApplicationActivity activity : tasks) {
                situationDto.addTaskDto(TaskToDtoMapper.map(activity));
            }

            ArrayList<String> message = new ArrayList<String>();
            message.add("Entropy: " + entropyAndPolicy.getFirst()
                    + ",  BrokenPolicy: " + ((entropyAndPolicy.getSecond() == null) ? "none" : entropyAndPolicy.getSecond().get(0).getLocalName()));
            logger.log(Color.black, "Resulting context", message);
            logContext(Color.RED);
            java.util.Date before = new java.util.Date();

            rootDecisionTreeNode = new DecisionTreeNode("Initial", "No action", "" + entropyAndPolicy.getFirst());

            ContextSnapshot result = reinforcementLearning(queue);
            java.util.Date after = new java.util.Date();
            modelAccess.setSimulation(false);
            Collection<ServiceCenterServer> servers1 = modelAccess.getAllServiceCenterServerInstances();
            ClusteringAlgorithmFactory factory = new ClusteringAlgorithmFactory();
            Object[] data = new Object[1];
            data[0] = 3;
            try {
                ClusteringAlgorithm kMeans = factory.getKMeansAlgorithm(3);
                List<ExtendedServerDto> extendedServerDtos = new ArrayList<ExtendedServerDto>();
                for (ServiceCenterServer serviceCenterServer : servers) {
                    extendedServerDtos.add(ServerToDtoMapper.map(serviceCenterServer));
                }
                kMeans.initializeClusters(extendedServerDtos);
                Cluster cl = kMeans.getNearestCluster(ServerToDtoMapper.map(servers1.iterator().next()));
                if (cl != null) {
                    System.out.println(cl.toString());
                } else {
                    System.out.println("CLuster null");
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            try {
                msg.setContentObject(new Object[]{"Running time", new java.util.Date(after.getTime() - before.getTime()).getTime()});
            } catch (IOException e) {
                e.printStackTrace();
            }
            msg.addReceiver(new AID(GlobalVars.GUIAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
            agent.send(msg);

            ArrayList<String> newMessage = new ArrayList<String>();
            Queue<ContextAction> actions = result.getActions();
            for (ContextAction action : actions) {
                situationDto.addActionDto(ActionToDtoMapper.map(action));
                System.out.println("Executing: " + action.toString());
                sendLogToGUI("Executing: " + action.toString());
                newMessage.add(action.toString());
                action.execute(modelAccess);
                action.executeOnServiceCenter(modelAccess);
            }

            try {
                if (actions.size() > 0) {
                    ContextSituationAccess.saveContextSituation(situationDto);
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            logger.log(Color.ORANGE, "Decision result", newMessage);

            if (result.getContextEntropy() > 0) {
                logContext(Color.RED);
                ArrayList<String> negotiationMessage = new ArrayList<String>();

                entropyAndPolicy = computeEntropy();

                if (entropyAndPolicy.getSecond().size() > 0 && entropyAndPolicy.getSecond().get(0).getPolicySubject() instanceof ApplicationActivity) {

                    ApplicationActivity activity = (ApplicationActivity) entropyAndPolicy.getSecond().get(0).getPolicySubject();

                    Negotiator negotiator = (Negotiator) NegotiatorFactory.getNashNegotiator();
                    ServiceCenterServer server = getMinDistanceServer(activity);
                    if (server != null) {

                        negotiationMessage.add("Negotiating between " + activity.getLocalName() + " and " + server.getLocalName());
                        sendLogToGUI("Negotiating between " + activity.getLocalName() + " and " + server.getLocalName());
                        if (server != null) {
                            CPU cpu = server.getCpuResources().iterator().next();
                            MEM mem = server.getMemResources().iterator().next();
                            Map<String, Double> negotiatedValues = negotiator.negotiate(server, activity);
                            double optimalCpu = cpu.getAssociatedCores().get(0).getOptimalWorkLoad();
                            double totalCpu = cpu.getAssociatedCores().get(0).getMaximumWorkLoad();
                            double currentCpu = cpu.getAssociatedCores().get(0).getCurrentWorkLoad();
                            double optimalMem = mem.getOptimalWorkLoad();

                            if (negotiatedValues.get(Negotiator.NEGOTIATED_CPU) + currentCpu > (optimalCpu + totalCpu) / 2.0) {
                                double current = negotiatedValues.get(Negotiator.NEGOTIATED_CPU) + currentCpu;
                                optimalCpu = 2 * current - cpu.getMaximumWorkLoad();
                            }
                            if (negotiatedValues.get(Negotiator.NEGOTIATED_MEMORY) + mem.getCurrentWorkLoad() > (mem.getOptimalWorkLoad() + mem.getMaximumWorkLoad()) / 2.0) {
                                double current = negotiatedValues.get(Negotiator.NEGOTIATED_MEMORY) + mem.getCurrentWorkLoad();
                                optimalMem = 2 * current - mem.getMaximumWorkLoad();
                            }
                            SetServerStateActivity newActivity =
                                    modelAccess.createSetServerStateActivity("Set_state_for_" + server.getName()
                                            + "_to_" + 1);
                            newActivity.execute(modelAccess);
                            newActivity.executeOnServiceCenter(modelAccess);
                            result.getActions().add(newActivity);

                            ServerAdaptationAction defaultServerAdaptationAction = modelAccess.createServerAdaptationAction("ServerAdaptationAction_" + server);
                            //new DefaultServerAdaptationAction(server,(int)optimalCpu,(int)optimalMem);
                            defaultServerAdaptationAction.setNewOptimalValueForCpu((int) optimalCpu);
                            defaultServerAdaptationAction.setNewOptimalValueForMem((int) optimalMem);
                            defaultServerAdaptationAction.setServer(server);

                            negotiationMessage.add(defaultServerAdaptationAction.toString());

                            defaultServerAdaptationAction.execute(modelAccess);
                            result.getActions().add(defaultServerAdaptationAction);

                            ApplicationAdaptationAction applicationAdaptationAction = modelAccess.createApplicationAdaptationAction("ApplicationAdaptationAction_" + activity.getLocalName());
                            applicationAdaptationAction.setActivity(activity);
                            applicationAdaptationAction.setCpuMin((int) activity.getCpuRequiredMinValue());
                            applicationAdaptationAction.setCpuMax(negotiatedValues.get(Negotiator.NEGOTIATED_CPU).intValue());
                            applicationAdaptationAction.setMemMin((int) activity.getMemRequiredMinValue());
                            applicationAdaptationAction.setMemMax(negotiatedValues.get(Negotiator.NEGOTIATED_MEMORY).intValue());

                            negotiationMessage.add(applicationAdaptationAction.toString());

                            applicationAdaptationAction.execute(modelAccess);
                            result.getActions().add(applicationAdaptationAction);
                            DeployActivity deployActivityAction = modelAccess.createDeployActivity("Deploy_"
                                    + activity.getName() + "_to_" + server.getName());
                            deployActivityAction.setActivity(activity);
                            deployActivityAction.setResourceTo(server);
                            deployActivityAction.execute(modelAccess);
                            deployActivityAction.executeOnServiceCenter(modelAccess);
                            result.getActions().add(deployActivityAction);
                            entropyAndPolicy = computeEntropy();
                            result.setContextEntropy(entropyAndPolicy.getFirst());

                            negotiationMessage.add(deployActivityAction.toString());
                            sendLogToGUI(deployActivityAction.toString());
                            logger.log(Color.ORANGE, "Negotiation result", negotiationMessage);
                        }
                    }
                } else {

                    for (ServiceCenterServer server : modelAccess.getAllServiceCenterServerInstances()) {
                        if (server.getIsActive() && server.getRunningActivities().size() > 0) {
                            adjustPolicies(server);
                            sendLogToGUI("Server " + server.getLocalName() + " underused. Decreasing optimum load indicators");
                        }

                    }
                }
                JFrame frame = new JFrame("Decision tree");
                frame.setLayout(new BorderLayout());
                JTree tree = new JTree();
                DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Initial state");

                DecisionTreeNode rootDecisionNode = rootDecisionTreeNode;
                rootNode.add(new DefaultMutableTreeNode("Entropy: " + rootDecisionNode.getEntropy()));
                buildTree(rootNode, rootDecisionNode);

                tree.setModel(new DefaultTreeModel(rootNode));
                expandAll(tree, new TreePath(rootNode));
                JScrollPane scrollPane = new JScrollPane(tree);
                scrollPane.setViewportView(tree);
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.setSize(500, 500);
                frame.setVisible(true);

            }

            logContext(Color.GREEN);
//            OntModel ontModel = ModelFactory.createOntologyModel(org.mindswap.pellet.jena.PelletReasonerFactory.THE_SPEC);
//            ontModel.add(modelAccess.getOntologyModelFactory().getOwlModel().getJenaModel());
//            try {
//                ontModel.write(new BufferedWriter(new FileWriter(new File("./ontology/context_KAON_1.rdf-xml.owl"))));
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//
//            System.exit(1);
        } else {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            try {
                msg.setContentObject(new Object[]{"Refresh Energy", ""});
            } catch (IOException e) {
                e.printStackTrace();
            }
            msg.addReceiver(new AID(GlobalVars.GUIAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
            agent.send(msg);
        }
        smallestEntropyContext = null;
        agent.killScheduledTasks();

        System.out.println(modelAccess.getAllApplicationActivityInstances().size());
    }

    private void sendLogToGUI(String message) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        try {
            msg.setContentObject(new Object[]{"Log", message});
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.addReceiver(new AID(GlobalVars.GUIAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
        agent.send(msg);
    }

    private void logContext(Color color) {

        ArrayList<String> message = new ArrayList<String>();
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        for (ServiceCenterServer server : servers) {
            MEM mem = server.getMemResources().iterator().next();
            Core core = server.getCpuResources().iterator().next().getAssociatedCores().iterator().next();
            message.add(server.getLocalName());
            message.add("Server state: " + ((server.getIsActive()) ? "active" : "in low power state"));
            message.add("Cores no: " + server.getCpuResources().iterator().next().getAssociatedCores().size());
            message.add("CPU total: " + core.getMaximumWorkLoad() + " used: " + core.getCurrentWorkLoad());
            message.add("MEM total: " + mem.getMaximumWorkLoad() + " used: " + mem.getCurrentWorkLoad());
        }
        logger.log(color, "Computing Resources", message);
        ArrayList<String> message_1 = new ArrayList<String>();
        Collection<ApplicationActivity> activities = modelAccess.getAllApplicationActivityInstances();
        for (ApplicationActivity activity : activities) {
            message_1.add(activity.getLocalName());
            message_1.add("Cores requested: " + activity.getNumberOfCoresRequiredValue()
                    + " received: " + activity.getNumberOfCoresAllocatedValue());
            message_1.add("CPU maxRequested: " + activity.getCpuRequiredMaxValue()
                    + " minRequested: " + activity.getCpuRequiredMaxValue()
                    + " received: " + activity.getCpuAllocatedValue());
            message_1.add("MEM maxRequested: " + activity.getMemRequiredMaxValue()
                    + " minRequested: " + activity.getMemRequiredMaxValue()
                    + " received: " + activity.getMemAllocatedValue());

        }
        logger.log(color, "Application Activities", message_1);

    }

    private void buildTree(DefaultMutableTreeNode root, DecisionTreeNode decisionTreeNode) {
        for (DecisionTreeNode decisionNode : decisionTreeNode.getChildren()) {
            DefaultMutableTreeNode muttableNode = new DefaultMutableTreeNode(decisionNode.getNodeName());
            muttableNode.add(new DefaultMutableTreeNode("Entropy: " + decisionNode.getEntropy()));
            muttableNode.add(new DefaultMutableTreeNode("Action: " + decisionNode.getActionName()));
            buildTree(muttableNode, decisionNode);
            root.add(muttableNode);
        }
    }

    private void expandAll(JTree tree, TreePath parent) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path);
            }

        }
        if (node.getChildCount() > 2) {
            tree.expandPath(parent);
        }

    }

}
