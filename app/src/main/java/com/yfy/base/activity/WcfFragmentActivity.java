package com.yfy.base.activity;

import android.os.Bundle;
import com.yfy.base.BaseFragmentActivity;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.manager.WcfManager;
import com.yfy.net.manager.WcfManager.OnWcfTaskListener;

public abstract class WcfFragmentActivity extends BaseFragmentActivity
		implements OnWcfTaskListener {

	protected WcfManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWcf();
	}

	private void initWcf() {
		manager = new WcfManager(this);
	}

	public void execute(WcfTask wcfTask) {
		manager.execute(wcfTask);
	}
}
