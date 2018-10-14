package com.qinzi123.service;

import com.qinzi123.dao.WeixinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public abstract class AbstractWeixinService {

	private static final String QUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	protected HashMap<String, String> codeOpenIdMap = new HashMap<String, String>();
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	protected WeixinDao weixinDao;
	
	protected abstract String getAppId();
	protected abstract String getSecret();
	
	protected String getSessionUrl(String code){
		return String.format(QUEST_URL, getAppId(), getSecret(), code);
	}
	
	@SuppressWarnings("rawtypes")
	protected Map composeList(List<LinkedHashMap> list){
		return list.size() == 0 ? new HashMap() : list.get(0);
	}
	

	protected String getOpenId(String code){
		String openId = codeOpenIdMap.get(code); 
		if(openId != null) return openId;
		String getUrl = getSessionUrl(code);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		System.out.println(getUrl);
		Map map= restTemplate.getForObject(getUrl, Map.class);
		System.out.println(map);
		openId = map.get("openid") == null ? "" : map.get("openid").toString();
		if (openId.length() > 0) codeOpenIdMap.put(code, openId);
		return openId;
	}
	
}