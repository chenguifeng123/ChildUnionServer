package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public class MiniProgramSendObject {
	String touser;
	String template_id;
	String page;
	String form_id;
	Object data;
	String emphasis_keyword;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getEmphasis_keyword() {
		return emphasis_keyword;
	}

	public void setEmphasis_keyword(String emphasis_keyword) {
		this.emphasis_keyword = emphasis_keyword;
	}

	public String toString(){
		return String.format("touser=%s,template_id=%s,page=%s,form_id=%s,data=%s,emphasis_keyword=%s",
				touser, template_id, page, form_id, data, emphasis_keyword);
	}
}
