package com.qinzi123.service.impl;

import com.qinzi123.dao.CampaignDao;
import com.qinzi123.service.CampaignService;
import com.qinzi123.util.Utils;
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

	public int addOrder(Map map) {
		map.put("orderNo", Utils.getCurrentDateNoFlag());
		map.put("createTime", Utils.getCurrentDate());

		campaignDao.addOrder(map);
		int orderId = Integer.parseInt(map.get("id").toString());
		map.put("orderId", orderId);
		campaignDao.addOrderItem(map);

		return orderId;
	}
}
