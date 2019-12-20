package com.hf.hf_smartcloud.ui.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ZfPwdActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_sancode)
    ImageView btnSancode;
    @BindView(R.id.daojs)
    TextView daojs;
    @BindView(R.id.zfpwd_country_name)
    TextView zfpwdCountryName;
    @BindView(R.id.zfpwd_iv_flag)
    ImageView zfpwdIvFlag;
    @BindView(R.id.zfpwd_country_code_text)
    TextView zfpwdCountryCodeText;
    @BindView(R.id.ll_zfpwd_countryCode)
    LinearLayout llZfpwdCountryCode;
    @BindView(R.id.et_zfpwd_mobile)
    TextInputEditText etZfpwdMobile;
    @BindView(R.id.til_zfpwd_mobile)
    TextInputLayout tilZfpwdMobile;
    @BindView(R.id.yzcode)
    EditText yzcode;
    @BindView(R.id.etzfpwd)
    EditText etzfpwd;
    @BindView(R.id.etqrzfpwd)
    EditText etqrzfpwd;
    @BindView(R.id.btzfpwd_submit)
    Button btzfpwdSubmit;

    private String sign = "";
    private String phoneMail;
    private String captcha;
    private Gson gson = new Gson();
    private SendCodeEntity sendCodeEntity;
    private CheckCodeEntity checkCodeEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfpwd);
        ButterKnife.bind(this);
        initTitle();
        initViews();
    }

    private void initTitle() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("设置支付密码");
    }

    private void initViews() {
        daojs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(daojs, 60000, 1000); //倒计时1分钟
                mCountDownTimerUtils.start();
                phoneMail = zfpwdCountryName.getText().toString().trim()+"-"+etZfpwdMobile.getText().toString().trim();
                if (phoneMail == null) {
                    showToast("电话号码不能为空");
                    return;
                }
                getSendCode(phoneMail);
            }
        });
        llZfpwdCountryCode.setOnClickListener(this);
        btzfpwdSubmit.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zfpwd_countryCode:
                //国家码
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
                break;
            case R.id.btzfpwd_submit:
                //提交
                phoneMail = zfpwdCountryName.getText().toString().trim()+"-"+etZfpwdMobile.getText().toString().trim();
                getcheckCode(phoneMail,yzcode.getText().toString().trim());
                break;
        }
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
            zfpwdIvFlag.setImageResource(country.flag);
            zfpwdCountryCodeText.setText(country.name);
            zfpwdCountryName.setText("+" + country.code);
        }
    }

    /**
     * 发送验证码
     */
    private void getSendCode(String phoneMail) {
        if (!isConnNet(ZfPwdActivity.this)) {
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
                        ZfPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (sendCodeEntity.getRet() == 200) {
                                    sendCodeEntity.getData().getMsg();
                                } else {
                                    Toast.makeText(ZfPwdActivity.this, sendCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
    private void getcheckCode(String phoneMail,String captcha) {
        if (!isConnNet(ZfPwdActivity.this)) {
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
                        ZfPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                    String  phone = zfpwdCountryName.getText().toString().trim()+"-"+etZfpwdMobile.getText().toString().trim();
                                    String code = yzcode.getText().toString().trim();
                                    String pwd = etzfpwd.getText().toString().trim();
                                    String repwd = etqrzfpwd.getText().toString().trim();
                                    if (!pwd.equals(repwd)) {
                                        showToast("密码输入不一致");
                                        return;
                                    }
                                    if (phone.equals("")) {
                                        showToast("手机号码不能为空");
                                        return;
                                    }
                                    if (code.equals("")) {
                                        showToast("验证码不能为空");
                                        return;
                                    }

                                    submitZfPwd(phone,code,pwd);
                                } else {
                                    Toast.makeText(ZfPwdActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
     * 提交支付密码
     */
    private void submitZfPwd(String phoneMail,String captcha,String zfpwd) {
        if (!isConnNet(ZfPwdActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Forget_paypassword");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "safe");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("captcha", captcha);
        sendCodeSign.put("set_paypassword", zfpwd);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Verifiy");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "safe");
            map.put("account", phoneMail);
            map.put("captcha", captcha);
            map.put("set_paypassword", zfpwd);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.Forget_paypassword", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-submitZfPwd", "result-submitZfPwd:" + result);
                        checkCodeEntity = gson.fromJson(result, CheckCodeEntity.class);
                        ZfPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                    showToast(checkCodeEntity.getData().getMsg());
                                    finish();
                                } else {
                                    Toast.makeText(ZfPwdActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
