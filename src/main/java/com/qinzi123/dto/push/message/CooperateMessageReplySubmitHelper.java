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
        return "9mY0ssY4O1iSnG9c3hPID4BQxVBHA_GWWjBZ43-HEl4";
    }

    @Override
    String page(Object object) {
        return MessageTools.cardMessage2Page((CardMessage)object);
    }

    @Override
    Object data(Object object) {
        CardMessageReply cardMessageReply = (CardMessageReply)object;
        CooperateReplyMessage cooperateReplyMessage = new CooperateReplyMessage();
        cooperateReplyMessage.setName2(getKeyword(cardMessageReply.getCardInfo().getRealname()));
        cooperateReplyMessage.setThing3(getKeyword(cardMessageReply.getReplyMessage()));
        cooperateReplyMessage.setDate4(getKeyword(cardMessageReply.getCreateTime()));
        return cooperateReplyMessage;
    }
}
