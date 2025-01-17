package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFollowingDTO {
    String id;
    int count;
    String action;
}
