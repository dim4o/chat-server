package com.dim.chatserver.repo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dim.chatserver.model.Message;

@Repository
public class MessageRepoImpl implements MessageRepo {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Long save(Message message) {
        Long id = (Long) entityManager.unwrap(Session.class).save(message);
        // System.out.println("ID: " + id);
        return id;
    }

}
