package com.qinzi123.controller;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.service.CooperateWeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2018/11/17.
 */
@RestController
@Api(value = "商户处理", description = "商户处理")
public class CooperateController {

	@Autowired
	private CooperateWeixinService cooperateWeixinService;

	@ApiOperation(value = "合作消息列表", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/list", method= RequestMethod.POST)
	private List<CardMessage> listCooperate(@RequestBody Map map){
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		return cooperateWeixinService.getAllCardMessage(start, num);
	}

	@ApiOperation(value = "合作消息列表", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/message", method= RequestMethod.POST)
	private int addMessage(@RequestBody CardMessage cardMessage){
		return cooperateWeixinService.addMessage(cardMessage);
	}
}
