package utils.clustering;

import utils.clustering.impl.HierarhicalClusteringWithDistance;
import utils.clustering.impl.KMeansClustering;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 5, 2010
 * Time: 10:43:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClusteringAlgorithmFactory {

    public ClusteringAlgorithm getKMeansAlgorithm(int k) {
        try {
            return new KMeansClustering(k);
        } catch (Exception ex) {
            return new KMeansClustering();
        }

    }

    public ClusteringAlgorithm getHierarhicalAlgorithm(int levels) {
        return new HierarhicalClusteringWithDistance(levels);
    }
}
