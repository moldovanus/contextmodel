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
     public double distanceTo(ExtendedServerDto server) {
        double minDistance = 100000;   
        double memory1[] = {this.getOptimalMemory(), this.getTotalMemory(), this.getTotalMemory(), this.getOptimalMemory()};
        double cpu1[] = {this.getOptimalCPU(), this.getOptimalCPU(),this.getTotalCPU(),this.getTotalCPU()};

     double memory2[] = {server.getOptimalMemory(), server.getTotalMemory(), server.getTotalMemory(), server.getOptimalMemory()};
        double cpu2[] = {server.getOptimalCPU(), server.getOptimalCPU(),server.getTotalCPU(),server.getTotalCPU()};
         for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double dist = Math.sqrt((Math.pow(memory1[i] - memory2[j], 2) + Math.pow((cpu1[i] - cpu2[j]), 2) ));
                if (dist < minDistance) {
                   minDistance = dist;
                }
            }
        }
        return minDistance;

    }
    public boolean equals(ExtendedServerDto server){
        if (!server.getIpAddress().equalsIgnoreCase(ipAddress)) return false;
        if (!server.getMacAddress().equalsIgnoreCase(macAddress)) return false;
        if (server.getMaximumCPU()!=maximumCPU) return false;
        if (server.getOptimalCPU()!=optimumCPU) return false;
        if (server.getMaximumMemory()!= maximumMemory) return false;
        if (server.getOptimalMemory()!=optimumMemory) return false;
        if (server.getMaximumStorage()!= maximumStorage) return false;
        if (server.getOptimalStorage()!=optimumStorage) return false;

        return true;
    }
    
}
