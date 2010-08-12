package model.interfaces.actions;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 10, 2010
 * Time: 3:17:33 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * This class is intended to be a default action associated to an actuator ( for example increase temperature by 4 degrees)
 * It does not reside under the ContextAction tree because it is a predefined low level action brought by the actuator
 */
public interface FacilityDefaultAction extends ContextAction {

    void setEffect(ActionEffect effect);

    ActionEffect getEffect();

}
