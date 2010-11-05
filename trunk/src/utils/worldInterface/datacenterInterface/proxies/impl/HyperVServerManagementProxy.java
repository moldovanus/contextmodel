package utils.worldInterface.datacenterInterface.proxies.impl;

import globalLoop.utils.GlobalVars;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import utils.exceptions.ServiceCenterAccessException;
import utils.worldInterface.datacenterInterface.xmlParsers.ServerInfoSAXHandler;
import utils.worldInterface.dtos.DeployedTaskInfo;
import utils.worldInterface.dtos.ServerDto;
import utils.worldInterface.dtos.ServerInfo;
import utils.worldInterface.dtos.TaskDeployInfo;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
//
//    private void waitUntilTargetIsAlive(String ip) {
//        String pingCmd = "ping " + ip;
//        boolean ok = false;
//        while (!ok) {
//            try {
//                Runtime r = Runtime.getRuntime();
//                Process p = r.exec(pingCmd);
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    System.out.println(inputLine);
//
//                    if (!inputLine.contains("unreachable") && !inputLine.equals("") &&
//                            inputLine.contains("Reply")) {
//                        ok = true;
//                        break;
//                    }
//
//                }
//                in.close();
//
//            }
//            catch (IOException e) {
//                System.out.println(e);
//            }
//        }
//    }
//
//    public ServerDto getServerInfo() {
//
//        ServerDto serverDto = null;
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/GetServerInfo");
//
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String content = "";
//            // Response
//            String line;
//            while ((line = rd.readLine()) != null) {
//                if (DEBUG) {
//                    System.out.println(line);
//                }
//                if (line.length() > 0 && line.charAt(1) != '?') {
//                    content += "\n" + line;
//                }
//            }
//
//            try {
//                XMLReader reader = XMLReaderFactory.createXMLReader();
//                ServerInfoSAXHandler handler = new ServerInfoSAXHandler();
//                reader.setContentHandler(handler);
//                ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
//                InputSource source = new InputSource(in);
//                reader.parse(source);
//                serverDto = handler.getServerDto();
//                if (DEBUG) {
//                    // System.out.println("Handler ret " + handler.ret);
//                }
//                // res = Double.valueOf(handler.ret).doubleValue();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return serverDto;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    public void moveDestinationActions(String from, String to, String vmName) {
//        try {
//            //Socket sock = new Socket(hostName, 80);
//
////             String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
////                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
////                    "  <soap12:Body>\n" +
////                    "    <MoveDestinationActions  xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
////                    "      <from>" + from + "</from>\n" +
////                    "      <to>" + to + "</to>\n" +
////                    "      <vmName>" + vmName + "</vmName>\n" +
////                    "    </MoveDestinationActions >\n" +
////                    "  </soap12:Body>\n" +
////                    "</soap12:Envelope>";
////
////            String header = "POST /Service1.asmx HTTP/1.1\n" +
////                    "Host: server1\n" +
////                    "Content-Type: application/soap+xml; charset=utf-8\n" +
////                    "Content-Length: " + content.length() + "\n\n";
////
////            Socket socket = new Socket(hostName, 80);
////            socket.getOutputStream().write(header.getBytes());
////            socket.getOutputStream().write(content.getBytes());
////            System.out.println(header);
////            System.out.println(content);
//            File file1 = new File(from);
//            File file2 = new File(to);
//            file1.mkdir();
//            file2.mkdir();
//            copyDirectory(new File(from + "/" + vmName + "/"), new File(to + "/" + vmName));
//            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveDestinationActions?from="
//                    + to + "&to=" + to + "&vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//            System.out.println(url);
////            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveDestinationActions");
////            URLConnection connection = url.openConnection();
////            connection.setDoInput(true);
////            connection.setDoOutput(true);
////
////            //Send header
////            String data = "from=" + from + "&to=" + to + "&vmName=" + vmName;
////            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
////            wr.write("POST /Service1.asmx/MoveDestinationActions HTTP/1.1\r\n");
////            wr.write("Host: " + hostName + "\r\n");
////            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
////            wr.write("Content-Length: " + data.length() + "\r\n");
////            wr.write("\r\n");
////            wr.write(data);
////            wr.write("\r\n");
////
////
////            wr.flush();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void waitForOperationToFinish() {
//        try {
//            boolean ok = false;
//            while (!ok) {
//                URL url = new URL("http://" + hostName + "/Service1.asmx/IsJobCompleted");
//
//
//                URLConnection connection = url.openConnection();
//                connection.setDoInput(true);
//
//                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//
//                String content = "";
//                while ((line = rd.readLine()) != null) {
//                    if (DEBUG) {
//                        System.out.println(line);
//                    }
//                    if (line.length() > 0 && line.charAt(1) != '?') {
//                        content += "\n" + line;
//                    }
//                }
//
//
//                XMLReader reader = XMLReaderFactory.createXMLReader();
//                ServerInfoSAXHandler handler = new ServerInfoSAXHandler();
//                reader.setContentHandler(handler);
//                ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
//                InputSource source = new InputSource(in);
//                reader.parse(source);
//
//                ok = handler.isBooleanValue();
//                if (ok == true) break;
//                Thread.sleep(5000);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    public void moveAction1(String path, String vmName) {
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/CreateVirtualSystemSnapshot?vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            System.out.println(url);
//
//            File file = new File(GlobalVars.PHYSICAL_PATH + "Config");
//            file.mkdir();
//            stopVirtualMachine(vmName);
//            url = new URL("http://" + hostName + "/Service1.asmx/ExportVirtualMachineConfig?" +
//                    "vmName=" + vmName + "&exportDirectory=" + GlobalVars.PHYSICAL_PATH + "Config");
//
//            connection = url.openConnection();
//            System.out.println(url);
//            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            deleteVirtualMachine(vmName);
//            copyDirectory(new File(GlobalVars.PHYSICAL_PATH + "Config/" + vmName)
//                    , new File(GlobalVars.PHYSICAL_PATH + vmName));
////            InputStream in = new FileInputStream(GlobalVars.PHYSICAL_PATH + "Config/" + vmName + "/config.xml");
////            OutputStream out = new FileOutputStream(GlobalVars.PHYSICAL_PATH + vmName + "/config.xml");
////
////            // Copy the bits from instream to outstream
////            byte[] buf = new byte[1024];
////            int len;
////            while ((len = in.read(buf)) > 0) {
////                out.write(buf, 0, len);
////            }
////            in.close();
////            out.flush();
////            out.close();
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void moveAction2(String path, String vmName) {
//        try {
////            copyDirectory(new File(GlobalVars.PHYSICAL_PATH + "Config" + "/" + vmName), new File(GlobalVars.PHYSICAL_PATH + "/" + vmName));
//            URL url = new URL("http://" + hostName + "/Service1.asmx/DeployVirtualMachine?folder="
//                    + path + "&vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            System.out.println(url);
//
//            startVirtualMachine(vmName);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void moveSourceActions(String path, String vmName) {
//        try {
//
////            String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
////                    "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
////                    "  <soap12:Body>\n" +
////                    "    <MoveSourceActions xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
////                    "      <path>" + path + "</path>\n" +
////                    "      <vmName>" + vmName + "</vmName>\n" +
////                    "    </MoveSourceActions>\n" +
////                    "  </soap12:Body>\n" +
////                    "</soap12:Envelope>";
////
////            String header = "POST /Service1.asmx HTTP/1.1\n" +
////                    "Host: server1\n" +
////                    "Content-Type: application/soap+xml; charset=utf-8\n" +
////                    "Content-Length: " + content.length() + "\n\n";
////
////            Socket socket = new Socket(hostName, 80);
////            socket.getOutputStream().write(header.getBytes());
////            socket.getOutputStream().write(content.getBytes());
////            System.out.println(header);
////            System.out.println(content);
//
//            URL url = new URL("http://" + hostName + "/Service1.asmx/MoveSourceActions?path="
//                    + path + "&vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//
//            System.out.println(url);
//
////
////            //Send header
////            String data = "path=" + path + "&vmName=" + vmName;
////            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
////
////            wr.write("POST /Service1.asmx/MoveSourceActions HTTP/1.1\r\n");
////            wr.write("Host: " + hostName + "\r\n");
////            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
////            wr.write("Content-Length: " + data.length() + "\r\n");
////            wr.write(data);
////            wr.write("\r\n");
////            wr.write("\r\n");
////
////            wr.flush();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deployVirtualMachine(String from, String to, String vmName, String newName) {
////        try {
////            //TODO: remove the hardcoded vmName when multiple reference vm's can be defined
////            URL url = new URL("http://" + hostName + "/Service1.asmx/DeployVirtualMachine?from="
////                    + from + "&to=" + to + "&vmName=" + GlobalVars.BASE_VM_NAME + "&vmCopyName=" + newName + "");
////            URLConnection connection = url.openConnection();
////            connection.setDoInput(true);
////
////            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            if (DEBUG) {
////
////                // Response
////                String line;
////                while ((line = rd.readLine()) != null) {
////
////                    System.out.println(line);
////                }
////            }
////            startVirtualMachine(newName);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        throw new UnsupportedOperationException("Nu folosi");
//
//    }
//
//    public void startVirtualMachine(String vmName) {
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/StartVirtualMachine?vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//            //connection.setDoInput(true);
//            System.out.println(url);
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void stopVirtualMachine(String vmName) {
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/StopVirtualMachine?vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteVirtualMachine(String vmName) {
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/DeleteVirtualMachine?vmName=" + vmName);
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void modifyVirtualMachine(String vmName, int memory, int processorPercentage, int cores) {
//        try {
//            URL url = new URL("http://" + hostName + "/Service1.asmx/WakeUpServer?"
//                    + "vmName=" + vmName + "&memory=" + memory + "&procSpeed=" + processorPercentage +
//                    "&cores=" + cores);
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//
//                    System.out.println(line);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void copyDirectory(File sourceLocation, File targetLocation)
//            throws IOException {
//
//        if (sourceLocation.isDirectory()) {
//            if (!targetLocation.exists()) {
//                boolean ok = targetLocation.mkdir();
//                System.out.println(ok);
//            }
//
//            String[] children = sourceLocation.list();
//            for (int i = 0; i < children.length; i++) {
//                copyDirectory(new File(sourceLocation, children[i]),
//                        new File(targetLocation, children[i]));
//            }
//        } else {
//
//            InputStream in = new FileInputStream(sourceLocation);
//            OutputStream out = new FileOutputStream(targetLocation);
//
//            // Copy the bits from instream to outstream
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = in.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }
//            in.close();
//            out.flush();
//            out.close();
//        }
//    }
//
//
//    public void deployVirtualMachineWithCustomResources(String from, String to, String serverName, String base,
//                                                        String vmName, String vmCopyName,
//                                                        int memory, int processorPercentage, int nrCores) {
//        try {
//
////            String content = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
////                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
////                    "  <soap:Body>\n" +
////                    "    <DeployVirtualMachineWithModify xmlns=\"http://www.SelfOptimizingDatacenter.edu/\">\n" +
////                    "      <from>" + from + "</from>\n" +
////                    "      <to>" + to + "</to>\n" +
////                    "      <vmName>" + GlobalVars.BASE_VM_NAME + "</vmName>\n" +
////                    "      <vmCopyName>" + vmCopyName + "</vmCopyName>\n" +
////                    "      <memory>" + memory + "</memory>\n" +
////                    "      <procSpeed>" + processorPercentage + "</procSpeed>\n" +
////                    "      <nrCores>" + nrCores + "</nrCores>\n" +
////                    "    </DeployVirtualMachineWithModify>\n" +
////                    "  </soap:Body>\n" +
////                    "</soap:Envelope>";
////
////            String header = " POST /Service1.asmx HTTP/1.1\n" +
////                    "Host: server1\n" +
////                    "Content-Type: text/xml; charset=utf-8\n" +
////                    "Content-Length: " + content.length() + "\n" +
////                    "SOAPAction: \"http://www.SelfOptimizingDatacenter.edu/DeployVirtualMachineWithModify\"";
////            String content = "from="
////                    + from + "&to=" + to + "&vmName=" + GlobalVars.BASE_VM_NAME + "&vmCopyName=" + vmCopyName
////                    + "&memory=" + memory + "&procSpeed=" + processorPercentage + "&nrCores=" + nrCores + "\n";
////            String header = "POST /Service1.asmx/DeployVirtualMachineWithModify HTTP/1.1\n" +
////                    "Host: server1\n" +
////                    "Content-Type: application/x-www-form-urlencoded\n" +
////                    "Content-Length: " + content.length() + "\n\n";
////
////            Socket socket = new Socket(hostName, 80);
////            socket.getOutputStream().write(header.getBytes());
////            socket.getOutputStream().write(content.getBytes());
////            System.out.println(header);
////            System.out.println(content);
//            File targetFile = new File(GlobalVars.PHYSICAL_PATH);
//            targetFile.mkdir();
//            copyDirectory(new File(GlobalVars.PHYSICAL_PATH + base), new File(GlobalVars.PHYSICAL_PATH + vmCopyName));
//
////            //TODO: remove the hardcoded vmName when multiple reference vm's can be defined
//            URL url = new URL("http://" + hostName + "/Service1.asmx/DeployVirtualMachineWithModify?folder="
//                    + from + "&vmName=" + base + "&vmCopyName=" + vmCopyName
//                    + "&memory=" + memory + "&procSpeed=" + processorPercentage + "&nrCores=" + nrCores);
//            URLConnection connection = url.openConnection();
//            System.out.println(url.toString());
//            connection.setDoInput(true);
////            connection.setReadTimeout(900000);
////            connection.connect();
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            if (DEBUG) {
//                // Response
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }
//            // waitForOperationToFinish();
//            url = new URL("http://" + hostName + "/Service1.asmx/ModifyVirtualMachineName?vmName=" + base + "&vmNewName=" + vmCopyName);
//            connection = url.openConnection();
//            //  waitForOperationToFinish();
//            //rename virtual machine
//            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            startVirtualMachine(vmCopyName);
//            // waitForOperationToFinish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void wakeUpServer(String mac, String ipAddress, int port) {
////        System.err.println("Wake up disabled from Hyper-V Management Proxy");
////        try {
////            URL url = new URL("http://" + GlobalVars.GLOBAL_LOOP_CONTROLLER_IP + "/Service1.asmx/WakeUpServer?"
////                    + "mac=" + mac + "&ipAddress=" + GlobalVars.BROADCAST_IP_ADDRESS + "&port=" + GlobalVars.WAKE_UP_PORT);
////            URLConnection connection = url.openConnection();
//////            connection.setDoInput(true);
////            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            if (DEBUG) {
////                // Response
////                String line;
////                while ((line = rd.readLine()) != null) {
////                    System.out.println(line);
////                }
////            }
////            waitUntilTargetIsAlive(ipAddress);
////            //Thread.sleep(60000);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    public void sendServerToSleep() {
////        System.err.println("Sleep disabled from Hyper-V Management Proxy");
////        try {
////            URL url = new URL("http://" + hostName + "/Service1.asmx/SendServerToSleep");
////            URLConnection connection = url.openConnection();
////            connection.setDoInput(true);
////
////            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////            if (DEBUG) {
////                // Response
////                String line;
////                while ((line = rd.readLine()) != null) {
////
////                    System.out.println(line);
////                }
////            }
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    public String getEnergyConsumptionInfo() {
//        try {
//            Calendar cal = Calendar.getInstance();
//            //H = 24H, h = 12h
//            String DATE_FORMAT_NOW = "M/dd/yyyy h:mm:ss";  // aici am pus cu dd k si la minute are 2 mm nu 1 ( adik arata si 01 nu numa 1)
//            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//            String date = sdf.format(cal.getTime());
////            String theDate ="";
////            if (date.split(" ")[0].charAt(0)=='0')
////                theDate = date.split(" ")[0].substring(1);
////                else
////                theDate = date.split(" ")[0];
////            if (theDate.split("/")[1].charAt(0)=='0')
////                theDate = theDate.split("/")[0]+"//"+theDate.split("/")[1].substring(1)+"//"+theDate.split("/")[2];
////            String theTime = "";
////               if (date.split(" ")[1].substring(0,5).charAt(0)=='0')
////                theTime = date.split(" ")[1].substring(1,5);
////                else
////                theTime = date.split(" ")[1].substring(0,5);
////            if (theTime.split(":")[1].charAt(0)=='0')
////                theTime = theTime.split(":")[0]+":"+theTime.split(":")[1].substring(1);
//            String time = date.split(" ")[1];
//            String newTime = time.split(":")[0];
//            if (time.split(":")[2].equalsIgnoreCase("00") || time.split(":")[2].equalsIgnoreCase("01") || time.split(":")[2].equalsIgnoreCase("02") || time.split(":")[2].equalsIgnoreCase("03")) {
//                if (Integer.parseInt(time.split(":")[1]) - 1 < 10) {
//                    newTime += ":0" + (Integer.parseInt(time.split(":")[1]) - 1);
//                } else {
//                    newTime += ":" + (Integer.parseInt(time.split(":")[1]) - 1);
//                }
//            } else {
//                newTime += ":" + time.split(":")[1];
////                        if (Integer.parseInt(time.split(":")[1]) - 1 < 10) {
////                            newTime += ":0" + (Integer.parseInt(time.split(":")[1]) - 1);
////                        } else {
////                            newTime += ":" + (Integer.parseInt(time.split(":")[1]) - 1);
////                        }
//
//
//            }
//            if (time.split(":")[2].equalsIgnoreCase("00")) {
//                newTime += ":56";
//            } else {
//                if (time.split(":")[2].equalsIgnoreCase("01")) {
//                    newTime += ":57";
//                } else {
//                    if (time.split(":")[2].equalsIgnoreCase("02")) {
//                        newTime += ":58";
//                    } else if (time.split(":")[2].equalsIgnoreCase("03")) {
//                        newTime += ":59";
//                    } else {
//                        if (Integer.parseInt(time.split(":")[2]) - 4 < 10) {
//                            newTime += ":0" + (Integer.parseInt(time.split(":")[2]) - 4);
//                        } else {
//                            newTime += ":" + (Integer.parseInt(time.split(":")[2]) - 4);
//                        }
//
//                    }
//                }
//            }
//
//            URL url = new URL("http://" + hostName + "/Service1.asmx/GetPowerConsumption?"
//                    + "time=" + newTime + "&date=" + date.split(" ")[0]);
//
//            URLConnection connection = url.openConnection();
//            connection.setDoInput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//
//            String content = "";
//            while ((line = rd.readLine()) != null) {
//                if (DEBUG) {
//                    System.out.println(line);
//                }
//                if (line.length() > 0 && line.charAt(1) != '?') {
//                    content += "\n" + line;
//                }
//            }
//
//            try {
//                XMLReader reader = XMLReaderFactory.createXMLReader();
//                ServerInfoSAXHandler handler = new ServerInfoSAXHandler();
//                reader.setContentHandler(handler);
//                ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
//                InputSource source = new InputSource(in);
//                reader.parse(source);
//                return handler.getEnergyContent();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        return "";
//    }

//    public static void main(String[] args) {
//        ProxyFactory.setReturnStub(false);
//        ServerManagementProxyInterface proxy = ProxyFactory.createServerManagementProxy("192.168.1.13");
//        ServerManagementProxyInterface proxy1 = ProxyFactory.createServerManagementProxy("192.168.1.11");
//
//        proxy.moveAction1("\\\\192.168.1.10\\VirtualMachines\\Config\\CPUIntensive", "CPUIntensive");
////        proxy1.startVirtualMachine("CPUIntensive");
//        proxy1.moveAction2("\\\\192.168.1.10\\VirtualMachines\\", "CPUIntensive");
////        proxy.deleteVirtualMachine("A");
//    }


    public ServerInfo getServerInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DeployedTaskInfo deployVirtualMachine(TaskDeployInfo deployInfo) throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void startVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteVirtualMachine(String vmName) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void modifyVirtualMachine(String vmName, int memory, int procPercentage, int cores) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void wakeUpServer(String mac, String ipAddress, int port) throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendServerToSleep() throws ServiceCenterAccessException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEnergyConsumptionInfo() throws ServiceCenterAccessException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
