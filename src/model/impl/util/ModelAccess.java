package model.impl.util;


import model.impl.databaseImpl.DatabaseModelFactory;
import model.impl.ontologyImpl.OntologyModelFactory;
import model.impl.prevailerImpl.PrevailerModelFactory;
import model.interfaces.ContextElement;
import model.interfaces.actions.*;
import model.interfaces.policies.*;
import model.interfaces.resources.*;
import model.interfaces.resources.applications.*;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 15, 2010
 * Time: 9:58:53 AM
 * Delegates functionality to both OntologyModelFactory and DatabaseModelFactory
 */
public class ModelAccess implements ModelFactory {
    public static final int ONTOLOGY_ACCESS = 0;
    public static final int DATABASE_ACCESS = 1;
    public static final int PREVAYLER_ACCESS = 2;

    private OntologyModelFactory ontologyModelFactory;
    private DatabaseModelFactory databaseModelFactory;
    private PrevailerModelFactory prevailerModelFactory;

    public int accessType = 0;
    private boolean simulation = false;
    public OntologyModelFactory getOntologyModelFactory() {
        return ontologyModelFactory;
    }

    public DatabaseModelFactory getDatabaseModelFactory() {
        return databaseModelFactory;
    }

    public PrevailerModelFactory getPrevailerModelFactory() {
        return prevailerModelFactory;
    }

    private String generateUniqueName(String name) {
        return name + "_" + UUID.randomUUID().toString().substring(0, 3);
    }

    public ModelAccess(OntologyModelFactory ontologyModelFactory, DatabaseModelFactory databaseModelFactory,
                       PrevailerModelFactory prevailerModelFactory) {
        this.ontologyModelFactory = ontologyModelFactory;
        this.databaseModelFactory = databaseModelFactory;
        this.prevailerModelFactory = prevailerModelFactory;
    }


    public void setAccessType(int accessType) {
        this.accessType = accessType;
    }


    public void removeEntity(ContextElement entity) {
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                ontologyModelFactory.removeEntity(entity);
                break;
            case DATABASE_ACCESS:
                databaseModelFactory.removeEntity(entity);
                break;
            case PREVAYLER_ACCESS:
                prevailerModelFactory.removeEntity(entity);
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
    }

    public void persistEntity(ContextElement entity) {
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                ontologyModelFactory.persistEntity(entity);
                break;
            case DATABASE_ACCESS:
                databaseModelFactory.persistEntity(entity);
                break;
            case PREVAYLER_ACCESS:
                prevailerModelFactory.persistEntity(entity);
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
    }

