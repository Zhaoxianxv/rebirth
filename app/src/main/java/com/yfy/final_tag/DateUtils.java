package com.yfy.final_tag;

import android.annotation.SuppressLint;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeValue() {
		String rel = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);
		return rel;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeName() {
		String rel = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd");
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);
		return rel;
	}

	//
	public static String getDateTime(String type) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(type);
		return format.format(calendar.getTime());
	}



	public static long getLong(String da) {
		SimpleDateFormat currentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder sb=new StringBuilder();
		sb.append(da).append(" ").append("00:00:00");
		String date=sb.toString();
		Date time= null;
		try {
			time = currentTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time.getTime();
	}


	public static String getTime(long when) {
		Time time=new Time();
		time.set(when);
		return time.format("%H:%M:%S");
	}
	
	public static String getDateTime(long when) {
		Time time=new Time();
		time.set(when);
		return time.format("%Y-%m-%d %H:%M:%S");
	}
	

	//判断是否是今天
    public static boolean isToday(long when) {
        Time time = new Time();
        time.set(when);
        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }
    


//	获取yyyy/mm/dd格式日期
	public static String getDate(int year, int month, int day) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("/");
		int month2 = month ;
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

	//	获取yyyy年mm月 格式日期
	public static String getDate(int year, int month) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("年");
		int month2 = month + 1;
		if (month2 < 10) {
			sb.append("0").append(month2).append("月");
		} else {
			sb.append(month2).append("月");
		}

		return sb.toString();
	}
	//	获取yyyy年mm月dd日 格式日期
	public static String getDate2(int year, int month, int day) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("年");
		int month2 = month + 1;
		if (month2 < 10) {
			sb.append("0").append(month2).append("月");
		} else {
			sb.append(month2).append("月");
		}
		if (day < 10) {
			sb.append("0").append(day).append("");
		} else {
			sb.append(day).append("");
		}
		return sb.toString();
	}
	//	获取yyyy@mm@dd 格式日期
	public static String getDate(int year, int month, int day,String tag) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append(tag);
		int month2 = month + 1;
		if (month2 < 10) {
			sb.append("0").append(month2).append(tag);
		} else {
			sb.append(month2).append(tag);
		}
		if (day < 10) {
			sb.append("0").append(day).append("");
		} else {
			sb.append(day).append("");
		}
		return sb.toString();
	}



	public static String changeDate(String date,String type) {
		SimpleDateFormat currentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder sb=new StringBuilder();
		sb.append(date).append(" ").append("00:00:00");
		date=sb.toString();
		Date time= null;
		try {
			time = currentTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format = new SimpleDateFormat(type);
		return format.format(time);
	}
	public static String changeDate(String date) {
		SimpleDateFormat currentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder sb=new StringBuilder();
		sb.append(date).append(" ").append("00:00:00");
		date=sb.toString();
		Date time= null;
		try {
			time = currentTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(time);
	}
	public static String changeDate(String date,boolean b) {
		SimpleDateFormat currentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date time= null;
		try {
			time = currentTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd");
		return format.format(time);
	}

	public static String getWeek(String date) {
		SimpleDateFormat currentTime= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder sb=new StringBuilder();
		sb.append(date).append(" ").append("00:00:00");
		date=sb.toString();
		Date time= null;
		try {
			time = currentTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return getWeekOfDate(time);
	}
	/** * 获取指定日期是星期几

	 * 参数为null时表示获取当前日期是星期几

	 * @param date

	 * @return

	 */

	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar = Calendar.getInstance();
		if(date != null){
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return weekOfDays[w];
	}

}
