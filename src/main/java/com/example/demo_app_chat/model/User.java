package com.example.demo_app_chat.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
     private List<String> mutualFriends;
     int status; // 0: offline, 1: online
     String profileImagePath;
     List<String> friends; // Danh sách ID bạn bè
     List<String> posts_saved; // Danh sách bài viết đã lưu
     List<String> posts_shared; // Danh sách bài viết đã chia sẻ
//     List<String> following; // Danh sách ID người dùng mà bạn đang theo dõi
     List<String> suggestedFriends; // Danh sách ID bạn bè gợi ý
     private List<String> followers = new ArrayList<>();
     private List<String> following = new ArrayList<>();
     public User(String id, String username, String fullName, String profileImagePath, List<String> friends, List<String> followers,List<String> following ) {
          this.id = id;
          this.username = username;
          this.fullName = fullName;
          this.profileImagePath = profileImagePath;
          this.friends = friends != null ? friends : new ArrayList<>();
          this.status = 0; // Mặc định là offline
          this.followersCount = 0; // Mặc định là 0
          this.followingCount = 0; // Mặc định là 0
          this.posts_saved = new ArrayList<>();
          this.posts_shared = new ArrayList<>();
          this.mutualFriends = new ArrayList<>();
          this.suggestedFriends = new ArrayList<>();
          this.followers = new ArrayList<>();
          this.following = new ArrayList<>();
     }
}
