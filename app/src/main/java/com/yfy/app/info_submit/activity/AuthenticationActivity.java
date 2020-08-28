package com.yfy.app.info_submit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.yfy.app.WebActivity;
import com.yfy.app.info_submit.constants.InfosConstant;
import com.yfy.app.info_submit.utils.JsonStrParser;
import com.yfy.base.activity.WcfActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.ConvertObjtect;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import com.yfy.jpush.Logger;
import com.yfy.net.loading.interf.WcfTask;
import com.yfy.net.loading.task.ParamsTask;
import com.yfy.rebirth.R;

import butterknife.Bind;
import butterknife.OnClick;

public  class AuthenticationActivity extends WcfActivity {

	private static final String TAG = AuthenticationActivity.class.getSimpleName();
	@Bind(R.id.baobao_edit)
	EditText baobao_et;
	@Bind(R.id.mishi_et)
	EditText mishi_et;

	private String baobaoName;
	private String tel_number;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_authentication);
		initSQtoolbar();
	}
	private void initSQtoolbar() {
		assert toolbar != null;
		toolbar.setTitle("招生");

	}



	@OnClick(R.id.point)
	void setWeb(){
		Intent intent = new Intent(mActivity, WebActivity.class);
		Bundle b = new Bundle();
		b.putString(TagFinal.URI_TAG, TagFinal.POINT_PATH);
		b.putString(TagFinal.TITLE_TAG, "招生须知");
		intent.putExtras(b);
		startActivity(intent);
	}
	@OnClick(R.id.authente)
	void setSubmit(){
		baobaoName = baobao_et.getText().toString().trim();
		tel_number = mishi_et.getText().toString().trim();

		if (StringJudge.isEmpty(baobaoName) || StringJudge.isEmpty(tel_number)) {
			Toast.makeText(AuthenticationActivity.this, "请输入完整信息",Toast.LENGTH_SHORT).show();;
		} else {
			getBmcx();
		}
	}






	private void getBmcx(){
		Object[] params = new Object[] {
				baobaoName,
				tel_number
		};
		ParamsTask refreshTask = new ParamsTask(params,TagFinal.AUTHEN_BMCX, TagFinal.AUTHEN_BMCX);
		execute(refreshTask);
		showProgressDialog("正在加载");
	}
	private void getStuxx(String id){
		Object[] params = new Object[] {
				id
		};
		ParamsTask refreshTask = new ParamsTask(params,TagFinal.AUTHEN_GET_STU, TagFinal.AUTHEN_GET_STU);
		execute(refreshTask);
	}
	@Override
	public boolean onSuccess(String result, WcfTask wcfTask) {
		if (!isActivity())return false;
		Logger.e(result );
		String name=wcfTask.getName();
		if (name.equals(TagFinal.AUTHEN_BMCX)){
			if(ConvertObjtect.getInstance().getInt(result)==-1){
				toast(result);
				dismissProgressDialog();
			}else{
				getStuxx(result);
			}

		}
		if (name.equals(TagFinal.AUTHEN_GET_STU)){
			dismissProgressDialog();
			InfosConstant.grade = JsonStrParser.getFenBan(result);
			InfosConstant.totalList = JsonStrParser.getStuXx(result);
			Intent intent = new Intent(mActivity, FormShowActivity.class);
			startActivity(intent);
		}
		return false;
	}

	@Override
	public void onError(WcfTask wcfTask) {
		if (!isActivity())return ;
		dismissProgressDialog();
	}

	@Override
	public boolean isActivity() {
		return AppLess.isTopActivy(TAG);
	}
}
