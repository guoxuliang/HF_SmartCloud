package com.hf.hf_smartcloud.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseFragment;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.CurrentCityEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.http.NetUtils;
import com.hf.hf_smartcloud.ui.activity.MainActivity;
import com.hf.hf_smartcloud.ui.activity.VerCodeActivity;
import com.hf.hf_smartcloud.ui.activity.facility.HistoryMonitoringActivity;
import com.hf.hf_smartcloud.ui.activity.main.AbnormalInfoActivity;
import com.hf.hf_smartcloud.ui.activity.main.BusinessActivity;
import com.hf.hf_smartcloud.ui.activity.main.EquipmentLogActivity;
import com.hf.hf_smartcloud.ui.activity.main.PoliceInfoActivity;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.utils.SystemUtil;
import com.hf.hf_smartcloud.weigets.chart.CircleProgressView;
import com.hf.hf_smartcloud.weigets.chart.WaveView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment1 extends BaseFragment implements View.OnClickListener {
    MainActivity activity = (MainActivity) getActivity();
    View v;
    private WaveView wave;
    //    private EditText et;
//    private Button bt;
    private CircleProgressView circle_progress;
    private LinearLayout ll_policeinfo, ll_abnormal, ll_equipment, business;
    private RelativeLayout rlchart;

    private  String sign = "";
    private  String token = "";
    private Gson gson = new Gson();
    private CurrentCityEntity currentCityEntity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (v == null) {
            v = inflater.inflate(R.layout.fragment1, container, false);
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        token = getStringSharePreferences("token","token");
        initViews();
        String sysModel = SystemUtil.getSystemModel();//设备信息
        String uuid = SystemUtil.getUUID(getActivity());
        String inTime = getTime();
        Log.i("==info","==token:"+token+"==sysModel:"+sysModel+"==uuid:"+uuid+"==inTime:"+inTime);

        if(!token.equals("")){
            getCurrentCity(token);
        }
        if(!sysModel.equals("") && !uuid.equals("")){
            TrustDev(sysModel,uuid);
        }
    }

    private String  getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);
        // time1.setText("Date获取当前日期时间"+simpleDateFormat.format(date));
        return time;
    }

    private void initViews() {
        ll_policeinfo = v.findViewById(R.id.ll_policeinfo);
        ll_abnormal = v.findViewById(R.id.ll_abnormal);
        ll_equipment = v.findViewById(R.id.ll_equipment);
        business = v.findViewById(R.id.business);
        rlchart = v.findViewById(R.id.rlchart);
        ll_policeinfo.setOnClickListener(this);
        ll_abnormal.setOnClickListener(this);
        ll_equipment.setOnClickListener(this);
        business.setOnClickListener(this);
        rlchart.setOnClickListener(this);
        circle_progress = v.findViewById(R.id.circle_progress);
        wave = v.findViewById(R.id.wave);
//        et = v.findViewById(R.id.et);
//        bt = v.findViewById(R.id.bt);
        wave.setProgressWithAnim(Float.valueOf("0.5"));
        circle_progress.startAnimProgress(50, 1200);

//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                wave.setProgressWithAnim(Float.valueOf(et.getText().toString().trim()));
//                wave.setProgressWithAnim(Float.valueOf("0.5"));
//                circle_progress.startAnimProgress(50, 1200);
//                //监听进度条进度
//                circle_progress.setOnAnimProgressListener(new CircleProgressView.OnAnimProgressListener() {
//                    @Override
//                    public void valueUpdate(int progress) {
////                        tv_progress.setText(String.valueOf(progress));//显示进度条的数值
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlchart:
                //TODO 图标历史数据监测  BusinessActivity
                openActivity(HistoryMonitoringActivity.class);
                break;
            case R.id.ll_policeinfo:
                //TODO 报警信息 PoliceInfoActivity
                openActivity(PoliceInfoActivity.class);
                break;
            case R.id.ll_abnormal:
                //TODO 异常信息 AbnormalInfoActivity
                openActivity(AbnormalInfoActivity.class);
                break;
            case R.id.ll_equipment:
                //TODO 设备日志  EquipmentLogActivity
                openActivity(EquipmentLogActivity.class);
                break;
            case R.id.business:
                //TODO 商务合作  BusinessActivity
                openActivity(BusinessActivity.class);
                break;

        }
    }

//=======================获取当前城市=============================================================================================================================================
    private void getCurrentCity(String token) {
        if(!isConnNet(getActivity())){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.At_city");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try{
            Map<String, String> map = new HashMap<>();
            map.put("service","Customer.Safe.At_city");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("token", token);

            HttpUtils.doPost("https://www.huafanyun.com/api/?"+"service=Customer.Safe.At_city", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-getCurrentCity", "result-getCurrentCity:" + result);
                        currentCityEntity = gson.fromJson(result, CurrentCityEntity.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (currentCityEntity.getRet() == 200) {
                                    CurrentCityEntity.DataBean.ListsBean listBean = currentCityEntity.getData().getLists();
                                  String city = String.valueOf(listBean.getCity());
//                                    String city = "宝鸡";
                                  Log.i("city:","city:"+city);
                                  String oldCity = getStringSharePreferences("city","city");
                                  if(!oldCity.equals("")){
                                      if(!oldCity.equals(city)){
                                          showNormalDialog(city);
                                      }
                                  }else {
                                      setStringSharedPreferences("city","city",city);
                                      showNormalDialog(city);
                                  }
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //=======================调用信任城市=============================================================================================================================================
    private void TrustCity(String city) {
        if(!isConnNet(getActivity())){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Trust_city");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("city", city);
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try{
            Map<String, String> map = new HashMap<>();
            map.put("service","Customer.Safe.Trust_city");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("city", city);
            map.put("token", token);

            HttpUtils.doPost(Constants.SERVER_BASE_URL+"service=Customer.Safe.Trust_city", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-TrustCity", "result-TrustCity:" + result);
                        currentCityEntity = gson.fromJson(result, CurrentCityEntity.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (currentCityEntity.getRet() == 200) {
                                    showToast(currentCityEntity.getMsg());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    =======================调用信任设备接口=============================================================================================================================================
    private void TrustDev(String sysModel,String uuid) {
        if(!isConnNet(getActivity())){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Trust_uuid");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("name", sysModel);
        sendCodeSign.put("uuid", uuid);
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try{
            Map<String, String> map = new HashMap<>();
            map.put("service","Customer.Safe.Trust_uuid");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("name", sysModel);
            map.put("uuid", uuid);
            map.put("token", token);

            HttpUtils.doPost(Constants.SERVER_BASE_URL+"service=Customer.Safe.Trust_uuid", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-TrustDev", "result-TrustDev:" + result);
                        currentCityEntity = gson.fromJson(result, CurrentCityEntity.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (currentCityEntity.getRet() == 200) {

                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showNormalDialog(String newcity) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.mipmap.icon_heard);
        normalDialog.setTitle("信任城市");
        normalDialog.setMessage("你确定将"+newcity+"设置为信任城市?");
        normalDialog.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setStringSharedPreferences("city","city","");
                        setStringSharedPreferences("city","city",newcity);
                        if(!newcity.equals("")){
                            TrustCity(newcity);
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }
}