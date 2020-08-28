package com.yfy.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MyDialog extends AbstractDialog {

	private int layoutId;
	private int[] initId;
	private int[] listennerId;

	private View view;

	public MyDialog(Context context, int layoutId, int[] initId,
			int[] listennerId) {
		super(context);
		this.layoutId = layoutId;
		this.initId = initId;
		this.listennerId = listennerId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(layoutId);
		init();
	}

	private void init() {
		if (initId == null)
			initId = new int[0];
		for (int i = 0; i < initId.length; i++) {
			view = findViewById(initId[i]);
			map.put(initId[i], view);
		}
		if (listennerId == null)
			listennerId = new int[0];
		for (int i = 0; i < listennerId.length; i++) {
			map.get(listennerId[i]).setOnClickListener(onClickListener);
		}

		setCancelable(true);
		setCanceledOnTouchOutside(true);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		if (listener != null) {
			listener.init(this);
		}
	}

}
