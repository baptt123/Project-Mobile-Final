package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    String id;
    String email;
    String fullName;
    int gender; // 0: nam, 1: nữ
    String profileImagePath;
}
