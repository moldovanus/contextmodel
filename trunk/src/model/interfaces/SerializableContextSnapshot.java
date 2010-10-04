package model.interfaces;

import model.interfaces.actions.ContextAction;

import java.io.Serializable;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 4, 2010
 * Time: 1:01:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerializableContextSnapshot  implements Comparable, Serializable {
    private Queue<ContextAction> actions;

    public int compareTo(Object o) {
       return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
