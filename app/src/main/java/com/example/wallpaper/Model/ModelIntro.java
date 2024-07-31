package com.example.wallpaper.Model;

public class ModelIntro {
    private int image;
    private int text1;
    private int text2;


    public ModelIntro() {
    }

    public ModelIntro(int image, int text1, int text2) {
        this.image = image;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getText1() {
        return text1;
    }

    public void setText1(int text1) {
        this.text1 = text1;
    }

    public int getText2() {
        return text2;
    }

    public void setText2(int text2) {
        this.text2 = text2;
    }
}
