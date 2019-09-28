package com.qinzi123.service;

import java.util.Map;

/**
 * Created by chenguifeng on 2019/9/23.
 */
public interface RechargeMoneyService {
	Map prepay(Map map);
	Map payBack(String result);
}
