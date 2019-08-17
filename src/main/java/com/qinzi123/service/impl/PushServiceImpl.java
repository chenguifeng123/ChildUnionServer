package com.qinzi123.service.impl;

import com.qinzi123.dto.*;
import com.qinzi123.dto.template.CooperateMessageReplyTemplateHelper;
import com.qinzi123.dto.template.CooperateMessageTemplateHelper;
import com.qinzi123.service.BusinessWeixinService;
import com.qinzi123.service.FormIdService;
import com.qinzi123.service.PushService;
import com.qinzi123.service.TokenService;
import com.qinzi123.util.Utils;
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
	FormIdService formIdService;

	@Autowired
	CooperateMessageTemplateHelper cooperateMessageTemplateHelper;

	@Autowired
	CooperateMessageReplyTemplateHelper cooperateMessageReplyTemplateHelper;

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
		return cooperateDao.addMessageSend(cardMessageSend);
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

	/**
	 * 发送消息给指定客户
	 * @param cardMessage
	 * @return
	 */
	public boolean pushMessage2OneUser(CardMessage cardMessage){
		// 批量拿formId, 发送失败就继续取一个
		List<WxSmallFormId> wxSmallFormIdList = formIdService.getCanUseSmallFormId(
				cardMessage.getCardId());
		cardMessage.setUpdateTime(Utils.getCurrentDate());
		for(WxSmallFormId wxSmallFormId : wxSmallFormIdList) {
			SendObject sendObject = cooperateMessageTemplateHelper.generateSendObject(
					cardMessage.getCardInfo().getOpenid(), wxSmallFormId.getFormId(),
					cardMessage, cardMessage);
			if (pushSendObject2OneUser(sendObject)) {
				logger.info("插入发送成功记录");
				addMessageSend(cardMessage, cardMessage.getId());
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
		List<WxSmallFormId> wxSmallFormIdList = formIdService.getCanUseSmallFormId(cardId);
		for(WxSmallFormId wxSmallFormId : wxSmallFormIdList){
			SendObject sendObject = cooperateMessageReplyTemplateHelper.generateSendObject(openId,
					wxSmallFormId.getFormId(), cardMessage, cardMessageReply);
			boolean result = pushSendObject2OneUser(sendObject);
			if(result){
				logger.info("插入发送成功记录");
				addMessageSend(cardMessageReply, cardMessageReply.getMessageId());
				wxSmallFormId.setIsUse(Use.USE.getIsUse());
				formIdService.updateFormId(wxSmallFormId);
				break;
			}else
				logger.info(String.format("该formId发送失败, %s", wxSmallFormId.toString()));
		}
		return true;
	}
}
