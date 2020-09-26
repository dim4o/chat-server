package com.dim.chatserver.util;

import org.springframework.stereotype.Component;

import com.dim.chatserver.model.Message;


public interface MessageValidator {
    public boolean validate(Message message);
}
