package model.impl.prevailerImpl.transactions;

import model.impl.prevailerImpl.PrevaylerDatabaseContainer;
import model.interfaces.resources.ComplexResource;
import model.interfaces.resources.ServiceCenterITComputingResource;
import model.interfaces.resources.applications.HDDIntensiveActivity;
import org.prevayler.TransactionWithQuery;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 21, 2010
 * Time: 11:03:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetResourceByNameTransaction implements TransactionWithQuery {
    private String resourceType;
    private String resourceName;


    public GetResourceByNameTransaction() {
    }

    public GetResourceByNameTransaction(String resourceType, String resourceName) {
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }

    public Object executeAndQuery(Object o, Date date) throws Exception {
        PrevaylerDatabaseContainer prevaylerDatabaseContainer = (PrevaylerDatabaseContainer) o;
        if (resourceType.equals(ComplexResource.class.getName())) {
            return prevaylerDatabaseContainer.getComplexResource(resourceName);
        } else if (resourceType.equals(ServiceCenterITComputingResource.class.getName())) {
            return prevaylerDatabaseContainer.getServiceCenterITComputingResource(resourceName);
        } else if (resourceType.equals(HDDIntensiveActivity.class.getName())) {
            return prevaylerDatabaseContainer.getHDDIntensiveActivity(resourceName);
        } else {
            throw new UnsupportedOperationException("Unsupported class type for query");
        }
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
