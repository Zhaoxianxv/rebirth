package com.yfy.json;


import com.yfy.app.attennew.bean.LeaveType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonParser {
	public static List<LeaveType> getLeaveTypeList(String json) {
		List<LeaveType> leaveTypeList = new ArrayList<LeaveType>();
		try {
			JSONObject obj = new JSONObject(json);
			JSONArray typeArray = obj.getJSONArray("kqtype");
			for (int i = 0; i < typeArray.length(); i++) {
				JSONObject item = typeArray.getJSONObject(i);
				leaveTypeList.add(new LeaveType(item.getString("typeid"), item.getString("typename")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return leaveTypeList;
	}

	public static boolean isSuccess(String json) {
		try {
			if (json.equals("true")) {
				return true;
			}
			JSONObject obj = new JSONObject(json);
			String result = obj.getString("result");
			if (result.equals("true")) {
				return true;
			}
			String[] str = result.split("\\|");
			if (str[0].equals("true")) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public static String getErrorCode(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return obj.getString("error_code");
		} catch (JSONException e) {
			e.printStackTrace();
			return "网络错误！";
		}
	}


}
