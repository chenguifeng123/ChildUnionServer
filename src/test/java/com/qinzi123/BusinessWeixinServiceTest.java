package com.qinzi123;

import com.qinzi123.dao.CardDao;
import com.qinzi123.service.impl.BusinessWeixinServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chenguifeng on 2018/10/29.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BusinessWeixinServiceTest {

	@Autowired
	CardDao weixinDao;

	@Autowired
	private BusinessWeixinServiceImpl businessWeixinService;

	//@Ignore
	@Test
	public void testSetUser(){
/*		Map map = weixinDao.getCardInfoById("479");
		map.put("code", "000");
		map.put("tag", "1101");
		int result = businessWeixinService.setUser(map);
		Assert.assertTrue(result > 0);*/
	}
}
