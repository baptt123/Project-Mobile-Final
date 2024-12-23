package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Message;
import com.example.demo_app_chat.model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Messages, String> {

}
