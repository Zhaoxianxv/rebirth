package com.yfy.final_tag;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityManager {

	public static List<Activity> activity_list = new ArrayList<Activity>();

	public static void register(Activity activity) {
		activity_list.add(activity);
	}

	public static void closeAll() {
		for (Iterator<Activity> iterator = activity_list.iterator(); iterator.hasNext();) {
			Activity activity = iterator.next();
			activity.finish();
		}
	}
}
