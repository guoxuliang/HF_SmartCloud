package com.hf.hf_smartcloud.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.log.LogManager;
import com.hf.hf_smartcloud.log.LogProxy;
import com.hf.hf_smartcloud.receiver.ActivityCollector;
import com.hf.hf_smartcloud.utils.DimenUtils;
import com.hf.hf_smartcloud.utils.StringHelper;
import com.hf.hf_smartcloud.weigets.MyDialog;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

//import static com.gyf.barlibrary.ImmersionBar.getStatusBarHeight;


/**
 * @类名称: BaseActivity
 * @类描述: TODO(Activity的基类，这里进行一些公有的方法，对一些相同的操作进行统一处理)
 * @作者  guoxuliang
 * @日期 2019-7-6 下午1:27:28
 */
/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 *
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Dialog loginDialog;
    private Dialog updateDialog;
    // private SwipeBackLayout mSwipeBackLayout;
    private static final LogProxy log_baseActivity = LogManager.getLog("BaseActivity");

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * @Title: getVersionCode @Description: TODO(获取版本号 如：code
     *         =10) @param @return int 1 异常时返回 0 @throws
     */
//    protected int getVersionCode() {
//        try {
//            return this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
//        } catch (NameNotFoundException e) {
//            log_baseActivity.warn(e.getMessage(), e);
//            return 0;
//        }
//    }

    public String changeCard(String card) {
        String headCard = "";
        if (!StringHelper.isNullOrEmpty(card)) {
            int cardLength = card.length();
            if (cardLength > 6) {
                headCard = card.substring(0, cardLength - 6) + "******";
            }
        }

        return headCard;
    }

    /**
     * @Title: initOptions @Description:
     *         TODO(初始化DisplayImageOptions) @param @param imgsId @return
     *         DisplayImageOptions 返回类型 @throws
     */
//    public DisplayImageOptions initOptions(int imgsId) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(imgsId).showImageForEmptyUri(imgsId).showImageOnFail(imgsId)
//            .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
//        return options;
//    }

//    public DisplayImageOptions initEmepyOptions(int imgsId) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnFail(imgsId).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
//            .bitmapConfig(Bitmap.Config.RGB_565).build();
//        return options;
//    }

    /**
     * @Title: newSetViewWidthAndHeight @Description: TODO(动态设置View的宽高) @param
     *         context 设定文件 @param offset @param colum 列数 @return LayoutParams
     *         返回类型 @throws
     */
    public LayoutParams newSetViewWidthAndHeight(Context context, int offset, int colum) {
        int width = DimenUtils.getWindowsWidth(context);
        int c = DimenUtils.dip2px(context, offset);
        int realWidth = (width - c) / colum;
        LayoutParams mLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.width = realWidth;
        mLayoutParams.height = realWidth;
        return mLayoutParams;
    }

    /**
     * @Title: changePwdType
     * @Description: TODO(将保存的账户转换为3-4-4形式显示)
     * @param @param
     *            pwd
     * @param @return
     *            设定文件
     * @return String 返回类型
     * @throws 2016-05-12
     */
    protected String changePwdType(String pwd) {// 18966802006
        String password = "";
        if (!StringHelper.isNullOrEmpty(pwd) && pwd.length() == 11) {
            password = pwd.substring(0, 3) + " " + pwd.substring(3, 7) + " " + pwd.substring(7, 11);
        } else {
            password = "";
        }
        return password;
    }

    /**
     * @Title: changeShowPwdToSavePwd
     * @Description: TODO(将3-4-4形式的账户，去空格显示)
     * @param @param
     *            pwd
     * @param @return
     *            设定文件
     * @return String 返回类型
     * @throws 2016-05-12
     */
    protected String changeShowPwdToSavePwd(String pwd) {
        String password = "";
        if (!StringHelper.isNullOrEmpty(pwd) && pwd.length() == 13) {
            String[] array = pwd.split(" ");
            password = array[0] + array[1] + array[2];
        } else {
            password = "";
        }
        return password;
    }

    protected String removeNullString(String str) {
        String str2 = "";
        if (!StringHelper.isNullOrEmpty(str) && str.length() > 0) {
            str2 = str.replaceAll(" ", "");

        } else {
            str2 = "";
        }
        return str2;
    }

    protected String getVersionName() {
        try {
            return this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            log_baseActivity.warn(e.getMessage(), e);
            return "";
        }
    }

    // public Context getBaseContext() {
    // return BaseActivity.this;
    // }

    // 获取设备ID
    @SuppressLint("MissingPermission")
    protected String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();
        sb.append(tm.getDeviceId());
        // sb.append("\nDeviceSoftwareVersion = " +
        // tm.getDeviceSoftwareVersion());
        // sb.append("\nLine1Number = " + tm.getLine1Number());
        // sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
        // sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
        // sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
        // sb.append("\nNetworkType = " + tm.getNetworkType());
        // sb.append("\nPhoneType = " + tm.getPhoneType());
        // sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        // sb.append("\nSimOperator = " + tm.getSimOperator());
        // sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        // sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
        // sb.append("\nSimState = " + tm.getSimState());
        // sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        // sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());

        return sb.toString();
    }

