package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.EmailVerifycationDTO;
import com.example.demo_app_chat.model.EmailVerification;
import com.example.demo_app_chat.repository.EmailVerifycationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailVerifycationService {
    @Autowired
    EmailVerifycationRepository emailVerifycationRepository;
    public boolean addVerifycation(EmailVerification emailVerification){
        emailVerifycationRepository.save(emailVerification);
        return true;
    }
    public boolean checkEmailVerification(EmailVerifycationDTO emailVerifycationDTO){
        removeExpiredEmailVerifications();
        EmailVerification emailVerification = emailVerifycationRepository.findByEmail(emailVerifycationDTO.getEmail());
        return emailVerification != null && emailVerification.getCode().equals(emailVerifycationDTO.getCode());
    }
    public void remoteCodeHasBeenUse(String code) {
        // Tìm mã xác thực trong cơ sở dữ liệu
        EmailVerification emailVerification = emailVerifycationRepository.findByCode(code);

        if (emailVerification != null) {

            emailVerifycationRepository.delete(emailVerification);
        } else {

            System.out.println("Mã xác thực không tồn tại");
        }
    }

    public void removeExpiredEmailVerifications() {
        // Lấy thời gian hiện tại
        Date now = new Date();

        // Lấy tất cả các email verification từ database
        List<EmailVerification> allVerifications = emailVerifycationRepository.findAll();

        // Lọc các email verification có time_expire nhỏ hơn hiện tại
        List<EmailVerification> expiredVerifications = allVerifications.stream()
                .filter(ev -> ev.getTime_expire().before(now))
                .collect(Collectors.toList());

        // Xóa các email verification đã hết hạn
        emailVerifycationRepository.deleteAll(expiredVerifications);
    }
    public Date getExpiryTimeIn10Minutes() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Cộng thêm 10 phút
        LocalDateTime expireTime = now.plusMinutes(10);

        // Chuyển đổi sang java.util.Date
        return Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
