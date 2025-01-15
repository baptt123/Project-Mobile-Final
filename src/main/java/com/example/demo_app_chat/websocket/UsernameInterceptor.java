package com.example.demo_app_chat.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class UsernameInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Lấy query string từ URI
        String query = request.getURI().getQuery();
        if (query != null && query.contains("username=")) {
            // Trích xuất username từ query string
            String username = query.split("username=")[1];
            attributes.put("username", username);
        }
        return true; // Tiếp tục handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Không cần xử lý gì thêm sau handshake
    }
}
