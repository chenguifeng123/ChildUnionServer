package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface WeixinDao {

	public List<LinkedHashMap> listBusiness(@Param("id") String id,
											@Param("start") int start,
											@Param("num") int num);
	public List<LinkedHashMap> oneBusiness(@Param("id") String id);

	public int addFollower(@Param("userId") int userId, @Param("followerId") int followerId);
	public Map hasFollowered(@Param("userId") int userId, @Param("followerId") int followerId);
	public List<LinkedHashMap> getAllService();

	public Map getCardInfoByOpenId(@Param("openid")String openid);
	public Map getCardInfoById(@Param("id")String id);
	public int addCardInfo(Map map);
	public int updateCardInfo(Map map);
	public int addCardTag(Map map);
	public int updateCardTag(Map map);
	public Map getCardTagById(@Param("card_id")String card_id);

	public List<LinkedHashMap> getFollowerById(@Param("current_id") String current_id, @Param("my_id") String my_id);
	public List<LinkedHashMap> getFansById(@Param("current_id") String current_id, @Param("my_id") String my_id);
}
