package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/10.
 */
public interface CampaignDao {

	public List<LinkedHashMap> listCampaign(@Param("start") int start,
											@Param("num") int num);
	public List<LinkedHashMap> oneCampaign(@Param("id") int id);

	public List<LinkedHashMap> listOrder(@Param("cardId") int cardId,
										 @Param("start") int start,
										 @Param("num") int num);
	public List<LinkedHashMap> oneOrder(@Param("id") int id);

	public int addOrder(Map map);
	public int addOrderItem(Map map);
	public int addPayment(Map map);
	public int addPaymentScore(Map map);
	public int updateOrder(Map map);
}
