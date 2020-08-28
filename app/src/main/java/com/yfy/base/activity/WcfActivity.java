/**
 *
 */
package com.yfy.base.activity;

import android.os.Bundle;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.manager.WcfManager;
import com.yfy.net.manager.WcfManager.OnWcfTaskListener;

/**
 * @author yfy1
 * @version 1.0
 * @Desprition
 */
public abstract class WcfActivity extends BaseActivity implements
		OnWcfTaskListener {

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
