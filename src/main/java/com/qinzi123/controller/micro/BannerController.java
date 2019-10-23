package com.qinzi123.controller.micro;

import com.qinzi123.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by chenguifeng on 2019/10/23.
 */
@RestController
@Api(value = "广告处理", description = "广告处理")
public class BannerController {

	@Autowired
	BannerService bannerService;

	@ApiOperation(value = "广告查询", notes = "广告查询")
	@RequestMapping(value="/banner/list", method= RequestMethod.GET)
	private List<LinkedHashMap> listAllBanners(){
		return bannerService.listAllBanners();
	}

	@ApiOperation(value = "单个广告", notes = "单个广告")
	@RequestMapping(value="/banner/{id}", method=RequestMethod.GET)
	private List<LinkedHashMap> oneBanner(@PathVariable int id){
		return bannerService.oneBanner(id);
	}

}
