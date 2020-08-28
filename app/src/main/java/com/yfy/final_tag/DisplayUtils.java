package com.yfy.final_tag;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Aj Liao on 2016/1/13.
 */
public class DisplayUtils {


    private static int screenWidth = -1;
    private static int screenHeight = -1;


    /**
     * 获取设备的mac地址
     * 这里要特别说明一下,mac地址不是一定能获取的到的,你可能要更优先使用设备ID
     *
     * @return mac地址
     */
    public static String $mac() {

        String result = null;
        try {
            String path = "sys/class/net/wlan0/address";
            if ((new File(path)).exists()) {
                FileInputStream fis = new FileInputStream(path);
                byte[] buffer = new byte[8192];
                int byteCount = fis.read(buffer);
                if (byteCount > 0) {
                    result = new String(buffer, 0, byteCount, "utf-8");
                }
            }
            if (TextUtils.isEmpty(result)) {
                path = "sys/class/net/eth0/address";
                FileInputStream fis_name = new FileInputStream(path);
                byte[] buffer_name = new byte[8192];
                int byteCount_name = fis_name.read(buffer_name);
                if (byteCount_name > 0) {
                    result = new String(buffer_name, 0, byteCount_name, "utf-8");
                }
            }

            if (TextUtils.isEmpty(result)) {
                WifiManager wifi = (WifiManager) $.sAppContext.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifi.getConnectionInfo();
                result = wifiInfo.getMacAddress();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }

    /**
     * 获取屏幕分辨率宽度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            screenHeight = dm.heightPixels;
        }
        return screenHeight;
    }
}
