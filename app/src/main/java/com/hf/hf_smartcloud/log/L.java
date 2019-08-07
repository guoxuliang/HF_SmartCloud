package com.hf.hf_smartcloud.log;

import android.util.Log;

/**
 * android 日志打印工具类
 */
public class L {

    //TAG
    public static String TAG = "tonjies";

    //5个等级 DIWE

    public static void d(String text) {
        Log.d(TAG, text + "");
    }

    public static void i(String text) {
        Log.i(TAG, text + "");
    }

    public static void w(String text) {
        Log.w(TAG, text + "");
    }

    public static void e(String text) {
        Log.e(TAG, text + "");
    }

}