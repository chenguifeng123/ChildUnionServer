package com.qinzi123;

import com.qinzi123.dao.WeixinDao;
import com.qinzi123.dto.CardMessage;
import com.qinzi123.service.CooperateWeixinService;
import com.qinzi123.service.PushService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/20.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PushServiceTest {

	@Autowired
	WeixinDao weixinDao;

	@Autowired
	PushService pushService;

	@Autowired
	CooperateWeixinService cooperateWeixinService;

	CardMessage getCardMessage(){
		List<CardMessage> cardMessageList = cooperateWeixinService.getAllCardMessage(0, 1);
		if(cardMessageList == null || cardMessageList.size() == 0) return null;
		return cardMessageList.get(0);
	}

	@Test
	public void testPushOneMessage(){
		CardMessage cardMessage = getCardMessage();
		if(cardMessage != null) {
			Map map = weixinDao.getCardInfoById("479");
			String openid = map.get("openid").toString();
			boolean result = pushService.pushMessage2OneUser(openid, cardMessage);
			//Assert.assertTrue(result);
		}

	}

	@Test
	public void testPushAllMessage(){
		CardMessage cardMessage = getCardMessage();
		if(cardMessage != null) {
			boolean result = pushService.pushMessage2User(cardMessage);
			Assert.assertTrue(result);
		}
	}
}
