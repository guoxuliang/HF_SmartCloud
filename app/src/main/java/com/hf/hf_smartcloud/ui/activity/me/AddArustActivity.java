package com.hf.hf_smartcloud.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
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
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.CheckCodeEntity;
import com.hf.hf_smartcloud.entity.SendCodeEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.CountDownTimerUtils;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddArustActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_sancode)
    ImageView btnSancode;
    @BindView(R.id.trust_country_name)
    TextView trustCountryName;
    @BindView(R.id.trust_iv_flag)
    ImageView trustIvFlag;
    @BindView(R.id.trust_country_code_text)
    TextView trustCountryCodeText;
    @BindView(R.id.ll_trust_countryCode)
    LinearLayout llTrustCountryCode;
    @BindView(R.id.trust_country_code_layout)
    RelativeLayout trustCountryCodeLayout;
    @BindView(R.id.trust_yzcode)
    EditText trustYzcode;
    @BindView(R.id.trust_daojs)
    TextView trustDaojs;
    @BindView(R.id.btzfpwd_submit)
    Button btzfpwdSubmit;
    @BindView(R.id.et_trust_mobile)
    EditText etTrustMobile;


    private String sign = "";
    private String phoneMail;
    private String captcha;
    private Gson gson = new Gson();
    private SendCodeEntity sendCodeEntity;
    private CheckCodeEntity checkCodeEntity;
    private String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addarust);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
             flag = bundle.getString("falg");
        }

        initViews();
    }

    private void initViews() {
        trustDaojs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(trustDaojs, 60000, 1000); //倒计时1分钟
                mCountDownTimerUtils.start();
                String phoneMail = trustCountryName.getText().toString().trim() + "-" + etTrustMobile.getText().toString().trim();
                if (phoneMail == null) {
                    showToast("电话号码不能为空");
                    return;
                }
                getSendCode(phoneMail);
            }
        });
        llTrustCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //国家码
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
            }
        });
        btzfpwdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                phoneMail = trustCountryName.getText().toString().trim()+"-"+etTrustMobile.getText().toString().trim();
                getcheckCode(phoneMail,trustYzcode.getText().toString().trim());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Log.i("requestCode:::", "requestCode::" + requestCode + "resultCode:::" + resultCode + "data:::" + data.toString());

        //国家码回调
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            trustIvFlag.setImageResource(country.flag);
            trustCountryCodeText.setText(country.name);
            trustCountryName.setText("+" + country.code);
        }
    }

    /**
     * 发送验证码
     */
    private void getSendCode(String phoneMail) {
        if (!isConnNet(AddArustActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Send");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "safe");
        sendCodeSign.put("account", phoneMail);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Send");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "safe");
            map.put("account", phoneMail);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Publicity.Captcha.Send", map, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-getSendCode", "result-getSendCode:" + result);
                        sendCodeEntity = gson.fromJson(result, SendCodeEntity.class);
                        AddArustActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (sendCodeEntity.getRet() == 200) {
                                    sendCodeEntity.getData().getMsg();
                                } else {
                                    Toast.makeText(AddArustActivity.this, sendCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
     * 验证码校验
     */
    private void getcheckCode(String phoneMail, String captcha) {
        if (!isConnNet(AddArustActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Verifiy");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "safe");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("captcha", captcha);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Verifiy");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "safe");
            map.put("account", phoneMail);
            map.put("captcha", captcha);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Publicity.Captcha.Verifiy", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-getcheckCode", "result-getcheckCode:" + result);
                        checkCodeEntity = gson.fromJson(result, CheckCodeEntity.class);
                        AddArustActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                    String phone = trustCountryName.getText().toString().trim() + "-" + etTrustMobile.getText().toString().trim();
                                    String code = trustYzcode.getText().toString().trim();
                                    //TODO 提交接口
                                    addTrust(code);
                                } else {
                                    Toast.makeText(AddArustActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
     * 添加常驻城市
     */
    private void addTrust( String captcha) {
        if (!isConnNet(AddArustActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Trust_city");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("city", getStringSharePreferences("city","city"));
        sendCodeSign.put("captcha", captcha);
        sendCodeSign.put("token", getStringSharePreferences("token","token"));
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Safe.Trust_city");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("city", getStringSharePreferences("city","city"));
            map.put("captcha", captcha);
            map.put("token", getStringSharePreferences("token","token"));

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.Trust_city", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-addTrust", "result-addTrust:" + result);
                        checkCodeEntity = gson.fromJson(result, CheckCodeEntity.class);
                        AddArustActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                } else {
                                    Toast.makeText(AddArustActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
     * 添加信任设备
     */
    private void addDev( String captcha,String uuid,String name) {
        if (!isConnNet(AddArustActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Trust_uuid");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("name", name);
        sendCodeSign.put("captcha", captcha);
        sendCodeSign.put("uuid", uuid);
        sendCodeSign.put("token", getStringSharePreferences("token","token"));
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Safe.Trust_uuid");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("name", name);
            map.put("captcha", captcha);
            map.put("uuid", uuid);
            map.put("token", getStringSharePreferences("token","token"));

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.Trust_uuid", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-addTrust", "result-addTrust:" + result);
                        checkCodeEntity = gson.fromJson(result, CheckCodeEntity.class);
                        AddArustActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                } else {
                                    Toast.makeText(AddArustActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
