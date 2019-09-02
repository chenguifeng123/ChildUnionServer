package com.qinzi123;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by chenguifeng on 2019/3/20.
 */

public class LocalTest {

	private String convertFee(String fee){
		BigDecimal bigDecimal = new BigDecimal(fee);
		BigDecimal result = bigDecimal.multiply(new BigDecimal(100)).setScale(0);
		return String.valueOf(result.toString());
	}

	@Test
	@Ignore
	public void testA(){
		System.out.println(convertFee("0.15"));
	}

	@Test
	public void testRatio(){
		System.out.println(  (int) Math.ceil( 5 * 10 * 1.0 / 100));
	}
}
