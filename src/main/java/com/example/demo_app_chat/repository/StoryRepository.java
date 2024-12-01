package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.Story;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends MongoRepository<Story,Integer> {
    @Override
    List<Story> findAll();
}
