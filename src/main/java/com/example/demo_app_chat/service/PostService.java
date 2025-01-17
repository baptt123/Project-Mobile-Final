package com.example.demo_app_chat.service;


import com.example.demo_app_chat.model.Comment;
import com.example.demo_app_chat.model.Post;
import com.example.demo_app_chat.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
//        List<PostDTO> postDTOs = new ArrayList<>();
//        for (Post post : posts)
//         {
//            PostDTO postDTO= PostDTO.builder().caption(post.getCaption()).media(post.getMedia()).user(post.getUser()).build();
//            postDTOs.add(postDTO);
//        }
//        return postDTOs;
        return posts;
    }
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }
    public Post addComment(String postId, Comment comment) {
        // Tìm bài viết theo ID
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.get();
        if (post==null) {
            throw new RuntimeException("Post not found!");
        }
        // Thêm comment vào bài viết
        comment.setId(new org.bson.types.ObjectId().toString()); // Tạo ID ngẫu nhiên
        post.getComments().add(comment);

        // Lưu lại bài viết
        return postRepository.save(post);
    }
    public Post addCommentToPost(String postId, Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Post post = optionalPost.get();
        post.getComments().add(comment);
        return postRepository.save(post);
    }
}
