package gui.energyConsumption;

import gui.energyConsumption.energyConsumptionImpl.EnergyEstimator;
import model.impl.util.ModelAccess;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 9, 2010
 * Time: 10:58:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnergyConsumptionFactory {
    public EnergyConsumptionFactory(){

    }
    public EnergyConsumption getEstimator(ModelAccess modelAccess){
           return new EnergyEstimator(modelAccess); 
    }
}
