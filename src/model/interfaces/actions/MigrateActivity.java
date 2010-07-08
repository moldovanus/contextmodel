package model.interfaces.actions;

import model.interfaces.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:40:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MigrateActivity extends ConsolidationAction {
    ApplicationActivity getApplicationActivity();

    void setApplicationActivity(ApplicationActivity aplicationActivity);
}
