package utils.clustering.impl;


import utils.clustering.Cluster;
import utils.worldInterface.dtos.ExtendedServerDto;

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
    List servers;
    Object clusterCentroid;
    public ServersCluster (){
        servers = new ArrayList<ExtendedServerDto>();
    }
    public void addToCluster(Object o) {
        servers.add((ExtendedServerDto) o);
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
          boolean ok = false;
        if (servers.size()!=0){
        Object intermCentroid = null;
        for (Object server : servers) {
            double distance = 0.0;
            for (Object serverTo : servers) {
                  if (server instanceof ExtendedServerDto && serverTo instanceof ExtendedServerDto)
                distance += ((ExtendedServerDto)server).distanceTo((ExtendedServerDto) serverTo);
                else if (server instanceof ExtendedServerDto && serverTo instanceof TasksCluster)
                  distance += ((TasksCluster) serverTo).distanceToCluster((server));
                else if (server instanceof TasksCluster )
                        distance += ((TasksCluster)server).distanceToCluster(serverTo);
            }
            if (distance < smallestDistance) {
                smallestDistance = distance;
                intermCentroid = server;
            }
        }

        if (clusterCentroid != null && clusterCentroid.equals(intermCentroid)) {
            ok = true;
        } else {
            clusterCentroid = intermCentroid;
        }
        }
        return ok;
    }

    public List getAllElements() {
        return servers;
    }

    public double distanceToCluster(Object o) {
        if (!(o instanceof ExtendedServerDto || o instanceof ServersCluster)) return INFINITY;
        if (o instanceof TasksCluster){
            double minDist =INFINITY;
            Cluster cluster = (Cluster) o;
            for (Object o1:cluster.getAllElements()){
                double d =cluster.distanceToCluster(o1);
                if (minDist>d) minDist = d;
            }
            return minDist;
        }
        ExtendedServerDto task = (ExtendedServerDto) o;
        if (clusterCentroid == null) refreshClusterCentroid();
            if (servers.size()==0) return 0;
       if (clusterCentroid instanceof ExtendedServerDto)
        return task.distanceTo((ExtendedServerDto) clusterCentroid);
        else
           return ((TasksCluster) clusterCentroid).distanceToCluster(task);

    }

    public Object getClusterCentroid() {
        return clusterCentroid;
    }
    public boolean equals(Object o){
      if (!(o instanceof Cluster || o instanceof ExtendedServerDto)) return false;
        ServersCluster serversCluster= (ServersCluster) o;
        //TODO: Check if need the same centroids
     // if (!clusterCentroid.equals(serversCluster.getClusterCentroid())) return false;
      for (Object sv : serversCluster.getAllElements()){
            if (!(sv instanceof ExtendedServerDto)) return false;
          if (! servers.contains(sv)) return false;
      }
        return true;
    }
    public boolean contains ( Object o){
        if (!(o instanceof ExtendedServerDto)) return false;
        if (servers.contains(o)) return true;
        return false;
    }



}
