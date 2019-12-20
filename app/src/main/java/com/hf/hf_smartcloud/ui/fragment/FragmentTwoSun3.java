package com.hf.hf_smartcloud.ui.fragment;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.Sun3HostDevicesAdapter;
import com.hf.hf_smartcloud.base.BaseFragment;
import com.hf.hf_smartcloud.bean.UserEvent;
import com.hf.hf_smartcloud.ui.activity.facility.BlePorDevActivity;
import com.hf.hf_smartcloud.ui.activity.facility.DeviceSettingActivity;
import com.hf.hf_smartcloud.ui.activity.facility.DevicesPortableDetailsActivity;
import com.hf.hf_smartcloud.ui.activity.facility.MmolAimingActivity;
import com.hf.hf_smartcloud.ui.activity.facility.ResetAimingActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.utils.bluetooth.BluetoothLeClass;
import com.hf.hf_smartcloud.utils.bluetooth.LeDeviceListAdapter;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.hf.hf_smartcloud.utils.bluetooth.iBeaconClass;
import com.hf.hf_smartcloud.wrapper.EndlessRecyclerOnScrollListener;
import com.hf.hf_smartcloud.wrapper.LoadMoreWrapper;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentTwoSun3 extends BaseFragment {
    // 定义两个状态标志
    private final static String TAG = "FragmentTwoSun3";// DeviceScanNewActivity.class.getSimpleName();
    private static final int REQUEST_COARSE_LOCATION = 0;
    public static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static String UUID_CHAR6 = "0000fff6-0000-1000-8000-00805f9b34fb";
    public static String UUID_HERATRATE = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String UUID_TEMPERATURE = "00002a1c-0000-1000-8000-00805f9b34fb";
    static BluetoothGattCharacteristic gattCharacteristic_char1 = null;
    static BluetoothGattCharacteristic gattCharacteristic_heartrate = null;
    static BluetoothGattCharacteristic gattCharacteristic_char6 = null;
    static BluetoothGattCharacteristic gattCharacteristic_keydata = null;
    static BluetoothGattCharacteristic gattCharacteristic_temperature = null;
    // 读写BLE终端
    static private BluetoothLeClass mBLE;
    List<UserEvent> list;
    String name, mac, uuid;
    private TextView tv;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout_sun3;
    private RecyclerView recyclerView_sun3;
    private LoadMoreWrapper loadMoreWrapper;
    private Sun3HostDevicesAdapter adapter;
    // 搜索BLE终端
    private BluetoothAdapter mBluetoothAdapter;
    private LeDeviceListAdapter mLeDeviceListAdapter = null;
    private boolean mScanning;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        if (view == null) {
            view = inflater.inflate(R.layout.fragmenttwosun3, container, false);
        }
        EventBus.getDefault().register(this);
        mayRequestLocation();

        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Log.i(TAG, "initialize Bluetooth, has BLE system");
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Log.i(TAG, "mBluetoothAdapter = " + mBluetoothAdapter);
        }
        // 开蓝牙喽
        mBluetoothAdapter.enable();
        Log.i(TAG, "mBluetoothAdapter.enable");
        // 发现BLE终端的Service事件
        Log.i(TAG, "mBLE = e" + mBLE);
        mBLE = new BluetoothLeClass(getActivity());
        if (!mBLE.initialize()) {
            Log.e(TAG, "Unable to initialize Bluetooth");
            getActivity().finish();
        }

        // 发现BLE终端的Service事件  搜索回调
        mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);

        // 收到BLE终端数据交互的事件   接收回调
        mBLE.setOnDataAvailableListener(mOnDataAvailable);




        return view;
    }

    private void mayRequestLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(getActivity(), "动态请求权限", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
                return;
            }
        }
    }

    //系统方法,从requestPermissions()方法回调结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //确保是我们的请求
        if (requestCode == REQUEST_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "权限被授予", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    /**
     * 搜索到BLE终端服务的事�?
     */
    String str;
    private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new BluetoothLeClass.OnServiceDiscoverListener() {
        @Override
        public void onServiceDiscover(BluetoothGatt gatt) {
            displayGattServices(mBLE.getSupportedGattServices());
            Log.i("==gxlFragmentTwoSun3", "mBLESearchDeviceOkActivity" + mBLE.getSupportedGattServices());
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
            Log.e(TAG, "-->service type:" + Utils.getServiceType(type));
            Log.e(TAG, "-->includedServices size:"+ gattService.getIncludedServices().size());
            Log.e(TAG, "-->service uuid:" + gattService.getUuid());

            // -----Characteristics的字段信息---//
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                Log.e(TAG, "---->char uuid:" + gattCharacteristic.getUuid());

                int permission = gattCharacteristic.getPermissions();
                Log.e(TAG,
                        "---->char permission:"
                                + Utils.getCharPermission(permission));

                int property = gattCharacteristic.getProperties();
                Log.e(TAG,
                        "---->char property:"
                                + Utils.getCharPropertie(property));

                byte[] data = gattCharacteristic.getValue();
                if (data != null && data.length > 0) {
                    Log.e(TAG, "---->char value:" + new String(data));
                }

                if (gattCharacteristic.getUuid().toString().equals(UUID_CHAR6)) {
                    // 把char6 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_char6 = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.e(TAG, "---->UUID_CHAR6" + gattCharacteristic_char6);
                }

                if (gattCharacteristic.getUuid().toString().equals(UUID_HERATRATE)) {
                    // 把heartrate 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_heartrate = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i(TAG, "+++++++++UUID_HERATRATE");
                }

                if (gattCharacteristic.getUuid().toString()
                        .equals(UUID_KEY_DATA)) {
                    // 把keydata 保存起来,以方便后面读写数据时使用
                    gattCharacteristic_keydata = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i(TAG, "+++++++++UUID_KEY_DATA");
                }

                if (gattCharacteristic.getUuid().toString()
                        .equals(UUID_TEMPERATURE)) {
                    // 把temperature 保存起来，以方便后面读写数据时使用
                    gattCharacteristic_temperature = gattCharacteristic;
                    Characteristic_cur = gattCharacteristic;
                    // 接受Characteristic被写的通知，收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBLE.setCharacteristicNotification(gattCharacteristic, true);
                    Log.i(TAG, "+++++++++UUID_TEMPERATURE");
                }

                // -----Descriptors的字段信息----//
                List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic
                        .getDescriptors();
                for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
                    Log.e(TAG, "-------->desc uuid:" + gattDescriptor.getUuid());
                    int descPermission = gattDescriptor.getPermissions();
                    Log.e(TAG,
                            "-------->desc permission:"
                                    + Utils.getDescPermission(descPermission));

                    byte[] desData = gattDescriptor.getValue();
                    if (desData != null && desData.length > 0) {
                        Log.e(TAG, "-------->desc value:" + new String(desData));
                    }
                }
            }
        }

        Intent intent = new Intent(getActivity(), BlePorDevActivity.class);
        intent.putExtra("devname", name);
        intent.putExtra("mac_addr", mac);
        intent.putExtra("char_uuid", Characteristic_cur.getUuid().toString());
        startActivity(intent);
    }
    /**
     * 收到BLE终端数据交互的事�?
     */
    private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new BluetoothLeClass.OnDataAvailableListener() {
        /**
         * BLE终端数据被读的事�?
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, "onCharRead " + gatt.getDevice().getName() + " read " + characteristic.getUuid().toString() + " -> " + Utils.bytesToHexString(characteristic.getValue()));
            String curAppTaskPackgeName = null;
            String curAppTaskClassName = null;
            ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(Integer.MAX_VALUE);
            Log.i("size:::","size:::"+appTask.size());
            if (appTask.size() > 0) {
                curAppTaskPackgeName = appTask.get(0).topActivity.getPackageName();
                curAppTaskClassName = appTask.get(0).topActivity.getClassName();
                Log.i("==***","原始类名Read："+curAppTaskClassName+"包名："+curAppTaskPackgeName);
            }
            if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.BlePorDevActivity")){
                BlePorDevActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
            }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.ResetAimingActivity")){
                ResetAimingActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
            }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.MmolAimingActivity")){
                ResetAimingActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
            }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.DeviceSettingActivity")){
                DeviceSettingActivity.char6_displayMmolAiming(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
            }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.DevicesPortableDetailsActivity")){
                DevicesPortableDetailsActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
            }
            appTask.clear();
        }

        /**
         * 收到BLE终端写入数据回调
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharWrite " + gatt.getDevice().getName() + " write " + characteristic.getUuid().toString() + " -> " + new String(characteristic.getValue()));
            String curAppTaskPackgeName = null;
            String curAppTaskClassName = null;
            ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(Integer.MAX_VALUE);
            Log.i("size:::","size:::"+appTask.size());
            if (appTask.size() > 0) {
                curAppTaskPackgeName = appTask.get(0).topActivity.getPackageName();
                curAppTaskClassName = appTask.get(0).topActivity.getClassName();
                Log.i("==***","原始类名Write："+curAppTaskClassName+"包名："+curAppTaskPackgeName);
                if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.BlePorDevActivity")){
                    BlePorDevActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
                }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.ResetAimingActivity")){
                    ResetAimingActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
                }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.MmolAimingActivity")){
                    MmolAimingActivity.char6_displayMmolAiming(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
                }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.DeviceSettingActivity")){
                    DeviceSettingActivity.char6_displayMmolAiming(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
                }else if(curAppTaskClassName.equals("com.hf.hf_smartcloud.ui.activity.facility.DevicesPortableDetailsActivity")){
                    DevicesPortableDetailsActivity.char6_display(Utils.bytesToString(characteristic.getValue()), characteristic.getValue(), characteristic.getUuid().toString());
                }
            }

        }
    };

    // Device scan callback.监听扫描结果。
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(device, rssi, scanRecord);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String name = device.getName();
                    Log.i("==name", "==name" + name);
                    if (name != null) {
                        if (name != null && name.equals("HFP-1806A")) {
                            mLeDeviceListAdapter.addDevice(ibeacon);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }else if (name != null && name.equals("HFP-1804A")) {
                            mLeDeviceListAdapter.addDevice(ibeacon);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        } else if (name != null && name.equals("HFP-1801A")) {
                            mLeDeviceListAdapter.addDevice(ibeacon);
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
//                        else if (name != null && name.equals("HF-BLE-ZJH")) {
//                            mLeDeviceListAdapter.addDevice(ibeacon);
//                            mLeDeviceListAdapter.notifyDataSetChanged();
//                        }
                    } else {
                        Log.i("==name", "==name" + "name为空");
                    }
                }
            });
            // rssi
            Log.i(TAG, "rssi = " + rssi);
            Log.i(TAG, "mac = " + device.getAddress());
            Log.i(TAG, "scanRecord.length = " + scanRecord.length);
            Log.i("==name", "name = " + device.getName());
        }
    };


     public static void writeChar6(String string) {
        Log.i(TAG, "gattCsharacteristic_char6 = " + gattCharacteristic_char6);
        if (gattCharacteristic_char6 != null) {
            boolean bRet = gattCharacteristic_char6.setValue(string);
            mBLE.writeCharacteristic(gattCharacteristic_char6);
        }

    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mScanning = true;
            //搜索附近所有的外围设备
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            //停止扫描
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

//===============================================================================================================================================================

    @Override
    public void onResume() {
        super.onResume();
        // 发现BLE终端的Service事件  搜索回调
        mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);
        // 收到BLE终端数据交互的事件   接收回调
        mBLE.setOnDataAvailableListener(mOnDataAvailable);
//        mBLE.close();
        scanLeDevice(true);
        mLeDeviceListAdapter = new LeDeviceListAdapter(getActivity());
//        Log.i("name","name"+name);

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "---> onDestroy");
        super.onDestroy();
        Log.e(TAG, "start onDestroy~~~");
        scanLeDevice(false);
        mBLE.disconnect();
        mBLE.close();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initList();

    }

    private void initList() {
        swipeRefreshLayout_sun3 = view.findViewById(R.id.swipe_refresh_layout_sun3);
        recyclerView_sun3 = view.findViewById(R.id.recycler_view_sun3);
        // 设置刷新控件颜色
        swipeRefreshLayout_sun3.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 模拟获取数据
//        getData();
        if (list != null && list.size() != 0) {
            adapter = new Sun3HostDevicesAdapter(list);
            loadMoreWrapper = new LoadMoreWrapper(adapter);
        }

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_sun3.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView_sun3.setAdapter(loadMoreWrapper);
        recyclerView_sun3.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 点击
                showToast("您点击了" + position + "项");
                boolean bRet = mBLE.connect(list.get(position).getMac_addr());//蓝牙是否连接
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }else if(bRet==true){
                    Intent intent = new Intent(getActivity(), BlePorDevActivity.class);
                    intent.putExtra("devname", name);
                    intent.putExtra("mac_addr", mac);
                    startActivity(intent);
                }else {
                    showToast("连接失败，请重新连接...");
                }

                Log.i("==bRet", "==bRet==" + bRet);
//                Bundle bundle = new Bundle();
//                bundle.putString("name",list.get(position).getDev_name());
//                bundle.putString("mac",list.get(position).getMac_addr());
//                openActivity(BlePorDevActivity.class,bundle);

            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO 点击
                showToast("您长按了第" + posotion + "项");
            }
        }));

        // 设置下拉刷新
        swipeRefreshLayout_sun3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
//                list.clear();
//                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayout_sun3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout_sun3 != null && swipeRefreshLayout_sun3.isRefreshing()) {
                            swipeRefreshLayout_sun3.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerView_sun3.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (list.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void initViews() {
        tv = view.findViewById(R.id.test);
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UserEvent userEvent) {

        Gson gson1 = new Gson();
        list = gson1.fromJson(userEvent.getJsonList(), new TypeToken<List<UserEvent>>() {
        }.getType());
        if (list.size() > 0) {
            for (UserEvent userEvent1 : list) {
                Log.i("userEvent1:", ":::" + userEvent1.toString());
                name = userEvent1.getDev_name();
                mac = userEvent1.getMac_addr();
                uuid = userEvent1.getChar_uuid();
                tv.setText("名称:" + name + "\r\n" + "mac地址:" + mac + "\r\n");
                Hawk.put("name",name);
                Hawk.put("mac",mac);
                Hawk.put("uuid",uuid);
            }
        }
        initList();
    }
}