package com.qinzi123.dao;

import com.qinzi123.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/21.
 */
public interface CooperateDao {
	/** 商户合作等功能 **/
	int addMessage(CardMessage cardMessage);
	List<CardMessage> getAllCardMessage(@Param("start") int start, @Param("num")int num);
	List<String> getFansUser2Push(@Param("followerId") int followerId);
	int addMessageSend(CardMessageSend cardMessageSend);
	int addCurrentToken(WxSmallToken wxSmallToken);
	WxSmallToken getCurrentToken();
	List<CardMessage> getCardMessageByCardId(@Param("cardId") int cardId, @Param("start") int start, @Param("num")int num);
	CardMessage getCardMessageById(@Param("messageId") int messageId);

	int addFormId(WxSmallFormId wxSmallFormId);
	int updateFormId(WxSmallFormId wxSmallFormId);
	List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId);

	int addCardMessageReply(CardMessageReply cardMessageReply);
	List<CardMessageReply> getAllCardMessageReplyByMessageId(@Param("messageId") int messageId);
	CardMessageReply getCardMessageReplyById(@Param("replyId") int replyId);
}
