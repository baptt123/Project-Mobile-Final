//package com.example.demo_app_chat.controller;
//
//import com.example.demo_app_chat.model.FollowRequest;
//import com.example.demo_app_chat.model.User;
//import com.example.demo_app_chat.service.FollowService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//public class FollowController {
//    @Autowired
//    private FollowService followService;
//    @PostMapping
//    public ResponseEntity<?> follow(@RequestBody FollowRequest request) {
//        // Kiểm tra nếu người dùng theo dõi một người khác
//        boolean success = followService.follow(request.getFollowerId(), request.getFollowingId());
//        return success ? ResponseEntity.ok("Follow successful") : ResponseEntity.badRequest().body("Already followed");
//
//    }
//    @DeleteMapping
//    public ResponseEntity<?> unfollow(@RequestBody FollowRequest request) {
//        // Kiểm tra nếu người dùng hủy theo dõi một người khác
//        boolean success = followService.unfollow(request.getFollowerId(), request.getFollowingId());
//        return success ? ResponseEntity.ok("Unfollow successful") : ResponseEntity.badRequest().body("Not following");
//    }
//
//    @GetMapping("/followers/{userId}")
//    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {  // Thay Long -> String
//        return ResponseEntity.ok(followService.getFollowers(userId));
//    }
//
//    @GetMapping("/following/{userId}")
//    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {  // Thay Long -> String
//        return ResponseEntity.ok(followService.getFollowing(userId));
//    }
//}
