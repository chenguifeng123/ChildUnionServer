package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/17.
 */
public interface BusinessWeixinService {
	// tagName 标签名称应该是 ID, SQL 比较复杂暂 先使用Name
	List<LinkedHashMap> listBusiness(String id, int start, int num, String search, Integer tagId);
	List<LinkedHashMap> oneBusiness(String id);
	int getIdByCode(String code);
	int addFollower(int userId, int followerId);
	int deleteFollower(int userId, int followerId);
	boolean hasFollowed(int userId, int followerId);
	List<LinkedHashMap> getAllService();

	int setUser(Map map);
	Map getCardTagById(String id);
	List<LinkedHashMap> getFollowerById(String current_id, String my_id);
	List<LinkedHashMap> getFansById(String current_id, String my_id);
	int batchAddFollower(int userId, String[] idList);

	int sign(int cardId);
	int hasScoreHistory(int cardId);
}
