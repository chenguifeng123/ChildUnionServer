package com.qinzi123;

import com.qinzi123.dao.WeixinDao;
import com.qinzi123.service.BusinessWeixinService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by chenguifeng on 2018/10/29.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessWeixinServiceTest {

	@Autowired
	WeixinDao weixinDao;

	@Autowired
	private BusinessWeixinService businessWeixinService;

	@Test
	public void testSetUser(){
		Map map = weixinDao.getCardInfoById("479");
		map.put("code", "000");
		map.put("tag", "1101");
		int result = businessWeixinService.setUser(map);
		Assert.assertTrue(result > 0);
	}
}
