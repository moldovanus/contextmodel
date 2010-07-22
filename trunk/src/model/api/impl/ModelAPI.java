package model.api.impl;

import model.api.IModelAPI;
import model.impl.util.ModelFactory;
import model.interfaces.Event;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.SimpleResource;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 20, 2010
 * Time: 10:05:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelAPI implements IModelAPI {

    public double getLoadForSimpleResource(SimpleResource resource, ModelFactory modelFactory) {
        SimpleResource simpleResource = modelFactory.getSimpleResource(resource.getName());
        return simpleResource.getCurrentWorkLoad();
    }

    public List<Map.Entry<SimpleResource, Double>> getLoadForComplexResource(ComplexResource resource, ModelFactory modelFactory) {
        List<Map.Entry<SimpleResource, Double>> load = new ArrayList<Map.Entry<SimpleResource, Double>>();
        ComplexResource complexResource = modelFactory.getComplexResource(resource.getName());
        List<ServiceCenterITComputingResource> resources = complexResource.getResources();
        for (ServiceCenterITComputingResource serviceCenterITComputingResource : resources) {
            Map.Entry<SimpleResource, Double> entry
                    = new HashMap.SimpleEntry<SimpleResource, Double>(
                    (SimpleResource) serviceCenterITComputingResource
                    , serviceCenterITComputingResource.getCurrentWorkLoad());
            load.add(entry);
        }
        return load;
    }

    public boolean isPUEMetricFulfilled(ModelFactory modelFactory) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<ContextPolicy> brokenContextPolicies(List<ContextPolicy> allPolicies, ModelFactory modelFactory) {
        Collection<ContextPolicy> policies = modelFactory.getAllContextPolicyInstances();
        List<ContextPolicy> brokenPolicies = new ArrayList<ContextPolicy>();
        for (ContextPolicy policy : policies) {
            if (!policy.isRespected()) {
                brokenPolicies.add(policy);
            }
        }
        return brokenPolicies;
    }

    public Event signalStorageController(ModelFactory modelFactory) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
