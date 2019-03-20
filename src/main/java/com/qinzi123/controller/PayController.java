package com.qinzi123.controller;

import com.qinzi123.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/3/18.
 */
@RestController
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	PayService payService;

	@RequestMapping(value = "/order/prepay", method = RequestMethod.POST)
	private Map prepay(@RequestBody Map map){
		Map result = new HashMap<>();
		result = payService.prepay(map);
		return result;
	}

	@RequestMapping(value = "/order/callback")
	private void callback(HttpServletRequest request, HttpServletResponse response){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			//sb为微信返回的xml
			String notityXml = sb.toString();
			logger.info(notityXml);
			payService.payBack(notityXml);
		}catch (IOException e){
			logger.error("微信回调接口失败", e);
		}
	}
}
