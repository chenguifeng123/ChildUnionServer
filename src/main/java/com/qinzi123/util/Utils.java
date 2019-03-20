package com.qinzi123.util;

import com.qinzi123.exception.GlobalProcessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

	private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat EMPTY_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final long MINUTE_IN_HOUR = 60;
	private static final long MINUTE_IN_DAY = 1440;

	public static final String EQUAL_SQL = " %s = %s ";
	public static final String AND = " and ";
	public static final String OR = " or ";

	public static String getCurrentDate(){
		return DEFAULT_TIME_FORMAT.format(new Date());
	}

	public static String getCurrentDateNoFlag(){
		return EMPTY_TIME_FORMAT.format(new Date());
	}

	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	public static long dateLast(String date){
		try {
			return dateLast(DEFAULT_TIME_FORMAT.parse(date));
		}catch (Exception e){
			throw new GlobalProcessException("日期格式不正确", e);
		}
	}

	public static long dateLast(Date date){
		long last = date.getTime();
		long current = new Date().getTime();
		return (current - last)/DateUtils.MILLIS_PER_SECOND;
	}

	public static String getDateLast(long last){
		if(last < MINUTE_IN_HOUR) return String.format("%d分钟前", last);
		else if(last < MINUTE_IN_DAY){
			long hour = Double.valueOf(Math.floor(last / MINUTE_IN_HOUR)).longValue();
			return String.format("%d小时前", hour);
		}
		else {
			long day = Double.valueOf(Math.floor(last / MINUTE_IN_DAY)).longValue();
			return String.format("%d天前", day);
		}
	}

	public static String fillUrlParams(String url, Map<String, Object> params){
		if(params == null || params.size() == 0) return url;
		List<String> list = new ArrayList<>();
		Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<String, Object> entry = iterator.next();
			list.add(entry.getKey() + "=" + entry.getValue().toString());
		}
		return url + "?" + StringUtils.join(list, "&");
	}

	// 对前端传入的值进行处理，判断是否需要加引号
	public static String fillColumn(String column){
		return column.length() > 0 && StringUtils.isNumeric(column) ? column : String.format("'%s'", column);
	}

	// 字符串连接
	public static String join(String[] array, String joinChar){
		return StringUtils.join(array, joinChar);
	}

	public static String join(List<String>list, String joinChar){
		String[] array = new String[list.size()];
		array = list.toArray(array);
		return join(array, joinChar);
	}

	// 写文件
	public static void writeByteArrayToFile(String fileName, byte[] data){
		try{
			File file = new File(fileName);
			FileUtils.writeByteArrayToFile(file, data);
		}catch(IOException e){
			throw new RuntimeException(e.getCause());
		}
	}
}
