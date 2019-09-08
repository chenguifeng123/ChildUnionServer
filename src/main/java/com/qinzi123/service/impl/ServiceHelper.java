package com.qinzi123.service.impl;

import com.qinzi123.dto.CardInfo;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;

import java.util.Map;

/**
 * Created by chenguifeng on 2019/8/16.
 */
public class ServiceHelper {

	public static CardMessage convertMap2CardMessage(Map map, CardInfo cardInfo, String message){
		CardMessage cardMessage = new CardMessage();
		cardMessage.setId(Integer.parseInt(map.get("id").toString()));
		cardMessage.setCardId(Integer.parseInt(map.get("card_id").toString()));
		cardMessage.setCardInfo(cardInfo);
		cardMessage.setGiveLike(Integer.parseInt(map.get("give_like").toString()));
		cardMessage.setReadCount(Integer.parseInt(map.get("read_count").toString()));
		//设置通知消息标题
		//cardMessage.setTitle(message);
		cardMessage.setTitle(map.get("title").toString());
		cardMessage.setMessage(map.get("message").toString());

		return cardMessage;
	}

	public static CardMessageReply convertMap2CardMessageReply(Map map, String message){
		CardMessageReply cardMessageReply = new CardMessageReply();
		cardMessageReply.setId(Integer.parseInt(map.get("id").toString()));
		cardMessageReply.setCardId(Integer.parseInt(map.get("card_id").toString()));
		cardMessageReply.setMessageId(Integer.parseInt(map.get("message_id").toString()));
		//设置通知消息标题
		//cardMessageReply.setTitle(message);
		cardMessageReply.setTitle(map.get("reply_message").toString());
		//cardMessageReply.setReplyId(Integer.parseInt(map.get("reply_id").toString()));
		// 固定通知作者
		cardMessageReply.setReplyId(0);
		cardMessageReply.setReplyMessage(map.get("reply_message").toString());

		return cardMessageReply;
	}
}
