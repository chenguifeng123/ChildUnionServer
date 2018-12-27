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

	public int addScore(int cardId, ScoreType scoreType) {
		logger.info("增加用户积分");
		cardDao.addScoreHistory(new HashMap(){{
			put("card_id", cardId);
			put("score_type", scoreType.getType());
			put("score", scoreType.getScore());
		}});
		cardDao.updateScore(new HashMap(){{
			put("id", cardId);
			put("score", scoreType.getScore());
		}});
		return 1;
	}
}
