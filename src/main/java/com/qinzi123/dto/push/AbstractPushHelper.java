package com.qinzi123.dto.push;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public abstract class AbstractPushHelper {

	protected Keyword getKeyword(String value){
		return new Keyword(value);
	}

}
