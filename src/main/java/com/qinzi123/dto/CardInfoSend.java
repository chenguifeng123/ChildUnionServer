package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/19.
 */
public class CardInfoSend {
	int id;
	int cardId;
	int newCardId;
	String openid;
	String createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getNewCardId() {
		return newCardId;
	}

	public void setNewCardId(int newCardId) {
		this.newCardId = newCardId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
