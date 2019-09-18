package com.qinzi123.service.impl;

import com.qinzi123.dto.*;
import com.qinzi123.dto.template.CardInfoTemplateHelper;
import com.qinzi123.dto.template.CooperateMessageReplyTemplateHelper;
import com.qinzi123.dto.template.CooperateMessageTemplateHelper;
import com.qinzi123.service.BusinessWeixinService;
import com.qinzi123.service.PushService;
import com.qinzi123.service.TokenService;
import com.qinzi123.util.Utils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/19.
 */
@Component
public class PushServiceImpl extends AbstractWeixinService implements PushService {

	private Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

	@Autowired
	BusinessWeixinService businessWeixinService;

	@Autowired
	TokenService tokenService;

	@Autowired
	CardInfoTemplateHelper cardInfoTemplateHelper;

	@Autowired
	CooperateMessageTemplateHelper cooperateMessageTemplateHelper;

	@Autowired
	CooperateMessageReplyTemplateHelper cooperateMessageReplyTemplateHelper;

	public List<String> getFansUser2Push(@Param("followerId") int followerId) {
		return pushDao.getFansUser2Push(followerId);
	}

	public int addCardSend(CardInfoSend cardInfoSend) {
		return pushDao.addCardSend(cardInfoSend);
	}

	@Override
	public int addFormId(WxSmallFormId wxSmallFormId) {
		logger.info(String.format("插入新的formId %s", wxSmallFormId.toString()));
		return pushDao.addFormId(wxSmallFormId);
	}

	@Override
	public int batchAddFormId(int cardId, String[] formIdList) {
		logger.info("批量插入新的formI ");
		WxSmallFormId wxSmallFormId = new WxSmallFormId();
		wxSmallFormId.setIsUse(0);
		wxSmallFormId.setCardId(cardId);
		for(String formId : formIdList){
			wxSmallFormId.setFormId(formId);
			pushDao.addFormId(wxSmallFormId);
		}
		return 1;
	}

	public int updateFormId(WxSmallFormId wxSmallFormId) {
		logger.info(String.format("更新formId %s", wxSmallFormId.toString()));
		return pushDao.updateFormId(wxSmallFormId);
	}

