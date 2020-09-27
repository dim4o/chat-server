package com.dim.chatserver.service.rule;

import com.dim.chatserver.model.Message;

/**
 * An interface that represents a rule.
 */
public interface Rule {
    /**
     * Applies a rule to a given message.
     * 
     * @param message - a message to which a rule will be applied
     * @return whether the rule can be applied.
     */
    boolean apply(Message message);
}
