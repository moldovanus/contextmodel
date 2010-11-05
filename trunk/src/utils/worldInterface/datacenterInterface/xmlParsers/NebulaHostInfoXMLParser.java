package utils.worldInterface.datacenterInterface.xmlParsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import utils.worldInterface.dtos.ServerInfo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 4:18:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class NebulaHostInfoXMLParser {
    
    private NebulaHostInfoXMLParser() {
    }

    public static ServerInfo parseServerInfo(String info) throws Exception {

        InputStream stream = new ByteArrayInputStream(info.getBytes());
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(stream);

        Node state = document.getElementsByTagName("STATE").item(0);

        Node memUsage = document.getElementsByTagName("USED_MEM").item(0);
        Node cpuUsage = document.getElementsByTagName("USED_CPU").item(0);
        Node diskUsage = document.getElementsByTagName("USED_DISK").item(0);

        Node memMax = document.getElementsByTagName("MAX_MEM").item(0);
        Node cpuMax = document.getElementsByTagName("MAX_CPU").item(0);
        Node diskMax = document.getElementsByTagName("MAX_DISK").item(0);

//        Node memFree = document.getElementsByTagName("FREE_MEM").item(0);
//        Node cpuFree = document.getElementsByTagName("FREE_CPU").item(0);
//        Node diskFree = document.getElementsByTagName("FREE_DISK").item(0);

        ServerInfo dto = new ServerInfo();
        dto.setState(Integer.parseInt(state.getTextContent()));

        dto.setTotalCpu(Integer.parseInt(cpuMax.getTextContent()));
        dto.setUsedCpu(Integer.parseInt(cpuUsage.getTextContent()));

        dto.setTotalMem(Integer.parseInt(memMax.getTextContent()));
        dto.setUsedMem(Integer.parseInt(memUsage.getTextContent()));

        dto.setTotalDisk(Integer.parseInt(diskMax.getTextContent()));
        dto.setUsedDisk(Integer.parseInt(diskUsage.getTextContent()));

        return dto;
    }
}
