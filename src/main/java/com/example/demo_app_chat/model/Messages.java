package com.example.demo_app_chat.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("message")
public class Messages {
    @Id
    private int id;
    private String message;
    private Date sendingDate;
    private int idSender;
    private int idReceipt;
}
