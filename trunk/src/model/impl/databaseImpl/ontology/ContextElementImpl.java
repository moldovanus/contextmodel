package model.impl.databaseImpl.ontology;

import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 10:56:26 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ContextElementImpl implements ContextElement {
    protected String id;
    protected String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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