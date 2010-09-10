package globalLoop.agents.behaviors;

import globalLoop.agents.GUIAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class ReceiveChangesGUIBehaviour extends CyclicBehaviour {

    private GUIAgent guiAgent;
    private List<String> receivedMessages;

    public ReceiveChangesGUIBehaviour(GUIAgent guiAgent) {
        this.guiAgent = guiAgent;
        receivedMessages = new ArrayList<String>(10);
    }

    @Override
    public void action() {
        ACLMessage message = guiAgent.receive();
        if (message == null) return;


//        try {
//            Object[] messageContent = (Object[]) message.getContentObject();
//            if (messageContent[0].equals("SelfHealingMonitor")) {
//                if (messageContent[1].equals("actionsList")) {
//                    guiAgent.setEnviromentMonitorActionsList((ArrayList<String[]>) messageContent[2]);
//                } else if (messageContent[1].equals("brokenStatesList")) {
//                    guiAgent.setEnviromentMonitorBrokenStatesList((ArrayList<String[]>) messageContent[2]);
//                } else if (messageContent[1].equals("brokenPoliciesList")) {
//                    guiAgent.setEnviromentMonitorBrokenPoliciesList((ArrayList<String>) messageContent[2]);
//                }
//            }else if (messageContent[0].equals("EnviromentLogger")) {
//                guiAgent.logEnviromentManagementInformation((Color)messageContent[1],(String)messageContent[2],(ArrayList)messageContent[3]);
//            }else if (messageContent[0].equals("DatacenterLogger")) {
//                guiAgent.logDatacenterManagementInformation((Color)messageContent[1],(String)messageContent[2],(ArrayList)messageContent[3]);
//            }
//        } catch (UnreadableException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


        try {
            switch (message.getPerformative()) {
                case ACLMessage.INFORM:
                    Object[] messageContent = (Object[]) message.getContentObject();
                    if (messageContent[0].equals("Running time")) {
                        guiAgent.notifyObservers(new Object[]{messageContent[0], messageContent[1]});
                    } else {
                        guiAgent.notifyObservers(new Object[]{messageContent[0], messageContent[1]});
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
