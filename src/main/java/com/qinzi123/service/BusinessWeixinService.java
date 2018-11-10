package com.qinzi123.service;

import com.qinzi123.util.Utils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/10/4.
 */
@Service
public class BusinessWeixinService extends AbstractWeixinService{
	@Override
	protected String getAppId() {
		return "wx2f3e800fce3fd438";
	}

	@Override
	protected String getSecret() {
		return "fbe69347542e83483ea49e4e5fed2d3c";
	}

	public List<LinkedHashMap> listBusiness(String id, int start, int num, String search){
		if (search == null || search.trim().length() == 0)
			search = null;
		else
			search = String.format("%%%s%%", search);
		return weixinDao.listBusiness(id, start, num, search);
	}

	public List<LinkedHashMap> oneBusiness(String id){
		return weixinDao.oneBusiness(id);
	}

	public int getIdByCode(String code){
		String openid = getOpenId(code);
		Map map = weixinDao.getCardInfoByOpenId(openid);
		if (map == null || map.size() == 0) return -1;
		return Integer.parseInt(map.get("id").toString());
	}

	public int addFollower(int userId, int followerId){
		Map map = weixinDao.hasFollowered(userId, followerId);
		if(map != null && map.size() > 0) return 1;
		return weixinDao.addFollower(userId, followerId);
	}

	public int deleteFollower(int userId, int followerId){
		return weixinDao.deleteFollower(userId, followerId);
	}

	public List<LinkedHashMap> getAllService(){
		return weixinDao.getAllService();
	}

	public int setUser(Map map){
		// 获取 openid
		String code = map.get("code").toString();
		String openid = getOpenId(code);
		map.put("openid", openid);
		// 更新当前时间
		map.put("datetime", Utils.getCurrentDate());

		// 构建查询条件
		// 小程序和微信公众号共用一个表, openid 是两个
		//Map cardInfo =weixinDao.getCardInfoByOpenId(openid);
		String phone = map.get("phone").toString();
		String realname = map.get("realname").toString();
		Map cardInfo = weixinDao.getCardInfoByPhone(phone, realname);

		// 处理 标签,需要拆分成 3个标签
		String tag = map.get("tag").toString();
		String[] tagList = tag.split(",");
		int id = 0;
		for(int index = 0; index < tagList.length; index++){
			map.put("tag" + (index + 1), tagList[index]);
		}

		// 判断是更新还是插入
		if(cardInfo == null || cardInfo.size() == 0){
			weixinDao.addCardInfo(map);
			id = Integer.parseInt(map.get("id").toString());
			map.put("card_id", id);
			weixinDao.addCardTag(map);
		}else{
			//Map info = weixinDao.getCardInfoByOpenId(openid);
			Map info = weixinDao.getCardInfoByPhone(phone, realname);
			id = Integer.parseInt(info.get("id").toString());
			map.put("id", id);
			map.put("card_id", id);
			weixinDao.updateCardInfo(map);
			weixinDao.updateCardTag(map);
		}
		return id;
	}

	public Map getCardTagById(String id){
		return weixinDao.getCardTagById(id);
	}

	public List<LinkedHashMap> getFollowerById(String current_id, String my_id){
		return weixinDao.getFollowerById(current_id, my_id);
	}

	public List<LinkedHashMap> getFansById(String current_id, String my_id){
		return weixinDao.getFansById(current_id, my_id);
	}

	public int batchAddFollower(int userId, String[] idList){
		for(String id : idList)
			addFollower(userId, Integer.parseInt(id));
		return 1;
	}
}
