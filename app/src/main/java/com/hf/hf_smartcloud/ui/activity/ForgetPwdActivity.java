package com.hf.hf_smartcloud.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ib_navigation_back)
    ImageButton ibNavigationBack;
    @BindView(R.id.tv_navigation_label)
    TextView tvNavigationLabel;
    @BindView(R.id.repwd_country_name)
    TextView repwdCountryName;
    @BindView(R.id.repwd_flag)
    ImageView repwdFlag;
    @BindView(R.id.repwd_country_code_text)
    TextView repwdCountryCodeText;
    @BindView(R.id.repwdll_countryCode)
    LinearLayout llCountryCode;
    @BindView(R.id.country_code_edit_layout)
    RelativeLayout countryCodeEditLayout;
    @BindView(R.id.et_repwdmobile)
    TextInputEditText etRepwdmobile;
    @BindView(R.id.til_registermobile)
    TextInputLayout tilRegistermobile;
    @BindView(R.id.repwdphonetxt)
    EditText repwdphonetxt;
    @BindView(R.id.repwdtimebutton)
    Button repwdtimebutton;
    @BindView(R.id.repwd_bt_retrieve_submit)
    Button repwdBtRetrieveSubmit;
    @BindView(R.id.tv_retrieve_label)
    TextView tvRetrieveLabel;
    @BindView(R.id.lay_retrieve_container)
    LinearLayout layRetrieveContainer;
    private MyCountDownTimer myCountDownTimer;
    private String sign = "";
    private String phoneMail;
    private Gson gson = new Gson();
    private SendCodeEntity sendCodeEntity;
    private CheckCodeEntity checkCodeEntity;
    private String captcha;
    private String phmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_retrieve_pwd);
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews() {
        ibNavigationBack.setOnClickListener(this);
        llCountryCode.setOnClickListener(this);
        repwdBtRetrieveSubmit.setOnClickListener(this);
        repwdtimebutton.setOnClickListener(this);
        String countrycodeStr = repwdCountryName.getText().toString().trim();
        if (countrycodeStr.equals("+86")) {
            repwdFlag.setImageResource(R.drawable.flag_cn);
            repwdCountryCodeText.setText("中国");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.repwdll_countryCode:
                //TODO 跳转获取国家码页面
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
                break;
            case R.id.repwdtimebutton:
                //TODO 获取验证码
                myCountDownTimer = new ForgetPwdActivity.MyCountDownTimer(60000, 1000);
                myCountDownTimer.start();
                String CountryCode = repwdCountryName.getText().toString().trim();
                String CountryName = repwdCountryCodeText.getText().toString().trim();
                 phoneMail = etRepwdmobile.getText().toString().trim();
                String phmail = CountryCode+"-"+phoneMail;
                 Log.i("==","phoneMail:"+phoneMail+"==CountryCode:"+CountryCode+"==CountryName:"+CountryName);
                if (CountryCode.equals("")) {
                    showToast("国家码不能为空");
                    return;
                }
                if (CountryName.equals("")) {
                    showToast("国家名不能为空");
                    return;
                }
                if (phoneMail.equals("")) {
                    showToast("手机或邮箱不能为空");
                    return;
                }
                getSendCode(phmail);
                break;
            case R.id.repwd_bt_retrieve_submit:
                //TODO 下一步
                captcha = repwdphonetxt.getText().toString().trim();
                if (captcha.equals("")) {
                    showToast("验证码不能为空");
                    return;
                }
                showLoadingDialog("正在加载...");
                getcheckCode();
                break;
        }
    }

    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            repwdtimebutton.setClickable(false);
            repwdtimebutton.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            repwdtimebutton.setText("重新获取");
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_login_submit_lock, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                repwdtimebutton.setBackground(drawable);
            }
            //设置可点击
            repwdtimebutton.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            repwdFlag.setImageResource(country.flag);
            repwdCountryCodeText.setText(country.name);
            repwdCountryName.setText("+" + country.code);
        }
    }

    private void getSendCode(String phoneMail) {
        if(!isConnNet(ForgetPwdActivity.this)){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Send");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "edit");
        sendCodeSign.put("account", phoneMail);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Send");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "edit");
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (sendCodeEntity.getRet() == 200) {
                                    sendCodeEntity.getData().getMsg();
                                } else {
                                    Toast.makeText(ForgetPwdActivity.this, sendCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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

    //=====================验证码校验 ================================================
    private void getcheckCode() {
        if(!isConnNet(ForgetPwdActivity.this)){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Verifiy");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "edit");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("captcha", captcha);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Verifiy");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "edit");
            map.put("account", phoneMail);
            map.put("captcha", captcha);
           dismissLoadingDialog();
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phoneMail", phmail);
                                    bundle.putString("captcha", captcha);
                                    openActivity(ReSetPwadActivity.class, bundle);
                                } else {
                                    Toast.makeText(ForgetPwdActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
