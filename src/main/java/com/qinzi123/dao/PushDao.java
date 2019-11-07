package com.qinzi123.dao;

import com.qinzi123.dto.CardInfoSend;
import com.qinzi123.dto.CardMessageSend;
import com.qinzi123.dto.WxSmallFormId;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguifeng on 2019/8/24.
 */
public interface PushDao {

	int addMessageSend(CardMessageSend cardMessageSend);

	int addFormId(WxSmallFormId wxSmallFormId);
	int updateFormId(WxSmallFormId wxSmallFormId);
	List<WxSmallFormId> getCanUseSmallFormId(@Param("cardId") int cardId);
	List<Map> getEveryUserCanUseSmallFormId();
	List<String> getFansUser2Push(@Param("followerId") int followerId);

	int addCardSend(CardInfoSend cardInfoSend);
}
