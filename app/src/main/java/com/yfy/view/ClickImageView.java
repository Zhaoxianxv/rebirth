package com.yfy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.yfy.rebirth.R;


/**
 * @author yfy1
 *
 */
public class ClickImageView extends ImageView {

	// private final static String TAG = ClickImageView.class.getSimpleName();

	private final static int QPAQUE = 255;
	private float alpha = 255;
	private int imageRes;

	public ClickImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAttrs(context, attrs);
	}

	private void initAttrs(final Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ClickImageView);

		alpha = ta.getInteger(R.styleable.ClickImageView_alpha, QPAQUE);
		imageRes = ta.getResourceId(R.styleable.ClickImageView_image_src, 0);
		ta.recycle();
		if (imageRes != 0) {
			post(new Runnable() {
				@Override
				public void run() {
					Drawable drawable = newSelector(getContext(), imageRes, alpha);
					setImageDrawable(drawable);
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	public StateListDrawable newSelector(Context context, int resId, float percent) {
		StateListDrawable bg = new StateListDrawable();
		Bitmap sourceImg = BitmapFactory.decodeResource(context.getResources(),
				resId);
		Bitmap handlerImg = getTransparentBitmap(sourceImg, percent);
		bg.addState(new int[] { android.R.attr.state_pressed },
				new BitmapDrawable(handlerImg));
		bg.addState(new int[] {}, new BitmapDrawable(sourceImg));
		return bg;
	}


	private Bitmap getTransparentBitmap(Bitmap sourceImg, float number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0,
				sourceImg.getWidth(), sourceImg.getHeight());
		int hh = -1;
		for (int i = 0; i < argb.length; i++) {
			hh = argb[i] >> 24;
			if (hh != 0) {
				argb[i] = ((int)number << 24) | (argb[i] & 0x00FFFFFF);
			}
		}
		Bitmap handlerImg = Bitmap.createBitmap(argb, sourceImg.getWidth(),
				sourceImg.getHeight(), Config.ARGB_8888);
		return handlerImg;
	}

}
