package com.qinzi123.service;

import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/17.
 */
public interface PayService {
	Map prepay(Map map);
	Map payBack(String result);
}
