package com.dim.chatserver.model;

import java.util.regex.Pattern;

import com.dim.chatserver.service.rule.Rule;

/**
 * An enum for the message type - text, emotion.
 */
public enum MessageType implements Rule {
    SEND_TEXT {
        @Override
        public boolean apply(Message message) {
            if (isNullorEmpty(message.getPayload()))
                return false;
            if (message.getPayload().length() > 160)
                return false;
            return true;
        }
    },
    SEND_EMOTION {
        @Override
        public boolean apply(Message message) {
            if (isNullorEmpty(message.getPayload()))
                return false;
            if (message.getPayload().length() < 2 || message.getPayload().length() > 10)
                return false;
            if (Pattern.compile("\\d").matcher(message.getPayload()).find())
                return false;
            return true;
        }
    };
    
    private static boolean isNullorEmpty(String paylaod) {
        return paylaod == null || paylaod.trim().isEmpty();
    }
}
