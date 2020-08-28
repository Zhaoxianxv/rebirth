package com.yfy.final_tag;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.Selection;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewTools {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue,
                context.getResources().getDisplayMetrics());
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dipValue,
                context.getResources().getDisplayMetrics());
    }

    public static void releaseImageView(ImageView iv) {
        try {
            if (iv != null) {
                iv.setImageResource(0);
            }
        } catch (Throwable t) {
        }
    }

    public static void releaseBitmap(Bitmap bitmap) {
        try {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Throwable t) {
        }
    }

    public static void setImageResource(ImageView iv, int res) {
        try {
            if (iv != null) {
                iv.setImageResource(res);
            }
        } catch (Throwable t) {
        }
    }

    public static void setImageBitmap(ImageView iv, Bitmap bitmap) {
        try {
            if (iv != null && bitmap != null) {
                iv.setImageBitmap(bitmap);
            }
        } catch (Throwable t) {
        }
    }

    public static void setTextColor(TextView tv, int res) {
        if (Utils.isNull(tv)) return;
        tv.setTextColor(tv.getResources().getColor(res));
    }

    public static String getText(TextView tv) {
        if (Utils.isNull(tv)) return "";
        return tv.getText().toString();
    }

    /**
     * 首页在线直播Tag的背景
     */
    public static Drawable setRandomBackground(int bgColor) {
        // 外部矩形弧度
        float[] outerR = new float[]{0, 0, 0, 0, 0, 0, 10, 10};
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        //指定填充颜色
        drawable.getPaint().setColor(bgColor);
        // 指定填充模式
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }



    /**
     * 设置输入框的光标到末尾
     */
    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection(editable, editable.toString().length());
    }

    /**
     * 設置SwipeRefreshLayout颜色
     */
    public static void setSwipeRefreshLayoutColor(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(
               ColorRgbUtil.getBaseColor(),
                ColorRgbUtil.getOrangeRed(),
                ColorRgbUtil.getBaseColor(),
                ColorRgbUtil.getBaseColor());
    }

}
