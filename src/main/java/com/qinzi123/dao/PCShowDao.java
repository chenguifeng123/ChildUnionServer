package com.qinzi123.dao;

import com.qinzi123.happiness.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenguifeng on 2019/8/10.
 */
public interface PCShowDao {
	public List<CardInfo> getCardInfo(@Param("start") int start, @Param("pageSize") int pageSize);
	public List<BussinessPcShowCampaigns> getBussinessCampaigns(@Param("start") int start, @Param("pageSize") int pageSize);
	public List<BussinessMessage> getAllBussinesseMessages(@Param("start") int start, @Param("pageSize") int pageSize);
	public List<BussLeagueInfo> getBussLeagues(@Param("start") int start, @Param("pageSize") int pageSize);
	public List<BussLeagueInfo> getLeaguers(@Param("start") int start, @Param("pageSize") int pageSize);
	public List<CampaignPcshowInfo> getBussinessCampaignsById(@Param("id") int id);
	public  List<BussLeagueInfo> getMapLeaguersById(@Param("id")int id);

	public List getLeaguersTotalRecords();

	public List getBusCampTotalRecords();

	public List getBussLeaguesTotalRecords();

	public List getAllBusMesTotalRecords();

	public List<CardInfo> queryCardInfos();

	public List<BussLeagueCooperate> getBussLeagueCooperateById(@Param("id") int id);

	public List getCardTotalRecords();

	public List<BussinessPcShowCampaigns> searchCampaigns(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

	public List<BussinessMessage> searchBussinesseMessages(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

	public List<BussLeagueInfo> searchVipLeaguers(@Param("str") String str, @Param("start") int start, @Param("pageSize") int pageSize);

	public List getSearCampTotalRecords(@Param("json") String json);

	public List getSearBusMesTotalRecords(@Param("json") String json);

	public List getSearLeaguesTotalRecords(@Param("key") String key);
}
