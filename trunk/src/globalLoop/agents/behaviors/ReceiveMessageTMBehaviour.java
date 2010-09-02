package globalLoop.agents.behaviors;

import globalLoop.agents.TMAgent;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import utils.worldInterface.dtos.TaskDto;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: Apr 15, 2010
 * Time: 5:42:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveMessageTMBehaviour extends CyclicBehaviour {

    private TMAgent agent;

    public ReceiveMessageTMBehaviour(Agent agent) {
        super(agent);
        this.agent = (TMAgent) agent;
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
                    TaskDto[] tasks = (TaskDto[]) message.getContentObject();
                    //agent.clear();
                    agent.populateTaskWindow(tasks);
                    agent.clearFields();
                    break;

                case ACLMessage.INFORM:

                    break;

                case ACLMessage.SUBSCRIBE:

                    break;
                case ACLMessage.REFUSE:
                    JOptionPane.showMessageDialog(null, message.getContent(), "The transaction wasn't effectuated", 1);
                    //agent.reenableTasks();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
