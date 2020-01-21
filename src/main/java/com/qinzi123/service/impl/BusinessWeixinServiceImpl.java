package com.qinzi123.service.impl;

import com.qinzi123.dto.ScoreType;
import com.qinzi123.dto.WxCitys;
import com.qinzi123.exception.GlobalProcessException;
import com.qinzi123.service.BusinessWeixinService;
import com.qinzi123.service.PushMiniProgramService;
import com.qinzi123.service.ScoreService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/10/4.
 */
@Component
@EnableAsync
public class BusinessWeixinServiceImpl extends AbstractWechatMiniProgramService implements BusinessWeixinService{

	@Autowired
	ScoreService scoreService;

	@Autowired
	PushMiniProgramService pushService;

	private Logger logger = LoggerFactory.getLogger(BusinessWeixinServiceImpl.class);

	/**
	 * 列出所有商户, 可查询
	 * @param id 某用户
	 * @param start 开始记录
	 * @param num 查询条目
	 * @param search 搜索字段值
	 * @return
	 */
	public List<LinkedHashMap> listBusiness(String id, int start, int num,
											String search, Integer tagId, Integer city){
		if (search == null || search.trim().length() == 0)
			search = null;
		else
			search = String.format("%%%s%%", search);
		if (tagId == null || tagId == -1)
			tagId = null;
		if (city == null || city == -1)
			city = null;
		return cardDao.listBusiness(id, start, num, search, tagId, city);
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
		return getIdByOpenId(openid);
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

	private int loadUserId(Map map){
		// 构建查询条件
		// 小程序和微信公众号共用一个表, openid 是两个
		//Map cardInfo =cardDao.getCardInfoByOpenId(openid);
		logger.info("检查用户是否存在");
		String phone = map.get("phone").toString();
		String realname = map.get("realname").toString();
		List<Map> cardInfoMap = cardDao.getCardInfoByPhone(phone, realname);
		if(cardInfoMap != null && cardInfoMap.size() > 0) {
			if(cardInfoMap.size() > 1) throw new GlobalProcessException("相同的用户名和手机号码已存在");
			logger.info("用户已存在, {}", cardInfoMap.toString());
			return Integer.parseInt(cardInfoMap.get(0).get("id").toString());
		}
		return 0;
	}

	private Map makeUserMap(Map map){
		// 获取 openid
		String code = map.get("code").toString();
		String openid = getOpenId(code);
		map.put("openid", openid);
		// 更新当前时间
		map.put("datetime", Utils.getCurrentDate());
		// 保护用户如果没有输入性别
		if(map.get("gender") == null){
			map.put("gender", "0");
		}
		return map;
	}

	private Map makeTagMap(Map map){
		// 处理 标签,需要拆分成 3个标签
		String tag = map.get("tag").toString();
		String[] tagList = tag.split(",");
		int id = 0;
		for(int index = 0; index < tagList.length; index++){
			map.put("tag" + (index + 1), tagList[index]);
		}
		return map;
	}

	private void userPushMessage(Map map){
		int inviteId = Integer.parseInt(map.get("invite").toString());
		if(inviteId != -1) scoreService.addScore(inviteId, ScoreType.Invite);
		try{
			logger.info("注册成功，准备给所有用户推送消息");
			pushService.pushCard2AllUser(map);
		}catch (Exception e){
			logger.error("推送新注册用户，给所有用户失败", e);
		}
	}

	/**
	 * 更新用户信息
	 * @param map
	 * @return
	 */
	@Transactional
	public int setUser(Map map){
		int id = loadUserId(map);
		Map userMap = makeUserMap(map);
		Map tagMap = makeTagMap(map);
		if(id == 0){
			cardDao.addCardInfo(userMap);
			logger.info("新增用户成功, {}", userMap.toString());
			tagMap.put("card_id", Integer.parseInt(userMap.get("id").toString()));
			cardDao.addCardTag(tagMap);
			logger.info("新增用户标签成功, {}", tagMap.toString());
			userPushMessage(userMap);
		}else{
			userMap.put("id", id);
			tagMap.put("card_id", id);
			cardDao.updateCardInfo(userMap);
			logger.info("修改用户成功, {}", userMap.toString());
			cardDao.updateCardTag(tagMap);
			logger.info("修改用户标签成功, {}", tagMap.toString());
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
		cardDao.refreshCardDate(cardId);
		return 1;
	}

	public int hasScoreHistory(int cardId) {
		List<Map> list = cardDao.hasScoreHistory(cardId, ScoreType.Sign.getType());
		return list != null && list.size() > 0 ? 1 : 0;
	}

	@Override
	public List<WxCitys> listCitys() {
		return cardDao.listCitys();
	}
}
