package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/17.
 */
public class CardInfo {

	int id;
	String openid;
	String phone;
	String realname;
	String company;
	String job;
	String headimgurl;
	String weixincode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getWeixincode() {
		return weixincode;
	}

	public void setWeixincode(String weixincode) {
		this.weixincode = weixincode;
	}
}
