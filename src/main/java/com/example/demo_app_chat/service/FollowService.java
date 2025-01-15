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
        User currentUser = (User) userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
        User targetUser = (User)userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUserId));

        initializeUserLists(currentUser);
        initializeUserLists(targetUser);

        if (currentUser.getFriends().contains(targetUserId)) {
            // Already friends, no action needed
            return;
        }

        boolean isMutualFollow = targetUser.getFollowing().contains(currentUserId);

        if (isMutualFollow) {
            // Mutual follow: become friends
            currentUser.getFriends().add(targetUserId);
            targetUser.getFriends().add(currentUserId);

            // Remove from following/followers
            currentUser.getFollowing().remove(targetUserId);
            targetUser.getFollowers().remove(currentUserId);
            targetUser.getFollowing().remove(currentUserId);
            currentUser.getFollowers().remove(targetUserId);
        } else {
            // Not mutual follow: add to following and followers
            if (!currentUser.getFollowing().contains(targetUserId)) {
                currentUser.getFollowing().add(targetUserId);
            }
            if (!targetUser.getFollowers().contains(currentUserId)) {
                targetUser.getFollowers().add(currentUserId);
            }
        }

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }
    public void unfollowUser(String currentUserId, String targetUserId) {
        User currentUser = (User) userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUserId));
        User targetUser =(User) userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found: " + targetUserId));

        initializeUserLists(currentUser);
        initializeUserLists(targetUser);

        if (currentUser.getFriends().contains(targetUserId)) {
            // If they are friends, remove friendship and make them follower/following
            currentUser.getFriends().remove(targetUserId);
            targetUser.getFriends().remove(currentUserId);

            // A becomes a follower of B
            if (!currentUser.getFollowers().contains(targetUserId)) {
                currentUser.getFollowers().add(targetUserId);
            }

            // B becomes a following of A
            if (!targetUser.getFollowing().contains(currentUserId)) {
                targetUser.getFollowing().add(currentUserId);
            }
        } else {
            // If they are not friends, just remove from following/followers
            currentUser.getFollowing().remove(targetUserId);
            targetUser.getFollowers().remove(currentUserId);
        }

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }

    private void initializeUserLists(User user) {
        // Kiểm tra và khởi tạo các danh sách nếu chúng là null
        if (user.getFollowing() == null) {
            user.setFollowing(new ArrayList<>());
        }
        if (user.getFollowers() == null) {
            user.setFollowers(new ArrayList<>());
        }
        if (user.getFriends() == null) {
            user.setFriends(new ArrayList<>());
        }
    }

    public List<User> getFollowers(String userId) {
        User user = (User)userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> followerIds = user.getFollowers(); // Lấy danh sách ID từ trường followers

        // Truy vấn thông tin chi tiết của các followers từ MongoDB
        return userRepository.findAllById(followerIds);
    }
    // Lấy danh sách bạn bè
    public List<User> getFriends(String userId) {
        User user = (User)userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> friendIds = user.getFriends(); // Lấy danh sách ID từ trường friends

        // Truy vấn thông tin chi tiết của các bạn bè từ MongoDB
        return userRepository.findAllById(friendIds);
    }

    // Lấy danh sách người dùng mà bạn đang theo dõi
    public List<User> getFollowing(String userId) {
        User user = (User)userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> followingIds = user.getFollowing(); // Lấy danh sách ID từ trường following

        // Truy vấn thông tin chi tiết của những người đang theo dõi từ MongoDB
        return userRepository.findAllById(followingIds);
    }
}