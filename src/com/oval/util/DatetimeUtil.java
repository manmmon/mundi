package com.oval.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 系统时间的工具类。
 *
 * @author CPS Team
 */
public class DatetimeUtil {

	private static Logger log = LoggerFactory.getLogger(DatetimeUtil.class);

	// 一分钟单位时间
	public static final int MINUTE_MS = 60000;
	/**
	 * yyyyMMddHHmmss
	 */
	public static final String FORMAT_DATETIME_14 = "yyyyMMddHHmmss";
	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String FORMAT_DATETIME_17 = "yyyyMMddHHmmssSSS";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMAT_DATETIME_19 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static final String FORMAT_DATETIME_19_2 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * yyyy-MM-dd'T'HH:mm:ss
	 */
	public static final String FORMAT_DATETIME_19T = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String FORMAT_DATETIME_23 = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * yyyy-MM-dd-HH.mm.ss.SSSSSS
	 */
	public static final String FORMAT_DATETIME_26 = "yyyy-MM-dd-HH.mm.ss.SSSSSS";

	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ格林威治时区
	 */
	public static final String FORMAT_DATETIME_GMT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/**
	 * 2020-02-26T10:10:15+01:00
	 */
	public static final String FORMAT_DATETIME_UTC2 = "yyyy-MM-dd'T'HH:mm:ssXXX";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ
	 */
	public static final String FORMAT_DATETIME_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	/**
	 * yyyy-MM-dd'T'HH:mm:ssZ
	 */
	public static final String FORMAT_DATETIME_UTCZ = "yyyy-MM-dd'T'HH:mm:ssXXX";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ
	 */
	public static final String FORMAT_DATETIME_UTCSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SSS
	 */
	public static final String FORMAT_DATETIME_LOCAL = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	/**
	 * EEE MMM dd yyyy HH:mm:ss 'GMT+0800 (CST)'
	 */
	public static final String FORMAT_DATETIME_GMT0800 = "EEE MMM dd yyyy HH:mm:ss 'GMT+0800 (CST)'";

	/**
	 * yyyyMMddHHmmssZ
	 */
	public static final String FORMAT_DATETIME_Z = "yyyyMMddHHmmZ";

	/**
	 * yyyy/MM/dd
	 */
	public static final String FORMAT_DATE_11 = "yyyy/MM/dd";

	/**
	 * yyyy-MM-dd
	 */
	public static final String FORMAT_DATE_10 = "yyyy-MM-dd";
	/**
	 * yyyyMMdd
	 */
	public static final String FORMAT_DATE_8 = "yyyyMMdd";
	/**
	 * yyMMdd
	 */
	public static final String FORMAT_DATE_6 = "yyMMdd";
	/**
	 * HHmmss
	 */
	public static final String FORMAT_TIME_6 = "HHmmss";
	/**
	 * HHmmssSSS
	 */
	public static final String FORMAT_TIME_9 = "HHmmssSSS";

	public static final String STR_MILLISECOND = "ms";

	/** 日期比较的标的：年 y */
	public static final String INTERVAL_YEAR = "y";
	/** 日期比较的标的：月 m */
	public static final String INTERVAL_MONTH = "m";
	/** 日期比较的标的：日 d */
	public static final String INTERVAL_DAY = "d";
	/** 日期比较的标的：小时 h */
	public static final String INTERVAL_HOUR = "h";
	/** 日期比较的标的：分 min */
	public static final String INTERVAL_MINUTE = "min";
	/** 日期比较的标的：秒 s */
	public static final String INTERVAL_SECOND = "s";
	/** 日期比较的标的：毫秒 ms */
	public static final String INTERVAL_MILLISECOND = "ms";

	/** 日期周期：年 */
	public static final String PERIOD_YEAR = "1";
	/** 日期周期：月 */
	public static final String PERIOD_MONTH = "2";
	/** 日期周期：周 */
	public static final String PERIOD_WEEK = "3";
	/** 日期周期：日 */
	public static final String PERIOD_DAY = "4";

	private static final long MS_DAY = 86400000; // 1000 * 60s * 60m * 24h
	private static final long MS_HOUR = 3600000; // 1000 * 60s * 60m
	private static final long MS_MINUTE = 60000; // 1000 * 60s
	private static final long MS_SECOND = 1000; // 1000 * 60s

	private static Locale defaultLocale = Locale.getDefault();

	public static Timestamp nowTimestamp() {
		return getTimestamp(System.currentTimeMillis());
	}

	public static Timestamp getTimestamp(long time) {
		return new Timestamp(time);
	}

