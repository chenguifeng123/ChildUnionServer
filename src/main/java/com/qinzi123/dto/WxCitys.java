package com.qinzi123.dto;

import java.util.List;

/**
 * Created by chenguifeng on 2020/1/21.
 */
public class WxCitys {
	private String name;
	private List<WxOneCity> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WxOneCity> getList() {
		return list;
	}

	public void setList(List<WxOneCity> list) {
		this.list = list;
	}
}
