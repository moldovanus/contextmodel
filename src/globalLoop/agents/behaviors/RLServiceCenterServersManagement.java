package globalLoop.agents.behaviors;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import model.impl.ontologyImpl.actions.DefaultDeployActivity;
import model.impl.util.ModelAccess;
import model.interfaces.ContextSnapshot;
import model.interfaces.actions.ContextAction;
import model.interfaces.actions.DeployActivity;
import model.interfaces.actions.MigrateActivity;
import model.interfaces.actions.SetServerStateActivity;
import model.interfaces.policies.GPI_KPI_Policy;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;
import selfoptimizing.utils.Pair;

import java.util.*;

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
        double respectance =
                applicationActivity.getCPUWeight() *
                        (applicationActivity.getNumberOfCoresAllocatedValue()
                                - applicationActivity.getNumberOfCoresRequiredValue()
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

                if (usedCore > coreMaxAcceptableValue) {
                    diff = usedCore - coreMaxAcceptableValue;
                } else if (usedCore < coreMaxAcceptableValue) {
                    diff = coreMaxAcceptableValue - usedCore;
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
                diff = usedMemory - memoryMinAcceptableValue;
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
                if (policy.hasPolicyWeight()) {
                    for (ContextResource app : task) {
                        entropy += policy.getPolicyWeight() * taskRespectanceDegree((ApplicationActivity) app);
                    }
                }
            }
        }

        Collection<ITComputingContextPolicy> policies = modelAccess.getAllITComputingContextPolicyInstances();
        for (ITComputingContextPolicy policy : policies) {
            Collection<ContextResource> servers = policy.getPolicySubject();
            for (ContextResource r : servers) {
                ServiceCenterServer server = (ServiceCenterServer) r;
                if (server.getIsActive())
                    if (!policy.isRespected()) {
                        System.out.println("Broken server : " + server);
                        if (brokenPolicy == null) {
                            brokenPolicy = policy;
                        }
                        if (policy.hasPolicyWeight()) {
                            entropy += policy.getPolicyWeight() * energyRespectanceDegree(server);
                        } else
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
                        - optimal / 2.0, 2);  // Aceeasi problema pentru ca nu este minim
            }
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

//            System.out.println("Broken " + entropyAndPolicy.getSecond().getLocalName() + "\n Referenced " + entropyAndPolicy.getSecond().getReferenced().toString());

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

        System.out.println("\n Entropy" + entropyAndPolicy.getFirst() + "  " + newContext.getRewardFunction() + "  " + entropyAndPolicy.getSecond() + "\n");
        System.out.println("---B");


        Collection<ContextResource> associatedTasks = null;
        Collection<ContextResource> associatedServers = null;
        ServiceCenterServer server = null;

        if (entropyAndPolicy.getFirst() > 0) {
            if (entropyAndPolicy.getSecond() != null) {
                GPI_KPI_Policy policy = entropyAndPolicy.getSecond();

                if (policy.getPolicySubject().get(0) instanceof ApplicationActivity) {
                    associatedTasks = policy.getPolicySubject();
                } else {
                    associatedServers = policy.getPolicySubject();
                }
            }
            boolean deployed = false;     /// sa zica daca ii  deployed sau nu
            if (associatedTasks != null) {
                // deploy actions
                for (ServiceCenterServer serverInstance : servers) {

                    //  for (ContextResource res : associatedTasks) {
                    // TODO: punem mai multe later on cand consideram ca avem nevoie de mai multe taskuri asociate unei politici
                    ApplicationActivity task = (ApplicationActivity) associatedTasks.iterator().next();

                    if (serverInstance.getIsActive() && serverInstance.hasResourcesFor(task)
                            && !serverInstance.hostsActivity(task) && !task.isRunning()) {
                        DeployActivity newAction = new DefaultDeployActivity();//(protegeFactory, serverInstance.getName(), task.getName());
                        if (!newContext.getActions().contains(newAction)) {
                            newAction.addResource(serverInstance);
                            newAction.setActivity(task);

                            ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                            cs.getActions().add(newAction);
                            deployed = true;
                            newAction.execute(modelAccess);

                            Double afterExecuteEntropy = computeEntropy().getFirst();
                            cs.setContextEntropy(afterExecuteEntropy);
                            cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                            newAction.undo(modelAccess);

                            queue.add(cs);
                        }

                    }
                }


//             move actions
                Collection<ServiceCenterServer> servers1 = modelAccess.getAllServiceCenterServerInstances();
                for (ServiceCenterServer sourceServer : servers) {
                    if (!sourceServer.getIsActive()) {
                        Iterator it = sourceServer.getRunningActivities().iterator();
                        while (it.hasNext()) {
                            ApplicationActivity myTask = (ApplicationActivity) it.next();
                            for (ServiceCenterServer destinationServer : servers1) {
                                if (!destinationServer.getIsActive() && destinationServer.hasResourcesFor(myTask)
                                        && !destinationServer.hostsActivity(myTask)) {
                                    MigrateActivity newAction =
                                            modelAccess.createMigrateActivity("Migrate_from_"
                                                    + destinationServer.getName()
                                                    + "_to_" + myTask.getName() + "_Activity");

                                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                                    //if action is not already in the actions list
                                    if (!cs.getActions().contains(newAction)) {
                                        newAction.setResourceFrom(sourceServer);
                                        newAction.setResourceTo(destinationServer);
                                        newAction.setActivity(myTask);

                                        cs.getActions().add(newAction);
                                        newAction.execute(modelAccess);

                                        cs.setContextEntropy(computeEntropy().getFirst());
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
            if (server != null) {
                Iterator it = server.getRunningActivities().iterator();
                // move tasks from server
                while (it.hasNext()) {
                    ApplicationActivity myTask = (ApplicationActivity) it.next();
                    for (ServiceCenterServer destinationServer : servers) {
                        if (!destinationServer.getIsActive() && !destinationServer.hostsActivity(myTask)
                                && destinationServer.hasResourcesFor(myTask)) {
                            MigrateActivity newAction = modelAccess.createMigrateActivity("Migrate_from_"
                                    + server.getName()
                                    + "_to_" + myTask.getName() + "_Activity");
                            if (!newContext.getActions().contains(newAction)) {
                                newAction.setResourceFrom(server);
                                newAction.setResourceTo(destinationServer);
                                newAction.setActivity(myTask);

                                ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                                cs.getActions().add(newAction);

                                newAction.execute(modelAccess);
                                cs.setContextEntropy(computeEntropy().getFirst());
                                cs.setRewardFunction(computeRewardFunction(newContext, cs, newAction));
                                newAction.undo(modelAccess);

                                queue.add(cs);
                            }
                        }
                    }
                }
            }
//             wake up

            for (ServiceCenterServer serverInstance : servers) {
                if (serverInstance.getIsActive() && (associatedTasks != null) && serverInstance.hasResourcesFor((ApplicationActivity) associatedTasks.iterator().next())) { //&& (task!=null) && serverInstance.hasResourcesFor(task)) {
                    System.out.println(serverInstance.getLocalName() + " " + serverInstance.getIsActive() + " is waking up");
                    SetServerStateActivity newActivity =
                            modelAccess.createSetServerStateActivity("Set_state_for_" + serverInstance.getName()
                                    + "_to_" + 1);
                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                    //if action is not already in the actions list
                    if (!cs.getActions().contains(newActivity)) {
                        newActivity.addResource(serverInstance);
                        cs.getActions().add(newActivity);

                        newActivity.execute(modelAccess);
                        cs.setContextEntropy(computeEntropy().getFirst());
                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newActivity));
                        newActivity.undo(modelAccess);

                        queue.add(cs);
                    }
                }
            }

            // sleep
            for (ServiceCenterServer serverInstance : servers) {
                if (!serverInstance.getIsActive() && !(serverInstance.getRunningActivities() != null)) {
                    SetServerStateActivity newActivity =
                            modelAccess.createSetServerStateActivity("Set_state_for_" + serverInstance.getName()
                                    + "_to_" + 0);
                    ContextSnapshot cs = new ContextSnapshot(new LinkedList(newContext.getActions()));
                    if (!cs.getActions().contains(newActivity)) {
                        newActivity.addResource(serverInstance);
                        cs.getActions().add(newActivity);

                        newActivity.execute(modelAccess);
                        cs.setContextEntropy(computeEntropy().getFirst());
                        cs.setRewardFunction(computeRewardFunction(newContext, cs, newActivity));
                        newActivity.undo(modelAccess);

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


    @Override
    protected void onTick() {
        PriorityQueue<ContextSnapshot> queue = new PriorityQueue<ContextSnapshot>();
        ContextSnapshot initialContext = new ContextSnapshot(new LinkedList());
        Pair<Double, GPI_KPI_Policy> entropyAndPolicy = computeEntropy();

        System.out.println("Initial on tick entropy: " + entropyAndPolicy.getFirst() + " " + entropyAndPolicy.getSecond());

        initialContext.setContextEntropy(entropyAndPolicy.getFirst());
        initialContext.setRewardFunction(computeRewardFunction(null, initialContext, null));
        queue.add(initialContext);

        if (entropyAndPolicy.getSecond() != null) {
            ContextSnapshot result = reinforcementLearning(queue);
        }
    }
}
