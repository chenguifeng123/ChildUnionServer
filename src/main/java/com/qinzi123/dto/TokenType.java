package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2019/11/5.
 */
public enum TokenType {
	MiniProgram(0), OfficialAccount(1);

	private int type;

	TokenType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
