package com.lianle.utils.JUtils.base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类，验证数据是否符合规范
 * @Project:JUtils
 * @file:RegularUtils.java
 * @Authro:chenssy
 * @data:2014年8月7日
 */
public class RegexUtils {
	
	/**
	 * 判断输入的字符串是否符合Email格式.
	 * @autor:chenssy
	 * @data:2014年8月7日
	 *
	 * @param email
	 * 				传入的字符串
	 * @return 符合Email格式返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * @autor:chenssy
	 * @data:2014年8月7日
	 *
	 * @param value
	 * 				传入的字符串
	 * @return
	 */
	public static boolean isChinese(String value) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * 判断是否为浮点数，包括double和float
	 * @autor:chenssy
	 * @data:2014年8月7日
	 *
	 * @param value
	 * 			传入的字符串
	 * @return
	 */
	public static boolean isDouble(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(value).matches();
	}
	
	/**
	 * 判断是否为整数
	 * @autor:chenssy
	 * @data:2014年8月7日
	 *
	 * @param value
	 * 			传入的字符串
	 * @return
	 */
	public static boolean isInteger(String value) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(value).matches();
	}

	/**
	 * 获得匹配的字符串
	 *
	 * @param pattern 编译后的正则模式
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(Pattern pattern, String content, int groupIndex) {
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}

	/**
	 * 获得匹配的字符串
	 *
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(String regex, String content, int groupIndex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return get(pattern, content, groupIndex);
	}

	/**
	 * 简单匹配(单个返回)
	 *
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @return
	 */
	public static String get(String regex, String content) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * 简单匹配(多个返回)
	 *
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @return
	 */
	public static List<String> getMore(String regex, String content) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		List<String> result = new ArrayList<String>();
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result;
	}



	public static void main(String[] args) {
		String value = "http://5280bt.com/3761.html";
		String regex = "[1-9]\\d*\\.+";
		System.out.println(get(regex, value));
	}
}
