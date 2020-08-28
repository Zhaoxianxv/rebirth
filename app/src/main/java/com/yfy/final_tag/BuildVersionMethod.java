package com.yfy.final_tag;

import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.Html;
import com.yfy.base.App;
import com.yfy.base.Base;

import java.io.File;

/**
 * Created by yfyandr on 2018/5/7.
 * 用于区分不同版本间函数
 */

public class BuildVersionMethod {

    //判断是否是AndroidN以及更高的版本
    public static Uri getUri(File file){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return FileProvider.getUriForFile(App.getApp().getApplicationContext(), Base.AUTHORITY, file);
        }else{
            return Uri.fromFile(file);
        }
    }
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    //判断是否是AndroidN:Html.fromHtml以及更高的版本
    public static CharSequence getCharSequence(String htmlMsg){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlMsg,Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(htmlMsg);
        }
    }
    /**
     * 媒体库更新version判断
     */

    public static boolean isLaterKITKAT(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 5.0  imageView app:srcCompat
     */

//    代码中需要进行Drawable的实现类型转换时，可使用以下代码段执行：
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        VectorDrawable vectorDrawable =  (VectorDrawable) drawable;
//    } else {
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//    }

}
