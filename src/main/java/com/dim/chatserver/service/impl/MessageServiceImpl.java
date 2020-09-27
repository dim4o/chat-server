package com.dim.chatserver.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.repo.MessageRepo;
import com.dim.chatserver.service.MessageService;
import com.dim.chatserver.service.MessageValidatorService;

@Service
public class MessageServiceImpl implements MessageService {

    @SuppressWarnings("unused")
    @Autowired
    private MessageValidatorService validator;

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public ResponseEntity<Message> save(Message message) {
        message.setCreatedAt(new Date());

        /**
         * I made two implementations of the decision logic. Both of them take an
         * advantage of ability of the Enum to implement an interface.
         *     1. The first strategy is very simple - each type has it's own implementation
         *     about how to apply the rules.
         *     2. The second strategy uses a validation service and sets of rules. 
         *     The advantage is that it is more flexible because we have simple rules
         *     that can be combined to produce a different rule set for each specific case.
         */
        
        /** 
         * Strategy 1 (current)
         * See: {@link com.dim.chatserver.service.Rule} Rule
         * See: {@link com.dim.chatserver.model.MessageType} MessageType
         */
        if (!message.getType().apply(message))
            return new ResponseEntity<Message>(HttpStatus.PRECONDITION_FAILED);

        /** 
         * Strategy 2 
         * See: {@link com.dim.chatserver.service.Rule} Rule
         * See: {@link com.dim.chatserver.service.MessageValidatorService} MessageValidatorService
         * See: {@link com.dim.chatserver.service.impl.MessageValicatorServiceImpl} MessageValicatorServiceImpl
         */
        // if (!validator.validate(message))
        //    return new ResponseEntity<Message>(HttpStatus.PRECONDITION_FAILED);

        messageRepo.save(message);

        return new ResponseEntity<Message>(HttpStatus.CREATED);
    }

}
