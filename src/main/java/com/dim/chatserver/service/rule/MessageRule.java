package com.dim.chatserver.service.rule;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.dim.chatserver.model.Message;

/**
 * An enum that keeps the rules and sets of specific combination of rules.
 */
public enum MessageRule implements Rule {
    /** payload is mandatory parameter therefore should not be null or empty **/
    NOT_NULL_AND_NOT_EMPTY_RULE {
        @Override
        public boolean apply(Message m) {
            return m.getPayload() != null && !m.getPayload().trim().isEmpty();
        }
    },

    /** send_emotion the payload should be between 2 and 10 **/
    EMOTION_LENGTH_RULE {
        @Override
        public boolean apply(Message m) {
            return m.getPayload().length() > 1 && m.getPayload().length() < 11;
        }
    },

    /** send_emotion should not contain characters between 0 and 9 **/
    EMOTION_NO_DIGIT_RULE {
        @Override
        public boolean apply(Message m) {
            return !Pattern.compile("\\d").matcher(m.getPayload()).find();
        }
    },

    /** send_text payload length should be between 1 and 160 **/
    TEXT_LENGTH_RULE {
        @Override
        public boolean apply(Message m) {
            return m.getPayload().length() >= 1 && m.getPayload().length() <= 160;
        }
    };

    /** Combination of rules for test message **/
    public static Set<MessageRule> isValidTextSet = EnumSet.of(NOT_NULL_AND_NOT_EMPTY_RULE,
            TEXT_LENGTH_RULE);
    /** Combination of rules for a emotion message **/
    public static Set<MessageRule> isValidEmotionSet = EnumSet.of(NOT_NULL_AND_NOT_EMPTY_RULE,
            EMOTION_LENGTH_RULE, EMOTION_NO_DIGIT_RULE);

}
