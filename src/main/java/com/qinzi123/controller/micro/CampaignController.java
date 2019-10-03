package com.qinzi123.controller.micro;

import com.qinzi123.service.CampaignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/10.
 */
@RestController
@Api(value = "活动", description = "活动")
public class CampaignController {

	@Autowired
	CampaignService campaignService;

	@RequestMapping(value = "/campaign/list", method = RequestMethod.POST)
	private List<LinkedHashMap> listCampaign(@RequestBody Map map){
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		return campaignService.listCampaign(start, num);
	}

	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET)
	private List<LinkedHashMap> oneCampaign(@PathVariable int id){
		return campaignService.oneCampaign(id);
	}

}