//    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onBackPressed() {
        // if
//        imageLoader.stop();
        super.onBackPressed();
    }

    /**
     * 处理全局DDCartApplication初始化工作，子类务必首先调用super.onCreate(Bundle)方法。
     * 
     * @see Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log_baseActivity.verbose(this.getLocalClassName() + " onCreate");
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        // mSwipeBackLayout = getSwipeBackLayout();
        // mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
        // setContentView(R.layout.base_activity);
//         ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(BaseActivity.this));

        CrashReport.initCrashReport(getApplicationContext(), "dd748d0e46", true);

    }

    @Override
    protected void onResume() {
        log_baseActivity.verbose(this.getLocalClassName() + " onResume");
        JPushInterface.onResume(this);
        super.onResume();
    }

    /**
     * 当用户按下某个按键时调用适当的Callback方法。比如当用户按下返回键时调用onBackKeyDown(int, KeyEvent)方法。
     * 
     * @see Activity#onKeyDown(int, KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (onBackKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 子类可以选择重写该方法，用于处理返回键按下事件。基类的默认实现始终返回false。
     * 
     * @param keyCode
     * @param event
     * @return
     */
    protected boolean onBackKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    /**
     * 在结束Activity前显示提示对话框
     * 
     * @param message
     *            提示消息文本
     */
    protected void showFinishAlert(String message) {
        if (StringHelper.isNullOrEmpty(message)) {
            message = "The activity meet some error, must finish now.";
        }
        new AlertDialog.Builder(this).setTitle("提示").setIcon(android.R.drawable.ic_dialog_info).setMessage(message)
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BaseActivity.this.finish();

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    BaseActivity.this.finish();
                }
            }).show();
    }

    /**
     * 显示Toast提示
     * 
     * @param message
     *            提示消息文本
     * @param duration
     *            提示显示时间, {@link }, {@link }
     */
    protected void showToast(String message, int duration) {
        if (StringHelper.isNullOrEmpty(message)) {
            return;
        }
        Toast.makeText(getApplicationContext(), message, duration).show();
    }

    protected void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        log_baseActivity.verbose(this.getLocalClassName() + " onDestroy");
