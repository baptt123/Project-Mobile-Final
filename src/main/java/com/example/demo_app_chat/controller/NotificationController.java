package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;
    @PostMapping
    public ResponseEntity<String> insertNotification(@RequestBody Notifications notification) {
        notificationRepository.save(notification);
        return ResponseEntity.ok("Notification saved");
    }
}
