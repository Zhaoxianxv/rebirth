/**
 * 
 */
package com.yfy.final_tag;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * @version 1.0
 * @Desprition
 */
public abstract class ViewUtils {

	public static int getNumColumn(GridView gridView) {
		Class<GridView> tempGridView = GridView.class;
		int column = -1;
		try {
			Field field = tempGridView.getDeclaredField("mRequestedNumColumns"); //
			field.setAccessible(true); //
			column = Integer.valueOf(field.get(gridView).toString()); //
		} catch (Exception e1) {
			return 0;
		}

		if (column == -1) {
			return 0;
		}
		return column;
	}

	public  static int getHorizontalSpacing(GridView gridView) {
		Class<GridView> tempGridView = GridView.class;
		int spacing = -1;
		try {
			Field field = tempGridView
					.getDeclaredField("mRequestedHorizontalSpacing"); //
			field.setAccessible(true); //
			spacing = Integer.valueOf(field.get(gridView).toString()); //
		} catch (Exception e1) {
			return 0;
		}

		if (spacing == -1) {
			return 0;
		}
		return spacing;
	}


	public static ColorStateList getColorStateList(Context context, int resId) {
		ColorStateList colorStateList = null;
		try {
			XmlResourceParser xrp = context.getResources().getXml(resId);
			colorStateList = ColorStateList.createFromXml(context.getResources(), xrp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return colorStateList;
	}

	public static Drawable getDrawable(Context context, int resId, Resources.Theme theme) {
		if (Build.VERSION.SDK_INT >= 21) {
			return getDrawable21(context, resId, theme);
		} else {
			return context.getResources().getDrawable(resId);
		}
	}

	@TargetApi(21)
	private static Drawable getDrawable21(Context context, int resId, Resources.Theme theme) {
		return context.getResources().getDrawable(resId);
	}

	@SuppressWarnings("deprecation")
	public static StateListDrawable newSelector(Context context, int resId, int percent) {
		StateListDrawable bg = new StateListDrawable();
		Bitmap sourceImg = BitmapFactory.decodeResource(context.getResources(), resId);
		Bitmap handlerImg = getTransparentBitmap(sourceImg, percent);
		bg.addState(new int[] { android.R.attr.state_pressed }, new BitmapDrawable(handlerImg));
		bg.addState(new int[] {}, new BitmapDrawable(sourceImg));
		return bg;
	}

	private static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// ���ͼƬ��ARGBֵ
		number = number * 255 / 100;

		int hh = -1;
		for (int i = 0; i < argb.length; i++) {
			hh = argb[i] >> 24;
			if (hh != 0) {
				argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
			}

		}
		Bitmap handlerImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);

		return handlerImg;
	}

	public static int getViewWidth(ImageView imageView, int maxImageWidth) {
		final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

		final ViewGroup.LayoutParams params = imageView.getLayoutParams();
		int width = (params != null && params.width == ViewGroup.LayoutParams.WRAP_CONTENT) ? 0 : imageView.getWidth(); // Get
		// actual
		// image
		// width
		if (width <= 0 && params != null)
			width = params.width; // Get layout width parameter
		if (width <= 0)
			width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
		// maxWidth
		// parameter
		if (width <= 0)
			width = maxImageWidth;
		if (width <= 0)
			width = displayMetrics.widthPixels;

		return width;
	}

	public static int getViewHeight(ImageView imageView, int maxImageHeight) {

		final DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
		final ViewGroup.LayoutParams params = imageView.getLayoutParams();
		int height = (params != null && params.height == ViewGroup.LayoutParams.WRAP_CONTENT) ? 0 : imageView.getHeight(); // Get
		// actual
		// image
		// height
		if (height <= 0 && params != null)
			height = params.height; // Get layout height parameter
		if (height <= 0)
			height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
		// maxHeight
		// parameter
		if (height <= 0)
			height = maxImageHeight;
		if (height <= 0)
			height = displayMetrics.heightPixels;

		return height;
	}

	private static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = (Integer) field.get(object);
			if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		} catch (Exception e) {
		}
		return value;
	}




	public static void clearEdgeFlowDrawable(View view) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
			if (view instanceof AbsListView) {
				clearBlueEdge(AbsListView.class, view);
			} else if (view instanceof ScrollView) {
				clearBlueEdge(ScrollView.class, view);
			}
		}
	}

	@TargetApi(9)
	public static void clearBlueEdge(Class<?> clazz, View view) {
		try {
			Class<?> c = (Class<?>) Class.forName(clazz.getName());
			Field egtField = c.getDeclaredField("mEdgeGlowTop");
			Field egbBottom = c.getDeclaredField("mEdgeGlowBottom");
			egtField.setAccessible(true);
			egbBottom.setAccessible(true);
			Object egtObject = egtField.get(view); // this

			Object egbObject = egbBottom.get(view);

			Class<?> cc = (Class<?>) Class.forName(egtObject.getClass().getName());
			Field mGlow = cc.getDeclaredField("mGlow");
			mGlow.setAccessible(true);
			mGlow.set(egtObject, new ColorDrawable(Color.TRANSPARENT));
			mGlow.set(egbObject, new ColorDrawable(Color.TRANSPARENT));

			Field mEdge = cc.getDeclaredField("mEdge");
			mEdge.setAccessible(true);
			mEdge.set(egtObject, new ColorDrawable(Color.TRANSPARENT));
			mEdge.set(egbObject, new ColorDrawable(Color.TRANSPARENT));
		} catch (Exception e) {
		}
	}

}
