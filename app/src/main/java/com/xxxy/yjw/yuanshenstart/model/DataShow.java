package com.xxxy.yjw.yuanshenstart.model;

import java.util.List;

public class DataShow {
    private List<Users> usersList;
    private List<Content> contentList;

    public DataShow() {
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return "DataShow{" +
                "usersList=" + usersList +
                ", contentList=" + contentList +
                '}';
    }
}
