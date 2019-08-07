package com.hf.hf_smartcloud.ui.activity.me;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.UnbundQQWchatEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.ui.activity.me.address.AddressBean;
import com.hf.hf_smartcloud.ui.activity.me.address.AreaPickerView;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddaddressActivity extends BaseActivity {

    private AreaPickerView areaPickerView;
    private List<AddressBean> addressBeans;
    private LinearLayout llview;
    private int[] i;
    private TextView tvdizhi;

    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
initView();
        Gson gson = new Gson();
        addressBeans = gson.fromJson(getCityJson(), new TypeToken<List<AddressBean>>() {
        }.getType());

        areaPickerView = new AreaPickerView(this, R.style.Dialog, addressBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i=value;
                if (value.length == 3)
                    tvdizhi.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
                else
                    tvdizhi.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
            }
        });
    }

    private void initView() {
//        llview = findViewById(R.id.lladdressadd);
//        tvdizhi = findViewById(R.id.tvdizhi);
//        llview.setOnClickListener(this);
    }

    public void tvdizhi(View view) {
        tvdizhi = (TextView) view;
        areaPickerView.setSelect(i);
        areaPickerView.show();
    }

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.lladdressadd:
//                areaPickerView.setSelect(i);
//                areaPickerView.show();
//                break;
//        }
//    }

/**
 * ==============================添加收获地址=======================================================
  */
private void delAddress() {
    if (!isConnNet(this)) {
        showToast("请检查网络");
        return;
    }
    HashMap<String, String> sendCodeSign = new HashMap<>();
    sendCodeSign.put("service", "Customer.Address.Add_address");
    sendCodeSign.put("language", "zh_cn");
    sendCodeSign.put("token", getStringSharePreferences("token","token"));
    sendCodeSign.put("recipients", "recipients");
    sendCodeSign.put("telephone", "telephone");
    sendCodeSign.put("email", "email");
    sendCodeSign.put("postcode", "postcode");
    sendCodeSign.put("address", "address");
    sendCodeSign.put("is_default", "is_default");

    sign = SignUtil.Sign(sendCodeSign);
    try {
        Map<String, String> map = new HashMap<>();
        map.put("service", "Customer.Address.Add_address");
        map.put("language", "zh_cn");
        map.put("token", getStringSharePreferences("token","token"));
        map.put("recipients", "recipients");
        map.put("telephone", "telephone");
        map.put("email", "email");
        map.put("postcode", "postcode");
        map.put("address", "address");
        map.put("is_default", "is_default");
        map.put("sign", sign);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Address.Add_address", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result-delAddress", "result-delAddress:" + result);
                    //由于只返回成功或失败，所以此处用已有的实体bean接收。提高代码的利用率
                    UnbundQQWchatEntity unbundQQWchatEntity = gson.fromJson(result, UnbundQQWchatEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (unbundQQWchatEntity.getRet() == 200) {
                                showToast(unbundQQWchatEntity.getMsg());
                                finish();
                            } else {
                                showToast(unbundQQWchatEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//==============================修改收获地址========================================================
private void modifyAddress() {
    if (!isConnNet(this)) {
        showToast("请检查网络");
        return;
    }
    HashMap<String, String> sendCodeSign = new HashMap<>();
    sendCodeSign.put("service", "Customer.Address.Modify_address");
    sendCodeSign.put("language", "zh_cn");
    sendCodeSign.put("token", getStringSharePreferences("token","token"));
    sendCodeSign.put("recipients", "recipients");
    sendCodeSign.put("telephone", "telephone");
    sendCodeSign.put("email", "email");
    sendCodeSign.put("postcode", "postcode");
    sendCodeSign.put("address", "address");
    sendCodeSign.put("customer_address_id", "customer_address_id");
    sendCodeSign.put("is_default", "is_default");

    sign = SignUtil.Sign(sendCodeSign);
    try {
        Map<String, String> map = new HashMap<>();
        map.put("service", "Customer.Address.Modify_address");
        map.put("language", "zh_cn");
        map.put("token", getStringSharePreferences("token","token"));
        map.put("recipients", "recipients");
        map.put("telephone", "telephone");
        map.put("email", "email");
        map.put("postcode", "postcode");
        map.put("address", "address");
        map.put("customer_address_id", "customer_address_id");
        map.put("is_default", "is_default");
        map.put("sign", sign);

        HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Address.Modify_address", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("错误", "错误：" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Log.i("result-modifyAddress", "result-modifyAddress:" + result);
                    //由于只返回成功或失败，所以此处用已有的实体bean接收。提高代码的利用率
                    UnbundQQWchatEntity unbundQQWchatEntity = gson.fromJson(result, UnbundQQWchatEntity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (unbundQQWchatEntity.getRet() == 200) {
                                showToast(unbundQQWchatEntity.getMsg());
                                finish();
                            } else {
                                showToast(unbundQQWchatEntity.getMsg());
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}

