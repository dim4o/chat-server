package com.dim.chatserver.service;

import com.dim.chatserver.model.Message;

public interface MessageValidatorService {
    /**
     * Validates a message.
     * 
     * @param message - a message to be validated.
     * @return whether the message is valid
     */
    public boolean validate(Message message);
}
