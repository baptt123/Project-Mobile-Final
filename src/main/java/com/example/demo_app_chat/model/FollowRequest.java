package com.example.demo_app_chat.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequest {
    private String followerId;
    private String followingId;
}
