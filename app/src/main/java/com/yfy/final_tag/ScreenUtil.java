package com.yfy.final_tag;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class ScreenUtil {

    public static final int mToolBarHeight = 48;

    public static int sScreenWidth;
    public static int sScreenHeight;
    public static float sScreenDensity;
    public static float sScreenPPI;
    public static float sScreenInch;
    public static int sActionBarSize;

    public static void init(Context context) {
        WindowManager windowManger = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        sScreenWidth = getScreenWidth(windowManger);
        sScreenHeight = getScreenHeight(windowManger);
        sScreenDensity = getScreenDensity(windowManger);
        sScreenPPI = getScreenPPI(windowManger);
        sScreenInch = getScreenInch();
        sActionBarSize = getActionBarSize(context);
    }

    public static int getActionBarSize(Context context) {
        return ScreenUtil.getPxByDp(48, context);
    }

    public static int getPxByDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    public static int getPxByDp(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    public static int getDpByPx(int px, Context context) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(float spValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(float pxValue, Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static float pxToSp(float px, Context context) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static int getScreenWidth(WindowManager windowManger) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManger.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private static int getScreenHeight(WindowManager windowManger) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManger.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    private static float getScreenDensity(WindowManager windowManger) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManger.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    private static float getScreenPPI(WindowManager windowManger) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManger.getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi;
    }

    private static float getScreenInch() {
        return (float) Math.sqrt((Math.pow(sScreenHeight, 2) + Math.pow(sScreenWidth, 2))) / sScreenPPI;

    }

    public static float getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static ViewGroup.LayoutParams getConcertCellLayoutPara(ViewGroup.LayoutParams layoutParams) {
        final int width = sScreenWidth;
        final int height = sScreenWidth / 2;
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

    public static ViewGroup.LayoutParams getConcertCellLayoutPara(ViewGroup.LayoutParams layoutParams, int minHeight) {
        final int width = ScreenUtil.sScreenWidth;
        int height = width / 2 > minHeight ? width / 2 : minHeight;
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

    public static ViewGroup.LayoutParams getSingerImageLayoutPara(ViewGroup.LayoutParams layoutParams) {
        final int width = sScreenWidth;
        final int height = sScreenWidth;
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

    public static void setScreenMode(Activity activity, boolean fullscreen) {
        if (fullscreen) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 获取屏幕中控件底部位置的高度--即控件底部的Y点
     *
     * @return
     */
    public static int getScreenViewBottomHeight(View view) {
        return view.getBottom();
    }

    public static int getScreenBottomNaviHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
