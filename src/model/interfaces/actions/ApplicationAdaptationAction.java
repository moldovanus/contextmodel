package model.interfaces.actions;

import model.interfaces.resources.applications.ApplicationActivity;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:38:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationAdaptationAction extends ContextAction {

    public int getCpuMin();

    public void setCpuMin(int cpuMin) ;

    public int getCpuMax() ;

    public void setCpuMax(int cpuMax) ;

    public int getMemMin() ;

    public void setMemMin(int memMin);

    public int getMemMax() ;

    public void setMemMax(int memMax) ;

    public void setActivity (ApplicationActivity activity);
}
