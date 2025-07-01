package com.huntall.common.util;

/**
 * @author wangpeng
 * @since 2025年06月30日 17:51
 */
public class CommonUtil {

    /**
     * SDK时间解析
     *
     * @param time
     * @return
     */
    public static String parseTime(int time) {
        int year = (time >> 26) + 2000;
        int month = (time >> 22) & 15;
        int day = (time >> 17) & 31;
        int hour = (time >> 12) & 31;
        int min = (time >> 6) & 63;
        int second = (time) & 63;
        return year + "-" + month + "-" + day + "-" + hour + ":" + min + ":" + second;
    }

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
