package com.qinzi123.dto.template;

import com.qinzi123.util.Utils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/8/23.
 */
@Component
public class CardInfoTemplateHelper extends AbstractTemplateHelper{

	@Override
	String templateId() {
		return "jrdTdI5M8zeqHVBA-utX6J9SZaQJG4uPGPNBpOPxIFc";
	}

	@Override
	String page(Object object) {
		Map card = (Map)object;
		Map<String, Object> map = new HashMap(){{
			put("id", Integer.parseInt(card.get("id").toString()));
			put("isFollowed", 0);
		}};
		return Utils.fillUrlParams("pages/business/oneBusiness", map);
	}

	@Override
	String emphasis() {
		return null;
	}

	@Override
	Object data(Object object) {
		Map card = (Map)object;
		CardInfoTemplate cardInfoTemplate = new CardInfoTemplate();
		cardInfoTemplate.setKeyword1(getKeyword(card.get("realname").toString()));
		cardInfoTemplate.setKeyword2(getKeyword(card.get("phone").toString()));
		cardInfoTemplate.setKeyword3(getKeyword(card.get("company").toString()));
		return cardInfoTemplate;
	}
}
