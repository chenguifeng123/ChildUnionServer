package com.qinzi123.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2019/10/23.
 */
public interface BannerDao {
	public List<LinkedHashMap> listAllBanners();
	public List<LinkedHashMap> oneBanner(int id);
}
