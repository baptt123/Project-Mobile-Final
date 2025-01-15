package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerifycationRepository extends MongoRepository<EmailVerification,Integer> {
    EmailVerification findByEmail(String email);

    EmailVerification findByCode(String code);
}
