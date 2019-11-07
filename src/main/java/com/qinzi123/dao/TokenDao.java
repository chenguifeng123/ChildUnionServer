package com.qinzi123.dao;

import com.qinzi123.dto.WxSmallToken;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public interface TokenDao {
	int addCurrentToken(WxSmallToken wxSmallToken);
	WxSmallToken getCurrentToken(int type);
}
