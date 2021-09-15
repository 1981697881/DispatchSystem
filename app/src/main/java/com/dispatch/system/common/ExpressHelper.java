package com.dispatch.system.common;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 快递判断帮助类
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/12
 */
public class ExpressHelper {

    /**
     * 根据运单号获取是哪个快递公司
     */
    public static String getExpressName(String expressNum) {
        if (!TextUtils.isEmpty(expressNum)) {
            char[] chars = expressNum.toCharArray();
            int len = expressNum.length();
            if ((len == 14 && chars[0] == '7') ||
                    ((len == 15 || len == 12 || len == 13) && chars[0] == '5')) {
                return "中通快递";
            } else if (chars[0] == 'J' && chars[1] == 'T') {
                return "极兔快递";
            } else if (len == 13 && (chars[0] == '3' || chars[0] == '4'
                    || chars[0] == '6' || chars[0] == '7' || chars[0] == '8')) {
                return "韵达快递";
            } else if (len > 2 && ((chars[0] == 'Y' && chars[1] == 'T') ||
                    (chars[0] == 'D' && chars[1] == 'D') || chars[0] == 'G')) {
                return "圆通快递";
            } else if ((len == 12 && chars[0] == '1') ||
                    (len == 15 && chars[0] == '7')) {
                return "申通快递";
            }
        }
        return "无法识别快递公司";
    }
    public static String getExpressKey(String expressNum) {
        if (!TextUtils.isEmpty(expressNum)) {
            char[] chars = expressNum.toCharArray();
            int len = expressNum.length();
            if ((len == 14 && chars[0] == '7') ||
                    ((len == 15 || len == 12 || len == 13) && chars[0] == '5')) {
                return "ZTO";
            } else if (chars[0] == 'J' && chars[1] == 'T') {
                return "JT";
            } else if (len == 13 && (chars[0] == '3' || chars[0] == '4'
                    || chars[0] == '6' || chars[0] == '7' || chars[0] == '8')) {
                return "YD";
            } else if (len > 2 && ((chars[0] == 'Y' && chars[1] == 'T') ||
                    (chars[0] == 'D' && chars[1] == 'D') || chars[0] == 'G')) {
                return "YT";
            } else if ((len == 12 && chars[0] == '1') ||
                    (len == 15 && chars[0] == '7')) {
                return "STO";
            }
        }

        return "QT";
    }

    public static String formatTime(long time) {
        return DateFormat.format("HH:mm", time).toString();
    }

    /**
     * 时间戳转成年月日时分秒形式
     *
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp) {
        return getTime(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间戳转成年月日形式
     *
     * @param timestamp
     * @return
     */
    public static String getDate(long timestamp) {
        return getTime(timestamp, "yyyy-MM-dd");
    }

    /**
     * 时间戳转成年月日时分秒形式
     *
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(timestamp);
    }
}
