package com.qinzi123.service.impl;

import com.qinzi123.dao.CampaignDao;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.PayScoreService;
import com.qinzi123.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/9/23.
 */
@Service
public class PayScoreServiceImpl extends AbstractWeixinService implements PayScoreService{

	private Logger log = LoggerFactory.getLogger(PayScoreServiceImpl.class);

	private static final String message = "用户 {}, 订单 {}, 扣除积分{}, 兑换 {}";

	@Autowired
	CampaignDao campaignDao;

	@Autowired
	ScoreService scoreService;

	@Override
	@Transactional
	public Map payScore(Map map) {
		log.info("积分兑换开始" + map.toString());
		checkUser(map);
		int card = Integer.parseInt(map.get("card").toString());
		int payment = Integer.parseInt(map.get("payment").toString());
		String orderId = map.get("id").toString();
		Map scoreMap = new HashMap<>();
		scoreMap.put("orderId", orderId);
		scoreMap.put("orderNo", map.get("order").toString());
		scoreMap.put("payment", payment);
		scoreMap.put("message", map.get("body").toString());
		int pay = campaignDao.addPaymentScore(scoreMap);
		if( pay >= 0){
			campaignDao.updateOrder(map);
			scoreService.minusScore(card, payment);
			log.info(message, card, payment, orderId, "成功");
		}else{
			log.info(message, card, payment, orderId, "失败");
			throw new GlobalProcessException("兑换失败");
		}
		return map;
	}

}
