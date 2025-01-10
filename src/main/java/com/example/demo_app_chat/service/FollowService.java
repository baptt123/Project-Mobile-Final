package com.example.demo_app_chat.service;

//import com.example.demo_app_chat.model.SuggestFriend;
import com.example.demo_app_chat.model.User;
//import com.example.demo_app_chat.repository.SuggesFriendRepository;
import com.example.demo_app_chat.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private UserRepository userRepository;
    public void followUser(String currentUserId, String targetUserId) {

        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
        // Thêm targetUser vào danh sách following của currentUser
        currentUser.getFollowing().add(targetUserId);
        userRepository.save(currentUser);

        // Thêm currentUser vào danh sách followers của targetUser
        targetUser.getFollowers().add(currentUserId);
        userRepository.save(targetUser);

        // Kiểm tra nếu 2 người follow nhau thì trở thành bạn bè
        if (!currentUser.getFollowing().contains(targetUserId)) {
            currentUser.getFollowing().add(targetUserId);
            targetUser.getFollowers().add(currentUserId);
            userRepository.save(currentUser);
            userRepository.save(targetUser);
        }

        // Nếu cả hai follow nhau -> trở thành bạn bè
        if (targetUser.getFollowing().contains(currentUserId)) {
            if (!currentUser.getFriends().contains(targetUserId)) {
                currentUser.getFriends().add(targetUserId);
                targetUser.getFriends().add(currentUserId);
                userRepository.save(currentUser);
                userRepository.save(targetUser);
            }
        }
//        if (targetUser.getFollowing().contains(currentUserId)) {
//            currentUser.getFriends().add(targetUserId);
//            targetUser.getFriends().add(currentUserId);
//            userRepository.save(currentUser);
//            userRepository.save(targetUser);
//        }
    }
    public void unfollowUser(String currentUserId, String targetUserId) {
        User currentUser = (User) userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
        User targetUser = (User) userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUserId));

        // Xóa targetUser khỏi danh sách following của currentUser
        if (currentUser.getFollowing() != null) {
            currentUser.getFollowing().remove(targetUserId);
        }
        userRepository.save(currentUser);

        // Xóa currentUser khỏi danh sách followers của targetUser
        if (targetUser.getFollowers() != null) {
            targetUser.getFollowers().remove(currentUserId);
        }
        userRepository.save(targetUser);

        // Nếu họ là bạn bè thì xóa khỏi danh sách friends
        if (currentUser.getFriends() != null) {
            currentUser.getFriends().remove(targetUserId);
        } else {
            currentUser.setFriends(new ArrayList<>());
        }

        if (targetUser.getFriends() != null) {
            targetUser.getFriends().remove(currentUserId);
        } else {
            targetUser.setFriends(new ArrayList<>());
        }

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }
//    public void unfollowUser(String currentUserId, String targetUserId) {
//
//        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
//        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
//        // Xóa targetUser khỏi danh sách following của currentUser
//        currentUser.getFollowing().remove(targetUserId);
//        userRepository.save(currentUser);
//
//        // Xóa currentUser khỏi danh sách followers của targetUser
//        targetUser.getFollowers().remove(currentUserId);
//        userRepository.save(targetUser);
//
//        // Nếu họ là bạn bè thì xóa khỏi danh sách friends
//        currentUser.getFriends().remove(targetUserId);
//        targetUser.getFriends().remove(currentUserId);
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//    }
}

//    public void followUser(String currentUserId, String targetUserId) {
//        // Lấy người dùng hiện tại và mục tiêu
//        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
//        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
//        // Thêm targetUser vào danh sách following của currentUser
//        // Kiểm tra nếu đã follow rồi thì không thêm nữa
//        if (!currentUser.getFollowing().contains(targetUserId)) {
//            currentUser.getFollowing().add(targetUserId);
//            targetUser.getFollowers().add(currentUserId);
//            userRepository.save(currentUser);
//            userRepository.save(targetUser);
//        }
//
//        // Nếu cả hai follow nhau -> trở thành bạn bè
//        if (targetUser.getFollowing().contains(currentUserId)) {
//            if (!currentUser.getFriends().contains(targetUserId)) {
//                currentUser.getFriends().add(targetUserId);
//                targetUser.getFriends().add(currentUserId);
//                userRepository.save(currentUser);
//                userRepository.save(targetUser);
//            }
//        }
//    }
//
//    public void unfollowUser(String currentUserId, String targetUserId) {
//        // Lấy người dùng hiện tại và mục tiêu
//        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
//        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
//        // Xóa follow nếu tồn tại
//        currentUser.getFollowing().remove(targetUserId);
//        targetUser.getFollowers().remove(currentUserId);
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//
//        // Nếu là bạn bè thì xóa khỏi danh sách bạn bè
//        currentUser.getFriends().remove(targetUserId);
//        targetUser.getFriends().remove(currentUserId);
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//
//    }
//    public boolean isFollowing(String currentUserId, String targetUserId) {
//        // Kiểm tra trạng thái follow
//        User currentUser = (User) userRepository.findById(currentUserId)
//                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
//        return currentUser.getFollowing().contains(targetUserId);
//    }




//    public void followUser(String currentUserId, String targetUserId) {
//        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
//        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("User not found: " + targetUserId));
//
//        // Tạo thông tin Follow cho người dùng hiện tại
//        Follow currentUserFollow = new Follow(currentUser.getId(), currentUser.getFullName(), currentUser.getProfileImagePath());
//
//        // Tạo thông tin Follow cho người dùng mục tiêu
//        Follow targetUserFollow = new Follow(targetUser.getId(), targetUser.getFullName(), targetUser.getProfileImagePath());
//
//        // Thêm người dùng mục tiêu vào danh sách following của currentUser
//        if (!currentUser.getFollowing().stream().anyMatch(f -> f.getId().equals(targetUserId))) {
//            currentUser.getFollowing().add(targetUserFollow);
//            targetUser.getFollowers().add(currentUserFollow);
//            userRepository.save(currentUser);
//            userRepository.save(targetUser);
//        }
//
//        // Nếu cả hai người follow nhau thì trở thành bạn bè
//        if (targetUser.getFollowing().stream().anyMatch(f -> f.getId().equals(currentUserId))) {
//            if (!currentUser.getFriends().stream().anyMatch(f -> f.getId().equals(targetUserId))) {
//                currentUser.getFriends().add(targetUserFollow);
//                targetUser.getFriends().add(currentUserFollow);
//                userRepository.save(currentUser);
//                userRepository.save(targetUser);
//            }
//        }
//    }
//
//    public void unfollowUser(String currentUserId, String targetUserId) {
//        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
//        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(() -> new RuntimeException("User not found: " + targetUserId));
//
//        // Xóa người dùng mục tiêu khỏi danh sách following và followers
//        currentUser.getFollowing().removeIf(f -> f.getId().equals(targetUserId));
//        targetUser.getFollowers().removeIf(f -> f.getId().equals(currentUserId));
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//
//        // Xóa khỏi danh sách bạn bè nếu có
//        currentUser.getFriends().removeIf(f -> f.getId().equals(targetUserId));
//        targetUser.getFriends().removeIf(f -> f.getId().equals(currentUserId));
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//    }
//        public boolean isFollowing(String currentUserId, String targetUserId) {
//        // Kiểm tra trạng thái follow
//        User currentUser = (User) userRepository.findById(currentUserId)
//                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
//        return currentUser.getFollowing().contains(targetUserId);
//    }