package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    Optional<Object> findById(String id);

    User findByUsername(String userName);

    User findByEmail(String email);
}
