package com.suwish.proc.task.entity;

/**
 * @author by min.su on 2017/2/26.
 */
public class Performance {

    private String cpuUsage;
    private String totalMemory;
    private String catFreeMemory;
    private String apiFreeMemory;
    private String netReceive;
    private String netSend;

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getCatFreeMemory() {
        return catFreeMemory;
    }

    public void setCatFreeMemory(String catFreeMemory) {
        this.catFreeMemory = catFreeMemory;
    }

    public String getApiFreeMemory() {
        return apiFreeMemory;
    }

    public void setApiFreeMemory(String apiFreeMemory) {
        this.apiFreeMemory = apiFreeMemory;
    }

    public String getNetReceive() {
        return netReceive;
    }

    public void setNetReceive(String netReceive) {
        this.netReceive = netReceive;
    }

    public String getNetSend() {
        return netSend;
    }

    public void setNetSend(String netSend) {
        this.netSend = netSend;
    }

    public String getCpuRateDesc(){
        return "";
    }

    public String getMemInfoDesc(){
        return "";
    }

    public String getNetworkDesc(){
        return "";
    }
}
