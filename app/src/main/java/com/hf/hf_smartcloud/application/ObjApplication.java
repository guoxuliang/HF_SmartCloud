package com.hf.hf_smartcloud.application;

import android.app.Application;

import com.hf.hf_smartcloud.ui.jpush.Logger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class ObjApplication extends Application {
    private static final String TAG = "JIGUANG-Example";
    public static IWXAPI mWxApi;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();
        registerToWX();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, "wxd374bbe7c38365e8", false);
        // 将该app注册到微信
        mWxApi.registerApp("wxd374bbe7c38365e8");
    }

    private static ObjApplication sInstance;

    public ObjApplication() {
        super();
        sInstance = this;
    }

    public static ObjApplication getInstance() {
        return sInstance;
    }
}