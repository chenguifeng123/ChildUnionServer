package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public interface PushService {
	boolean pushMessage2OneUser(String openid, CardMessage cardMessage);
	boolean pushMessage2User(CardMessage cardMessage);
}
