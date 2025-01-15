package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.NotificationDTO;
import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.repository.NotificationRepository;
import com.example.demo_app_chat.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/insert-notification")
    public ResponseEntity<String> insertNotification(@RequestBody Notifications notification) {
        notificationService.save(notification);
        return ResponseEntity.ok("Notification saved");
    }

    @GetMapping("/get-notification")
    public List<NotificationDTO> getNotification() {
        return notificationService.getAllNotifications();
    }
}
