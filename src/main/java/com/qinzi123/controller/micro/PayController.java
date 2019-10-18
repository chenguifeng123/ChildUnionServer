package com.qinzi123.controller.micro;

import com.qinzi123.service.PayScoreService;
import com.qinzi123.service.RechargeMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/18.
 */
@Api(value = "支付", description = "支付")
@RestController
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	PayScoreService payScoreService;

	@Autowired
	RechargeMoneyService rechargeMoneyService;

	@ApiOperation(value = "充值积分", notes = "充值积分")
	@RequestMapping(value = "/order/payScore", method = RequestMethod.POST)
	private Map payScore(@RequestBody Map map){
		Map result = new HashMap<>();
		result = payScoreService.payScore(map);
		return result;
	}

	@ApiOperation(value = "仅仅兑付查看功能", notes = "充值积分")
	@RequestMapping(value = "/card/show/payScore/{card}-{showCard}", method = RequestMethod.GET)
	private Map payScore4ShowCard(@PathVariable int card, @PathVariable int showCard){
		return payScoreService.payShowCardScore(card, showCard);
	}

	@ApiOperation(value = "预支付", notes = "预支付")
	@RequestMapping(value = "/order/prepay", method = RequestMethod.POST)
	private Map prepay(@RequestBody Map map){
		Map result = new HashMap<>();
		result = rechargeMoneyService.prepay(map);
		return result;
	}

}
