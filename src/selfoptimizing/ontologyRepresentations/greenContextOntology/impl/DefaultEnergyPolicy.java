package selfoptimizing.ontologyRepresentations.greenContextOntology.impl;

import com.hp.hpl.jena.ontology.OntModel;
import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import selfoptimizing.ontologyRepresentations.greenContextOntology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Datacenter.owl#EnergyPolicy
 *
 * @version generated on Sun Mar 07 13:11:11 EET 2010
 */
public class DefaultEnergyPolicy extends DefaultPolicy
        implements EnergyPolicy {

    public DefaultEnergyPolicy(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultEnergyPolicy() {
    }


    public boolean getRespected(OntModel ontModel) {
        //TODO : maybe add checking if all the cores from the server are used

        Server server = this.getReferenced();
        if (server.getIsInLowPowerState()) {
            return true;
        }

        CPU cpu = server.getAssociatedCPU();
        for (Object o : cpu.getAssociatedCore()) {
            Core core = (Core) o;
            int usedCore = core.getUsed();
            if (usedCore < core.getMinAcceptableValue() || usedCore > core.getMaxAcceptableValue()) {
                return false;
            }
        }
        Memory memory = server.getAssociatedMemory();
        int usedMemory = memory.getUsed();
        if (usedMemory < memory.getMinAcceptableValue() || usedMemory > memory.getMaxAcceptableValue()) {
            return false;
        }

        Storage storage = server.getAssociatedStorage();
        int usedStorage = storage.getUsed();
        if (usedStorage < storage.getMinAcceptableValue() || usedStorage > storage.getMaxAcceptableValue()) {
            return false;
        }

        return true;
    }

    // Property http://www.owl-ontologies.com/Datacenter.owl#referenced

    public Server getReferenced() {
        return (Server) getPropertyValueAs(getReferencedProperty(), Server.class);
    }


    public RDFProperty getReferencedProperty() {
        final String uri = "http://www.owl-ontologies.com/Datacenter.owl#referenced";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasReferenced() {
        return getPropertyValueCount(getReferencedProperty()) > 0;
    }


    public void setReferenced(Server newReferenced) {
        setPropertyValue(getReferencedProperty(), newReferenced);
    }

}
