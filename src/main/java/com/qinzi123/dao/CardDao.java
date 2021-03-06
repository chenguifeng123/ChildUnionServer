package com.qinzi123.dao;

import com.qinzi123.dto.CardInfo;
import com.qinzi123.dto.WxCitys;
import com.qinzi123.dto.WxOneCity;
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
											@Param("tagId") Integer tagId,
											@Param("city") Integer city);
	public int countBusiness(@Param("city") Integer city);
	public List<LinkedHashMap> oneBusiness(@Param("id") String id);

	public int addFollower(@Param("userId") int userId, @Param("followerId") int followerId);
	public int deleteFollower(@Param("userId") int userId, @Param("followerId") int followerId);
	public Map hasFollowed(@Param("userId") int userId, @Param("followerId") int followerId);
	public List<LinkedHashMap> getAllService();

	public List<Map> getCardInfoByOpenId(@Param("openid")String openid);
	public Map getCardInfoById(@Param("id")String id);
	public CardInfo getCardInfoBeanById(@Param("id")int id);
	public List<Map> getCardInfoByPhone(@Param("phone")String phone, @Param("realname")String realname);
	public int addCardInfo(Map map);
	public int updateCardInfo(Map map);
	int refreshCardDate(@Param("id") int id);
	public int addCardTag(Map map);
	public int updateCardTag(Map map);
	public Map getCardTagById(@Param("card_id")String card_id);

	public List<LinkedHashMap> getFollowerById(@Param("current_id") String current_id, @Param("my_id") String my_id);
	public List<LinkedHashMap> getFansById(@Param("current_id") String current_id, @Param("my_id") String my_id);

	public int minusScore(Map map);
	public int addScore(Map map);
	public int addScoreHistory(Map map);
	public int addShowScoreHistory(Map map);
	public List<Map> hasScoreHistory(@Param("card_id") int card_id, @Param("score_type") int score_type);

	List<WxCitys> listCitys();
	WxOneCity oneCity(@Param("id") int id);
}
