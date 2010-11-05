package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: utcn
 * Date: Nov 4, 2010
 * Time: 10:32:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerInfo {

    public static final int STATE_PEND = 0;

    private int totalCpu;//coreNO*100
    private float usedCpu;

    private int totalMem;
    private float usedMem;

    private int totalDisk;
    private float usedDisk;

    private int state; //

    public int getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(int totalCpu) {
        this.totalCpu = totalCpu;
    }

    public float getUsedCpu() {
        return usedCpu;
    }

    public void setUsedCpu(float usedCpu) {
        this.usedCpu = usedCpu;
    }

    public int getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(int totalMem) {
        this.totalMem = totalMem;
    }

    public float getUsedMem() {
        return usedMem;
    }

    public void setUsedMem(float usedMem) {
        this.usedMem = usedMem;
    }

    public int getTotalDisk() {
        return totalDisk;
    }

    public void setTotalDisk(int totalDisk) {
        this.totalDisk = totalDisk;
    }

    public float getUsedDisk() {
        return usedDisk;
    }

    public void setUsedDisk(float usedDisk) {
        this.usedDisk = usedDisk;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
