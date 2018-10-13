package io.github.pleuvoir.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * date localDateTime 转换
 */
public class LocalDateUtils {

    //默认使用系统当前时区
    private static final ZoneId ZONE = ZoneId.systemDefault();

    /**
     * 根据传入的时间格式返回系统当前的时间
     * @param format string
     * @return
     */
    public static String nowByFormat(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }

    /**
     * LocalDateTime转String
     * @param localDateTime
     * @param format
     * @return
     */
    public static String localDateTimeByFormat(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * String转LocalDateTime
     * @param date
     * @param format
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String date, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    /**
     * LocalDate转String
     * @param localDate
     * @param format
     * @return
     */
    public static String localDateByFormat(LocalDate localDate, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * String转LocalDate
     * @param date
     * @param format
     * @return
     */
    public static LocalDate stringToLocalDate(String date, String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(date, dateTimeFormatter);
    }

    /**
     * 将Date转换成LocalDateTime
     *
     * @param d date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    /**
     * 将Date转换成LocalDate
     *
     * @param d date
     * @return
     */
    public static LocalDate dateToLocalDate(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    /**
     * 将Date转换成LocalTime
     *
     * @param d date
     * @return
     */
    public static LocalTime dateToLocalTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * 将LocalDate转换成Date
     *
     * @param localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDateTime转换成Date
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }
    
    /**
     * 
    * @Description: 将LocalDateTime转为Long类型时间戳
    * @param:
    * @author: liu
    * @date: 2018年7月27日 下午1:21:04
     */
    public static Long localDateTimeToLong(LocalDateTime dateTime) {
		return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    	
    }
}
