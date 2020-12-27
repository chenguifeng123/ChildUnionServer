package com.qinzi123.dto.push.message;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.push.MessageTools;
import com.qinzi123.dto.push.template.miniProgram.CooperateTemplate;
import com.qinzi123.util.Utils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenguifeng on 2020/12/27.
 */
@Component
public class CooperateMessageSubmitHelper extends CooperateMessageHelper {
    @Override
    String templateId() {
        return "LJaWcDFwNvldSKvb9rTdNNzXMeyYrhKYzWUf_Qjy8Nk";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage)object);
    }

    @Override
    Object data(Object object) {
        CardMessage cardMessage = (CardMessage)object;
        CooperateMessage cooperateMessage = new CooperateMessage();
        cooperateMessage.setThing1(getKeyword(cardMessage.getTitle()));
        cooperateMessage.setName2(getKeyword(cardMessage.getCardInfo().getRealname()));
        cooperateMessage.setDate3(getKeyword(cardMessage.getUpdateTime()));
        cooperateMessage.setName4(getKeyword(cardMessage.getCardInfo().getRealname()));
        return cooperateMessage;
    }
}
