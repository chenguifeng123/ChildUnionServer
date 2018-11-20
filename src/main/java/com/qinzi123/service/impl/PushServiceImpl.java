package com.qinzi123.service.impl;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageSend;
import com.qinzi123.dto.SendObject;
import com.qinzi123.dto.template.CooperateTemplateHelper;
import com.qinzi123.service.PushService;
import com.qinzi123.service.TokenService;
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
	TokenService tokenService;

	@Autowired
	CooperateTemplateHelper cooperateTemplateHelper;

	private List<String> getAllUser2Send(int followId){
		return weixinDao.getFansUser2Push(followId);
	}

	/**
	 * 增加发送消息的记录
	 * @param cardMessage
	 * @param openid
	 * @return
	 */
	private int addMessageSend(CardMessage cardMessage, String openid){
		CardMessageSend cardMessageSend = new CardMessageSend();
		cardMessageSend.setMessageId(cardMessage.getId());
		cardMessageSend.setOpenid(openid);
		return weixinDao.addMessageSend(cardMessageSend);
	}

	/**
	 * 发送消息给指定客户
	 * @param openid
	 * @param cardMessage
	 * @return
	 */
	public boolean pushMessage2OneUser(String openid, CardMessage cardMessage){
		logger.info(String.format("发送给客户 %s", openid));
		SendObject sendObject = cooperateTemplateHelper.generateSendObject(openid, cardMessage.getFormId(), cardMessage);
		Map result = sendTemplateMessage(tokenService.getToken(), sendObject);
		logger.info(String.format("发送结果:%s", result.toString()));
		if(result.get("errcode") != null){
			try {
				int errcode = Integer.parseInt(result.get("errcode").toString());
				if (errcode == 0) {
					logger.info("发送成功");
					addMessageSend(cardMessage, openid);
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
	 * 发送消息给, 消息体重用户的粉丝
	 * @param cardMessage
	 * @return
	 */
	public boolean pushMessage2User(CardMessage cardMessage) {
		logger.info("消息发送给自己");
		pushMessage2OneUser(cardMessage.getCardInfo().getOpenid(), cardMessage);
		logger.info("获取当前发消息用户的所有粉丝");
		List<String> openIdList = getAllUser2Send(cardMessage.getCardInfo().getId());
		for(String openid : openIdList){
			pushMessage2OneUser(openid, cardMessage);
		}
		return true;
	}
}
