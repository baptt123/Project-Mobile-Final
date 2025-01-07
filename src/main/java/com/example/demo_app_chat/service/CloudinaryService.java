package com.example.demo_app_chat.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo_app_chat.model.Notifications;
import com.example.demo_app_chat.model.Post;
import com.example.demo_app_chat.model.Story;
import com.example.demo_app_chat.model.UserInfo;
import com.example.demo_app_chat.repository.MessageRepository;
import com.example.demo_app_chat.repository.NotificationRepository;
import com.example.demo_app_chat.repository.PostRepository;
import com.example.demo_app_chat.repository.StoryRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class CloudinaryService {

    public static final String CLOUD_NAME = "dllqdawgo";
    public static final String API_KEY = "518211464238961";
    public static final String API_SECRET = "D7rrw9Zz5E5JGRSNADfblVPprtQ";
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final StoryRepository storyRepository;
    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final NotificationRepository notificationRepository;
    public CloudinaryService(PostRepository postRepository, StoryRepository storyRepository, MessageRepository messageRepository,NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.messageRepository = messageRepository;
    }

    public String uploadFileAndSaveStory(MultipartFile file,String userName) throws Exception {
        // Tạo Cloudinary instance với thông tin cấu hình trực tiếp
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        ));

        // Chuyển MultipartFile thành File tạm thời
        File tempFile = convertMultipartFileToFile(file);

        try {
            // Upload file lên Cloudinary
            Map<String, Object> fileupload = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
//                    "resource_type", "video"
            );

            Map uploadResult = cloudinary.uploader().upload(tempFile, fileupload);

            // Xóa file tạm sau khi upload
            tempFile.delete();
            Random rand = new Random();
            // Lấy URL của file đã upload
            String fileUrl = (String) uploadResult.get("url");
            Story story = Story.builder().id(UUID.randomUUID().toString()).imageStory(fileUrl).idUser(rand.nextInt(10)).userName(userName).build();
            storyRepository.save(story);
            Notifications notifications=Notifications.builder().id(UUID.randomUUID().toString()).action("New notification at"+new Date()).idUser(new Random().nextInt(1000)).build();
            notificationRepository.save(notifications);
            return fileUrl;
        } catch (IOException e) {
            throw new IOException("Upload that bai");
        }

    }

    public String uploadFileAndSavePost(MultipartFile file, String caption, String userName) throws Exception {
        // Tạo Cloudinary instance với thông tin cấu hình trực tiếp
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        ));

        // Chuyển MultipartFile thành File tạm thời
        File tempFile = convertMultipartFileToFile(file);

        try {
            // Đặt các tham số upload và chỉ định loại tài nguyên (video)
            Map<String, Object> fileupload = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
//                    "resource_type", "video" // Đảm bảo upload video
            );

            // Upload file lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(tempFile, fileupload);

            // Xóa file tạm sau khi upload
            tempFile.delete();

            // Lấy URL của file đã upload
            String fileUrl = (String) uploadResult.get("url");

            // Tạo đối tượng Post và lưu vào MongoDB
            UserInfo user = UserInfo.builder().userName(userName).profileImagePath("temp_link").build();
            Post post = Post.builder().user(user).media(fileUrl).caption(caption)
                    .likeCount(1).saveCount(2).isSaved(2).isLike(1).shareCount(2)
                    .comments(null).build();
            postRepository.save(post);
            Notifications notifications=Notifications.builder().id(UUID.randomUUID().toString()).action("New notification at"+new Date()).idUser(new Random().nextInt(1000)).build();
//            notificationService.save(notifications);
            notificationRepository.save(notifications);
            return fileUrl;

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Upload file thất bại");
        }
    }

    // Phương thức chuyển MultipartFile thành File tạm thời
    public File convertMultipartFileToFile(MultipartFile file) throws Exception {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}


