package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 12:52:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedTaskDto extends TaskDto{
    private float cpuWeight;
    private float memWeight;
    private float hddWeight;

    public float getCpuWeight() {
        return cpuWeight;
    }

    public void setCpuWeight(float cpuWeight) {
        this.cpuWeight = cpuWeight;
    }

    public float getMemWeight() {
        return memWeight;
    }

    public void setMemWeight(float memWeight) {
        this.memWeight = memWeight;
    }

    public float getHddWeight() {
        return hddWeight;
    }

    public void setHddWeight(float hddWeight) {
        this.hddWeight = hddWeight;
    }
}
