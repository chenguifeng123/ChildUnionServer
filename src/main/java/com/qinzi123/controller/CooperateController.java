package com.qinzi123.controller;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
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

	@ApiOperation(value = "所有合作消息列表", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/list", method= RequestMethod.POST)
	private List<CardMessage> listCooperate(@RequestBody Map map){
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		return cooperateWeixinService.getAllCardMessage(start, num);
}

	@ApiOperation(value = "商户合作消息列表", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/cardId", method= RequestMethod.POST)
	private List<CardMessage> listCooperateByCardId(@RequestBody Map map){
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		int cardId = Integer.parseInt(map.get("cardId").toString());
		return cooperateWeixinService.getCardMessageByCardId(cardId, start, num);
	}

	@ApiOperation(value = "新增合作消息", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/message", method= RequestMethod.POST)
	private int addMessage(@RequestBody CardMessage cardMessage){
		return cooperateWeixinService.addMessage(cardMessage);
	}

	@ApiOperation(value = "新增合作回复消息", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/messageReply", method= RequestMethod.POST)
	private int addMessageReply(@RequestBody CardMessageReply cardMessageReply){
		return cooperateWeixinService.addCardMessageReply(cardMessageReply);
	}

	@ApiOperation(value = "商户合作消息回复列表", notes = "合作消息列表")
	@RequestMapping(value="/cooperate/messageReply/{messageId}", method= RequestMethod.GET)
	private List<CardMessageReply> getAllCardMessageReplyByMessageId(@PathVariable("messageId") int messageId){
		return cooperateWeixinService.getAllCardMessageReplyByMessageId(messageId);
	}

}
