package com.example.demo_app_chat.service;

import com.example.demo_app_chat.model.User;
//import com.example.demo_app_chat.repository.SuggesFriendRepository;
import com.example.demo_app_chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendSuggestionService {
    @Autowired
    private UserRepository userRepository;
//    private SuggesFriendRepository suggesFriendRepository;
    public List<User> findSuggestedFriends(String userId) {
        User currentUser = (User) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Tìm những người không có trong danh sách "following" của người dùng hiện tại
        List<User> allUsers = userRepository.findAll();
        List<User> suggestedFriends = allUsers.stream()
                .filter(user -> !currentUser.getFollowing().contains(user.getId()) && !user.getId().equals(userId))
                .collect(Collectors.toList());

        return suggestedFriends;
//@Autowired
//private SuggesFriendRepository suggesFriendRepository;
//
//    public List<SuggestFriend> findSuggestedFriends(String userId) {
//        // Lấy người dùng hiện tại
//        SuggestFriend currentUser = suggesFriendRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Lấy danh sách bạn bè gợi ý từ repository
//        List<SuggestFriend> suggestedFriends = suggesFriendRepository.findSuggestedFriends(userId);
//
//        return suggestedFriends.stream()
//                .filter(friend -> !currentUser.getFollowing().contains(friend.getId()))
//                .collect(Collectors.toList());
    }
}
