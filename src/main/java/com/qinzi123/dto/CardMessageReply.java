package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/21.
 */
public class CardMessageReply extends BaseParam {
	int id;
	int messageId;
	int replyId;
	String replyMessage;
	String createTime;

	CardMessageReply replyInfo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public String getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public CardMessageReply getReplyInfo() {
		return replyInfo;
	}

	public void setReplyInfo(CardMessageReply replyInfo) {
		this.replyInfo = replyInfo;
	}
}