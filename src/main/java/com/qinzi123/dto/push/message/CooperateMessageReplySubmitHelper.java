package com.qinzi123.dto.push.message;

import com.qinzi123.dto.CardMessage;
import com.qinzi123.dto.CardMessageReply;
import com.qinzi123.dto.push.MessageTools;
import com.qinzi123.dto.push.template.miniProgram.CooperateTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by chenguifeng on 2020/12/27.
 */
@Component
public class CooperateMessageReplySubmitHelper extends CooperateMessageHelper{
    @Override
    String templateId() {
        return "4da162IYoX6oUoyLDxrF9AI3ANNClhTU0SR15rBF4JM";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage)object);
    }

    @Override
    Object data(Object object) {
        CardMessageReply cardMessageReply = (CardMessageReply)object;
        CooperateReplyMessage cooperateReplyMessage = new CooperateReplyMessage();
        cooperateReplyMessage.setThing1(getKeyword(cardMessageReply.getTitle()));
        cooperateReplyMessage.setName3(getKeyword(cardMessageReply.getCardInfo().getRealname()));
        cooperateReplyMessage.setDate2(getKeyword(cardMessageReply.getCreateTime()));
        return cooperateReplyMessage;
    }
}
