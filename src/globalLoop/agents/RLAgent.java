package globalLoop.agents;

import com.hp.hpl.jena.ontology.OntModel;
import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLFactory;
import edu.stanford.smi.protegex.owl.swrl.model.SWRLImp;
import edu.stanford.smi.protegex.owl.swrl.parser.SWRLParseException;
import globalLoop.agents.behaviors.RLServiceCenterServersManagement;
import globalLoop.agents.behaviors.ReceiveMessageRLBehaviour;
import globalLoop.utils.GlobalVars;
import gui.datacenterConfiguration.impl.ConfigurationGUI;
import jade.core.Agent;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.util.ModelAccess;

import java.io.File;

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
//        ConfigurationGUI gui = new ConfigurationGUI(modelAccess);
//        gui.setVisible(true);

//        FacilityDefaultAction facilityDefaultAction = modelAccess.createFacilityDefaultAction("Test_Action");
//
//        facilityDefaultAction.setEffect(new ActionEffect() {
//            double oldValue;
//
//            public void execute(ITFacilityPassiveResource resource) {
//                oldValue = resource.getRecordedValue();
//                resource.setRecordedValue(6.0);
//            }
//
//            public void undo(ITFacilityPassiveResource resource) {
//                resource.setRecordedValue(oldValue);
//            }
//        });
//
//        DeployActivity deployActivity = modelAccess.createDeployActivity("DeployAct_1");
//        ApplicationActivity applicationActivity = modelAccess.createApplicationActivity("ApplActivity_1");
//        ServiceCenterITComputingResource resourceTo = modelAccess.createServiceCenterServer("CompResource_1");
//        resourceTo.setCurrentEnergyState(0);
//        resourceTo.setResourceID("aaa");
//        resourceTo.setCurrentWorkLoad(30.0);
//        resourceTo.setMaximumWorkLoad(59.0);
//        resourceTo.setOptimalWorkLoad(20.0);
//
//        deployActivity.setActivity(applicationActivity);
//        deployActivity.setResourceTo(resourceTo);
//
//        ITComputingContextPolicy itComputingContextPolicy = modelAccess.createITComputingContextPolicy("Policy_1");
//        itComputingContextPolicy.setRespected(true);
//        itComputingContextPolicy.addPolicySubject(resourceTo);
//
//
//        Goal goal = new Goal() {
//            public boolean isAchieved(ITFacilityPassiveResource resource) {
//                double recordedValue = resource.getRecordedValue();
//                return recordedValue > 5 && recordedValue < 15;
//            }
//        };
//
//        ITFacilityResourceAdaptationAction adaptationAction = modelAccess.createITFacilityResourceAdaptationAction("TEST_ITFRAA");
//        Sensor passiveResource = modelAccess.createSensor("TEST_ITPR");
//
//        ITFacilityActiveResource activeResource = modelAccess.createITFacilityActiveResource("TEST_ITFAR");
////        adaptationAction.addAssociatedResources(passiveResource);
//        adaptationAction.setFacilityAction(facilityDefaultAction);
//        activeResource.addAssociatedAction(adaptationAction);
//        facilityDefaultAction.addResource(passiveResource);
//
//        EnvironmentPolicy policy = modelAccess.createEnvironmentPolicy("TEST_ENVP");
//        policy.setPolicyGoal(goal);
//        policy.setPolicyAction(adaptationAction);
//        policy.addPolicySubject(passiveResource);
//        policy.addPolicyTarget(activeResource);
//
//        ContextAction action = policy.getPolicyAction();
//        passiveResource.setRecordedValue(0.0);
//        System.out.println(passiveResource.getRecordedValue());
//        action.execute(modelAccess);
//        System.out.println(passiveResource.getRecordedValue());
//        action.undo(modelAccess);
//        System.out.println(passiveResource.getRecordedValue());
//        OWLModel owlModel = null;
//        OntModel ontModel = null;
//        File file = new File(GlobalVars.ONTOLOGY_FILE);
//        try {
//            owlModel = ProtegeOWL.createJenaOWLModelFromURI(file.toURI().toString());
//            ontModel = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//            ontModel.add(owlModel.getJenaModel());
//            ontModel.add(owlModel.getOntModel());
//        } catch (OntologyLoadException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        TestFactory testFactory = new TestFactory(owlModel);
//        SWRLFactory factory = new SWRLFactory(owlModel);
//
//        try {
//            SWRLImp swrlImp = factory.createImp("Test(?x) ->  testValue(?x, 20)");
//            swrlImp.enable();
//        } catch (SWRLParseException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        Test test = testFactory.createTest("TEST_B");
//
////        test.setTestValue(3, ontModel);
//
//        ontModel = com.hp.hpl.jena.rdf.model.ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//        ontModel.add(owlModel.getJenaModel());
//
//        System.out.println(test.hasTestValue());
//
//        System.out.println(test.getTestValue(ontModel));

        //addBehaviour(new RLFacilityManagementBehavior(this, 1000, modelAccess));
        addBehaviour(new RLServiceCenterServersManagement(this, modelAccess, 1000));
        addBehaviour(new ReceiveMessageRLBehaviour(this,modelAccess));
    }
}
