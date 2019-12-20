package com.hf.hf_smartcloud.bean;

public class CheckBoxBean {
    private boolean isChecked;
    private String name;
    private String mac;
    private String uuid;
    public CheckBoxBean(){

    }
    public CheckBoxBean(boolean isCheched) {
        this.isChecked = isCheched;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
