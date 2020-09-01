package com.yfy.final_tag;


import android.annotation.SuppressLint;
import android.media.ExifInterface;
import com.yfy.base.Base;
import com.yfy.jpush.Logger;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class StringUtils {

	//---------------------返回List<String>-----------------
	public static List<String> listToStringSplitCharacters(String content, String tag){
		List<String> list = Arrays.asList(content.split(Pattern.quote(tag)));
		return new ArrayList<>(list);
	}
	public static List<String> listToStringSplitCharactersHttp(String content,String tag,String http){
		List<String> list = Arrays.asList(content.split(Pattern.quote(tag)));
		List<String> data = new ArrayList<>();
		for (String s:list){
			data.add(http+s);
		}
		return data;
	}

	//---------------------返回String[]-----------------
	public static String[] arraysToStringSplitCharacters(String content, String tag){
		List<String> list = Arrays.asList(content.split(tag));
		return arraysToList(list);
	}

	public static String[] arraysToList(List<String> list){
		String[] se=new String[list.size()];
		return list.toArray(se);
	}
	public static byte[] arraysByteToString(String content ){
		return content.getBytes();
	}


	//---------------------返回String-----------------

	public static String stringToSpecialASCIICharacters(String name) {
		return Pattern.quote(name);
	}

	public static String stringToUpJson(String name) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '\n':
					sb.append("\\n");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}

	public static String stringToGetParamsXml(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s :list) {
			sb.append("<arr:string>")
					.append(s)
					.append("</arr:string>");
		}
		return  sb.toString();
	}

	public static String stringToImgToURlImg(String path){
		String title=path.substring(0, 4);
		if (title.equalsIgnoreCase("http")){
			return path;
		}else{
			return Base.RETROFIT_URI+path;
		}
	}

	public static String stringToGetTwoToDecimals(float f){
		DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(f);//format 返回的是字符串
	}

	public static String stringToGetTextJoint(String type, Object... args){
		return String.format(type,args);
	}
	public static String getTextJoint(String type, Object... args){
		return String.format(type,args);
	}

	public static String stringToArraysGetString(List<String> list, String tag) {
		StringBuilder sb = new StringBuilder();
		for (String name : list) {
			sb.append(name).append(Pattern.quote(tag));
		}
		String result = sb.toString();
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String stringToByteArrayGetString(byte[] encrypt ){
		StringBuilder sb = new StringBuilder();
		for (byte name : encrypt) {
			sb.append(name);
		}
		String result = sb.toString();
		if (StringJudge.isEmpty(result))result="";
		return result;
	}

}
