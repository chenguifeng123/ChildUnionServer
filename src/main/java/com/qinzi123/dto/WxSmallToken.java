package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/20.
 */
public class WxSmallToken {
	int id;
	String token;
	String createTime;
	int type;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString(){
		return String.format("id=%d,token=%s,createTime=%s,type=%d", id, token, createTime, type);
	}
}
