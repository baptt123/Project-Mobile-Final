package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.MessagesDTO;
import com.example.demo_app_chat.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {
    @Autowired
    private MessagesService messageService;

    public MessagesController(MessagesService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/getmessages/{fullNameSender}/{fullNameReceiver}")
    public ResponseEntity<List<MessagesDTO>> getAllMessages(@PathVariable String fullNameSender, @PathVariable String fullNameReceiver) {
       List<MessagesDTO> list=messageService.getAll(fullNameSender, fullNameReceiver);
       return ResponseEntity.ok(list);
    }
}
