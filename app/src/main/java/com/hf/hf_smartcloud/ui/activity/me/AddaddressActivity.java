package com.hf.hf_smartcloud.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.UnbundQQWchatEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.ui.activity.LoginActivity;
import com.hf.hf_smartcloud.ui.activity.me.address.AddressBean;
import com.hf.hf_smartcloud.ui.activity.me.address.AreaPickerView;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.views.MyClickButton;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddaddressActivity extends BaseActivity implements View.OnClickListener {

    private AreaPickerView areaPickerView;
    private List<AddressBean> addressBeans;
    private LinearLayout llview;
    private int[] i;
    private TextView tvdizhi;
    private TextView tv_AddCountyCode;
    private TextView address_country_code_text;
    private ImageView address_iv_flag;
private LinearLayout llccode;

    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    private LinearLayout lladdressadd;
    private MyClickButton sb_address;
    private String is_default = "1";
    private Button add_submit;
    private ImageView btn_back;
    private EditText shr_name, shr_yb, shr_phone, shr_xxdizhi;
    String shrName, shrPhone, shrPostcode, shrAddress, shrIs, customer_address_id, isxg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            shrName = bundle.getString("recipients");
            shrPhone = bundle.getString("telephone");
            shrPostcode = bundle.getString("postcode");
            shrAddress = bundle.getString("address");
            shrIs = bundle.getString("is_default");
            isxg = bundle.getString("xg");//是否修改
            customer_address_id = bundle.getString("customer_address_id");
            shr_name.setText(shrName);
            tv_AddCountyCode.setText(shrPhone.substring(0, shrPhone.indexOf("-")));//收货人电话国家码
            shr_phone.setText(shrPhone.substring(shrPhone.indexOf("-") + 1));//收货人电话
            shr_yb.setText(shrPostcode);
            tvdizhi.setText(shrAddress.substring(0, shrAddress.indexOf("市") + 1));
            shr_xxdizhi.setText(shrAddress.substring(shrAddress.indexOf("市") + 1));
            if (isxg != null) {
                add_submit.setText("提交修改");
            }

        }


        Gson gson = new Gson();
        addressBeans = gson.fromJson(getCityJson(), new TypeToken<List<AddressBean>>() {
        }.getType());

        areaPickerView = new AreaPickerView(this, R.style.Dialog, addressBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i = value;
                if (value.length == 3)
                    tvdizhi.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
                else
                    tvdizhi.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
            }
        });
    }

    private void initView() {
        llccode = findviewByid(R.id.llccode);
        address_country_code_text = findviewByid(R.id.address_country_code_text);
        address_iv_flag = findviewByid(R.id.address_iv_flag);
        llview = findViewById(R.id.lladdressadd);
        tvdizhi = findViewById(R.id.shr_dizhi);
        llview.setOnClickListener(this);
        llccode.setOnClickListener(this);
        tv_AddCountyCode = findviewByid(R.id.tv_AddCountyCode);
        tv_AddCountyCode.setOnClickListener(this);
        lladdressadd = findviewByid(R.id.lladdressadd);
        sb_address = findviewByid(R.id.wiperSwitch1);
        sb_address.setOnMbClickListener(new MyClickButton.OnMClickListener() {
            @Override
            public void onClick(boolean isRight) {
                if (isRight) {
                    Toast.makeText(AddaddressActivity.this, "默认", Toast.LENGTH_SHORT).show();
                    is_default = "1";
                } else {
                    Toast.makeText(AddaddressActivity.this, "非默认", Toast.LENGTH_SHORT).show();
//                  cleanDialog(1, "消息推送", "您确定关闭消息推送?");
                    is_default = "0";
                }
            }
        });
        add_submit = findviewByid(R.id.add_submit);
        add_submit.setOnClickListener(this);
        shr_name = findviewByid(R.id.shr_name);
        shr_yb = findviewByid(R.id.shr_yb);
        shr_phone = findviewByid(R.id.shr_phone);
        shr_xxdizhi = findviewByid(R.id.shr_xxdizhi);
        btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String countrycodeStr = tv_AddCountyCode.getText().toString().trim();
        if (countrycodeStr.equals("+86")) {
            address_iv_flag.setImageResource(R.drawable.flag_cn);
            address_country_code_text.setText("中国");
        }
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


    /**
     * ==============================添加收获地址=======================================================
     */
    private void appedAddress(String recipients, String telephone, String postcode, String address, String is_default) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Address.Add_address");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sendCodeSign.put("recipients", recipients);
        sendCodeSign.put("telephone", telephone);
//        sendCodeSign.put("email", "email");
        sendCodeSign.put("postcode", postcode);
        sendCodeSign.put("address", address);
        sendCodeSign.put("is_default", is_default);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Add_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("recipients", recipients);
            map.put("telephone", telephone);
//            map.put("email", "email");
            map.put("postcode", postcode);
            map.put("address", address);
            map.put("is_default", is_default);
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
                        Log.i("result-appedAddress", "result-appedAddress:" + result);
                        //由于只返回成功或失败，所以此处用已有的实体bean接收。提高代码的利用率
                        UnbundQQWchatEntity unbundQQWchatEntity = gson.fromJson(result, UnbundQQWchatEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (unbundQQWchatEntity.getRet() == 200) {
                                    showToast(unbundQQWchatEntity.getMsg());
                                    finish();
                                    openActivity(AddressListActivity.class);
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
    private void modifyAddress(String recipients, String telephone, String postcode, String address, String is_default, String customer_address_id) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Address.Modify_address");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sendCodeSign.put("recipients", recipients);
        sendCodeSign.put("telephone", telephone);
        sendCodeSign.put("postcode", postcode);
        sendCodeSign.put("address", address);
        sendCodeSign.put("customer_address_id", customer_address_id);
        sendCodeSign.put("is_default", is_default);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Modify_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("recipients", recipients);
            map.put("telephone", telephone);
            map.put("postcode", postcode);
            map.put("address", address);
            map.put("customer_address_id", customer_address_id);
            map.put("is_default", is_default);
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
                                    finish();
                                    openActivity(AddressListActivity.class);
                                } else {
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_AddCountyCode:
//                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
//                break;
            case R.id.llccode:
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
                break;
            case R.id.add_submit:
                if (isxg == null) {
                    String recipients = shr_name.getText().toString().trim();
                    String postcode = shr_yb.getText().toString().trim();
                    String telephone = tv_AddCountyCode.getText().toString().trim() + "-" + shr_phone.getText().toString().trim();
//                String telephone = shr_phone.getText().toString().trim();
                    String address = shr_xxdizhi.getText().toString().trim();
                    String allAddress = tvdizhi.getText().toString().trim() + address;
                    Log.i("allAddress", "allAddress" + allAddress);
                    if (recipients.equals("")) {
                        showToast("收货人不能为空");
                        return;
                    }
                    if (postcode.equals("")) {
                        showToast("邮编不能为空");
                        return;
                    }

                    if (telephone.equals("")) {
                        showToast("手机号不能为空");
                        return;
                    }
                    if (address.equals("")) {
                        showToast("地址不能为空");
                        return;
                    }
                    appedAddress(recipients, telephone, postcode, allAddress, is_default);
                } else {
                    String recipients = shr_name.getText().toString().trim();
                    String postcode = shr_yb.getText().toString().trim();
                    String telephone = tv_AddCountyCode.getText().toString().trim() + "-" + shr_phone.getText().toString().trim();
//                String telephone = shr_phone.getText().toString().trim();
                    String address = shr_xxdizhi.getText().toString().trim();
                    String allAddress = tvdizhi.getText().toString().trim() + address;
                    Log.i("allAddress", "allAddress" + allAddress);
                    if (recipients.equals("")) {
                        showToast("收货人不能为空");
                        return;
                    }
                    if (postcode.equals("")) {
                        showToast("邮编不能为空");
                        return;
                    }

                    if (telephone.equals("")) {
                        showToast("手机号不能为空");
                        return;
                    }
                    if (address.equals("")) {
                        showToast("地址不能为空");
                        return;
                    }
                    modifyAddress(recipients, telephone, postcode, allAddress, is_default, customer_address_id);
                }
                break;
            case R.id.lladdressadd:
                areaPickerView.setSelect(i);
                areaPickerView.show();
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode:::", "requestCode::" + requestCode + "resultCode:::" + resultCode + "data:::" + data.toString());
        if (data != null && !data.equals("")) {
            //国家码回调
            if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
                Country country = Country.fromJson(data.getStringExtra("country"));
                tv_AddCountyCode.setText("+" + country.code);
                address_iv_flag.setImageResource(country.flag);
                address_country_code_text.setText(country.name);
                String countrycode = String.valueOf(country.code);
                if (!countrycode.equals("86")) {
                    lladdressadd.setVisibility(View.GONE);
                } else {
                    lladdressadd.setVisibility(View.VISIBLE);
                }
            }
        } else {
            showToast("没有选择国家码");
        }

    }


}

