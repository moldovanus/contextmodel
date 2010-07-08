package model.interfaces.resources;

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

}
