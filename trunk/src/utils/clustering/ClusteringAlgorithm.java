package utils.clustering;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 5, 2010
 * Time: 9:52:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ClusteringAlgorithm {
    public static final String KMEANS = "KMeans";
    public static final String HIERARCHICAL="Hierarhical";
    public void initializeClusters(List objects);

    public Cluster getNearestCluster(Object object);

    public void addObjectToKnowledgeBase(Object object);
    public void addObjectsToKnowledgeBase(List objects);
    public void refreshClusters();
}
