package com.example.demo_app_chat.service;

import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private UserRepository userRepository;

    public void followUser(String currentUserId, String targetUserId) {
//        User currentUser = userRepository.findById(currentUserId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + currentUserId));
//        User targetUser = userRepository.findById(targetUserId)
//                .orElseThrow(() -> new UserNotFoundException("Target user not found with ID: " + targetUserId));
        User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
        User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
        // Thêm targetUser vào danh sách following của currentUser
        currentUser.getFollowing().add(targetUserId);
        userRepository.save(currentUser);

        // Thêm currentUser vào danh sách followers của targetUser
        targetUser.getFollowers().add(currentUserId);
        userRepository.save(targetUser);

        // Kiểm tra nếu 2 người follow nhau thì trở thành bạn bè
        if (targetUser.getFollowing().contains(currentUserId)) {
            currentUser.getFriends().add(targetUserId);
            targetUser.getFriends().add(currentUserId);
            userRepository.save(currentUser);
            userRepository.save(targetUser);
        }
    }

    public void unfollowUser(String currentUserId, String targetUserId) {
//        User currentUser = userRepository.findById(currentUserId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + currentUserId));
//        User targetUser = userRepository.findById(targetUserId)
//                .orElseThrow(() -> new UserNotFoundException("Target user not found with ID: " + targetUserId));
    User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
    User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
        // Xóa targetUser khỏi danh sách following của currentUser
        currentUser.getFollowing().remove(targetUserId);
        userRepository.save(currentUser);

        // Xóa currentUser khỏi danh sách followers của targetUser
        targetUser.getFollowers().remove(currentUserId);
        userRepository.save(targetUser);

        // Nếu họ là bạn bè thì xóa khỏi danh sách friends
        currentUser.getFriends().remove(targetUserId);
        targetUser.getFriends().remove(currentUserId);
        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }
}


//@Autowired
//private UserRepository userRepository;
//// Follow Người dùng khác
//public void followUser(String currentUserId, String targetUserId){
//    // Lấy thông tin của currentUser và targetUser
//    User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
//    User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
//    // Cập nhật ds following và followers
//    if (!currentUser.getFollowing().contains(targetUserId)) {
//        currentUser.getFollowing().add(targetUserId);
//    }
//    if (!targetUser.getFollowers().contains(currentUserId)) {
//        targetUser.getFollowers().add(currentUserId);
//    }
//
//    // Nếu targetUser đang follow currentUser, và ngược lại -->friend
//    if (targetUser.getFollowing().contains(currentUserId) && currentUser.getFollowing().contains(targetUserId)) {
//        currentUser.getFriends().add(targetUserId);
//        targetUser.getFriends().add(currentUserId);
//
//        // Lưu lại thay đổi
//        userRepository.save(currentUser);
//        userRepository.save(targetUser);
//    }
//}
//
//// Unfollow
//public void unfollowUser(String currentUserId, String targetUserId){
//    // Lấy thông tin của currentUser và targetUser
//    User currentUser = (User) userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found:" + currentUserId));
//    User targetUser = (User) userRepository.findById(targetUserId).orElseThrow(()-> new RuntimeException("User not found:" + targetUserId));
//    // Xóa khỏi ds following và followers
//    currentUser.getFollowing().remove(targetUserId);
//    targetUser.getFollowers().remove(currentUserId);
//
//    // Kiểm tra xem hai người có đang là bạn bè không
//    if (currentUser.getFriends().contains(targetUserId)) {
//        // Xóa B khỏi danh sách bạn bè của A
//        currentUser.getFriends().remove(targetUserId);
//
//        // Nếu B vẫn follow A, giữ nguyên danh sách bạn bè của B
//        if (!targetUser.getFollowing().contains(currentUserId)) {
//            // Nếu B không còn follow A, xóa A khỏi danh sách bạn bè của B
//            targetUser.getFriends().remove(currentUserId);
//        }
//    }
//
//    // Lưu lại thay đổi
//    userRepository.save(currentUser);
//    userRepository.save(targetUser);
//}