package com.qinzi123.service;

import com.qinzi123.dto.ScoreType;

/**
 * Created by chenguifeng on 2018/12/27.
 */
public interface ScoreService {
	public int addScore(int cardId, ScoreType scoreType);
	public int payAddScore(int cardId, int score);
	public int minusScore(int cardId, int score);
}
