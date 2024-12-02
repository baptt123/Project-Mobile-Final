package com.example.demo_app_chat.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatFileWebSocketHandler extends BinaryWebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new HashMap<>();

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        byte[] payload = message.getPayload().array();

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Khi kết nối mới, gán session cho một ID người dùng
        userSessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userSessions.remove(session.getId());
    }
}
