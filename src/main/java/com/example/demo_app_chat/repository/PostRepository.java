package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, Integer> {

    Post findByID(String id);
}
