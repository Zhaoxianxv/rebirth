package com.yfy.jpush;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.google.gson.Gson;
import com.yfy.app.attennew.AttenNewActivity;
import com.yfy.app.login.LoginRebirthActivity;
import com.yfy.app.maintainnew.MaintainNewActivity;
import com.yfy.base.Base;
import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.TagFinal;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;


public class MyReceiver extends BroadcastReceiver {

	private NotificationManager manager;

	private static final String TAG = "JIGUANG-Example";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				//send the Registration Id to your server...
			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				processCustomMessage(context, bundle);
			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

				//打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					Logger.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.get(key));
			}
		}
		return sb.toString();
	}







	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		initBadge(++badge_num);
		if (true) {
			if(StringJudge.isNull(Base.user)){
				Intent i = new Intent(context, LoginRebirthActivity.class);
				i.putExtras(bundle);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);
				return;
			}
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Logger.e("zxx","推送 标签 "+extras);
			Gson gson=new Gson();
			JpushBean bean=gson.fromJson(extras,JpushBean.class);
			String type=bean.getDes();
			String pkgName = null;
			ActivityManager manager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE) ;
			List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;
			if(runningTaskInfos != null){
				pkgName = runningTaskInfos.get(0).topActivity.getClassName();
			}
			Logger.e("zxx","推送 标签 "+type);
			Logger.e("zxx","推送 pkgName "+pkgName);

			Intent i;
			switch (type){
				case "leadermail":
					//打开自定义的Activity
//					Intent i = new Intent(context, BoxMainActivity.class);
//					i.putExtras(bundle);
//					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//					context.startActivity(i);
					break;
				case "maintain":
					if(pkgName.equals("com.yfy.app.maintainnew.MaintainNewDetailAdminActivity"))return;
					if(pkgName.equals("com.yfy.app.maintainnew.MaintainNewAddActivity"))return;
					//打开自定义的Activity
					i= new Intent(context, MaintainNewActivity.class);
					i.putExtras(bundle);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(i);
					break;
				case "attendance":
					if(pkgName.equals("com.yfy.app.attennew.AttenNewDetailAdminActivity"))return;
					if(pkgName.equals("com.yfy.app.attennew.AttenAddActivity"))return;
					i = new Intent(context, AttenNewActivity.class);
					i.putExtras(bundle);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
					context.startActivity(i);
					break;
					default:
						break;
			}
		}
	}
	/**
	 * 构建通知栏
	 * @param context
	 * @param
	 */



	/**
	 * ***********************************应用小红点
	 */
//
//	private int badge_num=0;
//	private Handler handler = new Handler();
//
//	private Runnable runnable = new MyRunnable();
//
//	public class MyRunnable implements Runnable {
//
//		@Override
//		public void run() {
//			handler.postDelayed(runnable, 9000);
//			BadgeUtil.setBadgeCount(App.getApp().getApplicationContext(), getCount(), R.drawable.logo);
//		}
//	}
//
//	private int getCount() {
//
//		return badge_num;
//	}
//
//	public void initBadge(int num){
//		if (num!= TagFinal.ZERO_INT){
//			if (handler == null) {
//				handler = new Handler();
//			}
//			if (runnable == null) {
//				runnable = new MyRunnable();
//			}
//			handler.post(runnable);
//			Logger.e("zxx", "initBadge: "+num );
////			initBadge(0);
//		}else{
//			if (handler != null) {
//				badge_num=0;
//				BadgeUtil.resetBadgeCount(App.getApp().getApplicationContext(), R.drawable.logo);
//				handler.removeCallbacksAndMessages(null);
//				runnable = null;
//				handler = null;
//
//			}
//		}
//	}


}
