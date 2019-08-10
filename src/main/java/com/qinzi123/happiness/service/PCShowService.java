package com.qinzi123.happiness.service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/8/10.
 */
public interface PCShowService {
	public List<Map<String, Object>> getCardInfo(String json);
	public List<Map<String, Object>> getBussinessCampaigns(String json);
	public List<Map<String, Object>> getAllBussinesseMessages(String json);
	public List<Map<String, Object>> getBussLeagues(String json);
	public List<Map<String, Object>> getVipLeaguers(String json);
	public List<Map<String, Object>> getBussinessCampaignsById(String json);
	public List<Map<String, Object>> getLeaguersById(String json);
	public Object getBusCampTotalRecords(String json);
	public Object getAllBusMesTotalRecords(String json);
	public Object getBussLeaguesTotalRecords(String json);
	public Object getLeaguersTotalRecords();
	public Object getCardTotalRecords();
}
