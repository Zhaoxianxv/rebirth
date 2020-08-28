package com.yfy.final_tag;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Aj Liao
 */
public class RatingUtils {

    public static void rating(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + AppLess.$appname()));
            activity.startActivity(intent);
        } catch (Throwable t) {
            //toast("您的手机上没有安装Android应用市场");
        }
    }
}
