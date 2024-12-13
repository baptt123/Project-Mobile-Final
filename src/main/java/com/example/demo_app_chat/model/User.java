package com.example.demo_app_chat.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("user")
public class User {

}
