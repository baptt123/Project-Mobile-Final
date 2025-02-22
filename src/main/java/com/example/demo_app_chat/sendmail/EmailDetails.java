package com.example.demo_app_chat.sendmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
}