package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.NotificationDTO;
import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.model.WebSocketHandlerImpl;
import com.example.demo_app_chat.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private WebSocketHandlerImpl webSocketHandlerImpl;
//    @Autowired
//    public ResponseEntity<?> save(Notifications notification) {
//        notificationRepository.save(notification);
//        return ResponseEntity.ok().build();
//    }
    public ResponseEntity<?> save(Notifications notification) {
        notificationRepository.save(notification);
        // Gửi thông báo sau khi lưu
        webSocketHandlerImpl.sendNotificationToAllClients(notification);
        return ResponseEntity.ok().build();
    }
    public List<NotificationDTO> getAllNotifications() {
        List<Notifications> notifications = notificationRepository.findAll();
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notifications notification : notifications) {
            NotificationDTO notificationDTO = NotificationDTO.builder().action(notification.getAction()).build();
            notificationDTOS.add(notificationDTO);
        }
        return notificationDTOS;
    }
}
