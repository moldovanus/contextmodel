package model.interfaces.policies;

import model.interfaces.ContextElement;
import model.interfaces.Event;
import model.interfaces.resources.ContextResource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:16:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextPolicy extends ContextElement {
    String getPolicyName();

    void setPolicyName(String policyName);

    List<ContextResource> getPolicySubject();

    void setPolicySubject(List<ContextResource> policySubject);

    void addPolicySubject(ContextResource policySubject);

    void removePolicySubject(ContextResource policySubject);

    List<ContextResource> getPolicyTarget();

    void setPolicyTarget(List<ContextResource> policyTarget);

    void addPolicyTarget(ContextResource policySubject);

    void removePolicyTarget(ContextResource policySubject);

    Event getTriggerEvent();

    void setTriggerEvent(Event event);

    String getEvaluationCondition();

    void setEvaluationCondition(String condition);

//    boolean isRespected();
//

    void setRespected(boolean value);
//

    public String getIsRespectedPropertyName();
    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#policyWeight

    float getPolicyWeight();

    boolean hasPolicyWeight();

    void setPolicyWeight(float newPolicyWeight);
}
