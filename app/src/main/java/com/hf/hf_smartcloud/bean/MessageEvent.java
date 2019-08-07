package com.hf.hf_smartcloud.bean;

import java.util.List;

public class MessageEvent {

    /**
     * openid : o6pw31kr42aQLo5HaQRftxix0O2Y
     * nickname : E=mc²
     * sex : 1
     * language : zh_CN
     * city : Xi'an
     * province : Shaanxi
     * country : CN
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/ZMN0ibI5UQDYibPxreXWysicwDJgRTfVNZde7BKT3dTwP4podgMmKibIDnnz9dibfAuKFbFGOlSjpuvJs4RX1iaImRWQ/132
     * privilege : []
     * unionid : o59pp6Fl46EAtI2Qk7lpZCbGtNU4
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public MessageEvent(String openid, String nickname,int sex, String language,String city, String province,String country, String headimgurl,String unionid) {
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.language = language;
        this.city = city;
        this.province = province;
        this.country = country;
        this.headimgurl = headimgurl;
        this.unionid = unionid;

    }
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
