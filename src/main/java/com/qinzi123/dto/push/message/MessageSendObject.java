package com.qinzi123.dto.push.message;

/**
 * Created by chenguifeng on 2020/12/27.
 */
public class MessageSendObject {
    String touser;
    String template_id;
    String page;
    Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString(){
        return String.format("touser=%s,template_id=%s,page=%s,data=%s",
                touser, template_id, page, data);
    }
}
