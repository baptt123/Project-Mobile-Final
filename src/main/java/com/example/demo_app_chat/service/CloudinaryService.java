    package com.example.demo_app_chat.service;

    import com.cloudinary.Cloudinary;
    import com.cloudinary.utils.ObjectUtils;
    import com.example.demo_app_chat.dto.UpdateAvatarDTO;
    import com.example.demo_app_chat.model.Post;
    import com.example.demo_app_chat.model.Story;
    import com.example.demo_app_chat.model.User;
    import com.example.demo_app_chat.model.UserInfo;
    import com.example.demo_app_chat.repository.MessageRepository;
    import com.example.demo_app_chat.repository.PostRepository;
    import com.example.demo_app_chat.repository.StoryRepository;
    import com.example.demo_app_chat.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.util.Map;
    import java.util.Optional;
    import java.util.Random;

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
        private final UserRepository userRepository;
        // Phương thức cập nhật ảnh đại diện
        public String updateAvatar(UpdateAvatarDTO updateAvatarDTO, MultipartFile file) throws Exception {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", CLOUD_NAME,
                    "api_key", API_KEY,
                    "api_secret", API_SECRET
            ));

            // Chuyển MultipartFile thành File tạm thời
            File tempFile = convertMultipartFileToFile(file);

            try {
                // Upload ảnh lên Cloudinary
                Map<String, Object> fileupload = ObjectUtils.asMap(
                        "use_filename", true,
                        "unique_filename", false,
                        "overwrite", true,
                        "resource_type", "image"
                );

                Map uploadResult = cloudinary.uploader().upload(tempFile, fileupload);

                // Xóa file tạm sau khi upload
                tempFile.delete();

                // Lấy URL của ảnh đã upload
                String fileUrl = (String) uploadResult.get("url");

                // Tìm người dùng từ ID và cập nhật ảnh đại diện
                Optional<User> optionalUser = userRepository.findById(updateAvatarDTO.getId());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setProfileImagePath(fileUrl); // Giả sử User có thuộc tính avatar
                    userRepository.save(user); // Lưu đối tượng User đã cập nhật
                    return fileUrl;
                } else {
                    throw new Exception("User not found");
                }
            } catch (IOException e) {
                throw new IOException("Upload ảnh thất bại", e);
            }
        }



        public CloudinaryService(PostRepository postRepository, StoryRepository storyRepository, MessageRepository messageRepository, UserRepository userRepository) {
            this.postRepository = postRepository;
            this.storyRepository = storyRepository;
            this.messageRepository = messageRepository;
            this.userRepository = userRepository;
        }

        public String uploadFileAndSaveStory(MultipartFile file) throws Exception {
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
                        "overwrite", true,
                        "resource_type", "video"
                );

                Map uploadResult = cloudinary.uploader().upload(tempFile, fileupload);

                // Xóa file tạm sau khi upload
                tempFile.delete();
                Random rand = new Random();
                // Lấy URL của file đã upload
                String fileUrl = (String) uploadResult.get("url");
                Story story = Story.builder().imageStory(fileUrl).idUser(rand.nextInt(10)).build();
                storyRepository.save(story);
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


