package model.impl.databaseImpl.ontology.policies;

import model.impl.databaseImpl.ontology.ContextElementImpl;
import model.interfaces.Event;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.resources.ContextResource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:32:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContextPolicyImpl extends ContextElementImpl implements ContextPolicy {
    private String policyName;
    private String evaluationCondition;

    private boolean respected;
    private List<ContextResource> policySubject;
    private List<ContextResource> policyTarget;

    private Event triggerEvent;

    public ContextPolicyImpl() {
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getEvaluationCondition() {
        return evaluationCondition;
    }

    public void setEvaluationCondition(String evaluationCondition) {
        this.evaluationCondition = evaluationCondition;
    }

    public boolean isRespected() {
        return respected;
    }

    public void setRespected(boolean respected) {
        this.respected = respected;
    }

    public List<ContextResource> getPolicySubject() {
        return policySubject;
    }

    public void setPolicySubject(List<ContextResource> policySubject) {
        this.policySubject = policySubject;
    }

    public List<ContextResource> getPolicyTarget() {
        return policyTarget;
    }

    public void setPolicyTarget(List<ContextResource> policyTarget) {
        this.policyTarget = policyTarget;
    }

    public Event getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(Event triggerEvent) {
        this.triggerEvent = triggerEvent;
    }
}
