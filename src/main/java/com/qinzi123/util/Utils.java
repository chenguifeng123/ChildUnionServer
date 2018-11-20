package com.qinzi123.util;

import com.qinzi123.exception.GlobalProcessException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

	private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final long MINUTE_IN_HOUR = 60;
	private static final long MINUTE_IN_DAY = 1440;

	public static String getCurrentDate(){
		return DEFAULT_TIME_FORMAT.format(new Date());
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
}
