package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    String username;
    String password;
    String email;
    String fullName;
    String gender;
}
