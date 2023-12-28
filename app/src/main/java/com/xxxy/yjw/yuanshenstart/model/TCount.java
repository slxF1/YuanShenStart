package com.xxxy.yjw.yuanshenstart.model;

public class TCount {
    private String polls_title;
    private String content_show;
    private String count;

    public TCount() {
    }

    public String getPolls_title() {
        return polls_title;
    }

    public void setPolls_title(String polls_title) {
        this.polls_title = polls_title;
    }

    public String getContent_show() {
        return content_show;
    }

    public void setContent_show(String content_show) {
        this.content_show = content_show;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TCount{" +
                "polls_title='" + polls_title + '\'' +
                ", content_show='" + content_show + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
