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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SetPwadActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_register_submit;
    private EditText et_pwd,et_repwd;

    private  String sign = "";
    private Gson gson = new Gson();
    private SubmitRegisterEntity submitRegisterEntity;
    private String phoneMail,captcha,password,password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpwad);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            phoneMail = bundle.getString("phoneMail");
            captcha = bundle.getString("captcha");
            setStringSharedPreferences("lszh","lszh",phoneMail);
        }
        initViews();
    }

    private void initViews() {
        et_pwd = findviewByid(R.id.et_pwd);
        et_repwd = findviewByid(R.id.et_repwd);
        bt_register_submit = findviewByid(R.id.bt_register_submit);
        bt_register_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_register_submit:
                password =  et_pwd.getText().toString().trim();
                password2 = et_repwd.getText().toString().trim();
                if(!password.equals(password2)){
                    showToast("两次密码输入不一致");
                    return;
                }
                setStringSharedPreferences("lsmm","lsmm",password);
                submitRegisterInfo();
                break;
        }
    }
    private void submitRegisterInfo() {
        //TODO 获取验证码
        submitRegister();
    }

    private void submitRegister() {
        if(!isConnNet(SetPwadActivity.this)){
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Register");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("account", phoneMail);
        sendCodeSign.put("password", password);
        sendCodeSign.put("captcha", captcha);

        sign = SignUtil.Sign(sendCodeSign);
        try{
            Map<String, String> map = new HashMap<>();
            map.put("service","Customer.Customer.Register");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("account", phoneMail);
            map.put("password", password);
            map.put("captcha", captcha);

            HttpUtils.doPost(Constants.SERVER_BASE_URL+"service=Customer.Customer.Register", map, new Callback() {
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
                                    Toast.makeText(SetPwadActivity.this, submitRegisterEntity.getData().getMsg(), Toast.LENGTH_LONG).show();
                                    openActivity(GuideSettingActivity.class);
                                    finish();
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
}
