package com.qinzi123.service.impl;

import com.qinzi123.dao.CardDao;
import com.qinzi123.dao.CooperateDao;
import com.qinzi123.dao.PushDao;
import com.qinzi123.dto.SendObject;
import com.qinzi123.exception.GlobalProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public abstract class AbstractWeixinService {

	private static final String QUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	private static final String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";
	protected static final String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	protected static final String NOTIFY_URL = "https://www.qinzi123.com/order/callback";
	protected static final String MSG_CHECK_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=%s";

	private Logger logger = LoggerFactory.getLogger(AbstractWeixinService.class);

	protected HashMap<String, String> codeOpenIdMap = new HashMap<String, String>();

	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	CardDao cardDao;

	@Autowired
	CooperateDao cooperateDao;

	@Autowired
	PushDao pushDao;

	/**
	 *  没有公众号,暂时 appid和 secret 还是统一的
	 */

	protected String getAppId(){
		return "wx2f3e800fce3fd438";
	}

	protected String getSecret(){
		return "fbe69347542e83483ea49e4e5fed2d3c";
	}

	protected String getMchId(){return "1527081391";}

	protected String getLocalIp() {return "121.43.58.101";}

	protected String getSessionUrl(String code){
		return String.format(QUEST_URL, getAppId(), getSecret(), code);
	}

	protected String getTokenUrl(){
		return String.format(TOKEN_URL, getAppId(), getSecret());
	}

	@SuppressWarnings("rawtypes")
	protected Map composeList(List<LinkedHashMap> list){
		return list.size() == 0 ? new HashMap() : list.get(0);
	}

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
	 * 从微信获取json数据
	 * @param url
	 * @return
	 */
	Map getJsonFromWeixin(String url){
		logger.info(String.format("获取JSON 从url %s", url));
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map map= restTemplate.getForObject(url, Map.class);
		logger.info(map.toString());
		return map;
	}

	/**
	 * 推送Json数据到微信
	 * @param url
	 * @param object
	 * @return
	 */
	Map sendTemplate(String url, Object object){
		logger.info(String.format("发送对象 %s, 到 url %s ", object.toString(), url));
		Map result = restTemplate.postForObject(url, object, Map.class);
		logger.info(result.toString());
		return result;
	}

	/**
	 * 获取OpenId
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

	/**
	 * 获取token
	 * @return
	 */
	String getAccessToken(){
		String getUrl = getTokenUrl();
		Map map= getJsonFromWeixin(getUrl);
		String token = map.get("access_token") == null ? "" : map.get("access_token").toString();
		return token;
	}

	/**
	 * 发送模板消息到微信用户
	 * @param token
	 * @param sendObject
	 * @return
	 */
	Map sendTemplateMessage(String token, SendObject sendObject){
		return sendTemplate(String.format(SEND_URL, token), sendObject);
	}


	/**
	 * 发送模板检查到微信
	 * @param token
	 * @param content
	 * @return
	 */
	void checkMsg(String token, String content){
		Map map = sendTemplate(String.format(MSG_CHECK_URL, token),
				new HashMap<String, String>(){{
					put("content", content);
				}}
		);
		logger.info("微信消息检测结果, {}", map.toString());
		if(map == null || map.size() == 0) throw new GlobalProcessException("微信消息检测失败");
		if(!"0".equalsIgnoreCase(map.get("errcode").toString()))
			//throw new GlobalProcessException(map.get("errMsg").toString());
			throw new GlobalProcessException("内容含有违法违规内容");
	}

}