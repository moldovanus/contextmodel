package model.impl.databaseImpl;

import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 10:56:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContextElementImpl extends DefaultOWLIndividual implements ContextElement {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
