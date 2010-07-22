package model.impl.prevailerImpl.transactions;

import model.impl.prevailerImpl.PrevaylerDatabaseContainer;
import model.interfaces.ContextElement;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.HDDIntensiveActivity;
import org.prevayler.TransactionWithQuery;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 21, 2010
 * Time: 11:14:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetAllResourceInstancesTransaction implements TransactionWithQuery {
    private String resourceType;

    public GetAllResourceInstancesTransaction() {
    }

    public GetAllResourceInstancesTransaction(String resourceType) {
        this.resourceType = resourceType;
    }

    public Object executeAndQuery(Object o, Date date) throws Exception {
        PrevaylerDatabaseContainer prevaylerDatabaseContainer = (PrevaylerDatabaseContainer) o;
        if (resourceType.equals(ComplexResource.class.getName())) {
            return prevaylerDatabaseContainer.getAllComplexResourceInstances();
        } else if (resourceType.equals(ServiceCenterITComputingResource.class.getName())) {
            return prevaylerDatabaseContainer.getAllServiceCenterITComputingResourceInstances();
        } else if (resourceType.equals(HDDIntensiveActivity.class.getName())) {
            return prevaylerDatabaseContainer.getAllHDDIntensiveActivityInstances();
        } else if (resourceType.equals(ContextElement.class.getName())) {
            return prevaylerDatabaseContainer.getAllContextElementInstances();
        } else {
            throw new UnsupportedOperationException("Unsupported class type for query");
        }
    }
}
