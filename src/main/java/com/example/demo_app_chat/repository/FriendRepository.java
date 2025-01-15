package com.example.demo_app_chat.repository;
import com.example.demo_app_chat.model.Friend;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//import org.springframework.stereotype.Repository;

import java.util.List;

public interface FriendRepository extends MongoRepository<Friend, String>{
        List<Friend> findByNameContainingIgnoreCase(String name); // Tìm kiếm bạn bè theo tên

    }
