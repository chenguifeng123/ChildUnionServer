package com.qinzi123.service;

import com.qinzi123.dto.*;

import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public interface PushService {
	boolean pushCard2AllUser(Map card);
	boolean pushMessage2OneUser(CardMessage cardMessage);
	boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply);

	int addFormId(WxSmallFormId wxSmallFormId);
	int updateFormId(WxSmallFormId wxSmallFormId);

}
