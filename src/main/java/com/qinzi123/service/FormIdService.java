package com.qinzi123.service;

import com.qinzi123.dto.WxSmallFormId;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenguifeng on 2018/11/21.
 */
public interface FormIdService {
	int addFormId(WxSmallFormId wxSmallFormId);
	int updateFormId(WxSmallFormId wxSmallFormId);
	List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId);
}
