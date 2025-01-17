package com.example.demo_app_chat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    @Override
    public synchronized void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Chuyển JSON từ client thành đối tượng Notifications
        Notifications notification = objectMapper.readValue(message.getPayload(), Notifications.class);
        System.out.println("Notification received: " + notification.getAction());

        // Gửi lại thông báo cho tất cả các client khác
        synchronized (sessions) {
            for (WebSocketSession s : sessions) {
                if (s != session && s.isOpen()) {
                    s.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        }
    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        System.out.println("Pong received from server: " + session.getId());
    }

    // Gửi thông báo tới tất cả client
    public void sendNotificationToAllClients(Notifications notification) {
        try {
            String notificationJson = objectMapper.writeValueAsString(notification);
            synchronized (sessions) {
                for (WebSocketSession session : sessions) {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(notificationJson));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
