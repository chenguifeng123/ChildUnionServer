package com.qinzi123.service.impl;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.service.CooperateWeixinService;
import com.qinzi123.service.PushService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/17.
 */
@Component
@EnableAsync
public class CooperateWeixinServiceImpl extends AbstractWeixinService implements CooperateWeixinService{

	@Autowired
	PushService pushService;

	private Logger logger = LoggerFactory.getLogger(CooperateWeixinServiceImpl.class);

	/**
	 * 把消息发保存到数据库
	 * @param cardMessage
	 * @return
	 */
	public int addMessage(CardMessage cardMessage) {
		int result = weixinDao.addMessage(cardMessage);
		logger.info("插入消息数据成功, 准备发送消息");
		pushMessage(cardMessage);
		return result;
	}

	@Async
	void pushMessage(CardMessage cardMessage){
		logger.info("异步准备发送消息");
		//pushService.pushMessage2User(cardMessage);
	}

	/**
	 * 获取所有数据
	 * @param start
	 * @param num
	 * @return
	 */
	public List<CardMessage> getAllCardMessage(int start, int num) {
		List<CardMessage> cardMessageList = weixinDao.getAllCardMessage(start, num);
		for(CardMessage cardMessage : cardMessageList)
			cardMessage.setLastString(Utils.getDateLast(cardMessage.getLast()));
		return cardMessageList;
	}

}
