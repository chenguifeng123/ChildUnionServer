package com.qinzi123.dto.push;

/**
 * Created by chenguifeng on 2018/11/20.
 */
public class Keyword {
	String value;

	public Keyword(){

	}

	public Keyword(String value){
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
