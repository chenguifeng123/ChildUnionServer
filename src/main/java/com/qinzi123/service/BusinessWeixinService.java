package com.qinzi123.service;

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

	public List<LinkedHashMap> listBusiness(String id, int start, int num){
		return weixinDao.listBusiness(id, start, num);
	}

	public List<LinkedHashMap> oneBusiness(String id){
		return weixinDao.oneBusiness(id);
	}

	public int addFollower(int userId, int followerId){
		return weixinDao.addFollower(userId, followerId);
	}

	public List<LinkedHashMap> getAllService(){
		return weixinDao.getAllService();
	}

	public int setUser(Map map){
		String code = map.get("code").toString();
		String openid = getOpenId(code);
		Map cardInfo =weixinDao.getCardInfoByOpenId(openid);
		map.put("openid", openid);
		String tag = map.get("tag").toString();
		String[] tagList = tag.split(",");
		int id = 0;
		for(int index = 0; index < tagList.length; index++){
			map.put("tag" + (index + 1), tagList[index]);
		}
		if(cardInfo == null || cardInfo.size() == 0){
			weixinDao.addCardInfo(map);
			id = Integer.parseInt(map.get("id").toString());
			map.put("card_id", id);
			weixinDao.addCardTag(map);
		}else{
			Map info = weixinDao.getCardInfoByOpenId(openid);
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
}
