package io.github.pleuvoir.message.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public enum DateFormat {

	/**
	 * yyyy-MM-dd
	 */
	DATE_DEFAULT("yyyy-MM-dd"),
	
	/**
	 * yyyyMMdd
	 */
	DATE_COMPACT("yyyyMMdd"),
	
	/**
	 * HH:mm:ss
	 */
	TIME_DEFAULT("HH:mm:ss"),
	
	/**
	 * yyyy-MM-dd HH:mm
	 */
	NOT_SS_DEFAULT("yyyy-MM-dd HH:mm"),
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss"),
	
	/**
	 * yyyyMMddHHmmss
	 */
	DATETIME_COMPACT("yyyyMMddHHmmss"),
	
	/**
	 * yyyy-MM-dd HH:mm:ss:SSS
	 */
	DATETIME_MILLISECOND("yyyy-MM-dd HH:mm:ss:SSS"),
	
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	DATETIME_MILLISECOND_1("yyyy-MM-dd HH:mm:ss.SSS"),
	
	/**
	 * yyyyMMddHHmmssSSS
	 */
	DATETIME_MILLISECOND_COMPACT("yyyyMMddHHmmssSSS"),
	
	/**
	 * HHmmss
	 */
	TIME_COMPACT("HHmmss");
	
	
	private final String pattern;

	private DateFormat(String pattern) {
		this.pattern = pattern;
	}

	
	/**
	 * 按照指定的格式进行格式化
	 */
	public String format(TemporalAccessor temporal) {
		if (temporal == null) 
			throw new IllegalArgumentException();
		return DateTimeFormatter.ofPattern(pattern).format(temporal);
	}
	
	/**
	 * 按照指定的格式进行格式化
	 */
	public String format(Date date) {
		if (date == null) 
			throw new IllegalArgumentException();
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 按照指定的格式进行解析
	 */
	public LocalDateTime parse(String datetime) {
		if (StringUtils.isBlank(datetime)) 
			throw new IllegalArgumentException();
		return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * 按照指定的格式进行解析
	 */
	public Date parseToDate(String datetime) throws ParseException {
		if (StringUtils.isBlank(datetime)) 
			throw new IllegalArgumentException();
		return new SimpleDateFormat(pattern).parse(datetime);
	}
	
	/**
	 * 按照指定的格式进行转化<br>
	 * 转化 1.7 与 1.8 的日期实现
	 */
	public Date convert(TemporalAccessor temporal) {
		Date date;
		try {
			date = parseToDate(format(temporal));
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		return date;
	}
	
	/**
	 * 按照指定的格式进行转化<br>
	 * 转化 1.7 与 1.8 的日期实现
	 */
	public LocalDateTime convert(Date date){
		return parse(format(date));
	}
	
	public String getPattern() {
		return pattern;
	}

}
