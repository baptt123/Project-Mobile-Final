package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Messages, String> {
@Query("{fullNameSender: ?0,fullNameReceiver: ?1}")
    List<Messages> getAllMessages( String fullNameSender, String fullNameReceiver);
}
