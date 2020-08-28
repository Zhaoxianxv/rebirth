package com.yfy.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;


public class BaseFragmentActivity extends FragmentActivity {
	private SparseArray<View> viewMaps = new SparseArray<View>();

	public void toastShow(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void toastShow(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}


	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		//初始化 ButterKnife
		ButterKnife.bind(this);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		ButterKnife.bind(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}


	protected void setOnClickListener(OnClickListener listener, View... views) {
		for (int i = 0; i < views.length; i++) {
			views[i].setOnClickListener(listener);
		}
	}

	protected void setOnClickListener(OnClickListener listener, int... resIds) {
		for (int i = 0; i < resIds.length; i++) {
			findViewById(resIds[i]).setOnClickListener(listener);
		}
	}


	protected View setViewVisibility(int resId, int visiblity) {
		View view = viewMaps.get(resId);
		if (view == null) {
			view = findViewById(resId);
			if (view != null) {
				viewMaps.put(resId, view);
				view.setVisibility(visiblity);
			}
		}
		return view;
	}

	protected View setTextViewText(int resId, String text) {
		View view = viewMaps.get(resId);
		if (view == null) {
			view = findViewById(resId);
			if (view != null) {
				viewMaps.put(resId, view);
			}
		}
		try {
			((TextView) view).setText(text);
			if (view.getVisibility() != View.VISIBLE) {
				view.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
		}
		return view;
	}

	protected View setTextViewText(int resId, int textId) {
		View view = viewMaps.get(resId);
		if (view == null) {
			view = findViewById(resId);
			if (view != null) {
				viewMaps.put(resId, view);
			}
		}
		try {
			((TextView) view).setText(textId);
			if (view.getVisibility() != View.VISIBLE) {
				view.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
		}
		return view;
	}
}
