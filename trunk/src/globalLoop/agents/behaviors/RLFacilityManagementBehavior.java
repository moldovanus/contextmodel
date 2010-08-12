package globalLoop.agents.behaviors;

import globalLoop.utils.GlobalVars;
import globalLoop.utils.SensorsValues;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import model.impl.util.ModelAccess;
import model.interfaces.ContextSnapshot;
import model.interfaces.actions.ContextAction;
import model.interfaces.actions.ITFacilityResourceAdaptationAction;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.policies.EnvironmentPolicy;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.ITFacilityActiveResource;
import model.interfaces.resources.Sensor;
import selfoptimizing.utils.Pair;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 12:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class RLFacilityManagementBehavior extends TickerBehaviour {
    private Agent agent;
    private ModelAccess modelAccess;

    public RLFacilityManagementBehavior(Agent a, long period, ModelAccess modelAccess) {
        super(a, period);
        agent = a;
        this.modelAccess = modelAccess;
    }


    private Pair<Double, EnvironmentPolicy> computeEntropy(ContextSnapshot contextSnapshot) {

        EnvironmentPolicy brokenPolicy = null;
        double entropy = 0.0;
        Collection<EnvironmentPolicy> policies = modelAccess.getAllEnvironmentPolicyInstances();

        for (EnvironmentPolicy policy : policies) {
            if (!policy.isRespected()) {
                if (brokenPolicy == null) {
                    brokenPolicy = policy;
                }
                entropy++;
            }
        }

        return new Pair<Double, EnvironmentPolicy>(entropy, brokenPolicy);
    }

    public Boolean hasCycles(HashMap<SensorsValues, SensorsValues> contexts, SensorsValues myContext) {
        if (contexts.get(myContext) != null) {
            return true;
        } else {
            return false;
        }
    }


    public ContextSnapshot reinforcementLearning(Queue<ContextSnapshot> queue, HashMap<SensorsValues, SensorsValues> contexts) throws Exception {

        ContextSnapshot context = queue.remove();

        SensorsValues values = new SensorsValues(modelAccess);
        //TODO: de pus memory si pentru chestia asta
//        Queue<Command> actions = memory.getActions(values);
//
//        //exists
//        if (actions != null) {
//            context.addActions(actions);
//            System.out.println("Remembered");
//            return context;
//        }
//
//        context.executeActions(policyConversionModel);
//        values = new SensorsValues(protegeFactory);
        Pair<Double, EnvironmentPolicy> contextEvaluationResult = computeEntropy(context);
        if (contextEvaluationResult.getFirst() == 0) {
            context.undoAllActions(modelAccess);
            return context;
        }
        SensorsValues newContext = new SensorsValues(modelAccess);

        if (!hasCycles(contexts, newContext)) {

            HashMap<SensorsValues, SensorsValues> myContexts = new HashMap<SensorsValues, SensorsValues>(contexts);
            myContexts.put(newContext, newContext);

            if (contextEvaluationResult.getFirst() > 0) {

                EnvironmentPolicy brokenPolicy = contextEvaluationResult.getSecond();
                Collection<ContextResource> associatedResources = brokenPolicy.getPolicySubject();

                for (ContextResource resource : associatedResources) {
                    Sensor sensor = (Sensor) resource;
                    //skip sensor if its value respects the Policy
                    if (sensor.hasAcceptableValue(modelAccess)) {
                        continue;
                    }

                    Collection<ITFacilityActiveResource> associatedActuators = sensor.getAssociatedActiveResources();

                    for (ITFacilityActiveResource actuator : associatedActuators) {

                        Collection<ITFacilityResourceAdaptationAction> associatedActions = actuator.getActions();

                        for (ITFacilityResourceAdaptationAction action : associatedActions) {
                            Queue<ContextAction> commandsQueue = new LinkedList(context.getActions());

                            action.execute(modelAccess);
                            commandsQueue.add(action);

                            ContextSnapshot afterIncrement = new ContextSnapshot(commandsQueue);
                            queue.add(afterIncrement);
                            action.undo(modelAccess);

                        }
                    }
                }

                if (queue.peek() == null) {
                    System.err.println("EMPTY QUEUE");
                    context.undoAllActions(modelAccess);
                    return context;
                } else {
                    context.undoAllActions(modelAccess);
                    return reinforcementLearning(queue, new HashMap<SensorsValues, SensorsValues>(myContexts));
                }
            }
        } else {
            if (queue.peek() == null) {
                System.err.println("EMPTY QUEUE");
                context.undoAllActions(modelAccess);
                return context;
            } else {
                context.undoAllActions(modelAccess);
                return reinforcementLearning(queue, new HashMap<SensorsValues, SensorsValues>(contexts));
            }
        }
        context.undoAllActions(modelAccess);
        return context;
    }

    private void setBrokenResources(ContextSnapshot contextSnapshot) {

        Map<String, String> brokenResources = new HashMap<String, String>();
        //Map<String, String> validResources = GlobalVars.validResources;
        brokenResources.clear();
        //validResources.clear();

        //set for printing
        ArrayList<String> brokenPoliciesNames = new ArrayList<String>();
        Collection<EnvironmentPolicy> policies = modelAccess.getAllEnvironmentPolicyInstances();

        for (ContextPolicy policy : policies) {
            Collection<ContextResource> sensors = policy.getPolicySubject();

            if (!policy.isRespected()) {

                brokenPoliciesNames.add(policy.getName());

                for (ContextResource s : sensors) {
                    Sensor sensor = (Sensor) s;
                    if (!sensor.hasAcceptableValue(modelAccess)) {
                        brokenResources.put(sensor.getLocalName(), sensor.getLocalName());
                    } else {
                        //validResources.put(sensor.getLocalName(), sensor.getLocalName());
                    }
                }

            }
        }

    }


    @Override
    protected void onTick() {

        System.out.println("On tick");

        Queue<ContextSnapshot> queue = new LinkedList<ContextSnapshot>();
        ContextSnapshot initialContext = new ContextSnapshot(new LinkedList<ContextAction>());
        SensorsValues currentValues = new SensorsValues(modelAccess);
        queue.add(initialContext);

//        smallestEntropy = 10000;
        ContextSnapshot contextSnapshot;
        Pair<Double, EnvironmentPolicy> entropyState = computeEntropy(initialContext);
        if (entropyState.getFirst() != 0) {
//            contextBroken = true;
            ArrayList<String> list = new ArrayList<String>();
            String policyName = entropyState.getSecond().getLocalName();
            list.add(policyName);

            EnvironmentPolicy brokenPolicy = entropyState.getSecond();
            Object[] associatedResources = brokenPolicy.getPolicySubject().toArray();

            ArrayList<String> brokenSensorsList = new ArrayList<String>();
            int associatedResourcesSize = associatedResources.length;
            for (int i = 0; i < associatedResourcesSize; i++) {

                //get the resource as individual from the global model such that getPropertyValue can be called on it
                Sensor sensor = (Sensor) associatedResources[i];

                //skip sensor if its value respects the Policy
                if (!sensor.hasAcceptableValue(modelAccess)) {
                    brokenSensorsList.add(sensor.getLocalName());
                }
            }

            try {
                long startMinutes = new java.util.Date().getTime();

                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setLanguage("JavaSerialization");

                msg.setContent("BROKEN");
                msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
                agent.send(msg);

                contextSnapshot = reinforcementLearning(queue, new HashMap<SensorsValues, SensorsValues>());

                msg.setContent("OK");
                msg.addReceiver(new AID(GlobalVars.RLAGENT_NAME + "@" + agent.getContainerController().getPlatformName()));
                agent.send(msg);

                long endMinutes = new java.util.Date().getTime();

                int value = (int) ((endMinutes - startMinutes) / 1000);

//                agent.setRlTime(value);

                System.err.println("Environment alg running time: " + value + " seconds");

            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }


            setBrokenResources(contextSnapshot);

            Queue<ContextAction> bestActionsList = contextSnapshot.getActions();

            System.err.println();
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.err.println("for " + currentValues);


            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.err.println("===============================================================");


            //TODO: de bagat totusi si memory da nu stim cum
//            memory.memorize(currentValues, bestActionsList);
//            contextSnapshot.executeActionsOnOWL();
            contextSnapshot.executeActions(modelAccess);


        }

    }


}

