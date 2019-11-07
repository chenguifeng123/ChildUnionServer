package com.qinzi123.dto.template;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public abstract class AbstractTemplateHelper {

	protected Keyword getKeyword(String value){
		return new Keyword(value);
	}

}
