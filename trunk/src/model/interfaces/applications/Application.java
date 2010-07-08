package model.interfaces.applications;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:07:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Application {
    List<ApplicationActivity> getActivityList();

    void getActivityList(List<ApplicationActivity> activityList);
}
