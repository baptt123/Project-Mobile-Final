package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.UpdatePostRequest;
import com.example.demo_app_chat.model.Comment;
import com.example.demo_app_chat.model.Post;
import com.example.demo_app_chat.repository.PostRepository;
import com.example.demo_app_chat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/getpost")
    public List<Post> getPost() {
        return postService.getAllPosts();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable String id) {
        Optional<Post> post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/get/updateLike/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody UpdatePostRequest request) {
        // Lấy bài viết theo ID từ repository
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Post post = postOptional.get();

        // Cập nhật trạng thái Like
        if (request.getIsLike() != 0) {
            if (request.getIsLike() == 1) {
                postOptional.get().setLikeCount(postOptional.get().getLikeCount() + 1); // Tăng số lượng Like
            } else if (request.getIsLike() == 0) {
                postOptional.get().setLikeCount(postOptional.get().getLikeCount() - 1); // Giảm số lượng Like
            }
            postOptional.get().setIsLike(request.getIsLike()); // Cập nhật trạng thái Like
        }

        // Cập nhật trạng thái Save
        if (request.getIsSaved() != 0) {
            if (request.getIsSaved() == 1) {
                postOptional.get().setSaveCount(postOptional.get().getSaveCount() + 1); // Tăng số lượng Save
            } else if (request.getIsSaved() == 0) {
                postOptional.get().setSaveCount(postOptional.get().getSaveCount() - 1); // Giảm số lượng Save
            }
            postOptional.get().setIsSaved(request.getIsSaved()); // Cập nhật trạng thái Save
        }

        // Lưu thay đổi vào database
        postRepository.save(post);

        // Trả về bài viết đã cập nhật
        return ResponseEntity.ok(post);
    }
    @PostMapping("/get/{postId}/comments")
    public ResponseEntity<?> getCommentsToPost(@PathVariable String postId, @RequestBody Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.get();
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(404).body("Post not found!");
        }

        if (comment.getFullName()== null || comment.getText() == null) {
            return ResponseEntity.badRequest().body("Fullname and text are required!");
        }
        post.getComments().add(comment);
        Post updatedPost = postRepository.save(post);

        return ResponseEntity.ok(updatedPost);

    }
}

