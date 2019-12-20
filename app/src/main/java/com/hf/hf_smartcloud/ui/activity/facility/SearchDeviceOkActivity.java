package com.hf.hf_smartcloud.ui.activity.facility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.UserEvent;
import com.hf.hf_smartcloud.ui.activity.MainActivity;
import com.hf.hf_smartcloud.utils.bluetooth.BluetoothLeClass;
import com.hf.hf_smartcloud.utils.bluetooth.LeDeviceListAdapter;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.hf.hf_smartcloud.utils.bluetooth.iBeaconClass;
import com.hf.hf_smartcloud.utils.corrugated.HorizontalListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
/**
* 搜索完成窗口界面
 * */
public class SearchDeviceOkActivity extends BaseActivity {
    //创建显示列表的listView
    private HorizontalListView listView;
    //列表标题list
    private List<String> listText;
    //创建适配器对象
    private ImageView iv_colse;
    private LinearLayout datalist,firstD;
    private GifImageView gifImageView_dev;
    private GifDrawable gifDrawable;
    //===============================================================
    // 搜索BLE终端
    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter = null;
    // 读写BLE终端
    static private BluetoothLeClass mBLE;
    //===============
    private boolean mScanning;
    private String devname;
    // 定义全部的蓝牙GATT特征值的UUID，TI的例程中有5个，Amo增加了一些，小北又修改一些
    public static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR1 = "0000fff1-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR2 = "0000fff2-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR3 = "0000fff3-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR4 = "0000fff4-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR5 = "0000fff5-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR6 = "0000fff6-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR7 = "0000fff7-0000-1000-8000-00805f9b34fb";
    public static String UUID_HERATRATE = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String UUID_TEMPERATURE = "00002a1c-0000-1000-8000-00805f9b34fb";
    // 用到的特征值
    static BluetoothGattCharacteristic gattCharacteristic_char1 = null;
    static BluetoothGattCharacteristic gattCharacteristic_char6 = null;
    static BluetoothGattCharacteristic gattCharacteristic_heartrate = null;
    static BluetoothGattCharacteristic gattCharacteristic_keydata = null;
    static BluetoothGattCharacteristic gattCharacteristic_temperature = null;

    private static final int REQUEST_COARSE_LOCATION = 0;
//    public String bluetoothAddress;
//    // 定义两个状态标志
//    public static final int REFRESH = 0x000001;
//    private final static int REQUEST_CODE = 1;
    static private byte writeValue_char1 = 0;
    //=========================================================================

    private int checkNum; // 记录选中的条目数量
    private Button btn_bund;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdeviceok);
        mayRequestLocation();
        //初始化页面对象
        init();
        //将数据显示在页面上
//        initDate();
        if(!getStringSharePreferences("play","play").equals("true")){
            timer.start();
        }else {
            firstD.setVisibility(View.GONE);
            datalist.setVisibility(View.VISIBLE);
        }

        initBle();
    }
    public  void init(){
        windowColor();
        btn_bund = findviewByid(R.id.btn_bund);
        listView=(HorizontalListView) findViewById(R.id.lv_text_view);
        listText=new ArrayList<String>();
        iv_colse = findViewById(R.id.iv_close);
        iv_colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datalist = findViewById(R.id.datalist);
        firstD = findViewById(R.id.firstD);
        gifImageView_dev = (GifImageView) findViewById(R.id.gifImageView_dev);
        gifDrawable = (GifDrawable) gifImageView_dev.getDrawable();
    }

    CountDownTimer timer = new CountDownTimer(7*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            firstD.setVisibility(View.GONE);
            datalist.setVisibility(View.VISIBLE);
            setStringSharedPreferences("play","play","true");
        }
    };


    public void windowColor() {
        Window window = getWindow();
        //取消设置Window半透明的Flag
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏为透明/或者需要的颜色
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));

        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部 默认在中间
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//设置宽度满屏
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes(lp);
        setFinishOnTouchOutside(true);//允许点击空白处关闭
    }