	/**
	 * <B>仅初始化时调用。</B>
	 *
	 * @param locale
	 */
	public static void setDefaultLocale(Locale locale) {
		if (locale != null) {
			defaultLocale = locale;
		}
	}

	private static TimeZone defaultTimeZone = TimeZone.getDefault();

	/**
	 * <B>仅初始化时调用。</B>
	 *
	 * @param timeZone
	 */
	public static void setDefaultTimeZone(TimeZone timeZone) {
		if (timeZone != null) {
			defaultTimeZone = timeZone;
		}
	}

	public static Calendar getCalendarInstance() {
		return Calendar.getInstance(defaultTimeZone, defaultLocale);
	}

	/**
	 * 获取当前日期下一周期的第一天日期
	 *
	 * @param period PERIOD_YEAR = 1; PERIOD_MONTH = 2; PERIOD_WEEK = 3; PERIOD_DAY
	 *               = 4;
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfNextPeriod(String period, Date date) {
		String interval = null;
		int offset = 1;
		String format = null;
		if (PERIOD_YEAR.equals(period)) {
			interval = INTERVAL_YEAR;
			format = "yyyy-01-01";
		} else if (PERIOD_MONTH.equals(period)) {
			interval = INTERVAL_MONTH;
			format = "yyyy-MM-01";
		} else if (PERIOD_WEEK.equals(period)) {
			interval = INTERVAL_DAY;
			offset = 8 - Integer.parseInt(getDayOfWeek(date));
			format = FORMAT_DATE_10;
		} else {
			return date;
		}
		SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format);
		String dateStr = dateFormat.format(dateAdd2(interval, offset, date));
		try {
			Date dt = dateFormat.parse(dateStr);
			return dt;
		} catch (ParseException e) {
			throw new RuntimeException("DatetimeUtil.getFirstDateOfNextPeriod(" + period + "," + date + ")", e);
		}

	}

	/**
	 * 获取当前日期周期的第一天日期
	 *
	 * @param field Calendar中定义的field
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfPeriod(String period, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (PERIOD_YEAR.equals(period)) {
			c.set(Calendar.DAY_OF_YEAR, 1);
		} else if (PERIOD_MONTH.equals(period)) {
			c.set(Calendar.DAY_OF_MONTH, 1);
		} else if (PERIOD_WEEK.equals(period)) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		} else {
			return date;
		}
		SimpleDateFormat dateFormat = getSimpleDateFormatInstance(FORMAT_DATE_10);
		String dateStr = dateFormat.format(c.getTime());
		try {
			Date dt = dateFormat.parse(dateStr);
			return dt;
		} catch (Exception e) {
			throw new RuntimeException("DatetimeUtil.getFirstDateOfPeriod(" + period + "," + date + ")", e);
		}

	}

	/**
	 * 获取当前日期周期的第一天日期
	 *
	 * @param field Calendar中定义的field
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfPeriod(String period) {
		Calendar c = Calendar.getInstance();
		c.setTime(getCurrentDate());
		if (PERIOD_YEAR.equals(period)) {
			c.set(Calendar.DAY_OF_YEAR, 1);
		} else if (PERIOD_MONTH.equals(period)) {
			c.set(Calendar.DAY_OF_MONTH, 1);
		} else if (PERIOD_WEEK.equals(period)) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		} else {
			return getCurrentDate();
		}
		SimpleDateFormat dateFormat = getSimpleDateFormatInstance(FORMAT_DATE_10);
		String dateStr = dateFormat.format(c.getTime());
		try {
			Date date = dateFormat.parse(dateStr);
			return date;
		} catch (Exception e) {
			throw new RuntimeException("DatetimeUtil.getFirstDateOfPeriod(" + period + ")", e);
		}

	}

	private static SimpleDateFormat getSimpleDateFormatInstance(String format) {
		return getSimpleDateFormatInstance(format, getCalendarInstance());
	}

	private static SimpleDateFormat getSimpleDateFormatInstance(String format, Calendar calendar) {
		SimpleDateFormat dateFormat = format != null ? new SimpleDateFormat(format) : new SimpleDateFormat();
		dateFormat.setCalendar(calendar == null ? getCalendarInstance() : calendar);
		dateFormat.setLenient(false);// 强调严格遵守该格式
		return dateFormat;
	}

	/**
	 * 获得当前字符日期yyyyMMdd，长度8，格式为：yyyyMMdd
	 *
	 * @return
	 */
	public static String getDateString() {
		return toDateString(new Date(), FORMAT_DATE_8);
	}

