package model.interfaces;

import jade.core.Agent;
import model.impl.util.ModelAccess;
import model.interfaces.actions.ContextAction;

import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 9, 2010
 * Time: 12:14:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextSnapshot implements Comparable {
    private Queue<ContextAction> actions;
    private double contextEntropy = 0;
    private double rewardFunction = 0;
    public final static double gamma = 0.5d;

    public ContextSnapshot(final Queue<ContextAction> actions) {
        this.actions = actions;
    }

    public double getContextEntropy() {
        return contextEntropy;
    }

    public void setContextEntropy(double contextEntropy) {
        this.contextEntropy = contextEntropy;
    }

    public void addActions(Queue<ContextAction> commands) {
        for (ContextAction command : commands) {
            actions.add(command);
        }
    }

    public void executeActions(ModelAccess modelAccess) {
        for (ContextAction command : actions) {
            command.execute(modelAccess);
        }
    }
//    // TODO: de scris metodele
//    public void executeActionsOnOWL() {
//        for (ContextAction command : actions) {
//            command.executeOnWebService();
//        }
//    }
//

    /**
     * Rewinds in the inverse order
     */
    public void undoAllActions(ModelAccess model) {

        Object[] commands = actions.toArray();

        for (int i = commands.length - 1; i >= 0; i--) {
            ContextAction command = (ContextAction) commands[i];
            command.undo(model);
            //System.out.println("Rewinding  " + command.toString());
        }
    }

    public Queue<ContextAction> getActions() {
        return actions;
    }

    public void setActions(final Queue<ContextAction> actions) {
        this.actions = actions;
    }

    /**
     * @return the rewardFunction
     */
    public double getRewardFunction() {
        return rewardFunction;
    }

    /**
     * @param rewardFunction the rewardFunction to set
     */
    public void setRewardFunction(double rewardFunction) {
        this.rewardFunction = rewardFunction;
    }

    /* public int compareTo(Object o) {

  if (o.getClass() != this.getClass())
      return 0;
  ContextSnapshot cs = (ContextSnapshot) o;
  if (cs.contextEntropy < contextEntropy)
      return 1;
  else if (cs.contextEntropy == contextEntropy) {
      if (cs.rewardFunction < rewardFunction) {
          return 1;
      } else if (cs.rewardFunction == rewardFunction) {
          return 0;
      } else {
          return -1;
      }
  } else return -1;
}      */

    public int compareTo(Object o) {
        if (o.getClass() != this.getClass())
            return 0;
        ContextSnapshot cs = (ContextSnapshot) o;
        if (cs.rewardFunction < rewardFunction) {
            return -1;
        } else if (cs.rewardFunction == rewardFunction) {
            return 0;
        } else {
            return 1;
        }
    }

    public String toString() {
        String description;
        description = "Entropy: " + this.contextEntropy + " Reward: " + this.rewardFunction;
        return description;
    }

    public void executeActionsOnX3D(Agent agent) {
        for (ContextAction ContextAction : actions) {
//            ContextAction.executeOnX3D(agent);
        }
    }

    public void executeOnServiceCenter(ModelAccess modelAccess) {
        for (ContextAction contextAction : actions) {
            contextAction.executeOnServiceCenter(modelAccess);
        }
    }

//    public void rewindOnX3D(ReinforcementLearningAgent agent) {
//        Object[] ContextActions = actions.toArray();
//
//        for (int i = ContextActions.length - 1; i >= 0; i--) {
//            ContextAction ContextAction = (ContextAction) ContextActions[i];
//            ContextAction.rewindOnX3D(agent);
//            //System.out.println("Rewinding  " + ContextAction.toString());
//        }
//    }
}
