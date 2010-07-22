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
 * Time: 10:58:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistEntityTransaction implements TransactionWithQuery {
    private String elementName;
    private String entityType;

    public PersistEntityTransaction() {
    }

    public PersistEntityTransaction(String elementName, String entityType) {
        this.elementName = elementName;
        this.entityType = entityType;
    }

    public Object executeAndQuery(Object o, Date date) throws Exception {
        //System.out.println("Creating: " +entityType +" - " + new java.util.Date());
        PrevaylerDatabaseContainer container = (PrevaylerDatabaseContainer) o;
        if (entityType.equals(ComplexResource.class.getName())) {
            return container.createComplexResource(elementName);
        } else if (entityType.equals(ServiceCenterITComputingResource.class.getName())) {
            return container.createServiceCenterITComputingResource(elementName);
        } else if (entityType.equals(HDDIntensiveActivity.class.getName())) {
            return container.createHDDIntensiveActivity(elementName);
        } else {
            throw new UnsupportedOperationException("Unsupported class type for query");
        }

    }
}
