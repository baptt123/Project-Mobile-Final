package com.example.demo_app_chat.dto;

    public class UpdatePostRequest {
    private int isLike;  // 1 = liked, 0 = not liked
    private int isSaved; // 1 = saved, 0 = not saved

    // Getters và Setters
    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public int getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Integer isSaved) {
        this.isSaved = isSaved;
    }
}
