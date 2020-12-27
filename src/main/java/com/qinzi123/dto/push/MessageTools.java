package com.qinzi123.dto.push;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2020/12/27.
 */
public class MessageTools {

    public static String cardMessage2Page(CardMessage cardMessage){
        Map<String, Object> map = new HashMap(){{
            put("id", cardMessage.getId());
            put("title", cardMessage.getTitle());
            put("message", cardMessage.getMessage());
            put("last", Utils.getDateLast(cardMessage.getLast()));
            put("read", cardMessage.getReadCount());
            put("like", cardMessage.getGiveLike());
            put("card", cardMessage.getCardId());
            put("phone", cardMessage.getCardInfo().getPhone());
            put("realname", cardMessage.getCardInfo().getRealname());
            put("job", cardMessage.getCardInfo().getJob());
            put("company", cardMessage.getCardInfo().getCompany());
            put("headimgurl", cardMessage.getCardInfo().getHeadimgurl());
        }};
        return Utils.fillUrlParams("pages/cooperate/oneMessage", map);
    }


}
