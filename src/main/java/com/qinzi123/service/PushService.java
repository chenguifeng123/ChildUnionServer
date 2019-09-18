package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.WxSmallFormId;

import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public interface PushService {
	boolean pushCard2AllUser(Map card);
	boolean pushMessage2OneUser(CardMessage cardMessage);
	boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply);

	int addFormId(WxSmallFormId wxSmallFormId);
	int batchAddFormId(int cardId, String[] formIdList);
	int updateFormId(WxSmallFormId wxSmallFormId);

}
