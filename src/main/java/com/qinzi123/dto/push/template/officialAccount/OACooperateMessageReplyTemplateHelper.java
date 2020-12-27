package com.qinzi123.dto.push.template.officialAccount;


import com.qinzi123.dto.CardMessageReply;
import org.springframework.stereotype.Component;

/**
 * Created by chenguifeng on 2019/11/5.
 */
@Component(value = "OACooperateReply")
public class OACooperateMessageReplyTemplateHelper extends OACooperateMessageTemplateHelper {

	@Override
	Object data(Object object) {
		OACooperateTemplate cooperateTemplate = new OACooperateTemplate();
		CardMessageReply cardMessageReply = (CardMessageReply)object;
		cooperateTemplate.setFirst(getFirst(cardMessageReply.getTitle()));
		cooperateTemplate.setKeyword1(getKeyword(cardMessageReply.getReplyMessage()));
		cooperateTemplate.setKeyword2(getKeyword(STATUS));
		cooperateTemplate.setRemark(getRemark(""));
		return cooperateTemplate;
	}
}
