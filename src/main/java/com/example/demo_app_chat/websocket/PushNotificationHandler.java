package com.example.demo_app_chat.websocket;

import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class PushNotificationHandler extends TextWebSocketHandler {
    @Autowired
    private NotificationRepository notificationRepository;
    private Map<String, WebSocketSession> notificationList=new HashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        notificationList.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        notificationList.remove(session);
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String getMessage=message.getPayload();
        ObjectMapper objectMapper=new ObjectMapper();
        Notifications notifications=objectMapper.readValue(getMessage, Notifications.class);
        notificationRepository.save(notifications);
        System.out.println(notifications);
         for(Map.Entry<String,WebSocketSession> entry:notificationList.entrySet()){
             WebSocketSession webSocketSession = entry.getValue();
             if(webSocketSession.isOpen()){
                 webSocketSession.sendMessage(new TextMessage(getMessage));
             }
         }
    }
}
