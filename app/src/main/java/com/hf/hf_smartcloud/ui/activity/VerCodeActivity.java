package com.hf.hf_smartcloud.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.CheckCodeEntity;
import com.hf.hf_smartcloud.entity.SendCodeEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.PhoneCode;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VerCodeActivity extends BaseActivity implements View.OnClickListener {
    private PhoneCode pc_1;
    private MyCountDownTimer myCountDownTimer;
    private Button bt_register_submit;
    private TextView phoneMailLocation;
    private String sign = "";
    private String phoneMail;
    private Gson gson = new Gson();

    private SendCodeEntity sendCodeEntity;
    private CheckCodeEntity checkCodeEntity;

    private String captcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vercode);
        getBundle();
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
        initViews();
        myCountDownTimer.start();
        /**
         * 获取验证码
         */
        SendCode();
    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phoneMail = bundle.getString("phoneEma");
            Log.i("====gxl===", "====gxl===" + phoneMail);
        }
    }

    private void initViews() {
        phoneMailLocation = findviewByid(R.id.phoneMailLocation);
        phoneMailLocation.setText("验证码已发送至:" + phoneMail);
        bt_register_submit = findviewByid(R.id.bt_register_submit);
        bt_register_submit.setOnClickListener(this);
        pc_1 = (PhoneCode) findViewById(R.id.pc_1);
        //注册事件回调（根据实际需要，可写，可不写）
        pc_1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                //TODO: 例如底部【下一步】按钮可点击
                captcha = pc_1.getPhoneCode();
                if (!captcha.equals("")) {
                    checkCode();
                }
                showToast("监听onSucess");
            }

            @Override
            public void onInput() {
                //TODO:例如底部【下一步】按钮不可点击
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_submit:
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_shape_corner_gray, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bt_register_submit.setBackground(drawable);
                }
                myCountDownTimer.start();
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
            bt_register_submit.setClickable(false);
            bt_register_submit.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            bt_register_submit.setText("重新获取");
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_login_submit_lock, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                bt_register_submit.setBackground(drawable);
            }
            //设置可点击
            bt_register_submit.setClickable(true);
        }
    }
//===============获取验证码接口======================================================================

    /**
     * 获取验证码接口
     */
    private void SendCode() {
        //TODO 获取验证码
        getSendCode();
    }

    private void getSendCode() {
        if (!isConnNet(VerCodeActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Send");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "register");
        sendCodeSign.put("account", phoneMail);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Send");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "register");
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
                                    Toast.makeText(VerCodeActivity.this, sendCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
    private void checkCode() {
        //TODO 获取验证码
//        captcha = pc_1.getPhoneCode();
        getcheckCode();
    }

    private void getcheckCode() {
        if (!isConnNet(VerCodeActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Captcha.Verifiy");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("type", "register");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("captcha", captcha);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Captcha.Verifiy");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("type", "register");
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkCodeEntity.getRet() == 200) {
                                    checkCodeEntity.getData().getMsg();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("phoneMail", phoneMail);
                                    bundle.putString("captcha", captcha);
                                    openActivity(SetPwadActivity.class, bundle);
                                } else {
                                    Toast.makeText(VerCodeActivity.this, checkCodeEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
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
