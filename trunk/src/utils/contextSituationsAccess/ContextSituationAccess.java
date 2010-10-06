package utils.contextSituationsAccess;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 6, 2010
 * Time: 11:57:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContextSituationAccess {

    private static final File file = new File("./situations.xml");

    private ContextSituationAccess() {
    }

    public static Collection<ContextSituationDto> getSituations() throws FileNotFoundException {
        XMLDecoder xmlDecoder = new XMLDecoder(new FileInputStream(file));
        Object content = xmlDecoder.readObject();
        return (content != null && content instanceof Collection) ? (Collection<ContextSituationDto>) content : new ArrayList<ContextSituationDto>();
    }


    public static void saveContextSituations(Collection<ContextSituationDto> dtos) throws FileNotFoundException {
        XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(file));
        xmlEncoder.writeObject(dtos);
        xmlEncoder.flush();
        xmlEncoder.close();
    }


    public static void saveContextSituation(ContextSituationDto situationDto) throws FileNotFoundException {
        Collection<ContextSituationDto> situationDtos = getSituations();
        situationDtos.add(situationDto);
        XMLEncoder xmlEncoder = new XMLEncoder(new FileOutputStream(file));
        xmlEncoder.writeObject(situationDtos);
        xmlEncoder.flush();
        xmlEncoder.close();
    }

//    public static void main(String[] args) {
//        try {
//            Collection<ContextSituationDto> situationDtos = getSituations();
//            situationDtos.size();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
}
