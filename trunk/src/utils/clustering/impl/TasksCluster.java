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
    List<ApplicationActivity> tasks;

    ApplicationActivity clusterCentroid;

    public TasksCluster() {
        tasks = new ArrayList<ApplicationActivity>();
    }

    public void addToCluster(Object o) {
        tasks.add((ApplicationActivity) o);
    }

    public void removeFromCluster(Object o) {
        tasks.remove(o);
        if (o.equals(clusterCentroid)) refreshClusterCentroid();
    }

    public boolean refreshClusterCentroid() {
        double smallestDistance = Cluster.INFINITY;
        ApplicationActivity intermCentroid = null;
        for (ApplicationActivity task : tasks) {
            double distance = 0.0;
            int i = 0;
            for (ApplicationActivity taskTo : tasks) {
                distance += task.getDistance(taskTo);
                i++;
            }

            if ((distance / i) < smallestDistance) {
                smallestDistance = distance;
                intermCentroid = task;
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
        return tasks;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double distanceToCluster(Object o) {
        if (!(o instanceof ApplicationActivity)) return INFINITY;
        ApplicationActivity task = (ApplicationActivity) o;
        if (clusterCentroid == null) refreshClusterCentroid();
            if (tasks.size()==0) return 0;
        return task.getDistance(clusterCentroid);
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
            if (!(task instanceof ApplicationActivity)) return false;
            if (!tasksCluster.contains(task)) return false;
        }
        return true;
    }

    public boolean contains(Object o) {
        if (!(o instanceof ApplicationActivity)) return false;
        if (tasks.contains(o)) return true;
        return false;
    }
}
