package com.example.temp.scan;

public class UsePDF {

    private String name;

    private int imageId;

    public UsePDF(String name,int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return this.name;
    }

    public int getImageId() {
        return this.imageId;
    }

}
