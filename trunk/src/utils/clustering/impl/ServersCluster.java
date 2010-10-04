package utils.clustering.impl;

import model.interfaces.resources.ServiceCenterServer;
import utils.clustering.Cluster;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 4, 2010
 * Time: 2:19:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServersCluster implements Cluster {
    List<ServiceCenterServer> servers ;
    ServiceCenterServer clusterCentroid;
    public void addToCluster(Object o) {
        servers.add((ServiceCenterServer)o);
     }

    public void removeFromCluster(Object o) {
        servers.remove(o);
    }

    public void refreshClusterCenter() {
        double smallestDistance = 0;
       ServiceCenterServer intermCentroid = null;
       for (ServiceCenterServer server:servers){
           double distance = 0.0;
           for (ServiceCenterServer taskTo:servers){
              distance += server.distanceTo(taskTo);
          }
           if (distance<smallestDistance){
               smallestDistance = distance;
               intermCentroid = server;
           }
       }
     clusterCentroid = intermCentroid;
    }

    public List getAllElements() {
        return servers;
    }

}
