package com.daiy.dylib.pictureview;

import java.io.Serializable;

public class HelpTopicImageBean implements Serializable {
    private String imgUrl;//url
    private String imgInfo;//title

    public HelpTopicImageBean() {
    }

    public HelpTopicImageBean(String imgUrl, String imgInfo) {
        this.imgUrl = imgUrl;
        this.imgInfo = imgInfo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgInfo() {
        return imgInfo;
    }

    public void setImgInfo(String imgInfo) {
        this.imgInfo = imgInfo;
    }
}
