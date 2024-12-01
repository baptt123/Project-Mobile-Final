package com.example.demo_app_chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/getdata")
public class CloudinaryUploadFileController {

    // Cloudinary Configuration (thay đổi theo thông tin của bạn)
    private static final String CLOUD_NAME = "dllqdawgo";
    private static final String API_KEY = "518211464238961";
    private static final String API_SECRET = "D7rrw9Zz5E5JGRSNADfblVPprtQ";

    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

        // Tạo Cloudinary instance với thông tin cấu hình trực tiếp
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        ));

        // Kiểm tra Cloudinary config
        System.out.println("Cloudinary Cloud Name: " + cloudinary.config.cloudName);

        // Chuyển MultipartFile thành File tạm thời
        File tempFile = convertMultipartFileToFile(file);

        try {
            // Upload file lên Cloudinary
            Map<String, Object> fileupload = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            Map uploadResult = cloudinary.uploader().upload(tempFile, fileupload);

            // Xóa file tạm sau khi upload
            tempFile.delete();

            // Lấy URL của file đã upload
            String fileUrl = (String) uploadResult.get("url");
            System.out.println("File URL: " + fileUrl);

            return ResponseEntity.ok("Upload thành công: " + fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload thất bại");
        }
    }

    // Phương thức chuyển MultipartFile thành File tạm thời
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
