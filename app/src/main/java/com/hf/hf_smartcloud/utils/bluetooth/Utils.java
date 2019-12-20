/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hf.hf_smartcloud.utils.bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class Utils {

	private static HashMap<Integer, String> serviceTypes = new HashMap();
	static {
		// Sample Services.
		serviceTypes.put(BluetoothGattService.SERVICE_TYPE_PRIMARY, "PRIMARY");
		serviceTypes.put(BluetoothGattService.SERVICE_TYPE_SECONDARY,
				"SECONDARY");
	}

	public static String getServiceType(int type) {
		return serviceTypes.get(type);
	}

	// -------------------------------------------
	private static HashMap<Integer, String> charPermissions = new HashMap();
	static {
		charPermissions.put(0, "UNKNOW");
		charPermissions
				.put(BluetoothGattCharacteristic.PERMISSION_READ, "READ");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED,
				"READ_ENCRYPTED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM,
				"READ_ENCRYPTED_MITM");
		charPermissions.put(BluetoothGattCharacteristic.PERMISSION_WRITE,
				"WRITE");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED,
				"WRITE_ENCRYPTED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM,
				"WRITE_ENCRYPTED_MITM");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED,
				"WRITE_SIGNED");
		charPermissions.put(
				BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM,
				"WRITE_SIGNED_MITM");
	}

	public static String getCharPermission(int permission) {
		return getHashMapValue(charPermissions, permission);
	}

	// -------------------------------------------
	private static HashMap<Integer, String> charProperties = new HashMap();
	static {

		charProperties.put(BluetoothGattCharacteristic.PROPERTY_BROADCAST,
				"BROADCAST");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS,
				"EXTENDED_PROPS");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_INDICATE,
				"INDICATE");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_NOTIFY,
				"NOTIFY");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_READ, "READ");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE,
				"SIGNED_WRITE");
		charProperties.put(BluetoothGattCharacteristic.PROPERTY_WRITE, "WRITE");
		charProperties.put(
				BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE,
				"WRITE_NO_RESPONSE");
	}

	public static String getCharPropertie(int property) {
		return getHashMapValue(charProperties, property);
	}

	// --------------------------------------------------------------------------
	private static HashMap<Integer, String> descPermissions = new HashMap();
	static {
		descPermissions.put(0, "UNKNOW");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ, "READ");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED,
				"READ_ENCRYPTED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM,
				"READ_ENCRYPTED_MITM");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE, "WRITE");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED,
				"WRITE_ENCRYPTED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED_MITM,
				"WRITE_ENCRYPTED_MITM");
		descPermissions.put(BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED,
				"WRITE_SIGNED");
		descPermissions.put(
				BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM,
				"WRITE_SIGNED_MITM");
	}

	public static String getDescPermission(int property) {
		return getHashMapValue(descPermissions, property);
	}

	private static String getHashMapValue(HashMap<Integer, String> hashMap,
			int number) {
		String result = hashMap.get(number);
		if (TextUtils.isEmpty(result)) {
			List<Integer> numbers = getElement(number);
			result = "";
			for (int i = 0; i < numbers.size(); i++) {
				result += hashMap.get(numbers.get(i)) + "|";
			}
		}
		return result;
	}

	/**
	 * 位运算结果的反推函数10 -> 2 | 8;
	 */
	static private List<Integer> getElement(int number) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < 32; i++) {
			int b = 1 << i;
			if ((number & b) > 0)
				result.add(b);
		}

		return result;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String bytesToString(byte[] src) {
		String res = new String(src);
		Log.i("res", "res====="+res);
		return res;
	}

	// HexString����>byte
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
//====================================================================================================
	/**
	 * 10进制转换为16进制
	 * */
	public static String To10_16(String str) {
		int intstr = Integer.parseInt(str);
		String strHex = Integer.toHexString(intstr);
		return strHex;
	}
	/**
	 * 16进制格式化输出
	 * */
	public static String FormaTting16(String str){
		//将十六进制格式化输出
		String strHex2 = String.format("%08x",str);
		return strHex2;
	}

	/**
	 * 10进制转换为2进制
	 * */
	public static String To10_2(int str) {
		String s = Integer.toBinaryString(str);
		return s;
	}
	/**
	 * 16进制转换为2进制
	 * */
	public static String To16_2(String str) {
		int ten = Integer.parseInt(str, 16);
		String two = Integer.toBinaryString(ten);
		return two;
	}
	/**
	 * 16进制转换为10进制
	 * */
	public static String To16_10(String str) {
		long dec_num = Long.parseLong(str, 16);
		String swc = String.valueOf(dec_num);
		return swc;
	}
	/**标气浓度指令算法*/
	public static double concentration(double a,double b,double c,double d,double e) {
		double known = a*(d-e)+(b*c);
		double x = known/60;
		return x;
	}

	/**
	 * 16进制转换为10进制  返回double类型
	 * */
	public static double To16_10doub(String str) {
		long dec_num = Long.parseLong(str, 16);
		double swc = dec_num;
		return swc;
	}
	/**
	 *
	 * <p>华氏度  = 32 + 摄氏度 × 1.8。</p>
	 * @param degree 需要转换的温度
	 * @param scale 保留的小数位
	 * @return
	 */
	public static double centigrade2Fahrenheit(double degree,int scale) {
		double d = 32 + degree * 1.8;
		return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 *
	 * <p>摄氏度  = (华氏度 - 32) ÷ 1.8。</p>
	 * @param degree 需要转换的温度
	 * @param scale 保留的小数位
	 * @return
	 */
	public static double fahrenheit2Centigrade(double degree, int scale) {
		double d = (degree - 32) / 1.8;
		return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/**
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static String date(String date){
		return date.replaceAll("[[\\s-:punct:]]","");
	}


	/**
	 * 30寄存器时间处理函数
	 * */
	public static String write30() {
		String result = null;
		SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		Log.i("==curDate","==curDate"+formatter.format(curDate));
		String date = formatter.format(curDate);
		try {
			String datec = dateToStamp(date);
			Log.i("==curDate","==curDate"+datec);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result = formatter.format(curDate);
		String aa = date(result);
		Log.i("aa","aa:"+aa);
		return aa;
	}

	/**
	 * 判断是否为小写，为小写的时候转为大写
	 * */
	public static String UpperCase(String str){
		boolean lrcbool=true;
		String result = null;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isUpperCase(c)) {
				lrcbool = false;
			}
		}
		if (lrcbool == false) {
			result = str.toUpperCase();
		}
		return result;
	}

	/**
	 * 判断字符串是否为十六进制
	 */
	public static boolean isHexNumber(String str) {
		boolean flag = false;
		for (int i = 0; i < str.length(); i++) {
			char cc = str.charAt(i);
			if (cc == '0' || cc == '1' || cc == '2' || cc == '3' || cc == '4' || cc == '5' || cc == '6' || cc == '7' || cc == '8' || cc == '9' || cc == 'A' || cc == 'B' || cc == 'C' ||
					cc == 'D' || cc == 'E' || cc == 'F' || cc == 'a' || cc == 'b' || cc == 'c' || cc == 'c' || cc == 'd' || cc == 'e' || cc == 'f') {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 字符串两两加空格
	 * string s = "FE680100111111116811043533B3377C16"
	 * FE 68 01 00 11 11 11 11 68 11 04 35 33 B3 37 7C 16
	 */
	public static String strSpace(String str) {
		String result = null;
		result =  str.replaceAll(".{2}(?!$)", "$0 ");
		return result;
	}

	/**
	 * 16进制LRC校验
	 */
	public static String makeChecksum(String data) {

		if (data == null || data.equals("")) {
			return "";
		}
		int total = 0;
		int len = data.length();
		int num = 0;
		while (num < len) {
			String s = data.substring(num, num + 2);
			System.out.println(s);
			total += Integer.parseInt(s, 16);
			num = num + 2;
		}
		/**
		 * 用256求余最大是255，即16进制的FF
		 */
		char LRC;
		int mod = total % 256;
		LRC = (char) (0xFF - mod);
		LRC = (char) (LRC + 1);
		Log.i("LRC", "LRC" + LRC);
		String hex = Integer.toHexString(LRC);
		len = hex.length();
		// 如果不够校验位的长度，补0,这里用的是两位校验
		if (len < 2) {
			hex = "0" + hex;
		}
//        int lrc = Integer.parseInt(hex);
//        char lrc2 = (char) (0x0ff-lrc);
		return hex;
	}



	/**
	 * 二进制补位
	 */
	public static String binary(String strBinart) {
		String strtwo = null;
		if (strBinart.length() == 0) {
			strtwo = "00000000";
		} else if (strBinart.length() == 1) {
			strtwo = "0000000" + strBinart;
		} else if (strBinart.length() == 2) {
			strtwo = "000000" + strBinart;
		} else if (strBinart.length() == 3) {
			strtwo = "00000" + strBinart;
		} else if (strBinart.length() == 4) {
			strtwo = "0000" + strBinart;
		} else if (strBinart.length() == 5) {
			strtwo = "000" + strBinart;
		} else if (strBinart.length() == 6) {
			strtwo = "00" + strBinart;
		} else if (strBinart.length() == 7) {
			strtwo = "0" + strBinart;
		}
		return strtwo;
	}

	/**
	 * 状态
	 */
	public static String statefun(String s) {
		String state_str = null;
		if (s.equals("000")) {
			state_str = "正常";
		} else if (s.equals("001")) {
			state_str = "1级报警";
		} else if (s.equals("010")) {
			state_str = "2级报警";
		} else if (s.equals("011")) {
			state_str = "传感器故障或者未接";
		} else if (s.equals("100")) {
			state_str = "超限报警";
		}
		return state_str;
	}

	/**
	 * 单位
	 */
	public static String unitfun(String s) {
		String unit_str = null;
		if (s.equals("00")) {
			unit_str = "%vol";
		} else if (s.equals("01")) {
			unit_str = "ppm";
		} else if (s.equals("10")) {
			unit_str = "%LEL";
		}
		return unit_str;
	}

	/**
	 * 精度
	 */
	public static float precisionfun(String s) {
		String precision_str = null;
		if (s.equals("00")) {
			precision_str = s;
		} else if (s.equals("01")) {
			int ss = Integer.parseInt(s);
			precision_str = String.valueOf(ss * 0.1);
		} else if (s.equals("10")) {
			int ss = Integer.parseInt(s);
			precision_str = String.valueOf(ss * 0.01);
		}else {

		}
		return Float.parseFloat(precision_str);
	}


	public static double precisionfun2(String s) {
		double precision_str = 0;
		if (s.equals("00")) {
			precision_str = 1;
		} else if (s.equals("01")) {
			precision_str =  0.1;
		} else if (s.equals("10")) {
			int ss = Integer.parseInt(s);
			precision_str = 0.01;
		}else {

		}
		return precision_str;
	}
	/**
	 * 气体类型
	 */
	public static String gastypefun(String strDec_num0F) {
		String gastype_str = null;
		if (strDec_num0F.equals("00")) {
			Log.i("##气体类型", "气体类型：" + "O2 氧气");
			gastype_str = "O2 氧气";
		} else if (strDec_num0F.equals("01")) {
			Log.i("##气体类型", "气体类型：" + "CO 一氧化碳");
			gastype_str = "CO 一氧化碳";
		} else if (strDec_num0F.equals("02")) {
			Log.i("##气体类型", "气体类型：" + "H2S 硫化氢");
			gastype_str = "H2S 硫化氢";
		} else if (strDec_num0F.equals("03")) {
			Log.i("##气体类型", "气体类型：" + "NH3 氨气");
			gastype_str = "NH3 氨气";
		} else if (strDec_num0F.equals("04")) {
			Log.i("##气体类型", "气体类型：" + "H2 氢气");
			gastype_str = "H2 氢气";
		} else if (strDec_num0F.equals("05")) {
			Log.i("##气体类型", "气体类型：" + "CL2 氯气");
			gastype_str = "CL2 氯气";
		} else if (strDec_num0F.equals("06")) {
			Log.i("##气体类型", "气体类型：" + "SO2 二氧化硫");
			gastype_str = "SO2 二氧化硫";
		} else if (strDec_num0F.equals("07")) {
			Log.i("##气体类型", "气体类型：" + "NO 一氧化氮");
			gastype_str = "NO 一氧化氮";
		} else if (strDec_num0F.equals("08")) {
			Log.i("##气体类型", "气体类型：" + "NO2 二氧化氮");
			gastype_str = "NO2 二氧化氮";
		} else if (strDec_num0F.equals("09")) {
			Log.i("##气体类型", "气体类型：" + "CH2O 甲醛");
			gastype_str = "CH2O 甲醛";
		} else if (strDec_num0F.equals("0A")) {
			Log.i("##气体类型", "气体类型：" + "O3 臭氧");
			gastype_str = "O3 臭氧";
		} else if (strDec_num0F.equals("0B")) {
			Log.i("##气体类型", "气体类型：" + "Ex 可燃气");
			gastype_str = "Ex 可燃气";
		} else if (strDec_num0F.equals("0C")) {
			Log.i("##气体类型", "气体类型：" + "HF 氟化氢");
			gastype_str = "HF 氟化氢";
		} else if (strDec_num0F.equals("0D")) {
			Log.i("##气体类型", "气体类型：" + "CFC 氟利昂");
			gastype_str = "CFC 氟利昂";
		} else if (strDec_num0F.equals("0E")) {
			Log.i("##气体类型", "气体类型：" + "F2 氟气");
			gastype_str = "F2 氟气";
		} else if (strDec_num0F.equals("0F")) {
			Log.i("##气体类型", "气体类型：" + "CO2 二氧化碳");
			gastype_str = "CO2 二氧化碳";
		} else if (strDec_num0F.equals("10")) {
			Log.i("##气体类型", "气体类型：" + "CH 甲烷");
			gastype_str = "CH 甲烷";
		} else if (strDec_num0F.equals("11")) {
			Log.i("##气体类型", "气体类型：" + "C6H6 苯");
			gastype_str = "C6H6 苯";
		} else if (strDec_num0F.equals("12")) {
			Log.i("##气体类型", "气体类型：" + "C2H4O 环氧乙烷");
			gastype_str = "C2H4O 环氧乙烷";
		} else if (strDec_num0F.equals("13")) {
			Log.i("##气体类型", "气体类型：" + "CH4O4S 硫酸甲酯");
			gastype_str = "CH4O4S 硫酸甲酯";
		} else if (strDec_num0F.equals("14")) {
			Log.i("##气体类型", "气体类型：" + "CH3OH 甲醇");
			gastype_str = "CH3OH 甲醇";
		} else if (strDec_num0F.equals("15")) {
			Log.i("##气体类型", "气体类型：" + "CH3OH_Ex 甲醇测爆");
			gastype_str = "CH3OH_Ex 甲醇测爆";
		} else if (strDec_num0F.equals("16")) {
			Log.i("##气体类型", "气体类型：" + "CH3CH2OH 乙醇");
			gastype_str = "CH3CH2OH 乙醇";
		} else if (strDec_num0F.equals("17")) {
			Log.i("##气体类型", "气体类型：" + "Natural 天然气");
			gastype_str = "Natural 天然气";
		} else if (strDec_num0F.equals("18")) {
			Log.i("##气体类型", "气体类型：" + "C7H8 甲苯");
			gastype_str = "C7H8 甲苯";
		} else if (strDec_num0F.equals("19")) {
			Log.i("##气体类型", "气体类型：" + "H2_Ex 氢气测爆");
			gastype_str = "H2_Ex 氢气测爆";
		} else if (strDec_num0F.equals("1A")) {
			Log.i("##气体类型", "气体类型：" + "Benzene 粗苯");
			gastype_str = "Benzene 粗苯";
		} else if (strDec_num0F.equals("1B")) {
			Log.i("##气体类型", "气体类型：" + "PH3 磷化氢");
			gastype_str = "PH3 磷化氢";
		} else if (strDec_num0F.equals("1C")) {
			Log.i("##气体类型", "气体类型：" + "NxOx 氮氧化物");
			gastype_str = "NxOx 氮氧化物";
		} else if (strDec_num0F.equals("1D")) {
			Log.i("##气体类型", "气体类型：" + "HCL 氯化氢");
			gastype_str = "HCL 氯化氢";
		}
		return gastype_str;
	}
	String chuchangDate;
	String jiaozhunDate;
	String jygenghuanDate;
	public ArrayList<Integer> initYS(String data31) {
		ArrayList<Integer> ReadDate2 = new ArrayList<>();
		int date01to10 = Integer.parseInt(data31.substring(5, 7), 16);
		int date02to10 = Integer.parseInt(data31.substring(7, 9), 16);
		int date03to10 = Integer.parseInt(data31.substring(9, 11), 16);
		int date04to10 = Integer.parseInt(data31.substring(11, 13), 16);
		int date05to10 = Integer.parseInt(data31.substring(13, 15), 16);
		ReadDate2 = Decompress_Times(date01to10, date02to10, date03to10, date04to10, date05to10);
		chuchangDate = (ReadDate2.get(0) + 2000) + "-" + (ReadDate2.get(1)) + "-" + (ReadDate2.get(2));
		jiaozhunDate = (ReadDate2.get(3) + 2000) + "-" + (ReadDate2.get(4));
		jygenghuanDate = (ReadDate2.get(5) + 2000) + "-" + (ReadDate2.get(6)) + "-" + (ReadDate2.get(7));
		Log.i("==gxl时间:::", "出厂时间:::" + chuchangDate + "\r\n" + "校准时间:::" + jiaozhunDate + "\r\n" + "建议更换时间：" + jygenghuanDate);

		String strd = systemDate();
		/**设备使用时间*/
		if (strd != null && chuchangDate != null) {
//            dateOperation(strd,chuchangDate);
			Log.i("==gxl设备使用时间:::", "设备使用时间:::" + dateOperation(strd, chuchangDate));
		}
		/**下一次标定时间*/
		Log.i("==gxl下一次标定时间:::", "下一次标定时间:::" + TimeDifference());

		/**寿命*/
		Log.i("==gxl寿命:::", "寿命:::" + dateOperation(jygenghuanDate, strd));

		return scale(dateOperation(strd, chuchangDate), TimeDifference(), dateOperation(jygenghuanDate, strd));
	}

	/**=====================================================================================================*/
	/**
	 * 解压时间
	 */
	public ArrayList<Integer> Decompress_Times(int a, int b, int c, int d, int e) {
		ArrayList<Integer> Dst_Msg = new ArrayList<>();
		//年 0-63
		Dst_Msg.add((e >> 2) & 0x3f);
		//月
		Dst_Msg.add((((e << 2) & 0x0c) | ((d >> 6) & 0x03)) & 0x0f);
		//日
		Dst_Msg.add((d >> 1) & 0x1f);
		//年
		Dst_Msg.add((((d << 5) & 0x20) | ((c >> 3) & 0x1f)) & 0x3f);
		//月
		Dst_Msg.add((((c << 1) & 0x0e) | ((b >> 7) & 0x01)) & 0x0f);
		//年
		Dst_Msg.add((b >> 1) & 0x3f);
		//月
		Dst_Msg.add((((b << 3) & 0x08) | ((a >> 5) & 0x07)) & 0x0f);
		//日
		Dst_Msg.add(a & 0x1f);

		return Dst_Msg;
	}

	/**
	 * 算时间差
	 */
	private String dateOperation(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long diff;
		long days = 0;
		long hours = 0;
		long minutes = 0;
		try {
			Date d1 = df.parse(date1);//系统时间
			Date d2 = df.parse(date2);//三大时间
			diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);

			hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
			System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
			Log.i("systemDate:::", "systemDate:::" + days + "天" + hours + "小时" + minutes + "分");
		} catch (Exception e) {
		}
		return days + "天" + hours + "小时" + minutes + "分";
	}

	/**
	 * 获取系统时间
	 */
	private String systemDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String str = simpleDateFormat.format(date);
		return str;
	}

	/**
	 * 算标定更换时间
	 */
	private String TimeDifference() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");

		Date date3 = new Date();//获取当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date3);
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
		calendar.add(Calendar.MONTH, 6);//当前时间前去一个月，即一个月前的时间
		calendar.getTime();//获取一年前的时间，或者一个月前的时间
		String nextBdDate = simpleDateFormat.format(calendar.getTime());
		String daysCount = dateOperation(nextBdDate + "-01", systemDate());
		Log.i("systemDate:::", "nextBdDate:::" + nextBdDate + "systemDate():::" + systemDate());
		return daysCount;
	}
	/**
	 * 时间总和
	 */
	private ArrayList<Integer> scale(String life1, String life2, String life3) {
		ArrayList<Integer> proportion = new ArrayList<>();
		proportion.add(Integer.parseInt(life1.substring(0, life1.indexOf("天"))));
		proportion.add(Integer.parseInt(life2.substring(0, life2.indexOf("天"))));
		proportion.add(Integer.parseInt(life3.substring(0, life3.indexOf("天"))));
		return proportion;
	}
}
