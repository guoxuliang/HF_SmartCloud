package com.hf.hf_smartcloud.utils.bluetooth;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.bean.CheckBoxBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeDeviceListAdapter extends BaseAdapter {

	public HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
	private ArrayList<iBeaconClass.iBeacon> mLeDevices;
	private LayoutInflater mInflator;
	private Activity mContext;


	private List<CheckBoxBean> mCheckBoxBean;


	public List<CheckBoxBean> getmCheckBoxBean() {
		return mCheckBoxBean;
	}

	public void setmCheckBoxBean(List<CheckBoxBean> mCheckBoxBean) {
		this.mCheckBoxBean = mCheckBoxBean;
	}

	public LeDeviceListAdapter(Activity c) {
		super();
		mContext = c;
		mLeDevices = new ArrayList<iBeaconClass.iBeacon>();
		mInflator = mContext.getLayoutInflater();
	}

	public void addDevice(iBeaconClass.iBeacon device) {
		if (device == null)
			return;

		for (int i = 0; i < mLeDevices.size(); i++) {
			String btAddress = mLeDevices.get(i).bluetoothAddress;
			if (btAddress.equals(device.bluetoothAddress)) {
				mLeDevices.add(i + 1, device);
				mLeDevices.remove(i);
				return;
			}
		}
		mLeDevices.add(device);

	}

	public iBeaconClass.iBeacon getDevice(int position) {
		return mLeDevices.get(position);
	}

	public void clear() {
		mLeDevices.clear();
	}

	@Override
	public int getCount() {
		return mLeDevices.size();
	}

	@Override
	public Object getItem(int i) {
		return mLeDevices.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		// General ListView optimization code.
		if (view == null) {
//			view = mInflator.inflate(R.layout.listitem_device, null);
			view = mInflator.inflate(R.layout.list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
			viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
			viewHolder.itemlayout = (LinearLayout) view.findViewById(R.id.itemlayout);
			viewHolder.cb = (CheckBox) view.findViewById(R.id.cb);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.cb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (viewHolder.cb.isChecked()){
					map.put(i,true);

				}else {
					map.remove(i);

				}
			}
		});
		if(map!=null&&map.containsKey(i)){
			viewHolder.cb.setChecked(true);
		}else {
			viewHolder.cb.setChecked(false);
		}

//		viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//				// TODO Auto-generated method stub
//				if (isChecked) {
//					map.put(i, isChecked);
//				} else {
//					map.remove(i);
//				}
//			}
//		});
//		viewHolder.cb.setChecked((map.get(i) == null ? false : true));



		iBeaconClass.iBeacon device = mLeDevices.get(i);
		final String deviceName = device.name;
		if (deviceName != null && deviceName.length() > 0)
			viewHolder.deviceName.setText(deviceName);
		else
			viewHolder.deviceName.setText(R.string.unknown_device);
		if (device.isIbeacon) {
			viewHolder.deviceName.append(" [iBeacon]");
		}

		viewHolder.deviceAddress.setText(device.bluetoothAddress);

		return view;
	}

	class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
		//		TextView deviceUUID;
//		TextView deviceMajor_Minor;
//		TextView devicetxPower_RSSI;
		LinearLayout itemlayout;
		CheckBox cb;
	}
}



