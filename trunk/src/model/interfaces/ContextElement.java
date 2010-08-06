package model.interfaces;

import edu.stanford.smi.protegex.owl.model.OWLIndividual;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 9, 2010
 * Time: 10:55:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextElement extends Serializable, OWLIndividual {
    String getName();

    void setName(String s);

    Integer getId();

    void setId(Integer integer);
}
