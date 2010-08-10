package model.interfaces.resources;

import model.impl.util.ModelAccess;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:00:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Sensor extends ITFacilityPassiveResource {
    //TODO: de bagat acceptable value, optimum, max, min, etc

    boolean hasAcceptableValue(ModelAccess access);

}
