package com.qinzi123.service.impl;

import com.qinzi123.dto.WxSmallToken;
import com.qinzi123.service.TokenService;
import com.qinzi123.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by chenguifeng on 2018/11/18.
 */
@Component
@EnableScheduling
public class TokenServiceImpl extends AbstractWeixinService implements TokenService{

	private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);
	private static final long WX_EXPIRE = 7200L;

	private WxSmallToken current_token = new WxSmallToken();

	@Scheduled(cron = "0 0 */2 * * ?")
	void refreshToken(){
		//log.info("更新token");
		//current_token = getAccessToken();
	}

	/**
	 * 保存新的token
	 * @param token
	 * @return
	 */
	private WxSmallToken addCurrentToken(String token){
		log.info("新增新的token");
		WxSmallToken wxSmallToken = new WxSmallToken();
		wxSmallToken.setToken(token);
		pushDao.addCurrentToken(wxSmallToken);
		return wxSmallToken;
	}

	/**
	 * 获取当前的token
	 * @return
	 */
	private WxSmallToken getCurrentToken(){
		synchronized (current_token) {
			if (current_token.getId() == 0) {
				WxSmallToken result = pushDao.getCurrentToken();
				current_token = result != null ? result : addCurrentToken(getAccessToken());
			}
			long last = Utils.dateLast(current_token.getCreateTime());
			if (last >= WX_EXPIRE) {
				log.info("token失效");
				current_token = addCurrentToken(getAccessToken());
			}
			return current_token;
		}
	}

	public String getToken() {
		return getCurrentToken().getToken();
	}
}
