/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selfoptimizing.utils.negotiator;

import selfoptimizing.ontologyRepresentations.greenContextOntology.Server;
import selfoptimizing.ontologyRepresentations.greenContextOntology.Task;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Administrator
 */
public interface Negotiator extends Serializable {

    public static final String NEGOTIATED_CPU = "CPU";
    public static final String NEGOTIATED_MEMORY = "MEMORY";
    public static final String NEGOTIATED_STORAGE = "STORAGE";

    public Map<String, Double> negotiate(Server server, Task task);
}
