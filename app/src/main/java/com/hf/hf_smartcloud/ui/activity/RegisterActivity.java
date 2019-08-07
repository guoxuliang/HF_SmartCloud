package com.hf.hf_smartcloud.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.utils.StringHelper;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_countryCode;
    private TextView regist_country_name, regist_country_code_text, server_tiaokuan, tv_back;
    private ImageView iv_flag;//国旗
    private TextInputEditText et_registermobile;//电话号码
    private Button bt_register_submit;//获取验证码按钮
    private CheckBox cb_protocol;//勾选条款

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_main_register_step_one);
        initViews();
    }

    private void initViews() {
        ll_countryCode = findviewByid(R.id.ll_countryCode);
        regist_country_name = findviewByid(R.id.regist_country_name);//国家码
        regist_country_code_text = findviewByid(R.id.regist_country_code_text);//国家名字
        server_tiaokuan = findviewByid(R.id.server_tiaokuan);//条款
        et_registermobile = findviewByid(R.id.et_registermobile);//电话号码
        bt_register_submit = findviewByid(R.id.bt_register_submit);//提交按钮
        cb_protocol = findviewByid(R.id.cb_protocol);//勾选
        iv_flag = findviewByid(R.id.iv_flag);//国旗
        tv_back = findviewByid(R.id.tv_back);//返回
        ll_countryCode.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        bt_register_submit.setOnClickListener(this);
        String countrycodeStr = regist_country_name.getText().toString().trim();
        if (countrycodeStr.equals("+86")) {
            iv_flag.setImageResource(R.drawable.flag_cn);
            regist_country_code_text.setText("中国");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_countryCode:
                //TODO 跳转获取国家码页面
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
                break;
            case R.id.server_tiaokuan:
                //TODO 跳转阅读条款页面
                break;
            case R.id.bt_register_submit:
                //TODO 跳转获取验证码
                String PhoneMail = et_registermobile.getText().toString().trim();
                String countryCodes = regist_country_name.getText().toString().trim();
                boolean IsphoneNumber = StringHelper.isPhoneNumber(PhoneMail);
                boolean isEmail = StringHelper.isEmail(PhoneMail);
                Log.i("==gxl==", "IsphoneNumber:" + IsphoneNumber + "==isEmail:" + isEmail);
                String countrycodeStr = regist_country_name.getText().toString().trim();
                if (PhoneMail.equals("")) {
                    showToast("手机号码或邮箱不能为空");
                    return;
                }
                if(!IsphoneNumber && !isEmail){
                    showToast("输入格式不正确");
                    return;
                }
                if(IsphoneNumber && isEmail){
                    showToast("输入格式不正确");
                    return;
                }
                intentVerCode(isEmail,IsphoneNumber,countrycodeStr,PhoneMail);

                break;
            case R.id.tv_back:
                //TODO 返回
                RegisterActivity.this.finish();
                break;

        }
    }

    private void intentVerCode(boolean isEmail,boolean IsphoneNumber,String countrycodeStr,String PhoneMail) {
        Intent intent = new Intent(this, VerCodeActivity.class);
        Bundle bundle = new Bundle();
        if (isEmail) {
            bundle.putString("phoneEma", PhoneMail);
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
        if (IsphoneNumber) {
//            bundle.putString("phoneEma", PhoneMail);
            bundle.putString("phoneEma", countrycodeStr + "-" + PhoneMail);
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            iv_flag.setImageResource(country.flag);
            regist_country_code_text.setText(country.name);
            regist_country_name.setText("+" + country.code);
        }
    }
}