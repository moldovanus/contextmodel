package model.interfaces.policies;

import model.interfaces.Goal;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:19:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GPI_KPI_Policy extends ContextPolicy {
    Goal getPolicyGoal();

    void setPolicyGoal();

    //ContextAction getPolicyAction();
    //void setPolicyAction(ContextAction contextAction);


}
