package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public interface PushOfficialAccountService {
	boolean pushMessage2OneUser(CardMessage cardMessage);
	boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply);
}
