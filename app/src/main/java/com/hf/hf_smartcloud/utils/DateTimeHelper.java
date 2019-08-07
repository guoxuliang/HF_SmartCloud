package com.hf.hf_smartcloud.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @类名称: DateTimeHelper
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @作者 fengxian
 * @日期 2013-9-6 下午1:46:21
 * 
 */
public class DateTimeHelper {

    public static String formatAsUtc(Date aDate, String aFormat) {
        SimpleDateFormat formater = new SimpleDateFormat(aFormat);
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formater.format(aDate);
    }

    public static String formatAsISO8601WithDefaultTimeZone(Date aDate) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return formater.format(aDate);
    }

    public static String systemTimeNYR() {
        String date = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            date = sDateFormat.format(new Date());
            return date;
        } catch (Exception e) {
        }
        return date;

    }
    // 将时间格式字符川转换成时间
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
