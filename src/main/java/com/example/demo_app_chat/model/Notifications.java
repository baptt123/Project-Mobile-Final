package com.example.demo_app_chat.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("notification")
public class Notifications {
    @Id
    private int id;
    private int idUser;
//    private int idPost;
//    private int idSenderUser;
    private String action;

    public void save(Notifications notification) {

    }
}
