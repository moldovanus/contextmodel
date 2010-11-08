package utils.worldInterface.datacenterInterface.proxies.impl;

import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: May 29, 2010
 * Time: 10:13:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProxyFactory {
    private static boolean returnStub = true;

    private ProxyFactory() {
    }

    public static ServerManagementProxyInterface createServerManagementProxy() {
        return (returnStub) ? new StubProxy() : new OpennebulaManagementProxy();
    }

    public static void setReturnStub(boolean returnStub) {
        ProxyFactory.returnStub = returnStub;
    }
}
    