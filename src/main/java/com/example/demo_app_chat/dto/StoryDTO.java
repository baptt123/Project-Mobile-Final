package com.example.demo_app_chat.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StoryDTO {
    private int id;
    private int idUser;
    private String imageStory;
}
