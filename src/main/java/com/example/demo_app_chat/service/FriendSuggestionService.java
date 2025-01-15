package com.example.demo_app_chat.service;

//import com.example.demo_app_chat.model.SuggestFriend;

import com.example.demo_app_chat.model.User;
//import com.example.demo_app_chat.repository.SuggesFriendRepository;
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

    public List<User> findSuggestedFriends(String userId) {
        User currentUser = (User) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Tìm những người không có trong danh sách "following" của người dùng hiện tại
        List<User> allUsers = userRepository.findAll();
        List<User> suggestedFriends = allUsers.stream()
                .filter(user -> !currentUser.getFollowing().contains(user.getId()) && !user.getId().equals(userId))
                .collect(Collectors.toList());
        return suggestedFriends;

    }
}
//        User currentUser = (User) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<User> following = userRepository.findSuggestedFriends(userId);
//        List<String> followingIds = following.stream().map(User::getId).collect(Collectors.toList());
//
//        // Lọc người dùng chưa được follow
//        return userRepository.findAll().stream()
//                .filter(user -> !followingIds.contains(user.getId()) && !user.getId().equals(userId))
//                .collect(Collectors.toList());


//    public List<User> findSuggestedFriends(String userId) {
//        // Lấy danh sách người dùng được gợi ý từ Repository
//        List<User> suggestedFriends = userRepository.findSuggestedFriends(userId);
//
//        // Trả về danh sách User với đầy đủ thông tin cần thiết
//        return suggestedFriends.stream()
//                .filter(user -> !user.getId().equals(userId)) // Bỏ những ID trùng với userId
//                .map(user -> {
//                    User friend = new User();
//                    friend.setId(user.getId());
//                    friend.setFullName(user.getFullName()); // Lấy fullname
//                    friend.setProfileImagePath(user.getProfileImagePath()); // Lấy ảnh đại diện
//                    return friend;
//                })
//                .collect(Collectors.toList());
//    }