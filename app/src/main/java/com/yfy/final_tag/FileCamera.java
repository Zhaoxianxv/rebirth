package com.yfy.final_tag;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import com.yfy.base.Base;

import java.io.File;

/**
 * Created by yfyandr on 2017/9/12.
 */

public class FileCamera {


    private Activity context;
    public static String photo_camera;
    public FileCamera(Activity context) {
        this.context = context;
    }


    public Intent takeCamera(){
        photo_camera= Environment.getExternalStorageDirectory().toString() + "/yfy/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        FileTools.createFile(photo_camera);
        File file = new File(photo_camera);
        Uri mOutPutFileUri ;
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mOutPutFileUri = FileProvider.getUriForFile(context, Base.AUTHORITY, file);
        }else{
            mOutPutFileUri = Uri.fromFile(file);
        }
        //这里进行替换uri的获得方式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag
        return intent;
    }
}
