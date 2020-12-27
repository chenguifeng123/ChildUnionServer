package com.qinzi123.service.impl;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.push.template.OfficialAccountSendObject;
import com.qinzi123.dto.push.template.officialAccount.OACooperateMessageReplyTemplateHelper;
import com.qinzi123.dto.push.template.officialAccount.OACooperateMessageTemplateHelper;
import com.qinzi123.service.PushOfficialAccountService;
import com.qinzi123.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by chenguifeng on 2019/11/5.
 */
@Service
public class PushOfficialAccountServiceImpl extends AbstractWechatOfficialAccountService implements PushOfficialAccountService {

	private Logger logger = LoggerFactory.getLogger(PushOfficialAccountServiceImpl.class);

	// 暂时只发一个人的
	private String openid = "oT37RviPpsAf-eg2lEZ6YQobkuG0";

	@Autowired
	TokenService tokenService;

	@Resource(name = "OACooperateMessage")
	OACooperateMessageTemplateHelper messageTemplateHelper;

	@Resource(name = "OACooperateReply")
	OACooperateMessageReplyTemplateHelper replyTemplateHelper;

	/**
	 * 发送消息
	 * @param sendObject
	 * @return
 */
	private boolean pushSendObject2OneUser(OfficialAccountSendObject sendObject){
		return push2OneUser(getToken(), sendObject);
	}

	public boolean pushMessage2OneUser(CardMessage cardMessage) {
		logger.info("发送活动消息到公众号的个人");
		pushSendObject2OneUser(messageTemplateHelper.generateSendObject(
				openid, cardMessage));
		return true;
	}

	public boolean pushMessageReply2OneUser(CardMessageReply cardMessageReply) {
		logger.info("发送活动审批消息到公众号的个人");
		pushSendObject2OneUser(replyTemplateHelper.generateSendObject(
				openid, cardMessageReply));
		return true;
	}
}
