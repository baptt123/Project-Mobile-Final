package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.model.Friend;
import com.example.demo_app_chat.service.FriendService;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService service;
//    // Lấy ds bạn bè
//    @GetMapping
//    public List<Friend> getAllFriends(){
//        return friendService.findAll();
//    }
//    // tìm kiếm bạn bè theo tên
//    @GetMapping("/search")
//    public List<Friend> searchFriends (@RequestParam String keyword){
//        return friendRepository.findByNameContainingIgnoreCase(keyword);
//    }
//    // Thêm bạn bè mới
//    @PostMapping
//    public Friend addFriend(@RequestBody Friend friend){
//        return friendRepository.save(friend);
//    }
//    //Xóa bạn bè
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteFriend(@PathVariable String id){
//        friendService.deleteById(id);
////        return ResponseEntity.ok("Xóa bạn bè thành công");
//        return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).build();
//    }
//    // Cập nhật trangj thái yêu thích bạn bè
//    @PutMapping("/{id}/favorite")
//    public ResponseEntity<Friend> updateFavoriteStatus(@PathVariable String id, @RequestParam boolean isFavorite){
//        Friend friend = friendRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy bạn bè"));
//        friend.setFavorite(isFavorite);
//        friendRepository.save(friend);
//        return ResponseEntity.ok(friend);
//    }
@GetMapping
public List<Friend> getAllFriends(@RequestParam(required = false) String search) {
    if (search != null) {
        return service.searchFriends(search);
    }
    return service.getAllFriends();
}

    @PostMapping
    public Friend addFriend(@RequestBody Friend friend) {
        return service.addFriend(friend);
    }

    @PutMapping("/{id}")
    public Friend updateFriend(@PathVariable String id, @RequestBody Friend friend) {
        return service.updateFriend(id, friend);
    }

    @DeleteMapping("/{id}")
    public void deleteFriend(@PathVariable String id) {
        service.deleteFriend(id);
    }

}
