package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/17.
 */
public class CardMessage extends BaseParam {
	int id;
	String title;
	String message;
	String updateTime;
	int readCount;
	int giveLike;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getGiveLike() {
		return giveLike;
	}

	public void setGiveLike(int giveLike) {
		this.giveLike = giveLike;
	}

	@Override
	public String toString(){
		return String.format("用户 %d 发布消息 %s", cardId, title);
	}
}
