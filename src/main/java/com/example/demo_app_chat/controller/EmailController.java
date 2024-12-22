package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.sendmail.EmailDetails;
import com.example.demo_app_chat.sendmail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Class
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendmail")
    public String sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);
        return status;
    }
}
