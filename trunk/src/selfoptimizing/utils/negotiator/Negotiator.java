/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.negotiator;



import model.interfaces.resources.ServiceCenterServer;
import model.interfaces.resources.applications.ApplicationActivity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Administrator
 */
public interface Negotiator extends Serializable {

    public static final String NEGOTIATED_CPU = "CPU";
    public static final String NEGOTIATED_MEMORY = "MEMORY";
    public static final String NEGOTIATED_STORAGE = "STORAGE";

    public Map<String, Double> negotiate(ServiceCenterServer server, ApplicationActivity task);
}
