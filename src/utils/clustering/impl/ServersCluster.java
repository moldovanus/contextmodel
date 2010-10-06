package utils.clustering.impl;

import model.interfaces.resources.ServiceCenterServer;
import utils.clustering.Cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 4, 2010
 * Time: 2:19:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServersCluster implements Cluster {
    List<ServiceCenterServer> servers;
    ServiceCenterServer clusterCentroid;
    public ServersCluster (){
        servers = new ArrayList<ServiceCenterServer>();
    }
    public void addToCluster(Object o) {
        servers.add((ServiceCenterServer) o);
    }

    public void removeFromCluster(Object o) {
        servers.remove(o);
    }

    public boolean refreshClusterCentroid() {
        double smallestDistance = Cluster.INFINITY;
        if (servers.size()==1){
            clusterCentroid=servers.get(0);
            return true;
        }
        ServiceCenterServer intermCentroid = null;
        for (ServiceCenterServer server : servers) {
            double distance = 0.0;
            for (ServiceCenterServer taskTo : servers) {
                distance += server.distanceTo(taskTo);
            }
            if (distance < smallestDistance) {
                smallestDistance = distance;
                intermCentroid = server;
            }
        }
        boolean ok = false;
        if (clusterCentroid != null && clusterCentroid.equals(intermCentroid)) {
            ok = true;
        } else {
            clusterCentroid = intermCentroid;
        }

        return ok;
    }

    public List getAllElements() {
        return servers;
    }

    public double distanceToCluster(Object o) {
        if (!(o instanceof ServiceCenterServer)) return INFINITY;
        ServiceCenterServer server = (ServiceCenterServer) o;
         if (clusterCentroid == null){
             refreshClusterCentroid();
         }
            if (servers.size()==0) return 0;
       System.out.println(clusterCentroid);
        return server.distanceTo(clusterCentroid);
    }

    public Object getClusterCentroid() {
        return clusterCentroid;
    }
    public boolean equals(Object o){
      if (!(o instanceof Cluster || o instanceof ServersCluster)) return false;
        ServersCluster serversCluster= (ServersCluster) o;
        //TODO: Check if need the same centroids
      if (!clusterCentroid.equals(serversCluster.getClusterCentroid())) return false;
      for (Object sv : serversCluster.getAllElements()){
            if (!(sv instanceof ServiceCenterServer)) return false;
          if (! servers.contains(sv)) return false;
      }
        return true;
    }
    public boolean contains ( Object o){
        if (!(o instanceof ServiceCenterServer)) return false;
        if (servers.contains(o)) return true;
        return false;
    }



}
