package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.repository.UserRepository;
import com.example.demo_app_chat.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;

    // API Follow người dùng khác
    @PostMapping("/{currentUserId}/follow/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable String currentUserId, @PathVariable String targetUserId) {
        followService.followUser(currentUserId, targetUserId);
        return ResponseEntity.ok("Followed successfully!");
    }

    // API Unfollow người dùng khác
    @DeleteMapping("/{currentUserId}/unfollow/{targetUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable String currentUserId, @PathVariable String targetUserId) {
        followService.unfollowUser(currentUserId, targetUserId);
        return ResponseEntity.ok("Unfollowed successfully!");
    }
    // API gợi ý bạn bè
    @GetMapping("/{userId}/suggestedFriends")
    public ResponseEntity<List<User>> getSuggestedFriends(@PathVariable String userId) {
        List<User> suggestedFriends = userRepository.findSuggestedFriends(userId);
        return ResponseEntity.ok(suggestedFriends);
    }

    // API lấy danh sách thông tin followers
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
        List<User> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }
    // API lấy danh sách bạn bè
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable String userId) {
        List<User> friends = followService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    // API lấy danh sách người dùng mà bạn đang theo dõi
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String userId) {
        List<User> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
//    // API kiểm tra trạng thái follow
//    @GetMapping("/{currentUserId}/isFollowing/{targetUserId}")
//    public ResponseEntity<Boolean> isFollowing(@PathVariable String currentUserId, @PathVariable String targetUserId) {
//        boolean isFollowing = followService.isFollowing(currentUserId, targetUserId);
//        return ResponseEntity.ok(isFollowing);
//    }
