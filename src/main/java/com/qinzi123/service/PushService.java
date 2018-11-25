package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public interface PushService {
	boolean pushMessage2OneUser(CardMessage cardMessage);
	boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply);
}
