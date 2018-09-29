package com.qinzi123.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	private String hello(){
		return "hello";
	}
	

}
