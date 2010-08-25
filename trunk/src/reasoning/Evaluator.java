package reasoning;

import model.interfaces.policies.ContextPolicy;
import utils.exceptions.IndividualNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Aug 25, 2010
 * Time: 11:45:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Evaluator {
    boolean evaluatePolicy(ContextPolicy policy, String propertyName) throws IndividualNotFoundException;
}
