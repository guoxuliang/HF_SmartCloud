package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.ReLoginpwdEntity;
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

public class ReLoginpwdActivity extends BaseActivity {
    @BindView(R.id.oldpwd)
    EditText oldpwd;
    @BindView(R.id.newpwd)
    EditText newpwd;
    @BindView(R.id.renewpwd)
    EditText renewpwd;
    @BindView(R.id.bt_reloginpwd_submit)
    Button btReloginpwdSubmit;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_sancode)
    ImageView btnSancode;

    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    private ReLoginpwdEntity reLoginpwdEntity;
    private String tvoldpwd,tvnewpwd,tvrenewpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloginpwd);
        ButterKnife.bind(this);
        initViews();
        initTitle();
    }

    private void initTitle() {
        tvTitle.setText("修改密码");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        btReloginpwdSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvoldpwd = oldpwd.getText().toString().trim();
                tvnewpwd = newpwd.getText().toString().trim();
                tvrenewpwd = renewpwd.getText().toString().trim();
                if(!tvnewpwd.equals(tvrenewpwd)){
                    showToast("确认密码输入不一致");
                    return;
                }
                reLoginpwd(tvoldpwd,tvnewpwd);
            }
        });
    }
    /**
     * 修改密码
     */
  private void reLoginpwd(String tvoldpwd,String tvnewpwd){
          if (!isConnNet(this)) {
              showToast("请检查网络");
              return;
          }
          HashMap<String, String> sendCodeSign = new HashMap<>();
          sendCodeSign.put("service", "Customer.Safe.Edit_password");
          sendCodeSign.put("language", "zh_cn");
          sendCodeSign.put("token", getStringSharePreferences("token", "token"));
      sendCodeSign.put("old_password", tvoldpwd);
      sendCodeSign.put("set_password", tvnewpwd);


          sign = SignUtil.Sign(sendCodeSign);
          try {
              Map<String, String> map = new HashMap<>();
              map.put("service", "Customer.Safe.Edit_password");
              map.put("language", "zh_cn");
              map.put("token", getStringSharePreferences("token", "token"));
              map.put("old_password", tvoldpwd);
              map.put("set_password", tvnewpwd);
              map.put("sign", sign);

              HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.Edit_password", map, new Callback() {
                  @Override
                  public void onFailure(Call call, IOException e) {
                      Log.i("错误", "错误：" + e);
                  }

                  @Override
                  public void onResponse(Call call, Response response) throws IOException {
                      try {
                          String result = response.body().string();
                          Log.i("result-LoginDev", "result-LoginDev:" + result);
                          reLoginpwdEntity = gson.fromJson(result, ReLoginpwdEntity.class);
                          ReLoginpwdActivity.this.runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (reLoginpwdEntity.getRet() == 200) {
                                      showToast(reLoginpwdEntity.getData().getMsg());
                                      finish();
                                  } else {
                                      showToast(reLoginpwdEntity.getMsg());
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
