package utils.worldInterface.datacenterInterface.proxies.impl;

import globalLoop.utils.GlobalVars;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import utils.worldInterface.datacenterInterface.proxies.ServerManagementProxyInterface;
import utils.worldInterface.datacenterInterface.xmlParsers.ServerInfoSAXHandler;
import utils.worldInterface.dtos.ServerDto;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: May 8, 2010
 * Time: 11:06:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class HyperVServerManagementProxy extends ServerManagementProxy {

    public HyperVServerManagementProxy(String hostName) {
        super(hostName);
    }

    private void waitUntilTargetIsAlive(String ip) {
        String pingCmd = "ping " + ip;
        boolean ok = false;
        while (!ok) {
            try {
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(pingCmd);

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);

                    if (!inputLine.contains("unreachable") && !inputLine.equals("") &&
                            inputLine.contains("Reply")) {
                        ok = true;
                        break;
                    }

                }
                in.close();

            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public ServerDto getServerInfo() {

        ServerDto serverDto = null;
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/GetServerInfo");

            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "";
            // Response
            String line;
            while ((line = rd.readLine()) != null) {
                if (DEBUG) {
                    System.out.println(line);
                }
                if (line.length() > 0 && line.charAt(1) != '?') {
                    content += "\n" + line;
                }
            }

            try {
                XMLReader reader = XMLReaderFactory.createXMLReader();
                ServerInfoSAXHandler handler = new ServerInfoSAXHandler();
                reader.setContentHandler(handler);
                ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
                InputSource source = new InputSource(in);
                reader.parse(source);
                serverDto = handler.getServerDto();
                if (DEBUG) {
                    // System.out.println("Handler ret " + handler.ret);
                }
                // res = Double.valueOf(handler.ret).doubleValue();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return serverDto;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moveDestinationActions(String path1, String path2, String vmName) {
        try {
            //Socket sock = new Socket(hostName, 80);

//             String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                    "  <soap12:Body>\n" +
//                    "    <MoveDestinationActions  xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
//                    "      <path1>" + path1 + "</path1>\n" +
//                    "      <path2>" + path2 + "</path2>\n" +
//                    "      <vmName>" + vmName + "</vmName>\n" +
//                    "    </MoveDestinationActions >\n" +
//                    "  </soap12:Body>\n" +
//                    "</soap12:Envelope>";
//
//            String header = "POST /Service1.asmx HTTP/1.1\n" +
//                    "Host: server1\n" +
//                    "Content-Type: application/soap+xml; charset=utf-8\n" +
//                    "Content-Length: " + content.length() + "\n\n";
//
//            Socket socket = new Socket(hostName, 80);
//            socket.getOutputStream().write(header.getBytes());
//            socket.getOutputStream().write(content.getBytes());
//            System.out.println(header);
//            System.out.println(content);

            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveDestinationActions?path1="
                    + path1 + "&path2=" + path2 + "&vmName=" + vmName);
            URLConnection connection = url.openConnection();
            System.out.println(url);
//            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveDestinationActions");
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//
//            //Send header
//            String data = "path1=" + path1 + "&path2=" + path2 + "&vmName=" + vmName;
//            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
//            wr.write("POST /Service1.asmx/MoveDestinationActions HTTP/1.1\r\n");
//            wr.write("Host: " + hostName + "\r\n");
//            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
//            wr.write("Content-Length: " + data.length() + "\r\n");
//            wr.write("\r\n");
//            wr.write(data);
//            wr.write("\r\n");
//
//
//            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void moveSourceActions(String path, String vmName) {
        try {

//            String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                    "  <soap12:Body>\n" +
//                    "    <MoveSourceActions xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
//                    "      <path>" + path + "</path>\n" +
//                    "      <vmName>" + vmName + "</vmName>\n" +
//                    "    </MoveSourceActions>\n" +
//                    "  </soap12:Body>\n" +
//                    "</soap12:Envelope>";
//
//            String header = "POST /Service1.asmx HTTP/1.1\n" +
//                    "Host: server1\n" +
//                    "Content-Type: application/soap+xml; charset=utf-8\n" +
//                    "Content-Length: " + content.length() + "\n\n";
//
//            Socket socket = new Socket(hostName, 80);
//            socket.getOutputStream().write(header.getBytes());
//            socket.getOutputStream().write(content.getBytes());
//            System.out.println(header);
//            System.out.println(content);
            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveSourceActions?path="
                    + path + "&vmName=" + vmName);
            URLConnection connection = url.openConnection();

            System.out.println(url);

//
//            //Send header
//            String data = "path=" + path + "&vmName=" + vmName;
//            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
//
//            wr.write("POST /Service1.asmx/MoveSourceActions HTTP/1.1\r\n");
//            wr.write("Host: " + hostName + "\r\n");
//            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
//            wr.write("Content-Length: " + data.length() + "\r\n");
//            wr.write(data);
//            wr.write("\r\n");
//            wr.write("\r\n");
//
//            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deployVirtualMachine(String from, String to, String vmName, String newName) {
        try {
            //TODO: remove the hardcoded vmName when multiple reference vm's can be defined
            URL url = new URL("http://" + hostName + "/Service1.asmx/DeployVirtualMachine?from="
                    + from + "&to=" + to + "&vmName=" + GlobalVars.BASE_VM_NAME + "&vmCopyName=" + newName + "");
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }
            startVirtualMachine(newName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startVirtualMachine(String vmName) {
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/StartVirtualMachine?vmName=" + vmName);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopVirtualMachine(String vmName) {
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/StopVirtualMachine?vmName=" + vmName);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVirtualMachine(String vmName) {
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/DeleteVirtualMachine?vmName=" + vmName);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {

                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyVirtualMachine(String vmName, int memory, int processorPercentage, int cores) {
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/WakeUpServer?"
                    + "vmName=" + vmName + "&memory=" + memory + "&procSpeed=" + processorPercentage +
                    "&cores=" + cores);
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {
                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deployVirtualMachineWithCustomResources(String from, String to,
                                                        String vmName, String vmCopyName,
                                                        int memory, int processorPercentage, int nrCores) {
        try {

//            String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                    "  <soap:Body>\n" +
//                    "    <DeployVirtualMachineWithModify xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
//                    "      <from>" + from + "</from>\n" +
//                    "      <to>" + to + "</to>\n" +
//                    "      <vmName>" + GlobalVars.BASE_VM_NAME + "</vmName>\n" +
//                    "      <vmCopyName>" + vmCopyName + "</vmCopyName>\n" +
//                    "      <memory>" + memory + "</memory>\n" +
//                    "      <procSpeed>" + processorPercentage + "</procSpeed>\n" +
//                    "      <nrCores>" + nrCores + "</nrCores>\n" +
//                    "    </DeployVirtualMachineWithModify>\n" +
//                    "  </soap:Body>\n" +
//                    "</soap:Envelope>";
//
//            String header = " POST /Service1.asmx HTTP/1.1\n" +
//                    "Host: server1\n" +
//                    "Content-Type: text/xml; charset=utf-8\n" +
//                    "Content-Length: " + content.length() + "\n" +
//                    "SOAPAction: \"http://www.SelfOptimizingDatacenter.edu/DeployVirtualMachineWithModify\"";
//            String content = "from="
//                    + from + "&to=" + to + "&vmName=" + GlobalVars.BASE_VM_NAME + "&vmCopyName=" + vmCopyName
//                    + "&memory=" + memory + "&procSpeed=" + processorPercentage + "&nrCores=" + nrCores + "\n";
//            String header = "POST /Service1.asmx/DeployVirtualMachineWithModify HTTP/1.1\n" +
//                    "Host: server1\n" +
//                    "Content-Type: application/x-www-form-urlencoded\n" +
//                    "Content-Length: " + content.length() + "\n\n";
//
//            Socket socket = new Socket(hostName, 80);
//            socket.getOutputStream().write(header.getBytes());
//            socket.getOutputStream().write(content.getBytes());
//            System.out.println(header);
//            System.out.println(content);

//            //TODO: remove the hardcoded vmName when multiple reference vm's can be defined
            URL url = new URL("http://" + hostName + "/Service1.asmx/DeployVirtualMachineWithModify?from="
                    + from + "&to=" + to + "&vmName=" + GlobalVars.BASE_VM_NAME + "&vmCopyName=" + vmCopyName
                    + "&memory=" + memory + "&procSpeed=" + processorPercentage + "&nrCores=" + nrCores);
            URLConnection connection = url.openConnection();
            System.out.println(url.toString());
//            connection.setDoInput(false);
//            connection.setDoOutput(false);
//            connection.setReadTimeout(0);
//            connection.setConnectTimeout(0);
//            connection.connect();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {
                // Response
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
            }
            startVirtualMachine(vmCopyName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wakeUpServer(String mac, String ipAddress, int port) {
//        System.err.println("Wake up disabled from Hyper-V Management Proxy");
        try {
            URL url = new URL("http://" + GlobalVars.GLOBAL_LOOP_CONTROLLER_IP + "/Service1.asmx/WakeUpServer?"
                    + "mac=" + mac + "&ipAddress=" + GlobalVars.BROADCAST_IP_ADDRESS + "&port=" + GlobalVars.WAKE_UP_PORT);
            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {
                // Response
                String line;
                while ((line = rd.readLine()) != null) {
                    System.out.println(line);
                }
            }
            waitUntilTargetIsAlive(ipAddress);
            //Thread.sleep(60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendServerToSleep() {
//        System.err.println("Sleep disabled from Hyper-V Management Proxy");
        try {
            URL url = new URL("http://" + hostName + "/Service1.asmx/SendServerToSleep");
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (DEBUG) {
                // Response
                String line;
                while ((line = rd.readLine()) != null) {

                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
//        ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy("192.168.2.13");
//       // proxy.moveSourceActions("\\\\192.168.2.10\\VirtualMachines\\Source","Empty");
//        proxy.moveDestinationActions("\\\\192.168.2.10\\VirtualMachines\\Source","\\\\192.168.2.10\\VirtualMachines\\Dest","Empty");
         
    }

}