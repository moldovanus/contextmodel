package globalLoop.agents;

import globalLoop.agents.behaviors.RLServiceCenterServersManagement;
import jade.core.Agent;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.util.ModelAccess;
import model.interfaces.Goal;
import model.interfaces.actions.*;
import model.interfaces.policies.EnvironmentPolicy;
import model.interfaces.policies.ITComputingContextPolicy;
import model.interfaces.resources.ITFacilityActiveResource;
import model.interfaces.resources.ITFacilityPassiveResource;
import model.interfaces.resources.Sensor;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 12, 2010
 * Time: 10:41:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class RLAgent extends Agent {
    private ModelAccess modelAccess;

    @Override
    protected void setup() {
        System.out.println("RLAgent " + getLocalName() + " started.");

        modelAccess = new ModelAccess(new OntologyModelFactory(), null, null);


        FacilityDefaultAction facilityDefaultAction = modelAccess.createFacilityDefaultAction("Test_Action");

        facilityDefaultAction.setEffect(new ActionEffect() {
            double oldValue;

            public void execute(ITFacilityPassiveResource resource) {
                oldValue = resource.getRecordedValue();
                resource.setRecordedValue(6.0);
            }

            public void undo(ITFacilityPassiveResource resource) {
                resource.setRecordedValue(oldValue);
            }
        });

        DeployActivity deployActivity = modelAccess.createDeployActivity("DeployAct_1");
        ApplicationActivity applicationActivity = modelAccess.createApplicationActivity("ApplActivity_1");
        ServiceCenterITComputingResource resourceTo = modelAccess.createServiceCenterServer("CompResource_1");
        resourceTo.setCurrentEnergyState(0);
        resourceTo.setResourceID("aaa");
        resourceTo.setCurrentWorkLoad(30.0);
        resourceTo.setMaximumWorkLoad(59.0);
        resourceTo.setOptimalWorkLoad(20.0);

        deployActivity.setActivity(applicationActivity);
        deployActivity.setResourceTo(resourceTo);

        ITComputingContextPolicy itComputingContextPolicy = modelAccess.createITComputingContextPolicy("Policy_1");
        itComputingContextPolicy.setRespected(true);
        itComputingContextPolicy.addPolicySubject(resourceTo);


        Goal goal = new Goal() {
            public boolean isAchieved(ITFacilityPassiveResource resource) {
                double recordedValue = resource.getRecordedValue();
                return recordedValue > 5 && recordedValue < 15;
            }
        };

        ITFacilityResourceAdaptationAction adaptationAction = modelAccess.createITFacilityResourceAdaptationAction("TEST_ITFRAA");
        Sensor passiveResource = modelAccess.createSensor("TEST_ITPR");

        ITFacilityActiveResource activeResource = modelAccess.createITFacilityActiveResource("TEST_ITFAR");
//        adaptationAction.addAssociatedResources(passiveResource);
        adaptationAction.setFacilityAction(facilityDefaultAction);
        activeResource.addAssociatedAction(adaptationAction);
        facilityDefaultAction.addResource(passiveResource);

        EnvironmentPolicy policy = modelAccess.createEnvironmentPolicy("TEST_ENVP");
        policy.setPolicyGoal(goal);
        policy.setPolicyAction(adaptationAction);
        policy.addPolicySubject(passiveResource);
        policy.addPolicyTarget(activeResource);

        ContextAction action = policy.getPolicyAction();
        passiveResource.setRecordedValue(0.0);
        System.out.println(passiveResource.getRecordedValue());
        action.execute(modelAccess);
        System.out.println(passiveResource.getRecordedValue());
        action.undo(modelAccess);
        System.out.println(passiveResource.getRecordedValue());


        //addBehaviour(new RLFacilityManagementBehavior(this, 1000, modelAccess));
        addBehaviour(new RLServiceCenterServersManagement(this, modelAccess, 1000));

    }
}