/**+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
private void mayRequestLocation() {
    if (Build.VERSION.SDK_INT >= 23) {
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            //判断是否需要 向用户解释，为什么要申请该权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                Toast.makeText(this, "动态请求权限", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
            return;
        } else {

        }
    } else {

    }
}

    //系统方法,从requestPermissions()方法回调结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //确保是我们的请求
        if (requestCode == REQUEST_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限被授予", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @SuppressLint("NewApi")
    private void initBle() {
        if (!SearchDeviceOkActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.i("gxl", "initialize Bluetooth, has BLE system");
        }
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported,Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            Log.i("gxl", "mBluetoothAdapter = " + mBluetoothAdapter);
        }
        // 开蓝牙喽
        mBluetoothAdapter.enable();
        Log.i("gxl", "mBluetoothAdapter.enable");

        mBLE = new BluetoothLeClass(this);
        if (!mBLE.initialize()) {
            Log.e("gxl", "Unable to initialize Bluetooth");
            finish();
        }
        Log.i("gxl", "mBLE = e" + mBLE);

        // 发现BLE终端的Service事件
        mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);

        // 收到BLE终端数据交互的事件
        mBLE.setOnDataAvailableListener(mOnDataAvailable);

//        new MyThread().start();
    }


    /**
     * 搜索到BLE终端服务的事�?
     */
    private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new BluetoothLeClass.OnServiceDiscoverListener() {

        @Override
        public void onServiceDiscover(BluetoothGatt gatt) {
            displayGattServices(mBLE.getSupportedGattServices());
            Log.i("==gxlSearchDeviceOkActivity", "mBLESearchDeviceOkActivity" + mBLE.getSupportedGattServices());
        }
    };

    /**
     * 收到BLE终端数据交互的事�?
     */
    private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new BluetoothLeClass.OnDataAvailableListener() {
        /**
         * BLE终端数据被读的事�?
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            // 执行 mBLE.readCharacteristic(gattCharacteristic); 后就会收到数�? if
            // (status == BluetoothGatt.GATT_SUCCESS)
            Log.e("gxl",
                    "onCharRead " + gatt.getDevice().getName() + " read "+ characteristic.getUuid().toString() + " -> "+ Utils.bytesToHexString(characteristic.getValue()));

            BlePorDevActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
        }

        /**
         * 收到BLE终端写入数据回调
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.e("", "onCharWrite " + gatt.getDevice().getName() + " write "
                    + characteristic.getUuid().toString() + " -> "
                    + new String(characteristic.getValue()));
            BlePorDevActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
        }
    };



    // 搞到Gatt中的服务信息、特征值信息、和描述符信息，然后跳到AmoComNewActivity啦
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null)
            return;
        BluetoothGattCharacteristic Characteristic_cur = null;

        for (BluetoothGattService gattService : gattServices) {
            // -----Service的字段信息----//
            int type = gattService.getType();
            Log.e("gxl", "-->service type:" + Utils.getServiceType(type));
            Log.e("gxl", "-->includedServices size:" + gattService.getIncludedServices().size());
            Log.e("gxl", "-->service uuid:" + gattService.getUuid());
            // -----Characteristics的字段信息---
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                Log.e("gxl", "---->char uuid:" + gattCharacteristic.getUuid());
                int permission = gattCharacteristic.getPermissions();
                Log.e("gxl","---->char permission:"+ Utils.getCharPermission(permission));

                int property = gattCharacteristic.getProperties();
                Log.e("gxl","---->char property:" + Utils.getCharPropertie(property));

                byte[] data = gattCharacteristic.getValue();
                if (data != null && data.length > 0) {
                    Log.e("gxl", "---->char value:" + new String(data));
                }

                if (gattCharacteristic.getUuid().toString().equals(UUID_CHAR6)) {
                    // 把char6 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_char6 = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i("gxl", "+++++++++UUID_CHAR6");
                }

                if (gattCharacteristic.getUuid().toString()
                        .equals(UUID_HERATRATE)) {
                    // 把heartrate 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_heartrate = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i("gxl", "+++++++++UUID_HERATRATE");
                }

                if (gattCharacteristic.getUuid().toString()
                        .equals(UUID_KEY_DATA)) {
                    // 把keydata 保存起来,以方便后面读写数据时使用
                    gattCharacteristic_keydata = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i("gxl", "+++++++++UUID_KEY_DATA");
                }

                if (gattCharacteristic.getUuid().toString()
                        .equals(UUID_TEMPERATURE)) {
                    // 把temperature 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_temperature = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i("gxl", "+++++++++UUID_TEMPERATURE");
                }

                // -----Descriptors的字段信息----//
                List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic
                        .getDescriptors();
                for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
                    Log.e("gxl", "-------->desc uuid:" + gattDescriptor.getUuid());
                    int descPermission = gattDescriptor.getPermissions();
                    Log.e("gxl","-------->desc permission:" + Utils.getDescPermission(descPermission));
                    byte[] desData = gattDescriptor.getValue();
                    if (desData != null && desData.length > 0) {
                        Log.e("gxl", "-------->desc value:" + new String(desData));
                    }
                }
            }
        }
    }
    static public void writeChar1() {
        byte[] writeValue = new byte[1];
        Log.i("gxl", "gattCharacteristic_char1 = " + gattCharacteristic_char1);
        if (gattCharacteristic_char1 != null) {
            writeValue[0] = writeValue_char1++;
            Log.i("gxl", "gattCharacteristic_char1.setValue writeValue[0] =" + writeValue[0]);
            boolean bRet = gattCharacteristic_char1.setValue(writeValue);
            mBLE.writeCharacteristic(gattCharacteristic_char1);
        }
    }
    static public void writeChar6(String string) {
        // byte[] writeValue = new byte[1];
        Log.i("gxl", "gattCharacteristic_char6 = " + gattCharacteristic_char6);
        if (gattCharacteristic_char6 != null) {
            boolean bRet = gattCharacteristic_char6.setValue(string);
            mBLE.writeCharacteristic(gattCharacteristic_char6);
        }
    }

    static public void read_char1() {
        byte[] writeValue = new byte[1];
        Log.i("gxl", "readCharacteristic = ");
        if (gattCharacteristic_char1 != null) {
            mBLE.readCharacteristic(gattCharacteristic_char1);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mBLE.close();
        mLeDeviceListAdapter = new LeDeviceListAdapter(this);
        listView.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 后期可以不用，此处只用于单选的时候
                 * */
                ArrayList<UserEvent> list = new ArrayList<>();
                UserEvent userEvent=new UserEvent();
                userEvent.setDev_name(mLeDeviceListAdapter.getDevice(position).name);
                userEvent.setMac_addr(mLeDeviceListAdapter.getDevice(position).bluetoothAddress);
                userEvent.setChar_uuid(mLeDeviceListAdapter.getDevice(position).proximityUuid);
                list.add(userEvent);
                Gson gson2=new Gson();
                String str=gson2.toJson(list);
                Intent intent = new Intent(SearchDeviceOkActivity.this, MainActivity.class);
                startActivity(intent);
                EventBus.getDefault().post(new UserEvent(str));
            }
        });

        btn_bund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Integer, Boolean> map =mLeDeviceListAdapter.map;

                // TODO Auto-generated method stub
                String options="选择的项是:";
                for(int j=0;j<mLeDeviceListAdapter.getCount();j++){
                    if(map.get(j)!=null){
                       String name = mLeDeviceListAdapter.getDevice(j).name;
                        String mac = mLeDeviceListAdapter.getDevice(j).bluetoothAddress;


                        options+="\n"+name+"."+mac;

                    }
                }
