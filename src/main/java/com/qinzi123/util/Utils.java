package com.qinzi123.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
}
