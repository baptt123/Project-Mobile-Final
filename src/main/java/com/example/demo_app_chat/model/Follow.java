//package com.example.demo_app_chat.model;
//
//import com.example.demo_app_chat.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.*;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.annotation.Id;
//import java.time.LocalDateTime;
//
//@Builder
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Document("follow")
//public class Follow {
//    private String id;  // MongoDB uses String as the default type for _id
//    private String followerId;  // Assuming you store the user ID as a string
//    private String followingId; // Assuming you store the user ID as a string
//    private LocalDateTime createdAt;
//    // Giả sử bạn có một UserRepository được tiêm vào ở đây (tuy nhiên sẽ phải thêm vào service)
//    private transient UserRepository userRepository;  // transient để không bị MongoDB lưu lại
//
//
//    // Lấy đối tượng User cho followerId
//    public User getFollower() {
//        if (userRepository != null) {
//            return userRepository.findById(followerId)
//                    .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
//        }
//        return null;
//    }
//
//    // Lấy đối tượng User cho followingId
//    public User getFollowing() {
//        if (userRepository != null) {
//            return userRepository.findById(followingId)
//                    .orElseThrow(() -> new EntityNotFoundException("Following user not found"));
//        }
//        return null;
//    }
//}
