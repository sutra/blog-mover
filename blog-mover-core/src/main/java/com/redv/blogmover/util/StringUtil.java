package com.redv.blogmover.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * @param startStr
	 *            传入开始的字符串
	 * @param endStr
	 *            传入结束的字符串s
	 * @param str
	 *            处理的字符串
	 * @return 返回结果
	 */
	public static String getContent(String startStr, String endStr, String doc) {
		return getContent(startStr, endStr, doc, false);
	}

	public static String getContent(String startStr, String endStr, String str,
			boolean bl) {
		try {
		int aTitleStartx = str.indexOf(startStr) + (bl ? 0 : startStr.length());
		int aTitleEndx = str.indexOf(endStr, aTitleStartx)
				+ (bl ? endStr.length() : 0);
		return str.substring(aTitleStartx, aTitleEndx);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getContent(String startStr, String endStr, String str,
			boolean bl, int x) {
		int aTitleStartx = str.indexOf(startStr, x)
				+ (bl ? 0 : startStr.length());
		int aTitleEndx = str.indexOf(endStr, aTitleStartx)
				+ (bl ? endStr.length() : 0);
		return str.substring(aTitleStartx, aTitleEndx);
	}

	/**
	 * Convert string to date object
	 * 
	 * @param strD
	 *            the string date format:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date strToDate(String strD) {
		try {
			String strFormat = "yyyy-MM-dd   HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			return sdf.parse(strD);
		} catch (java.text.ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * Unicode 转字符串
	 * @param str
	 * @return
	 */
	public static String unicodeToString(String str) { 
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))"); 
		Matcher matcher = pattern.matcher(str); 
		char ch; 
		while (matcher.find()) {  
			ch = (char) Integer.parseInt(matcher.group(2), 16);  
			str = str.replace(matcher.group(1), ch + ""); 
		} 
		return str;
	}
}
