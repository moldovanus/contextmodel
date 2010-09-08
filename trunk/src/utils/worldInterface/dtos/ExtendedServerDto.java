package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 11:00:47 AM
 * Adds IP, MAC fields to the standard ServerDto
 */
public class ExtendedServerDto extends ServerDto {

    private String ipAddress;
    private String macAddress;

    private int maximumCPU;
    private int optimumCPU;
    private int maximumMemory;
    private int optimumMemory;
    private int maximumStorage;
    private int optimumStorage;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getMaximumCPU() {
        return maximumCPU;
    }

    public void setMaximumCPU(int maximumCPU) {
        this.maximumCPU = maximumCPU;
    }

    public int getOptimalCPU() {
        return optimumCPU;
    }

    public void setOptimumCPU(int optimumCPU) {
        this.optimumCPU = optimumCPU;
    }

    public int getMaximumMemory() {
        return maximumMemory;
    }

    public void setMaximumMemory(int maximumMemory) {
        this.maximumMemory = maximumMemory;
    }

    public int getOptimalMemory() {
        return optimumMemory;
    }

    public void setOptimumMemory(int optimumMemory) {
        this.optimumMemory = optimumMemory;
    }

    public int getMaximumStorage() {
        return maximumStorage;
    }

    public void setMaximumStorage(int maximumStorage) {
        this.maximumStorage = maximumStorage;
    }

    public int getOptimalStorage() {
        return optimumStorage;
    }

    public void setOptimumStorage(int optimumStorage) {
        this.optimumStorage = optimumStorage;
    }
}
