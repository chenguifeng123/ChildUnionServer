package com.qinzi123.service.impl;

import com.qinzi123.dto.*;
import com.qinzi123.service.CooperateWeixinService;
import com.qinzi123.service.FormIdService;
import com.qinzi123.service.PushService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by chenguifeng on 2018/11/17.
 */
@Component
@EnableAsync
public class CooperateWeixinServiceImpl extends AbstractWeixinService implements CooperateWeixinService{

	@Autowired
	PushService pushService;

	@Autowired
	FormIdService formIdService;

	private Logger logger = LoggerFactory.getLogger(CooperateWeixinServiceImpl.class);

	/**
	 * 更新时间字段
	 * @param last
	 */
	private void refreshOneLast(BaseParam last){
		last.setLastString(Utils.getDateLast(last.getLast()));
	}
	/**
	 * 更新时间字段
	 * @param lastList
	 */
	private void refreshLast(List<? extends BaseParam> lastList){
		for(BaseParam last : lastList) refreshOneLast(last);
	}

	WxSmallFormId getOneWxSmallFormId(int cardId, String formId){
		WxSmallFormId wxSmallFormId = new WxSmallFormId();
		wxSmallFormId.setCardId(cardId);
		wxSmallFormId.setIsUse(Use.NOUSE.getIsUse());
		wxSmallFormId.setFormId(formId);
		return wxSmallFormId;
	}

	List<WxSmallFormId> generateWxSmallFormId(BaseParam cardMessage){
		List<WxSmallFormId> wxSmallFormIdList = new ArrayList<WxSmallFormId>(){{
			if(cardMessage.getFormIdList() != null)
				for(String fromId : cardMessage.getFormIdList()){
					add(getOneWxSmallFormId(cardMessage.getCardId(), fromId));
				}
			else
				add(getOneWxSmallFormId(cardMessage.getCardId(), cardMessage.getFormId()));
		}};
		return wxSmallFormIdList;

	}

	@Async
	void pushMessage(CardMessageReply cardMessageReply){
		logger.info("异步准备发送消息");
		pushService.pushMessageReply2OneUser(cardMessageReply);
	}

	/**
	 * 把消息发保存到数据库
	 * @param cardMessage
	 * @return
	 */
	public int addMessage(CardMessage cardMessage) {
		int result = cooperateDao.addMessage(cardMessage);
		logger.info("插入消息数据成功, 批量插入formId");
		for(WxSmallFormId wxSmallFormId: generateWxSmallFormId(cardMessage))
			formIdService.addFormId(wxSmallFormId);
		return result;
	}

	/**
	 * 阅读数
	 * @param id
	 * @return
	 */
	public int updateMessageRead(int id) {
		return cooperateDao.updateMessageRead(id);
	}

	/**
	 * 点赞数
	 * @param id
	 * @return
	 */
	public int updateMessageLike(int id) {
		return cooperateDao.updateMessageLike(id);
	}

	/**
	 * 获取所有数据
	 * @param start
	 * @param num
	 * @return
	 */
	public List<CardMessage> getAllCardMessage(int start, int num) {
		List<CardMessage> cardMessageList = cooperateDao.getAllCardMessage(start, num);
		refreshLast(cardMessageList);
		return cardMessageList;
	}

	/**
	 * 获取某个用户的 发布消息
	 * @param cardId
	 * @return
	 */
	@Override
	public List<CardMessage> getCardMessageByCardId(int cardId, int start, int num) {
		List<CardMessage> cardMessageList = cooperateDao.getCardMessageByCardId(cardId, start, num);
		refreshLast(cardMessageList);
		return cardMessageList;
	}

	/**
	 * 新增回复
	 * @param cardMessageReply
	 * @return
	 */
	public int addCardMessageReply(CardMessageReply cardMessageReply) {
		int result = cooperateDao.addCardMessageReply(cardMessageReply);
		logger.info("回复消息成功, 批量插入formId");
		for(WxSmallFormId wxSmallFormId: generateWxSmallFormId(cardMessageReply))
			formIdService.addFormId(wxSmallFormId);
		pushMessage(cardMessageReply);
		return result;
	}

	/**
	 * 返回当前消息的 回复数据
	 * @param messageId
	 * @return
	 */
	public List<CardMessageReply> getAllCardMessageReplyByMessageId(int messageId) {
		List<CardMessageReply> cardMessageReplyList = cooperateDao.getAllCardMessageReplyByMessageId(messageId);
		Map<Integer, CardMessageReply> map = new HashMap<>();
		for(CardMessageReply cardMessageReply : cardMessageReplyList){
			refreshOneLast(cardMessageReply);
			map.put(cardMessageReply.getId(), cardMessageReply);
			if(cardMessageReply.getReplyId() != 0)
				cardMessageReply.setReplyInfo(map.get(cardMessageReply.getReplyId()));
		}
		Collections.reverse(cardMessageReplyList);
		return cardMessageReplyList;
	}


}
