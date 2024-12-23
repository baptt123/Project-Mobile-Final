package com.example.demo_app_chat.controller;

import com.example.demo_app_chat.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.time.Duration;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/uploadfile")
public class CloudinaryUploadFileController {
    @Autowired
    private final CloudinaryService cloudinaryService;
    // Sinks để phát sự kiện
    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

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
            sink.tryEmitNext("1");
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
            sink.tryEmitNext("1");
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload file that bai");
        }


    }

    @GetMapping(value = "/sse/upload", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        return sink.asFlux()
                .delayElements(Duration.ofMillis(100)); // Thêm delay nhỏ để tránh nghẽn
    }

    @GetMapping(value = "/sse/like", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> likeEvents() {
        return sink.asFlux().delayElements(Duration.ofMillis(100));
    }

    @GetMapping(value = "/sse/comment", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> commentEvents() {
        return sink.asFlux().delayElements(Duration.ofMillis(100));
    }

    @GetMapping(value = "/sse/share", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> shareEvents() {
        return sink.asFlux().delayElements(Duration.ofMillis(100));
    }
    @GetMapping(value="/sse/follow",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> followEvents(){
        return sink.asFlux().delayElements(Duration.ofMillis(100));
    }
}

