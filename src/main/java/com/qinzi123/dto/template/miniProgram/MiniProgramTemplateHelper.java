package com.qinzi123.dto.template.miniProgram;

import com.qinzi123.dto.MiniProgramSendObject;
import com.qinzi123.dto.template.AbstractTemplateHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by chenguifeng on 2018/11/20.
 */
public abstract class MiniProgramTemplateHelper extends AbstractTemplateHelper{

	abstract String templateId();
	abstract String page(Object object);
	abstract String emphasis();
	abstract Object data(Object object);

	public MiniProgramSendObject generateSendObject(String toUser, String formId,
													Object pageData, Object showData){
		MiniProgramSendObject sendObject = new MiniProgramSendObject();
		sendObject.setTouser(toUser);
		sendObject.setTemplate_id(templateId());
		if(!StringUtils.isEmpty(page(pageData))) sendObject.setPage(page(pageData));
		sendObject.setForm_id(formId);
		sendObject.setData(data(showData));
		if(!StringUtils.isEmpty(emphasis())) sendObject.setEmphasis_keyword(emphasis());
		return sendObject;
	}

}
