package com.example.demo_app_chat.service;

import com.example.demo_app_chat.dto.*;
import com.example.demo_app_chat.model.EmailVerification;
import com.example.demo_app_chat.model.User;
import com.example.demo_app_chat.repository.EmailVerifycationRepository;
import com.example.demo_app_chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    EmailVerifycationRepository emailVerifycationRepository;
    @Autowired
    EmailVerifycationService emailVerifycationService;
    @Autowired
    private UserRepository userRepository;
    public User updateFollowingCount(String userId, int count, String action) {
        // Lấy User từ ID
        User user = getUserById(userId);

        // Kiểm tra xem User có tồn tại hay không
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        // Kiểm tra hành động: follow hoặc unfollow
        int updatedCount = user.getFollowingCount();
        if ("follow".equalsIgnoreCase(action)) {
            updatedCount += count; // Cộng thêm count khi follow
        } else if ("unfollow".equalsIgnoreCase(action)) {
            updatedCount -= count; // Trừ count khi unfollow
        } else {
            throw new IllegalArgumentException("Invalid action: " + action); // Nếu action không hợp lệ
        }

        // Kiểm tra nếu followingCount không âm
        if (updatedCount < 0) {
            throw new IllegalArgumentException("Following count cannot be negative");
        }

        // Cập nhật giá trị followingCount và lưu lại
        user.setFollowingCount(updatedCount);
        return userRepository.save(user);
    }



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User findUser(String user,String password){
        return userRepository.findByUsernameAndPassword(user,password);
    }
    public boolean findUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        return user != null; // Trả về true nếu tìm thấy, false nếu không
    }
    public boolean findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user!= null; // Trả về true nếu tìm thấy, false nếu không
    }

    public User saveUser(SignUpDTO userDTO) {
        if (findUserByUserName(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (findUserByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        // Xây dựng User mới nếu username chưa tồn tại
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();

        return userRepository.save(user); // Lưu user vào cơ sở dữ liệu
    }

    public User getUserById(String id) {
        return (User) userRepository.findById(id).orElse(null);
    }
    public boolean updateUser(UpdateUserDTO updateUserDTO) {
        // Tìm người dùng theo ID
        User user = (User) userRepository.findById(updateUserDTO.getId()).orElse(null);
        if (user == null) {
            return false;
        }

        // Cập nhật thông tin người dùng
        user.setEmail(updateUserDTO.getEmail());
        user.setFullName(updateUserDTO.getFullName());
        user.setGender(updateUserDTO.getGender());
        user.setProfileImagePath(updateUserDTO.getProfileImagePath());

        // Lưu thay đổi
        userRepository.save(user);
        return true; // Cập nhật thành công
    }
public boolean sendCode(String code) {
return false;
}
public boolean resetPassword(ForgotPasswordDTO forgotPasswordDTO){
        return false;
    }
    public boolean verifyCode(String code) {
        return false;
    }
    public boolean updatePassword(String code, String newPassword) {
        return false;
    }
    public boolean changePasswordWithCode(EmailVerifycationDTO emailVerifycationDTO) {
        // Kiểm tra mã xác thực và email
        EmailVerification emailVerification = emailVerifycationRepository.findByEmail(emailVerifycationDTO.getEmail());

        // Kiểm tra xem mã xác thực có tồn tại không và mã có khớp không
        if (emailVerification == null || !emailVerification.getCode().equals(emailVerifycationDTO.getCode())) {
            return false;  // Mã xác thực không hợp lệ
        }

        // Kiểm tra xem mã xác thực có hết hạn chưa
        if (emailVerification.getTime_expire().before(new Date())) {
            return false;  // Mã xác thực đã hết hạn
        }

        // Tìm người dùng theo email
        User user = userRepository.findByEmail(emailVerifycationDTO.getEmail());
        if (user == null) {
            return false;  // Không tìm thấy người dùng
        }

        // Cập nhật mật khẩu mới từ DTO
        user.setPassword(emailVerifycationDTO.getNewPassword());
        userRepository.save(user);  // Lưu thông tin người dùng với mật khẩu mới

        // Xóa mã xác thực sau khi sử dụng
        emailVerifycationRepository.delete(emailVerification);
        emailVerifycationService.remoteCodeHasBeenUse(emailVerification.getCode());
        return true;  // Mật khẩu đã được cập nhật thành công
    }

}
