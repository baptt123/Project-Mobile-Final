package com.example.demo_app_chat.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Embedded
    private UserInfo user;
    private String caption;
    private int likeCount;
    private int saveCount;
    private int shareCount;
    private int  isLike;
    private int isSaved;

    @ElementCollection
    private List<Long> comments;

    private String media;

}
