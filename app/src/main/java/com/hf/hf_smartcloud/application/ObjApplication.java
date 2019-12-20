package com.hf.hf_smartcloud.application;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.ui.jpush.Logger;
import com.orhanobut.hawk.Hawk;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

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
        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));
        super.onCreate();
        registerToWX();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Hawk.init(this).build();
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
    /**
     * 获取OkHttpClient
     * 设置允许HTTPS
     * */
    public static OkHttpClient getOkHttpClient(InputStream... certificates)
    {
        SSLSocketFactory sslSocketFactory = HttpUtils.getSslSocketFactory(certificates, null, null);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder = builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        });
        return builder.build();
    }
}