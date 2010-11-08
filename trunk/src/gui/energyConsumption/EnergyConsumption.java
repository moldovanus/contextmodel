package gui.energyConsumption;

import utils.exceptions.ApplicationException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 9, 2010
 * Time: 10:58:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EnergyConsumption {
    public Number getValueWithRunningAlgorithm() throws ApplicationException;

    public Number getValueWithoutAlgorithm() throws ApplicationException;
}