	/**
	 * 获得当前字符日期yyyy-MM-dd，长度10，格式为：yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getDateString10() {
		return toDateString(new Date(), FORMAT_DATE_10);
	}

	/**
	 * 获得当前字符日期yyMMdd，长度6，格式为：yyMMdd
	 *
	 * @return
	 */
	public static String getDateString2() {
		return toDateString(new Date(), FORMAT_DATE_6);
	}

	/**
	 * 获得当前字符日期时间，长度14，格式为：yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getDatetime14() {
		return toDateString(new Date(), FORMAT_DATETIME_14);
	}

	/**
	 * 获得当前字符日期时间，长度17，格式为：yyyyMMddHHmmssSSS
	 *
	 * @return
	 */
	public static String getDatetime17() {
		return toDateString(new Date(), FORMAT_DATETIME_17);
	}

	/**
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDatetime19() {
		return toDateString(new Date(), FORMAT_DATETIME_19);
	}

	/**
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static Date getDatetime20(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATETIME_19_2);
		try {
			Date resuslt = df.parse(toDateString(date, FORMAT_DATETIME_19_2));
			return resuslt;
		} catch (Exception e) {
			throw new RuntimeException("DatetimeUtil.getDatetime20(" + date + ")", e);
		}

	}

	/**
	 * @return yyyy-MM-dd'T'HH:mm:ss
	 */
	public static String getDatetime19T() {
		return toDateString(new Date(), FORMAT_DATETIME_19T);
	}

	/**
	 * 获取时间，长度6，格式为：HHmmss
	 *
	 * @return
	 */
	public static String getTime() {
		return toDateString(new Date(), FORMAT_TIME_6);
	}

	/**
	 * 获取时间，长度9，格式为：HHmmssSSS
	 *
	 * @return
	 */
	public static String getTime2() {
		return toDateString(new Date(), FORMAT_TIME_9);
	}

	/**
	 * 获取时间戳，长度24，格式为：yyyy-MM-dd HH:mm:ss.SSS
	 *
	 * @return
	 */
	public static String getTimestamp() {
		return toDateString(new Date(), FORMAT_DATETIME_23);
	}

	/**
	 * 获取时间戳，长度26，格式为：yyyy-MM-dd-HH.mm.ss.SSSSSS
	 *
	 * @return
	 */
	public static String getTimestamp2() {
		return toDateString(new Date(), FORMAT_DATETIME_26);
	}

