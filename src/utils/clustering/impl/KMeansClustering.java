package utils.clustering.impl;

import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;
import utils.clustering.Cluster;
import utils.clustering.ClusteringAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 5, 2010
 * Time: 9:39:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class KMeansClustering implements ClusteringAlgorithm {
    List objects;
    List<Cluster> clusters;
    public static int K = 10;
    public static final int MAXNRSTEPS = 100;
    public KMeansClustering(){
        
    }
    public KMeansClustering(int k) {
        K = k;
    }

    public Cluster getNearestCluster(Object o) {
        double smallestDist = Cluster.INFINITY;
        Cluster closestCluster = null;
        for (Cluster cl : clusters) {
            double dist = cl.distanceToCluster(o);
            if (dist < smallestDist) {
                smallestDist = dist;
                closestCluster = cl;
            }
        }
        return closestCluster;
    }

    public int getBelongingCluster(Object object) {
        int i=0;
        for (Cluster cl:clusters){
            if (cl.contains(object))return i;
            i++;
        }
        return -1;
    }

    public void initializeClusters(List objects) {
        this.objects = objects;
        if (objects.get(0) instanceof ApplicationActivity) {
            List<ApplicationActivity> tasks = (List<ApplicationActivity>) objects;
            clusters = new ArrayList<Cluster>();
            int nr = 0;
            for (int i = 0; i < K; i++) {
                Cluster cl = new TasksCluster();
                int step = 0;
                while (step < tasks.size() / K && nr < tasks.size()) {
                    cl.addToCluster(tasks.get(nr));
                    step++;
                    nr++;
                }
                clusters.add(i, cl);
            }
        } else {
            List<ServiceCenterServer> tasks = (List<ServiceCenterServer>) objects;
            clusters = new ArrayList<Cluster>();
            int nr = 0;
            for (int i = 0; i < K; i++) {
                Cluster cl = new ServersCluster();
                int step = 0;
                while (step < tasks.size() / K && nr < tasks.size()) {
                    cl.addToCluster(tasks.get(nr));
                    step++;
                    nr++;
                }
                clusters.add(i, cl);
            }

        }
        for (Cluster cl : clusters) cl.refreshClusterCentroid();
        refreshClusters();

    }

    public int getNrOfClusters() {
        return clusters.size();  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void addObjectToKnowledgeBase(Object object) {
        objects.add(object);
        getNearestCluster(object).addToCluster(object);
        refreshClusters();
    }

    public void addObjectsToKnowledgeBase(List objects) {
        objects.addAll(objects);
        for (Object o:objects){
                getNearestCluster(o).addToCluster(o);
          }

    }

    public void refreshClusters() {
        boolean changed = true;
        int i = 0;
        while (changed && MAXNRSTEPS > i) {
            changed = false;
            for (Cluster cl : clusters) {
                List elements = cl.getAllElements();
                List objectsToBeRemoved = new ArrayList();
                for (Object o : elements) {
                    if (o==null)
                    System.out.println("BAi here!");
                    Cluster nearest = getNearestCluster(o);
                    if (!nearest.equals(cl)) {
                        objectsToBeRemoved.add(o);
                        nearest.addToCluster(o);
                        changed = true;
                    }
                }
                for (Object o : objectsToBeRemoved){
                    cl.removeFromCluster(o);
                }
            }
            if (changed) {
                for (Cluster cl : clusters) cl.refreshClusterCentroid();
            }
            i++;
        }
    }
}
