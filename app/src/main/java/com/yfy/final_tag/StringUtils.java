package com.yfy.final_tag;


import android.annotation.SuppressLint;
import android.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class StringUtils {


	/**
	 * @return float 保留两位小数
	 */
	public static String getTwoTofloat(float f){
		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p=decimalFormat.format(f);//format 返回的是字符串
		return p;
	}


	//未读(%1$d)
	public static String getTextJoint(String type,Object... args){
		return String.format(type,args);
	}



	public static String subStr(List<String> list, String tag) {
		StringBuilder sb = new StringBuilder();
		for (String name : list) {
			sb.append(name).append(tag);
		}
		String result = sb.toString();
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}


	public static List<String> getListToString(String content,String tag){
		List<String> list = Arrays.asList(content.split(tag));
		return list;
	}

	public static String[] StringToArrays(String content,String tag){
		List<String> list = Arrays.asList(content.split(tag));

		return arraysToList(list);
	}

	public static String[] arraysToList(List<String> list){
		String[] se=new String[list.size()];
		return list.toArray(se);
	}



	//requst替换引号"
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
//                case '\\':
//                    sb.append("\\\\");
//                    break;
				default:
					sb.append(c);
					break;

			}
		}
		return sb.toString();
	}

	//    responece 替换\
	public static String string2Jso(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
//                case '\"':
//                    sb.append("\\\"");
//                    break;
				case '\\':
					sb.append("\\\\");
					break;
				default:
					sb.append(c);
					break;

			}
		}
		return sb.toString();
	}


	public static String getParamsXml(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s :list) {
			sb.append("<arr:string>")
					.append(s)
					.append("</arr:string>");
		}
		String resut = sb.toString();
		return resut;
	}


//    public static String string2Json(String s) {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < s.length(); i++) {
//
//            char c = s.charAt(i);
//            switch (c) {
//                case '\"':
//                    sb.append("\\\"");
//                    break;
//                case '\\':
//                    sb.append("\\\\");
//                    break;
//                case '/':
//                    sb.append("\\/");
//                    break;
//                case '\b':
//                    sb.append("\\b");
//                    break;
//                case '\f':
//                    sb.append("\\f");
//                    break;
//                case '\n':
//                    sb.append("\\n");
//                    break;
//                case '\r':
//                    sb.append("\\r");
//                    break;
//                case '\t':
//                    sb.append("\\t");
//                    break;
//                default:
//                    sb.append(c);
//            }
//        }
//        return sb.toString();
//    }


	public static String getDate(int year, int month, int day) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("/");
		int month2 = month + 1;
		if (month2 < 10) {
			sb.append("0").append(month2).append("/");
		} else {
			sb.append(month2).append("/");
		}
		if (day < 10) {
			sb.append("0").append(day);
		} else {
			sb.append(day);
		}
		return sb.toString();
	}

	public static String getDate2(int year, int month, int day) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("年");
		int month2 = month + 1;
		if (month2 < 10) {
			sb.append("0").append(month2).append("/");
		} else {
			sb.append(month2).append("月");
		}
		if (day < 10) {
			sb.append("0").append(day).append("日");
		} else {
			sb.append(day).append("日");
		}
		return sb.toString();
	}

//
//	public static String subClassIdStr(List<SchoolClass> list) {
//		StringBuilder sb = new StringBuilder();
//		for (SchoolClass schoolClass : list) {
//			sb.append(schoolClass.getClassid()).append(",");
//		}
//		String result = sb.toString();
//		if (result.length() > 0) {
//			result = result.substring(0, result.length() - 1);
//		}
//		return result;
//	}
//
//	public static String subNameStr(List<ChildBean> list) {
//		StringBuilder sb = new StringBuilder();
//		for (ChildBean contactMember : list) {
//			sb.append(contactMember.getUsername()).append(",");
//		}
//
//		String result = sb.toString();
//		if (result.length() > 0) {
//			result = result.substring(0, result.length() - 1);
//		}
//		return result;
//	}
//
//	public static String subIdStr(List<ChildBean> list) {
//		StringBuilder sb = new StringBuilder();
//		for (ChildBean contactMember : list) {
//			sb.append(contactMember.getUserid()).append(",");
//		}
//		String result = sb.toString();
//		if (result.length() > 0) {
//			result = result.substring(0, result.length() - 1);
//		}
//		return result;
//	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime() {
		String rel = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);
		return rel;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime2() {
		String rel = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);
		return rel;
	}


	/**
	 * 获取文件大小
	 */
	public static long getFileSize(String filePath) {
		long size = 0;
		File file = new File(filePath);
		if (file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 将子节数转换为Kb
	 */
	public static String convertBytesToOther(long byteSize) {
		String result = null;
		float size;

		DecimalFormat decimalFormat1 = new DecimalFormat(".0");
		DecimalFormat decimalFormat2 = new DecimalFormat(".00");

		if (byteSize < 1024) {
			result = byteSize + "B";
		} else {
			size = byteSize / 1024;
			if (size < 1024) {
				result = decimalFormat1.format(size) + "KB";
			} else {
				size = size / 1024;
				if (size < 1024) {
					result = decimalFormat2.format(size) + "M";
				} else {
					size = size / 1024;
					if (size < 1024) {
						result = decimalFormat2.format(size) + "G";
					}
				}
			}
		}
		return result;
	}

	public static int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}




	
}
