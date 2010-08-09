package model.interfaces.resources;

import model.interfaces.ContextElement;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:06:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MEM extends SimpleResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memClockRate

    float getMemClockRate();

    boolean hasMemClockRate();

    void setMemClockRate(float newMemClockRate);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();

    boolean hasPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);
}
