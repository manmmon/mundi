package com.oval.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {
	public static Logger log = Logger.getLogger(DateUtil.class);

	public static String getDateString(Date date, String pattern) {
		return (new SimpleDateFormat(pattern)).format(date);
	}

	public static String getToday(String pattern) {
		return getBeforeDayAgainstToday(0, pattern);
	}

	public static String transform(String date, String oldPattern, String newPattern) {
		String result = "";
		try {
			Date d = (new SimpleDateFormat(oldPattern)).parse(date);
			result = getDateString(d, newPattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String getLastDay(String pattern) {
		return getBeforeDayAgainstToday(1, pattern);
	}

	public static String getTodayOfLastMonth() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -1);
		return getDateString(date.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取当天之前或之后的某天 负数代表前几天 正数表示后几天
	 * 
	 * @param userDefinedDays
	 * @return
	 */
	public static String getUserDefinedDay(int userDefinedDays) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, userDefinedDays);
		return getDateString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getUserDefinedMonthEndDayBeforeUserDefinedDay(int userDefinedMonths, int userDefinedDay) {
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, userDefinedMonths);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay);
		cal.add(Calendar.DAY_OF_YEAR, userDefinedDay);
		return sdf.format(cal.getTime());
	}

	public static String getBeforeDay(int days, String pattern, String date) {
		Date temp = null;
		try {
			temp = (new SimpleDateFormat(pattern)).parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return getBeforeDay(days, pattern, temp);
	}

	public static String getFirstDayOfMonth(String pattern) {
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		date = cal.getTime();
		return simple.format(date);
	}

	public static String getBeforeDay(int days, String pattern, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR) - days;
		cal.set(Calendar.DAY_OF_YEAR, day);
		return getDateString(cal.getTime(), pattern);
	}

	public static String getBeforeDayAgainstToday(int days, String pattern) {
		String date = getBeforeDay(days, pattern, new Date());
		return date.replaceAll("/", "%2F");
	}

	public static String getBeforeMonthAgainstToday(int Months, String pattern) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, Months);
		return getDateString(cal.getTime(), pattern);
	}

	public static String getBeforeMonthAgainstTodayBenginDay(int days, String pattern) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		int minDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), minDay);
		return getDateString(cal.getTime(), pattern);
	}

	public static String[] split(String text, String deli) {
		return text.split(deli);
	}

	public static String getLastMonthEndDay() {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getLastMonthBenginDay() {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getNowMonthBenginDay() {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getNowMonthBenginDay(String pattern) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	public static String getLastMonthEndDay(String pattern) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	public static String getLastMonthBenginDay(String pattern) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	public static String getUserDefinedMonthEndDay(String pattern, int userDefinedMonths) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, userDefinedMonths);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	public static String getUserDefinedMonthBenginDay(String pattern, int userDefinedMonths) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, userDefinedMonths);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	public static String getNowMonth(int i) {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1 - i;
		if (month <= 0) {
			if (month <= 0) {
				year = year - 1;
				month = 12 + month;
			}
		}
		if (month >= 10) {
			return year + "-" + month;
		} else {
			return year + "-0" + month;
		}
	}

	public static String getUserDefinedMonthEndDay(int userDefinedMonths) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, userDefinedMonths);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getUserDefinedMonthBenginDay(int userDefinedMonths) {
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 调到上个月
		cal.add(Calendar.MONTH, userDefinedMonths);
		// 按你的要求设置时间
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String getLongToString(String transDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentTime = Calendar.getInstance();
		Calendar taskEndTime = Calendar.getInstance();
		try {
			currentTime.setTime(sdf.parse("2013-04-03"));
			taskEndTime.setTime(sdf.parse(transDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String s = "1364947200000";
		long m = 86400000;
		long n = Long.parseLong(s);

		long currentmilliseconds = currentTime.getTimeInMillis();
		long taskmilliseconds = taskEndTime.getTimeInMillis();

		long diff = taskmilliseconds - currentmilliseconds;

		long diffDays = diff / (24 * 60 * 60 * 1000);
		long mid = n + m * diffDays;
		return String.valueOf(mid);
	}

	public static String getFirstOfMonth(String date_str, String pattern) throws Exception {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		cale.setTime(formatter.parse(date_str));
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return formatter.format(cale.getTime()); // 当月第一天 2019-02-01
	}

	public static String getLastDayOfMonth(String date_str, String pattern) throws Exception {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		cale.setTime(formatter.parse(date_str));
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return formatter.format(cale.getTime()); // 当月第一天 2019-02-01
	}

	public static String getFirstOfNextMonth(String date_str, String pattern) throws Exception {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		cale.setTime(formatter.parse(date_str));
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return formatter.format(cale.getTime()); // 当月第一天 2019-02-01
	}

	// 获取1990年到某天的总天数
	public static int getDays1(int years, int month, int days) {

		int yDays = 0;
		for (int i = 1900; i < years; i++) {
			if (i % 4 == 0 && i % 100 != 0 && i % 400 == 0) {
				yDays = yDays + 366;
			} else {
				yDays = yDays + 365;
			}
		}

		/* 月： 按照平年每个月的天数：31,28,31,30,31,30,31,31,30,31,30,31 */
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int mDays = 0;
		if (month == 1) {
			mDays = 0;
		} else {
			for (int i = 0; i < month - 1; i++) {// 减1，因为当月没过，按下面的日期来算
				mDays += months[i];// 累加当月之前的天数
			}

		}
		/* 4，日期： 天数直接相加 */
		int sum = yDays + mDays + days;
		return sum;
	}

	// 获取1990年到某天的总天数
	public static int getDays(int years, int month, int days) {

		/*
		 * 1，年：（闰年能将4整除，不能将100整除） 1.1，按平年算，每年365天 1.2，每个闰年加1天，若输入的那年是闰年，2月后，要加1天，反之不用
		 */
		int yDays = (years - 1900) * 365;

		int rDays = 0;// 每个润年加1
		for (int i = 1900; i <= years; i++) {
			if (i % 4 == 0 && i % 100 != 0) {// 判断是否为闰年
				rDays = rDays + 1;// 只要是闰年就加1
				if (i == years && month < 3) {// 若输入的年份为润，判断其是否过了2月，没过减1
					rDays = rDays - 1;
				}
			}
		}
		/*
		 * 2，月： 按照平年每个月的天数：31,28,31,30,31,30,31,31,30,31,30,31
		 */
		int[] months = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int mDays = 0;
		if (month == 1) {
			mDays = 0;
		} else {
			for (int i = 0; i < month - 1; i++) {// 减1，因为当月没过，按下面的日期来算
				mDays += months[i];// 累加当月之前的天数
			}
		}
		/*
		 * 4，日期： 天数直接相加
		 */

		int sum = yDays + rDays + mDays + days + 2;

		return sum;
	}

	/**
	 * 获取莫一天的时间戳
	 * 
	 * @param days
	 * @param pattern
	 * @return
	 */
	public static Long getBeforeDayTime(int days, String pattern) {
		String date = getBeforeDay(days, pattern, new Date()) + " 00:00:00";
		Long time = 1L;
		Date parse;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			time = parse.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;

	}

	public static void main(String[] args) {

		try {
			String aa = getBeforeMonthAgainstToday(-1, "yyyy-MM-dd");
			System.out.println(aa);
			String a = getFirstDayOfMonth("yyyy-MM-dd");
			System.out.println(a);
			String b = DateUtil.getBeforeDayAgainstToday(1, "yyyy-MM-dd");
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
