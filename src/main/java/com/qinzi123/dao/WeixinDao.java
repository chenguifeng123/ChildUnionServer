package com.qinzi123.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface WeixinDao {

	public List<LinkedHashMap> listBusiness(@Param("id") String id);
	public List<LinkedHashMap> oneBusiness(@Param("id") String id);

	public int addFollower(@Param("userId") int userId, @Param("followerId") int followerId);
}
