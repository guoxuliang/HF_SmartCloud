package com.hf.hf_smartcloud.bean;

public class BleO9Bean {
    private String addressLoc;//地址位置【未存】
    private String consignNub;//寄存器【未存】
    private String onepolice;//一级报警
    private Float onepolice2;//一级报警，不带单位
    private String twopolice;//二级报警
    private String range;//量程
    private double ratio;//分辨率
    private String unit;//单位
    private String gastype;//气体类型
    private String zero;//零点校正值
    private String full;//满量程校正值
    private String realtime;//实时AD值


    public String getAddressLoc() {
        return addressLoc;
    }

    public void setAddressLoc(String addressLoc) {
        this.addressLoc = addressLoc;
    }

    public String getConsignNub() {
        return consignNub;
    }

    public void setConsignNub(String consignNub) {
        this.consignNub = consignNub;
    }

    public String getOnepolice() {
        return onepolice;
    }

    public void setOnepolice(String onepolice) {
        this.onepolice = onepolice;
    }

    public Float getOnepolice2() {
        return onepolice2;
    }

    public void setOnepolice2(Float onepolice2) {
        this.onepolice2 = onepolice2;
    }

    public String getTwopolice() {
        return twopolice;
    }

    public void setTwopolice(String twopolice) {
        this.twopolice = twopolice;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGastype() {
        return gastype;
    }

    public void setGastype(String gastype) {
        this.gastype = gastype;
    }

    public String getZero() {
        return zero;
    }

    public void setZero(String zero) {
        this.zero = zero;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRealtime() {
        return realtime;
    }

    public void setRealtime(String realtime) {
        this.realtime = realtime;
    }
}
