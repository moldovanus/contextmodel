package model.interfaces.resources;

import model.interfaces.ContextElement;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:06:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface HDD extends SimpleResource {


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();

    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#rotationSpeed

    Double getRotationSpeed();

    boolean hasRotationSpeed();

    void setRotationSpeed(Double newRotationSpeed);
}
