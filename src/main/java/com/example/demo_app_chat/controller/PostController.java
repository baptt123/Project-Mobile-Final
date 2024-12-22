package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.PostDTO;
import com.example.demo_app_chat.model.Post;
import com.example.demo_app_chat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/getpost")
    public List<PostDTO> getPost() {
        return postService.getAllPosts();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
}
