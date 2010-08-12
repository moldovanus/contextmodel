package globalLoop.agents;

import com.hp.hpl.jena.ontology.OntModel;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import globalLoop.utils.GlobalVars;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 12, 2010
 * Time: 10:31:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class CMAAgent extends Agent {
    private AgentController rlAgentController;

    @Override
    protected void setup() {
        System.out.println("CMA Agent " + getLocalName() + " started.");

        AgentContainer container = (AgentContainer) getContainerController();
        try {
            rlAgentController = container.createNewAgent(GlobalVars.RLAGENT_NAME, RLAgent.class.getName(), new Object[]{});
            rlAgentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
