package com.dim.chatserver.repo;

import com.dim.chatserver.model.Message;

public interface MessageRepo {
    /**
     * Saves a message to a database.
     * 
     * @param message - a message to be saved.
     * @return an identifier of the message
     */
    public Long save(Message message);
}


/**
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dim.chatserver.model.Message;

@RepositoryRestResource(collectionResourceRel = "messages", path = "messages")
public interface MessageRepo extends CrudRepository<Message, Long> {

}
**/