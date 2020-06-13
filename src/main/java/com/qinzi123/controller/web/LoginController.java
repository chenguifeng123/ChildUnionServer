package com.qinzi123.controller.web;

import com.qinzi123.dto.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/11.
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/loginPost" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loginPost(String user, String password, HttpSession httpSession){
		Map<String, Object> map = new HashMap<String, Object>();

		if(LoginUser.getLoginUser(user, password) == null){
			map.put("success", false);
			map.put("message", "011");
			return map;
		}

		httpSession.setAttribute(LoginFilter.SESSION_KEY, user);
		System.out.println("login success");
		map.put("success", true);
		map.put("message", "0");

		return map;
	}

	@RequestMapping(value = "/loginOut" , method = RequestMethod.GET)
	public String loginOut(HttpSession httpSession){
		httpSession.removeAttribute(LoginFilter.SESSION_KEY);
		return String.format("redirect:%s", LoginFilter.LOGIN);
	}
}
