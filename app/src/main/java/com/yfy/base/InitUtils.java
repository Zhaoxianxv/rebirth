package com.yfy.base;

import android.app.Activity;
import android.text.TextUtils;
import com.yfy.final_tag.TagFinal;
import com.yfy.net.SoapAccessor;
import com.yfy.net.SoapAccessor.WcfConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class InitUtils {

	public static void init(Activity activity) {
		initWcfJson(activity);
		initWcf();
	}

	public static void initWcfJson(Activity activity) {
		if (!TextUtils.isEmpty(Base.wcfInfo)) {
			return;
		}
		BufferedReader reader = null;
		try {
			StringBuilder sb = new StringBuilder();
			InputStream inputStream = activity.getAssets().open(Constants.WCF_TXT);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String s = null;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			Base.wcfInfo = sb.toString();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void initWcf() {
		SoapAccessor.getInstance().unInit();
		WcfConfiguration configuration = new WcfConfiguration(
				Base.NAMESPACE, Base.URL, Base.NET_SOAP_ACTION,
				TagFinal.TIME_OUT, Base.wcfInfo);
		SoapAccessor.getInstance().init(configuration);
	}




}
