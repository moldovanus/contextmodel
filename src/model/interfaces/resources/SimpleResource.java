package model.interfaces.resources;

import model.interfaces.ContextElement;
import model.interfaces.resources.applications.ApplicationActivity;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 10:59:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SimpleResource extends ServiceCenterITComputingResource {
    String getResourceWorkLoadProperty();

    void setResourceWorkLoadProperty(String value);

    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);

    boolean hasResourcesFor(ApplicationActivity activity);

}
