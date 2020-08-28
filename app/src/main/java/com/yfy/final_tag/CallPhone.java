package com.yfy.final_tag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallPhone {

//    打电话（权限）
    public static void callDirectly(Context mContext,String mobile){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        mContext.startActivity(intent);
    }

    /**
     * 开启安装未知来源权限
     */
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void toInstallPermissionSettingIntent() {
//        Uri packageURI = Uri.parse("package:"+getPackageName());
//        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
//        startActivityForResult(intent,TagFinal.UI_ADMIN);
//    }
}
