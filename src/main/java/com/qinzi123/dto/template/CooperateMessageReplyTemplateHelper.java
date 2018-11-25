package com.qinzi123.dto.template;

import com.qinzi123.dto.CardMessageReply;
import org.springframework.stereotype.Component;

/**
 * Created by chenguifeng on 2018/11/24.
 */
@Component
public class CooperateMessageReplyTemplateHelper extends CooperateMessageTemplateHelper {

	@Override
	Object data(Object object) {
		CardMessageReply cardMessageReply = (CardMessageReply)object;
		CooperateTemplate cooperateTemplate = new CooperateTemplate();
		cooperateTemplate.setKeyword1(getKeyword("有新回复消息"));
		cooperateTemplate.setKeyword2(getKeyword(cardMessageReply.getReplyMessage()));
		cooperateTemplate.setKeyword3(getKeyword(cardMessageReply.getCardInfo().getRealname()));
		cooperateTemplate.setKeyword4(getKeyword(cardMessageReply.getCreateTime()));
		cooperateTemplate.setKeyword5(getKeyword(cardMessageReply.getCardInfo().getPhone()));
		return cooperateTemplate;
	}
}
