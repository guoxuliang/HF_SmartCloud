package com.hf.hf_smartcloud.ui.activity.facility;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.MainQtAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.BleO9Bean;
import com.hf.hf_smartcloud.bean.BleOFBean;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.ui.fragment.FragmentTwoSun3;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.utils.bluetooth.ResultUtils;
import com.hf.hf_smartcloud.weigets.dialog.RemindTextDialog;
import com.hf.hf_smartcloud.wrapper.EndlessRecyclerOnScrollListener;
import com.hf.hf_smartcloud.wrapper.LoadMoreWrapper;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class BlePorDevActivity extends BaseActivity {

    private final static String TAG = "BlePorDevActivity";
    private static final int COMPLETED = 0;
    /**
     * 倒计时计时器
     */
    private static final int SHOW_SEARCH_DIALOG_MESSAGE = 2;
    private static final long SCAN_PERIOD = 10 * 1000;
    static String resultValue = null;
    static ArrayList<String> resultList = new ArrayList<>();
    static ArrayList<String> resultList09 = new ArrayList<>();
    static ArrayList<BleOFBean> resultListData = new ArrayList<>();
    static ArrayList<BleO9Bean> resultListData09 = new ArrayList<>();
    static String str1 = null;
    static String str2 = null;
    static TextView wendu, shidu;
    private static LinearLayout notdata, datapage;
    private static Button btn_retry;
    private static MainQtAdapter mainQtAdapter;
    private static RecyclerView recyclerMain;
    private static LoadMoreWrapper loadMoreWrapper;
    /**
     * 调用发送线程
     */
    int i = 0;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();
    /**
     * 温湿度传感器数据
     */
    Timer timer = new Timer();
    private ImageView btn_add, btn_sancode;
    private SwipeRefreshLayout swipeRefreshLayout_Main;
    private ImageView btn_back;
    private TextView tv_title, title_mac, title_tv;
    private String mac_addr, dev_name;
    private RemindTextDialog remindScanDialog = null;
    private RemindTextDialog.Builder builder = null;
    private Timer remindScanTimer = null;
    private int remindScanTimeIndex = 0;
    /**
     * 倒计时Handler
     */
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_SEARCH_DIALOG_MESSAGE:
                    builder = new RemindTextDialog.Builder(BlePorDevActivity.this, getString(R.string.remind_str), getString(R.string.remind_scaning_dev) + "(" + 10 + "s)", getString(R.string.remind_scaning_dev_stop), 1, new RemindTextDialog.Builder.OnCustomDialogListener() {
                        @Override
                        public void back(String str) {
                            // TODO Auto-generated method stub
                            Log.i("", "str====" + str);
                            if (str != null && str.equals("OK")) {
//                                scanLeDevice(false);
                                if (remindScanTimer != null) {
                                    remindScanTimer.cancel();
                                    remindScanTimer = null;
                                }
                            }
                        }
                    });
                    remindScanDialog = builder.create();
                    remindScanDialog.show();
                    remindScanTimeIndex = (int) (SCAN_PERIOD / 1000);
                    remindScanTimer = new Timer();
                    remindScanTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            remindScanTimeIndex = remindScanTimeIndex - 1;
                            if (remindScanTimeIndex > 0) {
                                Message message = new Message();
                                message.obj = getString(R.string.remind_scaning_dev) + ("(" + remindScanTimeIndex + "s)");
                                message.what = 0x10;
                                mhandler.sendMessage(message);
                            } else {
                                Log.i("", "停止搜索=====");
                                mhandler.sendEmptyMessage(0x20);
                                if (remindScanTimer != null) {
                                    remindScanTimer.cancel();
                                    remindScanTimer = null;
                                }
                            }
                        }
                    }, 1000, 1000);

                    break;
                case 0x10:
                    String txt = msg.obj.toString();
                    Log.i("==", "准备显示的内容为===" + txt);
                    builder.setMsgText(txt);
                    break;
                case 0x20:
                    if (remindScanDialog != null && remindScanDialog.isShowing()) {
                        remindScanDialog.dismiss();
                        remindScanDialog = null;
                    }
                    break;
            }
        }
    };
    private int temp1 = 0;
    private Handler handlerTime = new Handler() {
        public void handleMessage(Message msg) {
            if (resultList.size() != 0) {
                String fs0 = resultList.get(0);
                if (fs0.startsWith("#0117")) {
                    String res = ResultUtils.register17(fs0);
                    wendu.setText(res.substring(0, res.indexOf(" ")) + "℃");
                    shidu.setText(res.substring(res.indexOf(" ")).trim() + "%RH");
                }
            }
            initList(resultListData);
            temp1++;
        }
    };

    /**
     * 接受返回来的数据
     */
    public static synchronized void char6_display(final String str, byte[] data, String uuid) {
        Log.i("char6_display", "char6_display str = " + str);
        if (uuid.equals(FragmentTwoSun3.UUID_CHAR6)) {  //  的串口透传  uuid是否等于UUID_CHAR6
            if (str.startsWith("#") && !str.endsWith("\r\n")) {
                    str1 = str;
            } else if (!str.startsWith("#") && str.endsWith("\r\n")) {
                    str2 = str;
                    resultValue = str1 + str2;
                    resultList09.add(resultValue);

            } else if (str.startsWith("#") && str.endsWith("\r\n")) {
                resultValue = str;
                resultList.add(resultValue);
            }
        }
        if (resultList.size() == 7 /**&& resultList09.size() == 6*/) {
            for (int i = 1; i < resultList.size(); i++) {
                resultListData.add(ResultUtils.register0F(resultList.get(i)));
                Log.i("handlerUI", "handlerUI:" + ResultUtils.register0F(resultList.get(i)));
            }
            if (resultList09.size() == 6) {
                for (int i = 0; i < resultList09.size(); i++) {
                    resultListData09.add(ResultUtils.register09(resultList09.get(i)));
                    Log.i("handlerUI", "handlerUI09:" + ResultUtils.register09(resultList09.get(i)));
                }
            }

            if (resultListData != null && resultListData.size() != 0 /**&& resultListData09 != null && resultListData09.size() != 0*/) {
                mainQtAdapter = new MainQtAdapter(resultListData, resultListData09);
                loadMoreWrapper = new LoadMoreWrapper(mainQtAdapter);
            }
            if(resultList.size() == 7){
                Hawk.put("resultList",resultList);
            }
            if(resultList09.size() == 6){
                Hawk.put("resultList09",resultList09);
            }
            if(resultListData != null && resultListData.size() != 0){
                Hawk.put("resultListData",resultListData);
            }
            if(resultListData09 != null && resultListData09.size() != 0){
                Hawk.put("resultListData09",resultListData09);
            }
        }
    }

    /**
     * 去掉集合中的重复数据
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blepordev);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        initTitle();//初始化标题
        initViews();//初始化控件
        sendCommand();
        handlerTime.sendEmptyMessage(0);

    }

    private void initList(ArrayList<BleOFBean> resultListData) {

        swipeRefreshLayout_Main = findViewById(R.id.swipe_refresh_layout_Main);
        recyclerMain = findViewById(R.id.recyclerMain);
        // 设置刷新控件颜色
        swipeRefreshLayout_Main.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 模拟获取数据
        if (resultListData != null && resultListData.size() != 0) {
            mainQtAdapter = new MainQtAdapter(resultListData, resultListData09);
            loadMoreWrapper = new LoadMoreWrapper(mainQtAdapter);
            timer.cancel();
        }

        recyclerMain.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerMain.setAdapter(loadMoreWrapper);
        recyclerMain.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 点击
                showToast("您点击了" + position + "项");
                Bundle bundle = new Bundle();
                if(resultListData09.size()!=0){
                    bundle.putString("one",resultListData09.get(position).getOnepolice());//一级报警
                    bundle.putString("two",resultListData09.get(position).getTwopolice());//二级报警
                    bundle.putString("gastype",resultListData09.get(position).getGastype());//气体类型
                    bundle.putString("range",resultListData09.get(position).getRange());//量程
                    bundle.putString("unit",resultListData09.get(position).getUnit());//单位
                    bundle.putInt("position",position+1);//传感器位置
                    bundle.putString("gastype",resultListData09.get(position).getGastype());//传感器位置
                    bundle.putFloat("onexs", resultListData09.get(position).getOnepolice2());//一级报警没单位
                    bundle.putFloat("nongdu", resultListData.get(position).getNongdu2());//浓度
                    bundle.putString("jingdu", resultListData.get(position).getJingdu());//精度


                }

                openActivity(DevicesPortableDetailsActivity.class,bundle);

            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO 点击
                showToast("您长按了第" + posotion + "项");
            }
        }));

        // 设置下拉刷新
        swipeRefreshLayout_Main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                resultListData.clear();
                resultListData09.clear();
                resultList.clear();
                resultList09.clear();
                if(resultListData.size()==0 && resultListData09.size()==0){
                    sendCommand();
                }else {
                    showToast("刷新失败,请重试");
                }

                handlerTime.sendEmptyMessage(0);
                mainQtAdapter.notifyDataSetChanged();
//                loadMoreWrapper.notifyDataSetChanged();


                // 延时1s关闭下拉刷新
                swipeRefreshLayout_Main.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout_Main != null && swipeRefreshLayout_Main.isRefreshing()) {
                            swipeRefreshLayout_Main.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

    }

    /**
     * 发送数据
     */
    private void sendCommand() {
        for (int i = 0; i < list.size(); i++) {
            try {
                sleep(500);
                FragmentTwoSun3.writeChar6(list.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        recyclerMain = findViewById(R.id.recyclerMain);
        wendu = findviewByid(R.id.wendu);
        shidu = findviewByid(R.id.shidu);

        btn_sancode = findviewByid(R.id.btn_sancode);
        btn_add = findviewByid(R.id.btn_add);
        btn_back = findViewById(R.id.btn_back);
        tv_title = findViewById(R.id.tv_title);
        title_mac = findViewById(R.id.title_mac);
        title_tv = findViewById(R.id.title_tv);
        String namesp = getStringSharePreferences("name", "name");
        //通过Activity.getIntent()获取当前页面接收到的Intent。
        Intent intent = getIntent();
        dev_name = intent.getStringExtra("devname");
        mac_addr = intent.getStringExtra("mac_addr");
        if (!namesp.equals("")) {
            tv_title.setText(namesp);
        } else {
            tv_title.setText(dev_name);
        }

        title_mac.setText(mac_addr);
        title_tv.setVisibility(View.GONE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlePorDevActivity.this.finish();
            }
        });
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(v);
            }
        });
        btn_sancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name",dev_name);
                bundle.putString("mac_addr",mac_addr);
                openActivity(DeviceSettingActivity.class,bundle);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        list.add(Constants.TEMPORARYSTORAGE0901 + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0902 + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0903 + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0904 + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0905 + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0906 + "\r\n");

        list.add(Constants.TEMPORARYSTORAGE1701R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F01R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F02R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F03R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F04R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F05R + "\r\n");
        list.add(Constants.TEMPORARYSTORAGE0F06R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE1701R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F01R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F02R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F03R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F04R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F05R + "\r\n");
//        list2.add(Constants.TEMPORARYSTORAGE0F06R + "\r\n");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultListData09.clear();
        resultList09.clear();
        resultListData.clear();
        resultList.clear();
    }

    /**
     * 设置设备名称
     */
    public void alert_edit(View view) {
        final EditText et = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入设备名称")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        tv_title.setText(et.getText().toString().trim());
                        String name = et.getText().toString().trim();
                        Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
                        setStringSharedPreferences("name", "name", name);
                    }
                }).setNegativeButton("取消", null).show();
    }
}
