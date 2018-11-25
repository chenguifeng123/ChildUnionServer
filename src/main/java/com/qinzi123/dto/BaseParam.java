package com.qinzi123.dto;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/22.
 */
public class BaseParam {
	long last;
	String lastString;
	String formId;
	List<String> formIdList;

	int cardId;
	CardInfo cardInfo;

	public long getLast() {
		return last;
	}

	public void setLast(long last) {
		this.last = last;
	}

	public String getLastString() {
		return lastString;
	}

	public void setLastString(String lastString) {
		this.lastString = lastString;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public List<String> getFormIdList() {
		return formIdList;
	}

	public void setFormIdList(List<String> formIdList) {
		this.formIdList = formIdList;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
}