    public SetServerStateActivity createSetServerStateActivity(String name) {
        SetServerStateActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createSetServerStateActivity(generateUniqueName(name));
            //databaseModelFactory.createDPMAction(name);
        } else {
            //ontologyModelFactory.createDPMAction(name);
            object = databaseModelFactory.createSetServerStateActivity(generateUniqueName(name));
        }
        return object;
    }

    public SetServerStateActivity getSetServerStateActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getSetServerStateActivity(name);
        } else {
            return databaseModelFactory.getSetServerStateActivity(name);
        }
    }

    public Core createCore(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.createCore(generateUniqueName(name));
        } else {
            return databaseModelFactory.createCore(generateUniqueName(name));
        }
    }

    public Core getCore(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getCore(name);
        } else {
            return databaseModelFactory.getCore(name);
        }
    }

    public Collection<Core> getAllCoreInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllCoreInstances();
        } else {
            return databaseModelFactory.getAllCoreInstances();
        }
    }


    public Collection<SetServerStateActivity> getAllSetServerStateActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllSetServerStateActivityInstances();
        } else {
            return databaseModelFactory.getAllSetServerStateActivityInstances();
        }
    }

    public FacilityDefaultAction createFacilityDefaultAction(String name) {
        FacilityDefaultAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createFacilityDefaultAction(generateUniqueName(name));
            //databaseModelFactory.createDPMAction(name);
        } else {
            //ontologyModelFactory.createDPMAction(name);
            object = databaseModelFactory.createFacilityDefaultAction(generateUniqueName(name));
        }
        return object;
    }

    public DPMAction createDPMAction(String name) {
        DPMAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createDPMAction(generateUniqueName(name));
            //databaseModelFactory.createDPMAction(name);
        } else {
            //ontologyModelFactory.createDPMAction(name);
            object = databaseModelFactory.createDPMAction(generateUniqueName(name));
        }
        return object;
    }

    public DPMAction getDPMAction(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getDPMAction(name);
        } else {
            return databaseModelFactory.getDPMAction(name);
        }
    }

    public Collection<DPMAction> getAllDPMActionInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllDPMActionInstances();
        } else {
            return databaseModelFactory.getAllDPMActionInstances();
        }
    }


    public ITComputingResourceAdaptationAction createITComputingResourceAdaptationAction(String name) {
        ITComputingResourceAdaptationAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createITComputingResourceAdaptationAction(generateUniqueName(name));
            // databaseModelFactory.createITComputingResourceAdaptationAction(name);
        } else {
            // ontologyModelFactory.createITComputingResourceAdaptationAction(name);
            object = databaseModelFactory.createITComputingResourceAdaptationAction(generateUniqueName(name));
        }
        return object;
    }

    public ITComputingResourceAdaptationAction getITComputingResourceAdaptationAction(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getITComputingResourceAdaptationAction(name);
        } else {
            return databaseModelFactory.getITComputingResourceAdaptationAction(name);
        }
    }

    public Collection<ITComputingResourceAdaptationAction> getAllITComputingResourceAdaptationActionInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllITComputingResourceAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllITComputingResourceAdaptationActionInstances();
        }
    }

