package com.dim.chatserver.model;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Enables the use of case-insensitive enumerations request parameters
 */
@Component
public class MessageTypeConverter implements Converter<String, MessageType> {

    @Override
    public MessageType convert(String value) {
        return MessageType.valueOf(value.toUpperCase());
    }
}