package com.yfy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import com.yfy.rebirth.R;


@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements OnFocusChangeListener,
		TextWatcher {
	// private final static String TAG = ClearEditText.class.getSimpleName();

	private Drawable mLeftDrawable, mClearDrawable;
	private int mLeftRes = -1, mRightRes = -1;

	private int lWidth = -1;
	private int lHeight = -1;
	private int rWidth = -1;
	private int rHeight = -1;

	private final static int LEFT = 3;
	private final static int RIGHT = 4;

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {

		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		intDrawableDimension(context, attrs);
		init();
	}

	private void intDrawableDimension(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ClearEditText);

		mLeftRes = ta.getResourceId(R.styleable.ClearEditText_l_drawable, -1);
		mRightRes = ta.getResourceId(R.styleable.ClearEditText_r_drawable, -1);

		lWidth = ta
				.getDimensionPixelSize(R.styleable.ClearEditText_l_Width, -1);
		lHeight = ta.getDimensionPixelSize(R.styleable.ClearEditText_l_Height,
				-1);

		rWidth = ta
				.getDimensionPixelSize(R.styleable.ClearEditText_r_Width, -1);
		rHeight = ta.getDimensionPixelSize(R.styleable.ClearEditText_r_Height,
				-1);

		ta.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

	}

	private void init() {
		mLeftDrawable = getDrawable(mLeftRes, LEFT);
		mClearDrawable = getDrawable(mRightRes, RIGHT);

		if (mLeftDrawable != null) {
			mLeftDrawable.setBounds(0, 0, lWidth, lHeight);
		}

		if (mClearDrawable != null) {
			mClearDrawable.setBounds(0, 0, rWidth, rHeight);
		}
		setClearIconVisible(false);
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	private Drawable getDrawable(int resId, int location) {
		if (resId < 0) {
			return null;
		}

		Drawable drawable = getResources().getDrawable(resId);
		switch (location) {
		case LEFT:
			drawable.setBounds(0, 0, lWidth, lHeight);
			break;
		case RIGHT:
			drawable.setBounds(0, 0, rWidth, rHeight);
			break;
		}
		return drawable;
	}

	/**
	 * ��Ϊ���ǲ���ֱ�Ӹ�EditText���õ���¼������������ü�ס���ǰ��µ�λ����ģ�����¼� �����ǰ��µ�λ�� �� EditText�Ŀ�� -
	 * ͼ�굽�ؼ��ұߵļ�� - ͼ��Ŀ�� �� EditText�Ŀ�� - ͼ�굽�ؼ��ұߵļ��֮�����Ǿ�������ͼ�꣬��ֱ����û�п���
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (getCompoundDrawables()[2] != null) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				boolean touchable = event.getX() > (getWidth()
						- getPaddingRight() - rWidth)
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				if (touchable) {
					this.setText("");
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private boolean isClearIconShow;

	public void setClearIconShow(Boolean b) {
		isClearIconShow = true;
	}

	/**

	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (isClearIconShow) {
			return;
		}

		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	/**

	 * 
	 * @param visible
	 */
	protected void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(mLeftDrawable, getCompoundDrawables()[1], right,
				getCompoundDrawables()[3]);
	}

	/**

	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		setClearIconVisible(s.length() > 0);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	/**

	 */
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	/**
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

}
