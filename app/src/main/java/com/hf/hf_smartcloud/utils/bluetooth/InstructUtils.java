package com.hf.hf_smartcloud.utils.bluetooth;

import android.util.Log;

public class InstructUtils {

    /**
     * 07寄存器写操作
     * fixed固定值
     * 写入的值
     */
    public static String write07and08(String fixed, String write) {
        String result = null;
        String lrc = null;
        String strwrite = Utils.UpperCase(Utils.To10_16(write));
        if (strwrite.length() != 4) {
            if (strwrite.length() == 3) {
                strwrite = "0" + strwrite;
            } else if (strwrite.length() == 2) {
                strwrite = "00" + strwrite;
            } else if (strwrite.length() == 1) {
                strwrite = "000" + strwrite;
            }
        }
        String strresult = fixed + strwrite;
        Log.i("==strresult", "==strresult" + strresult);
        String lrcstr = Utils.UpperCase(Utils.makeChecksum(strresult));
        Log.i("==lrcstr", "==lrcstr" + lrcstr);
        result = ":" + strresult + lrcstr;
        return result;
    }

    //==============================================================================================
    public static String write30(String fixed, String datestr) {
        String result = null;
        String lrc = null;
        /**
         *  1.截取字符串
         *  2.字符串拼接
         *  3.Lrc算出校验码
         *  4.拼接指令
         *  5.返回正确的指令
         */
        int years = Integer.parseInt(datestr.substring(0, 4));
        String yearsstr = String.valueOf(years - 2000);
        String month = datestr.substring(4, 6);
        String days = datestr.substring(6, 8);
        String hour = datestr.substring(8, 10);
        String points = datestr.substring(10, 12);
//        String seconds = datestr.substring(12,14);
        String resultlrc = fixed + yearsstr + month + days + hour + points;
        String lrcstr = Utils.makeChecksum(resultlrc);
        boolean lrcbool = true;
        for (int i = 0; i < lrcstr.length(); i++) {
            char c = lrcstr.charAt(i);
            if (!Character.isUpperCase(c)) {
                lrcbool = false;
            }
        }
        if (lrcbool == false) {
            lrc = lrcstr.toUpperCase();
        }
        result = ":" + resultlrc + lrc;
        return result;
    }


}
