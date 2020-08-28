package com.yfy.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {

	private LayoutInflater inflater;
	private View view;
	private Context context;
	private int contentId;
	private int[] listennerId;

	private OnPopClickListenner listenner = null;

	public MyPopupWindow(Context context) {
		super(context);
	}

	public MyPopupWindow(Context context, int contentId, int[] listennerId) {
		this.context = context;
		this.contentId = contentId;
		this.listennerId = listennerId;
		inflater = LayoutInflater.from(context);
		view = inflater.inflate(contentId, null);
		init();
	}

	private void init() {
		setContentView(view);
		setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		setBackgroundDrawable(dw);
		setFocusable(true);
		setOutsideTouchable(true);

		if (listennerId == null)
			listennerId = new int[0];
		for (int i = 0; i < listennerId.length; i++) {
			view.findViewById(listennerId[i]).setOnClickListener(
					onClickListener);
		}
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (listenner != null) {
				listenner.onClick(v);
			}
		}
	};

	public void setOnPopClickListenner(OnPopClickListenner listenner) {
		this.listenner = listenner;
	}

	public static interface OnPopClickListenner {
		public void onClick(View view);
	}

}
