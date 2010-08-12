package model.interfaces.policies;

import model.interfaces.Goal;
import model.interfaces.actions.ContextAction;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:19:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GPI_KPI_Policy extends ContextPolicy {
    Goal getPolicyGoal();

    void setPolicyGoal(Goal goal);

    //ContextAction getPolicyAction();
    //void setPolicyAction(ContextAction contextAction);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#policyAction

    ContextAction getPolicyAction();

    boolean hasPolicyAction();

    void setPolicyAction(ContextAction newPolicyAction);

}
