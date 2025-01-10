package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.repository.UserRepository;
import com.example.demo_app_chat.service.FriendSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/api/list_friend")
@RequestMapping("/api/user")
public class FrienSuggestionController {
    @Autowired
    private UserRepository userRepository;
//      private FriendSuggestionService friendSuggestionService;
// API lấy danh sách bạn bè gợi ý
    @GetMapping("/suggested-friends/{userId}")
    public ResponseEntity<List<User>> getSuggestedFriends(@PathVariable String userId) {
        List<User> suggestedFriends = userRepository.findSuggestedFriends(userId);
        return ResponseEntity.ok(suggestedFriends);
    }
}

//@Autowired
//private FriendSuggestionService friendSuggestionService;
//// API lấy danh sách bạn bè gợi ý
//@GetMapping("/suggested-friends/{userId}")
//public ResponseEntity<List<SuggestFriend>> getSuggestedFriends(@PathVariable String userId) {
//    List<SuggestFriend> suggestedFriends = friendSuggestionService.findSuggestedFriends(userId);
//    return ResponseEntity.ok(suggestedFriends);
//}