package com.qinzi123.dto.template;

import com.qinzi123.dto.SendObject;
import org.apache.commons.lang.StringUtils;

/**
 * Created by chenguifeng on 2018/11/20.
 */
public abstract class AbstractTemplateHelper {

	abstract String templateId();
	abstract String page(Object object);
	abstract String emphasis();
	abstract Object data(Object object);

	Keyword getKeyword(String value){
		Keyword keyword = new Keyword();
		keyword.setValue(value);
		return keyword;
	}

	public SendObject generateSendObject(String toUser, String formId, Object object){
		SendObject sendObject = new SendObject();
		sendObject.setTouser(toUser);
		sendObject.setTemplate_id(templateId());
		if(!StringUtils.isEmpty(page(object))) sendObject.setPage(page(object));
		sendObject.setForm_id(formId);
		sendObject.setData(data(object));
		if(!StringUtils.isEmpty(emphasis())) sendObject.setEmphasis_keyword(emphasis());
		return sendObject;
	}

}
