package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.service.NotificationService;
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
    private NotificationService notificationService;
    @PostMapping("/add")
    public ResponseEntity<?> addNotification(@RequestBody Notifications notification) {
        return notificationService.save(notification);
    }

}
