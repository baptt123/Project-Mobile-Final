package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Message;
import com.example.demo_app_chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @PostMapping("/sendmessage")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        messageRepository.save(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}
