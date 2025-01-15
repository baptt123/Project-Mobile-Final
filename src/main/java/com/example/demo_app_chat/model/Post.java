package com.example.demo_app_chat.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
 String id = UUID.randomUUID().toString();
    @Embedded
    private UserInfo user;
    private String caption;
    private int likeCount;
    private int saveCount;
    private int shareCount;
    private int isLike; // 1 = liked, 0 = not liked
    private int isSaved; // 1 = saved, 0 = not saved
    private List<Comment> comments;
    private String media;
   // Getters and Setters
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getCaption() {
      return caption;
   }

   public void setCaption(String caption) {
      this.caption = caption;
   }

   public int getLikeCount() {
      return likeCount;
   }

   public void setLikeCount(int likeCount) {
      this.likeCount = likeCount;
   }

   public int getSaveCount() {
      return saveCount;
   }

   public void setSaveCount(int saveCount) {
      this.saveCount = saveCount;
   }

   public int getShareCount() {
      return shareCount;
   }

   public void setShareCount(int shareCount) {
      this.shareCount = shareCount;
   }

   public int getIsLike() {
      return isLike;
   }

   public void setIsLike(int isLike) {
      this.isLike = isLike;
   }

   public int getIsSaved() {
      return isSaved;
   }

   public void setIsSaved(int isSaved) {
      this.isSaved = isSaved;
   }

   public String getMedia() {
      return media;
   }

   public void setMedia(String media) {
      this.media = media;
   }

   public List<Comment> getComments() {
      return comments;
   }

   public void setComments(List<Comment> comments) {
      this.comments = comments;
   }

}


