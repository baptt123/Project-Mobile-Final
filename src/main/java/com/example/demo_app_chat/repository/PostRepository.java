package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, Integer> {
    Optional<Object> findById(String id);
}
