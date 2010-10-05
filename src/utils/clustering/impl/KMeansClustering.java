package utils.clustering.impl;

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
    public static int K = 10;
    public static int maxNrSteps = 100;
    public KMeansClustering(int k) {
        K = k;
    }

    public void initializeClusters(List objects) {
        if (objects.get(0) instanceof ApplicationActivity) {
            List<ApplicationActivity> tasks = (List<ApplicationActivity>) objects;
            List<Cluster> clusters = new ArrayList<Cluster>();
            int nr = 0;
            for (int i = 0; i < K; i++) {
                Cluster cl = new TasksCluster();
                int step = 0;
                while (step < tasks.size() / K && nr < tasks.size()) {
                    cl.addToCluster(tasks.get(nr));
                    step++;
                    nr++;
                }

            }
            boolean changed = true;

            while (changed){

            }
        }
    }

    public void getClosestCluster(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addObjectToKnowledgeBase(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void refreshClusters() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
