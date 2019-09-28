package com.qinzi123.service.impl;

import com.qinzi123.dao.CampaignDao;
import com.qinzi123.dto.OrderType;
import com.qinzi123.service.CampaignService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/2/25.
 */
@Service
public class CampaignServiceImpl extends AbstractWeixinService implements CampaignService {

	private Logger log = LoggerFactory.getLogger(CampaignServiceImpl.class);

	@Autowired
	CampaignDao campaignDao;

	public List<LinkedHashMap> listCampaign(int start, int num) {
		return campaignDao.listCampaign(start, num);
	}

	public List<LinkedHashMap> oneCampaign(int id) {
		return campaignDao.oneCampaign(id);
	}

	public List<LinkedHashMap> listOrder(int cardId, int start, int num) {
		return campaignDao.listOrder(cardId, start, num);
	}

	public List<LinkedHashMap> oneOrder(int id) {
		return campaignDao.oneOrder(id);
	}

	private int addOrder(Map map, OrderType orderType){
		log.info("增加订单" + map.toString());
		map.put("orderNo", Utils.getCurrentDateNoFlag());
		map.put("createTime", Utils.getCurrentDate());
		map.put("order_type", orderType.getType());

		campaignDao.addOrder(map);
		int orderId =  Integer.parseInt(map.get("id").toString());
		map.put("orderId", orderId);
		campaignDao.addOrderItem(map);
		return orderId;
	}

	@Override
	public int addOrder(Map map) {
		log.info("*******************增加积分订单*******************");
		return addOrder(map, OrderType.SCORE);
	}

	@Override
	public int addPayOrder(Map map) {
		log.info("*******************增加支付订单*******************");
		return addOrder(map, OrderType.PAY);
	}
}
