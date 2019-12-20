package com.hf.hf_smartcloud.bean;

import java.io.Serializable;

public class UserEvent implements Serializable {
    private String dev_name;
    private String mac_addr;
    private String char_uuid;

    private String jsonList;
    public UserEvent() {
    }

    public UserEvent(String dev_name,String mac_addr,String char_uuid) {
        this.dev_name = dev_name;
        this.mac_addr = mac_addr;
        this.char_uuid = char_uuid;
    }
    public String ooo(){
        return "123";
}
    public UserEvent(String jsonList) {
        this.jsonList = jsonList;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public String getMac_addr() {
        return mac_addr;
    }

    public void setMac_addr(String mac_addr) {
        this.mac_addr = mac_addr;
    }

    public String getChar_uuid() {
        return char_uuid;
    }

    public void setChar_uuid(String char_uuid) {
        this.char_uuid = char_uuid;
    }


    public String getJsonList() {
        return jsonList;
    }

    public void setJsonList(String jsonList) {
        this.jsonList = jsonList;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "dev_name='" + dev_name + '\'' +
                ", dev_name='" + dev_name + '\'' +
                ", char_uuid='" + char_uuid + '\'' +
                ", char_uuid='" + jsonList + '\'' +
                '}';
    }
}
