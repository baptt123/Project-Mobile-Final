//package com.example.demo_app_chat.service;
//
//import com.example.demo_app_chat.model.Follow;
//import com.example.demo_app_chat.model.User;
//import com.example.demo_app_chat.repository.FollowRepository;
//import com.example.demo_app_chat.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class FollowService {
//    @Autowired
//    private FollowRepository followRepository;
////    @Autowired
//    private UserRepository userRepository;
//public boolean follow(String followerId, String followingId) {
//    if (followerId.equals(followingId)) {
//        throw new IllegalArgumentException("User cannot follow themselves.");
//    }
//
//    if (!followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
//        User follower = userRepository.findById(followerId)
//                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
//        User following = userRepository.findById(followingId)
//                .orElseThrow(() -> new EntityNotFoundException("Following user not found"));
//
//        Follow follow = new Follow();
//        follow.setFollowerId(followerId);  // Thay vì set User, chỉ cần ID
//        follow.setFollowingId(followingId); // Thay vì set User, chỉ cần ID
//        follow.setCreatedAt(LocalDateTime.now());
//
//        followRepository.save(follow);
//
//        // Update counts
//        follower.setFollowingCount(follower.getFollowingCount() + 1);
//        following.setFollowersCount(following.getFollowersCount() + 1);
//
//        userRepository.save(follower);
//        userRepository.save(following);
//
//        return true;
//    }
//    return false;
//}
//
//    public boolean unfollow(String followerId, String followingId) { // Sửa Long -> String
//        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
//            followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
//
//            // Update counts
//            User follower = userRepository.findById(followerId)
//                    .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
//            User following = userRepository.findById(followingId)
//                    .orElseThrow(() -> new EntityNotFoundException("Following user not found"));
//
//            follower.setFollowingCount(follower.getFollowingCount() - 1);
//            following.setFollowersCount(following.getFollowersCount() - 1);
//
//            userRepository.save(follower);
//            userRepository.save(following);
//
//            return true;
//        }
//        return false;
//    }
//
//    public List<User> getFollowers(String userId) {
//        return followRepository.findAllByFollowingId(userId).stream()
//                .map(Follow::getFollower)
//                .collect(Collectors.toList());
//    }
//
//    public List<User> getFollowing(String userId) {
//        return followRepository.findAllByFollowerId(userId).stream()
//                .map(Follow::getFollowing)
//                .collect(Collectors.toList());
//    }
//
//}
