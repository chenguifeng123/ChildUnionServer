package com.qinzi123.dto.push.message;

import com.qinzi123.dto.push.template.MiniProgramSendObject;
import com.qinzi123.dto.push.AbstractPushHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by chenguifeng on 2020/12/27.
 */
public abstract class CooperateMessageHelper extends AbstractPushHelper {

    abstract String templateId();
    abstract String page(Object object);
    abstract Object data(Object object);

    public MessageSendObject generateSendObject(String toUser, Object pageData, Object showData){
        MessageSendObject sendObject = new MessageSendObject();
        sendObject.setTouser(toUser);
        sendObject.setTemplate_id(templateId());
        if(!StringUtils.isEmpty(page(pageData))) sendObject.setPage(page(pageData));
        sendObject.setData(data(showData));
        return sendObject;
    }

}
