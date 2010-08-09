package model.interfaces.resources.applications;

import model.interfaces.resources.BusinessContextResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:07:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Application extends BusinessContextResource {

    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#BP_ActivityList

    Collection getBP_ActivityList();


    boolean hasBP_ActivityList();

    Iterator listBP_ActivityList();

    void addBP_ActivityList(ApplicationActivity newBP_ActivityList);

    void removeBP_ActivityList(ApplicationActivity oldBP_ActivityList);

    void setBP_ActivityList(Collection newBP_ActivityList);
}
