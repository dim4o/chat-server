package com.dim.chatserver.service;

import org.springframework.http.ResponseEntity;

import com.dim.chatserver.model.Message;

public interface MessageService {
    public ResponseEntity<Message> save(Message message);
}
