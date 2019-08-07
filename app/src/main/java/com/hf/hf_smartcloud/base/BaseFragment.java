package com.hf.hf_smartcloud.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.ui.activity.MainActivity;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.receiver.ActivityCollector;
import com.hf.hf_smartcloud.utils.DimenUtils;
import com.hf.hf_smartcloud.utils.StringHelper;
import com.hf.hf_smartcloud.weigets.MyDialog;

import java.util.List;

/**
 * @类名称: BaseFragment
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @作者 fengxian
 * @日期 2013-9-7 下午1:32:03
 * 
 */
public abstract class BaseFragment extends Fragment {
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private Dialog loginDialog;
    private Context mContent;
    //上下文
    public MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContent = getActivity();
        ActivityCollector.addActivity(getActivity());
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContent));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 主界面的抽象方法初始化view
     *
     * @return
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        imageLoader.stop();
        ActivityCollector.removeActivity(getActivity());
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }
    
    @Override  
    public void setUserVisibleHint(boolean isVisibleToUser) {//判断当前fragment是否显示问题  
        super.setUserVisibleHint(isVisibleToUser);  
        Log.e("setUserVisibleHint", "setUserVisibleHint " + isVisibleToUser);  
    }

    /**
     * @Title: getVersionCode
     * @Description: TODO(获取版本号 如：code =10)
     * @param
     * @return int 1 异常时返回 0
     * @throws
     */
//    protected int getVersionCode() {
//        try {
//            return getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
//        } catch (NameNotFoundException e) {
//            return 0;
//        }
//    }

//    protected String getVersionName() {
//        try {
//            return getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
//        } catch (NameNotFoundException e) {
//            return "";
//        }
//    }
    
    /**
     * @Title: initOptions 
     * @Description: TODO(初始化DisplayImageOptions) 
     * @param @param imgsId
     * @return DisplayImageOptions    返回类型 
     * @throws
     */
//    public DisplayImageOptions initOptions(int imgsId) {
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(imgsId)
//				.showImageForEmptyUri(imgsId).showImageOnFail(imgsId).cacheInMemory(true)
//				.cacheOnDisc(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
//		return options;
//	}

    /**
     * 检测网路是否连通
     * 
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

    /** 等待提示框 */
    public void showLoadingDialog(String msg) {
        if (loginDialog == null) {
            loginDialog = new MyDialog(getActivity(), R.style.MyDialog);
        }
        if (loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.login_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.login_dialog_textview);
        text.setText(msg);
        // 设置显示位置
        Window window = loginDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置透明度
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.8f;
        lp.width = (int) (display.getWidth() * 0.85);
        window.setAttributes(lp);

        // 弹出对话框或某些模式窗口时，后面的内容会变得模糊或不清楚
        // window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
        // WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        loginDialog.setContentView(view);
        loginDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }
    
    /**
     * @Title: newSetViewWidthAndHeight 
     * @Description: TODO(动态设置View的宽高) 
     * @param context    设定文件 
     * @param offset     
     * @param colum    列数 
     * @return LayoutParams    返回类型 
     * @throws
     */
    public LayoutParams newSetViewWidthAndHeight(Context context, int offset, int colum) {
		int width = DimenUtils.getWindowsWidth(context);
		int c = DimenUtils.dip2px(context, offset);
		int realWidth = (width - c)/colum;
		LayoutParams mLayoutParams =new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.width=realWidth;
        mLayoutParams.height=realWidth;
    	 return mLayoutParams;
	}

    /**
     * 显示Toast提示
     * 
     * @param message
     *            提示消息文本
     * @param duration
     *            提示显示时间, {@link }
     */
    public void showToast(String message, int duration) {
        if (StringHelper.isNullOrEmpty(message)) {
            return;
        }
        Toast.makeText(getActivity().getApplicationContext(), message, duration).show();
    }

    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
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

    protected void setBooleanSharedPreferences(String filename, String key, boolean value) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(filename, 0).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected boolean getBooleanSharePreferences(String filename, String key) {
        boolean flag = getActivity().getSharedPreferences(filename, 0).getBoolean(key, false);
        return flag;
    }

    protected void setStringSharedPreferences(String filename, String key, String value) {
        if (mContent != null) {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(filename, 0).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    protected String getStringSharePreferences(String filename, String key) {
        String result = getActivity().getSharedPreferences(filename, 0).getString(key, "");

        if (!StringHelper.isNullOrEmpty(result)) {
            return result;
        } else {
            return "";
        }
    }
    protected String getDateSharePreferences(String filename, String key) {
        String result = getActivity().getSharedPreferences(filename, 0).getString(key, "");

        if (!StringHelper.isNullOrEmpty(result)) {
            return result;
        } else {
            return "";
        }
    }

    protected void setLongSharedpreferences(String filename, String key, long value) {
        if (mContent != null) {
            SharedPreferences.Editor editor = mContent.getSharedPreferences(filename, 0).edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    protected long getLongSharePreferences(String filename, String key) {
        long result = getActivity().getSharedPreferences(filename, 0).getLong(key, 0);
        return result;
    }

    protected void setIntSharedpreferences(String filename, String key, int value) {
        if (mContent != null) {
            SharedPreferences.Editor editor = mContent.getSharedPreferences(filename, 0).edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    protected int getIntSharePreferences(String filename, String key) {
        int result = getActivity().getSharedPreferences(filename, 0).getInt(key, 0);
        return result;
    }

    protected void setFloatSharedpreferences(String filename, String key, float value) {
        if (mContent != null) {
            SharedPreferences.Editor editor = mContent.getSharedPreferences(filename, 0).edit();
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    protected float getFloatSharePreferences(String filename, String key) {
        float result = getActivity().getSharedPreferences(filename, 0).getFloat(key, 0);
        return result;
    }

    /**
     * @Title: openTXJFWeb
     * @Description: TODO(跳转webActivity)
     */
//    protected void openWebActivity(String url, String title) {
//        if (isConnNet(getActivity())) {
//            if (!StringHelper.isNullOrEmpty(url)) {
//                Bundle b = new Bundle();
//                b.putString("query_url", url);
//                b.putString("query_title", title);
//                openActivity(WebMoreActivity.class, b);
//            }else{
//                showToast("访问链接异常，请稍后再试");
//            }
//        } else {
//            showToast("网络异常，请检查网络");
//        }
//    }
    /**
     * @Title: openTXJFWeb
     * @Description: TODO(跳转webActivity)
     */
//    protected void openWebActivity(String url, String title,int tag) {
//        if (isConnNet(getActivity())) {
//            if (!StringHelper.isNullOrEmpty(url)) {
//                Bundle b = new Bundle();
//                b.putString("query_url", url);
//                b.putString("query_title", title);
//                b.putInt("query_tag", tag);
//                openActivity(WebMoreActivity.class, b);
//            }else{
//                showToast("访问链接异常，请稍后再试");
//            }
//        } else {
//            showToast("网络异常，请检查网络");
//        }
//    }

    /**
     * 解决子Fragment中的onActivityResult()方法无响应问题。
     */ @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (getChildFragmentManager().getFragments() != null && getChildFragmentManager().getFragments().size() > 0){
             List<Fragment> fragments = getChildFragmentManager().getFragments();
             for (Fragment mFragment: fragments) {
                 mFragment.onActivityResult(requestCode, resultCode, data);
             }
         }
     }
}
