package com.example.demo_app_chat.service;

import com.example.demo_app_chat.model.Friend;
import com.example.demo_app_chat.repository.FriendRepository;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;
    // Lấy ds bạn bè
    public List<Friend> getAllFriends(){
        return friendRepository.findAll();
    }
    // tìm kiếm bạn bè theo tên
    public List<Friend> searchFriends (String keyword){
        return friendRepository.findByNameContainingIgnoreCase(keyword);
    }
    // Thêm bạn bè mới
    public Friend addFriend(Friend friend){
        return friendRepository.save(friend);
    }
    //Xóa bạn bè
//    public ResponseEntity<String> deleteFriend(String id){
//        friendRepository.deleteById(id);
////        return ResponseEntity.ok("Xóa bạn bè thành công");
//        return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
//    }
    public void deleteFriend(String id) {
        friendRepository.deleteById(id);
    }
    // Cập nhật trangj thái yêu thích bạn bè
//    public ResponseEntity<Friend> updateFavoriteStatus(String id, boolean isFavorite){
//        Friend friend = friendRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy bạn bè"));
//        friend.setFavorite(isFavorite);
//        friendRepository.save(friend);
//        return ResponseEntity.ok(friend);
//    }
    public Friend updateFriend(String id, Friend updatedFriend) {
        return friendRepository.findById(id).map(friend -> {
            friend.setName(updatedFriend.getName());
            friend.setProfileImageUrl(updatedFriend.getProfileImageUrl());
            return friendRepository.save(friend);
        }).orElseThrow(() -> new RuntimeException("Friend not found"));
    }
}
