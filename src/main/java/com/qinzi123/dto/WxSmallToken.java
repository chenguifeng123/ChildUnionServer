package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/20.
 */
public class WxSmallToken {
	int id;
	String token;
	String createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String toString(){
		return String.format("id=%d,token=%s,createTime=%s", id, token, createTime);
	}
}
