package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAdminDTO {
    private String fullName;
    private int gender;
    private String profileImagePath;
}
