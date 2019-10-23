package com.qinzi123.service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2019/10/23.
 */
public interface BannerService {
	public List<LinkedHashMap> listAllBanners();
	public List<LinkedHashMap> oneBanner(int id);
}
