package com.hf.hf_smartcloud.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.SubmitRegisterEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReSetPwadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.resetpwd)
    EditText resetpwd;
    @BindView(R.id.resetpwdtwo)
    EditText resetpwdtwo;
    @BindView(R.id.bt_resetpwd_submit)
    Button btResetpwdSubmit;

    private String sign = "";
    private Gson gson = new Gson();
    private SubmitRegisterEntity submitRegisterEntity;
    private String phoneMail, captcha, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwad);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        phoneMail = bundle.getString("phoneMail");
        captcha = bundle.getString("captcha");
        Log.i("==phoneMail", "phoneMail:" + phoneMail + "==captcha:" + captcha);
        initViews();
    }

    private void initViews() {
        btResetpwdSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_resetpwd_submit:
                password = resetpwd.getText().toString().trim();
                password2 = resetpwdtwo.getText().toString().trim();
                if (!password.equals(password2)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                //TODO 获取验证码
                submitRegister();
                break;
        }
    }

//    private TextWatcher editclick = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
//        @Override
//        public void afterTextChanged(Editable s) {
//            String money = etColler.getText().toString();
//            Pattern p = Pattern.compile("[0-9]*");
//            Matcher m = p.matcher(money);
//            if (m.matches()) {
//            } else {
//                ToastUtil.toastCenter(BuyCuurseActivity.this, "请输入正确的缴费金额");
//                etColler.setText("");
//            }
//        }
//    };

    //===============获取验证码=============================================================================================================================
//TODO 获取验证码
    private void submitRegister() {
        if(!isConnNet(ReSetPwadActivity.this)){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Safe.Forget_password");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("set_password", password);
        sendCodeSign.put("captcha", captcha);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Safe.Forget_password");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("account", phoneMail);
            map.put("set_password", password);
            map.put("captcha", captcha);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.Forget_password", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-submitRegister", "result-submitRegister:" + result);
                        submitRegisterEntity = gson.fromJson(result, SubmitRegisterEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (submitRegisterEntity.getRet() == 200) {
                                    submitRegisterEntity.getData().getMsg();
                                    Toast.makeText(ReSetPwadActivity.this, submitRegisterEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
                                    openActivity(LoginActivity.class);
                                    finish();
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
