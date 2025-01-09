//package com.example.demo_app_chat.repository;
//import com.example.demo_app_chat.model.Follow;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface FollowRepository extends JpaRepository<Follow, String> {
//    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);
//    void deleteByFollowerIdAndFollowingId(String followerId, String followingId);
//    List<Follow> findAllByFollowerId(String followerId);
//    List<Follow> findAllByFollowingId(String followingId);
//}
