package globalLoop.agents;

import com.hp.hpl.jena.ontology.Individual;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.swrl.exceptions.SWRLFactoryException;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import globalLoop.agents.behaviors.RLServiceCenterServersManagement;
import globalLoop.agents.behaviors.ReceiveMessageRLBehaviour;
import globalLoop.utils.GlobalVars;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import model.impl.util.ModelAccess;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.misc.Pair;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.ExtendedTaskDto;
import utils.worldInterface.dtos.TaskDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 12, 2010
 * Time: 10:41:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class RLAgent extends Agent {
    private ModelAccess modelAccess;
    private List<Pair<String, Integer>> toKill;

    public void sendAllTasksToClient() {
        Collection<ApplicationActivity> tasks = modelAccess.getAllApplicationActivityInstances();

        TaskDto[] t = new TaskDto[tasks.size()];
        int i = 0;
        for (ApplicationActivity task : tasks) {
            /*  receivedInfo = task.getReceivedInfo();
            requestedInfo = task.getRequestedInfo();
            s += task.getLocalName() + "-" + task.isRunning() + "=" + requestedInfo.getCores() + "=" + requestedInfo.getCpuMinAcceptableValue() + "=" + requestedInfo.getCpuMaxAcceptableValue() + "=" + requestedInfo.getMemoryMinAcceptableValue() + "=" + requestedInfo.getMemoryMaxAcceptableValue() + "=" + requestedInfo.getStorageMinAcceptableValue() + "=" + requestedInfo.getStorageMaxAcceptableValue();
            s += "=" + receivedInfo.getCores() + "=" + receivedInfo.getCpuReceived() + "=" + receivedInfo.getMemoryReceived() + "=" + receivedInfo.getStorageReceived() + "<";
            */
            t[i] = new TaskDto();
            t[i].setTaskName(task.getLocalName());
            t[i].setRunning(task.isRunning());

            t[i].setRequestedCores((int) task.getNumberOfCoresRequiredValue());
            t[i].setRequestedCPUMax((int) task.getCpuRequiredMaxValue());
            t[i].setRequestedCPUMin((int) task.getCpuRequiredMinValue());
            t[i].setRequestedMemoryMax((int) task.getMemRequiredMaxValue());
            t[i].setRequestedMemoryMin((int) task.getMemRequiredMinValue());
            t[i].setRequestedStorageMax((int) task.getHddRequiredMaxValue());
            t[i].setRequestedStorageMin((int) task.getHddRequiredMinValue());

            t[i].setReceivedCores((int) task.getNumberOfCoresAllocatedValue());
            t[i].setReceivedCPU((int) task.getCpuAllocatedValue());
            t[i].setReceivedMemory((int) task.getMemAllocatedValue());
            t[i].setReceivedStorage((int) task.getHddAllocatedValue());

            i++;
        }
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM_REF);
        if (msg != null) {
            try {
                msg.setContentObject(t);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            msg.addReceiver(new AID(GlobalVars.TMAGENT_NAME + "@" + this.getContainerController().getPlatformName()));
            /*
            try {
                msg.setContentObject(indvName);
                msg.setLanguage("JavaSerialization");
            } catch (IOException e) {
                e.printStackTrace();
            }   */
            this.send(msg);
        }
    }

    public void sendRefuseMessage() {
        ACLMessage msg = new ACLMessage(ACLMessage.REFUSE);
        msg.setContent("Server not found.");
        msg.addReceiver(new AID(GlobalVars.TMAGENT_NAME + "@" + this.getContainerController().getPlatformName()));

        this.send(msg);

    }

    public void addTaskToKill(Pair<String, Integer> taskInfo) {
        taskInfo.setSecond(new Long(new java.util.Date().getTime() + (taskInfo.getSecond() * 1000)).intValue());
        toKill.add(taskInfo);
    }

    public void killScheduledTasks() {
        int currentTimeInMillis = new Long(new Date().getTime()).intValue();
        Object[] entries = toKill.toArray();
        OWLModel model = modelAccess.getOntologyModelFactory().getOwlModel();
        List<ExtendedTaskDto> tasks = new ArrayList<ExtendedTaskDto>();

        List<String> deletedEntries = new ArrayList<String>();
        for (Object entryObject : entries) {
            Pair<String, Integer> entry = (Pair<String, Integer>) entryObject;
            if (currentTimeInMillis >= entry.getSecond()) {
                ApplicationActivity activity = modelAccess.getApplicationActivity(entry.getFirst());

                ExtendedTaskDto taskDto = new ExtendedTaskDto();
                taskDto.setTaskName(activity.getLocalName());
                taskDto.setRequestedCores((int) activity.getNumberOfCoresRequiredValue());
                taskDto.setCpuWeight(activity.getCPUWeight());
                taskDto.setMemWeight(activity.getMEMWeight());
                taskDto.setHddWeight(activity.getHDDWeight());
                taskDto.setRequestedCPUMin((int) activity.getCpuRequiredMinValue());
                taskDto.setRequestedCPUMax((int) activity.getCpuRequiredMaxValue());
                taskDto.setRequestedMemoryMin((int) activity.getMemRequiredMinValue());
                taskDto.setRequestedMemoryMax((int) activity.getMemRequiredMaxValue());
                taskDto.setRequestedStorageMin((int) activity.getHddRequiredMinValue());
                taskDto.setRequestedStorageMax((int) activity.getHddRequiredMaxValue());
                tasks.add(taskDto);

                deletedEntries.add(entry.getFirst());
                for (Object p : activity.getActivityPolicies()) {
                    QoSPolicy policy = (QoSPolicy) p;
                    SWRLFactory factory = new SWRLFactory(model);
                    try {
                        SWRLImp imp = factory.getImp(policy.getLocalName() + "_Rule");
                        imp.disable();
                        imp.deleteImp();
                    } catch (SWRLFactoryException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    OWLIndividual i = model.getOWLIndividual(policy.getName());
                    Individual ontIndividual = model.getOntModel().getIndividual(policy.getName());
                    ontIndividual.remove();
                    RDFResource resource = model.getRDFResource(policy.getName());
                    resource.getKnowledgeBase().deleteFrame(resource);
                    resource.getKnowledgeBase().deleteInstance(resource);
                    resource.delete();
                    i.getKnowledgeBase().deleteFrame(i);
                    i.getKnowledgeBase().deleteInstance(i);
                    i.delete();
                    model.deleteInstance(model.getInstance(i.getName()));
                    Instance instance = model.getInstance(policy.getName());
                    instance.getKnowledgeBase().deleteFrame(instance);
                    instance.getKnowledgeBase().deleteInstance(instance);
                    instance.delete();
                    policy.delete();

                }
                ServiceCenterServer server = activity.getAssociatedServer();
                server.removeRunningActivity(activity);
                ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy(server.getIpAddress());
                proxy.stopVirtualMachine(activity.getLocalName());
                proxy.deleteVirtualMachine(activity.getLocalName());
                Instance instance = model.getInstance(activity.getName());
                instance.getKnowledgeBase().deleteFrame(instance);
                instance.getKnowledgeBase().deleteInstance(instance);
                instance.delete();
                activity.delete();
                OWLIndividual i = model.getOWLIndividual(activity.getName());
                i.delete();
                i.getKnowledgeBase().deleteFrame(i);
                i.getKnowledgeBase().deleteInstance(i);
                model.deleteInstance(model.getInstance(i.getName()));
                Individual ontIndividual = model.getOntModel().getIndividual(activity.getName());
                ontIndividual.remove();
                RDFResource resource = model.getRDFResource(activity.getName());
                resource.delete();
                resource.getKnowledgeBase().deleteFrame(resource);
                resource.getKnowledgeBase().deleteInstance(resource);
                toKill.remove(entry);
                System.out.println("Deleted " + activity.getName());
            }

        }

        if (deletedEntries.size() > 0) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            try {
                msg.setContentObject(new Object[]{"Clones deleted", tasks});
            } catch (IOException e) {
                e.printStackTrace();
            }
            msg.addReceiver(new AID(GlobalVars.GUIAGENT_NAME + "@" + this.getContainerController().getPlatformName()));
            this.send(msg);
        }

    }

    @Override
    protected void setup() {
        System.out.println("RLAgent " + getLocalName() + " started.");
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            modelAccess = (ModelAccess) args[0];
        } else {
            System.out.println("[RLAgent] No Model Access provided.");
            this.doDelete();
            return;
        }

        toKill = new ArrayList<Pair<String, Integer>>();
//        ConfigurationGUI gui = new ConfigurationGUI(modelAccess);
//        gui.setVisible(true);

//        FacilityDefaultAction facilityDefaultAction = modelAccess.createFacilityDefaultAction("Test_Action");
//
//        facilityDefaultAction.setEffect(new ActionEffect() {
//            double oldValue;
//
//            public void execute(ITFacilityPassiveResource resource) {
//                oldValue = resource.getRecordedValue();
//                resource.setRecordedValue(6.0);
//            }
//
//            public void undo(ITFacilityPassiveResource resource) {
//                resource.setRecordedValue(oldValue);
//            }
//        });
//
//        DeployActivity deployActivity = modelAccess.createDeployActivity("DeployAct_1");
//        ApplicationActivity applicationActivity = modelAccess.createApplicationActivity("ApplActivity_1");
//        ServiceCenterITComputingResource resourceTo = modelAccess.createServiceCenterServer("CompResource_1");
//        resourceTo.setCurrentEnergyState(0);
//        resourceTo.setResourceID("aaa");
//        resourceTo.setCurrentWorkLoad(30.0);
//        resourceTo.setMaximumWorkLoad(59.0);
//        resourceTo.setOptimalWorkLoad(20.0);
//
//        deployActivity.setActivity(applicationActivity);
//        deployActivity.setResourceTo(resourceTo);
//
//        ITComputingContextPolicy itComputingContextPolicy = modelAccess.createITComputingContextPolicy("Policy_1");
//        itComputingContextPolicy.setRespected(true);
//        itComputingContextPolicy.addPolicySubject(resourceTo);
//
//
//        Goal goal = new Goal() {
//            public boolean isAchieved(ITFacilityPassiveResource resource) {
//                double recordedValue = resource.getRecordedValue();
//                return recordedValue > 5 && recordedValue < 15;
//            }
//        };
//
//        ITFacilityResourceAdaptationAction adaptationAction = modelAccess.createITFacilityResourceAdaptationAction("TEST_ITFRAA");
//        Sensor passiveResource = modelAccess.createSensor("TEST_ITPR");
//
//        ITFacilityActiveResource activeResource = modelAccess.createITFacilityActiveResource("TEST_ITFAR");
////        adaptationAction.addAssociatedResources(passiveResource);
//        adaptationAction.setFacilityAction(facilityDefaultAction);
//        activeResource.addAssociatedAction(adaptationAction);
//        facilityDefaultAction.addResource(passiveResource);
//
//        EnvironmentPolicy policy = modelAccess.createEnvironmentPolicy("TEST_ENVP");
//        policy.setPolicyGoal(goal);
//        policy.setPolicyAction(adaptationAction);
//        policy.addPolicySubject(passiveResource);
//        policy.addPolicyTarget(activeResource);
//
//        ContextAction action = policy.getPolicyAction();
//        passiveResource.setRecordedValue(0.0);
//        System.out.println(passiveResource.getRecordedValue());
//        action.execute(modelAccess);
//        System.out.println(passiveResource.getRecordedValue());
//        action.undo(modelAccess);
//        System.out.println(passiveResource.getRecordedValue());
//        OWLModel owlModel = null;
//        OntModel ontModel = null;
//        File file = new File(GlobalVars.ONTOLOGY_FILE);
//        try {
//            owlModel = ProtegeOWL.createJenaOWLModelFromURI(file.toURI().toString());
//            ontModel = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//            ontModel.add(owlModel.getJenaModel());
//            ontModel.add(owlModel.getOntModel());
//        } catch (OntologyLoadException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        TestFactory testFactory = new TestFactory(owlModel);
//        SWRLFactory factory = new SWRLFactory(owlModel);
//
//        try {
//            SWRLImp swrlImp = factory.createImp("Test(?x) ->  testValue(?x, 20)");
//            swrlImp.enable();
//        } catch (SWRLParseException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        Test test = testFactory.createTest("TEST_B");
//
////        test.setTestValue(3, ontModel);
//
//        ontModel = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//        ontModel.add(owlModel.getJenaModel());
//
//        System.out.println(test.hasTestValue());
//
//        System.out.println(test.getTestValue(ontModel));

        //addBehaviour(new RLFacilityManagementBehavior(this, 1000, modelAccess));
        addBehaviour(new RLServiceCenterServersManagement(this, modelAccess, 1000));
        addBehaviour(new ReceiveMessageRLBehaviour(this, modelAccess));
    }
}
