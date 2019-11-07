package com.qinzi123.service.impl;

import com.qinzi123.dao.BannerDao;
import com.qinzi123.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2019/10/23.
 */
@Service
public class BannerServiceImpl extends AbstractWechatMiniProgramService implements BannerService {


	@Autowired
	BannerDao bannerDao;

	public List<LinkedHashMap> listAllBanners() {
		return bannerDao.listAllBanners();
	}

	public List<LinkedHashMap> oneBanner(int id) {
		return bannerDao.oneBanner(id);
	}
}
