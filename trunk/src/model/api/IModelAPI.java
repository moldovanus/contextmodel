package model.api;

import model.impl.util.ModelFactory;
import model.interfaces.Event;
import model.interfaces.policies.ContextPolicy;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.SimpleResource;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 20, 2010
 * Time: 9:58:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IModelAPI {
    double getLoadForSimpleResource(SimpleResource resource, ModelFactory modelFactory);

    List<Map.Entry<SimpleResource, Double>> getLoadForComplexResource(ComplexResource resource, ModelFactory modelFactory);

    boolean isPUEMetricFulfilled(ModelFactory modelFactory);

    List<ContextPolicy> brokenContextPolicies(List<ContextPolicy> allPolicies, ModelFactory modelFactory);

    Event signalStorageController(ModelFactory modelFactory);
}
