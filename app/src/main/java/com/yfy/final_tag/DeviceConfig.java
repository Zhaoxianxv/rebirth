package com.yfy.final_tag;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;

public class DeviceConfig {

    public static String sDeviceID;
    public static String sDeviceName;
    public static String sDeviceOSVersion;
    public static String sAppversion;


    public static void init(Context context) {
        sDeviceID = getDeviceID(context);
        sDeviceName = getDeviceName(context);
        sDeviceOSVersion = getOSVersion(context);
        sAppversion = getAppVersion(context);
    }

    private static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String getDeviceName(Context context) {
        return Build.MODEL;
    }

    private static String getOSVersion(Context context) {
        return Build.VERSION.RELEASE;
    }

    private static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
