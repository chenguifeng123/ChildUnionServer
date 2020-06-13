package com.qinzi123.controller.web;

import com.qinzi123.dto.LoginUser;
import com.qinzi123.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/11.
 */
@RestController
@RequestMapping(value = "/manage")
public class EntityController {

	@Autowired
	EntityService entityService;

	private static final String City_Saas = "card_info";
	private static final String SAAS_KEY = "city";

	@RequestMapping(value = "/{tableName}/addEntity", method = RequestMethod.POST)
	public int addService(@PathVariable("tableName") String tableName,
						  @RequestBody Map<String, Object> map) {
		return entityService.addService(tableName, map);
	}

	@RequestMapping(value = "/{tableName}/updateEntity", method = RequestMethod.POST)
	public int updateService(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
		return entityService.updateService(tableName, map);

	}

	@RequestMapping(value = "/{tableName}/deleteEntity", method = RequestMethod.POST)
	public int deleteService(@PathVariable("tableName") String tableName, @RequestBody List<Map<String, Object>> list) {

		return entityService.deleteService(tableName, list);
	}

	@RequestMapping(value = "/{tableName}/findEntity", method = RequestMethod.POST)
	public List<LinkedHashMap> showService(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
		return entityService.showService(tableName, map);
	}

	@RequestMapping(value = "/{tableName}/showEntitys", method = RequestMethod.POST)
	public List<LinkedHashMap> showAllService(@PathVariable("tableName") String tableName, HttpSession httpSession) {
		if(City_Saas.equals(tableName)){
			String user = httpSession.getAttribute(LoginFilter.SESSION_KEY).toString();
			LoginUser loginUser = LoginUser.getLoginUser(user);
			if(loginUser != LoginUser.DEFAULT){
				return entityService.showSaaSService(tableName, String.format(" %s = %d", SAAS_KEY, loginUser.getCity()));
			}
		}
		return entityService.showAllService(tableName);
	}

	@RequestMapping(value = "/showSpecialService", method = RequestMethod.POST)
	public List<LinkedHashMap> showSpecialService(@RequestBody Map<String, Object> map) {
		return entityService.showSpecialService(map);
	}
}