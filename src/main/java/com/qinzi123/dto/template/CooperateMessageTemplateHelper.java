package com.qinzi123.dto.template;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.util.Utils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/20.
 */
@Component
public class CooperateMessageTemplateHelper extends AbstractTemplateHelper{
	String templateId() {
		return "OwhU78cHxk0TpyOR3N4ciOr8ciZ41ewmSOIbktvLGfc";
	}

	@Override
	String page(Object object) {
		CardMessage cardMessage = (CardMessage)object;
		Map<String, Object> map = new HashMap(){{
			put("id", cardMessage.getId());
			put("title", cardMessage.getTitle());
			put("message", cardMessage.getMessage());
			put("last", Utils.getDateLast(cardMessage.getLast()));
			put("read", cardMessage.getReadCount());
			put("like", cardMessage.getGiveLike());
			put("card", cardMessage.getCardId());
			put("phone", cardMessage.getCardInfo().getPhone());
			put("realname", cardMessage.getCardInfo().getRealname());
			put("job", cardMessage.getCardInfo().getJob());
			put("company", cardMessage.getCardInfo().getCompany());
			put("headimgurl", cardMessage.getCardInfo().getHeadimgurl());
		}};
		return Utils.fillUrlParams("pages/cooperate/oneMessage", map);
	}

	@Override
	String emphasis() {
		return "keyword1.DATA";
	}

	@Override
	Object data(Object object) {
		CardMessage cardMessage = (CardMessage)object;
		CooperateTemplate cooperateTemplate = new CooperateTemplate();
		//cooperateTemplate.setKeyword1(getKeyword(""));
		cooperateTemplate.setKeyword1(getKeyword(cardMessage.getTitle()));
		cooperateTemplate.setKeyword2(getKeyword(cardMessage.getMessage()));
		cooperateTemplate.setKeyword3(getKeyword(cardMessage.getCardInfo().getRealname()));
		cooperateTemplate.setKeyword4(getKeyword(cardMessage.getUpdateTime()));
		cooperateTemplate.setKeyword5(getKeyword(cardMessage.getCardInfo().getPhone()));
		return cooperateTemplate;
	}

}
