package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/17.
 */
public interface CooperateWeixinService {
	int addMessage(CardMessage cardMessage);
	List<CardMessage> getAllCardMessage(int start, int num);
}

