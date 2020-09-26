package com.dim.chatserver.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.model.MessageType;

@Component
public class MessageValicatorImpl implements MessageValidator {

    @Override
    public boolean validate(Message message) {
        String payload = message.getPayload();
        if (payload == null || payload.isEmpty())
            return false;

        if (message.getType().equals(MessageType.MESSAGE_TEXT)) {
            if (payload.length() > 160)
                return false;
        } else {
            if (payload.length() < 2 || payload.length() > 10)
                return false;
            Pattern p = Pattern.compile("\\d");
            Matcher m = p.matcher(payload);
            if (m.find())
                return false;
            
        }

        return true;
    }

}
