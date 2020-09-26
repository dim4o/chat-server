package com.dim.chatserver.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.model.MessageType;
//import com.dim.chatserver.repo.MessageRepo;
import com.dim.chatserver.util.MessageValidator;

@RestController
@RequestMapping(path = "messages")
public class MessageController {
    

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private MessageValidator validator;
    
//    @Autowired
//    private MessageRepo messageRepo;

    @PostMapping("/send_text")
    @Transactional
    public ResponseEntity<Message> sendText(@RequestBody Message message) {
        message.setType(MessageType.MESSAGE_TEXT);
        message.setCreatedAt(new java.util.Date());
        
        System.out.println(validator.validate(message));
        
        if (!validator.validate(message))
            return new ResponseEntity<Message>(HttpStatus.PRECONDITION_FAILED);
        
        
        entityManager.unwrap(Session.class).save(message);
        
//        messageRepo.save(message);

        System.out.println("TEST SENT");
        return new ResponseEntity<Message>(HttpStatus.CREATED);
    }

    @PostMapping("/send_emotion")
    @Transactional
    public ResponseEntity<Message> sendEmotion(@RequestBody Message message) {
        message.setType(MessageType.MESSAGE_EMOTION);
        message.setCreatedAt(new java.util.Date());
        
        if (!validator.validate(message))
            return new ResponseEntity<Message>(HttpStatus.PRECONDITION_FAILED);
        
        entityManager.unwrap(Session.class).save(message);

        return new ResponseEntity<Message>(HttpStatus.CREATED);
    }

}
