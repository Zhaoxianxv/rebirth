package com.yfy.glide;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.io.File;


public class GlideTools {

    //获取bitmap
//    public Bitmap getBitmap(Context mContxt, String url) throws ExecutionException, InterruptedException {
//        Drawable bitmap = Glide.with(mContxt)
//                .load(url)
//                .into(100, 100) // Width and height
//                .get();
//        return bitmap;
//    }

    //加载视频（只支持本地视频）
    public void mp4(Context mContxt, String url, ImageView imageView){
        String filePath = "/storage/emulated/0/Pictures/example_video.mp4";
        Glide.with(mContxt)
                .load(Uri.fromFile(new File(url)))
                .into(imageView);
    }

    //如果缩略图和原图都是网络上的同一张图，这种方式效果不明显。
    public void getSamllimage(Context mContxt, String url, ImageView imageView){
        Glide.with( mContxt )
                .load( url )
                .thumbnail( 0.1f ) // 缩略图长宽相对于原始图片的比例
                .into( imageView );
    }


    //无效果
    public static void  loadImage(Context mContxt, String url, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .into(imageView);
    }
    //无效果
    public static void  loadImage(Context mContxt, int res, ImageView imageView){
        Glide.with(mContxt)
                .load(res)
                .into(imageView);
    }
    //居中裁剪
    public static void  loadImageCenterCrop(Context mContxt, String res, ImageView imageView){
        Glide.with(mContxt)
                .load(res)
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .into(imageView);
    }
    //居中裁剪
    public static void  loadImageCenterCrop(Context mContxt, int res, ImageView imageView){
        Glide.with(mContxt)
                .load(res)
                .apply(RequestOptions.bitmapTransform(new CenterCrop()))
                .into(imageView);
    }
    //现在实现模糊效果加圆角效果
    public static void jj(Context mContxt, String url, ImageView imageView){
        MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(25),
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.ALL));

        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(imageView);
    }
    /**
     * @param mContxt
     * @param url
     * @param imageView  CircleCrop
     * @param error  本地图片要与显示的样式相同  placeholder没有裁剪效果
     */
    public static void chanCircle(Context mContxt, String url, ImageView imageView,int error){
        Glide.with(mContxt)
                .load(url)
                .apply(new RequestOptions().placeholder(error).circleCrop())
                .into(imageView);
    }
    public static void chanCircle(Context mContxt, int url, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .apply(new RequestOptions().circleCrop())
                .into(imageView);
    }


    //黑白图
    public static void  chan(Context mContxt, String url, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new GrayscaleTransformation()))
                .into(imageView);
    }
    //加载 CircleCrop
    //CenterCrop()
    public static void chanc(Context mContxt, String url, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }
    public static void chanc(Context mContxt, String url,int erorr, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    //圆角图
    public static void  chanr(Context mContxt, String url, ImageView imageView){

        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(imageView);
    }
    public static void chanMult(Context mContxt, String url, ImageView imageView){
        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),
                new RoundedCorners(10));

        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(multi))
                .into(imageView);
    }

    //模糊效果
    public static void chanb(Context mContxt, String url, ImageView imageView){
        Glide.with(mContxt)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25)))
                .into(imageView);
    }
}
