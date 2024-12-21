package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.PostDTO;
import com.example.demo_app_chat.model.Post;
import com.example.demo_app_chat.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts)
         {
            PostDTO postDTO= PostDTO.builder().caption(post.getCaption()).media(post.getMedia()).user(post.getUser()).build();
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }
    public Post getPostById(String id) {
        return postRepository.findByID(id);
    }
}
