package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.PostDTO;
import com.example.demo_app_chat.service.PostService;
//import com.example.demo_app_chat.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/getdata")
public class AdminController {
    @Autowired
    private PostService postService;
    @Autowired
//    private UserService userService;

    @GetMapping("/getpost")
    public ResponseEntity<?> getPost() {
        List<PostDTO> postDTOList = postService.getAllPosts();
        return ResponseEntity.ok(postDTOList);
    }

//    @GetMapping("/getuser")
//    public ResponseEntity<?> getUser() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
}
