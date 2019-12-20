package com.hf.hf_smartcloud.utils.bluetooth;

import android.util.Log;

import com.hf.hf_smartcloud.bean.BleO9Bean;
import com.hf.hf_smartcloud.bean.BleOFBean;
import com.hf.hf_smartcloud.bean.Date31Bean;
import com.hf.hf_smartcloud.bean.RecordBean;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResultUtils {
    /**
     * 返回结果处理类
     * */

    /**========************START*****寄存器解析寄存器解析寄存器解析寄存器解析*********==============================*/

    /**
     * 07寄存器 解析
     */
    //07零点校正寄存器【读】over
    public static String registerRead07(String data07) {
        String sreresult07;
        long dec_num07;
        String result = null;
        if (data07.length() == 11) {
            //读
            sreresult07 = data07.substring(5, 9);
            Log.i("==sreresult07", "==sreresult07" + sreresult07);
            dec_num07 = Long.parseLong(sreresult07, 16);
            result = String.valueOf(dec_num07);
            result = "零点AD值:" + result;
        } else if (data07.length() == 9) {
            String two = Utils.To16_2(data07.substring(3, 5));
            Log.i("==two", "==two" + two);
            if (two.length() == 8) {
                result = "0";//在函数调用的地方判断   0为设置AD值成功，1为失败
            } else {
                result = "1";
            }
        }
        return result;
    }

    /**
     * 08寄存器 解析
     */
    //08满量程校正寄存器【读】over
    public static String registerRead08(String data08) {
        String sreresult08;
        long dec_num08;
        String result = null;
        if (data08.length() == 11) {
            sreresult08 = data08.substring(5, 9);
            Log.i("==sreresult08", "==sreresult08" + sreresult08);
            dec_num08 = Long.parseLong(sreresult08, 16);
            result = String.valueOf(dec_num08);
            result = "满量程:" + result;
        } else if (data08.length() == 9) {
            String two = Utils.To16_2(data08.substring(3, 5));
            Log.i("==two", "==two" + two);
            if (two.length() == 8) {
                result = "0";//在函数调用的地方判断   0为设置AD值成功，1为失败
            } else {
                result = "1";
            }
        }

        return result;
    }

    /**
     * 09寄存器 解析
     */
    //09所有设置参数over
    public static BleO9Bean register09(String data) {
        String sreresult;
        String strdec = null;//气体类型
        long dec_num, dec_num2, dec_num3, dec_num4, dec_num5, dec_num6, dec_num7, dec_num8, dec_num9;
        String strdec_num = null, strdec_num2 = null, strdec_num3 = null, strdec_num4 = null, strdec_num5 = null, strdec_num6 = null, strdec_num7 = null, strdec_num8 = null, strdec_num9 = null;
        double fl_dec_num4 = 0;
        String sreresult4;
        if (data.length() == 39) {
            sreresult = data.substring(5, 35);
            Log.i("##sreresult", "sreresult:::" + sreresult);
            /**一级报警*/
            String sreresult1 = sreresult.substring(0, 4);
            dec_num = Long.parseLong(sreresult1, 16);
            Log.i("##一级报警", "一级报警：" + dec_num);
            /**二级报警*/
            String sreresult2 = sreresult.substring(4, 8);
            dec_num2 = Long.parseLong(sreresult2, 16);
            Log.i("##二级报警", "二级报警：" + dec_num2);
            /**量程*/
            String sreresult3 = sreresult.substring(8, 12);
            dec_num3 = Long.parseLong(sreresult3, 16);
            Log.i("##量程", "量程：" + dec_num3);
            strdec_num3 = String.valueOf(dec_num3);
            /**分辨率*/
             sreresult4 = sreresult.substring(12, 14);
//            dec_num4 = Long.parseLong(sreresult4, 16);
            Log.i("##分辨率", "分辨率：" + sreresult4);
//            strdec_num4 = String.valueOf(dec_num4);
            if (sreresult4.equals("00")) {
                fl_dec_num4 = 1;
            } else if (sreresult4.equals("01")) {
                fl_dec_num4 = 0.1;
            } else if (sreresult4.equals("10")) {
                fl_dec_num4 = 0.01;
            }

            /**单位*/
            String sreresult5 = sreresult.substring(14, 16);
            dec_num5 = Long.parseLong(sreresult5, 16);
            Log.i("##单位", "单位：" + dec_num5);

            Log.i("##单位", "单位：" + dec_num5);
            if (dec_num5 == 00) {
                Log.i("##单位", "单位：" + "%vol");
                strdec_num5 = "%vol";
            } else if (dec_num5 == 01) {
                Log.i("##单位", "单位：" + "ppm");
                strdec_num5 = "ppm";
            } else if (dec_num5 == 02) {
                Log.i("##单位", "单位：" + "%LEL");
                strdec_num5 = "%LEL";
            }

            /**气体类型*/
            String sreresult6 = sreresult.substring(16, 18);
            dec_num6 = Long.parseLong(sreresult6, 16);
            String strdec_numz6 = String.valueOf(dec_num6);
            Log.i("##气体类型", "气体类型：" + dec_num6);
            strdec = Utils.gastypefun(sreresult6);
            /**零点矫正值*/
            String sreresult7 = sreresult.substring(18, 22);
            dec_num7 = Long.parseLong(sreresult7, 16);
            Log.i("##零点矫正值", "零点矫正值：" + dec_num7);
            strdec_num7 = String.valueOf(dec_num7);

            /**满量程矫正值*/
            String sreresult8 = sreresult.substring(22, 26);
            dec_num8 = Long.parseLong(sreresult8, 16);
            Log.i("##满量程矫正值", "满量程矫正值：" + dec_num8);
            strdec_num8 = String.valueOf(dec_num8);

            /**实时AD值*/
            String sreresult9 = sreresult.substring(26, 30);
            dec_num9 = Long.parseLong(sreresult9, 16);
            Log.i("##实时AD值", "实时AD值：" + dec_num9);
            strdec_num9 = String.valueOf(dec_num9);

            if(sreresult4.equals("00")){
                strdec_num = String.valueOf(new java.text.DecimalFormat("0").format(dec_num * fl_dec_num4));
                strdec_num2 = String.valueOf(new java.text.DecimalFormat("0").format(dec_num2 * fl_dec_num4));
            }else if(sreresult4.equals("01")){
                strdec_num = String.valueOf(new java.text.DecimalFormat("0.0").format(dec_num * fl_dec_num4));
                strdec_num2 = String.valueOf(new java.text.DecimalFormat("0.0").format(dec_num2 * fl_dec_num4));
            }else if(sreresult4.equals("10")){
                strdec_num = String.valueOf(new java.text.DecimalFormat("0.00").format(dec_num * fl_dec_num4));
                strdec_num2 = String.valueOf(new java.text.DecimalFormat("0.00").format(dec_num2 * fl_dec_num4));
            }
//            strdec_num = String.valueOf(dec_num * fl_dec_num4);
//            strdec_num2 = String.valueOf(dec_num2 * fl_dec_num4);
            Log.i("##!!!", "##!!!" + strdec_num + strdec_num2);
        }
//        String endresult = "一级报警:" + strdec_num + strdec_num5 + "\r\n" + "二级报警：" + strdec_num2 + strdec_num5 + "\r\n" + "量程：" + strdec_num3 + strdec_num5 + "\r\n" + "分辨率：" + strdec_num4 + "\r\n" + "单位：" + strdec_num5 + "\r\n" + "气体类型：" + strdec + "\r\n" + "零点校正值：" + strdec_num7 + "\r\n" + "满量程校正值：" + strdec_num8 + "\r\n" + "实时AD值：" + strdec_num9;
        BleO9Bean bleO9Bean = new BleO9Bean();
        bleO9Bean.setOnepolice(strdec_num + strdec_num5);//一级报警
        bleO9Bean.setOnepolice2(Float.valueOf(strdec_num));//一级报警不带单位
        bleO9Bean.setTwopolice(strdec_num2 + strdec_num5);//二级报警
        bleO9Bean.setRange(strdec_num3);//量程
        bleO9Bean.setRatio(fl_dec_num4);//分辨率strdec_num4
        bleO9Bean.setUnit(strdec_num5);//单位
        bleO9Bean.setGastype(strdec);//气体类型
        bleO9Bean.setZero(strdec_num7);//零点矫正值
        bleO9Bean.setFull(strdec_num8);//满量程校正值
        bleO9Bean.setRealtime(strdec_num9);///实时AD值
        return bleO9Bean;

    }

    /**
     * 0F寄存器 解析
     */
    //0F当前气体浓度值
    public static BleOFBean register0F(String data0F) {
        BleOFBean bleOFBean = new BleOFBean();
        String sreresult0F;//截取的结果
        long dec_num0F;//16进制转换后的值
        String strDec_num0F = null;//字符串值
        String unit = null;//单位
        String precision = null;//精度
        String concentration = null;//浓度值
        String state = null;//状态
        String gastype = null;//气体类型
        String result = null;//最终返回结果
        float ers = 0;
        String jingdu = null;
        float intresult = 0;
        String address = null;
        String strtwo = null;
        String addressLoc = null;
        if (data0F.length() == 17) {
            sreresult0F = data0F.substring(1, 3);
            if (sreresult0F.equals("01")) {
//                address = "可燃气";
                addressLoc = "01";
            } else if (sreresult0F.equals("02")) {
//                address = "氨气";
                addressLoc = "02";
            } else if (sreresult0F.equals("03")) {
//                address = "氧气";
                addressLoc = "03";
            } else if (sreresult0F.equals("04")) {
//                address = "二氧化硫";
                addressLoc = "04";
            } else if (sreresult0F.equals("05")) {
//                address = "一氧化碳";
                addressLoc = "05";
            } else if (sreresult0F.equals("06")) {
//                address = "硫化氢";
                addressLoc = "06";
            }
        }
        if (data0F.length() == 17) {
            sreresult0F = data0F.substring(5, 9);
            Log.i("==sreresult0F", "==sreresult0F" + sreresult0F);
//            dec_num0F = Long.parseLong(sreresult0F, 16);
            concentration = sreresult0F;//实时浓度浓度值

        }
        if (data0F.length() == 17) {
            sreresult0F = data0F.substring(9, 11);
            Log.i("==sreresult0F", "==sreresult0F" + sreresult0F);
            dec_num0F = Long.parseLong(sreresult0F, 16);
            strDec_num0F = String.valueOf(dec_num0F);
            gastype = Utils.gastypefun(sreresult0F);
        }
        if (data0F.length() == 17) {
            sreresult0F = data0F.substring(11, 13);
            Log.i("==sreresult0F", "==sreresult0F" + sreresult0F);
            /**
             * 转二进制*/
            int ten = Integer.parseInt(sreresult0F, 16);
            String two = Integer.toBinaryString(ten);
            Log.i("==two", "==two" + two);
            if (two.length() != 8) {
                strtwo = Utils.binary(two);
                String s = strtwo.substring(1, 4);
                state = Utils.statefun(s);

            }
            if (strtwo.length() == 8) {
                String s = strtwo.substring(4, 6);
                unit = Utils.unitfun(s);
            }
            if (concentration != null) {
                if (strtwo.length() == 8) {
                    String s = strtwo.substring(6, 8);
                    if (s.equals("00")) {
                        intresult = Float.parseFloat(concentration);
                        ers = Float.parseFloat(concentration);
                        jingdu = "00";
                    } else if (s.equals("01")) {
                        intresult = Float.parseFloat(concentration);
                        ers = intresult / 10;
                        jingdu = "01";
                    } else if (s.equals("10")) {
                        intresult = Float.parseFloat(concentration);
                        ers = intresult / 100;
                        jingdu = "10";
                    }
                }
            }
        }
//        result = "气体类型:" + gastype + "\t\n" + "实时浓度:" + ers + unit + "\t\n" + "状态：" + state;
        String con = null;
        float con2=0;
        if(jingdu.equals("00")){
             con = new java.text.DecimalFormat("0").format(ers) + unit;
             con2 = Float.parseFloat(new java.text.DecimalFormat("0.00").format(ers));
        }else if(jingdu.equals("01")){
            con = new java.text.DecimalFormat("0.0").format(ers) + unit;
            con2 = Float.parseFloat(new java.text.DecimalFormat("0.00").format(ers));
        }else if(jingdu.equals("10")){
            con = new java.text.DecimalFormat("0.00").format(ers) + unit;
            con2 = Float.parseFloat(new java.text.DecimalFormat("0.00").format(ers));
        }

        result = gastype + " " + con + " " + state;
        Log.i("==result", "==result:::" + result);
        bleOFBean.setAddressLoc(addressLoc);//起始位置地址
        bleOFBean.setQtname(gastype);//气体类型
        bleOFBean.setNongdu(con);//气体浓度
        bleOFBean.setNongdu2(con2);//气体浓度,不带单位
        bleOFBean.setBaojing(state);//气体状态
        bleOFBean.setUnit(unit);//气体单位
        bleOFBean.setJingdu(jingdu);//气体精度
//        ArrayList<BleOFBean> list = new ArrayList<>();
//        list.add(bleOFBean);
        return bleOFBean;
    }

    /**
     * 14寄存器 解析
     */
    //14开启显示
    public static boolean register14(String data14, String Wr) {
        String result14, result14rw;
        boolean isGone = false;
        if (data14.length() == 9) {
            if (Wr.equals("R")) {
                result14 = data14.substring(5, 7);
                Log.i("==result14", "==result14" + result14);
                if (result14.equals("00")) {
                    isGone = false;
                } else if (result14.equals("01")) {
                    isGone = true;
                }
            } else if (Wr.equals("W")) {
                result14rw = Utils.To16_2(data14.substring(4, 6));
                isGone = result14rw.length() != 8;
            }
        }
        return isGone;
    }

    /**
     * 15寄存器 解析
     */
    //程序版本
    public static String register15(String data15) {
        String result = null;
        String version = null;
        String date = null;
        if (data15.length() == 15) {
            double versionres = Double.parseDouble(data15.substring(6, 8)) * 0.1;
            version = String.valueOf(versionres);
            String datestr = data15.substring(8, 14);
            if (datestr.length() == 8) {
                int date2 = Integer.parseInt(datestr.substring(0, 4));
                String years = String.valueOf(date2 + 2000);
                String month = (datestr.substring(4, 6));
                String day = (datestr.substring(6, 8));
                date = years + "-" + month + "-" + day;
            }
        }
        result = "版本:" + version + "\t\n" + "时间:" + date;
        return result;
    }

    /**
     * 16寄存器 解析
     */
    //版本
    public static String register16(String data16) {
//        #01160106E2
        String result = null;
        String attribute = null;
        String type = null;
        if (data16.length() == 16) {
            String attributestr = data16.substring(6, 8);
            if (attributestr.equals("01")) {
                attribute = "彩屏";
            } else if (attributestr.equals("00")) {
                attribute = "黑白屏";
            }
            String typestr = data16.substring(8, 10);
            if (typestr != null && !typestr.equals("")) {
                if (typestr.equals("06")) {
                    type = "六合一";
                } else if (typestr.equals("05")) {
                    type = "五合一";
                } else if (typestr.equals("04")) {
                    type = "四合一";
                } else if (typestr.equals("03")) {
                    type = "三合一";
                } else if (typestr.equals("02")) {
                    type = "二合一";
                } else if (typestr.equals("01")) {
                    type = "一合一";
                }
            }
            result = "屏幕材质:" + attribute + "\t\n" + "气体类型总数:" + type;
        }
        return result;
    }

    /**
     * 17温湿度寄存器 解析
     */
    //17温湿度【未完成】
    public static String register17(String data17) {
        String result = null;
        String temp = null;
        String wetness = null;
        if (data17.length() == 17) {
            String strtemp = Utils.To16_10(data17.substring(5, 9));
            double strtemp2 = Double.parseDouble(strtemp) * 0.1;
            temp = String.valueOf(strtemp2);

            String wetnessstr = Utils.To16_10(data17.substring(9, 13));
            double wetnessstr2 = Double.parseDouble(wetnessstr) * 0.1;
            BigDecimal bg = new BigDecimal(wetnessstr2);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            wetness = String.valueOf(f1);
        }
        result = temp + " " + wetness;
        return result;
    }

    /**
     * 20报警记录条数 解析
     */
    //20报警记录条数【已完成】
    public static String register20(String data20) {
        String result = null;
        String coverCount = null;
        String orderNmb = null;
        String orderCount = null;
        if (data20.length() == 19) {
            coverCount = Utils.To16_10(data20.substring(6, 10));
            orderNmb = Utils.To16_10(data20.substring(10, 14));
            orderCount = Utils.To16_10(data20.substring(14, 18));
        }
        result = "覆盖次数:" + coverCount + "\t\n" + "存储序号:" + orderNmb + "\t\n" + "存储总数:" + orderCount;
        return result;
    }

    /**
     * 21读取报警记录 解析
     */
    /**
     * 21读取报警记录 解析
     */
    //21读取报警记录【】
    public static RecordBean register21(String data21) {
        RecordBean recordBean = null;
        ArrayList<RecordBean> resultList = new ArrayList<>();
        String Date = null;
        String recordtype = null;
        String gastype = null;
        String showvalue = null;
        String param = null;
        String state = null;
        String unit = null;
        String precision = null;
        double precisionf = 0;
//        if (data21.size() != 0) {

//            for (int i = 0; i < data21.size(); i++) {
        recordBean = new RecordBean();
        if (data21.length() == 31) {
            recordBean.setAddress(data21.substring(1, 3));
            Log.i("21寄存器:", "地址：" + data21.substring(1, 3));
            recordBean.setRegister(data21.substring(3, 5));
            Log.i("21寄存器:", "寄存器：" + data21.substring(3, 5));
            String datestr = data21.substring(5, 17);
            if (datestr.length() == 12) {
                String yearsstr = Utils.To16_10(datestr.substring(0, 2));
                int date2 = Integer.parseInt(yearsstr);
                String years = String.valueOf(date2 + 2000);
                String month = Utils.To16_10(datestr.substring(2, 4));
                String days = Utils.To16_10(datestr.substring(4, 6));
                String hour = Utils.To16_10(datestr.substring(6, 8));
                String points = Utils.To16_10(datestr.substring(8, 10));
                String seconds = Utils.To16_10(datestr.substring(10, 12));
                recordBean.setDate(years + "-" + month + "-" + days + " " + hour + ":" + points + ":" + seconds);
                Log.i("21寄存器:", "日期：" + years + "-" + month + "-" + days + " " + hour + ":" + points + ":" + seconds);
            }
            String recordtypestr = data21.substring(17, 19);
            if (recordtypestr.length() == 2) {
                if (recordtypestr.equals("00")) {
                    if (data21.substring(19, 21).equals("F0")) {
                        recordtype = "开机";
                    } else if (data21.substring(19, 21).equals("F1")) {
                        recordtype = "应警";
                    } else if (data21.substring(19, 21).equals("F2")) {
                        recordtype = "关机";
                        if (data21.substring(21, 23).equals("00")) {
                            gastype = "手动关机";
                        } else if (data21.substring(21, 23).equals("01")) {
                            gastype = "01低电量自动关机";
                        } else if (data21.substring(21, 23).equals("02")) {
                            gastype = "USB错误";
                        } else if (data21.substring(21, 23).equals("03")) {
                            gastype = "03充电错误";
                        } else if (data21.substring(21, 23).equals("04")) {
                            gastype = "04电源错误";
                        } else if (data21.substring(21, 23).equals("05")) {
                            gastype = "自动关机";
                        }

                    } else if (data21.substring(19, 21).equals("F3")) {
                        recordtype = "设置操作";
                    }
                } else {
                    if (recordtypestr.equals("01")) {
                        //气体报警
                        recordtype = "气体报警";
                        gastype = Utils.gastypefun(data21.substring(19, 21));
                    } else if (recordtypestr.equals("02")) {
                        //气体报警
                        recordtype = "气体报警";
                        gastype = Utils.gastypefun(data21.substring(19, 21));
                    } else if (recordtypestr.equals("03")) {
                        //气体报警
                        recordtype = "气体报警";
                        gastype = Utils.gastypefun(data21.substring(19, 21));
                    } else if (recordtypestr.equals("04")) {
                        //气体报警
                        recordtype = "气体报警";
                        gastype = Utils.gastypefun(data21.substring(19, 21));
                    }
                    float showvaluestr = Float.parseFloat((data21.substring(21, 25)));//报警值

                    String paramtwo = Utils.binary(Utils.To16_2(data21.substring(25, 27)));
                    if (paramtwo != null && paramtwo.length() == 8) {
                        state = Utils.statefun(paramtwo.substring(1, 4));//状态
                        unit = Utils.unitfun(paramtwo.substring(4, 6));//单位
                        precisionf = Utils.precisionfun2(paramtwo.substring(6, 8));//精度
                        precision = String.valueOf(precisionf);
                    }
                    showvalue = String.valueOf(showvaluestr * precisionf);//最终报警值
                }
            }
        }
        recordBean.setRecordtype(recordtype);
        Log.i("21寄存器:", "记录类型：" + recordtype);
        recordBean.setGastype(gastype);
        Log.i("21寄存器:", "气体类型：" + gastype);
        recordBean.setPrecision(precision);
        Log.i("21寄存器:", "精度：" + precision);
        recordBean.setState(state);
        Log.i("21寄存器:", "状态：" + state);
        recordBean.setUnit(unit);
        Log.i("21寄存器:", "单位：" + unit);
        recordBean.setShowvalue(showvalue);
        Log.i("21寄存器:", "显示值：" + showvalue);
        Log.i("21寄存器:", "==============================================================================================");

        return recordBean;
    }
    //21读取报警记录【】
//    public static ArrayList<RecordBean> register21(ArrayList<String> data21) {
//        RecordBean recordBean = null;
//        ArrayList<RecordBean> resultList = new ArrayList<>();
//        String Date = null;
//        String recordtype = null;
//        String gastype = null;
//        String showvalue = null;
//        String param = null;
//        String state = null;
//        String unit = null;
//        String precision = null;
//        double precisionf = 0;
//        if (data21.size() != 0) {
//
//            for (int i = 0; i < data21.size(); i++) {
//                recordBean = new RecordBean();
//                if (data21.get(i).length() == 31) {
//                    recordBean.setAddress(data21.get(i).substring(1, 3));
//                    Log.i("21寄存器:","地址："+data21.get(i).substring(1, 3));
//                    recordBean.setRegister(data21.get(i).substring(3, 5));
//                    Log.i("21寄存器:","寄存器："+data21.get(i).substring(3, 5));
//                    String datestr = data21.get(i).substring(5, 17);
//                    if (datestr.length() == 12) {
//                        String yearsstr = Utils.To16_10(datestr.substring(0, 2));
//                        int date2 = Integer.parseInt(yearsstr);
//                        String years = String.valueOf(date2+2000);
//                        String month = Utils.To16_10(datestr.substring(2, 4));
//                        String days = Utils.To16_10(datestr.substring(4, 6));
//                        String hour = Utils.To16_10(datestr.substring(6, 8));
//                        String points = Utils.To16_10(datestr.substring(8, 10));
//                        String seconds = Utils.To16_10(datestr.substring(10, 12));
//                        recordBean.setDate(years + "-" + month + "-" + days + " " + hour + ":" + points + ":" + seconds);
//                        Log.i("21寄存器:","日期："+years + "-" + month + "-" + days + " " + hour + ":" + points + ":" + seconds);
//                    }
//                    String recordtypestr = data21.get(i).substring(17, 19);
//                    if (recordtypestr.length() == 2) {
//                        if (recordtypestr.equals("00")) {
//                            if (data21.get(i).substring(19, 21).equals("F0")) {
//                                recordtype = "开机";
//                            } else if (data21.get(i).substring(19, 21).equals( "F1")) {
//                                recordtype = "应警";
//                            } else if (data21.get(i).substring(19, 21).equals("F2")) {
//                                recordtype = "关机";
//                                if(data21.get(i).substring(21, 23).equals("00")){
//                                    gastype = "手动关机";
//                                }else if(data21.get(i).substring(21, 23).equals("01")){
//                                    gastype = "01低电量自动关机";
//                                }else if(data21.get(i).substring(21, 23).equals("02")){
//                                    gastype = "USB错误";
//                                }else if(data21.get(i).substring(21, 23).equals("03")){
//                                    gastype = "03充电错误";
//                                }else if(data21.get(i).substring(21, 23).equals("04")){
//                                    gastype = "04电源错误";
//                                }else if(data21.get(i).substring(21, 23).equals("05")){
//                                    gastype = "自动关机";
//                                }
//
//                            } else if (data21.get(i).substring(19, 21).equals("F3")) {
//                                recordtype = "设置操作";
//                            }
//                        } else {
//                            if (recordtypestr.equals("01")) {
//                                //气体报警
//                                recordtype = "气体报警";
//                                gastype = Utils.gastypefun(data21.get(i).substring(19, 21));
//                            } else if (recordtypestr.equals("02")) {
//                                //气体报警
//                                recordtype = "气体报警";
//                                gastype = Utils.gastypefun(data21.get(i).substring(19, 21));
//                            } else if (recordtypestr.equals("03")) {
//                                //气体报警
//                                recordtype = "气体报警";
//                                gastype = Utils.gastypefun(data21.get(i).substring(19, 21));
//                            } else if (recordtypestr.equals("04")) {
//                                //气体报警
//                                recordtype = "气体报警";
//                                gastype = Utils.gastypefun(data21.get(i).substring(19, 21));
//                            }
//                            float showvaluestr = Float.parseFloat((data21.get(i).substring(21, 25)));//报警值
//
//                            String paramtwo = Utils.binary(Utils.To16_2(data21.get(i).substring(25, 27)));
//                            if (paramtwo != null && paramtwo.length() == 8) {
//                                state = Utils.statefun(paramtwo.substring(1, 4));//状态
//                                unit = Utils.unitfun(paramtwo.substring(4, 6));//单位
//                                precisionf = Utils.precisionfun2(paramtwo.substring(6, 8));//精度
//                                precision = String.valueOf(precisionf);
//                            }
//                            showvalue = String.valueOf(showvaluestr * precisionf);//最终报警值
//                            recordBean.setRecordtype(recordtype);
//                            Log.i("21寄存器:","记录类型："+recordtype);
//                            recordBean.setGastype(gastype);
//                            Log.i("21寄存器:","气体类型："+gastype);
//                            recordBean.setPrecision(precision);
//                            Log.i("21寄存器:","精度："+precision);
//                            recordBean.setState(state);
//                            Log.i("21寄存器:","状态："+state);
//                            recordBean.setUnit(unit);
//                            Log.i("21寄存器:","单位："+unit);
//                            recordBean.setShowvalue(showvalue);
//                            Log.i("21寄存器:","显示值："+showvalue);
//                            Log.i("21寄存器:","==============================================================================================");
//                        }
//                    }
//                }
//                resultList.add(recordBean);
//                Log.i("resultList:::","resultList:::"+resultList.size());
//            }
//        }
//        return resultList;
//    }

    /**
     * 22.清除报警记录 解析
     */
    //22.清除报警记录【】
    public static boolean register22(String data22) {
        boolean result = false;
        if (data22.length() == 9) {
            result = data22.equals("#01A2005D") || data22.equals("#02A2005C") || data22.equals("#03A2005B") || data22.equals("#04A2005A") || data22.equals("#05A20059") || data22.equals("#06A20058");
        }
        return result;
    }

    /**
     * 30.设置时间 解析
     */
    //30.设置时间【】
    public static boolean register30(String data30) {
        boolean result = false;
        if (data30.length() == 9) {
            result = data30.equals("#01B0004F") || data30.equals("#02B0004E") || data30.equals("#03B0004D") || data30.equals("#04B0004C") || data30.equals("#05B0004B") || data30.equals("#06B0004A");
        }
        return result;
    }


    /**========************END*****寄存器解析寄存器解析寄存器解析寄存器解析*********========================*/

    /**
     * 21读取报警记录 解析
     */


    //31生命周期时间
    static String chuchangDate;
    static String jiaozhunDate;
    static String jygenghuanDate;
    public static Date31Bean register31(String data31) {
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
    public static ArrayList<Integer> Decompress_Times(int a, int b, int c, int d, int e) {
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
    private static String dateOperation(String date1, String date2) {
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
     * 获取系统日期
     */
    public static String systemDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 获取系统日期+时分秒
     */
    public static String systemDate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 算标定更换时间
     */
    private static String TimeDifference() {
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
    private static Date31Bean scale(String life1, String life2, String life3) {
        Date31Bean dateBean1=new Date31Bean();
        dateBean1.setIntone(Integer.parseInt(life1.substring(0, life1.indexOf("天"))));
        dateBean1.setInttwo(Integer.parseInt(life2.substring(0, life2.indexOf("天"))));
        dateBean1.setIntthree(Integer.parseInt(life3.substring(0, life3.indexOf("天"))));
        dateBean1.setStrone(life1);
        dateBean1.setStrtwo(life2);
        dateBean1.setStrthree(life3);

        dateBean1.setChuchangDate(chuchangDate);
        dateBean1.setJiaozhunDate(jiaozhunDate);
        dateBean1.setJygenghuanDate(jygenghuanDate);

        return dateBean1;
    }
}
