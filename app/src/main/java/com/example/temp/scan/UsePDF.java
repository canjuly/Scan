package com.example.temp.scan;

public class UsePDF {

    private String name;

    private int imageId;

    private String date;

    private String author;

    private String content;

    public UsePDF(String name,int imageId,String content,String date,String author) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
        this.date = date;
        this.author = author;
    }

    public String getName() {
        return this.name;
    }

    public int getImageId() {
        return this.imageId;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public String getAuthor() {
        return this.author;
    }
}