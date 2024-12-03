package com.example.demo_app_chat.service;

import com.example.demo_app_chat.model.Message;
import com.example.demo_app_chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<?> save(Message message) {
        messageRepository.save(message);
        return ResponseEntity.ok(message);
    }
}
