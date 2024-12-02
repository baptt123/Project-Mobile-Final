package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/getdata")
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
}
