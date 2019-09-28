package com.qinzi123.service.impl;

import com.qinzi123.dto.ScoreType;
import com.qinzi123.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by chenguifeng on 2018/12/27.
 */
@Component
public class ScoreServiceImpl extends AbstractWeixinService implements ScoreService{

	private Logger logger = LoggerFactory.getLogger(ScoreServiceImpl.class);
	private static final int PAY_TYPE = 4;

	private void addScoreHistory(int cardId, int type, int score){
		int id = cardDao.addScoreHistory(new HashMap(){{
			put("card_id", cardId);
			put("score_type", type);
			put("score", score);
		}});
		logger.info("记录{} 用户 {} 积分历史", id, cardId);
	}

	public int addScore(int cardId, ScoreType scoreType) {
		addScoreHistory(cardId, scoreType.getType(), scoreType.getScore());
		int id = cardDao.addScore(new HashMap(){{
			put("id", cardId);
			put("score", scoreType.getScore());
		}});
		logger.info("用户 {} 积分类型 {}, 增加 {} 积分", cardId, scoreType.getType(), scoreType.getScore());
		return id;
	}

	public int payAddScore(int cardId, int score) {
		addScoreHistory(cardId, PAY_TYPE, score);
		int id = cardDao.addScore(new HashMap(){{
			put("id", cardId);
			put("score", score);
		}});
		logger.info("用户 {} 支付现金, 增加 {} 积分", cardId, score);
		return id;
	}

	@Override
	public int minusScore(int cardId, int score) {
		int id = cardDao.minusScore(new HashMap(){{
			put("id", cardId);
			put("score", score);
		}});
		logger.info("用户 {} 减少 {} 积分", cardId, score);
		return id;
	}
}
