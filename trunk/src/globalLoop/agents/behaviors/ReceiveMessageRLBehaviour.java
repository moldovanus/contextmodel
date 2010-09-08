/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package globalLoop.agents.behaviors;

import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import globalLoop.agents.RLAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
//import selfoptimizing.actionEnforcement.command.selfOptimizingCommand.RemoveTaskFromServerCommand;
//import selfoptimizing.contextaware.GlobalVars;
//import selfoptimizing.contextaware.agents.ReinforcementLearningAgent;
//import selfoptimizing.ontologyRepresentations.greenContextOntology.*;
import main.PelletJena;
import model.impl.util.ModelAccess;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ExtendedTaskDto;
import utils.worldInterface.dtos.TaskDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Moldovanus
 */
public class ReceiveMessageRLBehaviour extends CyclicBehaviour {

    private RLAgent agent;
    private SWRLFactory swrlFactory;
    private ModelAccess modelAccess;

    public ReceiveMessageRLBehaviour(Agent agent, ModelAccess modelAccess) {
        super(agent);
        this.modelAccess = modelAccess;
        this.agent = (RLAgent) agent;
    }

    @Override
    public void action() {
        ACLMessage message = agent.receive();
        if (message == null) {
            return;
        }


        try {
            switch (message.getPerformative()) {
                case ACLMessage.INFORM_REF:
//                    String individualName = (String) message.getContentObject();
//                    final RDFResource individual = owlModel.getRDFResource(individualName);
//                    if (!individual.getProtegeType().getNamedSuperclasses(true).contains(owlModel.getRDFSNamedClass("sensor"))) {
//                        return;
//                    }
//
//                    RDFProperty urlProperty = owlModel.getRDFProperty("has-web-service-URI");
//                    String url = individual.getPropertyValue(urlProperty).toString();
//
//                    //register the web service URL read from the external file into the jena ont model
//                    Individual sensor = jenaModel.getIndividual(GlobalVars.base + "#" + individualName);
//                    Property urlJenaProperty = jenaModel.getDatatypeProperty(GlobalVars.base + "#has-web-service-URI");
//                    sensor.setPropertyValue(urlJenaProperty, jenaModel.createLiteralStatement(
//                            sensor, urlJenaProperty, url).getLiteral().as(RDFNode.class));
                    break;

                case ACLMessage.INFORM:
                    Object content = message.getContent();
                    if (content.equals("BROKEN")) {
                        // agent.setContextIsOK(false);
                    } else if (content.equals("OK")) {
                        // agent.setContextIsOK(true);
                    } else {
                        Object[] contentData = (Object[]) content;
                        Object dataType = contentData[0];
                        if (dataType.equals("Task added")) {
                            TaskDto taskDto = (TaskDto) contentData[1];
                            ApplicationActivity task = modelAccess.createApplicationActivity(taskDto.getTaskName());
//                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa          " + taskDto.getTaskName());
                            QoSPolicy policy = modelAccess.createQoSPolicy(taskDto.getTaskName() + "_QoSPolicy");
                            System.out.println(policy);
                            List<ContextResource> subjects = new ArrayList<ContextResource>(1);
                            subjects.add(task);
                            policy.setPolicySubject(subjects);
                            policy.setPolicyTarget(subjects);
                            policy.setPolicyName(taskDto.getTaskName() + "_QoSPolicy");
                            policy.setPolicyWeight(1);
                            task.setCpuAllocatedValue(0);
                            task.setCpuRequiredMaxValue(taskDto.getRequestedCPUMax());
                            task.setCpuRequiredMinValue(taskDto.getRequestedCPUMin());
                            task.setMemAllocatedValue(0);
                            task.setMemRequiredMaxValue(taskDto.getRequestedMemoryMax());
                            task.setMemRequiredMinValue(taskDto.getRequestedMemoryMin());
                            task.setHddAllocatedValue(0);
                            task.setHddRequiredMaxValue(taskDto.getRequestedStorageMax());
                            task.setHddRequiredMinValue(taskDto.getRequestedStorageMin());
                            task.setNumberOfCoresAllocatedValue(0);
                            task.setNumberOfCoresRequiredValue(taskDto.getRequestedCores());
                            task.setCpuWeight(0.3f);
                            task.setMemWeight(0.3f);
                            task.setHddWeight(0.3f);
                            //task.setResourceID("Task_" + taskDto.getTaskName());
                            task.setResourceID(task.getFrameID().getName());
                            task.addActivityPolicies(policy);
                            SWRLFactory factory = new SWRLFactory(modelAccess.getOntologyModelFactory().getOwlModel());
                            String swrlRule = "";
                            try {
                                swrlRule = "ApplicationActivity(?x) ^ resourceID(?x,\"" + task.getResourceID() + "\") " +
                                        " ^ memAllocatedValue(?x,?memAllocated)" +
                                        " ^ memRequiredMaxValue(?x, ?memMax)" +
                                        " ^ memRequiredMinValue(?x,?memMin)" +
                                        " ^ swrlb:lessThanOrEqual(?memMin,?memAllocated)" +
                                        " ^ swrlb:lessThanOrEqual(?memAllocated,?memMax) \n " +
                                        " ^ hddAllocatedValue(?x,?hddAllocated) ^ hddRequiredMaxValue(?x, ?hddMax) ^ swrlb:lessThanOrEqual(?hddAllocated,?hddMax) ^ hddRequiredMinValue(?x,?hddMin) ^ swrlb:lessThanOrEqual(?hddMin,?hddAllocated) \n" +
                                        " ^ cpuAllocatedValue(?x,?cpuAllocated) ^ cpuRequiredMaxValue(?x, ?cpuMax) ^ cpuRequiredMinValue(?x,?cpuMin) ^ swrlb:lessThanOrEqual(?cpuMin, ?cpuAllocated) ^ swrlb:lessThanOrEqual(?cpuAllocated,?cpuMax) " +
                                        " -> isRespected(" + policy.getName() + ", true)";
                                SWRLImp imp = factory.createImp(policy.getName() + "_Rule", swrlRule);

                                //imp.enable();
                            } catch (SWRLParseException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        } else if (dataType.equals("Servers added")) {
                            List<ExtendedServerDto> servers = (List<ExtendedServerDto>) contentData[1];
                            for (ServiceCenterServer server : modelAccess.getAllServiceCenterServerInstances()) {
                                Collection<CPU> cpus = server.getCpuResources();
                                Collection<MEM> mems = server.getMemResources();
                                Collection<HDD> hdds = server.getHddResources();
                                for (CPU cpu : cpus) {
                                    Collection cores = cpu.getAssociatedCores();
                                    for (Object o : cores) {
                                        Core core = (Core) o;
                                        core.delete();
                                    }
                                    cpu.delete();
                                }
                                for (MEM memory : mems) {
                                    memory.delete();
                                }
                                for (HDD storage : hdds) {
                                    storage.delete();
                                }


                                Collection<ApplicationActivity> tasks = server.getRunningActivities();
                                for (Object task : tasks) {
                                    server.removeRunningActivity((ApplicationActivity) task);
                                }
                                server.delete();
                            }

                            for (ITComputingContextPolicy policy : modelAccess.getAllITComputingContextPolicyInstances()) {
                                policy.delete();
                            }
                            ArrayList<Integer> energyStates = new ArrayList<Integer>();
                            energyStates.add(0);
                            energyStates.add(1);

                            for (ExtendedServerDto dto : servers) {
                                String serverName = dto.getServerName();
                                ServiceCenterServer server = modelAccess.createServiceCenterServer(serverName);
                                server.setIpAddress(dto.getIpAddress());
                                server.setMacAddress(dto.getMacAddress());
                                server.setIsActive(false);
                                server.setResourceID(server.getName());
                                server.setEnergyStates(energyStates);
                                server.setCurrentEnergyState(0);
                                server.addResourceWorkloadProperty("Workload_Property");
                                CPU cpu = modelAccess.createCPU(serverName + "_CPU_");
                                cpu.setResourceID(cpu.getName());
                                cpu.setCurrentEnergyState(0);

                                server.setCPUWeight(0.5f);
                                int coreCount = dto.getCoreCount();
                                int maximumCPU = dto.getMaximumCPU();
                                int optimumCPU = dto.getOptimalCPU();
                                for (int i = 0; i < coreCount; i++) {
                                    Core core = modelAccess.createCore(serverName + "_Core_" + i);
                                    core.setMaximumWorkLoad((double) maximumCPU);
                                    core.setOptimalWorkLoad((double) optimumCPU);
                                    cpu.addAssociatedCore(core);
                                    core.setEnergyStates(energyStates);
                                    core.setResourceID(core.getName());
                                    core.addPartOf(cpu);
                                    core.setCurrentEnergyState(0);
                                }
                                cpu.setEnergyStates(energyStates);
                                server.addCpuResource(cpu);

                                MEM memory = modelAccess.createMEM(serverName + "_Memory");
                                memory.setResourceID(memory.getName());
                                memory.setMaximumWorkLoad((double) dto.getMaximumMemory());
                                memory.setOptimalWorkLoad((double) dto.getOptimalMemory());
                                memory.setEnergyStates(energyStates);
                                memory.setCurrentEnergyState(0);

                                server.setMEMWeight(0.25f);

                                server.addMemResource(memory);

                                HDD storage = modelAccess.createHDD(serverName + "_Storage");
                                storage.setResourceID(storage.getName());
                                storage.setMaximumWorkLoad((double) dto.getMaximumStorage());
                                storage.setOptimalWorkLoad((double) dto.getOptimalStorage());
                                storage.setEnergyStates(energyStates);
                                server.setHDDWeight(0.25f);
                                server.addHddResources(storage);
                                storage.setCurrentEnergyState(0);
                                storage.setPhysicalPath("//");

                                ITComputingContextPolicy policy =
                                        modelAccess.createITComputingContextPolicy(server.getLocalName() + "_EnergyPolicy");
                                policy.addPolicySubject(server);
                                policy.addPolicyTarget(server);
                                policy.setPolicyWeight(1.0f);
                                policy.setPolicyName(policy.getName());
                                policy.setEvaluationCondition("Evaluation_condition");

                                cpu.addPartOf(server);
                                memory.addPartOf(server);
                                storage.addPartOf(server);

                                PelletJena.generateEnergyRule((modelAccess.getOntologyModelFactory()).getOwlModel(), policy);
                            }


                        }
                        if (dataType.equals("Task added")) {
                            TaskDto taskDto = (TaskDto) contentData[1];
                            ApplicationActivity task = modelAccess.createApplicationActivity(taskDto.getTaskName());
                            QoSPolicy policy = modelAccess.createQoSPolicy(taskDto.getTaskName() + "_QoSPolicy");
                            System.out.println(policy);
                            List<ContextResource> subjects = new ArrayList<ContextResource>(1);
                            subjects.add(task);
                            policy.setPolicySubject(subjects);
                            policy.setPolicyTarget(subjects);
                            policy.setPolicyName(taskDto.getTaskName() + "_QoSPolicy");
                            policy.setPolicyWeight(1);
                            task.setCpuAllocatedValue(0);
                            task.setCpuRequiredMaxValue(taskDto.getRequestedCPUMax());
                            task.setCpuRequiredMinValue(taskDto.getRequestedCPUMin());
                            task.setMemAllocatedValue(0);
                            task.setMemRequiredMaxValue(taskDto.getRequestedMemoryMax());
                            task.setMemRequiredMinValue(taskDto.getRequestedMemoryMin());
                            task.setHddAllocatedValue(0);
                            task.setHddRequiredMaxValue(taskDto.getRequestedStorageMax());
                            task.setHddRequiredMinValue(taskDto.getRequestedStorageMin());
                            task.setNumberOfCoresAllocatedValue(0);
                            task.setNumberOfCoresRequiredValue(taskDto.getRequestedCores());
                            task.setCpuWeight(0.3f);
                            task.setMemWeight(0.3f);
                            task.setHddWeight(0.3f);
                            //task.setResourceID("Task_" + taskDto.getTaskName());
                            task.setResourceID(task.getFrameID().getName());
                            task.addActivityPolicies(policy);
                            SWRLFactory factory = new SWRLFactory(modelAccess.getOntologyModelFactory().getOwlModel());
                            String swrlRule = "";
                            PelletJena.generateBusinessRule((modelAccess.getOntologyModelFactory()).getOwlModel(), policy);
                        } else if (dataType.equals("Tasks added")) {
                            List<ExtendedTaskDto> tasks = (List<ExtendedTaskDto>) contentData[1];
                            for (ExtendedTaskDto taskDto : tasks) {
                                ApplicationActivity task = modelAccess.createApplicationActivity(taskDto.getTaskName());
                                QoSPolicy policy = modelAccess.createQoSPolicy(taskDto.getTaskName() + "_QoSPolicy");
                                System.out.println(policy);
                                List<ContextResource> subjects = new ArrayList<ContextResource>(1);
                                subjects.add(task);
                                policy.setPolicySubject(subjects);
                                policy.setPolicyTarget(subjects);
                                policy.setPolicyName(taskDto.getTaskName() + "_QoSPolicy");
                                policy.setPolicyWeight(1);
                                task.setCpuAllocatedValue(0);
                                task.setCpuRequiredMaxValue(taskDto.getRequestedCPUMax());
                                task.setCpuRequiredMinValue(taskDto.getRequestedCPUMin());
                                task.setMemAllocatedValue(0);
                                task.setMemRequiredMaxValue(taskDto.getRequestedMemoryMax());
                                task.setMemRequiredMinValue(taskDto.getRequestedMemoryMin());
                                task.setHddAllocatedValue(0);
                                task.setHddRequiredMaxValue(taskDto.getRequestedStorageMax());
                                task.setHddRequiredMinValue(taskDto.getRequestedStorageMin());
                                task.setNumberOfCoresAllocatedValue(0);
                                task.setNumberOfCoresRequiredValue(taskDto.getRequestedCores());
                                task.setCpuWeight(0.3f);
                                task.setMemWeight(0.3f);
                                task.setHddWeight(0.3f);
                                task.setResourceID(task.getFrameID().getName());
                                task.addActivityPolicies(policy);
                                SWRLFactory factory = new SWRLFactory(modelAccess.getOntologyModelFactory().getOwlModel());
                                String swrlRule = "";
                                PelletJena.generateBusinessRule((modelAccess.getOntologyModelFactory()).getOwlModel(), policy);
                            }
                        }
                    }
                    break;
                case ACLMessage.INFORM_IF:

                    ApplicationActivity selectedTask = modelAccess.getApplicationActivity("http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#" + message.getContent());
                    if (selectedTask.hasAssociatedServer()) {
                        selectedTask.getAssociatedServer().removeRunningActivity(selectedTask);
                        ServerManagementProxyInterface management = ProxyFactory.createServerManagementProxy(selectedTask.getAssociatedServer().getIpAddress());
                        management.deleteVirtualMachine(selectedTask.getLocalName());
                        agent.sendAllTasksToClient();
                    } else {
                        agent.sendRefuseMessage();
                    }
                    break;
                case ACLMessage.SUBSCRIBE:
                    Boolean value = (Boolean) message.getContentObject();
//                    agent.setContextDirty(value);
                    System.err.println("Context dirty received");
                    break;
                //http://www.owl-ontologies.com/Datacenter.owl#Task_1
            }
        } catch (Exception
                ex) {
            ex.printStackTrace(System.err);
        }
    }
}
