package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.*;
import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.service.EmailVerifycationService;
import com.example.demo_app_chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    EmailVerifycationService emailVerifycationService;
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUserProfile() {
        List<User> list = userService.getAllUsers();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // sing in : đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        // Tìm người dùng dựa trên username và password
        User user = userService.findUser(userDTO.getUsername(), userDTO.getPassword());

        if (user != null) {
            // Xóa hoặc mã hóa password trước khi trả về thông tin
//            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


    // sign up : đăng kí
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody SignUpDTO user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/following")
    public ResponseEntity<?> updateFollowing(@RequestBody UpdateFollowingDTO updateFollowingDTO) {
        try {
            // Gọi service để cập nhật following count
            User updatedUser = userService.updateFollowingCount(updateFollowingDTO.getId(), updateFollowingDTO.getCount(), updateFollowingDTO.getAction());
            // Trả về thông tin user đã cập nhật
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // Xử lý trường hợp không tìm thấy user
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateUserDTO updateUserDTO) {
        // Gọi UserService để xử lý logic cập nhật
        boolean isUpdated = userService.updateUser(updateUserDTO);

        // Nếu không cập nhật thành công, trả về NOT FOUND
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy người dùng hoặc cập nhật thất bại.");
        }

        // Trả về thông báo thành công
        return ResponseEntity.ok("Cập nhật thông tin người dùng thành công.");
    }
    @PostMapping("/changewithcode")
    public ResponseEntity<String> changePasswordWithCode(@RequestBody EmailVerifycationDTO emailVerifycationDTO) {
        // Kiểm tra tính hợp lệ của mã xác thực và email
        boolean isVerified = emailVerifycationService.checkEmailVerification(emailVerifycationDTO);

        if (!isVerified) {
            // Nếu mã xác thực không hợp lệ hoặc đã hết hạn
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã xác thực không hợp lệ hoặc đã hết hạn.");
        }

        // Gọi service để thay đổi mật khẩu
        boolean isPasswordChanged = userService.changePasswordWithCode(emailVerifycationDTO);

        if (isPasswordChanged) {
            // Nếu mật khẩu được thay đổi thành công
            return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công.");
        } else {
            // Nếu có lỗi xảy ra khi thay đổi mật khẩu
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi thay đổi mật khẩu.");
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        boolean isUpdated = userService.updateUser(updateUserDTO);
        if (isUpdated) {
            return ResponseEntity.ok("Cập nhật thông tin người dùng thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại.");
        }
    }

}

