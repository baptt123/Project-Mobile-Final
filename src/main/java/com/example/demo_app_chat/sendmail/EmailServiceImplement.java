package com.example.demo_app_chat.sendmail;

import com.example.demo_app_chat.model.EmailVerification;
import com.example.demo_app_chat.service.EmailVerifycationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
// Class
// Implementing EmailService interface
public class EmailServiceImplement implements EmailService {
@Autowired
    EmailVerifycationService emailVerifycationService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")private String sender;

    public String sendSimpleMail(EmailDetails details)
    {
        try {
            // xóa code hết hạn
            emailVerifycationService.removeExpiredEmailVerifications();
            details.setMsgBody(generateRandomHash());
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText("Mã của bạn là: "+ details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setCode(details.getMsgBody());
            emailVerification.setEmail(details.getRecipient());
            emailVerification.setTime_expire(emailVerifycationService.getExpiryTimeIn10Minutes());
emailVerifycationService.addVerifycation(emailVerification);
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";

        }
        catch (Exception e) {
            e.printStackTrace(); // Ghi log chi tiết lỗi
            return "Error while Sending Mail: " + e.getMessage();
        }

    }
    public static String generateRandomHash() {
        Random random = new Random();
        StringBuilder hash = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // tạo số từ 0 đến 9
            hash.append(digit);
        }

        return hash.toString();
    }

}