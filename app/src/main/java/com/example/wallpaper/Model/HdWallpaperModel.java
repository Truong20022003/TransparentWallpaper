package com.example.wallpaper.Model;

public class HdWallpaperModel {
    private int idImage;
    private int imageUrl;

    public HdWallpaperModel() {
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HdWallpaperModel(int idImage, int imageUrl) {
        this.idImage = idImage;
        this.imageUrl = imageUrl;
    }
}
