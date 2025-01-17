package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.StoryDTO;
import com.example.demo_app_chat.model.Story;
import com.example.demo_app_chat.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public List<StoryDTO> getAllStory() {
        List<Story> storyList = storyRepository.findAll();
        List<StoryDTO> storyDTOList = new ArrayList<>();
        for (Story story : storyList) {
            StoryDTO storyDTO = StoryDTO.builder().imageStory(story.getImageStory()).build();
            storyDTOList.add(storyDTO);

        }
        return storyDTOList;
    }
    public List<Story> findAll(){
        List<Story> stories=storyRepository.findAll();
        return stories;
    }
}
