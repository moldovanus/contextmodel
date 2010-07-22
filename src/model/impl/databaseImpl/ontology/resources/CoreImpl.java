package model.impl.databaseImpl.ontology.resources;

import model.interfaces.ContextElement;
import model.interfaces.resources.Core;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 22, 2010
 * Time: 10:22:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoreImpl extends SimpleResourceImpl implements Core {
    private ContextElement partOf;

    public ContextElement getPartOf() {
        return partOf;
    }

    public void setPartOf(ContextElement partOf) {
        this.partOf = partOf;
    }
}
