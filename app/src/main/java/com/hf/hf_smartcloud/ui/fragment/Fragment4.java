package com.hf.hf_smartcloud.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseFragment;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.ExitLoginEntity;
import com.hf.hf_smartcloud.entity.QueryInfoEntity;
import com.hf.hf_smartcloud.http.AllowX509TrustManager;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.ui.activity.LoginActivity;
import com.hf.hf_smartcloud.ui.activity.me.AccountChargesActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddressListActivity;
import com.hf.hf_smartcloud.ui.activity.me.BillActivity;
import com.hf.hf_smartcloud.ui.activity.me.HelpActivity;
import com.hf.hf_smartcloud.ui.activity.me.InvoiceActivity;
import com.hf.hf_smartcloud.ui.activity.me.MessageActivity;
import com.hf.hf_smartcloud.ui.activity.me.PayActivity;
import com.hf.hf_smartcloud.ui.activity.me.PersonalInformationActivity;
import com.hf.hf_smartcloud.ui.activity.me.QualificationActivity;
import com.hf.hf_smartcloud.ui.activity.me.SecurityActivity;
import com.hf.hf_smartcloud.ui.activity.me.SettingActivity;
import com.hf.hf_smartcloud.ui.activity.me.SignInActivity;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment4 extends BaseFragment implements View.OnClickListener {
    private View view;
    private LinearLayout ll_my, qiandao, addressList, ll_msg, ll_anquan, ll_help, ll_zpzz, ll_setting, ll_exitLogin;
    private ImageView eye_total;
    private TextView tv_total;
    private TextView tv_zdgl;
    private TextView tv_zzgm;
    private TextView tv_zhcz;
    private TextView tv_fpgl;
    private CircleImageView headPhoto;//头像
    private TextView tvnickName;//昵称
    private String sign = "";
    private String toKen = "";
    private String exterNal = "";
    private Gson gson = new Gson();
    private ExitLoginEntity exitLoginEntity;

    private String token = "";
    private QueryInfoEntity queryInfoEntity;
    private QueryInfoEntity.DataBean.ListsBean list;

    private String url = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

    }

    @Override
    public void onResume() {
        super.onResume();
        QueryInfo();
    }

    private void initViews() {
        ll_zpzz = view.findViewById(R.id.ll_zpzz);
        ll_my = view.findViewById(R.id.ll_my);
        ll_msg = view.findViewById(R.id.ll_msg);
        ll_anquan = view.findViewById(R.id.ll_anquan);
        ll_help = view.findViewById(R.id.ll_help);
        ll_setting = view.findViewById(R.id.ll_setting);
        qiandao = view.findViewById(R.id.qiandao);
        ll_exitLogin = view.findViewById(R.id.ll_exitLogin);
        addressList = view.findViewById(R.id.addressList);
        eye_total = view.findViewById(R.id.eye_total);
        tv_total = view.findViewById(R.id.tv_total);

        tv_zdgl = view.findViewById(R.id.tv_zdgl);
        tv_zzgm = view.findViewById(R.id.tv_zzgm);
        tv_zhcz = view.findViewById(R.id.tv_zhcz);
        tv_fpgl = view.findViewById(R.id.tv_fpgl);
        headPhoto = view.findViewById(R.id.headPhoto);
//                Glide.with(getActivity())
//                        .load(url)
//                        .placeholder(R.mipmap.icon_heard)
//                        .priority(Priority.LOW)
//                        .error(R.mipmap.icon_heard)
//                        .into(headPhoto);
                Glide.with(getActivity()).load(url).into(headPhoto);
//            }
        tvnickName = view.findViewById(R.id.tvnickName);
        tvnickName.setText("暂无");
        ll_setting.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_anquan.setOnClickListener(this);
        ll_msg.setOnClickListener(this);
        ll_my.setOnClickListener(this);
        qiandao.setOnClickListener(this);
        addressList.setOnClickListener(this);
        eye_total.setOnClickListener(this);
        tv_total.setOnClickListener(this);
        ll_zpzz.setOnClickListener(this);
        tv_zdgl.setOnClickListener(this);
        tv_zzgm.setOnClickListener(this);
        tv_zhcz.setOnClickListener(this);
        tv_fpgl.setOnClickListener(this);
        ll_exitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my:
                //TODO 点击头像跳转
                if (queryInfoEntity != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("pic", queryInfoEntity.getData().getLists().getPic());
                    bundle.putString("account", queryInfoEntity.getData().getLists().getAccount());
                    bundle.putString("nickname", queryInfoEntity.getData().getLists().getNickname());
                    bundle.putString("sex", queryInfoEntity.getData().getLists().getSex());
                    bundle.putString("birthday", queryInfoEntity.getData().getLists().getBirthday());
                    bundle.putString("industry", queryInfoEntity.getData().getLists().getIndustry());
                    bundle.putString("company", queryInfoEntity.getData().getLists().getCompany());
                    openActivity(PersonalInformationActivity.class, bundle);
                }

                break;
            case R.id.qiandao:
                //TODO 签到
                openActivity(SignInActivity.class);
                break;
            case R.id.addressList:
                //TODO 添加地址
                openActivity(AddressListActivity.class);
                break;
            case R.id.eye_total:
                //TODO 打开金额显示眼睛
//                openActivity(AddressListActivity.class);
                eye_total.setVisibility(View.INVISIBLE);
                tv_total.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_total:
                //TODO 关闭眼睛显示金额
//                openActivity(AddressListActivity.class);
                eye_total.setVisibility(View.VISIBLE);
                tv_total.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_zdgl:
                //TODO 账单管理
                openActivity(BillActivity.class);
                break;
            case R.id.tv_zzgm:
                //TODO 增值购买
                openActivity(PayActivity.class);
                break;
            case R.id.tv_zhcz:
                //TODO 账户充值
                openActivity(AccountChargesActivity.class);
                break;
            case R.id.tv_fpgl:
                //TODO 发票管理
                openActivity(InvoiceActivity.class);
                break;
            case R.id.ll_msg:
                //TODO 消息中心
                openActivity(MessageActivity.class);
                break;
            case R.id.ll_anquan:
                //TODO 安全中心
                openActivity(SecurityActivity.class);
                break;
            case R.id.ll_help:
                //TODO 使用帮助
                openActivity(HelpActivity.class);
                break;
            case R.id.ll_zpzz:
                //TODO 赠票资质
                openActivity(QualificationActivity.class);
                break;
            case R.id.ll_setting:
                //TODO 设置
                openActivity(SettingActivity.class);
                break;
            case R.id.ll_exitLogin:
                //TODO 退出登录
                showNormalDialog();
                break;
        }
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.mipmap.icon_heard);
        normalDialog.setTitle("退出登录");
        normalDialog.setMessage("你确定要退出本次登录?");
        normalDialog.setPositiveButton("狠心离开",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toKen = getStringSharePreferences("token", "token");
                        if (!toKen.equals("")) {
                            exitLogin(toKen);
                        }
                    }
                });
        normalDialog.setNegativeButton("再想想",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    //===============================================

    /**
     * 退出登录接口
     */
    private void exitLogin(String token) {
        if (!isConnNet(getActivity())) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Logout");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", token);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("token", token);
            AllowX509TrustManager.allowAllSSL();
            HttpUtils.doPost(com.hf.hf_smartcloud.constants.Constants.SERVER_BASE_URL + "service=Customer.Customer.Logout", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("==exitLogin错误", "==exitLogin错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-exitLogin", "result-exitLogin:" + result);

                        exitLoginEntity = gson.fromJson(result, ExitLoginEntity.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (exitLoginEntity.getRet() == 200) {
                                    showToast(exitLoginEntity.getMsg());
                                    exterNal = getStringSharePreferences("external", "external");
                                    setStringSharedPreferences("customer_id", "customer_id", "");
                                    setStringSharedPreferences("customer_group_id", "customer_group_id", "");
                                    setStringSharedPreferences("customer_pay_level_id", "customer_pay_level_id", "");
                                    setStringSharedPreferences("customer_vitality_id", "customer_vitality_id", "");
                                    setStringSharedPreferences("customer_permission_ids", "customer_permission_ids", "");
                                    setStringSharedPreferences("customer_address_id", "customer_address_id", "");
                                    setStringSharedPreferences("account", "account", "");
                                    setStringSharedPreferences("nickname", "nickname", "");
                                    setStringSharedPreferences("sex", "sex", "");
                                    setStringSharedPreferences("birthday", "birthday", "");
                                    setStringSharedPreferences("pic", "pic", "");
                                    setStringSharedPreferences("industry", "industry", "");
                                    setStringSharedPreferences("company", "company", "");
                                    setStringSharedPreferences("qq", "qq", "");
                                    setStringSharedPreferences("wechat", "wechat", "");
                                    setStringSharedPreferences("trust", "trust", "");
                                    setStringSharedPreferences("register_time", "register_time", "");
                                    setStringSharedPreferences("real_identity", "real_identity", "");
                                    setStringSharedPreferences("status", "status", "");
                                    setStringSharedPreferences("remark", "remark", "");
                                    setStringSharedPreferences("token", "token", "");
                                    setBooleanSharedPreferences("isLogin", "isLogin", false);
                                    setStringSharedPreferences("external", "external", "");
                                    setStringSharedPreferences("city", "city", "");
                                    openActivity(LoginActivity.class);
                                    getActivity().finish();

                                } else {
                                    showToast(exitLoginEntity.getData().getMsg() + "");
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


    private void QueryInfo() {
        if (!isConnNet(getActivity())) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Info");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Customer.Info");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("sign", sign);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Customer.Info", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-QueryInfo", "result-QueryInfo:" + result);
                        queryInfoEntity = gson.fromJson(result, QueryInfoEntity.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (queryInfoEntity.getRet() == 200) {
                                    //TODO 填充数据
//                                    if(queryInfoEntity.getData().getMsg()=="success"){
                                    url = queryInfoEntity.getData().getLists().getPic();
                                    String name = queryInfoEntity.getData().getLists().getNickname();
                                    list = queryInfoEntity.getData().getLists();
                                    if (!url.equals("")) {
                                            Glide.with(getActivity()).load(url).centerCrop().into(headPhoto);
//                                                Glide.with(getActivity())
//                                                        .load(url)
//                                                        .placeholder(R.mipmap.icon_heard)
//                                                        .priority(Priority.LOW)
//                                                        .error(R.mipmap.icon_heard)
//                                                        .into(headPhoto);

                                    }
                                    if (!name.equals("")) {
                                        tvnickName.setText(queryInfoEntity.getData().getLists().getNickname());
                                    }
//                                    }else if(queryInfoEntity.getData().getMsg()=="fail"){
//                                    }
                                } else {
                                    showToast(queryInfoEntity.getMsg());
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