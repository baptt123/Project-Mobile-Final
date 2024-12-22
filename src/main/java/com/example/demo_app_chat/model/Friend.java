package com.example.demo_app_chat.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("friend")
    public class Friend {
        private String id;
        private String name;
        private String profileImageUrl;
        private boolean isFavorite;

//        public void save(com.example.demo_app_chat.model.Notifications notification) {
//
//        }
}
