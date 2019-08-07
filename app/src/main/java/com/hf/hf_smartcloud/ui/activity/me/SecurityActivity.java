package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.UnbundQQWchatEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.weigets.MyDialog2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SecurityActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_sancode)
    ImageView btnSancode;
    @BindView(R.id.ll_zfpwd)
    LinearLayout llZfpwd;
    @BindView(R.id.ivqq)
    ImageView ivqq;
    @BindView(R.id.tvqq)
    TextView tvqq;
    @BindView(R.id.ivchat)
    ImageView ivchat;
    @BindView(R.id.tvchat)
    TextView tvchat;
    @BindView(R.id.qqnotLogin)
    LinearLayout qqnotLogin;
    @BindView(R.id.wechartnotLogin)
    LinearLayout wechartnotLogin;

    private String qq = "";
    private String wechat = "";
    private String token = "";
    private String sign = "";
    private Gson gson = new Gson();
    private UnbundQQWchatEntity unbundQQWchatEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        qq = getStringSharePreferences("qq", "qq");
        wechat = getStringSharePreferences("wechat", "wechat");
        token = getStringSharePreferences("token", "token");
        initTitle();
        initViews();
    }

    private void initViews() {
        if (!qq.equals("")) {
            ivqq.setImageResource(R.drawable.icon_bundqq_yes);
            tvqq.setText("已绑定");
        }
        if (!wechat.equals("")) {
            ivchat.setImageResource(R.drawable.iconbundwec_yes);
            tvchat.setText("已绑定");
        }

        qqnotLogin.setOnClickListener(this);
        wechartnotLogin.setOnClickListener(this);
        llZfpwd.setOnClickListener(this);

    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add = findviewByid(R.id.btn_add);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("安全中心");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddaddressActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_zfpwd:
                //TODO  设置支付密码
                openActivity(ZfPwdActivity.class);
                break;
            case R.id.qqnotLogin:
                //TODO  QQ解绑
                if (!qq.equals("")) {
                    initDialog("提示:是否解除QQ绑定？", "QQ");
                } else {
                    showToast("未绑定QQ");
                }

                break;
            case R.id.wechartnotLogin:
                //TODO  微信解绑
                if (!wechat.equals("")) {
                    initDialog("提示:是否解除QQ绑定？", "wechat");
                } else {
                    showToast("未绑定微信");
                }
                break;
        }
    }

    private void initDialog(String title, String flag) {
        MyDialog2 myDialog = new MyDialog2();
        myDialog.initDialog(this, title);
        myDialog.buttonClickEvent(new MyDialog2.DialogButtonClick() {
            @Override
            public void cilckComfirmButton(View view) {
                // TODO 点击确定按钮
                if (flag.equals("QQ")) {
                    //TODO 调用qq接触绑定接口
                    UnbundLogin(token, "Customer.Customer.Qq_unlogin",flag);
                } else if (flag.equals("wechart")) {
                    //TODO 调用微信接触绑定接口
                    UnbundLogin(token, "Customer.Customer.Wechat_unlogin",flag);
                }
            }

            @Override
            public void cilckCancleButton(View view) {
                //点击取消按钮
            }
        });
    }

    //========================解除QQ绑定=====================================================================================================================================
    private void UnbundLogin(String token, String urls,String flag) {
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", urls);
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", urls);
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("token", token);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=" + urls, map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-UnbundLogin", "result-UnbundLogin:" + result);
                        unbundQQWchatEntity = gson.fromJson(result, UnbundQQWchatEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (unbundQQWchatEntity.getRet() == 200) {
                                    showToast("解绑成功");
                                    if(flag.equals("QQ")){
                                        ivqq.setImageResource(R.drawable.icon_bundqq_not);
                                        tvqq.setText("未绑定");
                                    }else {
                                        ivchat.setImageResource(R.drawable.iconbundwec_not);
                                        tvchat.setText("未绑定");
                                    }

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
