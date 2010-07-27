package model.impl.prevailerImpl.transactions;

import model.impl.prevailerImpl.PrevaylerDatabaseContainer;
import model.interfaces.ContextElement;
import org.prevayler.Transaction;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 11:07:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveResourceTransaction implements Transaction {
    private ContextElement element;

    public RemoveResourceTransaction() {
    }

    public RemoveResourceTransaction(ContextElement element) {
        this.element = element;
    }

    public ContextElement getElement() {
        return element;
    }

    public void setElement(ContextElement element) {
        this.element = element;
    }

    public void executeOn(Object o, Date date) {
        PrevaylerDatabaseContainer container = (PrevaylerDatabaseContainer) o;
        container.removeEntity(element);
    }
}
