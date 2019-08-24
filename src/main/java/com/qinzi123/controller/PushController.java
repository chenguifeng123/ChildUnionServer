package com.qinzi123.controller;

import com.qinzi123.dto.WxSmallFormId;
import com.qinzi123.service.PushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenguifeng on 2019/8/23.
 */
@RestController
@Api(value = "获取formId", description = "获取formId")
public class PushController {

	@Autowired
	PushService pushService;

	@ApiOperation(value = "更新用户", notes = "更新用户")
	@RequestMapping(value="/formId/{id}", method= RequestMethod.POST)
	private int addUserCard(@PathVariable("id") int id,
							@RequestBody WxSmallFormId wxSmallFormId){
		return pushService.addFormId(wxSmallFormId);
	}

}
