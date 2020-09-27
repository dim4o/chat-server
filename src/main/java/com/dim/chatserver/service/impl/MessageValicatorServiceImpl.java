package com.dim.chatserver.service.impl;

import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.model.MessageType;
import com.dim.chatserver.service.MessageValidatorService;
import com.dim.chatserver.service.rule.MessageRule;

@Service
public class MessageValicatorServiceImpl implements MessageValidatorService {

    @Override
    public boolean validate(Message message) {
        Set<MessageRule> rules = null;
        switch (message.getType()) {
        case SEND_TEXT:
            rules = MessageRule.isValidTextSet;
            break;
        case SEND_EMOTION:
            rules = MessageRule.isValidEmotionSet;
            break;
        default:
            throw new UnsupportedOperationException(
                    String.format("'%s' type is not supported.", message.getType()));
        }

        for (MessageRule rule : rules)
            if (!rule.apply(message))
                return false;

        return true;
    }

    /**
     * An alternative implementation without too much OOP (not used).
     */
    public boolean validate_v1(Message message) {
        String payload = message.getPayload();
        if (payload == null || payload.trim().isEmpty())
            return false;
        if (message.getType().equals(MessageType.SEND_TEXT)) {
            if (payload.length() > 160)
                return false;
        } else {
            if (payload.length() < 2 || payload.length() > 10)
                return false;
            if (Pattern.compile("\\d").matcher(payload).find())
                return false;
        }

        return true;
    }

}
