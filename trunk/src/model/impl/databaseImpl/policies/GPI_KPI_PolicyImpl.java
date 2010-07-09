package model.impl.databaseImpl.policies;

import model.interfaces.Goal;
import model.interfaces.policies.GPI_KPI_Policy;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:35:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class GPI_KPI_PolicyImpl extends ContextPolicyImpl implements GPI_KPI_Policy {
    private Goal policyGoal;

    public Goal getPolicyGoal() {
        return policyGoal;
    }

    public void setPolicyGoal(Goal policyGoal) {
        this.policyGoal = policyGoal;
    }
}
