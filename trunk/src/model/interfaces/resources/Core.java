package model.interfaces.resources;

import model.interfaces.ContextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 18, 2010
 * Time: 12:25:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Core extends SimpleResource {
    ContextElement getPartOf();

    void setPartOf(ContextElement newPartOf);
}
