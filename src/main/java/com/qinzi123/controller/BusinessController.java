package com.qinzi123.controller;

import com.qinzi123.service.BusinessWeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.List;


@RestController
@Api(value = "商户处理", description = "商户处理")
public class BusinessController {

	@Autowired
	private BusinessWeixinService businessWeixinService;
	
	@ApiOperation(value = "商户列表", notes = "商户列表")
	@RequestMapping(value="/business/list/{id}", method=RequestMethod.GET)
	private List<LinkedHashMap> listBusiness(@PathVariable String id){
		return businessWeixinService.listBusiness(id);
	}

	@ApiOperation(value = "具体商户信息", notes = "具体商户信息")
	@RequestMapping(value="/business/info/{id}", method=RequestMethod.GET)
	private List<LinkedHashMap> oneBusiness(@PathVariable String id){
		return businessWeixinService.oneBusiness(id);
	}

}
