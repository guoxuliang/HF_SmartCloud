package com.hf.hf_smartcloud.bean;

public class RecordBean {
    private String address;//地址
    private String register;//寄存器
    private String date;//时间
    private String recordtype;//记录类型
    private String gastype;//气体类型/事件
    private String precision;//精度
    private String showvalue;//显示值
    private String param;//参数
    private String state;//状态
    private String unit;//单位

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }


//    public RecordBean(String address, String register, String date, String recordtype, String gastype, String showvalue) {
//        super();
//        this.address = address;
//        this.register = register;
//        this.date = date;
//        this.recordtype = recordtype;
//        this.gastype = gastype;
//        this.showvalue = showvalue;
//
//    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getGastype() {
        return gastype;
    }

    public void setGastype(String gastype) {
        this.gastype = gastype;
    }

    public String getShowvalue() {
        return showvalue;
    }

    public void setShowvalue(String showvalue) {
        this.showvalue = showvalue;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getunit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
