package model.interfaces.resources;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:07:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CPU extends SimpleResource {
    List<Core> getAssociatedCores();

    void setAssociatedCores(List<Core> newAssociatedCores);

    float getCacheRate();

    void setCacheRate(float newCacheRate);

    float getClockRate();

    void setClockRate(float newClockRate);

}
