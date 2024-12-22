package com.example.demo_app_chat.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("user")
public class User {
     @Id
      String id = UUID.randomUUID().toString();
     String username;
     String password;
     String email;
     String fullName;
     int gender; // 0: nam, 1: nữ
     int followersCount;
     int followingCount;
     int status; // 0: offline, 1: online
     String profileImagePath;
     List<String> friends; // Danh sách ID bạn bè
     List<String> posts_saved; // Danh sách bài viết đã lưu
     List<String> posts_shared; // Danh sách bài viết đã chia sẻ
}
