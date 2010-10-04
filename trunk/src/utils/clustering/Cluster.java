package utils.clustering;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 4, 2010
 * Time: 2:20:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Cluster {
    public final int INFINITY= 100000;
    public void addToCluster (Object o);
    public void removeFromCluster (Object o);
    public void refreshClusterCenter();
    public List getAllElements();
    public double distanceToCluster(Object o);
}
