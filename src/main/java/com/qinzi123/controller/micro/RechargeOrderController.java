package com.qinzi123.controller.micro;

import com.qinzi123.service.CampaignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/10/3.
 */
@RestController
@Api(value = "充值", description = "充值")
public class RechargeOrderController {

	private static final int PRODUCT_ID = 1;
	private static final String DEFAULT_PRODUCT = "productId";

	@Autowired
	CampaignService campaignService;

	@RequestMapping(value = "/rechargeOrder/list", method = RequestMethod.POST)
	private List<LinkedHashMap> listRechargeOrder(@RequestBody Map map){
		int card = Integer.parseInt(map.get("card").toString());
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		return campaignService.listRechargeOrder(card, start, num);
	}

	@RequestMapping(value = "/rechargeOrder/{id}", method = RequestMethod.GET)
	private List<LinkedHashMap> oneRechargeOrder(@PathVariable int id){
		return campaignService.oneRechargeOrder(id);
	}

	@RequestMapping(value = "/rechargeOrder/data", method = RequestMethod.POST)
	private int addPayOrder(@RequestBody Map map){
		map.put(DEFAULT_PRODUCT, PRODUCT_ID);
		return campaignService.addPayOrder(map);
	}

}
