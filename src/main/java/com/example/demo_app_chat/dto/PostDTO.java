package com.example.demo_app_chat.dto;

import com.example.demo_app_chat.model.UserInfo;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UserInfo user;
    private String caption;
    private String media;
}
