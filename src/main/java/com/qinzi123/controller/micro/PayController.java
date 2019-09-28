package com.qinzi123.controller.micro;

import com.qinzi123.service.PayScoreService;
import com.qinzi123.service.RechargeMoneyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/18.
 */
@RestController
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	PayScoreService payScoreService;

	@Autowired
	RechargeMoneyService rechargeMoneyService;

	@RequestMapping(value = "/order/payScore", method = RequestMethod.POST)
	private Map payScore(@RequestBody Map map){
		Map result = new HashMap<>();
		result = payScoreService.payScore(map);
		return result;
	}

	@RequestMapping(value = "/order/prepay", method = RequestMethod.POST)
	private Map prepay(@RequestBody Map map){
		Map result = new HashMap<>();
		result = rechargeMoneyService.prepay(map);
		return result;
	}

}
