package com.example.demo_app_chat.dto;

import lombok.*;

import java.util.Date;

@Builder
@ToString
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor

public class MessagesDTO {
    private String id;
    private String message;
    private Date sendingDate;
    private String fullNameSender;
    private String fullNameReceiver;
}
