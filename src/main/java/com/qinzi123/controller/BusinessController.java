package com.qinzi123.controller;

import com.qinzi123.service.BusinessWeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "商户处理", description = "商户处理")
public class BusinessController {

	@Autowired
	private BusinessWeixinService businessWeixinService;
	
	@ApiOperation(value = "商户列表", notes = "商户列表")
	@RequestMapping(value="/business/list", method=RequestMethod.POST)
	private List<LinkedHashMap> listBusiness(@RequestBody Map map){
		String id = map.get("id").toString();
		int start = Integer.parseInt(map.get("start").toString());
		int num = Integer.parseInt(map.get("num").toString());
		String search = map.get("search").toString();
		return businessWeixinService.listBusiness(id, start, num, search);
	}

	@ApiOperation(value = "商户列表", notes = "商户列表")
	@RequestMapping(value="/business/list/{id}-{start}-{num}", method=RequestMethod.GET)
	private List<LinkedHashMap> listBusinessNoSearch(@PathVariable String id, @PathVariable int start,
											 @PathVariable int num){
		return businessWeixinService.listBusiness(id, start, num, null);
	}


	@ApiOperation(value = "具体商户信息", notes = "具体商户信息")
	@RequestMapping(value="/business/info/{id}", method=RequestMethod.GET)
	private List<LinkedHashMap> oneBusiness(@PathVariable String id){
		return businessWeixinService.oneBusiness(id);
	}

	@ApiOperation(value = "获取根据code信息", notes = "具体商户信息")
	@RequestMapping(value="/business/info/code/{code}", method=RequestMethod.GET)
	private int getIdByCode(@PathVariable String code){
		return businessWeixinService.getIdByCode(code);
	}

	@ApiOperation(value = "关注", notes = "关注")
	@RequestMapping(value="/business/addFollower/{userId}-{followerId}", method=RequestMethod.GET)
	private int addFollower(@PathVariable int userId, @PathVariable int followerId){
		return businessWeixinService.addFollower(userId, followerId);
	}

	@ApiOperation(value = "取消关注", notes = "取消关注")
	@RequestMapping(value="/business/deleteFollower/{userId}-{followerId}", method=RequestMethod.GET)
	private int deleteFollower(@PathVariable int userId, @PathVariable int followerId){
		return businessWeixinService.deleteFollower(userId, followerId);
	}

	@ApiOperation(value = "服务列表", notes = "服务列表")
	@RequestMapping(value="/business/service/list", method=RequestMethod.GET)
	private List<LinkedHashMap> getAllService(){
		return businessWeixinService.getAllService();
	}

	@ApiOperation(value = "更新用户", notes = "更新用户")
	@RequestMapping(value="/business/setUser", method=RequestMethod.POST)
	private int addUserCard(@RequestBody Map map){
		return businessWeixinService.setUser(map);
	}

	@ApiOperation(value = "获取用户标签", notes = "获取用户标签")
	@RequestMapping(value="/business/tag/{id}", method=RequestMethod.GET)
	private Map getCardTagById(@PathVariable String id){
		return businessWeixinService.getCardTagById(id);
	}

	@ApiOperation(value = "获取我关注的", notes = "获取我关注的")
	@RequestMapping(value="/business/my/follower/{current_id}-{my_id}", method=RequestMethod.GET)
	private List<LinkedHashMap> getFollowerById(@PathVariable String current_id, @PathVariable String my_id){
		return businessWeixinService.getFollowerById(current_id, my_id);
	}

	@ApiOperation(value = "获取关注我的", notes = "获取关注我的")
	@RequestMapping(value="/business/my/fans/{current_id}-{my_id}", method=RequestMethod.GET)
	private List<LinkedHashMap> getFansById(@PathVariable String current_id, @PathVariable String my_id){
		return businessWeixinService.getFansById(current_id, my_id);
	}

	@ApiOperation(value = "批量关注", notes = "批量关注")
	@RequestMapping(value="/business/batchAddFollower/{userId}-{followerIdList}", method=RequestMethod.GET)
	private int batchAddFollower(@PathVariable int userId, @PathVariable String followerIdList){
		return businessWeixinService.batchAddFollower(userId, followerIdList.split(","));
	}


}
