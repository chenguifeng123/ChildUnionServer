package com.qinzi123.dto;

import com.qinzi123.exception.GlobalProcessException;

/**
 * Created by chenguifeng on 2018/12/27.
 */
public enum ScoreType {
	Invite(100, 1), Message(40, 2), MessageReply(20, 4), Sign(10, 3), ShowCard(50, 5);

	int score;
	int type;

	ScoreType(int score, int type){
		this.score = score;
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public int getType() {
		return type;
	}

	public static ScoreType getScoreType(int type){
		for(ScoreType scoreType : values()){
			if(scoreType.getType() == type)
				return scoreType;
		}
		throw new GlobalProcessException(String.format("%d 类型不匹配", type));
	}
}
