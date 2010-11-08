package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 8, 2010
 * Time: 11:00:47 AM
 * Adds IP, MAC fields to the standard ServerDto
 */
public class ExtendedServerDto {

    private String ipAddress;
    private String macAddress;
    private String serverName;

    private int maximumCPU;
    private int totalCPU;
    private int optimumCPU;
    private int usedCPU;

    private int maximumMemory;
    private int optimumMemory;
    private int totalMemory;
    private int usedMemory;

    private int maximumStorage;
    private int optimumStorage;
    private int totalStorage;
    private int usedStorage;

    private int coreNo;

    public int getCoreNo() {
        return coreNo;
    }

    public int getTotalCPU() {
        return totalCPU;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setTotalCPU(int totalCPU) {
        this.totalCPU = totalCPU;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getTotalStorage() {
        return totalStorage;
    }

    public void setTotalStorage(int totalStorage) {
        this.totalStorage = totalStorage;
    }

    public void setCoreNo(int coreNo) {
        this.coreNo = coreNo;
    }

    public int getUsedCPU() {
        return usedCPU;
    }

    public void setUsedCPU(int usedCPU) {
        this.usedCPU = usedCPU;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(int usedStorage) {
        this.usedStorage = usedStorage;
    }

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

        double memory1[] = {this.getUsedMemory(), (this.getTotalMemory() + this.getOptimalMemory()) / 2.0, (this.getTotalMemory() + this.getOptimalMemory()) / 2.0, this.getUsedMemory()};
        double cpu1[] = {this.getUsedCPU(), this.getUsedCPU(), (this.getTotalCPU() + this.getOptimalCPU()) / 2.0, (this.getTotalCPU() + this.getOptimalCPU()) / 2.0};

        double memory2[] = {server.getUsedMemory(), (server.getTotalMemory() + server.getOptimalMemory()) / 2.0, (server.getTotalMemory() + server.getOptimalMemory()) / 2.0, server.getUsedMemory()};
        double cpu2[] = {server.getUsedCPU(), server.getUsedCPU(), (server.getTotalCPU() + server.getOptimalCPU()) / 2.0, (server.getTotalCPU() + server.getOptimalCPU()) / 2.0};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double dist = Math.sqrt((Math.pow(memory1[i] - memory2[j], 2) + Math.pow((cpu1[i] - cpu2[j]), 2)));
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        if (this.getCoreNo() != server.getCoreNo()) {
            minDistance *= (Math.abs(coreNo - server.getCoreNo()) + 1);
        }
        return minDistance;

    }

    public boolean equals(ExtendedServerDto server) {
        if (!server.getIpAddress().equalsIgnoreCase(ipAddress)) return false;
        if (!server.getMacAddress().equalsIgnoreCase(macAddress)) return false;
        if (server.getMaximumCPU() != maximumCPU) return false;
        if (server.getOptimalCPU() != optimumCPU) return false;
        if (server.getMaximumMemory() != maximumMemory) return false;
        if (server.getOptimalMemory() != optimumMemory) return false;
        if (server.getMaximumStorage() != maximumStorage) return false;
        if (server.getOptimalStorage() != optimumStorage) return false;
        if (server.getUsedCPU() != usedCPU) return false;
        if (server.getUsedMemory() != usedMemory) return false;
        if (server.getUsedStorage() != usedStorage) return false;

        return true;
    }

}
