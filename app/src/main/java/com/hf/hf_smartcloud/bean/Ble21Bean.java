package com.hf.hf_smartcloud.bean;

public class Ble21Bean {
    private String addressLoc;//起始符
    private String consignNub;//寄存器编号【未存】
    private String qtname;//气体类型
    private String nongdu;//浓度
    private String baojing;//状态
    private String unit;//单位
    private String jingdu;//精度

    public String getAddressLoc() {return addressLoc;}

    public void setAddressLoc(String addressLoc) {this.addressLoc = addressLoc;}

    public String getConsignNub() {return consignNub;}

    public void setConsignNub(String consignNub) {this.consignNub = consignNub;}

    public String getQtname() {
        return qtname;
    }

    public void setQtname(String qtname) {
        this.qtname = qtname;
    }

    public String getNongdu() {
        return nongdu;
    }

    public void setNongdu(String nongdu) {
        this.nongdu = nongdu;
    }

    public String getBaojing() {return baojing;}

    public void setBaojing(String baojing) {
        this.baojing = baojing;
    }

    public String getUnit() { return unit; }

    public void setUnit(String unit) {this.unit = unit;}

    public String getJingdu() {return jingdu;}

    public void setJingdu(String jingdu) {this.jingdu = jingdu;}
}
