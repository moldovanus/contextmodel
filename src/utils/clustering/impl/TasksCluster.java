package utils.clustering.impl;


import utils.clustering.Cluster;
import utils.worldInterface.dtos.ExtendedTaskDto;

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
    List tasks;

    Object clusterCentroid;

    public TasksCluster() {
        tasks = new ArrayList<ExtendedTaskDto>();
    }

    public void addToCluster(Object o) {
        tasks.add((ExtendedTaskDto) o);
    }

    public void removeFromCluster(Object o) {
        tasks.remove(o);
        if (o.equals(clusterCentroid)) refreshClusterCentroid();
    }

    public boolean refreshClusterCentroid() {
        double smallestDistance = Cluster.INFINITY;
        Object intermCentroid = null;
        boolean ok = false;
        if (tasks.size()!=0){
        for (Object task : tasks) {
            double distance = 0.0;
            int i = 0;
            for (Object taskTo : tasks) {
                if (task instanceof ExtendedTaskDto && taskTo instanceof ExtendedTaskDto)
                distance += ((ExtendedTaskDto)task).distanceTo((ExtendedTaskDto) taskTo);
                else if (task instanceof ExtendedTaskDto && taskTo instanceof TasksCluster)
                  distance += ((TasksCluster)taskTo).distanceToCluster((task));
                else if (task instanceof TasksCluster )
                        distance += ((TasksCluster)task).distanceToCluster(taskTo);
                i++;
            }

            if ((distance / i) < smallestDistance) {
                smallestDistance = distance;
                intermCentroid = task;
            }
        }

        if (clusterCentroid != null && clusterCentroid.equals(intermCentroid)) {
            ok = true;
        } else {
            clusterCentroid = intermCentroid;
        }       }
        return ok;

                
    }

    public List getAllElements() {
        return tasks;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double distanceToCluster(Object o) {
        if (!(o instanceof ExtendedTaskDto || o instanceof TasksCluster)) return INFINITY;
        if (o instanceof TasksCluster){
            double minDist =INFINITY;
            Cluster cluster = (Cluster) o;
            for (Object o1:cluster.getAllElements()){
                double d =cluster.distanceToCluster(o1);
                if (minDist>d) minDist = d;
            }
            return minDist;
        }
        ExtendedTaskDto task = (ExtendedTaskDto) o;
        if (clusterCentroid == null) refreshClusterCentroid();
            if (tasks.size()==0) return 0;
       if (clusterCentroid instanceof ExtendedTaskDto)
        return task.distanceTo((ExtendedTaskDto) clusterCentroid);
        else
           return ((TasksCluster) clusterCentroid).distanceToCluster(task);

    }

    public Object getClusterCentroid() {
        return clusterCentroid;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Cluster || o instanceof TasksCluster)) return false;
        TasksCluster tasksCluster = (TasksCluster) o;
        //TODO: Check if need the same centroids
        if (tasksCluster.getAllElements().size()!=tasks.size()) return false;
        if (tasksCluster.getAllElements().size()==0 && tasks.size()==0) return true;
        if (!clusterCentroid.equals(tasksCluster.getClusterCentroid())) return false;
        for (Object task : tasksCluster.getAllElements()) {
            if (!(task instanceof ExtendedTaskDto)) return false;
            if (!tasksCluster.contains(task)) return false;
        }
        return true;
    }

    public boolean contains(Object o) {
        if (!(o instanceof ExtendedTaskDto)) return false;
        if (tasks.contains(o)) return true;
        return false;
    }
}
