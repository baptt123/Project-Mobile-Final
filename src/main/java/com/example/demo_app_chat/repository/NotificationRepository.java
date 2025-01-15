package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notifications, Integer> {
}
