package com.hf.hf_smartcloud.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.MessageEvent;
import com.hf.hf_smartcloud.entity.GetUnionIdEntity;
import com.hf.hf_smartcloud.entity.QqLoginEntity;
import com.hf.hf_smartcloud.entity.SendLoginEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.utils.StringHelper;
import com.hf.hf_smartcloud.weigets.CheckAppInstalledUtil;
import com.hf.hf_smartcloud.weigets.popwios.AlbumOrCameraPopupWindow;
import com.hf.hf_smartcloud.weigets.popwios.OnCameraOrAlbumSelectListener;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String APPID = "101714818";
    private TextInputLayout mMobileTil;
    private TextView register_tv;//注册
    private ImageView iv_qqlogin, iv_wechartlogin;
    private AlbumOrCameraPopupWindow ACWindow;

    //===========================
    private LinearLayout ll_login_countryCode;
    private TextView login_country_name, login_country_code_text;//国家码和国家名称
    private ImageView login_iv_flag;//国旗
    private TextInputEditText et_login_mobile;//用户名【手机号或者邮箱】
    private TextInputEditText et_login_password;//密码
    private CheckBox cb_remember_login;//记住密码
    private TextView tv_login_forget_pwd;//忘记密码按钮入口
    private Button next_btn;//下一步登陆按钮

    //=======QQ登录======
    private Tencent mTencent; //qq主操作对象
    private IUiListener loginListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope; //获取信息的范围参数
    private UserInfo userInfo; //qq用户信息
    //========================================
    private String sign = "";
    private Gson gson = new Gson();
    private SendLoginEntity sendLoginEntity;
    private String phoneMail, phoneMailTv, password;
    private String url, unionid, sex;
    private SendLoginEntity.DataBean.ListsBean lists;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_login);
        initView();
        initData();

    }

    private void initView() {
        ll_login_countryCode = findviewByid(R.id.ll_login_countryCode);
        ll_login_countryCode.setOnClickListener(this);
        login_country_name = findviewByid(R.id.login_country_name);//国家码
        login_country_code_text = findviewByid(R.id.login_country_code_text);//国家名
        login_iv_flag = findviewByid(R.id.login_iv_flag);//国旗
        et_login_mobile = findviewByid(R.id.et_login_mobile);//用户名
        et_login_password = findviewByid(R.id.et_login_password);//密码
        cb_remember_login = findviewByid(R.id.cb_remember_login);//记住密码
        tv_login_forget_pwd = findviewByid(R.id.tv_login_forget_pwd);//忘记密码
        tv_login_forget_pwd.setOnClickListener(this);
        next_btn = findviewByid(R.id.next_btn);
        next_btn.setOnClickListener(this);
        register_tv = findviewByid(R.id.register_tv);
        register_tv.setOnClickListener(this);
        mMobileTil = (TextInputLayout) findViewById(R.id.til_login_mobile);
        iv_qqlogin = findviewByid(R.id.iv_qqlogin);
        iv_qqlogin.setOnClickListener(this);
        iv_wechartlogin = findviewByid(R.id.iv_wechartlogin);
        iv_wechartlogin.setOnClickListener(this);
        String countrycodeStr = login_country_name.getText().toString().trim();
        if (countrycodeStr.equals("+86")) {
            login_iv_flag.setImageResource(R.drawable.flag_cn);
            login_country_code_text.setText("中国");
        }
    }

    /**
     * 选择企业登录或者个人登录
     */
    private OnCameraOrAlbumSelectListener onCameraOrAlbumSelectListener = new OnCameraOrAlbumSelectListener() {
        @Override
        public void OnSelectCamera() {
            showToast("企业登录--建设中......");
            openActivity(GuideSettingActivity.class);
        }

        @Override
        public void OnSelectAlbum() {
//            //调用登录接口
//            phoneMailTv = et_login_mobile.getText().toString().trim();
//            password = et_login_password.getText().toString().trim();
//            boolean IsphoneNumber = StringHelper.isPhoneNumber(phoneMailTv);
//            boolean isEmail = StringHelper.isEmail(phoneMailTv);
//            String countryCode = login_country_name.getText().toString().trim();
//            if (phoneMailTv.equals("")) {
//                showToast("用户名不能为空");
//                return;
//            }
//            if (password.equals("")) {
//                showToast("密码不能为空");
//                return;
//            }
//            if (IsphoneNumber) {
//                phoneMail = countryCode + "-" + phoneMailTv;
//            } else {
//                phoneMail = phoneMailTv;
//            }
//            showLoadingDialog("正在登录");
//            sendLogin(phoneMail, password);
        }
    };


    public boolean verifyMobile() {
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(((EditText) findViewById(R.id.et_login_mobile)).getText().toString());
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                if (verifyMobile()) {
                    mMobileTil.setErrorEnabled(false);
                } else {
                    mMobileTil.setErrorEnabled(true);
                    mMobileTil.setError("手机号格式错误");
                }

                //调用登录接口
                phoneMailTv = et_login_mobile.getText().toString().trim();
                password = et_login_password.getText().toString().trim();
                boolean IsphoneNumber = StringHelper.isPhoneNumber(phoneMailTv);
                boolean isEmail = StringHelper.isEmail(phoneMailTv);
                String countryCode = login_country_name.getText().toString().trim();
                if (phoneMailTv.equals("")) {
                    showToast("用户名不能为空");
                    return;
                }
                if (password.equals("")) {
                    showToast("密码不能为空");
                    return;
                }
                if (IsphoneNumber) {
                    phoneMail = countryCode + "-" + phoneMailTv;
                } else {
                    phoneMail = phoneMailTv;
                }
                showLoadingDialog("正在登录");
                        sendLogin(phoneMail, password);
                break;
            case R.id.register_tv:
                openActivity(RegisterActivity.class);
                break;
            case R.id.iv_qqlogin:
                //TODO QQ登录
                login();
                break;
            case R.id.iv_wechartlogin:
                //TODO 微信登录
                boolean wechatinstall = CheckAppInstalledUtil.isInstalled(this, "com.tencent.mm");
                if (wechatinstall == false) {
                    Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
                    return;
                }
                EventBus.getDefault().register(this);
                showLoadingDialog("正在登录");
                loginToWeiXin();
                break;
            case R.id.ll_login_countryCode:
                //TODO 国家码
                startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
                break;
            case R.id.tv_login_forget_pwd:
                //TODO 忘记密码
                openActivity(ForgetPwdActivity.class);
                break;
        }
    }

    /**
     * 登录接口
     */
    private void sendLogin(String userName, String passWd) {
        if (!isConnNet(LoginActivity.this)) {
            showToast("请检查网络");
            dismissLoadingDialog();
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
                    dismissLoadingDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    dismissLoadingDialog();
                    //创建一个线程
                    try {
                        String result = response.body().string();
                        /**
                         * 测试数据
                         */
//                        String result = "{\"ret\":200,\"data\":{\"status\":1,\"msg\":\"fail\",\"error\":{\"fail\":\"fail\"},\"lists\":{}},\"msg\":\"\",\"debug\":{\"stack\":[\"[#0 - 0ms - PHALAPI_INIT]D:\\\\program\\\\wamp64\\\\www\\\\www\\\\openly\\\\api\\\\index.php(6)\",\"[#1 - 1ms - PHALAPI_RESPONSE]D:\\\\program\\\\wamp64\\\\www\\\\www\\\\openly\\\\api\\\\phalapi\\\\vendor\\\\phalapi\\\\kernal\\\\src\\\\PhalApi.php(46)\",\"[#2 - 25ms - PHALAPI_FINISH]D:\\\\program\\\\wamp64\\\\www\\\\www\\\\openly\\\\api\\\\phalapi\\\\vendor\\\\phalapi\\\\kernal\\\\src\\\\PhalApi.php(74)\"],\"sqls\":[],\"version\":\"2.6.1\"}}";
                        Log.i("result-sendLogin", "result-sendLogin:" + result);
                        sendLoginEntity = gson.fromJson(result, SendLoginEntity.class);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (sendLoginEntity.getRet() == 200 && sendLoginEntity.getData().getStatus() == 0) {
                                    lists = sendLoginEntity.getData().getLists();
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
                                        openActivity(MainActivity.class);
                                        finish();
                                    } else {
                                        Toast.makeText(getBaseContext(),sendLoginEntity.getData().getError().getFail(),Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(),sendLoginEntity.getData().getError().getFail(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("e","e:"+e);
                        showToast(e.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * QQ登录
     */
    private void login() {
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            //开始qq授权登录
            mTencent.login(LoginActivity.this, scope, loginListener);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Log.i("requestCode:::", "requestCode::" + requestCode + "resultCode:::" + resultCode + "data:::" + data.toString());
        //QQ登录
        if (requestCode == Constants.REQUEST_API) {
            Tencent.handleResultData(data, loginListener);
            System.out.println("开始获取用户信息");
            userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            userInfo.getUserInfo(userInfoListener);
            String accessToken = mTencent.getAccessToken();
            Log.i("==qqLogin=json=api", "json=" + accessToken);
//            if(!accessToken.equals("")){
//                url = "https://graph.qq.com/oauth2.0/me?access_token="+accessToken+"&unionid=1";
//                unionid = getUnionID(url);
//            }
        }

        if (requestCode == Constants.REQUEST_LOGIN) {
//            if (resultCode == Constants.RESULT_LOGIN) {
            Tencent.handleResultData(data, loginListener);
            System.out.println("开始获取用户信息");
            userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            String accessToken = mTencent.getAccessToken();
            Log.i("==qqLogin=json=LOGIN", "json=" + accessToken);
            userInfo.getUserInfo(userInfoListener);
        }
        //国家码回调
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            login_iv_flag.setImageResource(country.flag);
            login_country_code_text.setText(country.name);
            login_country_name.setText("+" + country.code);
        }
    }

    private void initData() {
        //初始化qq主操作对象
        mTencent = Tencent.createInstance(APPID, LoginActivity.this);
        //要所有权限，不然会再次申请增量权限，这里不要设置成get_user_info,add_t
        scope = "all";
        loginListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onComplete(Object value) {
                // TODO Auto-generated method stub
                System.out.println("有数据返回..");
                if (value == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) value;
                    int ret = jo.getInt("ret");
                    Log.i("==qqLogin=json=", "json=" + String.valueOf(jo));
                    if (ret == 0) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.i("==error==", "==error==" + e.toString());
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    Log.i("==qqLogin=json=", "json=" + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String pic = jo.getString("figureurl_qq");
                    String accessToken = mTencent.getAccessToken();
                    Log.i("==qqLogin=json=", "json=" + accessToken);
                    Toast.makeText(LoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                    if (!accessToken.equals("")) {
                        url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken + "&unionid=1";

                        getUnionID(url, nickName, gender, pic);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
            }
        };
    }

    /**
     * 微信登录
     */
    private void loginToWeiXin() {
        IWXAPI mApi = WXAPIFactory.createWXAPI(this, com.hf.hf_smartcloud.constants.Constants.APP_ID, true);
        mApi.registerApp(com.hf.hf_smartcloud.constants.Constants.APP_ID);

        if (mApi != null && mApi.isWXAppInstalled()) {
            dismissLoadingDialog();
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);
        } else {
//                Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEventBusMsg(MessageEvent event) {
        String nickname = event.getNickname();
        String sex = String.valueOf(event.getSex());
        String pic = event.getHeadimgurl();
        String wehartunionid = event.getUnionid();
        if (!nickname.equals("") && !sex.equals("") && !pic.equals("") && !wehartunionid.equals("")) {
            showLoadingDialog("正在登录");
            SendWechartLogin(nickname, sex, pic, wehartunionid);
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mTencent != null) {
            //注销登录
            mTencent.logout(LoginActivity.this);
        }
        super.onDestroy();
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //======================QQ登录获取unionid===================================================

    /**
     * 登录接口
     */
    private void getUnionID(String url, String nickName, String gender, String pic) {
        try {
            HttpUtils.doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("==sendLogin错误", "==sendLogin错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-getUnionID", "result-getUnionID:" + result);
                        String str = result;
                        String str1 = str.substring(str.indexOf("{"));
                        String str2 = str1.substring(0, str1.indexOf(");"));
                        Log.i("result-str2", "result-str2:" + str2);
                        GetUnionIdEntity getUnionIdEntity = gson.fromJson(str2, GetUnionIdEntity.class);

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                unionid = getUnionIdEntity.getUnionid();
                                Log.i("unionid==", "unionid===" + unionid);
                                if (gender.equals("男")) {
                                    sex = "1";
                                } else if (gender.equals("女")) {
                                    sex = "2";
                                } else {
                                    sex = "0";
                                }
                                if (!unionid.equals("")) {
                                    showLoadingDialog("正在登录");
                                    SendQqLogin(nickName, sex, pic, unionid);
                                } else {
                                    showToast("用户信息获取失败");
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
    // //======================调用服务器QQ登录===================================================

    private void SendQqLogin(String nickname, String sex, String pic, String unionid) {
        dismissLoadingDialog();
        if (!isConnNet(LoginActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Qq_login");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("nickname", nickname);
        sendCodeSign.put("sex", sex);
        sendCodeSign.put("pic", pic);
        sendCodeSign.put("unionid", unionid);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Customer.Qq_login");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("nickname", nickname);
            map.put("sex", sex);
            map.put("pic", pic);
            map.put("unionid", unionid);

            HttpUtils.doPost(com.hf.hf_smartcloud.constants.Constants.SERVER_BASE_URL + "service=Customer.Customer.Qq_login", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-SendQqLogin", "result-SendQqLogin:" + result);
                        QqLoginEntity qqLoginEntity = gson.fromJson(result, QqLoginEntity.class);
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qqLoginEntity.getRet() == 200 && qqLoginEntity.getData().getStatus()==0) {
                                    QqLoginEntity.DataBean.ListsBean lists = qqLoginEntity.getData().getLists();
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
                                        openActivity(MainActivity.class);
                                        finish();
                                    } else {
                                        showToast(qqLoginEntity.getData().getMsg() + "");
                                    }

                                } else {
                                    showToast(qqLoginEntity.getData().getError().getFail() + "");
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

    // //======================调用服务器微信登录===================================================
    private void SendWechartLogin(String nickname, String sex, String pic, String unionid) {
        dismissLoadingDialog();
        if (!isConnNet(LoginActivity.this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Customer.Wechat_login");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("nickname", nickname);
        sendCodeSign.put("sex", sex);
        sendCodeSign.put("pic", pic);
        sendCodeSign.put("unionid", unionid);
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Customer.Wechat_login");
            map.put("sign", sign);
            map.put("language", "zh_cn");
            map.put("nickname", nickname);
            map.put("sex", sex);
            map.put("pic", pic);
            map.put("unionid", unionid);

            HttpUtils.doPost(com.hf.hf_smartcloud.constants.Constants.SERVER_BASE_URL + "service=Customer.Customer.Wechat_login", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-SendWechartLogin", "result-SendWechartLogin:" + result);
                        QqLoginEntity qqLoginEntity = gson.fromJson(result, QqLoginEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (qqLoginEntity.getRet() == 200 && qqLoginEntity.getData().getStatus()==0) {
                                    QqLoginEntity.DataBean.ListsBean lists = qqLoginEntity.getData().getLists();
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
                                        openActivity(MainActivity.class);
                                        finish();
                                    } else {
                                        showToast(qqLoginEntity.getData().getMsg() + "");
                                    }

                                } else {
                                    showToast(qqLoginEntity.getData().getError().getFail() + "");
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