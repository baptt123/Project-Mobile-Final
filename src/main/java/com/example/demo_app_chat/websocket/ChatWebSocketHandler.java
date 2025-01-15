package com.example.demo_app_chat.websocket;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo_app_chat.model.Messages;
import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.model.UserSession;
import com.example.demo_app_chat.repository.MessageRepository;
import com.example.demo_app_chat.service.CloudinaryService;
import com.example.demo_app_chat.service.MessagesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private MessagesService messageService;

    // Lưu trữ các session WebSocket theo ID người dùng
    private final Map<UserSession, WebSocketSession> userSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //thiet lap ket noi session
        String username = (String) session.getAttributes().get("username");
        if (username != null && !username.isEmpty()) {
            userSessions.put(UserSession.builder().username(username).build(), session);
            System.out.println("User " + username + " connected");
        }
    }
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Lấy username từ attributes của session
//        String username = (String) session.getAttributes().get("username");
//
//        if (username != null) {
//            // Lấy nội dung tin nhắn
//            String msgContent = message.getPayload();
//            System.out.println("Received message from " + username + ": " + msgContent);
//
//            // Chuyển đổi tin nhắn JSON sang đối tượng Messages
//            ObjectMapper objectMapper = new ObjectMapper();
//            Messages getMessage = objectMapper.readValue(msgContent, Messages.class);
//            //Map luu tru du lieu cua json
//            Map<String,Object> convertJSON=objectMapper.convertValue(getMessage, Map.class);
//            String userNameReceiver=convertJSON.get("fullNameReceiver").toString();
//            // Lặp qua tất cả các session và gửi tin nhắn
//            for (Map.Entry<UserSession, WebSocketSession> entry : userSessions.entrySet()) {
//                    if(entry.getKey().getUsername().equals(userNameReceiver)) {
//                        messageService.save(getMessage);
//                        entry.getValue().sendMessage(new TextMessage(msgContent));
//                    }else{
//                        messageService.save(getMessage);
//                }
//                    break;
//            }
//        }
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Lấy username từ attributes của session
        String username = (String) session.getAttributes().get("username");

        if (username != null) {
            // Lấy nội dung tin nhắn
            String msgContent = message.getPayload();
            System.out.println("Received message from " + username + ": " + msgContent);

            // Chuyển đổi tin nhắn JSON sang đối tượng Messages
            ObjectMapper objectMapper = new ObjectMapper();
            Messages getMessage = objectMapper.readValue(msgContent, Messages.class);

            // Map lưu trữ dữ liệu của JSON
            Map<String, Object> convertJSON = objectMapper.convertValue(getMessage, Map.class);
            String userNameReceiver = convertJSON.get("fullNameReceiver").toString();

            // Lưu tin nhắn vào cơ sở dữ liệu
            messageService.save(getMessage);

            // Gửi tin nhắn tới người nhận
            for (Map.Entry<UserSession, WebSocketSession> entry : userSessions.entrySet()) {
                String currentUser = entry.getKey().getUsername();
                if (currentUser.equals(userNameReceiver)) {
                    // Gửi tin nhắn tới người nhận
                    entry.getValue().sendMessage(new TextMessage(msgContent));
                } else if (currentUser.equals(username)) {
                    // Gửi tin nhắn xác nhận tới người gửi nếu cần
                    entry.getValue().sendMessage(new TextMessage("{\"status\": \"sent\"}"));
                }
            }
        }
    }





    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // lay username tu websocketsession
        String username = (String) session.getAttributes().get("username");

        if (username != null) {
            // bien luu tru usersession de xoa
            UserSession userSessionToRemove = null;

            // duyet qua map neu thoa dieu kien thi luu tru
            for (Map.Entry<UserSession, WebSocketSession> entry : userSessions.entrySet()) {
                if (entry.getKey().getUsername().equals(username)) {
                    userSessionToRemove = entry.getKey();
                    break;
                }
            }

            // xoa session khoi map
            if (userSessionToRemove != null) {
                userSessions.remove(userSessionToRemove);
                System.out.println("User " + username + " disconnected");
            }
        }
        System.out.println(status.getCode());
    }
}




