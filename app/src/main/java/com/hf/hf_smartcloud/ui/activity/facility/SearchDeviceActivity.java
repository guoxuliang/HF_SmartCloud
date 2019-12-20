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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.UserEvent;
import com.hf.hf_smartcloud.ui.activity.MainActivity;
import com.hf.hf_smartcloud.ui.fragment.Fragment2;
import com.hf.hf_smartcloud.utils.bluetooth.BluetoothLeClass;
import com.hf.hf_smartcloud.utils.bluetooth.LeDeviceListAdapter;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.hf.hf_smartcloud.utils.bluetooth.iBeaconClass;
import com.hf.hf_smartcloud.weigets.dialog.RemindTextDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/**
 * 搜索蓝牙设备旧界面
 * */
public class SearchDeviceActivity extends BaseActivity {
/**
 * 倒计时计时器
 * */
//    private static final int SHOW_SEARCH_DIALOG_MESSAGE = 2;
//    private static final long SCAN_PERIOD = 10 * 1000;
//    private RemindTextDialog remindScanDialog = null;
//    private RemindTextDialog.Builder builder = null;
//    private Timer remindScanTimer = null;
//    private int remindScanTimeIndex = 0;
//=============================================
    private ListView mList;
    //=============
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
    public String bluetoothAddress;
    // 定义两个状态标志
    public static final int REFRESH = 0x000001;
    private final static int REQUEST_CODE = 1;
    static private byte writeValue_char1 = 0;
//=============================================
    /**
     * 倒计时Handler
     * */
//    Handler mhandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case SHOW_SEARCH_DIALOG_MESSAGE:
//                    builder = new RemindTextDialog.Builder(SearchDeviceActivity.this, getString(R.string.remind_str),getString(R.string.remind_scaning_dev)+"("+10+"s)",getString(R.string.remind_scaning_dev_stop), 1,new RemindTextDialog.Builder.OnCustomDialogListener() {
//                        @Override
//                        public void back(String str) {
//                            // TODO Auto-generated method stub
//                            Log.i("","str===="+str);
//                            if(str != null && str.equals("OK")){
////                                scanLeDevice(false);
//                                if(remindScanTimer != null){
//                                    remindScanTimer.cancel();
//                                    remindScanTimer = null;
//                                }
//                            }
//                        }
//                    });
//                    remindScanDialog = builder.create();
//                    remindScanDialog.show();
//                    remindScanTimeIndex = (int) (SCAN_PERIOD / 1000);
//                    remindScanTimer = new Timer();
//                    remindScanTimer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            remindScanTimeIndex = remindScanTimeIndex - 1;
//                            if(remindScanTimeIndex > 0){
//                                Message message = new Message();
//                                message.obj = getString(R.string.remind_scaning_dev)+("("+remindScanTimeIndex+"s)");
//                                message.what = 0x10;
//                                mhandler.sendMessage(message);
//                            }else {
//                                Log.i("","停止搜索=====");
//                                mhandler.sendEmptyMessage(0x20);
////                                if(mScanning){
////                                    scanLeDevice(false);
////                                }
//                                if(remindScanTimer != null){
//                                    remindScanTimer.cancel();
//                                    remindScanTimer = null;
//                                }
//                            }
//                        }
//                    }, 1000,1000);
//
//                    break;
//                case 0x10:
//                    String txt = msg.obj.toString();
//                    Log.i("==", "准备显示的内容为==="+txt);
//                    builder.setMsgText(txt);
//                    break;
//                case 0x20:
//                    if(remindScanDialog != null && remindScanDialog.isShowing()){
//                        remindScanDialog.dismiss();
//                        remindScanDialog = null;
//                    }
//                    break;
//            }
//        }
//    };

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdevice);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        mayRequestLocation();
//        mhandler.sendEmptyMessage(SHOW_SEARCH_DIALOG_MESSAGE);//搜索倒计时
        initViews();
        initBle();
    }
    private void initViews(){
        mList = findViewById(R.id.list);
    }


    @SuppressLint("NewApi")
    private void initBle() {
        if (!SearchDeviceActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
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

            // OtherActivity.char6_display(BleUtils.bytesToHexString(characteristic.getValue()));

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
        }//
 /*       Intent intent = new Intent();
        intent.putExtra("dev_name", devname);
        intent.putExtra("mac_addr", bluetoothAddress);
        intent.putExtra("char_uuid", Characteristic_cur.getUuid().toString());
        setResult(3, intent);
        finish();*/
 //方法二：利用json
//        ArrayList<UserEvent> list = new ArrayList<>();
//        UserEvent userEvent=new UserEvent();
//        userEvent.setDev_name(devname);
//        userEvent.setMac_addr(bluetoothAddress);
//        userEvent.setChar_uuid(Characteristic_cur.getUuid().toString());
//        list.add(userEvent);
//
//        Gson gson2=new Gson();
//        String str=gson2.toJson(list);
//        Log.i("str==str","str==str"+str);
//        Intent intent = new Intent();
//        intent.putExtra("jsonList", str);
//        setResult(3, intent);
//        finish();

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
            // writeValue[0] = writeValue_char1++;
            // Log.i(TAG, "gattCharacteristic_char6.setValue writeValue[0] =" +
            // writeValue[0]);

            boolean bRet = gattCharacteristic_char6.setValue(string);

//			mBLE.requestMtu(40);
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
        Log.i("gxl", "---> onResume");
        super.onResume();
        mBLE.close();
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter(this);
        mList.setAdapter(mLeDeviceListAdapter);
//        recyclerViewfs.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final iBeaconClass.iBeacon device = mLeDeviceListAdapter.getDevice(position);
//                devname = device.name;
//                if (device == null)
//                    return;
//                if (mScanning) {
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    mScanning = false;
//                }
//
//                Log.i("gxl", "mBluetoothAdapter.enable");
//                bluetoothAddress = device.bluetoothAddress;
//                boolean bRet = mBLE.connect(device.bluetoothAddress);
//
//
//                Log.i("gxl", "connect bRet = " + bRet);
//
//                Toast toast = Toast.makeText(SearchDeviceActivity.this, "正在连接设备并获取服务中", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
                ArrayList<UserEvent> list = new ArrayList<>();
                UserEvent userEvent=new UserEvent();
                userEvent.setDev_name(mLeDeviceListAdapter.getDevice(position).name);
                userEvent.setMac_addr(mLeDeviceListAdapter.getDevice(position).bluetoothAddress);
                userEvent.setChar_uuid(mLeDeviceListAdapter.getDevice(position).proximityUuid);
                list.add(userEvent);

                Gson gson2=new Gson();
                String str=gson2.toJson(list);
                Log.i("str==str","str==str"+str);
                Intent intent = new Intent();
                intent.putExtra("jsonList", str);
                setResult(3, intent);
                finish();

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
//                    else if (name != null && name.equals("HFP-1806A")){
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
