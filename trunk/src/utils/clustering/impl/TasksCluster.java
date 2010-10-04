package utils.clustering.impl;

import model.interfaces.resources.applications.ApplicationActivity;
import utils.clustering.Cluster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 4, 2010
 * Time: 2:18:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TasksCluster implements Cluster {
    List <ApplicationActivity> tasks;
    ApplicationActivity clusterCentroid;
    public TasksCluster(){
        tasks = new ArrayList<ApplicationActivity>();
    }
    public void addToCluster(Object o) {
           tasks.add((ApplicationActivity)o);
    }

    public void removeFromCluster(Object o) {
           tasks.remove(o);
        }

    public void refreshClusterCenter() {
       double smallestDistance = 0;
       ApplicationActivity intermCentroid = null;
       for (ApplicationActivity task:tasks){
           double distance = 0.0;
           for (ApplicationActivity taskTo:tasks){
              distance += task.getDistance(taskTo);
          }
           if (distance<smallestDistance){
               smallestDistance = distance;
               intermCentroid = task;
           }
       }
     clusterCentroid = intermCentroid;
    }

    public List getAllElements() {
        return tasks;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public double distanceToCluster(Object o){
       if (!(o instanceof ApplicationActivity)) return 0;
       ApplicationActivity task = (ApplicationActivity) o;
       return task.getDistance(clusterCentroid);
    }
}
