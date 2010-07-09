package model.impl.databaseImpl.actions;

import model.interfaces.actions.MigrateActivity;
import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 9:25:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrateActivityImpl extends ConsolidationActionImpl implements MigrateActivity {
    private ApplicationActivity applicationActivity;

    public MigrateActivityImpl() {
    }

    public MigrateActivityImpl(ApplicationActivity applicationActivity) {
        this.applicationActivity = applicationActivity;
    }

    public ApplicationActivity getApplicationActivity() {
        return applicationActivity;
    }

    public void setApplicationActivity(ApplicationActivity applicationActivity) {
        this.applicationActivity = applicationActivity;
    }
}