//        ImageLoader.getInstance().stop();
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        log_baseActivity.verbose(this.getLocalClassName() + " onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        log_baseActivity.verbose(this.getLocalClassName() + " onPause");
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        log_baseActivity.verbose(this.getLocalClassName() + " onRestart");
//        MyReceiver.setAliasAndTags(getApplicationContext());
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        log_baseActivity.verbose(this.getLocalClassName() + " onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        log_baseActivity.verbose(this.getLocalClassName() + " onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        log_baseActivity.verbose(this.getLocalClassName() + " onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        log_baseActivity.verbose(this.getLocalClassName() + " onStop");
        super.onStop();
    }

    protected boolean hasExtra(String pExtraKey) {
        if (getIntent() != null) {
            return getIntent().hasExtra(pExtraKey);
        }
        return false;
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /** 等待提示框 */
    public void showLoadingDialog(String msg) {
        if (loginDialog == null) {
            loginDialog = new MyDialog(BaseActivity.this, R.style.MyDialog);
        }
        if (loginDialog.isShowing()) {
            loginDialog.dismiss();
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.login_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.login_dialog_textview);
        text.setText(msg);
        // 设置显示位置
        Window window = loginDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置透明度
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.8f;
        lp.width = (int) (display.getWidth() * 0.85);
        window.setAttributes(lp);

        // 弹出对话框或某些模式窗口时，后面的内容会变得模糊或不清楚
        // window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
        // WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; // 默认返回 false

                }
            }
        });
        loginDialog.setContentView(view);
        loginDialog.setCancelable(false);
        loginDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }

    /** 检查更新Dialog */
    public void checkNewVersionDialog() {
        updateDialog = new MyDialog(BaseActivity.this, R.style.MyDialog);
        updateDialog.setContentView(R.layout.check_version_dialog);

        updateDialog.show();
    }

    public void canclecheckNewVersionDialog() {
        if (updateDialog != null && updateDialog.isShowing()) {
            updateDialog.dismiss();
        }
    }

    /**
     * 检测网路是否连通
     * @param context
     * @return
     */
    public static boolean isConnNet(Context context) {
        // 获得手机所有连接管理对象（包括对wi-fi等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获得网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已连接
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                        ;
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    protected void setBooleanSharedPreferences(String filename, String key, boolean value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean getBooleanSharePreferences(String filename, String key) {
        boolean flag = this.getSharedPreferences(filename, 0).getBoolean(key, false);
        return flag;
    }

    protected void setStringSharedPreferences(String filename, String key, String value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected String getStringSharePreferences(String filename, String key) {
        String result = this.getSharedPreferences(filename, 0).getString(key, "");
        return result;
    }

    protected void setLongSharedpreferences(String filename, String key, long value) {
        SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    protected long getLongSharePreferences(String filename, String key) {
        long result = this.getSharedPreferences(filename, 0).getLong(key, 0);
        return result;
    }


    protected void setIntSharedpreferences(String filename, String key, int value) {
            SharedPreferences.Editor editor = this.getSharedPreferences(filename, 0).edit();
            editor.putInt(key, value);
            editor.commit();
    }

    protected int getIntSharePreferences(String filename, String key) {
        int result = this.getSharedPreferences(filename, 0).getInt(key, 0);
        return result;
    }

    /**
     * 如果输入法打开则关闭，如果没打开则打开 @Title: hideInputMethods @Description:
     * TODO(这里用一句话描述这个方法的作用) @param 设定文件 @return void 返回类型 @throws
     */
    public void hideInputMethods() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 接受软键盘输入的编辑文本或其它视图 @Title: showInputMethods @Description:
     * TODO(这里用一句话描述这个方法的作用) @param 设定文件 @return void 返回类型 @throws
     */
    public void showInputMethods() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInputFromInputMethod(BaseActivity.this.getCurrentFocus().getWindowToken(),
            InputMethodManager.SHOW_FORCED);
    }

    public void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        // imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
    }

//    public String getUserId() {
//        String str = "";
//        if (!StringHelper.isNullOrEmpty(getStringSharePreferences(Constants.SETTINGS, Constants.USERID))) {
//            str = getStringSharePreferences(Constants.SETTINGS, Constants.USERID);
//            return str;
//        } else {
//            str = "";
//        }
//        return str;
//
//    }

//    public String getUserName() {
//        String str = "";
//        if (!StringHelper.isNullOrEmpty(getStringSharePreferences(Constants.SETTINGS, Constants.ACCOUNT))) {
//            str = getStringSharePreferences(Constants.SETTINGS, Constants.ACCOUNT);
//            return str;
//        } else {
//            str = "";
//        }
//        return str;
//
//    }

    public void defaultFinish() {
        super.finish();
    }

    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub

    }

    // EditText错误提示
    public void showTipError(EditText ed, String estring) {
        // int ecolor = R.color.black;
        // ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        // SpannableStringBuilder ssbuilder = new
        // SpannableStringBuilder(estring);
        // ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
        // ed.setError(ssbuilder);
        showToast(estring, Toast.LENGTH_SHORT);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);
            LinearLayout layout = new LinearLayout(this);
            layout.addView(listItem);
            layout.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * @Title: openTXJFWeb
     * @Description: TODO(跳转webActivity)
     */
//    protected void openWebActivity(String url, String title) {
//        if (isConnNet(this)) {
//            if (!StringHelper.isNullOrEmpty(url)) {
//                Bundle b = new Bundle();
//                b.putString("query_url", url);
//                b.putString("query_title", title);
//                openActivity(WebMoreActivity.class, b);
//            } else {
//                showToast("访问链接异常，请稍后再试");
//            }
//        } else {
//            showToast("网络异常，请检查网络");
//        }
//    }

    protected <T extends View> T findviewByid(int id) {
        return (T) super.findViewById(id);
    }

//    public static void setStatusBarTranslucent(Activity activity){
//        //5.0以上
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            //获取DecorView
//            View decorView = activity.getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 - 5.0 之间
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            //在状态栏的部分添加一个布局 setContentView 的源码,自己添加一个布局（高度是状态栏的高度）
//            View view = new View(activity);
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
//            view.setLayoutParams(params);
//            view.setBackgroundColor(Color.TRANSPARENT);
//
//            ViewGroup decorView = (ViewGroup)activity.getWindow().getDecorView();
//            decorView.addView(view);
//
//            //获取activity中setContentView布局的根布局
//            ViewGroup contentView = activity.findViewById(android.R.id.content);
//            contentView.setPadding(0,getStatusBarHeight(activity),0,0);
//        }
//    }

    //轮播图的监听方法
//    public abstract void OnBannerClick(int position);

//    public abstract void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition);
}
