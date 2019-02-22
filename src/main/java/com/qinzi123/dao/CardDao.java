package com.qinzi123.dao;

import com.qinzi123.dto.CardInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface CardDao {

	/** 商户名单等功能 **/

	public List<LinkedHashMap> listBusiness(@Param("id") String id,
											@Param("start") int start,
											@Param("num") int num,
											@Param("search") String search,
											@Param("tagId") Integer tagId);
	public List<LinkedHashMap> oneBusiness(@Param("id") String id);

	public int addFollower(@Param("userId") int userId, @Param("followerId") int followerId);
	public int deleteFollower(@Param("userId") int userId, @Param("followerId") int followerId);
	public Map hasFollowed(@Param("userId") int userId, @Param("followerId") int followerId);
	public List<LinkedHashMap> getAllService();

	public Map getCardInfoByOpenId(@Param("openid")String openid);
	public Map getCardInfoById(@Param("id")String id);
	public CardInfo getCardInfoBeanById(@Param("id")int id);
	public Map getCardInfoByPhone(@Param("phone")String phone, @Param("realname")String realname);
	public int addCardInfo(Map map);
	public int updateCardInfo(Map map);
	public int addCardTag(Map map);
	public int updateCardTag(Map map);
	public Map getCardTagById(@Param("card_id")String card_id);

	public List<LinkedHashMap> getFollowerById(@Param("current_id") String current_id, @Param("my_id") String my_id);
	public List<LinkedHashMap> getFansById(@Param("current_id") String current_id, @Param("my_id") String my_id);

	public int updateScore(Map map);
	public int addScoreHistory(Map map);
	public List<Map> hasScoreHistory(@Param("card_id") int card_id, @Param("score_type") int score_type);


}
