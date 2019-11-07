package com.qinzi123.dto.template.officialAccount;

import com.qinzi123.dto.OfficialAccountSendObject;
import com.qinzi123.dto.template.AbstractTemplateHelper;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public abstract class OATemplateHelper extends AbstractTemplateHelper{

	abstract String templateId();
	abstract String url();
	abstract Object data(Object object);

	protected First getFirst(String value){
		return new First(value);
	}

	protected Remark getRemark(String value){
		return new Remark(value);
	}

	public OfficialAccountSendObject generateSendObject(String toUser, Object showData){
		OfficialAccountSendObject sendObject = new OfficialAccountSendObject();
		sendObject.setTouser(toUser);
		sendObject.setTemplate_id(templateId());
		sendObject.setUrl(url());
		sendObject.setData(data(showData));
		return sendObject;
	}
}
