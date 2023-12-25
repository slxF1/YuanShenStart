package com.xxxy.yjw.yuanshenstart.model;

public class Users {
    private int userid;
    private int useruid;
    private String userName;

    public Users() {
    }

    public Users(int userid, int useruid, String userName) {
        this.userid = userid;
        this.useruid = useruid;
        this.userName = userName;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUseruid() {
        return useruid;
    }

    public void setUseruid(int useruid) {
        this.useruid = useruid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userid=" + userid +
                ", useruid=" + useruid +
                ", userName='" + userName + '\'' +
                '}';
    }
}