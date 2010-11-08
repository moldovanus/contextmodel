package utils.worldInterface.dtos;

/**
 * Created by IntelliJ IDEA.
 * User: oneadmin
 * Date: Nov 8, 2010
 * Time: 11:55:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalHost {
    public static final String IM_KVM = "im_kvm";
    public static final String IM_XEN = "im_xen";
    public static final String VMM_KVM = "vmm_kvm";
    public static final String VMM_XEN = "vmm_xen";
    public static final String TM_SSH = "tm_ssh";
    public static final String TM_NFS = "tm_nfs";


    private int id;
    private String hostname;
    private String mac;
    private String im;
    private String vmm;
    private String tm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getVmm() {
        return vmm;
    }

    public void setVmm(String vmm) {
        this.vmm = vmm;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }
}
