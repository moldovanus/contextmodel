package model.interfaces.resources;

import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:06:27 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServiceCenterServer extends ComplexResource {
    //TODO: quantificare de currentWorkload pt server

    boolean hostsActivity(ApplicationActivity activity);

    String getIpAddress();

    void setIpAddress(String newIpAddress);

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#macAddress

    String getMacAddress();

    void setMacAddress(String newMacAddress);

    public void resetInitialValues();

    public void markInitialValues();
    public double distanceTo(ServiceCenterServer server);
}
