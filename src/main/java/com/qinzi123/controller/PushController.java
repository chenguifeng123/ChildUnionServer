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

	@ApiOperation(value = "新增form", notes = "新增form")
	@RequestMapping(value="/formId/{id}", method= RequestMethod.POST)
	private int addFormId(@PathVariable("id") String id,
							@RequestBody WxSmallFormId wxSmallFormId){
		return pushService.addFormId(wxSmallFormId);
	}

	@ApiOperation(value = "批量新增form", notes = "批量新增form")
	@RequestMapping(value="/formList/{card}-{idList}", method= RequestMethod.POST)
	private int batchAddFormId(@PathVariable("card") int card,
							   @PathVariable("idList") String idList){
		return pushService.batchAddFormId(card, idList.split(","));
	}

}
