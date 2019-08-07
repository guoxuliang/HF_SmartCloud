package com.hf.hf_smartcloud.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//签名工具
public class SignUtil {
    public static String Sign(HashMap<String, String> map) {
        String md5=null;
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();
        Set<String> strings = map.keySet();
        List list=new ArrayList<String>(strings);
        Collections.sort(list);
        for (int i = 0; i <list.size() ; i++) {
            valueList.add(map.get(list.get(i)));
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < valueList.size(); i++) {
            stringBuffer.append(valueList.get(i));
        }
        String s = "apisign:" + stringBuffer;
        try {
            String s1 = new String(s.getBytes(), "utf-8");
            Log.i("aaa", "Sign: "+s1);
             md5= string2MD5(s1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5;
    }


    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        if (TextUtils.isEmpty(inStr)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(inStr.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}
