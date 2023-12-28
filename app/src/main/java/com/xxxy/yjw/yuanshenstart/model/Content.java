package com.xxxy.yjw.yuanshenstart.model;

public class Content {
    private int contentid;
    private String  contentshow;

    public String getContentimg() {
        return contentimg;
    }

    public void setContentimg(String contentimg) {
        this.contentimg = contentimg;
    }

    private String contentimg;

    public int getContentid() {
        return contentid;
    }

    public void setContentid(int contentid) {
        this.contentid = contentid;
    }

    public String getContentshow() {
        return contentshow;
    }

    public void setContentshow(String contentshow) {
        this.contentshow = contentshow;
    }

    public Content(int contentid, String contentshow) {
        this.contentid = contentid;
        this.contentshow = contentshow;
    }

    public Content() {
    }

    @Override
    public String toString() {
        return "Content{" +
                "contentid=" + contentid +
                ", contentshow='" + contentshow + '\'' +
                ", contentimg='" + contentimg + '\'' +
                '}';
    }
}