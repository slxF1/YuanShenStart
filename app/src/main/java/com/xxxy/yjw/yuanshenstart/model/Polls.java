package com.xxxy.yjw.yuanshenstart.model;

public class Polls {
    private int pollsid;
    private Content content_id;
    private Users users_id;
    private String polls_title;
    private String polls_time;

    public Polls() {
    }

    public int getPollsid() {
        return pollsid;
    }

    public void setPollsid(int pollsid) {
        this.pollsid = pollsid;
    }

    public Content getContent_id() {
        return content_id;
    }

    public void setContent_id(Content content_id) {
        this.content_id = content_id;
    }

    public Users getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Users users_id) {
        this.users_id = users_id;
    }

    public String getPolls_title() {
        return polls_title;
    }

    public void setPolls_title(String polls_title) {
        this.polls_title = polls_title;
    }

    public String getPolls_time() {
        return polls_time;
    }

    public void setPolls_time(String polls_time) {
        this.polls_time = polls_time;
    }

    @Override
    public String toString() {
        return "Polls{" +
                "pollsid=" + pollsid +
                ", content_id=" + content_id +
                ", users_id=" + users_id +
                ", polls_title='" + polls_title + '\'' +
                ", polls_time='" + polls_time + '\'' +
                '}';
    }
}