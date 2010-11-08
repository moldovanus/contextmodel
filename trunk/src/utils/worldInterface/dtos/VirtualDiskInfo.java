package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 5:35:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualDiskInfo {

    private String type;
    private int size = 1024;
    private String format;
    private String source;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
