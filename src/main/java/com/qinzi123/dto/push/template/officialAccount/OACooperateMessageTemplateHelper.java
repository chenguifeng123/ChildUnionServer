package com.qinzi123.dto.push.template.officialAccount;

import com.qinzi123.dto.CardMessage;
import org.springframework.stereotype.Component;

/**
 * Created by chenguifeng on 2019/11/5.
 */
@Component(value = "OACooperateMessage")
public class OACooperateMessageTemplateHelper extends OATemplateHelper {

	protected final static String STATUS = "待审批";

	String templateId() {
		return "T_1GtufjNfBUCWDBDfFU9UGBjGqzlFGGCKeWhAjj8xQ";
	}

	String url() {
		return "https://www.qinzi123.com/manage/home.html";
	}

	Object data(Object object) {
		OACooperateTemplate cooperateTemplate = new OACooperateTemplate();
		CardMessage cardMessage = (CardMessage)object;
		cooperateTemplate.setFirst(getFirst(cardMessage.getTitle()));
		cooperateTemplate.setKeyword1(getKeyword(cardMessage.getMessage()));
		cooperateTemplate.setKeyword2(getKeyword(STATUS));
		cooperateTemplate.setRemark(getRemark(""));
		return cooperateTemplate;
	}
}
