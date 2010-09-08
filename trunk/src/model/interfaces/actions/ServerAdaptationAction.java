package model.interfaces.actions;

import model.interfaces.resources.ServiceCenterServer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 7, 2010
 * Time: 11:35:28 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ServerAdaptationAction  extends ITComputingResourceAdaptationAction{
  public int getNewOptimalValueForCpu() ;

    public void setNewOptimalValueForCpu(int newOptimalValueForCpu);

    public int getNewOptimalValueForMem() ;

    public void setNewOptimalValueForMem(int newOptimalValueForMem);
    public void setServer(ServiceCenterServer server);
    public ServiceCenterServer getServer();
}

