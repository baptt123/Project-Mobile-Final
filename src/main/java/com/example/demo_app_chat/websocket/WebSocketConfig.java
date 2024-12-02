package com.example.demo_app_chat.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getChatWebSocketHandler(), "/chat")
                .setAllowedOrigins("*");  // Cho phép tất cả các nguồn (client)
        registry.addHandler(getPushNotificationHandler(), "/notifications").setAllowedOrigins("*");
        registry.addHandler(getChatFileWebSocketHandler(),"/uploadfile").setAllowedOrigins("*");
    }

    @Bean
    public PushNotificationHandler getPushNotificationHandler() {
        return new PushNotificationHandler();
    }
    @Bean
    public ChatWebSocketHandler getChatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }
    @Bean
    public ChatFileWebSocketHandler getChatFileWebSocketHandler() {return new ChatFileWebSocketHandler();}
}

