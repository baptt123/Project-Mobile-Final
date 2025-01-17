package com.example.demo_app_chat.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserInfo {
    @Field("userName")
    private String userName;
    @Field("profileImage_path")
    private String profileImagePath;

}
