package com.example.demo_app_chat.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("story")
public class Story {
    @Id
    private int id;
    private int idUser; // ID của người đăng story
    private String imageStory; // Đường dẫn ảnh
//    private String caption; // Mô tả story
}
