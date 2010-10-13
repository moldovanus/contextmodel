package utils.clustering.impl;

import utils.clustering.Cluster;
import utils.clustering.ClusteringAlgorithm;
import utils.worldInterface.dtos.ExtendedServerDto;
import utils.worldInterface.dtos.ExtendedTaskDto;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 12, 2010
 * Time: 9:47:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class HierarhicalClusteringWithDistance implements ClusteringAlgorithm {
    private List allObjects;
    public static int LEVELS = 3;
    private List<Cluster> clusters;
    private PriorityQueue edges;
    private PriorityQueue treeEdges;
    private double[][] distanceMatrix;
    private double[][] minSpanningTree;

    public class Edge implements Comparable {
        private Object end1;
        private Object end2;
        private double cost;

        public Object getEnd1() {
            return end1;
        }

        public void setEnd1(Object end1) {
            this.end1 = end1;
        }

        public Object getEnd2() {
            return end2;
        }

        public void setEnd2(Object end2) {
            this.end2 = end2;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public int compareTo(Object o) {
            if (!(o instanceof Edge)) return Cluster.INFINITY;
            Edge edge = (Edge) o;
            if (this.getCost() < edge.getCost()) return -1;
            if (this.getCost() > edge.getCost()) return 1;
            return 0;
        }


    }

    public HierarhicalClusteringWithDistance() {
        allObjects = new ArrayList();
        clusters = new ArrayList();
        edges = new PriorityQueue();
        treeEdges = new PriorityQueue();

    }

    public HierarhicalClusteringWithDistance(int levels) {
        allObjects = new ArrayList();
        clusters = new ArrayList();
        edges = new PriorityQueue();
        treeEdges = new PriorityQueue();
        LEVELS = levels;
    }

    private void createDistanceMatrix(List objects) {
        for (int i = 0; i < objects.size(); i++) {
            Object o1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                Object o2 = objects.get(j);
                if (o1 instanceof ExtendedTaskDto && (!o1.equals(o2))) {
                    ExtendedTaskDto task = (ExtendedTaskDto) o1;
                    distanceMatrix[i][j] = task.distanceTo((ExtendedTaskDto) o2);
                    distanceMatrix[j][i] = distanceMatrix[i][j];
                    Edge edge = new Edge();
                    edge.setEnd1(o1);
                    edge.setEnd2(o2);
                    edge.setCost(distanceMatrix[i][j]);
                    edges.add(edge);
                } else {
                    ExtendedServerDto server = (ExtendedServerDto) o1;
                    distanceMatrix[i][j] = server.distanceTo((ExtendedServerDto) o2);
                    distanceMatrix[j][i] = distanceMatrix[i][j];
                    Edge edge = new Edge();
                    edge.setEnd1(o1);
                    edge.setEnd2(o2);
                    edge.setCost(distanceMatrix[i][j]);
                    edges.add(edge);
                }
            }
        }
    }

    public void createMinimumSpanningTree(List objects) {
        boolean[] used = new boolean[objects.size()];
        boolean ok = true;
        // create minimum spanning tree between objects(tasks, servers)
        while (ok) {
            Edge e = (Edge) edges.peek();
            int i = objects.indexOf(e.getEnd1());
            int j = objects.indexOf(e.getEnd2());
            if (!used[i] && !used[j]) {
                minSpanningTree[i][j] = e.getCost();
                minSpanningTree[j][i] = e.getCost();
                used[i] = true;
                used[j] = true;
            }
            ok = false;
            for (boolean d : used) {
                if (d == false) ok = true;
            }
        }
    }

    public void initializeClusters(List objects) {
        allObjects.clear();
        clusters.clear();
        allObjects.addAll(objects);
        distanceMatrix = new double[objects.size()][objects.size()];
        minSpanningTree = new double[objects.size()][objects.size()];
        createDistanceMatrix(objects);
        createMinimumSpanningTree(objects);
        refreshClusters();

    }

    public int getNrOfClusters() {
        return clusters.size();
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
        int i = 0;
        for (Cluster cl : clusters) {
            if (cl.contains(object)) return i;
            i++;
        }
        return -1;
    }


    public void addObjectToKnowledgeBase(Object object) {
        this.allObjects.add(object);
        refreshClusters();
    }

    public void addObjectsToKnowledgeBase(List objects) {
        this.allObjects.addAll(objects);
        refreshClusters();
    }
    //TODO: Write this functiooooon-> distance between clusters

    public void refreshClusters() {
        clusters = new ArrayList<Cluster>(allObjects.size());
        if (allObjects.get(0) instanceof ExtendedTaskDto) {
            for (int i = 0; i < allObjects.size(); i++) {
                TasksCluster t = new TasksCluster();
                t.addToCluster(allObjects.get(i));
                clusters.add(t);
            }
        } else {
            for (int i = 0; i < allObjects.size(); i++) {
                ServersCluster s = new ServersCluster();
                s.addToCluster(allObjects.get(i));
                clusters.add(s);
            }
        }
        int i = 0;
        while (i < LEVELS) {
            double minDistance = Cluster.INFINITY;
            Cluster cluster1 = null;
            Cluster cluster2 = null;
            for (Cluster cl1 : clusters) {
                for (Cluster cl2 : clusters) {
                    if (!cl1.equals(cl2) && cl1.distanceToCluster(cl2) < minDistance) {
                        minDistance = cl1.distanceToCluster(cl2);
                        cluster1 = cl1;
                        cluster2 = cl2;
                    }
                }
            }
            if (cluster1 != null && cluster2 != null) {
                clusters.remove(cluster1);
                clusters.remove(cluster2);
                Cluster cl;
                if (cluster1.getAllElements().get(0) instanceof ExtendedTaskDto) {
                    cl = new TasksCluster();
                } else {
                    cl = new ServersCluster();
                }
                cl.addToCluster(cluster1.getAllElements());
                cl.addToCluster(cluster2.getAllElements());
                clusters.add(cl);
            } else break;
            i++;
        }
    }
}
