package com.xxxy.yjw.yuanshenstart.model;

public class ShowIndex {
    //投票显示主页内容
    private String pollsid;
    private Users users_id;
    private String polls_title;
    private String polls_time;

    public ShowIndex() {
    }

    public String getPollsid() {
        return pollsid;
    }

    public void setPollsid(String pollsid) {
        this.pollsid = pollsid;
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
        return "ShowIndex{" +
                "pollsid='" + pollsid + '\'' +
                ", users_id=" + users_id +
                ", polls_title='" + polls_title + '\'' +
                ", polls_time='" + polls_time + '\'' +
                '}';
    }
}
