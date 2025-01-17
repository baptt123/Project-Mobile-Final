package com.example.demo_app_chat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("notification")
public class Notifications {
    @Id
    private String id;
    private String idUser;
//    private int idPost;
//    private int idSenderUser;
    private String action;

    public void save(Notifications notification) {

    }
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";  // Trả về chuỗi JSON rỗng nếu có lỗi
        }
    }
}
