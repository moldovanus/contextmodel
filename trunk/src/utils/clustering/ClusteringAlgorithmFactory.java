package utils.clustering;

import org.apache.commons.lang.UnhandledException;
import utils.clustering.impl.KMeansClustering;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 5, 2010
 * Time: 10:43:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClusteringAlgorithmFactory {
    /**
     *
     * @param algorithmName- the name of the algorithm, take it from interface ClusteringAlgorithm
     * @param data - for the algorithm like nr steps, nr clusters and so on
     *  KMeans -> data[0] = nr of clusters - Integer
     */
    public ClusteringAlgorithm createAlgorithm (String algorithmName, Object [] data)throws Exception{
      if (algorithmName.equalsIgnoreCase(ClusteringAlgorithm.KMEANS)){
          try{
          return new KMeansClustering((Integer)data[0]);
          }catch(Exception ex){
             return new KMeansClustering();
          }
      }
        else throw new Exception("Only KMeans exists for now");
    }
}
