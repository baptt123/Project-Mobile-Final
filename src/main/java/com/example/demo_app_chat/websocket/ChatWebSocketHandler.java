package com.example.demo_app_chat.websocket;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo_app_chat.model.Messages;
import com.example.demo_app_chat.repository.MessageRepository;
import com.example.demo_app_chat.service.CloudinaryService;
import com.example.demo_app_chat.service.MessageService;
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
    private MessageService messageService;

    // Lưu trữ các session WebSocket theo ID người dùng
    private final Map<String, WebSocketSession> userSessions = new HashMap<>();
//    public Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", CloudinaryService.CLOUD_NAME,
//            "api_key", CloudinaryService.API_KEY,
//            "api_secret", CloudinaryService.API_SECRET
//    ));
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
        String msgContent = message.getPayload();
        System.out.println("Received message: " + msgContent);

        // Chuyển đổi tin nhắn từ JSON sang đối tượng Message
        ObjectMapper objectMapper = new ObjectMapper();
        Messages getMessage = objectMapper.readValue(msgContent, Messages.class);

        // Lưu tin nhắn vào cơ sở dữ liệu hoặc thực hiện các thao tác khác với tin nhắn nếu cần
        messageService.save(getMessage);

        // Gửi tin nhắn đến tất cả các client kết nối
        for (Map.Entry<String, WebSocketSession> entry : userSessions.entrySet()) {
            WebSocketSession wsSession = entry.getValue();
            if (wsSession.isOpen()) {
                // Gửi tin nhắn cho tất cả các client (bao gồm cả người gửi)
                messageService.save(getMessage);
                wsSession.sendMessage(new TextMessage(msgContent));
            }
        }
    }


//    @Override
//    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//        try {
//            // Lấy dữ liệu tệp từ BinaryMessage
//            byte[] fileData = message.getPayload().array();
//
//            // Tạo InputStream từ dữ liệu tệp đã nhận
//            InputStream inputStream = new ByteArrayInputStream(fileData);
//
//            // Upload tệp lên Cloudinary
//            Map uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
//
//            // Lấy URL của tệp đã upload
//            String fileUrl = uploadResult.get("secure_url").toString();
//
//            // Tạo một số ngẫu nhiên để làm ID cho tin nhắn và người gửi
//            Random random = new Random();
//            int randomNum = random.nextInt(100000);
//
//            // Lấy ngày giờ hiện tại để lưu vào tin nhắn
//            LocalDateTime localDateTime = LocalDateTime.now();
//            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//
//            // Tạo một tin nhắn mới với URL tệp
//            Message fileMessage = Message.builder()
//                    .id(randomNum)
//                    .message(fileUrl)
//                    .sendingDate(date)
//                    .idReceipt(randomNum)
//                    .idSender(randomNum)
//                    .build();
//
//            // Lưu tin nhắn vào MongoDB
//            messageRepository.save(fileMessage);
//
//            // Gửi thông báo tới các client khác về tệp đã được upload
//            for (Map.Entry<String, WebSocketSession> entry : userSessions.entrySet()) {
//                WebSocketSession wsSession = entry.getValue();
//                if (wsSession.isOpen()) {
//                    wsSession.sendMessage(new TextMessage("File uploaded: " + fileUrl));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


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




