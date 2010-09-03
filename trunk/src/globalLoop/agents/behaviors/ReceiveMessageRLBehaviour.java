/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package globalLoop.agents.behaviors;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import globalLoop.agents.RLAgent;
import globalLoop.utils.GlobalVars;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
//import selfoptimizing.actionEnforcement.command.selfOptimizingCommand.RemoveTaskFromServerCommand;
//import selfoptimizing.contextaware.GlobalVars;
//import selfoptimizing.contextaware.agents.ReinforcementLearningAgent;
//import selfoptimizing.ontologyRepresentations.greenContextOntology.*;
import model.impl.util.ModelAccess;
import model.interfaces.policies.BusinessPolicy;
import model.interfaces.policies.QoSPolicy;
import model.interfaces.resources.ContextResource;
import model.interfaces.resources.applications.Application;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.proxies.impl.ProxyFactory;
import utils.worldInterface.dtos.TaskDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
                    String content = (String) message.getContent();
                    if (content.equals("BROKEN")) {
                        // agent.setContextIsOK(false);
                    } else if (content.equals("OK")) {
                        // agent.setContextIsOK(true);
                    } else {
                        TaskDto taskDto = (TaskDto) message.getContentObject();
                        ApplicationActivity task = modelAccess.createApplicationActivity(taskDto.getTaskName());
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa          " + taskDto.getTaskName());
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
                        task.setResourceID("Task_" + taskDto.getTaskName());

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
                                    " -> isRespected(" + task.getName() + ", true)";
                            SWRLImp imp = factory.createImp(swrlRule);
                            imp.enable();
                        } catch (SWRLParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                    break;
                case ACLMessage.INFORM_IF:
//                    System.out.println("http://www.owl-ontologies.com/Datacenter.owl#" + message.getContent().split("-")[0]);
//
//                    Task selectedTask = protegeFactory.getTask("http://www.owl-ontologies.com/Datacenter.owl#" + message.getContent().split("-")[0]);
//                    if (selectedTask.getAssociatedServer() != null) {
//                        RemoveTaskFromServerCommand command = new RemoveTaskFromServerCommand(protegeFactory, selectedTask.getName(), selectedTask.getAssociatedServer().getName());
//                        command.execute(jenaModel);
//                        command.executeOnX3D(agent);
//                        command.executeOnWebService();
//                        ServerManagementProxyInterface management;
//
//                        if (selectedTask.getAssociatedServer() != null) {
//                            management = ProxyFactory.createServerManagementProxy(selectedTask.getAssociatedServer().getServerIPAddress());
//                            management.deleteVirtualMachine(selectedTask.getTaskName());
//                        }
//
//                        selectedTask.delete();
//
//
//                        agent.sendAllTasksToClient();
//                    } else {
//                        agent.sendRefuseMessage();
//                    }
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
