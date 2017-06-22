package com.goldenweb.fxpg.frame.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * @Description: TODO(日期格式化)
 * @author Lee 
 * @date 2014-2-11 上午9:58:30
 */
public class FormatDateTime {
 
	
	public static String toYear(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(d);
	}
	
	public static String toMonth(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(d);
	}
	
	public static String toDay(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(d);
	}
	
	/**
	 * @Description TODO(转换日期为年月 yyyy-MM)
	 * @Title toYearMonth
	 * @param d 日期
	 * @return String
	 * @author Lee
	 * @time 2014-2-11 上午9:58:50
	 */
	public static String toYearMonth(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(d);
	}
 
	/**
	 * @Description TODO(转换日期为短日期格式的字符串 yyyy-MM-dd)
	 * @Title toShortFormat
	 * @param d 日期
	 * @return String
	 * @author Lee
	 * @time 2014-2-11 上午9:59:30
	 */
	public static String toShortFormat(final Date d) {
		if (d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}
	
	/**
	 * @Description TODO(转换日期为短日期格式的字符串 yyyy-MM-dd)
	 * @Title toShortFormat
	 * @param d 日期
	 * @return Date
	 * @author Lee
	 * @throws ParseException 
	 * @time 2014-2-11 上午9:59:30
	 */
	public static Date toShortFormat(final String d) throws ParseException {
		if (d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(d);
	}
	 
	/**
	 * @Description TODO(转换日期为全日期格式的字符串 yyyy-MM-dd HH:mm:ss)
	 * @Title toFullFormat
	 * @param d 日期
	 * @return String
	 * @author Lee
	 * @time 2014-2-11 上午10:00:11
	 */
	public static String toFullFormat(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	
	/**
	 * @Description TODO(转换日期为全日期格式的字符串 yyyy-MM-dd HH:mm:ss)
	 * @Title toFullFormat
	 * @param d 日期
	 * @return Date
	 * @author Lee
	 * @throws ParseException 
	 * @time 2014-2-11 上午10:00:11
	 */
	public static Date toFullFormat(final String d) throws ParseException {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(d);
	}
	
	public static Date toFullFormatByDate(final Date d) throws ParseException {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(sdf.format(d));
	}
	 
	/**
	 * @Description TODO(转换日期为月日格式的字符串 MM-dd)
	 * @Title toMonthDayFormat
	 * @param d 日期
	 * @return String
	 * @author Lee
	 * @time 2014-2-11 上午10:00:51
	 */
	public static String toMonthDayFormat(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(d);
	}
	 
	/**
	 * @Description TODO(转换日期为时间格式的字符串 HH:mm:ss)
	 * @Title toTimeFormat
	 * @param d 日期
	 * @return String
	 * @author Lee
	 * @time 2014-2-11 上午10:01:08
	 */
	public static String toTimeFormat(final Date d) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(d);
	}
}
