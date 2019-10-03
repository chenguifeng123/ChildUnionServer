package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/2/25.
 */
public interface CampaignService {
	List<LinkedHashMap> listCampaign(int start, int num);
	List<LinkedHashMap> oneCampaign(int id);

	List<LinkedHashMap> listOrder(int cardId, int start, int num);
	List<LinkedHashMap> oneOrder(int id);

	List<LinkedHashMap> listRechargeOrder(int cardId, int start, int num);
	List<LinkedHashMap> oneRechargeOrder(int id);

	int addOrder(Map map);
	int addPayOrder(Map map);

/*	int addProduct(WxProduct wxProduct);
	int updateProduct(WxProduct wxProduct);
	int deleteProduct(int productId);

	int addOrder();*/

}
