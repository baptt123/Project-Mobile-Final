package com.example.demo_app_chat.model;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Document("emailverifycation")
public class EmailVerification {
    @Id
    String id = UUID.randomUUID().toString();
    String email;
    String code;
    Date time_expire;
}
