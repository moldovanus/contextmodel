package globalLoop.agents.behaviors;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import model.impl.util.ModelAccess;
import model.interfaces.ContextSnapshot;
import model.interfaces.actions.ContextAction;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.policies.GPI_KPI_Policy;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;
import selfoptimizing.ontologyRepresentations.greenContextOntology.EnergyPolicy;
import selfoptimizing.utils.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 1:45:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class RLServiceCenterServersManagement extends TickerBehaviour {
    private Agent agent;
    ModelAccess modelAccess;
    private ContextSnapshot smallestEntropyContext;

    public RLServiceCenterServersManagement(Agent a, ModelAccess modelAccess, long period) {
        super(a, period);
        agent = a;
        this.modelAccess = modelAccess;
    }


    private double taskRespectanceDegree(ApplicationActivity applicationActivity) {

        double respectance = applicationActivity.getCPUWeight()
//                * (requested.getCores() - received.getCores()
//                + requested.getCpuMaxAcceptableValue() - received.getCpuReceived())
                + applicationActivity.getCPURequiredValue() - applicationActivity.getCPUAllocatedValue()
                + applicationActivity.getMEMWeight() * (applicationActivity.getMEMRequiredValue() - applicationActivity.getMEMAllocatedValue())
                + applicationActivity.getHDDWeight() * (applicationActivity.getHDDRequiredValue() - applicationActivity.getHDDAllocatedValue());
        return respectance;
    }

    private double energyRespectanceDegree(ServiceCenterServer server) {
        double respectance = 0.0;
        Collection<ServiceCenterITComputingResource> resources = server.getResources();

        CPU associatedCPU = null;
        Collection<Core> cores = new ArrayList<Core>();
//        selfoptimizing.ontologyRepresentations.greenContextOntology.Memory serverMemory = server.getAssociatedMemory();
        HDD storage = null;
        MEM memory = null;
        double cpuCores = 0.0;

        double diff = 0.0;
        for (Core core : cores) {
            diff = 0.0;
            Double usedCore = core.getCurrentWorkLoad();
            Double coreMaxAcceptableValue = core.getMaximumWorkLoad();

            if (usedCore > coreMaxAcceptableValue) {
                diff = usedCore - coreMaxAcceptableValue;
            } else if (usedCore < coreMaxAcceptableValue) {
                //TODO:changed din usedCore - coreMaxAcceptableValue pentru ca dadea entropie negativa
                diff = coreMaxAcceptableValue - usedCore;
            }
            cpuCores += diff;
        }
        cpuCores /= cores.size();
//        respectance += associatedCPU.getWeight() * cpuCores;
        respectance += cpuCores;
        diff = 0.0;

        double usedMemory = memory.getCurrentWorkLoad();
        double memoryMaxAcceptableValue = memory.getMaximumWorkLoad();

        //TODO: trebe minimum workload cred
        int memoryMinAcceptableValue = 0; //memory.getMinAcceptableValue();

        if (usedMemory > memoryMaxAcceptableValue) {
            diff = usedMemory - memoryMaxAcceptableValue;
        } else if (usedMemory < memoryMinAcceptableValue) {
            diff = usedMemory - memoryMinAcceptableValue;
        }
//        respectance += memory.getWeight() * diff;
        respectance += diff;
        diff = 0.0;

        Double usedStorage = storage.getCurrentWorkLoad();
        Double storageMaxAcceptableValue = storage.getMaximumWorkLoad();
        int storageMinAcceptableValue = 0;//storage.getMinAcceptableValue();

        if (usedStorage > storageMaxAcceptableValue) {
            diff = usedStorage - storageMaxAcceptableValue;
        } else if (usedStorage < storageMinAcceptableValue) {
            //TODO:changed din usedStorage - storageMinAcceptableValue pentru ca dadea entropie negativa
            diff = storageMinAcceptableValue - usedStorage;
        }

//        respectance += storage.getWeight() * diff;
        respectance += diff;
        return respectance;
    }

    private Pair<Double, GPI_KPI_Policy> computeEntropy() {
        GPI_KPI_Policy brokenPolicy = null;
        double entropy = 0.0;
        Collection<QoSPolicy> qosPolicies = modelAccess.getAllQoSPolicyInstances();

        for (QoSPolicy policy : qosPolicies) {

            Collection<ContextResource> task = policy.getPolicySubject();
            //if task has been deleted
            if (task == null) {
                continue;
            }
            if (!policy.isRespected()) {
                if (brokenPolicy == null) {
                    brokenPolicy = policy;
                }

                //TODO; de bagat priority la politica
//                if (policy.hasPriority()) {
//                    entropy += policy.getPriority() * taskRespectanceDegree(task);
//                }
            }
        }

        Collection<GPI_KPI_Policy> policies = modelAccess.getAllGPI_KPI_PolicyInstances();
        for (GPI_KPI_Policy policy : policies) {
            Collection<ContextResource> servers = policy.getPolicySubject();

            for (ContextResource r : servers) {
//                if (!server.getIsInLowPowerState())
                if (!policy.isRespected()) {
                    //System.out.println("Broken server : " + server);
                    if (brokenPolicy == null) {
                        brokenPolicy = policy;
                    }
//                if (policy.hasPriority()) {
//
//                    entropy += policy.getPriority() * energyRespectanceDegree(server);
//                }
                    entropy += energyRespectanceDegree((ServiceCenterServer) r);
                }
            }
        }

        return new Pair<Double, GPI_KPI_Policy>(entropy, brokenPolicy);
    }


    private double computeRewardFunction(ContextSnapshot previous, ContextSnapshot current, ContextAction c) {
        double function = 0.0d;
        if (previous != null) {
            function += previous.getRewardFunction();
            //TODO: de bagat cost pe actiune
            double temp = previous.getRewardFunction() - current.getContextEntropy(); //- c.getCost() - current.getActions().size() * 100;
            function = ContextSnapshot.gamma * temp;
        } else {
            function = -current.getContextEntropy();
        }

        return function;
    }

    protected ServiceCenterServer getMinDistanceServer(ContextAction task) {
        ServiceCenterServer retServer = null;
        double difference;
        double minDif = 10000000.0d;
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        MEM mem = null;
        HDD hdd = null;

        for (ServiceCenterServer server : servers) {
            //TODO: de facut ceva k f nashpa asa sa iterezi de fiecare data

            Collection<Core> cores = new ArrayList<Core>();
            for (ServiceCenterITComputingResource resource : server.getResources()) {
                if (resource instanceof Core) {
                    cores.add((Core) resource);
                } else if (resource instanceof MEM) {
                    mem = (MEM) resource;
                } else if (resource instanceof HDD) {
                    hdd = (HDD) resource;
                }
            }

            difference = 0.0d;
            for (Core core : cores) {
                difference += Math.pow(core.getMaximumWorkLoad()
                        - core.getCurrentWorkLoad()
                        - 0, 2);      //TODO: Aceeasi problema pentru ca nu este minim
            }

            difference += Math.pow(mem.getMaximumWorkLoad() - mem.getCurrentWorkLoad()
                    - 0, 2);      // Aceeasi problema pentru ca nu este minim
            difference += Math.pow(hdd.getMaximumWorkLoad() - hdd.getCurrentWorkLoad()
                    - 0, 2);  // Aceeasi problema pentru ca nu este minim
            difference = Math.sqrt(difference);

            //TODO; pentru cand bagam negociere sa facem si metodele astea
//            if (server.hasResourcesToBeNegotiatedFor(task))
//                if (difference < minDif) {
//                    minDif = difference;
//                    retServer = server;
//                }
        }
        return retServer;
    }

    //TODO: metoda de reinforcementLearning

    private ContextSnapshot reinforcementLearning(PriorityQueue<ContextSnapshot> queue) {

        ContextSnapshot newContext = queue.poll();
        if (newContext == null) {
            Pair<Double, GPI_KPI_Policy> entropyAndPolicy = computeEntropy();

            System.out.println("Could not repair the context totally. Returning best solution:");
            Queue<ContextAction> commands = smallestEntropyContext.getActions();
            for (ContextAction command : commands) {
                System.out.println(command.toString());
            }

            System.out.println("Broken " + entropyAndPolicy.getSecond().getLocalName() + "\n Referenced " + entropyAndPolicy.getSecond().getReferenced().toString());

            //agent.getSelfOptimizingLogger().log(Color.red, "No solution found", "Could not repair the context totally. Returning best solution.");
            return smallestEntropyContext;
        }
        System.out.println("---A");
        Collection<ServiceCenterServer> servers = modelAccess.getAllServiceCenterServerInstances();
        newContext.executeActions(modelAccess);
//        datacenterMemory.restoreProtegeFactory(protegeFactory);
          //TODO: de bagat dupa ce bagam memoria
//        Queue<ContextAction> commands = datacenterMemory.getActionsForTasks(protegeFactory);
//        if (commands != null) {
//            System.out.println("Remembered...!!");
//            newContext.addActions(commands);
//        }
        Pair<Double, GPI_KPI_Policy> entropyAndPolicy = computeEntropy();

        if (smallestEntropyContext != null) {
            if (entropyAndPolicy.getFirst() < smallestEntropyContext.getContextEntropy())
                smallestEntropyContext = newContext;
        } else {
            smallestEntropyContext = newContext;
        }

        System.out.println("\n" + entropyAndPolicy.getFirst() + "  " + newContext.getRewardFunction() + "  " + entropyAndPolicy.getSecond() + "\n");
        System.out.println("---B");


        ApplicationActivity task = null;
        ServiceCenterServer server = null;

        if (entropyAndPolicy.getFirst() > 0) {
            if (entropyAndPolicy.getSecond() != null) {
                GPI_KPI_Policy policy = entropyAndPolicy.getSecond();
                if (policy instanceof QoSPolicy) {
                    task = (ApplicationActivity) policy.getReferenced();
                } else {
                    server = (ServiceCenterServer) policy.getReferenced();
                }
            }
            boolean deployed = false;     /// sa zica daca ii  deployed sau nu
//            if (task != null) {
//                // deploy actions
//                for (Server serverInstance : servers) {
//                    if (!serverInstance.getIsInLowPowerState() && serverInstance.hasResourcesFor(task)
//                            && !serverInstance.containsTask(task) && !task.isRunning()) {
//                        SelfOptimizingCommand newAction = new DeployTaskCommand(protegeFactory, serverInstance.getName(), task.getName());
//                        if (!newContext.getActions().contains(newAction)) {
//                            ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
//                            cs.getActions().add(newAction);
//                            deployed = true;
//                            newAction.execute(datacenterPolicyConversionModel);
//
//                            Double afterExecuteEntropy = computeEntropy().getFirst();
//                            cs.setContextEntropy(afterExecuteEntropy);
//                            cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
//                            newAction.rewind(datacenterPolicyConversionModel);
//
//                            queue.add(cs);
//                        }
//                    }
//                }

            // move actions
//                Collection<Server> servers1 = protegeFactory.getAllServerInstances();
//                for (Server serverInstance : servers) {
//                    if (!serverInstance.getIsInLowPowerState()) {
//                        Iterator it = serverInstance.listRunningTasks();
//                        while (it.hasNext()) {
//                            Task myTask = (DefaultTask) it.next();
//                            for (Server otherServerInstance : servers1) {
//                                if (!otherServerInstance.getIsInLowPowerState() && otherServerInstance.hasResourcesFor(myTask)
//                                        && !otherServerInstance.containsTask(myTask)) {
//                                    SelfOptimizingCommand newAction = new MoveTaskCommand(protegeFactory, serverInstance.getName(), otherServerInstance.getName(), myTask.getName());
//
//                                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
//                                    //if action is not already in the actions list
//                                    if (!cs.getActions().contains(newAction)) {
//                                        cs.getActions().add(newAction);
//                                        newAction.execute(datacenterPolicyConversionModel);
//
//                                        cs.setContextEntropy(computeEntropy().getFirst());
//                                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
//                                        newAction.rewind(datacenterPolicyConversionModel);
//
//                                        queue.add(cs);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//        }
//            if (server != null) {
//                Iterator it = server.listRunningTasks();
//                // move tasks from server
//                while (it.hasNext()) {
//                    Task myTask = (DefaultTask) it.next();
//                    for (Server serverInstance : servers) {
//                        if (!serverInstance.getIsInLowPowerState() && !serverInstance.containsTask(myTask)
//                                && serverInstance.hasResourcesFor(myTask)) {
//                            Command newAction = new MoveTaskCommand(protegeFactory, server.getName(), serverInstance.getName(), myTask.getName());
//                            if (!newContext.getActions().contains(newAction)) {
//                                ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
//                                cs.getActions().add(newAction);
//
//                                newAction.execute(datacenterPolicyConversionModel);
//                                cs.setContextEntropy(computeEntropy().getFirst());
//                                cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
//                                newAction.rewind(datacenterPolicyConversionModel);
//
//                                queue.add(cs);
//                            }
//                        }
//                    }
//                }
//    }
            // wake up

//            for (Server serverInstance : servers) {
//                if (serverInstance.getIsInLowPowerState()) { //&& (task!=null) && serverInstance.hasResourcesFor(task)) {
//                    System.out.println(serverInstance.getLocalName() + " " + serverInstance.getIsInLowPowerState() + " is waking up");
//                    Command newAction = new WakeUpServerCommand(protegeFactory, serverInstance.getName());
//                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
//                    //if action is not already in the actions list
//                    if (!cs.getActions().contains(newAction)) {
//                        cs.getActions().add(newAction);
//
//                        newAction.execute(datacenterPolicyConversionModel);
//                        cs.setContextEntropy(computeEntropy().getFirst());
//                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
//                        newAction.rewind(datacenterPolicyConversionModel);
//
//                        queue.add(cs);
//                    }
//                }
//            }
//            // sleep
//            for (Server serverInstance : servers) {
//                if (!serverInstance.getIsInLowPowerState() && !serverInstance.hasRunningTasks()) {
//                    Command newAction = new SendServerToLowPowerStateCommand(protegeFactory, serverInstance.getName());
//
//                    if (!newContext.getActions().contains(newAction)) {
//                        ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
//                        cs.getActions().add(newAction);
//
//                        newAction.execute(datacenterPolicyConversionModel);
//                        cs.setContextEntropy(computeEntropy().getFirst());
//                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
//
//                        newAction.rewind(datacenterPolicyConversionModel);
//                        queue.add(cs);
//                    }
//                }
//            }

            /*

            //TODO : to be changed to allow allocating less than maximum also ? nush ce am vrut sa zic aici
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


    @Override
    protected void onTick() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
