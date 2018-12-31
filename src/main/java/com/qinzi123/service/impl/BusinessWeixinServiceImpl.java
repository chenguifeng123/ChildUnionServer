package com.qinzi123.service.impl;

import com.qinzi123.dto.ScoreType;
import com.qinzi123.service.BusinessWeixinService;
import com.qinzi123.service.ScoreService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/10/4.
 */
@Component
@EnableAsync
public class BusinessWeixinServiceImpl extends AbstractWeixinService implements BusinessWeixinService{

	@Autowired
	ScoreService scoreService;

	private Logger logger = LoggerFactory.getLogger(BusinessWeixinServiceImpl.class);

	/**
	 * 列出所有商户, 可查询
	 * @param id 某用户
	 * @param start 开始记录
	 * @param num 查询条目
	 * @param search 搜索字段值
	 * @return
	 */
	public List<LinkedHashMap> listBusiness(String id, int start, int num, String search){
		if (search == null || search.trim().length() == 0)
			search = null;
		else
			search = String.format("%%%s%%", search);
		return cardDao.listBusiness(id, start, num, search);
	}

	/**
	 * 查询某个商户情况
	 * @param id 商户ID
	 * @return
	 */
	public List<LinkedHashMap> oneBusiness(String id){
		return cardDao.oneBusiness(id);
	}

	/**
	 * 根据 code 获取 card id 值
	 * @param code
	 * @return
	 */
	public int getIdByCode(String code){
		String openid = getOpenId(code);
		Map map = cardDao.getCardInfoByOpenId(openid);
		if (map == null || map.size() == 0) return -1;
		return Integer.parseInt(map.get("id").toString());
	}

	/**
	 * 是否关注过
	 * @param userId
	 * @param followerId
	 * @return
	 */
	public boolean hasFollowed(int userId, int followerId) {
		Map map = cardDao.hasFollowed(userId, followerId);
		return (map != null && map.size() > 0);
	}

	/**
	 * 关注操作
	 * @param userId
	 * @param followerId
	 * @return
	 */
	public int addFollower(int userId, int followerId){
		return !hasFollowed(userId, followerId) ?
				cardDao.addFollower(userId, followerId) : 1;
	}

	/**
	 * 取消关注
	 * @param userId
	 * @param followerId
	 * @return
	 */
	public int deleteFollower(int userId, int followerId){
		return cardDao.deleteFollower(userId, followerId);
	}

	/**
	 * 获取所有服务的 标签 tag
	 * @return
	 */
	public List<LinkedHashMap> getAllService(){
		return cardDao.getAllService();
	}

	/**
	 * 更新用户信息
	 * @param map
	 * @return
	 */
	public int setUser(Map map){
		// 获取 openid
		String code = map.get("code").toString();
		String openid = getOpenId(code);
		map.put("openid", openid);
		// 更新当前时间
		map.put("datetime", Utils.getCurrentDate());

		// 构建查询条件
		// 小程序和微信公众号共用一个表, openid 是两个
		//Map cardInfo =cardDao.getCardInfoByOpenId(openid);
		String phone = map.get("phone").toString();
		String realname = map.get("realname").toString();
		Map cardInfo = cardDao.getCardInfoByPhone(phone, realname);

		// 处理 标签,需要拆分成 3个标签
		String tag = map.get("tag").toString();
		String[] tagList = tag.split(",");
		int id = 0;
		for(int index = 0; index < tagList.length; index++){
			map.put("tag" + (index + 1), tagList[index]);
		}

		// 判断是更新还是插入
		if(cardInfo == null || cardInfo.size() == 0){
			cardDao.addCardInfo(map);
			id = Integer.parseInt(map.get("id").toString());
			map.put("card_id", id);
			cardDao.addCardTag(map);
			if(map.get("invite") != null){
				int inviteId = Integer.parseInt(map.get("invite").toString());
				if(inviteId != -1) scoreService.addScore(inviteId, ScoreType.Invite);
			}
		}else{
			//Map info = cardDao.getCardInfoByOpenId(openid);
			Map info = cardDao.getCardInfoByPhone(phone, realname);
			id = Integer.parseInt(info.get("id").toString());
			map.put("id", id);
			map.put("card_id", id);
			cardDao.updateCardInfo(map);
			cardDao.updateCardTag(map);
		}
		return id;
	}

	/**
	 * 根据 用户id获取 标签
	 * @param id
	 * @return
	 */
	public Map getCardTagById(String id){
		return cardDao.getCardTagById(id);
	}

	/**
	 * 获取某人 关注的商户列表, 同时需要提取我的关注信息
	 * @param current_id 某商户
	 * @param my_id 我
	 * @return
	 */
	public List<LinkedHashMap> getFollowerById(String current_id, String my_id){
		return cardDao.getFollowerById(current_id, my_id);
	}

	/**
	 * 获取关注 某人的粉丝, 同时需要提取我的关注信息
	 * @param current_id 某商户
	 * @param my_id 我
	 * @return
	 */
	public List<LinkedHashMap> getFansById(String current_id, String my_id){
		return cardDao.getFansById(current_id, my_id);
	}

	/**
	 * 批量关注商户
	 * @param userId
	 * @param idList
	 * @return
	 */
	public int batchAddFollower(int userId, String[] idList){
		for(String id : idList)
			addFollower(userId, Integer.parseInt(id));
		return 1;
	}

	/**
	 * 签到积分
	 * @param cardId
	 * @return
	 */
	public int sign(int cardId) {
		scoreService.addScore(cardId, ScoreType.Sign);
		return 1;
	}

	public int hasScoreHistory(int cardId) {
		List<Map> list = cardDao.hasScoreHistory(cardId, ScoreType.Sign.getType());
		return list != null && list.size() > 0 ? 1 : 0;
	}
}
