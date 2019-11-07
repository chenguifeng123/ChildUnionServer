package com.qinzi123.service;

import com.qinzi123.dto.TokenType;
import com.qinzi123.dto.WxSmallToken;

/**
 * Created by chenguifeng on 2018/11/18.
 */
public interface TokenService {
	WxSmallToken getToken(TokenType tokenType);
	boolean addCurrentToken(TokenType tokenType, String token);
}