	/**
	 * 日期格式化为: yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return "";
		}
		return toDateString(date, FORMAT_DATE_10);
	}

	/**
	 *
	 * @param date
	 * @param format DatetimeUtil.FORMAT_DATE_n
	 * @return
	 */
	public static String toDateString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format);
		return dateFormat.format(date);
	}

	/**
	 * 14位字符串转换日期 yyyyMMddHHmmss -> yyyy/MM/dd-HH:mm:ss
	 *
	 * @param dateStr yyyyMMddHHmmss
	 * @return  yyyy/MM/dd-HH:mm:ss
	 */
	public static String date14To19(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
		try {
			Date date = df.parse(dateStr);
			return df2.format(date);
		} catch (Exception e) {
			throw new RuntimeException("DatetimeUtil.date14To19(" + dateStr + ")", e);
		}
	}
	
	/**
	 * 14位字符串转换日期 yyyyMMddHHmmss -> yyyy/MM/dd-HH:mm:ss
	 *
	 * @param dateStr yyyyMMddHHmmss
	 * @return  yyyy/MM/dd-HH:mm:ss
	 */
	public static String dateTo10(String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		
		int len = dateStr.length();
		
		dateStr = dateStr.replace("年", "-");
		dateStr = dateStr.replace("月", "-");
		dateStr = dateStr.replace("日", "");
		
		dateStr = dateStr.replaceAll("/", "-");
		len = dateStr.length();
		if (len > 10 && dateStr.indexOf(" ") > 0) {
			return dateStr.substring(0,dateStr.indexOf(" ")).trim();
		}
		
		return dateStr;
	}
	
	/**
	 * 14位字符串转换日期 yyyyMMddHHmmss -> yyyy/MM/dd-HH:mm:ss
	 *
	 * @param dateStr yyyyMMddHHmmss
	 * @return  yyyy/MM/dd-HH:mm:ss
	 */
	public static String date19TTo14(String dateStr) {
		// yyyy-MM-dd'T'HH:mm:ss
		SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_19T);
		Date date;
		try {
			date = utcOfferSetSimpleDateFormat.parse(dateStr);
			SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_14);
			return localDateFormat.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("", e);
		}
		return null;
	}

	public static Date getCurrentDate() {
		return strToDate(toDateString(new Date(), FORMAT_DATE_10), FORMAT_DATE_10);
	}

	public static Date getCurrentDate2(Date date) {
		return strToDate(toDateString(date, FORMAT_DATE_11), FORMAT_DATE_11);
	}

	public static Date getCurrentDatetime() {
		return new Date();
	}

	/**
	 * 获取当前日期时间<br>
	 *
	 * @return com.eternalinfo.cup.bas.common.DateTime
	 */
	public static DateTime getCurrentDateTime() {
		String datetime = toDateString(new Date(), FORMAT_DATETIME_17);
		DateTime dt = new DateTime();
		dt.setTimestamp(datetime);
		dt.setDatetime(datetime.substring(0, 14));
		dt.setDate(datetime.substring(0, 8));
		dt.setTime(datetime.substring(8, 14));
		dt.setTimeMS(datetime.substring(8));
		dt.setYear(datetime.substring(0, 4));
		dt.setMonth(datetime.substring(4, 6));
		dt.setDay(datetime.substring(6, 8));
		dt.setHour(datetime.substring(8, 10));
		dt.setMinute(datetime.substring(10, 12));
		dt.setSecond(datetime.substring(12, 14));
		dt.setMilliSecond(datetime.substring(14));
		return dt;
	}

	/**
	 * @see #dateAdd(String, int, Date, String);
	 */
	public static String dateAdd(String interval, int number, Date date) {
		return dateAdd(interval, number, date, FORMAT_DATE_8);
	}

	/**
	 * @see #dateAdd(String, int, Date, String);
	 */
	public static String dateAdd(String interval, int number, String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_8);
		Date _date = new Date();
		try {
			_date = dateFormat.parse(date);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return dateAdd(interval, number, _date, FORMAT_DATE_8);
	}

	/**
	 * 返回格式化后的日期字符串，此日期加上了时间间隔(interval,number)<br>
	 * 例：<br>
	 * 取当前时间的年份减1的日期<br>
	 * DatetimeUtil.dateAdd("y", -1, new Date());<br>
	 * return "20070415"
	 *
	 * @param interval INTERVAL_YEAR "y"-年, INTERVAL_MONTH "m"-月, INTERVAL_DAY
	 *                 "d"-日, INTERVAL_HOUR "h"-时, INTERVAL_MINUTE "min"-分,
	 *                 INTERVAL_SECOND "s"-秒
	 * @param number   要加上的时间间隔的数目。其数值可以为正数（得到未来的日期），也可以为负数（得到过去的日期）。
	 * @param date     传入的日期
	 * @param format   日期格式化字符串
	 * @return
	 */
	public static String dateAdd(String interval, int number, Date date, String format) {
		// interval, number, date
		if (date == null) {
			return "";
		}
		Date date2 = dateAdd2(interval, number, date);
		SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format);
		return dateFormat.format(date2);
	}

	/**
	 *
	 * @param interval INTERVAL_YEAR "y"-年, INTERVAL_MONTH "m"-月, INTERVAL_DAY
	 *                 "d"-日, INTERVAL_HOUR "h"-时, INTERVAL_MINUTE "min"-分,
	 *                 INTERVAL_SECOND "s"-秒
	 * @param number
	 * @param date
	 * @return
	 */
	public static Date dateAdd2(String interval, int number, Date date) {
		// interval, number, date
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		if (interval.equals(INTERVAL_YEAR)) {
			calendar.add(Calendar.YEAR, number);

		} else if (interval.equals(INTERVAL_MONTH)) {
			calendar.add(Calendar.MONTH, number);

		} else if (interval.equals(INTERVAL_DAY)) {
			calendar.add(Calendar.DATE, number);

		} else if (interval.equals(INTERVAL_HOUR)) {
			calendar.add(Calendar.HOUR, number);

		} else if (interval.equals(INTERVAL_MINUTE)) {
			calendar.add(Calendar.MINUTE, number);

		} else if (interval.equals(INTERVAL_SECOND)) {
			calendar.add(Calendar.SECOND, number);

		} else if (interval.equals(INTERVAL_MILLISECOND)) {
			calendar.add(Calendar.MILLISECOND, number);

		}

		/*
		 * SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format, calendar);
		 * return dateFormat.format(calendar.getTime());
		 */
		return calendar.getTime();
	}

	/**
	 * 比较两个日期的间隔数， 由参数interval指定按“年，月，日，小时，分钟”的间隔类型。
	 *
	 * @param interval  INTERVAL_YEAR ("y"-年); INTERVAL_MONTH ("m"-月); INTERVAL_DAY
	 *                  ("d"-日); INTERVAL_HOUR ("h"-时); INTERVAL_MINUTE ("min"-分)
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public static long dateDiff(String interval, String dateBegin, String dateEnd) {

		return dateDiff(interval, toDate(dateBegin), toDate(dateEnd));

	}

	public static long dateDiff(String interval, Date dateBegin, Date dateEnd) {

		Calendar calendar1 = getCalendarInstance();
		Calendar calendar2 = getCalendarInstance();

		boolean negativeFlag = false;
		if (dateBegin.compareTo(dateEnd) == 0) {
			// date1 == date2
			return 0;
		} else if (dateBegin.compareTo(dateEnd) == -1) {
			// date1 < date2
			calendar1.setTime(dateBegin);
			calendar2.setTime(dateEnd);
		} else {
			// date1 > date2
			negativeFlag = true;
			calendar1.setTime(dateEnd);
			calendar2.setTime(dateBegin);
		}

		long result = 0;
		long value1 = 0, value2 = 0;

		if (interval.equals(INTERVAL_YEAR)) {
			value1 = calendar1.get(Calendar.YEAR);
			value2 = calendar2.get(Calendar.YEAR);
			result = value2 - value1;

		} else if (interval.equals(INTERVAL_MONTH)) {
			value1 = calendar1.get(Calendar.MONTH);
			value2 = calendar2.get(Calendar.MONTH);
			long year1 = calendar1.get(Calendar.YEAR);
			long year2 = calendar2.get(Calendar.YEAR);
			result = (year2 - year1) * 12 + (value2 - value1);

		} else if (interval.equals(INTERVAL_DAY)) {
			/*
			 * value1 = calendar1.get(Calendar.DAY_OF_YEAR); value2 =
			 * calendar2.get(Calendar.DAY_OF_YEAR);
			 */
			value1 = calendar1.getTimeInMillis();
			value2 = calendar2.getTimeInMillis();
			result = (value2 - value1) / MS_DAY;

		} else if (interval.equals(INTERVAL_HOUR)) {
			value1 = calendar1.getTimeInMillis();
			value2 = calendar2.getTimeInMillis();
			result = (value2 - value1) / MS_HOUR;

		} else if (interval.equals(INTERVAL_MINUTE)) {
			value1 = calendar1.getTimeInMillis();
			value2 = calendar2.getTimeInMillis();
			result = (value2 - value1) / MS_MINUTE;

		} else if (interval.equals(INTERVAL_SECOND)) {
			value1 = calendar1.getTimeInMillis();
			value2 = calendar2.getTimeInMillis();
			result = (value2 - value1) / MS_SECOND;

		}

		return negativeFlag ? 0 - result : result;

	}

	/**
	 * 将字符转成Date对象
	 *
	 * @param str 支持模式yyyyMMdd, yyMMdd, yyyyMMddHHmmss, yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static Date toDate(String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}

		int len = dateStr.length();
		switch (len) {
		case 6:
			return strToDate(dateStr, FORMAT_DATE_6);
		case 8:
			return strToDate(dateStr, FORMAT_DATE_8);
		case 10:
			return strToDate(dateStr, FORMAT_DATE_10);
		case 14:
			return strToDate(dateStr, FORMAT_DATETIME_14);
		case 17:
			return strToDate(dateStr, FORMAT_DATETIME_17);
		case 19:
			if (dateStr.indexOf('T')>-1){
				return strToDate(dateStr, FORMAT_DATETIME_19T);
			}else{
				return strToDate(dateStr, FORMAT_DATETIME_19);
			}
		case 21:
		case 22:
		case 23:
			return strToDate(dateStr, FORMAT_DATETIME_23);
		case 26:
			return strToDate(dateStr, FORMAT_DATETIME_26);
		default:
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
			try {
				return sdf.parse(dateStr);
			} catch (Exception e) {
				//log.error("toDate(" + dateStr + ")", e);
				throw new RuntimeException("DatetimeUtil.toDate(" + dateStr + ") error.", e);
			}
			// return new Date(dateStr);
		}
	}

	/**
	 *
	 * @param SourceStr
	 * @param FormatString
	 * @return
	 */
	public static Date strToDate(String str, String format) {
		try {
			SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format);
			return dateFormat.parse(str);
		} catch (Throwable t) {
			throw new RuntimeException("DatetimeUtil.strToDate(" + str + "," + format + ") error.", t);
		}

	}

	/**
	 *
	 * @param str 时区的简称或全称。
	 * @return
	 */
	public static TimeZone toTimeZone(String str) {
		try {
			TimeZone result = (str == null || str.trim().length() == 0) ? null : TimeZone.getTimeZone(str);
			return result;
		} catch (Throwable t) {
			throw new RuntimeException("DatetimeUtil.toTimeZone(" + str + ") error.", t);
		}

	}

	/**
	 * 对日期字符串进行格式化输出
	 *
	 * @param strDate 要处理的日期字符串 8位为日期，6位为时间
	 * @param format  格式化类型
	 * @return 格式化后的日期字符串
	 */
	public static String format(String strDate, String format) {
		String strOutDate = strDate;
		int len = strDate.length();
		if (len == 6) {
			Date date = strToDate(strDate, "hhmmss");
			strOutDate = toDateString(date, format);
		} else if (len == 8) {
			Date date = strToDate(strDate, "yyyyMMdd");
			strOutDate = toDateString(date, format);
		} else {
			strOutDate = strDate;
		}
		return strOutDate;
	}

	/**
	 * 对XMLGregorianCalendar进行格式化输出
	 *
	 * @param date
	 * @param format 格式化类型
	 * @return 格式化后的日期字符串
	 */
	public static String format(XMLGregorianCalendar date, String format) {
		//TimeZone.setDefault(TimeZone.getTimeZone("EST"));
		DateFormat formatter = new SimpleDateFormat(format);
		//formatter.setTimeZone(TimeZone.getTimeZone("EST"));
		String newTime = formatter.format(date.toGregorianCalendar().getTime());
		Date newDate = null;
		try {
			newDate = formatter.parse(newTime);
		} catch (Exception e) {
			try {
				throw new Exception("DatetimeUtil.format(" + date + "," + format + ") error.", e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return toDateString(newDate, format);
	}

	/**
	 * 把2001-12-27 16:14:34格式的转成20011227161434格式
	 *
	 * @param dateString
	 * @return
	 */
	public static String format(String strDate) {
		String tmpString = strDate;
		StringBuffer sb = new StringBuffer();
		String[] separator = new String[] { "-", " ", ":" };
		for (int i = 0; i < separator.length; i++) {
			while (tmpString.indexOf(separator[i]) > -1) {
				int j = tmpString.indexOf(separator[i]);
				sb.append(tmpString.substring(0, j));
				tmpString = tmpString.substring(j + 1);
			}
		}
		sb.append(tmpString);
		return sb.toString();
	}

	/**
	 * 得到“yyyy-MM-dd 00:00:00”格式的日期
	 *
	 * @return
	 */
	public static Date format1(Date date) {
		Calendar c = getCalendarInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
		return c.getTime();
	}

	/**
	 * 得到“yyyy-MM-dd 23:59:59”格式的日期
	 *
	 * @return
	 */
	public static Date format2(Date date) {
		Calendar c = getCalendarInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
		return c.getTime();
	}

	/**
	 * 得到“yyyy-MM-dd 00:00:00”格式的日期
	 *
	 * @return
	 */
	public static Timestamp format1(Timestamp timestamp) {
		Calendar c = getCalendarInstance();
		c.setTimeInMillis(timestamp.getTime());
		c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return getTimestamp(c.getTimeInMillis());
	}

	/**
	 * 得到“yyyy-MM-dd 23:59:59”格式的日期
	 *
	 * @return
	 */
	public static Timestamp format2(Timestamp timestamp) {
		Calendar c = getCalendarInstance();
		c.setTimeInMillis(timestamp.getTime());
		c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return getTimestamp(c.getTimeInMillis());
	}

	/**
	 * 日期字符串比较大小<br>
	 *
	 * @param dateStr1 <br>
	 * @param dateStr2 <br>
	 * @return -1 = dateStr1 less than dateStr2<br>
	 *         0 = dateStr1 equal dateStr2<br>
	 *         1 = dateStr1 greater than dateStr2<br>
	 *         2 = parameter error
	 */
	public static int compare(String dateStr1, String dateStr2) {
		if (dateStr1 == null || dateStr1.trim().length() == 0 || dateStr2 == null || dateStr2.trim().length() == 0) {
			return 2;
		}
		Date date1 = toDate(dateStr1);
		Date date2 = toDate(dateStr2);

		try {
			return date1.compareTo(date2);
		} catch (Exception e) {
			return 2;
		}
	}

	/**
	 * 判断输入的str是否符合format的日期时间格式
	 *
	 * @param str
	 * @param format
	 * @return
	 */
	public static boolean isDateTimeFormat(String dateStr, String format) {
		try {
			if (StringUtil.isEmpty(dateStr) || StringUtil.isEmpty(format)) {
				return false;
			}
			if (dateStr.length() != format.length()) {
				return false;
			}
			if (!dateStr.matches("[1-9][0-9]{3}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])")) {
				return false;
			}
			SimpleDateFormat dateFormat = getSimpleDateFormatInstance(format);
			dateFormat.parse(dateStr);
			return true;
		} catch (Throwable t) {
			log.warn("DatetimeUtil.isDateTimeFormat(" + dateStr + "," + format + ")", t);
			return false;
		}
	}

	public static boolean isFirstDayOfMonth(Calendar calendar) {
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFirstDayOfQuarter(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH);
		if (isFirstDayOfMonth(calendar) && (month == Calendar.JANUARY || month == Calendar.APRIL
				|| month == Calendar.JULY || month == Calendar.OCTOBER)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFirstDayOfYear(Calendar calendar) {
		if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将8位日期字符yyyyMMdd转成10位日期yyyy-MM-dd
	 *
	 * @param str
	 * @return
	 */
	public static String date8To10(String dateStr) {
		if (dateStr.length() != 8) {
			 return dateStr;
		}
		String result = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
		return result;
	}

	/**
	 * 当使用6位日期格式时，20世纪和21世纪应以按以标准进行区分： 当YY大于79时表示19YY，当YY小于或等于79时表示20YY
	 *
	 * @param dateStr
	 * @return
	 */
	public static String date6To8(String dateStr) {
		if (dateStr.length() == 6) {
			String y79 = "79";
			String yy = dateStr.substring(0, 2);
			if (y79.compareTo(yy) < 0) {
				dateStr = "19" + dateStr;
			} else {
				dateStr = "20" + dateStr;
			}
		}
		return dateStr;
	}

	/**
	 * 获取 日期对应的星期几 1,2,3,4,5,6,7 7-周日 1-周一 2-周二 3-周三 4-周四 5-周五 6-周六
	 */
	public static String getDayOfWeek(String dateString) {
		Calendar calendar = getCalendarInstance();
		Date date = DatetimeUtil.strToDate(dateString, DatetimeUtil.FORMAT_DATE_8);
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (0 == dayOfWeek) {
			dayOfWeek = 7;
		}
		return dayOfWeek + "";
	}

	public static String getDayOfWeek(Date date) {

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (0 == dayOfWeek) {
			dayOfWeek = 7;
		}
		return String.valueOf(dayOfWeek);
	}

	public static Date getLocalDateFromUtc(String strDate) {
		if (StringUtil.isEmpty(strDate)) {
			return null;
		}
		int len = strDate.length();
		switch (len) {
		case 24:
			try {
				// 格林威治时间
				SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_GMT);
				utcSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
				Date date = utcSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_23);
				return toDate(localDateFormat.format(date));
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 28:
			try {
				// UTC时间带时区
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTC);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_23);
				return toDate(localDateFormat.format(date));
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 17:
			try {
				// 时间带时区
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_Z);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_14);
				return toDate(localDateFormat.format(date));
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		default:
			log.error("日期[" + strDate + "]的格式非[YYYY-MMDDThh:mm:ss.sssZ、YYYY-MM-DDThh:mm:ss.sss+/-hh:mm]，无法转换!");
			return null;
		}
	}

	public static String getLocalDateFromUtcStr(String strDate) {
		if (StringUtil.isEmpty(strDate)) {
			return null;
		}
		int len = strDate.length();
		switch (len) {
		case 24:
			try {
				// 格林威治时间
				SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_GMT);
				utcSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
				Date date = utcSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_23);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 28:
			try {
				// UTC时间带时区
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTC);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_23);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 17:
			try {
				// 时间带时区
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_Z);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_14);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		default:
			log.error("日期[" + strDate + "]的格式非[YYYY-MMDDThh:mm:ss.sssZ、YYYY-MM-DDThh:mm:ss.sss+/-hh:mm]，无法转换!");
			return null;
		}
	}

	/**
	 * IOSDATETIME转换
	 *
	 * @param strDate
	 * @return
	 */
	public static String getLocalDateFromIOSDateStr(String strDate) {
		if (StringUtil.isEmpty(strDate)) {
			return null;
		}
		int len = strDate.length();
		switch (len) {
		case 19:
			try {
				// yyyy-MM-dd'T'HH:mm:ss
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_19T);
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 20:
			try {
				// yyyy-MM-dd'T'HH:mm:ssZ
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTCZ);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 23:
			try {
				// yyyy-MM-dd'T'HH:mm:ss.SSS
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_LOCAL);
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 24:
			try {
				// yyyy-MM-dd'T'HH:mm:ss.SSSZ
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTCSSSZ);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 25:
			try {
				// yyyy-MM-dd'T'HH:mm:ss+XX:XX
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTC2);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 28:
			try {
				// UTC时间带时区
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTC);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		case 29:
			try {
				// yyyy-MM-dd'T'HH:mm:ss.SSS+XX:XX
				SimpleDateFormat utcOfferSetSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_UTCSSSZ);
				utcOfferSetSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date = utcOfferSetSimpleDateFormat.parse(strDate);
				SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATETIME_19);
				return localDateFormat.format(date);
			} catch (ParseException e) {
				log.error("", e);
			}
			return null;
		default:
			log.error("日期[" + strDate + "]的格式非[YYYY-MMDDThh:mm:ss.sssZ、YYYY-MM-DDThh:mm:ss.sss+/-hh:mm]，无法转换!");
			return null;
		}
	}

	public static String getUtcDateString(Date date) {
		SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_GMT);
		utcSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Zulu"));
		return utcSimpleDateFormat.format(date);
	}

	public static Timestamp toTimestampForUtc0800DateString(String dateStr) {

		SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_GMT0800, Locale.US);
		try {
			Date date = utcSimpleDateFormat.parse(dateStr);
			Timestamp tsp = new Timestamp(date.getTime());
			return tsp;
		} catch (ParseException e) {
			//log.error("日期[" + dateStr + "]的格式非[EEE MMM dd yyyy HH:mm:ss 'GMT+0800 (CST)]，无法转换!");
			throw new RuntimeException("DatetimeUtil.toTimestampForUtc0800DateString(" + dateStr + ")", e);
		}

	}

	public static Date toDateForUtc0800DateString(String dateStr) {
		SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME_GMT0800, Locale.US);
		try {
			Date date = utcSimpleDateFormat.parse(dateStr);
			SimpleDateFormat localDateFormat = getSimpleDateFormatInstance(FORMAT_DATE_10);
			date = toDate(localDateFormat.format(date));
			return date;
		} catch (Exception e) {
			//log.error("日期[" + dateStr + "]的格式非[EEE MMM dd yyyy HH:mm:ss 'GMT+0800 (CST)]，无法转换!");
			throw new RuntimeException("DatetimeUtil.toDateForUtc0800DateString(" + dateStr + ")", e);
		}

	}

	/**
	 * 获取两个时间段内的所有日期
	 *
	 * @author wot_xujiayou_hrx
	 * @param beginDay 开始日期，必须传8位，如20190201
	 * @param endDay   结束日期，必须传8位，如20190203
	 * @return 日期列表20190201 20190202 20190203
	 */
	public static List<String> getBetweenDays(String beginDay, String endDay) {
		// 日期为空直接返回
		if (StringUtil.isEmpty(beginDay) || StringUtil.isEmpty(endDay)) {
			return null;
		}
		// 日期长度等于8
		if (beginDay.length() != 8 || endDay.length() != 8) {
			return null;
		}
		// 日期格式不对直接返回,避免出现20190229这样不规则的日期
		Date beginDayDate = DatetimeUtil.toDate(beginDay);
		Date endDayDate = DatetimeUtil.toDate(endDay);
		if (beginDayDate == null || endDayDate == null) {
			return null;
		}
		// 起始日期不能大于结束日期
		if (endDay.compareTo(beginDay) < 0) {
			return null;
		}
		List<String> betweenList = new ArrayList<String>();
		try {
			Calendar begin = Calendar.getInstance();
			begin.setTime(beginDayDate);
			begin.add(Calendar.DATE, -1);
			while (true) {
				begin.add(Calendar.DATE, 1);
				String newEndDay = toDateString(begin.getTime(), FORMAT_DATE_8);
				betweenList.add(newEndDay);
				if (endDay.equals(newEndDay)) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("DatetimeUtil.getBetweenDays(" + beginDay + "," + endDay + ")", e);
			betweenList = null;
		}
		return betweenList;
	}

	public static Timestamp dateToTimestamp(Date date) {
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * Date时间格式转XMLGregorianCalendar格式
	 *
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) {
		String formatStr = FORMAT_DATETIME_19T;
		//GregorianCalendar cal = new GregorianCalendar();
		//cal.setTime(date);
		try {
			XMLGregorianCalendar gc = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(new SimpleDateFormat(formatStr).format(date));
			return gc;
		} catch (Exception e) {
			throw new RuntimeException("DatetimeUtil.dateToXmlGregorianCalendar(" + date + ")", e);
		}
	}

	public static XMLGregorianCalendar dateToXmlGregorianCalendar(String dateStr) {
		Date date = toDate(dateStr);
		return dateToXmlGregorianCalendar(date);
	}

	/**
	 * String类型的日期比较
	 *
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public static int dateCompare(String dateStr1, String dateStr2) {
		Date dt1 = toDate(dateStr1);
		Date dt2 = toDate(dateStr2);
		return dt1.compareTo(dt2);
	}

}
