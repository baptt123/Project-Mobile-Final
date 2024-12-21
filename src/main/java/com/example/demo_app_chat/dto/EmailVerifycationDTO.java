package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifycationDTO {
    String email;
    String code;
    String newPassword;
}
