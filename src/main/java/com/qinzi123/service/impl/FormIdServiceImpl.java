package com.qinzi123.service.impl;

import com.qinzi123.dao.CooperateDao;
import com.qinzi123.dto.WxSmallFormId;
import com.qinzi123.service.FormIdService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/21.
 */
@Component
public class FormIdServiceImpl implements FormIdService{
	private Logger logger = LoggerFactory.getLogger(FormIdServiceImpl.class);

	@Autowired
	CooperateDao cooperateDao;

	public int addFormId(WxSmallFormId wxSmallFormId) {
		logger.info(String.format("插入新的formId %s", wxSmallFormId.toString()));
		return cooperateDao.addFormId(wxSmallFormId);
	}

	public int updateFormId(WxSmallFormId wxSmallFormId) {
		logger.info(String.format("更新formId %s", wxSmallFormId.toString()));
		return cooperateDao.updateFormId(wxSmallFormId);
	}

	public List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId) {
		logger.info(String.format("获取%d可用formId列表", cardId));
		return cooperateDao.getCanUseSmallFormId(cardId);
	}
}
