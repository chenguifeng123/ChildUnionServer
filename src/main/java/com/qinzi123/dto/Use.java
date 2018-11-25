package com.qinzi123.dto;

/**
 * Created by chenguifeng on 2018/11/24.
 */
public enum Use {
	USE(1), NOUSE(0);

	int isUse;

	private Use(int isUse){
		this.isUse = isUse;
	}

	public int getIsUse(){
		return isUse;
	}
}
