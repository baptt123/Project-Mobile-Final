package com.example.demo_app_chat.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
    private String id= UUID.randomUUID().toString();
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
