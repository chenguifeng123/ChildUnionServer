package com.qinzi123.service.impl;

import com.qinzi123.dto.TokenType;
import com.qinzi123.exception.GlobalProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWechatMiniProgramService extends AbstractWechatService{

	// 请求小程序用户的 URL
	private static final String QUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	// 小程序发送模板消息 URL
	private static final String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

	// 小程序发送订阅消息 URL
	private static final String SEND_SUBSCRIBE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s";

	// 微信支付 URL
	protected static final String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	protected static final String NOTIFY_URL = "https://www.qinzi123.com/order/callback";

	private Logger logger = LoggerFactory.getLogger(AbstractWechatMiniProgramService.class);

	protected HashMap<String, String> codeOpenIdMap = new HashMap<String, String>();

	/**
	 *  没有公众号,暂时 appid和 secret 还是统一的
	 */

	protected String getAppId(){
		return "wx2f3e800fce3fd438";
	}

	protected String getSecret(){
		return "fbe69347542e83483ea49e4e5fed2d3c";
	}

	protected String getSendUrl(){
		return SEND_URL;
	}

	protected String getMchId(){return "1527081391";}

	protected String getLocalIp() {return "121.43.58.101";}

	protected String getSessionUrl(String code){
		return String.format(QUEST_URL, getAppId(), getSecret(), code);
	}


	protected TokenType getTokenType(){
		return TokenType.MiniProgram;
	}
	/**
	 * 通过OpenID 找到系统用户
	 * @param openid 微信小程序的openid
	 * @return 系统用户编号
	 */
	protected int getIdByOpenId(String openid){
		List<Map> openidList = cardDao.getCardInfoByOpenId(openid);
		if(openidList.size() > 1) throw new GlobalProcessException("用户已经注册过");
		if(openidList.size() == 0) return -1;
		return Integer.parseInt(openidList.get(0).get("id").toString());
	}

	/**
	 * 检查支付的用户是否存在
	 * @param map
	 */
	protected Map checkUser(Map map){
		logger.info("检查用户是否存在, 如果存在则取出openid ");
		String card = map.get("card").toString();
		Map cardMap = cardDao.getCardInfoById(card);
		if(cardMap == null || cardMap.size() == 0 || cardMap.get("openid") == null)
			throw new GlobalProcessException("用户不存在");
		String openid = cardMap.get("openid").toString();
		logger.info("openid 为 " + openid);
		map.put("openid", openid);
		return map;
	}

	/**
	 * 获取 OpenId
	 * @param code
	 * @return
	 */
	String getOpenId(String code){
		logger.info(String.format("获取OPEN ID, 从code %s", code));
		String openId = codeOpenIdMap.get(code);
		if(openId != null) return openId;
		String getUrl = getSessionUrl(code);
		Map map= getJsonFromWeixin(getUrl);
		openId = map.get("openid") == null ? "" : map.get("openid").toString();
		if (openId.length() > 0) codeOpenIdMap.put(code, openId);
		return openId;
	}

}