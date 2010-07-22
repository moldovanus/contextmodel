package evaluation;

import model.impl.databaseImpl.DatabaseModelFactory;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.prevailerImpl.PrevailerModelFactory;
import model.impl.util.ModelAccess;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 15, 2010
 * Time: 11:31:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class InstanceGenerator {

    public static ModelAccess getModelAccessInstance(int accessType) {
        OntologyModelFactory ontologyModelFactory = (accessType == ModelAccess.ONTOLOGY_ACCESS) ? new OntologyModelFactory() : null;
        DatabaseModelFactory databaseModelFactory = (accessType == ModelAccess.DATABASE_ACCESS) ? new DatabaseModelFactory() : null;
        PrevailerModelFactory prevailerModelFactory = (accessType == ModelAccess.PREVAYLER_ACCESS) ? new PrevailerModelFactory() : null;

        ModelAccess modelAccess = new ModelAccess(ontologyModelFactory, databaseModelFactory, prevailerModelFactory);
        modelAccess.setAccessType(accessType);
        return modelAccess;
    }

    public static ModelAccess generateComplexResourceInstances(int instanceCount, int accessType) {


        OntologyModelFactory ontologyModelFactory = (accessType == ModelAccess.ONTOLOGY_ACCESS) ? new OntologyModelFactory() : null;
        DatabaseModelFactory databaseModelFactory = (accessType == ModelAccess.DATABASE_ACCESS) ? new DatabaseModelFactory() : null;
        PrevailerModelFactory prevailerModelFactory = (accessType == ModelAccess.PREVAYLER_ACCESS) ? new PrevailerModelFactory() : null;

        ModelAccess modelAccess = new ModelAccess(ontologyModelFactory, databaseModelFactory, prevailerModelFactory);
        modelAccess.setAccessType(accessType);

        for (int i = 0; i < instanceCount; i++) {
            ComplexResource complexResource = modelAccess.createComplexResource("ComplexResource_" + i);
            List<String> workload = new ArrayList<String>();
            List<ServiceCenterITComputingResource> resources = new ArrayList<ServiceCenterITComputingResource>();
            for (int j = 0; j < instanceCount; j++) {
                workload.add(complexResource.getName() + "Workload_" + j);
                ServiceCenterITComputingResource resource =
                        modelAccess.createServiceCenterITComputingResource(complexResource.getName() + "_SCITCR_" + j);
                List<Integer> integer = new ArrayList<Integer>();
                List<ApplicationActivity> activities = new ArrayList<ApplicationActivity>();
                resource.setCurrentEnergyState(0);
                resource.setMaximumWorkLoad(0.0);
                resource.setOptimalWorkLoad(0.0);
                resource.setCurrentWorkLoad(0.0);
                for (int k = 0; k < instanceCount; k++) {
                    integer.add(k);
                    ApplicationActivity activity =
                            modelAccess.createHDDIntensiveActivity(
                                    complexResource.getName() + "_" + resource.getName()
                                            + "_" + "AA_" + k);
                    activity.setCPUAllocatedValue(0.0);
                    activity.setHDDAllocatedValue(0.0);
                    activity.setMEMAllocatedValue(0.0);

                    activity.setCPURequiredValue(0.0);
                    activity.setHDDRequiredValue(0.0);
                    activity.setMEMRequiredValue(0.0);

                    activity.setPerformanceDegradation(0.0);
                    activity.setPerformanceEstimation(0.0);
                    activities.add(activity);
                    //modelAccess.persistEntity(activity);
                }
                resource.setEnergyStates(integer);
                resource.setRunningActivities(activities);
                //modelAccess.persistEntity(resource);
                resources.add(resource);
            }
            complexResource.setResourceWorkloadProperty(workload);
            complexResource.setResources(resources);
            modelAccess.persistEntity(complexResource);
        }

//         ComplexResource resource = modelAccess.createComplexResource("Test");
//        List<String> workload = new ArrayList<String>();
//        List<ServiceCenterITComputingResource> resources = new ArrayList<ServiceCenterITComputingResource>();
//        ServiceCenterITComputingResource r =
//                modelAccess.createServiceCenterITComputingResource("_SCITCR_");
//        List<Integer> integer = new ArrayList<Integer>();
//        List<ApplicationActivity> activities = new ArrayList<ApplicationActivity>();
//
//        ApplicationActivity activity =
//                modelAccess.createApplicationActivity(
//                        "_" + "AA_");
//        activity.setCPUAllocatedValue(0.0);
//        activity.setHDDAllocatedValue(0.0);
//        activity.setMEMAllocatedValue(0.0);
//
//        activity.setCPURequiredValue(0.0);
//        activity.setHDDRequiredValue(0.0);
//        activity.setMEMRequiredValue(0.0);
//
//        activity.setPerformanceDegradation(0.0);
//        activity.setPerformanceEstimation(0.0);
//        activities.add(activity);
//        modelAccess.persistEntity(activity);
//
//        r.setEnergyStates(integer);
//        r.setRunningActivities(activities);
//        modelAccess.persistEntity(r);
//        resources.add(resource);
//
//         resource.setResourceWorkloadProperty(workload);
//         resource.setResources(resources);
//         modelAccess.persistEntity(resource);

        return modelAccess;

    }
}
