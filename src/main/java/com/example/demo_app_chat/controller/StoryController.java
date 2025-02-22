package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Story;
import com.example.demo_app_chat.repository.StoryRepository;
import com.example.demo_app_chat.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/story")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @GetMapping("/getstories")
    public ResponseEntity<List<Story>> getStories() {
        List<Story> stories = (List<Story>) storyService.findAll();
        if (stories == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stories);

    }
}
