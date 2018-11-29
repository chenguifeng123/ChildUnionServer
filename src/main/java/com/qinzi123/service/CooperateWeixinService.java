package com.qinzi123.service;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/17.
 */
public interface CooperateWeixinService {
	int addMessage(CardMessage cardMessage);
	int updateMessageRead(int id);
	int updateMessageLike(int id);
	List<CardMessage> getAllCardMessage(int start, int num);
	List<CardMessage> getCardMessageByCardId(int cardId, int start, int num);

	int addCardMessageReply(CardMessageReply cardMessageReply);
	List<CardMessageReply> getAllCardMessageReplyByMessageId(int messageId);
}