	public List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId) {
		logger.info(String.format("获取%d可用formId列表", cardId));
		return pushDao.getCanUseSmallFormId(cardId);
	}

	public List<Map> getEveryUserCanUseSmallFormId() {
		logger.info("获取所有可用formId列表");
		return pushDao.getEveryUserCanUseSmallFormId();
	}

	/**
	 * 增加发送消息的记录
	 * @param baseParam
	 * @param messageId
	 * @return
	 */
	private int addMessageSend(BaseParam baseParam, int messageId){
		CardMessageSend cardMessageSend = new CardMessageSend();
		cardMessageSend.setMessageId(messageId);
		cardMessageSend.setOpenid(baseParam.getCardInfo().getOpenid());
		cardMessageSend.setCardId(baseParam.getCardId());
		return pushDao.addMessageSend(cardMessageSend);
	}

	/**
	 * 发送消息
	 * @param sendObject
	 * @return
	 */
	private boolean pushSendObject2OneUser(SendObject sendObject){
		logger.info(String.format("发送消息{ %s }", sendObject.toString()));
		Map result = sendTemplateMessage(tokenService.getToken(), sendObject);
		logger.info(String.format("发送结果:%s", result.toString()));
		if(result.get("errcode") != null){
			try {
				int errcode = Integer.parseInt(result.get("errcode").toString());
				if (errcode == 0) {
					logger.info("发送成功");
					return true;
				}
			}catch (Exception e){
				// 发送一个失败, 不影响其他发送
				logger.error("异常消息", e);
			}
		}
		logger.info("发送失败");
		return false;
	}

	@Override
	public boolean pushCard2AllUser(Map cardMap) {
		// 批量拿formId, 发送失败就继续取一个
		List<Map> cardFormList = getEveryUserCanUseSmallFormId();
		for(Map cardFormMap : cardFormList) {
			try {
				SendObject sendObject = cardInfoTemplateHelper.generateSendObject(
						cardFormMap.get("openid").toString(),
						cardFormMap.get("form_id").toString(),
						cardMap, cardMap);
				if (pushSendObject2OneUser(sendObject)) {
					logger.info("插入发送成功记录");
					CardInfoSend cardInfoSend = new CardInfoSend();
					cardInfoSend.setCardId(Integer.parseInt(cardFormMap.get("card_id").toString()));
					cardInfoSend.setNewCardId(Integer.parseInt(cardMap.get("card_id").toString()));
					cardInfoSend.setOpenid(cardFormMap.get("openid").toString());
					addCardSend(cardInfoSend);

					WxSmallFormId wxSmallFormId = new WxSmallFormId();
					wxSmallFormId.setId(Integer.parseInt(cardFormMap.get("id").toString()));
					wxSmallFormId.setIsUse(1);
					updateFormId(wxSmallFormId);
				}
			}catch (Exception e){
				logger.error("当前记录发送失败" + cardFormMap.toString(), e);
			}
		}
		return true;
	}

	/**
	 * 发送消息给指定客户
	 * @param cardMessage
	 * @return
	 */
	public boolean pushMessage2OneUser(CardMessage cardMessage){
		// 批量拿formId, 发送失败就继续取一个
		List<WxSmallFormId> wxSmallFormIdList = getCanUseSmallFormId(
				cardMessage.getCardId());
		cardMessage.setUpdateTime(Utils.getCurrentDate());
		for(WxSmallFormId wxSmallFormId : wxSmallFormIdList) {
			SendObject sendObject = cooperateMessageTemplateHelper.generateSendObject(
					cardMessage.getCardInfo().getOpenid(), wxSmallFormId.getFormId(),
					cardMessage, cardMessage);
			if (pushSendObject2OneUser(sendObject)) {
				logger.info("插入发送成功记录");
				addMessageSend(cardMessage, cardMessage.getId());
				updateFormId(wxSmallFormId);
				break;
			}
		}
		return true;
	}

	/**
	 * 发送回复消息给用户
	 * @param cardMessageReply
	 * @return
	 */
	public boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply) {
		logger.info("发送模板, 先取到消息体");
		// 此处的对象是 用户推过来的, 很多数据库信息还没填好
		CardMessage cardMessage = cooperateDao.getCardMessageById(cardMessageReply.getMessageId());
		CardInfo cardInfo = cardDao.getCardInfoBeanById(cardMessageReply.getCardId());
		cardMessageReply.setCardInfo(cardInfo);
		cardMessageReply.setCreateTime(Utils.getCurrentDate());

		// 拿到发送的用户
		String openId = "";
		int cardId = 0;
		if(cardMessageReply.getReplyId() != 0){
			logger.info("有回复ID, 发送给回复的人");
			int replyId = cardMessageReply.getReplyId();
			CardMessageReply reply = cooperateDao.getCardMessageReplyById(replyId);
			cardId = reply.getCardId();
			CardInfo replyCardInfo = cardDao.getCardInfoBeanById(cardId);
			openId = replyCardInfo.getOpenid();
		}else{
			logger.info("没有回复ID, 发送给作者");
			cardId = cardMessage.getCardId();
			openId = cardMessage.getCardInfo().getOpenid();
		}

		// 批量拿formId, 发送失败就继续取一个
		List<WxSmallFormId> wxSmallFormIdList = getCanUseSmallFormId(cardId);
		for(WxSmallFormId wxSmallFormId : wxSmallFormIdList){
			SendObject sendObject = cooperateMessageReplyTemplateHelper.generateSendObject(openId,
					wxSmallFormId.getFormId(), cardMessage, cardMessageReply);
			boolean result = pushSendObject2OneUser(sendObject);
			if(result){
				logger.info("插入发送成功记录");
				addMessageSend(cardMessageReply, cardMessageReply.getMessageId());
				wxSmallFormId.setIsUse(Use.USE.getIsUse());
				updateFormId(wxSmallFormId);
				break;
			}else
				logger.info(String.format("该formId发送失败, %s", wxSmallFormId.toString()));
		}
		return true;
	}
}