//    public ContextAction createContextAction(String name) {
//        ContextAction object;
//        if (accessType == ONTOLOGY_ACCESS) {
//            object = ontologyModelFactory.createContextAction(name);
//            //databaseModelFactory.createContextAction(name);
//        } else {
//            //ontologyModelFactory.createContextAction(name);
//            object = databaseModelFactory.createContextAction(name);
//        }
//        return object;
//    }
//
//    public ContextAction getContextAction(String name) {
//        if (accessType == ONTOLOGY_ACCESS) {
//            return ontologyModelFactory.getContextAction(name);
//        } else {
//            return databaseModelFactory.getContextAction(name);
//        }
//    }
//
//    public Collection<ContextAction> getAllContextActionInstances() {
//        if (accessType == ONTOLOGY_ACCESS) {
//            return ontologyModelFactory.getAllContextActionInstances();
//        } else {
//            return databaseModelFactory.getAllContextActionInstances();
//        }
//    }

    public HDDIntensiveActivity createHDDIntensiveActivity(String name) {
        HDDIntensiveActivity object;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.createHDDIntensiveActivity(generateUniqueName(name));
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.createHDDIntensiveActivity(generateUniqueName(name));
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.createHDDIntensiveActivity(generateUniqueName(name));
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public HDDIntensiveActivity getHDDIntensiveActivity(String name) {
        HDDIntensiveActivity object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getHDDIntensiveActivity(name);
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getHDDIntensiveActivity(name);
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getHDDIntensiveActivity(name);
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public Collection<HDDIntensiveActivity> getAllHDDIntensiveActivityInstances() {
        Collection<HDDIntensiveActivity> object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getAllHDDIntensiveActivityInstances();
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getAllHDDIntensiveActivityInstances();
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getAllHDDIntensiveActivityInstances();
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ApplicationActivity createApplicationActivity(String name) {
        ApplicationActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            if (ontologyModelFactory.getApplicationActivity(name) != null) {
                object = ontologyModelFactory.createApplicationActivity(generateUniqueName(name));
            } else {
                object = ontologyModelFactory.createApplicationActivity((name));
            }
            // databaseModelFactory.createContextAction(name);
        } else {
            //ontologyModelFactory.createApplicationActivity(name);
            object = databaseModelFactory.createApplicationActivity(generateUniqueName(name));
        }
        return object;
    }

    public ApplicationActivity getApplicationActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getApplicationActivity(name);
        } else {
            return databaseModelFactory.getApplicationActivity(name);
        }
    }

    public Collection<ApplicationActivity> getAllApplicationActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllApplicationActivityInstances();
        } else {
            return databaseModelFactory.getAllApplicationActivityInstances();
        }
    }

    public GPI_KPI_Policy createGPI_KPI_Policy(String name) {
        GPI_KPI_Policy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createGPI_KPI_Policy((name));
            // databaseModelFactory.createGPI_KPI_Policy(name);
        } else {
            //ontologyModelFactory.createGPI_KPI_Policy(name);
            object = databaseModelFactory.createGPI_KPI_Policy((name));
        }
        return object;
    }

    public GPI_KPI_Policy getGPI_KPI_Policy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getGPI_KPI_Policy(name);
        } else {
            return databaseModelFactory.getGPI_KPI_Policy(name);
        }
    }

    public Collection<GPI_KPI_Policy> getAllGPI_KPI_PolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllGPI_KPI_PolicyInstances();
        } else {
            return databaseModelFactory.getAllGPI_KPI_PolicyInstances();
        }
    }


    public ContextPolicy createContextPolicy(String name) {
        ContextPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createContextPolicy((name));
            // databaseModelFactory.createContextPolicy(name);
        } else {
            // ontologyModelFactory.createContextPolicy(name);
            object = databaseModelFactory.createContextPolicy((name));
        }
        return object;
    }

    public ContextPolicy getContextPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getContextPolicy(name);
        } else {
            return databaseModelFactory.getContextPolicy(name);
        }
    }

    public Collection<ContextPolicy> getAllContextPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllContextPolicyInstances();
        } else {
            return databaseModelFactory.getAllContextPolicyInstances();
        }
    }

    public ServiceCenterITComputingResource createServiceCenterITComputingResource(String name) {
        ServiceCenterITComputingResource object;

        switch (accessType) {
            case ONTOLOGY_ACCESS:
                if (ontologyModelFactory.getServiceCenterITComputingResource(name) != null) {
                    object = ontologyModelFactory.createServiceCenterITComputingResource(generateUniqueName(name));
                } else {
                    object = ontologyModelFactory.createServiceCenterITComputingResource((name));
                }
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.createServiceCenterITComputingResource(generateUniqueName(name));
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.createServiceCenterITComputingResource(generateUniqueName(name));
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ServiceCenterITComputingResource getServiceCenterITComputingResource(String name) {
        ServiceCenterITComputingResource object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getServiceCenterITComputingResource(name);
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getServiceCenterITComputingResource(name);
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getServiceCenterITComputingResource(name);
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public Collection<ServiceCenterITComputingResource> getAllServiceCenterITComputingResourceInstances() {
        Collection<ServiceCenterITComputingResource> object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getAllServiceCenterITComputingResourceInstances();
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getAllServiceCenterITComputingResourceInstances();
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getAllServiceCenterITComputingResourceInstances();
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ContextResource getContextResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getContextResource(name);
        } else {
            return databaseModelFactory.getContextResource(name);
        }
    }

    public Collection<ContextResource> getAllContextResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllContextResourceInstances();
        } else {
            return databaseModelFactory.getAllContextResourceInstances();
        }
    }

    public ServiceCenterServer createServiceCenterServer(String name) {
        ServiceCenterServer object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createServiceCenterServer(generateUniqueName(name));
            //databaseModelFactory.createServiceCenterServer(name);
        } else {
            //ontologyModelFactory.createServiceCenterServer(name);
            object = databaseModelFactory.createServiceCenterServer(generateUniqueName(name));
        }
        return object;
    }

    public ServiceCenterServer getServiceCenterServer(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getServiceCenterServer(name);
        } else {
            return databaseModelFactory.getServiceCenterServer(name);
        }
    }

    public Collection<ServiceCenterServer> getAllServiceCenterServerInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllServiceCenterServerInstances();
        } else {
            return databaseModelFactory.getAllServiceCenterServerInstances();
        }
    }

    public ComplexResource createComplexResource(String name) {
        ComplexResource object;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.createComplexResource((name));
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.createComplexResource((name));
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.createComplexResource((name));
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ComplexResource getComplexResource(String name) {
        ComplexResource object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getComplexResource(name);
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getComplexResource(name);
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getComplexResource(name);
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public Collection<ComplexResource> getAllComplexResourceInstances() {
        Collection<ComplexResource> object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getAllComplexResourceInstances();
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getAllComplexResourceInstances();
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getAllComplexResourceInstances();
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ContextElement getContextElement(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getContextElement(name);
        } else {
            return databaseModelFactory.getContextElement(name);
        }
    }

    public Collection<ContextElement> getAllContextElementInstances() {
        Collection<ContextElement> object = null;
        switch (accessType) {
            case ONTOLOGY_ACCESS:
                object = ontologyModelFactory.getAllContextElementInstances();
                break;
            case DATABASE_ACCESS:
                object = databaseModelFactory.getAllContextElementInstances();
                break;
            case PREVAYLER_ACCESS:
                object = prevailerModelFactory.getAllContextElementInstances();
                break;
            default:
                throw new UnsupportedOperationException("Invalid access type");
        }
        return object;
    }

    public ServiceCenterITFacilityResource createServiceCenterITFacilityResource(String name) {
        ServiceCenterITFacilityResource object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createServiceCenterITFacilityResource((name));
            //databaseModelFactory.createServiceCenterITFacilityResource(name);
        } else {
            //ontologyModelFactory.createServiceCenterITFacilityResource(name);
            object = databaseModelFactory.createServiceCenterITFacilityResource((name));
        }
        return object;
    }

    public ServiceCenterITFacilityResource getServiceCenterITFacilityResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getServiceCenterITFacilityResource(name);
        } else {
            return databaseModelFactory.getServiceCenterITFacilityResource(name);
        }
    }

    public Collection<ServiceCenterITFacilityResource> getAllServiceCenterITFacilityResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllServiceCenterITFacilityResourceInstances();
        } else {
            return databaseModelFactory.getAllServiceCenterITFacilityResourceInstances();
        }
    }

    public BusinessContextResource createBusinessContextResource(String name) {
        BusinessContextResource object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createBusinessContextResource((name));
            //databaseModelFactory.createBusinessContextResource(name);
        } else {
            // ontologyModelFactory.createBusinessContextResource(name);
            object = databaseModelFactory.createBusinessContextResource((name));
        }
        return object;
    }

    public BusinessContextResource getBusinessContextResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getBusinessContextResource(name);
        } else {
            return databaseModelFactory.getBusinessContextResource(name);
        }
    }

    public Collection<BusinessContextResource> getAllBusinessContextResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllBusinessContextResourceInstances();
        } else {
            return databaseModelFactory.getAllBusinessContextResourceInstances();
        }
    }

    public Application createApplication(String name) {
        Application object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createApplication(generateUniqueName(name));
            // databaseModelFactory.createApplication(name);
        } else {
            // ontologyModelFactory.createApplication(name);
            object = databaseModelFactory.createApplication(generateUniqueName(name));
        }
        return object;
    }

    public Application getApplication(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getApplication(name);
        } else {
            return databaseModelFactory.getApplication(name);
        }
    }

    public Collection<Application> getAllApplicationInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllApplicationInstances();
        } else {
            return databaseModelFactory.getAllApplicationInstances();
        }
    }

    public CPUIntensiveActivity createCPUIntensiveActivity(String name) {
        CPUIntensiveActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createCPUIntensiveActivity((name));
            databaseModelFactory.createCPUIntensiveActivity(name);
        } else {
            ontologyModelFactory.createCPUIntensiveActivity(name);
            object = databaseModelFactory.createCPUIntensiveActivity((name));
        }
        return object;
    }

    public CPUIntensiveActivity getCPUIntensiveActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getCPUIntensiveActivity(name);
        } else {
            return databaseModelFactory.getCPUIntensiveActivity(name);
        }
    }

    public Collection<CPUIntensiveActivity> getAllCPUIntensiveActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllCPUIntensiveActivityInstances();
        } else {
            return databaseModelFactory.getAllCPUIntensiveActivityInstances();
        }
    }

    public ServerAdaptationAction createServerAdaptationAction(String name) {
        ServerAdaptationAction object = null;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createServerAdaptationAction(generateUniqueName(name));
            // databaseModelFactory.createApplicationAdaptationAction(name);
        } else {
            //ontologyModelFactory.createApplicationAdaptationAction(name);
            //   object = databaseModelFactory.createServerAdaptationAction(generateUniqueName(name));
        }
        return object;
    }

    public ApplicationAdaptationAction createApplicationAdaptationAction(String name) {
        ApplicationAdaptationAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createApplicationAdaptationAction(generateUniqueName(name));
            // databaseModelFactory.createApplicationAdaptationAction(name);
        } else {
            //ontologyModelFactory.createApplicationAdaptationAction(name);
            object = databaseModelFactory.createApplicationAdaptationAction(generateUniqueName(name));
        }
        return object;
    }

    public ApplicationAdaptationAction getApplicationAdaptationAction(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getApplicationAdaptationAction(name);
        } else {
            return databaseModelFactory.getApplicationAdaptationAction(name);
        }
    }

    public Collection<ApplicationAdaptationAction> getAllApplicationAdaptationActionInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllApplicationAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllApplicationAdaptationActionInstances();
        }
    }

    public ApplicationRedesign createApplicationRedesign(String name) {
        ApplicationRedesign object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createApplicationRedesign(generateUniqueName(name));
            databaseModelFactory.createApplicationRedesign(name);
        } else {
            ontologyModelFactory.createApplicationRedesign(name);
            object = databaseModelFactory.createApplicationRedesign(generateUniqueName(name));
        }
        return object;
    }

    public ApplicationRedesign getApplicationRedesign(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getApplicationRedesign(name);
        } else {
            return databaseModelFactory.getApplicationRedesign(name);
        }
    }

    public Collection<ApplicationRedesign> getAllApplicationRedesignInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllApplicationRedesignInstances();
        } else {
            return databaseModelFactory.getAllApplicationRedesignInstances();
        }
    }

    public ITFacilityActiveResource createITFacilityActiveResource(String name) {
        ITFacilityActiveResource object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createITFacilityActiveResource((name));
            // databaseModelFactory.createITFacilityActiveResource(name);
        } else {
            // ontologyModelFactory.createITFacilityActiveResource(name);
            object = databaseModelFactory.createITFacilityActiveResource((name));
        }
        return object;
    }

    public ITFacilityActiveResource getITFacilityActiveResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getITFacilityActiveResource(name);
        } else {
            return databaseModelFactory.getITFacilityActiveResource(name);
        }
    }

    public Collection<ITFacilityActiveResource> getAllITFacilityActiveResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllITFacilityActiveResourceInstances();
        } else {
            return databaseModelFactory.getAllITFacilityActiveResourceInstances();
        }
    }

    public HDD createHDD(String name) {
        HDD object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createHDD(generateUniqueName(name));
            //databaseModelFactory.createHDD(name);
        } else {
            //ontologyModelFactory.createHDD(name);
            object = databaseModelFactory.createHDD(generateUniqueName(name));
        }
        return object;
    }

    public HDD getHDD(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getHDD(name);
        } else {
            return databaseModelFactory.getHDD(name);
        }
    }

    public Collection<HDD> getAllHDDInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllHDDInstances();
        } else {
            return databaseModelFactory.getAllHDDInstances();
        }
    }

    public SimpleResource createSimpleResource(String name) {
        SimpleResource object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createSimpleResource(generateUniqueName(name));
            //databaseModelFactory.createSimpleResource(name);
        } else {
            //ontologyModelFactory.createSimpleResource(name);
            object = databaseModelFactory.createSimpleResource(generateUniqueName(name));
        }
        return object;
    }

    public SimpleResource getSimpleResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getSimpleResource(name);
        } else {
            return databaseModelFactory.getSimpleResource(name);
        }
    }

    public Collection<SimpleResource> getAllSimpleResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllSimpleResourceInstances();
        } else {
            return databaseModelFactory.getAllSimpleResourceInstances();
        }
    }

    public ITComputingContextPolicy createITComputingContextPolicy(String name) {
        ITComputingContextPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createITComputingContextPolicy(generateUniqueName(name));
            //databaseModelFactory.createITComputingContextPolicy(name);
        } else {
            //ontologyModelFactory.createITComputingContextPolicy(name);
            object = databaseModelFactory.createITComputingContextPolicy(generateUniqueName(name));
        }
        return object;
    }

    public ITComputingContextPolicy getITComputingContextPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getITComputingContextPolicy(name);
        } else {
            return databaseModelFactory.getITComputingContextPolicy(name);
        }
    }

    public Collection<ITComputingContextPolicy> getAllITComputingContextPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllITComputingContextPolicyInstances();
        } else {
            return databaseModelFactory.getAllITComputingContextPolicyInstances();
        }
    }

    public ITFacilityPassiveResource createITFacilityPassiveResource(String name) {
        ITFacilityPassiveResource object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createITFacilityPassiveResource((name));
            // databaseModelFactory.createITFacilityPassiveResource(name);
        } else {
            //ontologyModelFactory.createITFacilityPassiveResource(name);
            object = databaseModelFactory.createITFacilityPassiveResource((name));
        }
        return object;
    }

    public ITFacilityPassiveResource getITFacilityPassiveResource(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getITFacilityPassiveResource(name);
        } else {
            return databaseModelFactory.getITFacilityPassiveResource(name);
        }
    }

    public Collection<ITFacilityPassiveResource> getAllITFacilityPassiveResourceInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllITFacilityPassiveResourceInstances();
        } else {
            return databaseModelFactory.getAllITFacilityPassiveResourceInstances();
        }
    }

    public MigrateActivity createMigrateActivity(String name) {
        MigrateActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createMigrateActivity(generateUniqueName(name));
            // databaseModelFactory.createMigrateActivity(name);
        } else {
            //ontologyModelFactory.createMigrateActivity(name);
            object = databaseModelFactory.createMigrateActivity(generateUniqueName(name));
        }
        return object;
    }

    public MigrateActivity getMigrateActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getMigrateActivity(name);
        } else {
            return databaseModelFactory.getMigrateActivity(name);
        }
    }

    public Collection<MigrateActivity> getAllMigrateActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllMigrateActivityInstances();
        } else {
            return databaseModelFactory.getAllMigrateActivityInstances();
        }
    }

    public ConsolidationAction createConsolidationAction(String name) {
        ConsolidationAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createConsolidationAction(generateUniqueName(name));
            // databaseModelFactory.createConsolidationAction(name);
        } else {
            // ontologyModelFactory.createConsolidationAction(name);
            object = databaseModelFactory.createConsolidationAction(generateUniqueName(name));
        }
        return object;
    }

    public ConsolidationAction getConsolidationAction(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getConsolidationAction(name);
        } else {
            return databaseModelFactory.getConsolidationAction(name);
        }
    }

    public Collection<ConsolidationAction> getAllConsolidationActionInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllConsolidationActionInstances();
        } else {
            return databaseModelFactory.getAllConsolidationActionInstances();
        }
    }

    public Sensor createSensor(String name) {
        Sensor object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createSensor((name));
            // databaseModelFactory.createSensor(name);
        } else {
            // ontologyModelFactory.createSensor(name);
            object = databaseModelFactory.createSensor((name));
        }
        return object;
    }

    public Sensor getSensor(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getSensor(name);
        } else {
            return databaseModelFactory.getSensor(name);
        }
    }

    public Collection<Sensor> getAllSensorInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllSensorInstances();
        } else {
            return databaseModelFactory.getAllSensorInstances();
        }
    }

    public Actuator createActuator(String name) {
        Actuator object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createActuator((name));
            //databaseModelFactory.createActuator(name);
        } else {
            // ontologyModelFactory.createActuator(name);
            object = databaseModelFactory.createActuator((name));
        }
        return object;
    }

    public Actuator getActuator(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getActuator(name);
        } else {
            return databaseModelFactory.getActuator(name);
        }
    }

    public Collection<Actuator> getAllActuatorInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllActuatorInstances();
        } else {
            return databaseModelFactory.getAllActuatorInstances();
        }
    }

    public BusinessPolicy createBusinessPolicy(String name) {
        BusinessPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createBusinessPolicy(generateUniqueName(name));
            //databaseModelFactory.createBusinessPolicy(name);
        } else {
            //ontologyModelFactory.createBusinessPolicy(name);
            object = databaseModelFactory.createBusinessPolicy(generateUniqueName(name));
        }
        System.out.println("BussinessPolicy" + object + name);
        return object;
    }

    public BusinessPolicy getBusinessPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getBusinessPolicy(name);
        } else {
            return databaseModelFactory.getBusinessPolicy(name);
        }
    }

    public Collection<BusinessPolicy> getAllBusinessPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllBusinessPolicyInstances();
        } else {
            return databaseModelFactory.getAllBusinessPolicyInstances();
        }
    }

    public MEM createMEM(String name) {
        MEM object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createMEM(generateUniqueName(name));
            //databaseModelFactory.createMEM(name);
        } else {
            // ontologyModelFactory.createMEM(name);
            object = databaseModelFactory.createMEM(generateUniqueName(name));
        }
        return object;
    }

    public MEM getMEM(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getMEM(name);
        } else {
            return databaseModelFactory.getMEM(name);
        }
    }

    public Collection<MEM> getAllMEMInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllMEMInstances();
        } else {
            return databaseModelFactory.getAllMEMInstances();
        }
    }

    public ExternalStorage createExternalStorage(String name) {
        ExternalStorage object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createExternalStorage((name));
            // databaseModelFactory.createExternalStorage(name);
        } else {
            // ontologyModelFactory.createExternalStorage(name);
            object = databaseModelFactory.createExternalStorage((name));
        }
        return object;
    }

    public ExternalStorage getExternalStorage(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getExternalStorage(name);
        } else {
            return databaseModelFactory.getExternalStorage(name);
        }
    }

    public Collection<ExternalStorage> getAllExternalStorageInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllExternalStorageInstances();
        } else {
            return databaseModelFactory.getAllExternalStorageInstances();
        }
    }

    public CPU createCPU(String name) {
        CPU object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createCPU(generateUniqueName(name));
            //databaseModelFactory.createCPU(name);
        } else {
            // ontologyModelFactory.createCPU(name);
            object = databaseModelFactory.createCPU(generateUniqueName(name));
        }
        return object;
    }

    public CPU getCPU(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getCPU(name);
        } else {
            return databaseModelFactory.getCPU(name);
        }
    }

    public Collection<CPU> getAllCPUInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllCPUInstances();
        } else {
            return databaseModelFactory.getAllCPUInstances();
        }
    }

    public SLAPolicy createSLAPolicy(String name) {
        SLAPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createSLAPolicy(generateUniqueName(name));
            // databaseModelFactory.createSLAPolicy(name);
        } else {
            // ontologyModelFactory.createSLAPolicy(name);
            object = databaseModelFactory.createSLAPolicy(generateUniqueName(name));
        }
        return object;
    }

    public SLAPolicy getSLAPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getSLAPolicy(name);
        } else {
            return databaseModelFactory.getSLAPolicy(name);
        }
    }

    public Collection<SLAPolicy> getAllSLAPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllSLAPolicyInstances();
        } else {
            return databaseModelFactory.getAllSLAPolicyInstances();
        }
    }

    public MEMIntensiveActivity createMEMIntensiveActivity(String name) {
        MEMIntensiveActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createMEMIntensiveActivity((name));
            //databaseModelFactory.createMEMIntensiveActivity(name);
        } else {
            //ontologyModelFactory.createMEMIntensiveActivity(name);
            object = databaseModelFactory.createMEMIntensiveActivity((name));
        }
        return object;
    }

    public MEMIntensiveActivity getMEMIntensiveActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getMEMIntensiveActivity(name);
        } else {
            return databaseModelFactory.getMEMIntensiveActivity(name);
        }
    }

    public Collection<MEMIntensiveActivity> getAllMEMIntensiveActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllMEMIntensiveActivityInstances();
        } else {
            return databaseModelFactory.getAllMEMIntensiveActivityInstances();
        }
    }

    public Facility createFacility(String name) {
        Facility object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createFacility(generateUniqueName(name));
            //databaseModelFactory.createFacility(name);
        } else {
            //ontologyModelFactory.createFacility(name);
            object = databaseModelFactory.createFacility(generateUniqueName(name));
        }
        return object;
    }

    public Facility getFacility(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getFacility(name);
        } else {
            return databaseModelFactory.getFacility(name);
        }
    }

    public Collection<Facility> getAllFacilityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllFacilityInstances();
        } else {
            return databaseModelFactory.getAllFacilityInstances();
        }
    }

    public QoSPolicy createQoSPolicy(String name) {
        QoSPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {

            object = ontologyModelFactory.createQoSPolicy(generateUniqueName(name));
            //databaseModelFactory.createQoSPolicy(name);
        } else {
            // ontologyModelFactory.createQoSPolicy(name);
            object = databaseModelFactory.createQoSPolicy(generateUniqueName(name));
        }
        return object;
    }

    public QoSPolicy getQoSPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getQoSPolicy(name);
        } else {
            return databaseModelFactory.getQoSPolicy(name);
        }
    }

    public Collection<QoSPolicy> getAllQoSPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllQoSPolicyInstances();
        } else {
            return databaseModelFactory.getAllQoSPolicyInstances();
        }
    }

    public DeployActivity createDeployActivity(String name) {
        DeployActivity object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createDeployActivity(generateUniqueName(name));
            //databaseModelFactory.createDeployActivity(name);
        } else {
            // ontologyModelFactory.createDeployActivity(name);
            object = databaseModelFactory.createDeployActivity(generateUniqueName(name));
        }
        return object;
    }

    public DeployActivity getDeployActivity(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getDeployActivity(name);
        } else {
            return databaseModelFactory.getDeployActivity(name);
        }
    }

    public Collection<DeployActivity> getAllDeployActivityInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllDeployActivityInstances();
        } else {
            return databaseModelFactory.getAllDeployActivityInstances();
        }
    }

    public EnvironmentPolicy createEnvironmentPolicy(String name) {
        EnvironmentPolicy object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createEnvironmentPolicy(generateUniqueName(name));
            //databaseModelFactory.createEnvironmentPolicy(name);
        } else {
            // ontologyModelFactory.createEnvironmentPolicy(name);
            object = databaseModelFactory.createEnvironmentPolicy(generateUniqueName(name));
        }
        return object;
    }

    public EnvironmentPolicy getEnvironmentPolicy(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getEnvironmentPolicy(name);
        } else {
            return databaseModelFactory.getEnvironmentPolicy(name);
        }
    }

    public Collection<EnvironmentPolicy> getAllEnvironmentPolicyInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllEnvironmentPolicyInstances();
        } else {
            return databaseModelFactory.getAllEnvironmentPolicyInstances();
        }
    }

    public ITFacilityResourceAdaptationAction createITFacilityResourceAdaptationAction(String name) {
        ITFacilityResourceAdaptationAction object;
        if (accessType == ONTOLOGY_ACCESS) {
            object = ontologyModelFactory.createITFacilityResourceAdaptationAction(generateUniqueName(name));
            //databaseModelFactory.createITFacilityResourceAdaptationAction(name);
        } else {
            // ontologyModelFactory.createITFacilityResourceAdaptationAction(name);
            object = databaseModelFactory.createITFacilityResourceAdaptationAction(generateUniqueName(name));
        }
        return object;
    }

    public ITFacilityResourceAdaptationAction getITFacilityResourceAdaptationAction(String name) {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getITFacilityResourceAdaptationAction(name);
        } else {
            return databaseModelFactory.getITFacilityResourceAdaptationAction(name);
        }
    }

    public Collection<ITFacilityResourceAdaptationAction> getAllITFacilityResourceAdaptationActionInstances() {
        if (accessType == ONTOLOGY_ACCESS) {
            return ontologyModelFactory.getAllITFacilityResourceAdaptationActionInstances();
        } else {
            return databaseModelFactory.getAllITFacilityResourceAdaptationActionInstances();
        }
    }


    public boolean isSimulation() {
        return simulation;
    }

    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }
}
