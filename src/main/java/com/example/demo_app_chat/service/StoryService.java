package com.example.demo_app_chat.service;

import com.example.demo_app_chat.model.Story;
import com.example.demo_app_chat.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;
    public ResponseEntity<?> findAll(){
        List<Story> stories=storyRepository.findAll();
        return ResponseEntity.ok(stories);
    }
}
