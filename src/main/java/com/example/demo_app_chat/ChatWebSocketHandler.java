package com.example.demo_app_chat;

import com.example.demo_app_chat.model.Message;
import com.example.demo_app_chat.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private MessageRepository messageRepository;
    // Lưu trữ các session WebSocket theo ID người dùng
    private final Map<String, WebSocketSession> userSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Khi kết nối mới, gán session cho một ID người dùng
//        String username = session.getUri().getQuery(); // Giả sử client gửi tên người dùng trong query param (ví dụ: ws://localhost:8080/chat?username=John)
//        if (username != null && !username.isEmpty()) {
//            userSessions.put(username, session);
//            System.out.println("User " + username + " connected");
//        }
        userSessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String username = session.getUri().getQuery(); // Lấy tên người dùng từ query param
//        if (username != null) {
        // Kiểm tra xem người gửi có trong map hay không
        String msgContent = message.getPayload();
//            System.out.println("Received message from " + username + ": " + msgContent);
        System.out.println(msgContent);
        ObjectMapper objectMapper = new ObjectMapper();
        Message getMessage = objectMapper.readValue(msgContent, Message.class);
        // Nếu muốn gửi tin nhắn cho tất cả người dùng, bạn có thể lặp qua tất cả các session trong userSessions
        for (Map.Entry<String, WebSocketSession> entry : userSessions.entrySet()) {
            WebSocketSession wsSession = entry.getValue();
            if (wsSession.isOpen()) {
//                wsSession.sendMessage(new TextMessage(username + ": " + msgContent));
                messageRepository.save(getMessage);
                wsSession.sendMessage(new TextMessage(msgContent));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String username = session.getUri().getQuery();
//        if (username != null) {
//            userSessions.remove(username);
//            System.out.println("User " + username + " disconnected");
//        }
        userSessions.remove(session.getId());
    }
}




