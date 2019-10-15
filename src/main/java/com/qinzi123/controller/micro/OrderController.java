package com.qinzi123.controller.micro;

import com.qinzi123.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/10/3.
 */
@RestController
@Api(value = "订单", description = "订单")
public class OrderController {

	@Autowired
	CampaignService campaignService;

	@ApiOperation(value = "订单列表", notes = "订单列表")
	@RequestMapping(value = "/order/list", method = RequestMethod.POST)
	private List<LinkedHashMap> listOrder(@RequestBody Map map){
		int card = Integer.parseInt(map.get("card").toString());
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		return campaignService.listOrder(card, start, num);
	}

	@ApiOperation(value = "获取订单", notes = "获取订单")
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	private List<LinkedHashMap> oneOrder(@PathVariable int id){
		return campaignService.oneOrder(id);
	}

	@ApiOperation(value = "订单新增", notes = "订单新增")
	@RequestMapping(value = "/order/data", method = RequestMethod.POST)
	private int addOrder(@RequestBody Map map){
		return campaignService.addOrder(map);
	}


}
