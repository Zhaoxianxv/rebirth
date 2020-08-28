package com.yfy.view.mult;

import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class RoundImageDrawable extends Drawable {

	private final static String TAG = RoundImageDrawable.class.getSimpleName();

	private Paint mPaint;
	private Bitmap mBitmap;
	private int mRadiusPix;
	private RectF rectF;

	public RoundImageDrawable(Bitmap bitmap, int radiusPix) {
		mBitmap = bitmap;
		mRadiusPix = radiusPix;
		BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setShader(bitmapShader);
	}

	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		rectF = new RectF(left, top, right, bottom);
		Rect rect = new Rect(left, top, right, bottom);
		copyBounds(rect);
		Log.e(TAG, rectF.toString());
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(rectF, mRadiusPix, mRadiusPix, mPaint);
	}

	@Override
	public int getIntrinsicWidth() {
		Log.e(TAG, "mBitmap.getWidth()=" + mBitmap.getWidth());
		return mBitmap.getWidth();
	}

	@Override
	public int getIntrinsicHeight() {
		Log.e(TAG, "mBitmap.getHeight()=" + mBitmap.getHeight());
		return mBitmap.getHeight();
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

}
