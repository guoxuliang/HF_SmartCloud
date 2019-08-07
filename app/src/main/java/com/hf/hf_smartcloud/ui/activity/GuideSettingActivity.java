package com.hf.hf_smartcloud.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.PerInfoEntity;
import com.hf.hf_smartcloud.entity.SelectTradeEntity;
import com.hf.hf_smartcloud.entity.SendLoginEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.views.DateUtils;
import com.hf.hf_smartcloud.views.SexBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GuideSettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.dp_datetimepicker_date)
    DatePicker dpDatetimepickerDate;
    @BindView(R.id.tvpass)
    TextView tvpass;
    @BindView(R.id.sex)
    SexBox sex;
    @BindView(R.id.lltrade)
    LinearLayout lltrade;
    @BindView(R.id.hyValue)
    TextView hyValue;
    private Button subon;
    private String date = "";
    private SimpleDateFormat format;
    private Calendar calendar;
    private String t;
    private String sexflag;
    private String timec;//时间戳

    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();

    private PerInfoEntity perInfoEntity;
    private SelectTradeEntity selectTradeEntity;
    private SendLoginEntity sendLoginEntity;
    private AlertDialog alertDialog2; //单选框
    private String[] arr;
    private String industry;
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidesetting);
        ButterKnife.bind(this);
        token = getStringSharePreferences("token", "token");
        userName = getStringSharePreferences("lszh", "lszh");
        password = getStringSharePreferences("lsmm", "lsmm");
        initViews();

    }

    private void initViews() {
        subon = findviewByid(R.id.subon);
        lltrade.setOnClickListener(this);
        subon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timec = DateUtils.getTimestamp(date, "yyyy-MM-dd");
//                Log.i("==日期选择:::", "onDateChanged: " + format.format(calendar.getTime()) + "==" + calendar.getTime() + "timec:::" + timec + "==sj:::");
                switch (sex.getstatu()) {
                    case 0:
                        t = "未选择";
                        break;
                    case 1:
                        t = "男";
                        sexflag = "1";
                        break;
                    case 2:
                        t = "女";
                        sexflag = "2";
                        break;
                    default:
                        t = "错误";
                        sexflag = "0";
                        break;
                }
               String tokenFlag = getStringSharePreferences("token","token");
                if(tokenFlag.equals("")){
                    if (!userName.equals("") && !password.equals("")) {
                        sendLogin(userName, password);
                    }
                }else {
                    if (!sexflag.equals("") || !timec.equals("")) {
                        PerInfo(sexflag, timec, industry,tokenFlag);
                    }
                }



            }


        });
        tvpass.setOnClickListener(this);
        dpDatetimepickerDate.init(2013, 8, 20, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 获取一个日历对象，并初始化为当前选中的时间
                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                format = new SimpleDateFormat("yyyy-MM-dd");
//                Toast.makeText(GuideSettingActivity.this, format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                date = format.format(calendar.getTime());
            }
        });


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvpass:
                openActivity(LoginActivity.class);
                break;
            case R.id.lltrade:
                SelectTrade(v);
                break;
        }
    }

    public void showSingleAlertDialog(View view, String[] arr) {
//        final String[] items = {"单选1", "单选2", "单选3", "单选4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("请选择行业");
        alertBuilder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(GuideSettingActivity.this, arr[i], Toast.LENGTH_SHORT).show();
                industry = arr[i];
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!industry.equals("")) {
                    hyValue.setText(industry);
                }
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    //==============================完善性别和生日个人信息====================================================================================
    private void PerInfo(String sexflag, String timec, String industry,String token) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Edit");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("nickname", "");
        sendCodeSign.put("sex", sexflag);
        sendCodeSign.put("birthday", timec);
        sendCodeSign.put("pic", "");
        sendCodeSign.put("industry", industry);
        sendCodeSign.put("company", "");
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Customer.Edit");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("nickname", "");
            map.put("sex", sexflag);
            map.put("birthday", timec);
            map.put("pic", "");
            map.put("industry", industry);
            map.put("company", "");
            map.put("token", token);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Customer.Edit", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-PerInfo", "result-PerInfo:" + result);
                        perInfoEntity = gson.fromJson(result, PerInfoEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (perInfoEntity.getRet() == 200) {
                                    showToast(perInfoEntity.getMsg());
                                    openActivity(MainActivity.class);
                                    finish();
                                    //TODO 此处调用查询接口
                                } else {
                                    showToast(perInfoEntity.getMsg());
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

    //==============================行业选择====================================================================================
    private void SelectTrade(View v) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Sundry.Industry");
        sendCodeSign.put("language", "zh_cn");
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Sundry.Industry");
            map.put("language", "zh_cn");
            map.put("sign", sign);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Publicity.Sundry.Industry", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-SelectTrade", "result-SelectTrade:" + result);
                        selectTradeEntity = gson.fromJson(result, SelectTradeEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (selectTradeEntity.getRet() == 200) {
                                    showToast(selectTradeEntity.getMsg());
                                    List<String> listsData = selectTradeEntity.getData().getLists();
                                    final int size = listsData.size();
                                    arr = (String[]) listsData.toArray(new String[size]);
                                    if (arr != null) {
                                        showSingleAlertDialog(v, arr);
                                    }
                                } else {
                                    showToast(selectTradeEntity.getMsg());
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
//==============================登录接口====================================================================================

    /**
     * 登录接口
     */
    private void sendLogin(String userName, String passWd) {
        dismissLoadingDialog();
        if (!isConnNet(GuideSettingActivity.this)) {
            showToast("请检查网络");
            return;
        }

        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Login");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("account", userName);
        sendCodeSign.put("password", passWd);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("account", userName);
            map.put("password", passWd);

            HttpUtils.doPost(com.hf.hf_smartcloud.constants.Constants.SERVER_BASE_URL + "service=Customer.Customer.Login", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("==sendLogin错误", "==sendLogin错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-sendLogin", "result-sendLogin:" + result);
                        sendLoginEntity = gson.fromJson(result, SendLoginEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (sendLoginEntity.getRet() == 200) {
                                    SendLoginEntity.DataBean.ListsBean lists = sendLoginEntity.getData().getLists();
                                    if (lists != null) {
                                        setStringSharedPreferences("customer_id", "customer_id", lists.getCustomer_id());
                                        setStringSharedPreferences("customer_group_id", "customer_group_id", lists.getCustomer_group_id());
                                        setStringSharedPreferences("customer_pay_level_id", "customer_pay_level_id", lists.getCustomer_pay_level_id());
                                        setStringSharedPreferences("customer_vitality_id", "customer_vitality_id", lists.getCustomer_vitality_id());
                                        setStringSharedPreferences("customer_permission_ids", "customer_permission_ids", lists.getCustomer_permission_ids());
                                        setStringSharedPreferences("customer_address_id", "customer_address_id", lists.getCustomer_address_id());
                                        setStringSharedPreferences("account", "account", lists.getAccount());
                                        setStringSharedPreferences("nickname", "nickname", lists.getNickname());
                                        setStringSharedPreferences("sex", "sex", lists.getSex());
                                        setStringSharedPreferences("birthday", "birthday", lists.getBirthday());
                                        setStringSharedPreferences("pic", "pic", String.valueOf(lists.getPic()));
                                        setStringSharedPreferences("industry", "industry", lists.getIndustry());
                                        setStringSharedPreferences("company", "company", lists.getCompany());
                                        setStringSharedPreferences("qq", "qq", lists.getQq());
                                        setStringSharedPreferences("wechat", "wechat", lists.getWechat());
                                        setStringSharedPreferences("trust", "trust", lists.getTrust());
                                        setStringSharedPreferences("register_time", "register_time", lists.getRegister_time());
                                        setStringSharedPreferences("real_identity", "real_identity", lists.getReal_identity());
                                        setStringSharedPreferences("status", "status", lists.getStatus());
                                        setStringSharedPreferences("remark", "remark", lists.getRemark());
                                        setStringSharedPreferences("pay_password", "pay_password", String.valueOf(lists.getPay_password()));
                                        setStringSharedPreferences("id_card", "id_card", String.valueOf(lists.getId_card()));
                                        setStringSharedPreferences("token", "token", lists.getToken());
                                        setStringSharedPreferences("expires_time", "expires_time", lists.getExpires_time());
                                        setStringSharedPreferences("type", "type", lists.getType());
                                        setBooleanSharedPreferences("isLogin", "isLogin", true);
                                        if (!sexflag.equals("") || !timec.equals("")) {
                                            PerInfo(sexflag, timec, industry,lists.getToken());
                                        }
                                    } else {
                                        showToast(sendLoginEntity.getData().getMsg() + "");
                                    }

                                } else {
                                    showToast(sendLoginEntity.getData().getMsg() + "");
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
