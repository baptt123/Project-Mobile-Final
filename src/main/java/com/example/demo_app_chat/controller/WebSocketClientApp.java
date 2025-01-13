package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.model.WebSocketHandlerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

public class WebSocketClientApp {

    private static final String WS_URL = "ws://localhost:8080/notifications";
    private final WebSocketClient webSocketClient;
    private WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketClientApp() {
        this.webSocketClient = new StandardWebSocketClient();
    }

    /**
     * Kết nối WebSocket tới server.
     */
    public void connect() {
        try {
            if (session == null || !session.isOpen()) {
                WebSocketHandler handler = new WebSocketHandlerImpl();
                session = webSocketClient.doHandshake(handler, WS_URL).get();
                System.out.println("Kết nối WebSocket thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể kết nối WebSocket. Thử lại sau...");
        }
    }

    /**
     * Gửi thông báo qua WebSocket.
     *
     * @param notification Đối tượng Notifications cần gửi.
     */
    public void sendNotification(Notifications notification) {
        try {
            if (session != null && session.isOpen()) {
                String notificationJson = objectMapper.writeValueAsString(notification);
                session.sendMessage(new TextMessage(notificationJson));
                System.out.println("Notification sent: " + notificationJson);
            } else {
                System.out.println("Kết nối WebSocket không khả dụng, thử kết nối lại...");
                connect();
                sendNotification(notification); // Gửi lại sau khi kết nối
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Không thể gửi thông báo");
        }
    }

    public static void main(String[] args) {
        WebSocketClientApp clientApp = new WebSocketClientApp();
        // Kết nối WebSocket
        clientApp.connect();
        // Gửi thông báo
        Notifications notification = new Notifications("11", "675e6e12324f75314d7fb41b", "ccccc");
        clientApp.sendNotification(notification);

        Notifications anotherNotification = new Notifications("12", "675e6e12324f75314d7fb41b", "ok em ");
        clientApp.sendNotification(anotherNotification);
    }
}