//显示选择内容
                Toast.makeText(getApplicationContext(), options, Toast.LENGTH_LONG).show();



            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("gxl", "---> onStop");
        super.onStop();
        DisplayStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("gxl", "start onDestroy~~~");
        scanLeDevice(false);
        mBLE.disconnect();
        mBLE.close();
    }

    public void DisplayStop() {
        Log.i("gxl", "DisplayStop---");
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
//        invalidateOptionsMenu();
    }


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi,scanRecord);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String name = device.getName();
                    Log.i("==name","==name"+name);
                    if (name != null && name.equals("HFP-1806A")){
                        mLeDeviceListAdapter.addDevice(ibeacon);
                        mLeDeviceListAdapter.notifyDataSetChanged();
                    }else if (name != null && name.equals("HFP-1804A")){
                        mLeDeviceListAdapter.addDevice(ibeacon);
                        mLeDeviceListAdapter.notifyDataSetChanged();
                    }else if (name != null && name.equals("HFP-1801A")){
                        mLeDeviceListAdapter.addDevice(ibeacon);
                        mLeDeviceListAdapter.notifyDataSetChanged();
                    }
//                    else if (name != null && name.equals("HF-BLE-ZJH")){
//                        mLeDeviceListAdapter.addDevice(ibeacon);
//                        mLeDeviceListAdapter.notifyDataSetChanged();
//                    }

                }
            });
            // rssi
            Log.i("gxl", "rssi = " + rssi);
            Log.i("gxl", "mac = " + device.getAddress());
            Log.i("gxl", "scanRecord.length = " + scanRecord.length);
            Log.i("==name", "name = " + device.getName());
        }
    };
}
