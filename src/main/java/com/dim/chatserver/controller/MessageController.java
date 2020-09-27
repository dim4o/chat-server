package com.dim.chatserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.model.MessageType;
import com.dim.chatserver.service.MessageService;

@RestController
@RequestMapping(path = "messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/{type}")
    public ResponseEntity<Message> sendText(@PathVariable(value = "type") MessageType type,
            @RequestBody Message message) {
        message.setType(type);

        return messageService.save(message);
    }

}
