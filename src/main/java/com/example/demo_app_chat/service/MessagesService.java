package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.MessagesDTO;
import com.example.demo_app_chat.model.Messages;
import com.example.demo_app_chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {
    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<?> save(Messages message) {
        messageRepository.save(message);
        return ResponseEntity.ok(message);
    }
    public List<MessagesDTO> getAll(String fullNameSender,String fullNameReceiver) {
        List<MessagesDTO> messagesDTOList = new ArrayList<>();
        for(Messages m : messageRepository.getAllMessages(fullNameSender,fullNameReceiver)) {
            MessagesDTO messagesDTO = MessagesDTO.builder().id(m.getId()).message(m.getMessage()).sendingDate(m.getSendingDate()).fullNameSender(fullNameSender).fullNameReceiver(fullNameReceiver).build();
            messagesDTOList.add(messagesDTO);
        }
        return messagesDTOList;
    }
}
