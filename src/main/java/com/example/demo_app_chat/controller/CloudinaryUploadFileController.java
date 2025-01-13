package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.dto.UpdateAvatarDTO;
import com.example.demo_app_chat.model.Message;
import com.example.demo_app_chat.service.CloudinaryService;
import com.example.demo_app_chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/uploadfile")
public class CloudinaryUploadFileController {
    @Autowired
    private final CloudinaryService cloudinaryService;

    public CloudinaryUploadFileController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;

    }

    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("caption") String caption,
                                             @RequestParam("userName") String userName) {
        try {
            // Gọi service để upload file và lưu thông tin vào MongoDB
            String fileUrl = cloudinaryService.uploadFileAndSavePost(file, caption, userName);

            return ResponseEntity.ok("Upload thành công: " + fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload thất bại");
        }
    }

    @PostMapping("/uploadfilestory")
    public ResponseEntity<String> uploadFileStory(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadFileAndSaveStory(file);
            System.out.println(fileUrl);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload file that bai");
        }
    }
    @PutMapping("/updateavatar/{id}")
    public ResponseEntity<String> updateAvatar(@PathVariable String id,
                                               @RequestParam("file") MultipartFile file) {
        try {
            // Tạo đối tượng DTO với ID người dùng
            UpdateAvatarDTO updateAvatarDTO = new UpdateAvatarDTO(id, "default_image_path");

            // Gọi service để upload ảnh lên Cloudinary và cập nhật avatar
            String fileUrl = cloudinaryService.updateAvatar(updateAvatarDTO, file);

            return ResponseEntity.ok("Cập nhật avatar thành công: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Cập nhật avatar thất bại: " + e.getMessage());
        }
    }

}

