package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2019/8/31.
 */
public enum OrderType {
	SCORE(0), PAY(1);

	int type;

	OrderType(int type){
		this.type = type;
	}

	public int getType() {
		return type;
	}
}


