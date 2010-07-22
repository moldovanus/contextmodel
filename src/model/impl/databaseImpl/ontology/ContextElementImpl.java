package model.impl.databaseImpl.ontology;

import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 10:56:26 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ContextElementImpl extends DefaultOWLIndividual implements ContextElement {
    protected Integer id;
    protected String name;

    {
        id = -1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ContextElementImpl that = (ContextElementImpl) o;

        return id.equals(that.id);

    }

}
