package com.example.demo_app_chat.websocket;

import com.example.demo_app_chat.model.WebSocketHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Đăng ký handler cho chat và thông báo
        registry.addHandler(getChatWebSocketHandler(), "/chat")
                .addInterceptors(new UsernameInterceptor())
                .setAllowedOrigins("*");  // Cho phép tất cả các nguồn (client)
        registry.addHandler(getWebSocketHandlerImpl(), "/notifications").setAllowedOrigins("*"); // Dùng WebSocketHandlerImpl cho notifications
    }

    @Bean
    public WebSocketHandlerImpl getWebSocketHandlerImpl() {
        return new WebSocketHandlerImpl(); // Dùng WebSocketHandlerImpl để xử lý thông báo
    }

    @Bean
    public ChatWebSocketHandler getChatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }
}
