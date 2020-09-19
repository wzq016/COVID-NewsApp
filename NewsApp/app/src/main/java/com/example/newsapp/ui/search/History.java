package com.example.newsapp.ui.search;

public class History {
    private String content;
    private int imageId;

    public History(String content, int imageId){
        this.content = content;
        this.imageId = imageId;

    }

    public String getName() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }
}
