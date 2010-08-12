package model.interfaces.resources.applications;

import edu.stanford.smi.protegex.owl.model.RDFProperty;
import model.interfaces.ContextElement;
import model.interfaces.resources.BusinessContextResource;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 8, 2010
 * Time: 11:09:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ApplicationActivity extends BusinessContextResource {

    //TODO: de bagat task weight 
    //TODO: de bagat mai multe cores

    void setCPUWeight(Double weight);

    void setHDDWeight(Double weight);

    void setMEMWeight(Double weight);


    Double getCPUAllocatedValue();

    void setCPUAllocatedValue(Double value);

    Double getHDDAllocatedValue();

    void setHDDAllocatedValue(Double value);

    Double getMEMAllocatedValue();

    void setMEMAllocatedValue(Double value);

    Double getPerformanceEstimation();

    void setPerformanceEstimation(Double value);

    Double getPerformanceDegradation();

    void setPerformanceDegradation(Double value);
    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#cpuRequiredMaxValue

    float getCpuRequiredMaxValue();

    boolean hasCpuRequiredMaxValue();

    void setCpuRequiredMaxValue(float newCpuRequiredMaxValue);

    float getCpuRequiredMinValue();

    RDFProperty getCpuRequiredMinValueProperty();

    boolean hasCpuRequiredMinValue();

    void setCpuRequiredMinValue(float newCpuRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMaxValue

    float getHddRequiredMaxValue();

    RDFProperty getHddRequiredMaxValueProperty();

    boolean hasHddRequiredMaxValue();

    void setHddRequiredMaxValue(float newHddRequiredMaxValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#hddRequiredMinValue

    float getHddRequiredMinValue();


    boolean hasHddRequiredMinValue();

    void setHddRequiredMinValue(float newHddRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMaxValue

    float getMemRequiredMaxValue();


    boolean hasMemRequiredMaxValue();

    void setMemRequiredMaxValue(float newMemRequiredMaxValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#memRequiredMinValue

    float getMemRequiredMinValue();


    boolean hasMemRequiredMinValue();

    void setMemRequiredMinValue(float newMemRequiredMinValue);


    // Property http://www.semanticweb.org/ontologies/2010/6/ContextModel.owl#partOf

    Collection getPartOf();


    boolean hasPartOf();

    Iterator listPartOf();

    void addPartOf(ContextElement newPartOf);

    void removePartOf(ContextElement oldPartOf);

    void setPartOf(Collection newPartOf);


}
