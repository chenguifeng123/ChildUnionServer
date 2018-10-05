package com.qinzi123.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2018/10/4.
 */
@Service
public class BusinessWeixinService extends AbstractWeixinService{
	@Override
	protected String getAppId() {
		return "wx2f3e800fce3fd438";
	}

	@Override
	protected String getSecret() {
		return null;
	}

	public List<LinkedHashMap> listBusiness(String id){
		return weixinDao.listBusiness(id);
	}

	public List<LinkedHashMap> oneBusiness(String id){
		return weixinDao.oneBusiness(id);
	}
}
